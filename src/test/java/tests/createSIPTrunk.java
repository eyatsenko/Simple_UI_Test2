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
        WebDriverWait wait = new WebDriverWait(getDriver(), 15);

        // login to the instance
        LoginPage.open();
        loginPage.getLoginField().sendKeys(loginPage.getLogin());
        loginPage.getPasswordField().sendKeys(loginPage.getPassword());
        loginPage.getPasswordField().sendKeys(Keys.ENTER);

        // switch to PBX module
        getDriver().manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
        LoginPage.switchToPBXmodule();

        // wait for PBX Management Sidebar and click on it
        wait.until(ExpectedConditions.elementToBeClickable(dashboardPage.getPbxManagementSection()));
        dashboardPage.getPbxManagementSection().click();

        // wait for sipTrunks Sidebar and click on it
        wait.until(ExpectedConditions.elementToBeClickable(dashboardPage.getSipTrunksMenu()));
        dashboardPage.getSipTrunksMenu().click();

        // save the last name of sip trunk
        String lastName = getDriver().findElement(By.xpath(sipTrunksPage.getLastSipTrunkXPATH())).getAttribute("data-title");
        Integer lastNameInt = Integer.parseInt(lastName);

        // wait for createNewTrunk button and click on it
        wait.until(ExpectedConditions.elementToBeClickable(sipTrunksPage.getAddNewSipTrunkButton()));
        sipTrunksPage.getAddNewSipTrunkButton().click();

        // fill name field
        StringBuilder sb = new StringBuilder();
        Integer newNameInt = ++lastNameInt;
        StringBuilder newName = sb.append(newNameInt.toString());
        final String newNameString = newName.toString();

        sipTrunksModal.getNameField().sendKeys(newNameString);

        // fill description
        sipTrunksModal.getDescriptionField().sendKeys(sipTrunksModal.getDescription());

        // create new SIP trunk
        sipTrunksModal.getAddButton().click();

        //wait for spinner disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(sipTrunksModal.getSpinnerXPATH())));

        // refresh the page
        getDriver().navigate().refresh();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By
                        .cssSelector(sipTrunksModal.getModalPopupCSS())));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(sipTrunksPage.getLastSipTrunkXPATH())));

        // check that element has been created
        WebElement createdTrunk = getDriver().findElement(By.xpath(sipTrunksPage.getLastSipTrunkXPATH()));
        Assert.assertEquals(newNameString, createdTrunk.getAttribute("data-title"));
        System.out.println("SIP trunk " + newNameString + " has been created");
    }

    @After
    public void tearDown() {
        if (getDriver() != null)
            getDriver().quit();
    }
}

