package ActionUtils;

import ConnectionConstants.ConnectionConstants;
import Managers.ScriptManager;
import com.google.gson.JsonParser;
import io.qameta.allure.Step;
import lombok.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

@RequiredArgsConstructor
public class UserActions {

    @Setter private String access_token;
    @NonNull @Getter private ScriptManager parent;


    @Step("get user status")
    @SneakyThrows({MalformedURLException.class, IOException.class})
    public String getStatus() {
        //формируем строку запроса
        String request = "https://api.vk.com/method/" +
                "status.get" +
                "?out=0" +
                "&access_token=" + access_token +
                "&v=" + ConnectionConstants.v;

        String line = "";

        URL url = new URL(request);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        line = reader.readLine();
        reader.close();

        String responseResult = new JsonParser()
                .parse(line)
                .getAsJsonObject()
                .get("response")
                .getAsJsonObject()
                .get("text")
                .toString()
                .replace("[", "")
                .replace("]", "")
                .replace("\"", "");
        return responseResult;
    }

    @Step("set user status")
    @SneakyThrows({MalformedURLException.class, IOException.class})
    public void setStatus(String s) {
        //формируем строку запроса
        String request = "https://api.vk.com/method/" +
                "status.set" +
                "?text=" + s +
                "&access_token=" + access_token +
                "&v=" + ConnectionConstants.v;
        parent.getConnection().getTransportClient().post(request).getContent();
    }

    @Step("Get user's groups")
    @SneakyThrows({MalformedURLException.class, IOException.class})
    public String getGroupsId() {

        String responseResult;
        //формируем строку запроса
        String request = "https://api.vk.com/method/" +
                "groups.get" +
                "?filter=admin" +
                "&access_token=" + access_token +
                "&v=" + ConnectionConstants.v;
        String line = "";

        URL url = new URL(request);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        line = reader.readLine();
        reader.close();
        try {
            responseResult = new JsonParser()
                    .parse(line)
                    .getAsJsonObject()
                    .get("response")
                    .getAsJsonObject()
                    .get("items")
                    .toString()
                    .replace("[", "")
                    .replace("]", "");
        }
        catch (NullPointerException e) {
            System.out.println(line);
            responseResult = "";
        }
        return responseResult;
    }
}
