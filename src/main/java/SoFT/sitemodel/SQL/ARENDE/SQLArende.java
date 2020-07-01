package SoFT.sitemodel.SQL.ARENDE;

import SoFT.data.DataStructures;
import SoFT.data.EnumTypes;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.*;
import se.soprasteria.automatedtesting.webdriver.api.base.BasePageObject;
import se.soprasteria.automatedtesting.webdriver.helpers.driver.AutomationDriver;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SuppressWarnings("ALL")
public class SQLArende extends BasePageObject implements SQLArende_Interface {
    boolean VISUAL_DEBUG = true;
    WebDriverWait wait = new WebDriverWait(driver,20);

    public SQLArende(AutomationDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageLoaded() {
        return false;
    }

    // SOFT CRM ribbon knapp
    //
    @FindBy(xpath = "//*[@id='TabnavTabLogoTextId']")
    protected WebElement btnSoftCRM;

    // Sökfält för resenär
    //
    @FindBy(xpath = "//input[@id='crmGrid_findCriteria']")
    protected WebElement inputFindResenar;

    // Ärende
    //
    @FindBy(xpath = "//*[@id='tab3']")
    protected WebElement tabArende;


    Set<String> windowHandles;
    /*
    Sparar huvudfönstret för att kunna byta mellan den och det nya fönster som kommer upp
    */
    String parentWindowHandle = driver.getWindowHandle();

    /*
    Skapar tom variabel för Ärende som skapas när man valt en Resenär
    */
    String childWindowHandle = "";

    /*
    Skapar tom variabel för Nytt Document fönstret
    */
    String documentWindowHandle = "";

    /*
    Skapar tom variabel för Ny tilldelning fönstret
    */
    String tilldelningWindowHandle = "";

    @Override
    public  boolean CreateFardtjanstArende(DataStructures.Resenar resenar) throws SQLException, ClassNotFoundException {


        String pk_arende = null;
        parentWindowHandle = driver.getWindowHandle();

        driver.switchTo().defaultContent();
        try {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt( "contentIFrame0"));
            boolean staleElement = true;
            while(staleElement){
                try{
                    wait.until(ExpectedConditions.elementToBeClickable(inputFindResenar));
                    staleElement = false;

                } catch(WebDriverException e){
                    staleElement = true;
                }
            }
            if(VISUAL_DEBUG)
            {
                wait.until(ExpectedConditions.elementToBeClickable(inputFindResenar));
                drawBorder(inputFindResenar);
            }

            // Markera sökfältet för att hitta Resenär
            //
            wait.until(ExpectedConditions.elementToBeClickable(inputFindResenar)).click();

            // Skicka in personnummer till sökfältet men rensa först
            //
            wait.until(ExpectedConditions.elementToBeClickable(inputFindResenar)).clear();
            wait.until(ExpectedConditions.elementToBeClickable(inputFindResenar)).sendKeys(resenar.PK_RESENAR);
            // TODO: Remove static sleep
            sleep(1000);

            // Skicka in retur till sökfältet för att sökning ska ske
            //
            wait.until(ExpectedConditions.elementToBeClickable(inputFindResenar)).sendKeys(Keys.ENTER);
            // TODO: Remove static sleep
            sleep(1000);

        }
        catch( TimeoutException ex)
        {

        }

        // Sök upp alla rader i tabellen för att kunna välja rätt resenär.
        //
        List<WebElement> custList = driver.findElements(By.xpath("//*[@id=\"gridBodyTable\"]/tbody/tr"));

        // Om det finns resultat i tabellen, dvs om resenär hittades
        //
        if(custList.size()!=0)
        {
            if(VISUAL_DEBUG)
                drawBorder(custList.get(0));

            // Det bör bara komma upp en enda rad men för säkerhets skull välj första raden
            //
            driver.executeJavaScript("arguments[0].click();", custList.get(0));

            // Välj alla kolumner i raden
            //
            List<WebElement> columnsInLoop = custList.get(0).findElements(By.tagName("td"));

            // Om personnummret som står på raden stämmer med den som kom som parameter i funktionen
            //
            if(resenar.PK_RESENAR.equals(columnsInLoop.get(2).getText())) {
                // öppna resenär
                //
                columnsInLoop.get(1).findElement(By.tagName("a")).click();
                driver.switchTo().defaultContent();


                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame1"));

                // Kollapsar Ärende
                //
                if (VISUAL_DEBUG) {
                    wait.until(ExpectedConditions.elementToBeClickable(tabArende));
                    drawBorder(tabArende);
                }

                wait.until(ExpectedConditions.elementToBeClickable(tabArende)).findElements(By.tagName("a")).get(0).click();

                if (VISUAL_DEBUG) {
                    // Markera Lägg till färdtjänstärende knappen
                    //

                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='fardtjanstarenden_contextualButtonsContainer']")));
                    drawBorder("//div[@id='fardtjanstarenden_contextualButtonsContainer']");
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='fardtjanstarenden_contextualButtonsContainer']")));
                    WebElement elem = driver.findElement(By.xpath("//div[@id='fardtjanstarenden_contextualButtonsContainer']"));
                    drawBorder(elem);
                    drawBorder(elem.findElements(By.tagName("a")).get(0));
                }

                boolean staleElement = true;
                while(staleElement){
                    try{
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='fardtjanstarenden_contextualButtonsContainer']"))).findElements(By.tagName("a")).get(0).click();
                        staleElement = false;

                    } catch(StaleElementReferenceException e){
                        staleElement = true;
                    }
                }
                //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='fardtjanstarenden_contextualButtonsContainer']"))).findElements(By.tagName("a")).get(0).click();

                /*
                Byter till föräldrarfönstret för att sedan spara alla fönster i en variabel för att byta mellan
                */
                windowHandles = driver.getWindowHandles();

                for (String handle : windowHandles) {
                    if (!handle.equals(parentWindowHandle)) {
                        childWindowHandle = handle;
                    }
                }

                 /*
                Vänta på att formuläret laddas in i nya  fönstret. Byter
                */
                driver.switchTo().window(childWindowHandle);

                // TODO: STALE ELEMENT

