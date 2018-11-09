import Managers.ScriptManager;
import org.junit.Before;
import org.junit.Test;

import static ConnectionConstants.ConnectionConstants.vkSafeTime;

public class MoreThanMaxTest {

    String max_status = "777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777";

    @Before
    public void apiSafeTime() {
        try {
            Thread.sleep(vkSafeTime);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void groupMaxLength() {
        ScriptManager.builder()
                .getConnection()
                .setUserConnection()
                .getConnection()
                .setGroupConnection()
                .collectGroupId()
                .changeGroupStatus(max_status)
                .collectGroupStatus()
                .build()
                .getApiAsserts()
                .setExpected(max_status)
                .assertLength();
    }

    @Test
    public void groupWebMaxLength() {
        ScriptManager.builder()
                .getConnection()
                .setUserConnection()
                .getConnection()
                .setGroupConnection()
                .collectGroupId()
                .changeGroupStatus(max_status)
                .build()
                .getWebAsserts()
                .initializeWebDriver()
                .setExpected(max_status)
                .collectActualGroupStatus()
                .closeDriver()
                .assertLength();
    }

    @Test
    public void userMaxLength() {
        ScriptManager.builder()
                .getConnection()
                .setUserConnection()
                .changeUserStatus(max_status)
                .collectUserStatus()
                .build()
                .getApiAsserts()
                .setExpected(max_status)
                .assertLength();
    }

    @Test
    public void userWebMaxLength() {
        ScriptManager.builder()
                .getConnection()
                .setUserConnection()
                .changeUserStatus(max_status)
                .build()
                .getWebAsserts()
                .initializeWebDriver()
                .setExpected(max_status)
                .collectActualUserStatus()
                .closeDriver()
                .assertLength();
    }
}
