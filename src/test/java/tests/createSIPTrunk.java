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
import org.apache.log4j.Logger;
import java.util.concurrent.TimeUnit;
import static driver.Driver.getDriver;



public class createSIPTrunk {
    final Logger log = Logger.getLogger(createSIPTrunk.class);

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
        log.info("User is logged in.");
        LoginPage.switchToPBXModule();

        // wait for PBX Management Sidebar and click on it
        wait.until(ExpectedConditions.elementToBeClickable(dashboardPage.getPbxManagementSection()));
        log.info("PBX Dashboard is opened.");
        dashboardPage.getPbxManagementSection().click();

        // wait for sipTrunks Sidebar and click on it
        wait.until(ExpectedConditions.elementToBeClickable(dashboardPage.getSipTrunksMenu()));
        log.info("PBX Management section is opened.");
        dashboardPage.getSipTrunksMenu().click();

        // wait for createNewTrunk button and click on it
        wait.until(ExpectedConditions.elementToBeClickable(sipTrunksPage.getAddNewSipTrunkButton()));
        log.info("Sip Trunks page is opened.");
        sipTrunksPage.getAddNewSipTrunkButton().click();

        // fill name field
        wait.until(ExpectedConditions.elementToBeClickable(sipTrunksModal.getNameField()));
        log.info("'Add New' modal window is opened.");
        final String newName = sipTrunksPage.generateRandomSipTrunkName(6, "qwertyuiopasdfghjklzxcvbnm");
        sipTrunksModal.getNameField().sendKeys(newName);
        log.info("Name is filled.");

        // fill description
        sipTrunksModal.getDescriptionField().sendKeys(sipTrunksModal.getDescription());
        log.info("Description is filled.");

        // create new SIP trunk
        sipTrunksModal.getAddButton().click();
        log.info("'Add' button is clicked.");

        //wait for spinner disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(sipTrunksModal.getSpinnerXPATH())));
        log.info("Spinner disappeared.");

        // refresh the page
        getDriver().navigate().refresh();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By
                        .cssSelector(sipTrunksModal.getModalPopupCSS())));
        log.info("The page is reloaded.");

        // check that element has been created
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(sipTrunksPage.getLastSipTrunkXPATH())));
        WebElement createdTrunk = getDriver().findElement(By.xpath(sipTrunksPage.getLastSipTrunkXPATH()));
        Assert.assertEquals(newName, createdTrunk.getAttribute("data-title"));
        log.info("SIP trunk '" + newName + "' has been created");
    }

    @After
    public void tearDown() {
        if (getDriver() != null)
            getDriver().quit();
    }
}

