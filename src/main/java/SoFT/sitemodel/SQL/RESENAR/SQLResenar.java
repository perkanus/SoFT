package SoFT.sitemodel.SQL.RESENAR;

import SoFT.data.DataStructures;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import se.soprasteria.automatedtesting.webdriver.api.base.BasePageObject;
import se.soprasteria.automatedtesting.webdriver.helpers.driver.AutomationDriver;

import java.util.List;

public class SQLResenar extends BasePageObject implements SQLResenarI_Interface {
    // TODO: Add Try/Catch returning false or true depending on test outcome. Creeatte meaningfull messages
    boolean VISUAL_DEBUG = true;
    WebDriverWait wait = new WebDriverWait(driver,20);

    public SQLResenar(AutomationDriver driver) {
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

    // NY(TT) Resenär knapp
    //
    @FindBy(xpath = "//*[@id='contact|NoRelationship|HomePageGrid|Mscrm.HomepageGrid.contact.NewRecord']/span/a")
    protected WebElement webElement_RESENAR_topButtons_NewResenar;

    // Spara och stäng knapp
    //
    @FindBy(xpath = "//*[@id='contact|NoRelationship|Form|Mscrm.Form.contact.SaveAndClose']/span/a")
    protected WebElement webElement_RESENAR_topButtons_SaveAndClose;

    // Spara knapp
    //
    @FindBy(xpath = "//*[@id='contact|NoRelationship|Form|Mscrm.Form.contact.Save']/span/a")
    protected WebElement webElement_RESENAR_topButtons_Save;

    // Fält för personnummer/reservnummer
    //
    @FindBy(xpath = "//input[@id='ftj_identitetsnummer_i']")
    protected WebElement inputIDNumber;

    // Etikett som används för att kontrollera att uppslag från PU lyckades
    //
    @FindBy(xpath = "//*[@id='Land_label']")
    protected WebElement landLabel;

    // Etikett för kundnummer
    //
    @FindBy(xpath = "//*[@id='Kundnummer_label']")
    protected WebElement kundNummerLabel;

    @Override
    public boolean AddResenar(DataStructures.Resenar resenar) {
        try
        {
            driver.switchTo().defaultContent();

            if(resenar.RESENAR_CREATED.equals("N") && !resenar.RESENAR_OWNER.equals("NONE")) {

                if (VISUAL_DEBUG) {
                    wait.until(ExpectedConditions.elementToBeClickable(webElement_RESENAR_topButtons_NewResenar));
                    drawBorder(webElement_RESENAR_topButtons_NewResenar);
                }
                wait.until(ExpectedConditions.elementToBeClickable(webElement_RESENAR_topButtons_NewResenar)).click();

                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame1"));

                if (VISUAL_DEBUG) {
                    wait.until(ExpectedConditions.elementToBeClickable(inputIDNumber));
                    drawBorder(inputIDNumber, "red", 1);
                }

                wait.until(ExpectedConditions.elementToBeClickable(inputIDNumber)).click();
                wait.until(ExpectedConditions.elementToBeClickable(inputIDNumber)).sendKeys(resenar.PK_RESENAR);
                sleep(1000);
                wait.until(ExpectedConditions.elementToBeClickable(inputIDNumber)).sendKeys(Keys.TAB);
                sleep(1000);
                /* TODO: Implement later. Not found in PU
                if (wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='crmNotifications']"))) != null) {
                    List<WebElement> notificationElements = driver.findElement(By.xpath("//*[@id='crmNotifications']")).findElements(By.className("Notification"));
                    if (notificationElements.size() > 0)
                        for (int i = 0; i < notificationElements.size(); i++) {
                            if (notificationElements.get(i).getText().contains("Identitetsnummer hittades inte i PU")) {
                                resenar.RESENAR_NOT_IN_PU = "T";
                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='closeButton']"))).click();

                                boolean foundAlert = false;
                                WebDriverWait wait = new WebDriverWait(driver, 0 );
                                try {
                                    wait.until(ExpectedConditions.alertIsPresent());
                                    foundAlert = true;
                                } catch (TimeoutException eTO) {
                                    foundAlert = false;
                                }
                            }
                        }
                }
                */
                if (VISUAL_DEBUG) {
                    wait.until(ExpectedConditions.elementToBeClickable(landLabel));
                    drawBorder(landLabel);
                }
                wait.until(ExpectedConditions.elementToBeClickable(landLabel));

                driver.switchTo().defaultContent();


                if (VISUAL_DEBUG) {
                    wait.until(ExpectedConditions.elementToBeClickable(webElement_RESENAR_topButtons_Save));
                    drawBorder(webElement_RESENAR_topButtons_Save, "red", 1);
                    wait.until(ExpectedConditions.elementToBeClickable(webElement_RESENAR_topButtons_SaveAndClose));
                    drawBorder(webElement_RESENAR_topButtons_SaveAndClose, "red", 1);
                }
                // TODO: Fix update better


                wait.until(ExpectedConditions.elementToBeClickable(webElement_RESENAR_topButtons_Save)).click();
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame1"));


                if (VISUAL_DEBUG) {
                    wait.until(ExpectedConditions.elementToBeClickable(kundNummerLabel));
                    drawBorder(kundNummerLabel, "red", 1);
                }
                wait.until((ExpectedCondition<Boolean>) driver -> kundNummerLabel.getText().length() == 12);
                //wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(kundNummerLabel,"--")));
                resenar.RESENAR_KUNDNUMMER = kundNummerLabel.getText().replace("'","");

                driver.switchTo().defaultContent();
                wait.until(ExpectedConditions.elementToBeClickable(webElement_RESENAR_topButtons_SaveAndClose)).click();
                resenar.RESENAR_CREATED = "Y";
                sleep(3000);
                return true;
            }
            else
            {
                logger.info("Person redan upplagd i SoFT!");
                return true;
            }
        }
        catch (Exception e)
        {
            return false;
        }
    }

    // Sökfält för resenär
    //
    @FindBy(xpath = "//input[@id='crmGrid_findCriteria']")
    protected WebElement inputSearch;


    @Override
    public boolean FindResenar(DataStructures.Resenar resenar) {
        // TODO: Fix so table row is selected instead.
        try
        {
            driver.switchTo().defaultContent();

            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt( "contentIFrame0"));

            if(VISUAL_DEBUG)
            {
                wait.until(ExpectedConditions.elementToBeClickable(inputSearch));
                drawBorder(inputSearch);
            }

            wait.until(ExpectedConditions.elementToBeClickable(inputSearch)).click();
            wait.until(ExpectedConditions.elementToBeClickable(inputSearch)).sendKeys(resenar.PK_RESENAR);
            wait.until(ExpectedConditions.elementToBeClickable(inputSearch)).sendKeys(Keys.ENTER);
            List<WebElement> custList = driver.findElements(By.xpath("//*[@id=\"gridBodyTable\"]/tbody/tr"));

            if(custList.size()==1)
            {
                resenar.RESENAR_CREATED = "Y";
                if(VISUAL_DEBUG)
                    drawBorder(custList.get(0));
                driver.executeJavaScript("arguments[0].click();", custList.get(0));

                if(VISUAL_DEBUG)
                {
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='crmGrid_clearCriteriaButton']")));
                    drawBorder("//a[@id='crmGrid_clearCriteriaButton']");
                }
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='crmGrid_clearCriteriaButton']"))).click();
                return true;
            }
            else
            {
                resenar.RESENAR_CREATED = "N";
                if(VISUAL_DEBUG)
                {
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='crmGrid_clearCriteriaButton']")));
                    drawBorder("//a[@id='crmGrid_clearCriteriaButton']");
                }
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='crmGrid_clearCriteriaButton']"))).click();
                return true;
            }
        }
        catch(Exception e)
        {
            return false;
        }

    }
}
