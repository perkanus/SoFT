package SoFT.sitemodel.mainpage;

import SoFT.data.EnumTypes;
import SoFT.data.ArendeData;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import se.soprasteria.automatedtesting.webdriver.api.base.BasePageObject;
import se.soprasteria.automatedtesting.webdriver.helpers.driver.AutomationDriver;

public class SoFTMainPage extends BasePageObject implements SoFTMainPageI {
    boolean VISUAL_DEBUG = true;


    // SOFT CRM ribbon knapp
    @FindBy(xpath = "//*[@id='TabnavTabLogoTextId']")
    protected WebElement btnSoftCRM;

    // Ärendehantering ribbon knapp
    @FindBy(xpath = "//*[@id='TabCS-main']")
    protected WebElement btnArendeHantering;

    // Ärendehantering-pil ribbon knapp
    @FindBy(xpath = "//*[@id='TabCS']")
    protected WebElement ribbonBtnArendehanteringArrow;

    // Ärendehantering-pil ribbon knapp
    @FindBy(xpath = "//a[@id='nav_contacts']")
    protected WebElement ribbonBtnResenar;

    // Tabell Färdtjänstärende
    @FindBy(xpath = "//*[@id=\"component_fardtjanstarende_d\"]")
    protected WebElement tblFardtjanstAr;

    // Tabell Överklagandeärende
    @FindBy(xpath = "//*[@id=\"component_overklagandearende_d\"]")
    protected WebElement tblOverklagandeAr;

    // Tabell Uppgift till sakkunnig
    @FindBy(xpath = "//*[@id=\"component_uppgift_till_sakkunnig_d\"]")
    protected WebElement tblUppgiftSak;

    // Tabell Uppgift till sakkunnig
    @FindBy(xpath = "//*[@id='Tabnav_contacts-main']/a")
    protected WebElement ribbonBtnResenarer;


    public SoFTMainPage(AutomationDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageLoaded() {
        WebDriverWait wait = new WebDriverWait(driver,10);
        driver.switchTo().defaultContent();

        boolean staleElement= true;
        while(staleElement){
            try{
                wait.until(ExpectedConditions.elementToBeClickable(btnSoftCRM));
                staleElement = false;

            } catch(StaleElementReferenceException e){
                logger.info("Stale element at AddForeskrift #2 usage of btnSoftCRM");
                staleElement = true;
            }
        }

        if (elementHelper.isElementDisplayedWithinTime(btnSoftCRM, 15000) )
        {
            return true;
        }
        else
        {
            return false;
        }

        //return elementHelper.isElementDisplayedWithinTime(tblUppgiftSak, 15000);
    }

    @Override
    public boolean SoFTPrepare(EnumTypes.Menuchoice menu) {
        WebDriverWait wait = new WebDriverWait(driver,20);
        // TODO: Make this default
        driver.switchTo().defaultContent();

        boolean staleElement= true;

        while(staleElement){
            try{
                wait.until(ExpectedConditions.elementToBeClickable(btnSoftCRM));
                staleElement = false;

            } catch(StaleElementReferenceException e){
                logger.info("Stale element at SoFTPrepare #1 usage of ribbonBtnArendehanteringArrow");
                staleElement = true;
            }
        }
        staleElement = true;
        while(staleElement){
            try{
                wait.until(ExpectedConditions.elementToBeClickable(ribbonBtnArendehanteringArrow));
                staleElement = false;

            } catch(StaleElementReferenceException e){
                logger.info("Stale element at SoFTPrepare #1 usage of ribbonBtnArendehanteringArrow");
                staleElement = true;
            }
        }

        if(VISUAL_DEBUG)
        {
            wait.until(ExpectedConditions.elementToBeClickable(ribbonBtnArendehanteringArrow));
            drawBorder(ribbonBtnArendehanteringArrow);
        }
        WebElement menu_element = ribbonBtnArendehanteringArrow;
        wait.until(ExpectedConditions.elementToBeClickable(ribbonBtnArendehanteringArrow)).click();

        switch(menu)
        {
            // case statements
            // values must be of same type of expression
            case RESENAR:
                menu_element = driver.findElement(By.xpath("//a[@id='contact']"));
                break;

            case FTJARENDE:
                menu_element = driver.findElement(By.xpath("//a[@id='fardtjanstarenden']"));
                break;

            case SJRARENDE:
                menu_element = driver.findElement(By.xpath("//a[@id='ftj_sjukresearende']"));
                break;

            case OVERKLAGANDEARENDE:
                menu_element = driver.findElement(By.xpath("//a[@id='ftj_overklagandearende']"));
                break;

            case RESESALDOJUSTERING:
                menu_element = driver.findElement(By.xpath("//a[@id='ftj_justeringavresesaldo']"));
                break;

            default :
                // Statements
        }
        staleElement = true;
        while(staleElement){
            try{
                wait.until(ExpectedConditions.elementToBeClickable(menu_element));
                staleElement = false;

            } catch(StaleElementReferenceException e){
                logger.info("Stale element at SoFTPrepare #1 usage of ribbonBtnArendehanteringArrow");
                staleElement = true;
            }
        }
        if(VISUAL_DEBUG)
        {
            // fardtjanstarenden
            wait.until(ExpectedConditions.elementToBeClickable(menu_element));
            drawBorder(menu_element);
        }
        wait.until(ExpectedConditions.elementToBeClickable(menu_element)).click();


        return true;
    }

    // Sökfält för resenär
    //
    @FindBy(xpath = "//input[@id='crmGrid_findCriteria']")
    protected WebElement inputSearch;

    @Override
    public boolean ResenarPrepare(EnumTypes.ResenarMenuchoise menu, ArendeData arendeData) {
        return false;
    }

    @Override
    public void drawBorder(String xpath){
        WebElement element_node = driver.findElement(By.xpath(xpath));
        driver.executeJavaScript("arguments[0].style.border='3px solid red'", element_node);
    }

    @Override
    public void drawBorder(WebElement element_node){
        driver.executeJavaScript("arguments[0].style.border='3px solid red'", element_node);
    }

}
