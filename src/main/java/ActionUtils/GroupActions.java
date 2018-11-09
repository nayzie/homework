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
public class GroupActions {
    @Setter private String access_token;
    @NonNull @Getter
    ScriptManager parent;

    @Step("Get group status")
    @SneakyThrows({MalformedURLException.class, IOException.class})
    public String getGroupStatus(String id) {
        //формируем строку запроса
        String request = "https://api.vk.com/method/" +
                "status.get" +
                "?out=0" +
                "&group_id=" + id +
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

    @Step("Set group status")
    @SneakyThrows({MalformedURLException.class, IOException.class})
    public void setStatus(String s, String id) {
        //формируем строку запроса
        String request = "https://api.vk.com/method/" +
                "status.set" +
                "?text=" + s +
                "&group_id=" + id +
                "&access_token=" + access_token +
                "&v=" + ConnectionConstants.v;

        parent.getConnection().getTransportClient().post(request).getContent();

    }
}
