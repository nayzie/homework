package PageObjects;

import io.qameta.allure.Step;
import lombok.Getter;
import lombok.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static ConnectionConstants.ConnectionConstants.home_url;

public class SimplifiedMain {
    private By statusLocator = By.className("current_info");

    @NonNull private WebDriver driver;
    @NonNull @Getter private Object parent;

    @Getter private String status;


    public SimplifiedMain(@NonNull WebDriver driver, @NonNull Object parent) {
        this.driver = driver;
        this.parent = parent;
    }

    private WebElement getStatusElement() { return driver.findElement(statusLocator); }


    @Step("Open Main Page")
    public SimplifiedMain openMainPage() {
        driver.get(home_url);
        return this;
    }


    @Step("Get status at the page")
    public SimplifiedMain collectUserStatus() {
        status = getStatusElement().getText();
        return this;
    }




}
