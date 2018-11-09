package Asserts;

import Managers.AssertManager;
import io.qameta.allure.Step;
import lombok.*;

import static ConnectionConstants.ConnectionConstants.max_status_length;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@RequiredArgsConstructor
public class WebAsserts {
    @NonNull private String actual;
    @NonNull private AssertManager parent;
    @Getter private String expected;

    public WebAsserts setExpected(String expected) {
        this.expected = expected;
        return this;
    }

    public WebAsserts initializeWebDriver() {
        parent.getParent().initializeWebDriver();
        return this;
    }

    @Step ("Login and collect actual status")
    public WebAsserts collectActualUserStatus() {
        actual = parent
                .getParent()
                .getLoginPage()
                .openLoginPage()
                .enterEmail()
                .enterPass()
                .submit()
                .getParent()
                .getSimplifiedMain()
                .openMainPage()
                .collectUserStatus()
                .getStatus();
        return this;
    }

    @Step ("Login and collect actual status")
    public WebAsserts collectActualGroupStatus() {
        actual = parent
                .getParent()
                .getLoginPage()
                .openLoginPage()
                .enterEmail()
                .enterPass()
                .submit()
                .getParent()
                .getSimplifiedGroup()
                .openGroupPage()
                .collectGroupStatus()
                .getStatus();
        return this;
    }

    @Step ("Close driver")
    public WebAsserts closeDriver(){
        parent.getParent().getDriver().quit();
        return this;
    }

    @Step("Assert that actual is same as expected")
    public void assertStatus() {
        assertThat(actual, is(equalTo(expected)));
    }
    @Step ("Assert that actual length is same as max length")
    public void assertLength() { assertThat(actual.length(), is(equalTo(max_status_length))); }

}
