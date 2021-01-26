package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import static Driver.Driver.getDriver;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class createSIPTrunk {
    private WebDriver driver;
    private WebDriverWait wait;

    private String signInButtonCSS = "#login-form > div.row.button-area > div.col-xs-4 > button";
    private String login = "autotest@test.com";
    private String password = "12345Qwerty!";
    private String pbxUrl = "https://cp-inst266-client.phonexa.xyz/p4/?forceComponentSwitch=pbx";
    private String pbxManagementCSS = "body > div.wrapper > aside.main-sidebar > section > ul > li:nth-child(6) > a";
    private String sipTrunksCSS = "body > div.wrapper > aside.main-sidebar > section > ul > li.treeview.menu-opened > ul > li:nth-child(6) > a";
    private String createNewTrunkCSS = "body > div.wrapper > div.content-wrapper > section.content > div:nth-child(3) > div > div > div.box-header > div > div > div.input-group-btn > button";
    private String modalWindowCSS = "#modalPopup > div > div";
    private String nameFieldID = "trunkform-name";
    private String descriptionFieldID = "trunkform-description";
    private String addButtonCSS = "#trunk-form > div.box-footer > button.btn.btn-success";
    private String description = "autotestdesc";

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
        getDriver().manage().window().maximize();
    }

    @Test
    public void checkSipTrunk() {
        LoginPage loginPage = new LoginPage();
        LoginPage.open();

        // login to the instance
        loginPage.getLoginField().sendKeys(login);
        loginPage.getPasswordField().sendKeys(password);
        loginPage.getPasswordField().sendKeys(Keys.ENTER);

        // switch to PBX module
        //driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
        driver.navigate().to(pbxUrl);

        // wait for PBX Management Sidebar and click on it
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector(pbxManagementCSS)).click();

        // wait for sipTrunks  Sidebar and click on it
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector(sipTrunksCSS)).click();


        // wait for button and click on it
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector(createNewTrunkCSS)).click();

        // fill name field
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Date date = new Date();
        String name = String.valueOf(date.getTime());
        driver.findElement(By.id(nameFieldID)).sendKeys(name);

        // fill description
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id(descriptionFieldID)).sendKeys(description);

        // create new SIP trunk
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector(addButtonCSS)).click();

        // refresh the page
        driver.navigate().refresh();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // check that element has been created
        WebElement createdTrunk = driver.findElement(By.xpath("//table/tbody/tr[1]/td[3]/span"));
        Assert.assertEquals(createdTrunk.getAttribute("data-title"), name);
    }

    @After
    public void tearDown() {
        if (driver != null)
            driver.quit();
    }
}

