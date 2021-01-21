package pages.sipTrunks;

import driver.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SipTrunksPage {
    public SipTrunksPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(css = "[data-url=\"/p4/pbx/trunk/add\"]")
    private WebElement addNewCategoryButton;

}
