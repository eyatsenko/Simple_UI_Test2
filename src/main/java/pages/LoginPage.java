package pages;

import driver.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static driver.Driver.getDriver;


public class LoginPage {

    public LoginPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    public static final String loginPageURL = "https://cp-inst266-client.phonexa.xyz/auth/login/";
    private String login = "autotest@test.com";
    private String password = "12345Qwerty!";

    @FindBy(id = "loginform-email")
    private WebElement loginField;

    @FindBy(id = "loginform-password")
    private WebElement passwordField;

    public WebElement getLoginField(){
        return this.loginField;
    }

    public WebElement getPasswordField(){
        return this.passwordField;
    }

    public String getLogin (){
        return this.login;
    }

    public String getPassword (){
        return this.password;
    }

    public static LoginPage open() {
        Driver.getDriver().get(loginPageURL);
        return new LoginPage();
    }

    public static void switchToPBXmodule () {
        new WebDriverWait(getDriver(), 15, 500)
                .until(ExpectedConditions.titleIs("Select Component"));
        getDriver().navigate().to("https://cp-inst266-client.phonexa.xyz/p4/?forceComponentSwitch=pbx");
    }

}
