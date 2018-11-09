package PageObjects;

import ConnectionConstants.ConnectionConstants;
import Managers.ScriptManager;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Login {

    private By emailFieldLocator = By.id("index_email");
    private By passwordFieldLocator = By.id("index_pass");
    private By loginButtonLocator = By.id("index_login_button");

    @NonNull @Getter private ScriptManager parent;

    @NonNull private WebDriver driver;

    public Login(@NonNull WebDriver driver, @NonNull ScriptManager parent) {
        this.driver = driver;
        this.parent = parent;
    }

    private WebElement getEmailField() { return driver.findElement(emailFieldLocator); }
    private WebElement getPassField() { return driver.findElement(passwordFieldLocator); }
    private WebElement getLoginButton() { return driver.findElement(loginButtonLocator); }


    @Step("Open login page")
    public Login openLoginPage() {
        driver.get("https://vk.com");
        return this;
    }

    @Step("Enter email")
    public Login enterEmail() {
        getEmailField().sendKeys(ConnectionConstants.email);
        return this;
    }

    @Step("Enter pass")
    public Login enterPass() {
        getPassField().sendKeys(ConnectionConstants.pass);
        return this;
    }

    @Step("Submit")
    public Login submit() {
        getLoginButton().click();
        return this;
    }

    @Step("Login")
    public Login login(){
        openLoginPage()
                .enterPass()
                .enterEmail()
                .submit();
        return this;
    }

    public ScriptManager build() {
        return getParent();
    }
}
