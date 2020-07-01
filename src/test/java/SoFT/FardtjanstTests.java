package SoFT;

import SoFT.base.SoFTBaseTest;
import SoFT.data.EnumTypes;
import SoFT.data.ArendeData;
import SoFT.excel.ExcelHandler;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.testng.Assert;
import org.testng.annotations.*;
import se.soprasteria.automatedtesting.webdriver.helpers.driver.AutomationDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FardtjanstTests extends SoFTBaseTest {
    List<ArendeData> arendeDataList = new ArrayList<ArendeData>();

    @BeforeMethod
    public void LoadExcel() throws IOException {
        // TODO: Skapa flera sheets som blir lästa om man behöver lagra data i andra flickar i Excel-arket
        //
        logger.info("Executing @BeforeMethod LoadExcel.");
        ExcelHandler testpersoner = new ExcelHandler();
        Sheet arendeSheet = testpersoner.readExcel("src/main/resources/data/","Testpersoner.xls", "Person");
        int rowCount = arendeSheet.getLastRowNum()-arendeSheet.getFirstRowNum();
        DataFormatter formatter = new DataFormatter();
        arendeDataList.clear();

        for (int i = 1; i < rowCount+1; i++) {
            Row row = arendeSheet.getRow(i);
            if(row.getCell(0) != null && row.getCell(0).toString().length()!=0){
                arendeDataList.add(new ArendeData(
                        formatter.formatCellValue(row.getCell(0)),                                          // Personnummer
                        row.getCell(1).toString(),                      // Skapa Färdtjänsttillstånd
                        row.getCell(2).toString(),                      // Skapa Sjukresetillstånd
                        (row.getCell(3) == null) ? "" : formatter.formatCellValue(row.getCell(3)),          // Flagga om resenären finns tillagd i SoFT
                        (row.getCell(4) == null) ? "" : formatter.formatCellValue(row.getCell(4)),      // Färdtjänstärende ID
                        (row.getCell(5) == null) ? "" : formatter.formatCellValue(row.getCell(5)),      // Sjukreseärende ID
                        (row.getCell(6) == null) ? "" : formatter.formatCellValue(row.getCell(6)),      // Kundnummer
                        (row.getCell(7) == null) ? "" : formatter.formatCellValue(row.getCell(7)),      // Färdtjänsttillstånd ID
                        (row.getCell(8) == null) ? "" : formatter.formatCellValue(row.getCell(8)),      // Sjukresetillstånd ID
                        (row.getCell(9) == null) ? "" : formatter.formatCellValue(row.getCell(9)),      // Färdtjänstkortsnummer
                        (row.getCell(10) == null) ? "" : formatter.formatCellValue(row.getCell(10)),     // Sjukresekortsnummer
                        (row.getCell(11) == null) ? "" : row.getCell(11).toString(),                     // Tilldelning Färdsätt
                        (row.getCell(12) == null) ? "" : formatter.formatCellValue(row.getCell(12)),                     // Tilldelning Antal Ledsagare
                        (row.getCell(13) == null) ? "" : row.getCell(13).toString(),                     // Tilldelning Elmoped
                        (row.getCell(14) == null) ? "" : row.getCell(14).toString(),                     // Tilldelning Ensamåkning
                        (row.getCell(15) == null) ? "" : row.getCell(15).toString(),                     // Tilldelning Framsätesplacering
                        (row.getCell(16) == null) ? "" : row.getCell(16).toString(),                     // Tilldelning Allergianpassat Fordon
                        (row.getCell(17) == null) ? "" : row.getCell(17).toString(),                     // Tilldelning Hund
                        (row.getCell(18) == null) ? "" : row.getCell(18).toString(),                     // Tilldelning Länsgränsdispens
                        (row.getCell(19) == null) ? "" : row.getCell(19).toString(),                     // Tilldelning Båttaxi
                        (row.getCell(20) == null) ? "" : row.getCell(20).toString(),                       // beslutsText
                        (row.getCell(21) == null) ? "" : row.getCell(21).toString(),                       // beslutKod
                        (row.getCell(22) == null) ? "" : row.getCell(22).toString()                       // idemia filsträng
                        ));
            }
        }
    }


    @Test(dataProvider = "getDriver", groups = {"standard"}, priority = 0)
        public void AddArenden(AutomationDriver driver) {
            String stringFoto = "";
            Assert.assertTrue(loginPageInterface.performLogin(), "loginPage.performLogin() failed.");
            Assert.assertTrue(mainPage.isPageLoaded(), "mainPage.isPageLoaded() failed.");


            for (int i = 0; i < arendeDataList.size(); i++) {
                if(arendeDataList.get(i).skapaFTJTILL.equals("Y") && arendeDataList.get(i).resenarSkapad.equals("Y"))
                {
                    if(arendeDataList.get(i).skapaFTJTILL.equals("Y") && arendeDataList.get(i).FTJArendeId.length() != 8) {
                        Assert.assertTrue(mainPage.SoFTPrepare(EnumTypes.Menuchoice.RESENAR), "mainPage.SoFTPrepare() failed");
                        Assert.assertTrue(fardtjanstPage.CreateFardtjanstArende(arendeDataList.get(i)), "fardtjanstPage.CreateFardtjanstArende() failed");
                        Assert.assertTrue(fardtjanstPage.CreateFardtjansArendeDocument(arendeDataList.get(i), EnumTypes.DocumentTypes.FOTO, stringFoto ), "fardtjanstPage.CreateFardtjansArendeDocument() failed");

                        sleep(1000);
                        Assert.assertTrue(fardtjanstPage.CreateFardtjanstForeskrift(arendeDataList.get(i)), "fardtjanstPage.CreateFardtjanstForeskrift() failed");
                        sleep(1000);
                        Assert.assertTrue(fardtjanstPage.ModifyFardtjandsArende(arendeDataList.get(i), stringFoto), "fardtjanstPage.ModifyFardtjandsArende()");
                    }
                }
            }
    }

    @Test(dataProvider = "getDriver", groups = {"standard"}, priority = 1)
    public void ApproveArenden(AutomationDriver driver) {
        Assert.assertTrue(loginPageInterface.performLogin(), "loginPage.performLogin() failed");
        Assert.assertTrue(mainPage.isPageLoaded(), "mainPage.isPageLoaded() failed");
        for (int i = 0; i < arendeDataList.size(); i++) {
            if(arendeDataList.get(i).beslutatKod.equals("EMPTY") && !arendeDataList.get(i).beslutsText.equals("") && !arendeDataList.get(i).FTJArendeId.equals("") && !arendeDataList.get(i).FTJArendeId.equals("EMPTY"))
            {
                Assert.assertTrue(mainPage.SoFTPrepare(EnumTypes.Menuchoice.RESENAR), "mainPage.SoFTPrepare() failed");
                Assert.assertTrue(fardtjanstPage.DecideFardtjanstArende(arendeDataList.get(i)), "fardtjanstPage.DecideFardtjanstArende() failed");
            }
        }
    }

    @Test(dataProvider = "getDriver", groups = {"standard"}, priority = 2)
    public void GetFardbevisData(AutomationDriver driver) {
        logger.info("Executing @Test GetFardbevisData.");
        Assert.assertTrue(loginPageInterface.performLogin(), "loginPage.performLogin() failed");
        Assert.assertTrue(mainPage.isPageLoaded(), "mainPage.isPageLoaded() failed");
        for (int i = 0; i < arendeDataList.size(); i++) {
            if(arendeDataList.get(i).beslutatKod.equals("BEVLD"))
            {
                Assert.assertTrue(mainPage.SoFTPrepare(EnumTypes.Menuchoice.RESENAR), "mainPage.SoFTPrepare() failed");
                Assert.assertTrue(fardtjanstPage.GetFTJFardbevisData(arendeDataList.get(i)), "fardtjanstPage.GetFTJFardbevisData() failed");
            }
        }
    }

    @Test(dataProvider = "getDriver", groups = {"standard"}, priority = 3)
    public void GetFardtjansttillstandData(AutomationDriver driver) {
        logger.info("Executing @Test GetFardtjansttillstandData.");
        Assert.assertTrue(loginPageInterface.performLogin(), "loginPage.performLogin() failed");
        Assert.assertTrue(mainPage.isPageLoaded(), "mainPage.isPageLoaded() failed");
        for (int i = 0; i < arendeDataList.size(); i++) {
            if(arendeDataList.get(i).beslutatKod.equals("BEVLD"))
            {
                Assert.assertTrue(mainPage.SoFTPrepare(EnumTypes.Menuchoice.RESENAR), "mainPage.SoFTPrepare() failed");
                Assert.assertTrue(fardtjanstPage.GetFTJTillstandId(arendeDataList.get(i)), "fardtjanstPage.GetFTJTillstandId() failed");
            }
        }
    }

    @AfterMethod
    public void UpdateExcel() throws IOException {
        logger.info("Executing @AfterMethod UpdateExcel.");
        ExcelHandler writefile = new ExcelHandler();
        writefile.writeExcel("src/main/resources/data/","Testpersoner.xls", "Person", arendeDataList);
    }
}
