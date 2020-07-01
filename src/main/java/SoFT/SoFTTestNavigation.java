package SoFT;

import SoFT.base.SoFTBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import se.soprasteria.automatedtesting.webdriver.helpers.driver.AutomationDriver;


public class SoFTTestNavigation extends SoFTBaseTest {

    public enum TopNavigation {
        SOFT, ARENDEHANTERING, ARENDEHANTERINGARROW, INSTRUMENTPANELER, RESENARER, FARDTJANSARENDEN, SJUKRESEARENDEN, OVERKLAGANDEARENDEN
    }

    public enum ResenarNavigation {
        RESENAR, FTJARENDE, SJRARENDE, OVERKLAGANDEARENDE,
        RESESALDOJUSTERING
    }

    @Test(dataProvider = "getDriver", groups = {"standard"}, priority = 3, invocationCount = 5)
    public void Navigation(AutomationDriver driver) {
        try {
            Assert.assertTrue(loginPageInterface.performLogin(), "loginPage.performLogin() failed");
            Assert.assertTrue(mainPage.isPageLoaded(), "mainPage.isPageLoaded() failed");
            Assert.assertTrue(softNavigation.TopNavigation(TopNavigation.SOFT), "mainPage.SoFTPrepare() failed");
            Assert.assertTrue(softNavigation.TopNavigation(TopNavigation.ARENDEHANTERING), "mainPage.SoFTPrepare() failed");
            //Assert.assertTrue(softNavigation.TopNavigation(TopNavigation.ARENDEHANTERINGARROW), "mainPage.SoFTPrepare() failed");
            //Assert.assertTrue(softNavigation.TopNavigation(TopNavigation.INSTRUMENTPANELER), "mainPage.SoFTPrepare() failed");
            Assert.assertTrue(softNavigation.TopNavigation(TopNavigation.RESENARER), "mainPage.SoFTPrepare() failed");
            Assert.assertTrue(softNavigation.TopNavigation(TopNavigation.FARDTJANSARENDEN), "mainPage.SoFTPrepare() failed");
            Assert.assertTrue(softNavigation.TopNavigation(TopNavigation.SJUKRESEARENDEN), "mainPage.SoFTPrepare() failed");
            Assert.assertTrue(softNavigation.TopNavigation(TopNavigation.OVERKLAGANDEARENDEN), "mainPage.SoFTPrepare() failed");
        } catch (AssertionError err) {
        }

    }
}