                staleElement = true;
                while(staleElement){
                    try{
                        wait.until(ExpectedConditions.elementToBeClickable(btnSoftCRM));
                        staleElement = false;

                    } catch(StaleElementReferenceException e){
                        logger.info("STALE ELEMENT: btnSoftCRM");
                        staleElement = true;
                    }
                }

                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame0"));


                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_sattmigsomhandlaggare']/div[1]"))).click();
                //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@id='ftj_arendetypid_lookupValue']"))).click();
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='ftj_arendetypid']"))).click();

                // TODO: Fixa snyggt
                try {
                    String s1 = "Nytt tillstånd";
                    byte[] bytes = s1.getBytes("UTF-8"); // Charset to encode into
                    String s2 = new String(bytes, "UTF-8");
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='ftj_arendetypid_ledit']"))).sendKeys(s2);
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='ftj_arendetypid_ledit']"))).sendKeys(Keys.TAB);
                }
                catch (UnsupportedEncodingException e) {
                }



                driver.switchTo().defaultContent();
                if (VISUAL_DEBUG) {
                    //
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_fardtjanstarende|NoRelationship|Form|Mscrm.Form.ftj_fardtjanstarende.SaveAndClose']/span/a")));
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_fardtjanstarende|NoRelationship|Form|Mscrm.Form.ftj_fardtjanstarende.Save']/span/a")));


                    drawBorder("//*[@id='ftj_fardtjanstarende|NoRelationship|Form|Mscrm.Form.ftj_fardtjanstarende.SaveAndClose']/span/a");
                    drawBorder("//*[@id='ftj_fardtjanstarende|NoRelationship|Form|Mscrm.Form.ftj_fardtjanstarende.Save']/span/a");

                }
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_fardtjanstarende|NoRelationship|Form|Mscrm.Form.ftj_fardtjanstarende.Save']/span/a"))).click();
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame0"));
                wait.until((ExpectedCondition<Boolean>) driver -> driver.findElement(By.xpath("//*[@id='Ärendenummer_label']")).getText().length() == 8);


                pk_arende = driver.findElement(By.xpath("//*[@id='Ärendenummer_label']")).getText();
                resenar.RESENAR_CREATE_FTJARENDE = "N";
                // TODO: Handle notifications in main arende window. Fix static sleep. Perhaps
                //
                sleep(3000);
                if (wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='crmNotifications']"))) != null) {
                    List<WebElement> notificationElements = driver.findElement(By.xpath("//*[@id='crmNotifications']")).findElements(By.className("Notification"));
                    if (notificationElements.size() > 0) {
                        for (int i = 0; i < notificationElements.size(); i++) {
                            if (notificationElements.get(i).getText().contains("Pågående ändring: Tryck på spara för att verkställa ändringen.")) {
                                driver.switchTo().defaultContent();
                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_fardtjanstarende|NoRelationship|Form|Mscrm.Form.ftj_fardtjanstarende.Save']/span/a"))).click();
                                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("InlineDialog_Iframe"));
                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='butBegin']"))).click();
                                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame0"));
                            }
                        }
                    }
                    else
                    {
                    }
                }
            }
            else {
            }
        }
        else
        {

        }
        // TODO: If ARENDE_BESLUT WILL CHANGE HERE IS THE place to Do it.
        resenar.AddArende(pk_arende, resenar.PK_RESENAR, "", "FTJ", "NONE", "Beviljad", "NONE" );
        return true;
    }

    @Override
    public boolean CreateFardtjansArendeDocument(DataStructures.Resenar resenar, EnumTypes.DocumentTypes documenttype) {
        // TODO: Fix variables and add tracing
        resenar.arendeList.get(0).ARENDE_FOTO = "Foto_" + new Timestamp(System.currentTimeMillis()).toString();

        logger.info("AddDocument Begin");

        switch (documenttype)
        {
            case FOTO:
                boolean staleElement = true;
                while(staleElement){
                    try{
                        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("WebResource_FileDownload"));
                        logger.info("WebResource_FileDownload found and selected.");
                        staleElement = false;

                    } catch(StaleElementReferenceException e){
                        staleElement = true;
                    }
                }

                if (VISUAL_DEBUG) {
                    // Markera ny dokument knappen
                    //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='newDokument']")));
                    //drawBorder("//div[@class='newDokument']");
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"app\"]/div/div[1]")));
                    drawBorder("//*[@id=\"app\"]/div/div[1]");
                    //*[@id="app"]/div/div[2]
                    //*[@id="app"]/div/div[1]
                }
                /*
                // Add this to make certain that add foreskrift loads correctly before moving forward to add document
                //
                //*[@id="tilldelningsuppgifter_addImageButton"]
                if (VISUAL_DEBUG) {
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id=\"foreskriftsuppgifter_addImageButton\"]")));
                    drawBorder("//a[@id=\"foreskriftsuppgifter_addImageButton\"]");
                }
                */
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='newDokument']"))).click();

                /*
                Byter till föräldrarfönstret för att sedan spara alla fönster i en variabel för att byta mellan
                */
                windowHandles = driver.getWindowHandles();

                for (String handle : windowHandles) {
                    if (!handle.equals(parentWindowHandle) || !handle.equals(childWindowHandle)) {
                        documentWindowHandle = handle;
                    }
                }

                /*
                Vänta på att formuläret laddas in i nya  fönstret. Byter
                */
                driver.switchTo().window(documentWindowHandle);

                staleElement = true;
                while(staleElement){
                    try{
                        wait.until(ExpectedConditions.elementToBeClickable(btnSoftCRM));
                        staleElement = false;

                    } catch(StaleElementReferenceException e){
                        staleElement = true;
                    }
                }

                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame0"));
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='ftj_dokumenttypid_ledit']"))).click();
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='ftj_dokumenttypid_ledit']"))).sendKeys("Foto");
                //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='ftj_dokumenttypid_ledit']"))).sendKeys(Keys.TAB);

                //*[@id="ftj_namn"]
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='ftj_namn']/div"))).click();
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='ftj_namn_i']"))).sendKeys(resenar.arendeList.get(0).ARENDE_FOTO);
                //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='ftj_namn_i']"))).sendKeys(Keys.TAB);
                driver.switchTo().defaultContent();
                if (VISUAL_DEBUG) {
                    //
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_dokument|NoRelationship|Form|Mscrm.Form.ftj_dokument.Save']/span/a")));
                    drawBorder("//*[@id='ftj_dokument|NoRelationship|Form|Mscrm.Form.ftj_dokument.Save']/span/a");
                    //ftj_dokument|NoRelationship|Form|Mscrm.SaveAndClosePrimary
                }
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_dokument|NoRelationship|Form|Mscrm.Form.ftj_dokument.Save']/span/a"))).click();
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame0"));
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_godkantfotografi']/div[1]"))).click();

                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("WebResource_File_Upload"));
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/div[1]/label/strong"))).click();


                sleep(2000); // Väntar på att fildialogen ska laddas så autoit scriptet ska kunna köras
                try {
                    ProcessBuilder pb = new ProcessBuilder("C:\\Program Files (x86)\\AutoIt3\\AutoIt3.exe", "C:\\work\\AddPicture.au3");
                    Process p = pb.start();
                }
                catch (IOException e) {
                    System.out.println("exception happened - here's what I know: ");
                    e.printStackTrace();
                    System.exit(-1);
                    return false;
                }

                staleElement = true;
                while(staleElement){
                    try{
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='submitBtn']"))).click();
                        staleElement = false;

                    } catch(StaleElementReferenceException e){
                        staleElement = true;
                    }
                }

                driver.switchTo().defaultContent();

                staleElement = true;
                while(staleElement){
                    try{
                        wait.until(ExpectedConditions.elementToBeClickable(btnSoftCRM));
                        staleElement = false;

                    } catch(StaleElementReferenceException e){
                        staleElement = true;
                    }
                }

                staleElement = true;
                while(staleElement){
                    try{
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_dokument|NoRelationship|Form|Mscrm.Form.ftj_dokument.SaveAndClose']/span/a"))).click();
                        staleElement = false;

                    } catch(StaleElementReferenceException e){
                        staleElement = true;
                    }
                    catch (ElementNotInteractableException e1) {
                        staleElement = true;

                    }
                }

                return true;
            case PDF:
                staleElement = true;
                while(staleElement){
                    try{
                        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("WebResource_FileDownload"));
                        logger.info("WebResource_FileDownload found and selected.");
                        staleElement = false;

                    } catch(StaleElementReferenceException e){
                        staleElement = true;
                    }
                }

                if (VISUAL_DEBUG) {
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"app\"]/div/div[1]")));
                    drawBorder("//*[@id=\"app\"]/div/div[1]");

                }

                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='newDokument']"))).click();

                /*
                Byter till föräldrarfönstret för att sedan spara alla fönster i en variabel för att byta mellan
                */
                windowHandles = driver.getWindowHandles();

                for (String handle : windowHandles) {
                    if (!handle.equals(parentWindowHandle) || !handle.equals(childWindowHandle)) {
                        documentWindowHandle = handle;
                    }
                }

                /*
                Vänta på att formuläret laddas in i nya  fönstret. Byter
                */
                driver.switchTo().window(documentWindowHandle);

                staleElement = true;
                while(staleElement){
                    try{
                        wait.until(ExpectedConditions.elementToBeClickable(btnSoftCRM));
                        staleElement = false;

                    } catch(StaleElementReferenceException e){
                        staleElement = true;
                    }
                }

                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame0"));
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='ftj_dokumenttypid_ledit']"))).click();
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='ftj_dokumenttypid_ledit']"))).sendKeys("Ansökan");
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='ftj_namn']/div"))).click();
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='ftj_namn_i']"))).sendKeys("Ansökan");

                driver.switchTo().defaultContent();
                if (VISUAL_DEBUG) {
                    //
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_dokument|NoRelationship|Form|Mscrm.Form.ftj_dokument.Save']/span/a")));
                    drawBorder("//*[@id='ftj_dokument|NoRelationship|Form|Mscrm.Form.ftj_dokument.Save']/span/a");
                    //ftj_dokument|NoRelationship|Form|Mscrm.SaveAndClosePrimary
                }
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_dokument|NoRelationship|Form|Mscrm.Form.ftj_dokument.Save']/span/a"))).click();
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame0"));

                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("WebResource_File_Upload"));
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/div[1]/label/strong"))).click();


                sleep(2000); // Väntar på att fildialogen ska laddas så autoit scriptet ska kunna köras
                try {
                    ProcessBuilder pb = new ProcessBuilder("C:\\Program Files (x86)\\AutoIt3\\AutoIt3.exe", "C:\\work\\AddPDF.au3");
                    Process p = pb.start();
                }
                catch (IOException e) {
                    System.out.println("exception happened - here's what I know: ");
                    e.printStackTrace();
                    System.exit(-1);
                    return false;
                }

                staleElement = true;
                while(staleElement){
                    try{
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='submitBtn']"))).click();
                        staleElement = false;

                    } catch(StaleElementReferenceException e){
                        staleElement = true;
                    }
                }

                driver.switchTo().defaultContent();

                staleElement = true;
                while(staleElement){
                    try{
                        wait.until(ExpectedConditions.elementToBeClickable(btnSoftCRM));
                        staleElement = false;

                    } catch(StaleElementReferenceException e){
                        staleElement = true;
                    }
                }

                staleElement = true;
                while(staleElement){
                    try{
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_dokument|NoRelationship|Form|Mscrm.Form.ftj_dokument.SaveAndClose']/span/a"))).click();
                        staleElement = false;

                    } catch(StaleElementReferenceException e){
                        staleElement = true;
                    }
                    catch (ElementNotInteractableException e1) {
                        staleElement = true;

                    }
                }

                return true;
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean CreateFardtjanstForeskrift(DataStructures.Resenar resenar) {
        int intSelection;

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = format1.format(today);

        driver.switchTo().window( childWindowHandle );
        driver.switchTo().defaultContent();

        // TODO: Don't forget this in every place btnSoftCRM appears to avoid stale element exception
        boolean staleElement = true;
        while(staleElement){
            try{
                wait.until(ExpectedConditions.elementToBeClickable(btnSoftCRM));
                staleElement = false;

            } catch(StaleElementReferenceException e){
                staleElement = true;
            }
        }

        if (VISUAL_DEBUG) {
            drawBorder(btnSoftCRM);
        }
        staleElement = true;
        while(staleElement){
            try{
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame0"));
                logger.info("contentIFrame0 found and selected.");
                staleElement = false;

            } catch(StaleElementReferenceException e){
                staleElement = true;
            }
        }

        // TODO: Add föreskrift
        if (VISUAL_DEBUG) {
            //*[@id="foreskriftsuppgifter_addImageButton"]
            //*[@id="foreskriftsuppgifter_contextualButtonsContainer"]/div
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"foreskriftsuppgifter_contextualButtonsContainer\"]/div")));
            drawBorder("//*[@id=\"foreskriftsuppgifter_contextualButtonsContainer\"]/div");
        }
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"foreskriftsuppgifter_contextualButtonsContainer\"]/div"))).click();

        windowHandles = driver.getWindowHandles();

        for (String handle : windowHandles) {
            if (!handle.equals(parentWindowHandle) || !handle.equals(childWindowHandle)) {
                tilldelningWindowHandle = handle;
            }
        }
        driver.switchTo().window(tilldelningWindowHandle);

        // TODO: Don't forget this in every place btnSoftCRM appears to avoid stale element exception
        staleElement = true;
        while(staleElement){
            try{
                wait.until(ExpectedConditions.elementToBeClickable(btnSoftCRM));
                staleElement = false;

            } catch(StaleElementReferenceException e){
                staleElement = true;
            }
        }

        if (VISUAL_DEBUG) {
            drawBorder(btnSoftCRM);
        }

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame0"));



        if (VISUAL_DEBUG) {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_giltigtfran_iDateInput']")));
            drawBorder("//*[@id='ftj_giltigtfran_iDateInput']");
        }

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_giltigtfran_iDateInput']"))).sendKeys(date1.toString());

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='ftj_fardsattid']/div"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_fardsattid_ledit']"))).sendKeys(resenar.arendeList.get(0).arendeForeskrift.FORESKRIFT_FARDSATT);
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_fardsattid_ledit']"))).sendKeys(Keys.TAB);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@id='ftj_antalledsagare_d']/div"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_antalledsagare_i']"))).sendKeys(resenar.arendeList.get(0).arendeForeskrift.FORESKRIFT_ANTAL_LEDSAGARE);
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_antalledsagare_i']"))).sendKeys(Keys.TAB);


        // Elmoped
        //
        intSelection = (resenar.arendeList.get(0).arendeForeskrift.FORESKRIFT_ELMOPED.equals("Ja")) ? 2 : (resenar.arendeList.get(0).arendeForeskrift.FORESKRIFT_ELMOPED.equals("Nej")) ? 3 : 1;
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='ftj_elmoped']/div"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='ftj_elmoped_i']/option[" + intSelection + "]"))).click();
        logger.info("FORESKRIFT_ELMOPED: " + intSelection);
        // Ensamåkning
        //
        intSelection = (resenar.arendeList.get(0).arendeForeskrift.FORESKRIFT_ENSAMAKNING.equals("Ja")) ? 2 : (resenar.arendeList.get(0).arendeForeskrift.FORESKRIFT_ENSAMAKNING.equals("Nej")) ? 3 : 1;
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='ftj_ensamakning']/div"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='ftj_ensamakning_i']/option[" + intSelection + "]"))).click();
        logger.info("FORESKRIFT_ENSAMAKNING: " + intSelection);
        // TODO: Framsätesplacering
        //

        // Allergianpassatfordon
        //
        intSelection = (resenar.arendeList.get(0).arendeForeskrift.FORESKRIFT_ALLERGIANPASSAT.equals("Ja")) ? 2 : (resenar.arendeList.get(0).arendeForeskrift.FORESKRIFT_ALLERGIANPASSAT.equals("Nej")) ? 3 : 1;
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='ftj_allergianpassatfordon']/div"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='ftj_allergianpassatfordon_i']/option[" + intSelection + "]"))).click();
        logger.info("FORESKRIFT_ALLERGIANPASSAT: " + intSelection);
        // Ledarservicehund
        //
        intSelection = (resenar.arendeList.get(0).arendeForeskrift.FORESKRIFT_HUND.equals("Ja")) ? 2 : (resenar.arendeList.get(0).arendeForeskrift.FORESKRIFT_HUND.equals("Nej")) ? 3 : 1;
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_ledarserviceellerassistanshund']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='ftj_ledarserviceellerassistanshund_i']/option[" + intSelection + "]"))).click();
        logger.info("FORESKRIFT_ALLERGIANPASSAT: " + intSelection);
        // Länsdispens
        //
        intSelection = (resenar.arendeList.get(0).arendeForeskrift.FORESKRIFT_LANSGRANSDISPENS.equals("Ja")) ? 2 : (resenar.arendeList.get(0).arendeForeskrift.FORESKRIFT_LANSGRANSDISPENS.equals("Nej")) ? 3 : 1;
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='ftj_lansgransdispens']/div"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='ftj_lansgransdispens_i']/option[" + intSelection + "]"))).click();
        logger.info("FORESKRIFT_LANSGRANSDISPENS: " + intSelection);
        // Båttaxi
        //
        intSelection = (resenar.arendeList.get(0).arendeForeskrift.FORESKRIFT_BATTAXI.equals("Ja")) ? 2 : (resenar.arendeList.get(0).arendeForeskrift.FORESKRIFT_BATTAXI.equals("Nej")) ? 3 : 1;
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='ftj_battaxi']/div"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='ftj_battaxi_i']/option[" + intSelection + "]"))).click();
        logger.info("FORESKRIFT_BATTAXI: " + intSelection);

        if (resenar.arendeList.get(0).arendeForeskrift.FORESKRIFT_BATTAXI.equals("Ja"))
        {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='ftj_franbrygga']/div"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_franbrygga_i']"))).sendKeys(resenar.arendeList.get(0).arendeForeskrift.FORESKRIFT_BRYGGA_1);

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='ftj_tillbrygga']/div"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_tillbrygga_i']"))).sendKeys(resenar.arendeList.get(0).arendeForeskrift.FORESKRIFT_BRYGGA_2);

        }

        driver.switchTo().defaultContent();
        if (VISUAL_DEBUG) {
            //
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_foreskriftsuppgifter|NoRelationship|Form|Mscrm.Form.ftj_foreskriftsuppgifter.SaveAndClose']/span/a")));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_foreskriftsuppgifter|NoRelationship|Form|Mscrm.Form.ftj_foreskriftsuppgifter.Save']/span/a")));


            drawBorder("//*[@id='ftj_foreskriftsuppgifter|NoRelationship|Form|Mscrm.Form.ftj_foreskriftsuppgifter.SaveAndClose']/span/a");
            drawBorder("//*[@id='ftj_foreskriftsuppgifter|NoRelationship|Form|Mscrm.Form.ftj_foreskriftsuppgifter.Save']/span/a");

        }
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_foreskriftsuppgifter|NoRelationship|Form|Mscrm.Form.ftj_foreskriftsuppgifter.SaveAndClose']/span/a"))).click();

        return true;
    }

    @Override
    public boolean ModifyFardtjandsArende(DataStructures.Resenar resenar) {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = format1.format(today);

        boolean staleElement = true;

        driver.switchTo().window( childWindowHandle );
        driver.switchTo().defaultContent();
        //driver.navigate().refresh();

        staleElement = true;
        while(staleElement){
            try{
                wait.until(ExpectedConditions.elementToBeClickable(btnSoftCRM));
                staleElement = false;

            } catch(StaleElementReferenceException e){
                logger.info("Stale element at AddForeskrift #2 usage of btnSoftCRM");
                staleElement = true;
            }
        }


        if (VISUAL_DEBUG) {
            drawBorder(btnSoftCRM);
        }
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt( "contentIFrame0"));
        staleElement = true;
        while(staleElement){
            try{
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("WebResource_FileDownload"));
                logger.info("WebResource_FileDownload found and selected.");
                staleElement = false;

            } catch(StaleElementReferenceException e){
                staleElement = true;
            }
        }


        if (VISUAL_DEBUG) {
            // markera refresh
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='app']/div/div[2]")));
            drawBorder("//*[@id='app']/div/div[2]");
        }

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='app']/div/div[2]"))).click();

        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame0"));

        // Datum Giltig från
        //
        if (VISUAL_DEBUG) {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='ftj_tillstandgiltigtfran']")));
            //*[@id="ftj_tillstandgiltigtfran"]
            drawBorder("//div[@id='ftj_tillstandgiltigtfran']");
        }

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='ftj_tillstandgiltigtfran']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_tillstandgiltigtfran_iDateInput']"))).sendKeys(date1.toString());
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_tillstandgiltigtfran_iDateInput']"))).sendKeys(Keys.TAB);

        // Välj Foto
        //
        if (VISUAL_DEBUG) {

            //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='ftj_fotodokumentid']/div")));
            //drawBorder("//div[@id='ftj_fotodokumentid']/div");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"ftj_fotodokumentid\"]")));
            drawBorder("//*[@id=\"ftj_fotodokumentid\"]");
        }

        // TODO: Useful if element does not respond to Selenium Click()
        //
        WebElement ele = driver.findElement(By.xpath("//*[@id=\"ftj_fotodokumentid\"]"));
        driver.executeJavaScript("arguments[0].click();", ele);


