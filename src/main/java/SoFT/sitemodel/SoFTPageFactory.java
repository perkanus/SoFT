package SoFT.sitemodel;

import SoFT.sitemodel.SQL.ARENDE.SQLArende;
import SoFT.sitemodel.SQL.NAVIGATION.SoFTNavigation;
import SoFT.sitemodel.SQL.RESENAR.SQLResenar;
import SoFT.sitemodel.fardtjanst.Fardtjanst;
import SoFT.sitemodel.resenar.Resenar;
import org.openqa.selenium.WebDriver;
import SoFT.sitemodel.loginpage.LoginPage_Interface;
import SoFT.sitemodel.loginpage.LoginPage;
import SoFT.sitemodel.mainpage.SoFTMainPageI;
import SoFT.sitemodel.mainpage.SoFTMainPage;
import se.soprasteria.automatedtesting.webdriver.api.utility.Errors;
import se.soprasteria.automatedtesting.webdriver.helpers.driver.AutomationDriver;

public class SoFTPageFactory {

    public static LoginPage_Interface getLoginPage(AutomationDriver driver) {
        if (driver.isWeb()) {
            return new LoginPage(driver);
        }
        if (driver.isAndroid()) {
            //return Android login page
        }
        if (driver.isIos()) {
            //return iOS login page
        }
        throw new RuntimeException(getInvalidDriverError(driver));
    }

    public static SoFTMainPageI getMainPage(AutomationDriver driver) {
        if (driver.isWeb()) {
            return new SoFTMainPage(driver);
        }
        if (driver.isAndroid()) {
            //return Android main page
        }
        if (driver.isIos()) {
            //return iOS main page
        }
        throw new RuntimeException(getInvalidDriverError(driver));
    }

    public static Fardtjanst getFardtjanstPage(AutomationDriver driver) {
        if (driver.isWeb()) {
            return new Fardtjanst(driver);
        }
        if (driver.isAndroid()) {
            //return Android main page
        }
        if (driver.isIos()) {
            //return iOS main page
        }
        throw new RuntimeException(getInvalidDriverError(driver));
    }

    public static Resenar getResenarPage(AutomationDriver driver) {
        if (driver.isWeb()) {
            return new Resenar(driver);
        }
        if (driver.isAndroid()) {
            //return Android main page
        }
        if (driver.isIos()) {
            //return iOS main page
        }
        throw new RuntimeException(getInvalidDriverError(driver));
    }

    public static SQLResenar getSQLResenarPage(AutomationDriver driver) {
        if (driver.isWeb()) {
            return new SQLResenar(driver);
        }
        if (driver.isAndroid()) {
            //return Android main page
        }
        if (driver.isIos()) {
            //return iOS main page
        }
        throw new RuntimeException(getInvalidDriverError(driver));
    }

    public static SQLArende getSQLArendePage(AutomationDriver driver) {
        if (driver.isWeb()) {
            return new SQLArende(driver);
        }
        if (driver.isAndroid()) {
            //return Android main page
        }
        if (driver.isIos()) {
            //return iOS main page
        }
        throw new RuntimeException(getInvalidDriverError(driver));
    }

    public static SoFTNavigation getSoFTNavigationPage(AutomationDriver driver) {
        if (driver.isWeb()) {
            return new SoFTNavigation(driver);
        }
        if (driver.isAndroid()) {
            //return Android main page
        }
        if (driver.isIos()) {
            //return iOS main page
        }
        throw new RuntimeException(getInvalidDriverError(driver));
    }
    private static String getInvalidDriverError(WebDriver webDriver) {
        String[] fixes = {"Change webdriver configuration to a valid one for this project",
                "Implement support for webdriver: " + webDriver.toString()};
        return Errors.getErrorMessage("Tried to start unsupported webdriver", fixes);
    }

}
