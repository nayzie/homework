package PageObjects;

import Managers.ScriptManager;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static ConnectionConstants.ConnectionConstants.group_url;

public class SimplifiedGroup {
    private By statusLocator = By.className("current_text");

    @NonNull
    private WebDriver driver;
    @NonNull @Getter
    private ScriptManager parent;

    @Getter private String status;


    public SimplifiedGroup(@NonNull WebDriver driver, @NonNull ScriptManager parent) {
        this.driver = driver;
        this.parent = parent;
    }

    private WebElement getStatusElement() { return driver.findElement(statusLocator); }


    @Step("Open Main Page")
    public SimplifiedGroup openGroupPage() {
        driver.get(group_url + parent.getGroupId());
        return this;
    }



    @Step("Get status at the page")
    public SimplifiedGroup collectGroupStatus() {
        status = getStatusElement().getText();
        return this;
    }
}
