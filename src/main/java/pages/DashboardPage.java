package pages;

import driver.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DashboardPage {
    public DashboardPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    public static final String dashboardPageURL = "https://cp-inst266-client.phonexa.xyz/p4/dashboard/index";

    @FindBy(css = "body > div.wrapper > aside.main-sidebar > section > ul > li:nth-child(5) > a")
    private WebElement pbxManagementSection;

    @FindBy(css = "body > div.wrapper > aside.main-sidebar > section > ul > li.treeview.menu-opened > ul > li:nth-child(6) > a")
    private WebElement sipTrunksMenu;

    public WebElement getPbxManagementSection(){
        return this.pbxManagementSection;
    }

    public WebElement getSipTrunksMenu(){
        return this.sipTrunksMenu;
    }
}
