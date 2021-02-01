package pages.sipTrunks;

import driver.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.security.SecureRandom;

public class SipTrunksPage {
    private static final SecureRandom RND = new SecureRandom();;
    private String lastSipTrunkXPATH = "//table/tbody/tr[1]/td[3]/span";

    public SipTrunksPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//button[@data-url=\"/p4/pbx/trunk/add\"]")
    private WebElement addNewSipTrunkButton;

    public WebElement getAddNewSipTrunkButton(){
        return this.addNewSipTrunkButton;
    }

    public String getLastSipTrunkXPATH () {
        return this.lastSipTrunkXPATH;
    }

    public String generateRandomSipTrunkName () {
            int length = 6;
            StringBuilder sb = new StringBuilder(Math.max(length, 13));
            String alphabet = "qwertyuiopasdfghjklzxcvbnm";
            for (int i = 0; i < length; i++) {
                int len = alphabet.length();
                int random = RND.nextInt(len);
                char c = alphabet.charAt(random);
                sb.append(c);
            }
            return sb.toString();
    }
}
