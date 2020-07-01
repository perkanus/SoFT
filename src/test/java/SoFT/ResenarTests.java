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

public class ResenarTests extends SoFTBaseTest {
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
            else
            {

            }
        }
    }

    @Test(dataProvider = "getDriver", groups = {"standard"}, priority = 0)
    public void VerifyResenar(AutomationDriver driver) {
        logger.info("Executing @Test VerifyResenar.");
        Assert.assertTrue(loginPageInterface.performLogin(), "loginPage.performLogin() failed");
        Assert.assertTrue(mainPage.isPageLoaded(), "mainPage.isPageLoaded() failed");
        Assert.assertTrue(mainPage.SoFTPrepare(EnumTypes.Menuchoice.RESENAR), "mainPage.SoFTPrepare() failed");
        for (int i = 0; i < arendeDataList.size(); i++) {
            if (!arendeDataList.get(i).resenarSkapad.equals("Y"))
                Assert.assertTrue(resenarPage.VerifyResenar(arendeDataList.get(i)), "resenarPage.VerifyResenar() failed");
        }
    }

    @Test(dataProvider = "getDriver", groups = {"standard"}, priority = 1) //, dependsOnMethods = "VerifyResenar"
    public void AddResenar(AutomationDriver driver) {
        logger.info("Executing @Test AddResenar.");
        Assert.assertTrue(loginPageInterface.performLogin(), "loginPage.performLogin() failed");
        Assert.assertTrue(mainPage.isPageLoaded(), "mainPage.isPageLoaded() failed");
        Assert.assertTrue(mainPage.SoFTPrepare(EnumTypes.Menuchoice.RESENAR), "mainPage.SoFTPrepare() failed");
        for (int i = 0; i < arendeDataList.size(); i++) {
            Assert.assertTrue(resenarPage.AddResenar(arendeDataList.get(i)), "resenarPage.AddResenar");
        }
    }

    @AfterMethod
    public void UpdateExcel() throws IOException {
        logger.info("Executing @AfterMethod UpdateExcel.");
        ExcelHandler writefile = new ExcelHandler();
        writefile.writeExcel("src/main/resources/data/","Testpersoner.xls", "Person", arendeDataList);
    }


}
