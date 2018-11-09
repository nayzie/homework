package Managers;

import ActionUtils.Connection;
import ActionUtils.GroupActions;

import ActionUtils.UserActions;
import Asserts.ApiAsserts;
import Asserts.WebAsserts;
import PageObjects.Login;
import PageObjects.SimplifiedGroup;
import PageObjects.SimplifiedMain;
import io.qameta.allure.Step;
import lombok.*;
import org.openqa.selenium.WebDriver;

@Getter
public class ScriptManager {
    Connection connection;
    UserActions userActions;
    GroupActions groupActions;
    Login loginPage;
    ApiAsserts apiAsserts;
    WebAsserts webAsserts;
    SimplifiedMain simplifiedMain;
    SimplifiedGroup simplifiedGroup;

    @Setter private String userStatus;
    @Setter private String expUserStatus;
    @Setter private String groupStatus;
    @Setter private String expGroupStatus;
    @Getter private String groupId;

    private String actual;
    private WebDriver driver;

    public ScriptManager() {
        connection = new Connection(this);
        userActions = new UserActions(this);
        groupActions = new GroupActions(this);


    }

    public static ScriptManager builder() {
        return new ScriptManager();
    }

    @Step
    public ScriptManager initializeWebDriver(){

        driver = new DriverManager().getDriver();

        loginPage = new Login(driver, this);
        simplifiedMain = new SimplifiedMain(driver, this);
        simplifiedGroup = new SimplifiedGroup(driver, this);
        return this;
    }



    @Step
    public ScriptManager collectUserStatus() {
        setUserStatus(userActions.getStatus());
        actual = getUserStatus();
        return this;
    }

    @Step
    public ScriptManager collectGroupStatus() {
        setGroupStatus(groupActions.getGroupStatus(groupId));
        actual = getGroupStatus();
        return this;
    }

    @Step
    public ScriptManager collectGroupStatus(String id) {
        setGroupStatus(groupActions.getGroupStatus(id));
        actual = getGroupStatus();
        return this;
    }

    @Step
    public ScriptManager changeUserStatus(String status) {
        userActions.setStatus(status);
        return this;
    }

    @Step
    public ScriptManager changeGroupStatus(String status, String id) {
        groupActions.setStatus(status, id);
        return this;
    }

    @Step
    public ScriptManager changeGroupStatus(String status) {

        groupActions.setStatus(status, groupId);
        return this;
    }

    @Step
    public ScriptManager collectGroupId(){
        groupId = userActions.getGroupsId().split(",")[0];
        return this;
    }



    public AssertManager build() {
        return new AssertManager(this, actual);
    }

}
