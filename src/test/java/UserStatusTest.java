import Managers.ScriptManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static ConnectionConstants.ConnectionConstants.vkSafeTime;

@RequiredArgsConstructor
@RunWith(value = Parameterized.class)
public class UserStatusTest {
    @NonNull String actual;
    @NonNull String expected;

    @Before
    public void apiSafeTime() {
        try {
            Thread.sleep(vkSafeTime);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {

        Object[][] data = new Object[][]{
                {"", ""},
                {"Pupa", "Pupa"},
                {"1234567890", "1234567890"},
                {"https://pddmaster.ru/ekzamen-pdd", "https://pddmaster.ru/ekzamen-pdd"},
                {"الليل", "الليل"},
                {":love:", ":love:"},
                {"Select%20id%20from%20user", "Select id from user"},
                {"%20", ""},


        };
        return Arrays.asList(data);
    }

    @Test
    public void userApiStatusTest() {
        ScriptManager.builder()
                .getConnection()
                .setUserConnection()
                .changeUserStatus(actual)
                .collectUserStatus()
                .build()
                .getApiAsserts()
                .setExpected(expected)
                .assertStatus();
    }


}
