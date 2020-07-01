package SoFT.sitemodel.loginpage;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import se.soprasteria.automatedtesting.webdriver.api.base.BasePageObject;
import se.soprasteria.automatedtesting.webdriver.helpers.driver.AutomationDriver;

import java.io.IOException;

public class LoginPage extends BasePageObject implements LoginPage_Interface {
    @FindBy(css = ".submit.EdgeButton.EdgeButton--primary.EdgeButtom--medium") // used from classname: submit EdgeButton EdgeButton--primary EdgeButtom--medium
    protected WebElement loginButton;
    @FindBy(css = ".js-username-field.email-input.js-initial-focus") // used from classname: js-username-field email-input js-initial-focus
    protected WebElement usernameTextbox;
    @FindBy(css = ".js-password-field") // used from classname: js-password-field
    protected WebElement passwordTextbox;

    public LoginPage(AutomationDriver driver) {
        super(driver);
    }


    @Override
    public boolean isPageLoaded() {
        // TODO: IMPLEMENT
        logger.info("Verifying that the loginpage is visible");
        return true;
        //return elementHelper.isElementDisplayedWithinTime(loginButton, 5000);
    }


    public boolean performLogin(){
        try {
            ProcessBuilder pb = new ProcessBuilder("C:\\Program Files (x86)\\AutoIt3\\AutoIt3.exe", "C:\\work\\Username.au3");
            Process p = pb.start();
            sleep(2000);
            logger.info("Typed username");
            ProcessBuilder pa = new ProcessBuilder("C:\\Program Files (x86)\\AutoIt3\\AutoIt3.exe", "C:\\work\\password.au3");
            Process p_a = pa.start();
            logger.info("Typed password");
            return true;
        }
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
            return false;
        }
    }

}
