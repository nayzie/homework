package Managers;

import Asserts.ApiAsserts;
import Asserts.WebAsserts;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class AssertManager {
    ApiAsserts apiAsserts;
    @Getter(AccessLevel.NONE)
    WebAsserts webAsserts;
    ScriptManager parent;
    @Getter(AccessLevel.NONE) DriverManager driverManager;

    public AssertManager(ScriptManager parent, String actual) {
        this.parent = parent;
        if(actual == null) actual = "";
        apiAsserts = new ApiAsserts(actual, this);
        webAsserts = new WebAsserts(actual, this);



    }

    public WebAsserts getWebAsserts() {

        return webAsserts;
    }
}
