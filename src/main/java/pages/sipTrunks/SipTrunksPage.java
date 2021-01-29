package pages.sipTrunks;

import driver.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SipTrunksPage {
    private String lastSipTrunkXPATH = "//table/tbody/tr[1]/td[3]/span";

    public SipTrunksPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(css = "body > div.wrapper > div.content-wrapper > section.content > div:nth-child(3) > div > div > div.box-header > div > div > div.input-group-btn > button")
    private WebElement addNewSipTrunkButton;

    public WebElement getAddNewSipTrunkButton(){
        return this.addNewSipTrunkButton;
    }

    public String getLastSipTrunkXPATH () {
        return this.lastSipTrunkXPATH;
    }
}