//        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_fotodokumentid_ledit']"))).sendKeys(fotoTimestamp);
//        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_fotodokumentid_ledit']"))).sendKeys(Keys.TAB);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"ftj_fotodokumentid\"]"))).sendKeys("stringFoto");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"ftj_fotodokumentid\"]"))).sendKeys(Keys.TAB);

        // Beslut
        //
        if (VISUAL_DEBUG) {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='ftj_beslut']/div")));
            drawBorder("//div[@id='ftj_beslut']/div");
        }

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='ftj_beslut']/div"))).click();

        WebElement selectElement = driver.findElement(By.xpath("//select[@id='ftj_beslut_i']"));
        Select select = new Select(selectElement);
        List<WebElement> allOptions = select.getOptions();

        // TODO: Describe
        int intBeslut = 0;

        for (int j = 0; j < allOptions.size(); j++) {
            System.out.println("Value: " + allOptions.get(j).getAttribute("value") + " Title: " + allOptions.get(j).getAttribute("title"));
            if(allOptions.get(j).getText().equals(resenar.arendeList.get(0).ARENDE_BESLUT.toString()))
            {
                intBeslut = j+1;
                break;
            }
        }

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='ftj_beslut_i']/option[" + intBeslut + "]"))).click();


        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_fardtjanstarende|NoRelationship|Form|Mscrm.Form.ftj_fardtjanstarende.SaveAndClose']/span/a"))).click();
        driver.switchTo().window( parentWindowHandle );
        driver.switchTo().defaultContent();


        return true;
    }


    // Ärendehantering-pil ribbon knapp
    @FindBy(xpath = "//*[@id='TabCS']")
    protected WebElement ribbonBtnArendehanteringArrow;

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
        if (VISUAL_DEBUG)
        {
            drawBorder(btnSoftCRM);
        }

        while(staleElement){
            try{
                wait.until(ExpectedConditions.elementToBeClickable(ribbonBtnArendehanteringArrow));
                staleElement = false;

            } catch(StaleElementReferenceException e){
                logger.info("Stale element at SoFTPrepare #1 usage of ribbonBtnArendehanteringArrow");
                staleElement = true;
            }
        }
        if (VISUAL_DEBUG)
        {
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

        while(staleElement){
            try{
                if(VISUAL_DEBUG)
                {
                    // fardtjanstarenden
                    wait.until(ExpectedConditions.elementToBeClickable(menu_element));
                    drawBorder(menu_element);
                }
                wait.until(ExpectedConditions.elementToBeClickable(menu_element)).click();
                staleElement = false;

            } catch(StaleElementReferenceException e){
                logger.info("Stale element at SoFTPrepare #1 usage of ribbonBtnArendehanteringArrow");
                staleElement = true;
            }
        }
        return true;
    }

    @Override
    public boolean DecideFardtjanstArende(DataStructures.Resenar resenar) {
        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt( "contentIFrame0"));
        boolean staleElement = true;
        while(staleElement){
            try{
                wait.until(ExpectedConditions.elementToBeClickable(inputFindResenar));
                logger.debug("Found Resenärsök.");
                staleElement = false;

            } catch(WebDriverException e){
                staleElement = true;
            }
        }
        if(VISUAL_DEBUG)
        {
            wait.until(ExpectedConditions.elementToBeClickable(inputFindResenar));
            drawBorder(inputFindResenar);
        }

        // Markera sökfältet för att hitta Resenär
        //
        wait.until(ExpectedConditions.elementToBeClickable(inputFindResenar)).click();

        // Skicka in personnummer till sökfältet men rensa först
        //
        wait.until(ExpectedConditions.elementToBeClickable(inputFindResenar)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(inputFindResenar)).sendKeys(resenar.PK_RESENAR);
        // TODO: Remove static sleep
        sleep(1000);
        // Skicka in retur till sökfältet för att sökning ska ske
        //
        wait.until(ExpectedConditions.elementToBeClickable(inputFindResenar)).sendKeys(Keys.ENTER);
        // TODO: Remove static sleep
        sleep(1000);
        // Sök upp alla rader i tabellen för att kunna välja rätt resenär.
        //
        List<WebElement> custList = driver.findElements(By.xpath("//*[@id=\"gridBodyTable\"]/tbody/tr"));

        // Om det finns resultat i tabellen, dvs om resenär hittades
        // TODO: move out of visual debug
        if(custList.size()!=0) {
            if (VISUAL_DEBUG)
                drawBorder(custList.get(0));
            driver.executeJavaScript("arguments[0].click();", custList.get(0));
            // Välj alla kolumner i raden
            //
            List<WebElement> columnsInLoop = custList.get(0).findElements(By.tagName("td"));

            // Om personnummret som står på raden stämmer med den som kom som parameter i funktionen
            //
            if(resenar.PK_RESENAR.equals(columnsInLoop.get(2).getText())) {
                // öppna resenär
                //
                columnsInLoop.get(1).findElement(By.tagName("a")).click();
                driver.switchTo().defaultContent();
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame1"));

                SelectFromMenu(EnumTypes.ResenarMenuchoise.FARDTJANSARENDE);


                //wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame1"));
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("area_ftj_Contact_FardtjanstarendeFrame"));

                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='crmGrid_ftj_Contact_Fardtjanstarende_findCriteria']"))).sendKeys(resenar.arendeList.get(0).PK_ARENDE);
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='crmGrid_ftj_Contact_Fardtjanstarende_findCriteria']"))).sendKeys(Keys.ENTER);

                custList = driver.findElements(By.xpath("//*[@id=\"gridBodyTable\"]/tbody/tr"));

                // Om det finns resultat i tabellen, dvs om resenär hittades
                //
                if(custList.size()!=0)
                {
                    if(VISUAL_DEBUG)
                        drawBorder(custList.get(0));

                    // Det bör bara komma upp en enda rad men för säkerhets skull välj första raden
                    //
                    driver.executeJavaScript("arguments[0].click();", custList.get(0));

                    // Välj alla kolumner i raden
                    //
                    columnsInLoop = custList.get(0).findElements(By.tagName("td"));

                    // Om personnummret som står på raden stämmer med den som kom som parameter i funktionen
                    //
                    if(columnsInLoop.get(1).getText().equals("Färdtjänstärende - " + resenar.arendeList.get(0).PK_ARENDE)) {
                        // Öppna Ärende
                        //
                        columnsInLoop.get(1).findElement(By.tagName("a")).click();

                        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame1"));
                        if (VISUAL_DEBUG) {
                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"ftj_fotodokumentid\"]/div[1]")));
                            drawBorder("//*[@id=\"ftj_fotodokumentid\"]/div[1]");
                        }

                        // TODO: Useful if element does not respond to Selenium Click()
                        //
                        //sleep(3000000);

                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"ftj_fotodokumentid\"]/div[1]"))).click();
                        if (wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"ftj_fotodokumentid_ledit\"]"))).getText().equals(""))
                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"ftj_fotodokumentid_ledit\"]"))).sendKeys(resenar.arendeList.get(0).ARENDE_FOTO);
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"ftj_fotodokumentid_ledit\"]"))).sendKeys(Keys.TAB);
                        sleep(5000);


                        DecisionSelection(resenar);
                        resenar.arendeList.get(0).ARENDE_STATUS = "BEVLD";


                    }
                }
            }
        }
        else
        {
            // TODO: IF RESENAR NOT IN SOFT - IT SHOULD NEVER HAPPEN
        }

        return true;
    }

    @Override
    public boolean GetFTJTillstandId(DataStructures.Resenar resenar) throws SQLException, ClassNotFoundException {
        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt( "contentIFrame0"));

        boolean staleElement = true;
        while(staleElement){
            try{
                wait.until(ExpectedConditions.elementToBeClickable(inputFindResenar));
                staleElement = false;

            } catch(WebDriverException e){
                staleElement = true;
            }
        }

        staleElement= true;

        while(staleElement){
            try{
                wait.until(ExpectedConditions.elementToBeClickable(inputFindResenar));
                staleElement = false;

            } catch(StaleElementReferenceException e){
                logger.info("Stale element at SoFTPrepare #1 usage of ribbonBtnArendehanteringArrow");
                staleElement = true;
            }
        }

        if(VISUAL_DEBUG)
        {
            wait.until(ExpectedConditions.elementToBeClickable(inputFindResenar));
            drawBorder(inputFindResenar);
        }

        // Markera sökfältet för att hitta Resenär
        //
        wait.until(ExpectedConditions.elementToBeClickable(inputFindResenar)).click();
        // Skicka in personnummer till sökfältet men rensa först
        //
        wait.until(ExpectedConditions.elementToBeClickable(inputFindResenar)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(inputFindResenar)).sendKeys(resenar.PK_RESENAR);
        // TODO: Remove static sleep
        sleep(1000);
        // Skicka in retur till sökfältet för att sökning ska ske
        //
        wait.until(ExpectedConditions.elementToBeClickable(inputFindResenar)).sendKeys(Keys.ENTER);
        // TODO: Remove static sleep
        sleep(1000);
        // Sök upp alla rader i tabellen för att kunna välja rätt resenär.
        //
        List<WebElement> custList = driver.findElements(By.xpath("//*[@id=\"gridBodyTable\"]/tbody/tr"));


        // Om det finns resultat i tabellen, dvs om resenär hittades
        //
        if(custList.size()!=0) {
            if (VISUAL_DEBUG)
                drawBorder(custList.get(0));
            driver.executeJavaScript("arguments[0].click();", custList.get(0));
            // Välj alla kolumner i raden
            //
            List<WebElement> columnsInLoop = custList.get(0).findElements(By.tagName("td"));

            // Om personnummret som står på raden stämmer med den som kom som parameter i funktionen
            //
            if (resenar.PK_RESENAR.equals(columnsInLoop.get(2).getText())) {
                // öppna resenär
                //
                columnsInLoop.get(1).findElement(By.tagName("a")).click();
                driver.switchTo().defaultContent();
                sleep(1000);
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame1"));

                SelectFromMenu(EnumTypes.ResenarMenuchoise.TILLSTAND);
                // TODO STALE DOCUMENT ElementClickInterceptedException
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("area_ftj_Contact_TillstandFrame"));

                boolean interceptedElement= true;

                while(interceptedElement){
                    try{
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@id='gridBodyTable']/tbody/tr/td[2]/nobr/a"))).click();
                        interceptedElement = false;

                    } catch(ElementClickInterceptedException e){
                        logger.info("interceptedElement element at ");
                        interceptedElement = true;
                    }
                }

                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame1"));
                // TODO: CATCH
                resenar.arendeList.get(0).AddInfo(wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-for-id='ftj_idnummer_label']"))).getText());
                logger.info("CATCH FTJ TILL ID: " + wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-for-id='ftj_idnummer_label']"))).getText());
            }
        }
        return true;
    }

    @Override
    public boolean GetFTJFardbevisData(DataStructures.Resenar resenar) {
        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt( "contentIFrame0"));
        boolean staleElement = true;
        while(staleElement){
            try{
                wait.until(ExpectedConditions.elementToBeClickable(inputFindResenar));
                staleElement = false;

            } catch(WebDriverException e){
                staleElement = true;
            }
        }

        if(VISUAL_DEBUG)
        {
            wait.until(ExpectedConditions.elementToBeClickable(inputFindResenar));
            drawBorder(inputFindResenar);
        }

        // Markera sökfältet för att hitta Resenär
        //
        wait.until(ExpectedConditions.elementToBeClickable(inputFindResenar)).click();
        // Skicka in personnummer till sökfältet men rensa först
        //
        wait.until(ExpectedConditions.elementToBeClickable(inputFindResenar)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(inputFindResenar)).sendKeys(resenar.PK_RESENAR);
        // TODO: Remove static sleep
        sleep(1000);
        // Skicka in retur till sökfältet för att sökning ska ske
        //
        wait.until(ExpectedConditions.elementToBeClickable(inputFindResenar)).sendKeys(Keys.ENTER);
        // TODO: Remove static sleep
        sleep(1000);
        // Sök upp alla rader i tabellen för att kunna välja rätt resenär.
        //
        List<WebElement> custList = driver.findElements(By.xpath("//*[@id=\"gridBodyTable\"]/tbody/tr"));

        // Om det finns resultat i tabellen, dvs om resenär hittades
        //
        if(custList.size()!=0) {
            if (VISUAL_DEBUG)
                drawBorder(custList.get(0));
            driver.executeJavaScript("arguments[0].click();", custList.get(0));
            // Välj alla kolumner i raden
            //
            List<WebElement> columnsInLoop = custList.get(0).findElements(By.tagName("td"));

            // Om personnummret som står på raden stämmer med den som kom som parameter i funktionen
            //
            if(resenar.PK_RESENAR.equals(columnsInLoop.get(2).getText())) {
                // öppna resenär
                //
                columnsInLoop.get(1).findElement(By.tagName("a")).click();
                driver.switchTo().defaultContent();
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame1"));

                SelectFromMenu(EnumTypes.ResenarMenuchoise.FARDBEVIS);
                //wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt( "contentIFrame1"));
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt( "area_ftj_Contact_FardbevisFrame"));

                custList = driver.findElements(By.xpath("//*[@id=\"gridBodyTable\"]/tbody/tr"));


                // Om tillstånd finns välj den
                //
                if(custList.size()!=0) {
                    if (VISUAL_DEBUG)
                        drawBorder(custList.get(0));
                    driver.executeJavaScript("arguments[0].click();", custList.get(0));
                    // Välj alla kolumner i raden
                    //
                    columnsInLoop = custList.get(0).findElements(By.tagName("td"));
                    columnsInLoop.get(1).findElement(By.tagName("a")).click();
                    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt( "contentIFrame1"));

                    resenar.arendeList.get(0).sitInformation.INFO_FTJ_KORTNUMMER = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"Nummer_label\"]/a"))).getText();
                    resenar.arendeList.get(0).sitInformation.INFO_FTJ_KORT_ID = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"Id-nummer_label\"]"))).getText();
                    resenar.arendeList.get(0).sitInformation.INFO_IDEMIA_FILENAME = "SLA_Order_" + wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"Ordernummer_label\"]"))).getText() + "_"
                            + wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-for-id=\"createdon_label\"]"))).getText().replace(":","").replace("-", "").replace(" ","") + "nn"
                            + "_05_09.zip.gpg";
                    //arendeData.kortnummerFTJ = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"Nummer_label\"]/a"))).getText() + "-" + wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"Id-nummer_label\"]"))).getText();
                    //arendeData.idemiaData = "SLA_Order_" + wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"Ordernummer_label\"]"))).getText() + "_"
                    //        + wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-for-id=\"createdon_label\"]"))).getText().replace(":","").replace("-", "").replace(" ","") + "nn"
                     //       + "_05_09.zip.gpg";
                }
            }
        }
        else
        {
            // TODO: IF RESENAR NOT IN SOFT - IT SHOULD NEVER HAPPEN
        }
        return true;
    }

    public boolean DecisionSelection(DataStructures.Resenar resenar)
    {
        // TODO: Implement Beviljad
        /*
        ftj_fardtjanstarende|NoRelationship|Form|Mscrm.Form.ftj_fardtjanstarende.Save
        ftj_fardtjanstarende|NoRelationship|Form|Mscrm.Form.ftj_fardtjanstarende.SaveAndClose
        ftj_fardtjanstarende|NoRelationship|Form|Mscrm.Form.ftj_fardtjanstarende.NewRecord
        ftj_fardtjanstarende|NoRelationship|Form|Mscrm.Form.ftj_fardtjanstarende.Delete
        ftj_fardtjanstarende|NoRelationship|Form|ftj.ftj_fardtjanstarende.Besluta.Button
        ftj_fardtjanstarende|NoRelationship|Form|ftj.ftj_fardtjanstarende.BeslutaDirekt.Button
        ftj_fardtjanstarende|NoRelationship|Form|ftj.ftj_fardtjanstarende.Komplettering.Button
        ftj_fardtjanstarende|NoRelationship|Form|ftj.ftj_fardtjanstarende.Sakkunnig.Button
        ftj_fardtjanstarende|NoRelationship|Form|ftj.ftj_fardtjanstarende.ForhandsgranskaBeslutsbrev.Button
         */
        driver.switchTo().defaultContent();
        switch(resenar.arendeList.get(0).ARENDE_BESLUT) {
            case "Beviljad":
                logger.debug("Clicking Besluta Direkt button.");
                if (VISUAL_DEBUG) {
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_fardtjanstarende|NoRelationship|Form|ftj.ftj_fardtjanstarende.BeslutaDirekt.Button']/span/a")));
                    drawBorder("//*[@id='ftj_fardtjanstarende|NoRelationship|Form|ftj.ftj_fardtjanstarende.BeslutaDirekt.Button']/span/a");
                }
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_fardtjanstarende|NoRelationship|Form|ftj.ftj_fardtjanstarende.BeslutaDirekt.Button']/span/a"))).click();
                // TODO: Add real check and not static

                boolean staleElement= true;

                while(staleElement){
                    try{
                        if (VISUAL_DEBUG) {
                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_fardtjanstarende|NoRelationship|Form|ftj.ftj_fardtjanstarende.Aktivera.Button']")));
                            drawBorder("//*[@id='ftj_fardtjanstarende|NoRelationship|Form|ftj.ftj_fardtjanstarende.Aktivera.Button']");
                        }
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_fardtjanstarende|NoRelationship|Form|ftj.ftj_fardtjanstarende.Aktivera.Button']")));
                        logger.debug("Found Aktivera button.");
                        staleElement = false;

                    } catch(StaleElementReferenceException e){
                        logger.info("Stale element at SoFTPrepare #1 usage of ribbonBtnArendehanteringArrow");
                        staleElement = true;
                    }
                }
                logger.debug("Sleep 5 secs.");
                sleep(10000);

                break;
            case "Beviljad delvis":
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_fardtjanstarende|NoRelationship|Form|Mscrm.Form.ftj_fardtjanstarende.SaveAndClose']/span/a"))).click();
                break;
            case "Avslås":
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_fardtjanstarende|NoRelationship|Form|Mscrm.Form.ftj_fardtjanstarende.SaveAndClose']/span/a"))).click();
                break;
            case "Avvisad":
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_fardtjanstarende|NoRelationship|Form|Mscrm.Form.ftj_fardtjanstarende.SaveAndClose']/span/a"))).click();
                break;
            case "Avskriven":
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_fardtjanstarende|NoRelationship|Form|Mscrm.Form.ftj_fardtjanstarende.SaveAndClose']/span/a"))).click();
                break;
            default:
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ftj_fardtjanstarende|NoRelationship|Form|Mscrm.Form.ftj_fardtjanstarende.SaveAndClose']/span/a"))).click();
                break;
        }

        return true;

    }
    public boolean SelectFromMenu(EnumTypes.ResenarMenuchoise menuchoise)
    {
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

        boolean noSuchElement= true;

        while(noSuchElement){
            try{
                wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//span[@id='TabNode_tab0Tab']/a/span/span")))).click();
                noSuchElement = false;

            } catch(NoSuchElementException e){
                logger.info("Stale element at SoFTPrepare #1 usage of ribbonBtnArendehanteringArrow");
                noSuchElement = true;
            }
        }


        WebElement menu_element = btnSoftCRM;
        switch(menuchoise)
        {
            // case statements
            // values must be of same type of expression
            case TILLSTAND:
                menu_element = driver.findElement(By.xpath("//a[@id='Node_nav_ftj_Contact_Tillstand']/span[2]"));
                break;

            case FARDBEVIS:
                menu_element = driver.findElement(By.xpath("//a[@id='Node_nav_ftj_Contact_Fardbevis']/span[2]"));
                break;

            case TILLDELNING:
                menu_element = driver.findElement(By.xpath("//a[@id='Node_nav_ftj_Contact_Tilldelning']/span[2]"));
                break;

            case FARDTJANSARENDE:
                menu_element = driver.findElement(By.xpath("//a[@id='Node_nav_ftj_Contact_Fardtjanstarende']/span[2]"));
                break;

            case FORESKRIFTER:
                menu_element = driver.findElement(By.xpath("//a[@id='Node_nav_ftj_Contact_Foreskrifter']/span[2]"));
                break;
            case RESESALDO:
                menu_element = driver.findElement(By.xpath("//a[@id='Node_nav_ftj_Contact_Resesaldo']/span[2]"));
                break;
            case TILLDELNINGSBUDGET:
                menu_element = driver.findElement(By.xpath("//a[@id='Node_nav_ftj_Contact_Tilldelningsbudget']/span[2]"));
                break;
            case DOKUMENT:
                menu_element = driver.findElement(By.xpath("//a[@id='Node_nav_ftj_Contact_Dokument']/span[2]"));
                break;
            case SJUKRESEARENDE:
                menu_element = driver.findElement(By.xpath("//a[@id='Node_nav_ftj_Contact_Sjukresearende']/span[2]"));
                break;
            default :
                // Statements
        }

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

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt( "contentIFrame1"));

        return true;
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
