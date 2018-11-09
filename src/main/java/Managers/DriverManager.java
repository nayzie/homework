package Managers;

import io.github.bonigarcia.wdm.Architecture;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.AccessLevel;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static ConnectionConstants.ConnectionConstants.RESOLUTION_HEIGHT;
import static ConnectionConstants.ConnectionConstants.RESOLUTION_LENGTH;

public class DriverManager {
    @Getter(AccessLevel.NONE) private static ChromeOptions chromeOptions;
    @Getter private WebDriver driver;

    public DriverManager() {
        setDriver();
    }

    private void setDriver() {

        WebDriverManager.config().setArchitecture(Architecture.X64);

        WebDriverManager.chromedriver().setup();
        chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--window-size="+ RESOLUTION_LENGTH +"," + RESOLUTION_HEIGHT);
        driver = new ChromeDriver(chromeOptions);

        WebDriver.Timeouts timeouts = driver.manage().timeouts();
        timeouts.implicitlyWait(100, TimeUnit.SECONDS);
        timeouts.pageLoadTimeout(400, TimeUnit.SECONDS);
        timeouts.setScriptTimeout(400, TimeUnit.SECONDS);

    }

}
