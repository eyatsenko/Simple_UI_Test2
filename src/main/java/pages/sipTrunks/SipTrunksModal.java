package pages.sipTrunks;

import Driver.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SipTrunksModal {
    public SipTrunksModal() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(id = "trunkform-name")
    private WebElement nameField;

    public WebElement getNameField(){
        return this.nameField;
    }

    @FindBy(id = "trunkform-description")
    private WebElement descriptionField;

    public WebElement getDescriptionField(){
        return this.descriptionField;
    }

    @FindBy(css = "#trunk-form > div.box-footer > button.btn.btn-success")
    private WebElement addButton;

    public WebElement getAddButton(){
        return this.addButton;
    }

}
