package ActionUtils;

import Managers.ScriptManager;
import com.vk.api.sdk.client.ClientResponse;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import io.qameta.allure.Step;
import lombok.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;

import static ConnectionConstants.ConnectionConstants.*;


@RequiredArgsConstructor
public class Connection {

    @Getter private String access_token_user;
    @Getter private String access_token_group;
    private boolean isGroup;
    @Getter private TransportClient transportClient;

    @NonNull @Getter private ScriptManager parent;


    @Step("Set connection and get authtoken")
    @SneakyThrows({MalformedURLException.class, IOException.class})
    public ScriptManager setUserConnection() {
        isGroup = false;
        transportClient = HttpTransportClient.getInstance();

        ClientResponse response = authorize();
        // Получили редирект на подтверждение требований приложения
        try {
            Thread.sleep(vkSafeTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String HeaderLocation = getRelocation(login(response));

        // Проходим по нему
        try {
            Thread.sleep(vkSafeTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        response = transportClient.post(HeaderLocation);

        // Теперь в след редиректе необходимый токен
        // Теперь последний редирект на получение токена


        HeaderLocation = getRelocation(grantAccess(response));


        // Просто спарсим его сплитами
        if (HeaderLocation != null) {
            if (HeaderLocation.contains("access_token")) {
                access_token_user = HeaderLocation.split("#")[1].split("&")[0].split("=")[1];
                parent.getUserActions().setAccess_token(access_token_user);
            }
            else
            {
                try {
                    Thread.sleep(vkSafeTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                response = transportClient.post(HeaderLocation);
                HeaderLocation = getRelocation(response);
                access_token_user = HeaderLocation.split("#")[1].split("&")[0].split("=")[1];
                parent.getUserActions().setAccess_token(access_token_user);
            }
        }
        return parent;
    }

    @Step("Set connection and get authtoken")
    public ScriptManager setGroupConnection(String groupIds) {
        isGroup = true;
        transportClient = HttpTransportClient.getInstance();
        try {
            Thread.sleep(vkSafeTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ClientResponse response = authorizeForGroup(groupIds);
        // Получили редирект на подтверждение требований приложения
        // Проходим по нему
        // Теперь в след редиректе необходимый токен
        // Теперь последний редирект на получение токена
        // Просто спарсим его сплитами

        String HeaderLocation = getRelocation(grantAccess(response));
        if (HeaderLocation != null) {
            access_token_group = HeaderLocation.split("#")[1].split("&")[0].split("=")[1];
            parent.getUserActions().setAccess_token(access_token_group);
        }
        return parent;
    }

    @Step("Set connection and get authtoken")
    public ScriptManager setGroupConnection() {
        isGroup = true;
        transportClient = HttpTransportClient.getInstance();
        String groupIds = parent.getUserActions().getGroupsId();
        ClientResponse response = authorizeForGroup(groupIds);
        // Получили редирект на подтверждение требований приложения
        // Проходим по нему
        // Теперь в след редиректе необходимый токен
        // Теперь последний редирект на получение токена
        // Просто спарсим его сплитами
        try {
            Thread.sleep(vkSafeTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String HeaderLocation = getRelocation(grantAccess(response));
        if (HeaderLocation != null) {
            access_token_group = HeaderLocation.split("#")[1].split("&")[0].split("=")[1];
            parent.getUserActions().setAccess_token(access_token_group);
        }
        return parent;
    }

    @Step("authorize")
    @SneakyThrows({MalformedURLException.class, IOException.class})
    private ClientResponse authorize() {

        ClientResponse response;
        try {
            Thread.sleep(vkSafeTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        response = transportClient.post(
                "https://oauth.vk.com/authorize?" +
                        "client_id="+client_id+
                        "&scope="+scope+
                        "&redirect_uri="+redirect_uri+
                        "&display="+display+
                        "&response_type="+response_type+
                        "&v="+v
        );

        return  response;
    }

    @Step("authorize")
    @SneakyThrows({MalformedURLException.class, IOException.class})
    private ClientResponse authorizeForGroup(String groupsids) {
        ClientResponse response;
        try {
            Thread.sleep(vkSafeTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        response = transportClient.post(
                "https://oauth.vk.com/authorize?" +
                        "client_id="+client_id+
                        "group_ids" + groupsids +
                        "&scope="+scope+",messages,wall"+
                        "&redirect_uri="+redirect_uri+
                        "&display="+display+
                        "&response_type="+response_type+
                        "&v="+v
        );

        return  response;
    }

    @Step("Login")
    @SneakyThrows({MalformedURLException.class, IOException.class})
    private ClientResponse login(ClientResponse response) {


        //Получаем редирект
        if(response.getHeaders().get("Location") == null) {
            try {
                Document loginPageDOM = Jsoup.parse(response.getContent());
                String ip_h = loginPageDOM.getElementsByAttributeValue("name", "ip_h").get(0).val();
                String lg_h = loginPageDOM.getElementsByAttributeValue("name", "lg_h").get(0).val();
                String to = loginPageDOM.getElementsByAttributeValue("name", "to").get(0).val();

                //Для запроса авторизации необходимо два параметра полученных в первом запросе
                try {
                    Thread.sleep(vkSafeTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                response = transportClient.post("https://login.vk.com/?act=login&soft=1" +
                        "&ip_h=" + ip_h +
                        "&_origin=https://oauth.vk.com" +
                        "&lg_h=" + lg_h +
                        "&to=" + to +
                        "&expire=0" +
                        "&email=" + email +
                        "&pass=" + pass);
            }
            catch (IndexOutOfBoundsException e) {
                System.out.println("Token already taken");;
            }
            }


        else{
            String HeaderLocation = getRelocation(response);
            try {
                Thread.sleep(vkSafeTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            response = transportClient.post(HeaderLocation);
        }
        return response;
    }


    @Step("Grand access")
    @SneakyThrows({MalformedURLException.class, IOException.class})
    private ClientResponse grantAccess(ClientResponse response) {

        if ( response.getHeaders().get("Location") == null) {
            try {
                String HeaderLocation =
                        Jsoup.parse(response.getContent())
                                .getElementsByAttributeValue("method", "post")
                                .get(0)
                                .attributes()
                                .get("action");
                try {
                    Thread.sleep(vkSafeTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                response = transportClient.post(HeaderLocation);
            }
            catch (IndexOutOfBoundsException e) {
                System.out.println("Token already taken");
            }

        }
        else {
            String HeaderLocation = getRelocation(response);

            // Проходим по нему
            try {
                Thread.sleep(vkSafeTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            response = transportClient.post(HeaderLocation);
        }

        return response;
    }

    private String getRelocation(ClientResponse response) {
        String HeaderLocation = response.getHeaders().get("Location");

        if(HeaderLocation != null && HeaderLocation.contains("access_token")) {
            if (isGroup) {
                access_token_group = HeaderLocation.split("#")[1].split("&")[0].split("=")[1];
                parent.getGroupActions().setAccess_token(access_token_group);
            }
            else {
                access_token_user = HeaderLocation.split("#")[1].split("&")[0].split("=")[1];
                parent.getUserActions().setAccess_token(access_token_user);
            }
        }
        // Проходим по нему

        return HeaderLocation;

    }



}
