package pages;

import driver.Driver;
import org.openqa.selenium.support.PageFactory;

public class DashboardPage {
    public DashboardPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    public static final String dashboardPageURL = "https://cp-inst266-client.phonexa.xyz/p4/dashboard/index";

}
