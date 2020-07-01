package SoFT.sitemodel.resenar;

import SoFT.data.ArendeData;
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

public class Resenar extends BasePageObject implements ResenarI {
    // TODO: Add Try/Catch returning false or true depending on test outcome. Creeatte meaningfull messages
    boolean VISUAL_DEBUG = true;
    WebDriverWait wait = new WebDriverWait(driver,20);

    public Resenar(AutomationDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageLoaded() {
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
    public boolean AddResenar(ArendeData arendeData) {
        driver.switchTo().defaultContent();

        if(arendeData.resenarSkapad.equals("N")) {

                if (VISUAL_DEBUG) {
                    wait.until(ExpectedConditions.elementToBeClickable(webElement_RESENAR_topButtons_NewResenar));
                    drawBorder(webElement_RESENAR_topButtons_NewResenar);
                }
                wait.until(ExpectedConditions.elementToBeClickable(webElement_RESENAR_topButtons_NewResenar)).click();

                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame1"));

                if (VISUAL_DEBUG) {
                    wait.until(ExpectedConditions.elementToBeClickable(inputIDNumber));
                    drawBorder(inputIDNumber);
                }

                wait.until(ExpectedConditions.elementToBeClickable(inputIDNumber)).click();
                wait.until(ExpectedConditions.elementToBeClickable(inputIDNumber)).sendKeys(arendeData.Personnummer.replace("'", ""));
                sleep(1000);
                wait.until(ExpectedConditions.elementToBeClickable(inputIDNumber)).sendKeys(Keys.TAB);
                sleep(1000);


                if (VISUAL_DEBUG) {
                    wait.until(ExpectedConditions.elementToBeClickable(landLabel));
                    drawBorder(landLabel);
                }
                wait.until(ExpectedConditions.elementToBeClickable(landLabel));

                driver.switchTo().defaultContent();


                if (VISUAL_DEBUG) {
                    wait.until(ExpectedConditions.elementToBeClickable(webElement_RESENAR_topButtons_Save));
                    drawBorder(webElement_RESENAR_topButtons_Save);
                    wait.until(ExpectedConditions.elementToBeClickable(webElement_RESENAR_topButtons_SaveAndClose));
                    drawBorder(webElement_RESENAR_topButtons_SaveAndClose);
                }
                // TODO: Fix update better


                wait.until(ExpectedConditions.elementToBeClickable(webElement_RESENAR_topButtons_Save)).click();
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame1"));


                if (VISUAL_DEBUG) {
                    wait.until(ExpectedConditions.elementToBeClickable(kundNummerLabel));
                    drawBorder(kundNummerLabel);
                }
            wait.until((ExpectedCondition<Boolean>) driver -> kundNummerLabel.getText().length() == 12);
                //wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(kundNummerLabel,"--")));
                arendeData.Kundnummer = kundNummerLabel.getText().replace("'","");

                driver.switchTo().defaultContent();
                wait.until(ExpectedConditions.elementToBeClickable(webElement_RESENAR_topButtons_SaveAndClose)).click();
                arendeData.resenarSkapad = "Y";
                sleep(3000);
                return true;
        }
        else
        {
            logger.info("Person redan upplagd i SoFT!");
            return true;
        }
    }

    // Sökfält för resenär
    //
    @FindBy(xpath = "//input[@id='crmGrid_findCriteria']")
    protected WebElement inputSearch;

    @Override
    public boolean VerifyResenar(ArendeData arendeData) {
        logger.info("Verifying that Resenär with personnummer:" + arendeData.Personnummer + " exists in SoFT.");
        driver.switchTo().defaultContent();

        try {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt( "contentIFrame0"));
            if(VISUAL_DEBUG)
            {
                wait.until(ExpectedConditions.elementToBeClickable(inputSearch));
                drawBorder(inputSearch);
            }

            wait.until(ExpectedConditions.elementToBeClickable(inputSearch)).click();
            wait.until(ExpectedConditions.elementToBeClickable(inputSearch)).sendKeys(arendeData.Personnummer.replace("'",""));
            wait.until(ExpectedConditions.elementToBeClickable(inputSearch)).sendKeys(Keys.ENTER);
            List<WebElement> custList = driver.findElements(By.xpath("//*[@id=\"gridBodyTable\"]/tbody/tr"));

            if(custList.size()==1)
            {
                // TODO: add //*[@id="Kundnummer_label"]
                //logger.info(custList.size() + " SIZE ############################");

                arendeData.resenarSkapad = "Y";
                if(VISUAL_DEBUG)
                    drawBorder(custList.get(0));
                driver.executeJavaScript("arguments[0].click();", custList.get(0));

                if(VISUAL_DEBUG)
                {
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='crmGrid_clearCriteriaButton']")));
                    drawBorder("//a[@id='crmGrid_clearCriteriaButton']");
                }
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='crmGrid_clearCriteriaButton']"))).click();
            }
            else
            {
                // Kunde inte hitta. Uppdatera objekt.
                arendeData.resenarSkapad = "N";
                if(VISUAL_DEBUG)
                {
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='crmGrid_clearCriteriaButton']")));
                    drawBorder("//a[@id='crmGrid_clearCriteriaButton']");
                }
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='crmGrid_clearCriteriaButton']"))).click();
            }
            return true;
        }
        catch( org.openqa.selenium.TimeoutException ex)
        {
            return false;
        }
    }

    @Override
    public boolean FindResenar(ArendeData arendeData) {
        driver.switchTo().defaultContent();
        //if(resenar.flagIsAdded != null)

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt( "contentIFrame0"));

        if(VISUAL_DEBUG)
        {
            wait.until(ExpectedConditions.elementToBeClickable(inputSearch));
            drawBorder(inputSearch);
        }

        wait.until(ExpectedConditions.elementToBeClickable(inputSearch)).click();
        wait.until(ExpectedConditions.elementToBeClickable(inputSearch)).sendKeys(arendeData.Personnummer.replace("'",""));
        wait.until(ExpectedConditions.elementToBeClickable(inputSearch)).sendKeys(Keys.ENTER);
        List<WebElement> custList = driver.findElements(By.xpath("//*[@id=\"gridBodyTable\"]/tbody/tr"));

        if(custList.size()==1)
        {
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
            if(VISUAL_DEBUG)
            {
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='crmGrid_clearCriteriaButton']")));
                drawBorder("//a[@id='crmGrid_clearCriteriaButton']");
            }
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='crmGrid_clearCriteriaButton']"))).click();
             return false;
        }
    }
}
