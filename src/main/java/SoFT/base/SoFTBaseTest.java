package SoFT.base;
import SoFT.sitemodel.*;
import SoFT.sitemodel.SQL.ARENDE.SQLArende;
import SoFT.sitemodel.SQL.NAVIGATION.SoFTNavigation;
import SoFT.sitemodel.SQL.RESENAR.SQLResenar;
import SoFT.sitemodel.fardtjanst.Fardtjanst;
import SoFT.sitemodel.loginpage.LoginPage_Interface;
import SoFT.sitemodel.mainpage.SoFTMainPageI;
import SoFT.sitemodel.resenar.Resenar;
import se.soprasteria.automatedtesting.webdriver.api.base.BaseTestCase;
import se.soprasteria.automatedtesting.webdriver.api.base.BaseTestConfig;
import se.soprasteria.automatedtesting.webdriver.helpers.driver.AutomationDriver;

public class SoFTBaseTest extends BaseTestCase {

    protected SoFTMainPageI mainPage;
    protected LoginPage_Interface loginPageInterface;
    protected Fardtjanst fardtjanstPage;
    protected Resenar resenarPage;
    protected SQLResenar sqlResenarPage;
    protected SQLArende sqlArendePage;
    protected SoFTNavigation softNavigation;

    @Override
    protected String getDriverConfigId() {
        return "chromedriver";
    }

    @Override
    protected String getConfigFile() {
        return "SoFT/SoFT_config.xml";
    }

    @Override
    protected void initPages(AutomationDriver driver) {
        logger.info("Initialising pages to be used in test");
        loginPageInterface = SoFTPageFactory.getLoginPage(driver);
        mainPage = SoFTPageFactory.getMainPage(driver);
        fardtjanstPage = SoFTPageFactory.getFardtjanstPage(driver);
        resenarPage = SoFTPageFactory.getResenarPage(driver);
        sqlResenarPage = SoFTPageFactory.getSQLResenarPage(driver);
        sqlArendePage = SoFTPageFactory.getSQLArendePage(driver);
        softNavigation = SoFTPageFactory.getSoFTNavigationPage(driver);
    }

    @Override
    protected void initializeDriver(AutomationDriver driver) {
        if (driver.isWeb()) {
            driver.navigate().to(BaseTestConfig.getConfigurationOption("url_mainpage"));
        } else if (driver.isAndroid()){
            driver.navigate().to("https://mobile.twitter.com/login");
        } else if (driver.isIos()){
            driver.navigate().to("https://mobile.twitter.com/login");
        }
        sleep(1000);
        logger.info("Navigated to Twitter's login-page, sleeping for 1s to allow it to initiliaze");
    }
}
