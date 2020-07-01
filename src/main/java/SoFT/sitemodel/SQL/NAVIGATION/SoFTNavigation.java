package SoFT.sitemodel.SQL.NAVIGATION;

import SoFT.SoFTTestNavigation;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import se.soprasteria.automatedtesting.webdriver.api.base.BasePageObject;
import se.soprasteria.automatedtesting.webdriver.helpers.driver.AutomationDriver;

public class SoFTNavigation extends BasePageObject implements SoFTNavigation_Interface {
    // TODO: Add Try/Catch returning false or true depending on test outcome. Create meaningful messages
    boolean VISUAL_DEBUG = true;
    WebDriverWait wait = new WebDriverWait(driver,20);

    public SoFTNavigation(AutomationDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageLoaded() {
        return false;
    }

    @Override
    public void drawBorder(String xpath){
        WebElement element_node = driver.findElement(By.xpath(xpath));
        driver.executeJavaScript("arguments[0].style.border='1px solid red'", element_node);
    }

    @Override
    public void drawBorder(WebElement element_node){
        driver.executeJavaScript("arguments[0].style.border='1px solid red'", element_node);
    }

    @Override
    public void drawBorder(String xpath, String color, int width) {
        WebElement element_node = driver.findElement(By.xpath(xpath));
        driver.executeJavaScript("arguments[0].style.border='"+ width + "px solid " + color + "'", element_node);
    }

    @Override
    public void drawBorder(WebElement element_node, String color, int width) {
        driver.executeJavaScript("arguments[0].style.border='"+ width + "px solid " + color + "'", element_node);
    }


    @Override
    public boolean TopNavigation(SoFTTestNavigation.TopNavigation topNavigation) {
        try
        {
            // select top
            driver.switchTo().defaultContent();

            switch (topNavigation)
            {
                case SOFT:
                    // Click on CRM Button
                    //
                    try {
                        if(VISUAL_DEBUG)
                        {
                            wait.until(ExpectedConditions.elementToBeClickable(top_button_CRM));
                            drawBorder(top_button_CRM, "red", 1);
                        }
                        wait.until(ExpectedConditions.elementToBeClickable(top_button_CRM)).click();
                    }
                    catch (Exception ex)
                    {
                        logger.error("Exception when clicking CRM button. Message: " + ex.getMessage());
                        return false;
                    }
                    break;
                case ARENDEHANTERING:
                    // Click on Ärendehantering Button
                    //
                    try {
                        boolean staleElement = true;
                        while(staleElement){
                            try{
                                wait.until(ExpectedConditions.elementToBeClickable(top_button_Arendehantering));
                                staleElement = false;

                            } catch(StaleElementReferenceException ex){
                                logger.error("StaleElementReferenceException when clicking Ärendehantering button. Message: " + ex.getMessage());
                                staleElement = true;
                            }
                        }
                        if(VISUAL_DEBUG)
                        {
                            wait.until(ExpectedConditions.elementToBeClickable(top_button_Arendehantering));
                            drawBorder(top_button_Arendehantering, "red", 1);
                        }
                        wait.until(ExpectedConditions.elementToBeClickable(top_button_Arendehantering)).click();
                    }
                    catch (Exception ex)
                    {
                        logger.error("Exception when clicking Ärendehantering button. Message: " + ex.getMessage());
                    }
                    break;
                case ARENDEHANTERINGARROW:
                    break;
                case INSTRUMENTPANELER:
                    ArrowClick();
                    break;
                case RESENARER:
                    ArrowClick();
                    // Click on Ärendehantering Button
                    //
                    try {
                        boolean staleElement = true;
                        while(staleElement){
                            try{
                                wait.until(ExpectedConditions.elementToBeClickable(top_button_Resenar));
                                staleElement = false;

                            } catch(StaleElementReferenceException ex){
                                logger.error("StaleElementReferenceException when clicking Resenär button. Message: " + ex.getMessage());
                                staleElement = true;
                            }
                        }
                        if(VISUAL_DEBUG)
                        {
                            wait.until(ExpectedConditions.elementToBeClickable(top_button_Resenar));
                            drawBorder(top_button_Resenar, "red", 1);
                        }
                        wait.until(ExpectedConditions.elementToBeClickable(top_button_Resenar)).click();
                    }
                    catch (Exception ex)
                    {
                        logger.error("Exception when clicking Resenär button. Message: " + ex.getMessage());
                        return false;
                    }
                    break;
                case FARDTJANSARENDEN:
                    ArrowClick();
                    try {
                        boolean staleElement = true;
                        while(staleElement){
                            try{
                                wait.until(ExpectedConditions.elementToBeClickable(top_button_Fardtjanstarenden));
                                staleElement = false;

                            } catch(StaleElementReferenceException ex){
                                logger.error("StaleElementReferenceException when clicking Färdtjänstärenden button. Message: " + ex.getMessage());
                                staleElement = true;
                            }
                        }
                        if(VISUAL_DEBUG)
                        {
                            wait.until(ExpectedConditions.elementToBeClickable(top_button_Fardtjanstarenden));
                            drawBorder(top_button_Fardtjanstarenden, "red", 1);
                        }
                        wait.until(ExpectedConditions.elementToBeClickable(top_button_Fardtjanstarenden)).click();
                    }
                    catch (Exception ex)
                    {
                        logger.error("Exception when clicking Färdtjänstärenden button. Message: " + ex.getMessage());
                        return false;
                    }
                    break;
                case SJUKRESEARENDEN:
                    ArrowClick();
                    try {
                        boolean staleElement = true;
                        while(staleElement){
                            try{
                                wait.until(ExpectedConditions.elementToBeClickable(top_button_Sjukresearenden));
                                staleElement = false;

                            } catch(StaleElementReferenceException ex){
                                logger.error("StaleElementReferenceException when clicking Sjukreseärenden button. Message: " + ex.getMessage());
                                staleElement = true;
                            }
                        }
                        if(VISUAL_DEBUG)
                        {
                            wait.until(ExpectedConditions.elementToBeClickable(top_button_Sjukresearenden));
                            drawBorder(top_button_Sjukresearenden, "red", 1);
                        }
                        wait.until(ExpectedConditions.elementToBeClickable(top_button_Sjukresearenden)).click();
                    }
                    catch (Exception ex)
                    {
                        logger.error("Exception when clicking Sjukreseärenden button. Message: " + ex.getMessage());
                        return false;
                    }
                    break;
                case OVERKLAGANDEARENDEN:
                    ArrowClick();
                    try {
                        boolean staleElement = true;
                        while(staleElement){
                            try{
                                wait.until(ExpectedConditions.elementToBeClickable(top_button_Overklagandearenden));
                                staleElement = false;

                            } catch(StaleElementReferenceException ex){
                                logger.error("StaleElementReferenceException when clicking Överklagandeärenden button. Message: " + ex.getMessage());
                                staleElement = true;
                            }
                        }
                        if(VISUAL_DEBUG)
                        {
                            wait.until(ExpectedConditions.elementToBeClickable(top_button_Overklagandearenden));
                            drawBorder(top_button_Overklagandearenden, "red", 1);
                        }
                        wait.until(ExpectedConditions.elementToBeClickable(top_button_Overklagandearenden)).click();
                    }
                    catch (Exception ex)
                    {
                        logger.error("Exception when clicking Överklagandeärenden button. Message: " + ex.getMessage());
                        return false;
                    }
                    break;
                default:
                    break;
            }
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    private boolean ArrowClick()
    {
        // Click on Ärendehantering arrow Button
        //
        try {
            if(VISUAL_DEBUG)
            {
                boolean staleElement = true;
                while(staleElement){
                    try{
                        wait.until(ExpectedConditions.elementToBeClickable(top_button_Arendehantering_arrow));
                        staleElement = false;

                    } catch(StaleElementReferenceException ex){
                        logger.error("StaleElementReferenceException when clicking Ärendehantering arrow button. Message: " + ex.getMessage());
                        staleElement = true;
                    }
                }
                drawBorder(top_button_Arendehantering_arrow, "red", 1);
            }
            wait.until(ExpectedConditions.elementToBeClickable(top_button_Arendehantering_arrow)).click();
        }
        catch (Exception ex)
        {
            logger.error("Exception when clicking Ärendehantering arrow button. Message: " + ex.getMessage());
            driver.switchTo().defaultContent();
            return false;
        }
        driver.switchTo().defaultContent();
        return true;
    }

    // APPENDIX 1
    // @FindBy

    // SOFT CRM ribbon knapp
    @FindBy(xpath = "//*[@id='TabnavTabLogoTextId']")
    protected WebElement top_button_CRM;

    // Ärendehantering ribbon knapp
    @FindBy(xpath = "//*[@id='TabCS-main']")
    protected WebElement top_button_Arendehantering;

    // Ärendehantering ribbon knapp
    @FindBy(xpath = "//*[@id='TabCS']")
    protected WebElement top_button_Arendehantering_arrow;

    // Resenär ribbon knapp
    @FindBy(xpath = "//a[@id='contact']")
    protected WebElement top_button_Resenar;

    // Färdtjänstärenden ribbon knapp
    @FindBy(xpath = "//a[@id='fardtjanstarenden']")
    protected WebElement top_button_Fardtjanstarenden;

    // Sjukreseärenden ribbon knapp
    @FindBy(xpath = "//a[@id='ftj_sjukresearende']")
    protected WebElement top_button_Sjukresearenden;

    // Sjukreseärenden ribbon knapp
    @FindBy(xpath = "//a[@id='ftj_overklagandearende']")
    protected WebElement top_button_Overklagandearenden;
}
