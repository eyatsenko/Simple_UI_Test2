package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.DashboardPage;
import pages.LoginPage;
import pages.sipTrunks.SipTrunksModal;
import pages.sipTrunks.SipTrunksPage;

import java.util.concurrent.TimeUnit;

import static driver.Driver.getDriver;

public class createSIPTrunk {
    private String login = "autotest@test.com";
    private String password = "12345Qwerty!";
    private String pbxUrl = "https://cp-inst266-client.phonexa.xyz/p4/?forceComponentSwitch=pbx";
    private String description = "autotestdesc";

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
        getDriver().manage().window().maximize();
    }

    @Test
    public void checkSipTrunk() {
        LoginPage loginPage = new LoginPage();
        DashboardPage dashboardPage = new DashboardPage();
        SipTrunksPage sipTrunksPage = new SipTrunksPage();
        SipTrunksModal sipTrunksModal = new SipTrunksModal();

        // login to the instance
        LoginPage.open();
        loginPage.getLoginField().sendKeys(login);
        loginPage.getPasswordField().sendKeys(password);
        loginPage.getPasswordField().sendKeys(Keys.ENTER);

        // switch to PBX module
        getDriver().manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
        new WebDriverWait(getDriver(), 15, 500)
                .until(ExpectedConditions.titleIs("Select Component"));
        getDriver().navigate().to(pbxUrl);

        // wait for PBX Management Sidebar and click on it
        new WebDriverWait(getDriver(), 15, 500)
                .until(ExpectedConditions.elementToBeClickable(dashboardPage.getPbxManagementSection()));
        dashboardPage.getPbxManagementSection().click();

        // wait for sipTrunks Sidebar and click on it
        new WebDriverWait(getDriver(), 15, 500)
                .until(ExpectedConditions.elementToBeClickable(dashboardPage.getSipTrunksMenu()));
        dashboardPage.getSipTrunksMenu().click();

        // save the last name of sip trunk
        String lastName = getDriver().findElement(By.xpath("//table/tbody/tr[1]/td[3]/span")).getAttribute("data-title");
        Integer lastNameInt = Integer.parseInt(lastName);

        // wait for createNewTrunk button and click on it
        new WebDriverWait(getDriver(), 15, 500)
                .until(ExpectedConditions.elementToBeClickable(sipTrunksPage.getAddNewSipTrunkButton()));
        sipTrunksPage.getAddNewSipTrunkButton().click();

        // fill name field
        StringBuilder sb = new StringBuilder();
        Integer newNameInt = ++lastNameInt;
        StringBuilder newName = sb.append(newNameInt.toString());
        final String newNameString = newName.toString();

        sipTrunksModal.getNameField().sendKeys(newNameString);

        // fill description
        sipTrunksModal.getDescriptionField().sendKeys(description);

        // create new SIP trunk
        sipTrunksModal.getAddButton().click();

        //wait for spinner disappear
        new WebDriverWait(getDriver(), 10, 200)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='formLoaderContent']")));

        // refresh the page
        getDriver().navigate().refresh();

        new WebDriverWait(getDriver(), 10, 500)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#modalPopup > div > div")));

        new WebDriverWait(getDriver(), 15, 500)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table/tbody/tr[1]/td[3]/span")));

        // check that element has been created
        WebElement createdTrunk = getDriver().findElement(By.xpath("//table/tbody/tr[1]/td[3]/span"));
        Assert.assertEquals(newNameString, createdTrunk.getAttribute("data-title"));
        System.out.println("SIP trunk " + newNameString + " has been created");
    }

    @After
    public void tearDown() {
        if (getDriver() != null)
            getDriver().quit();
    }
}

