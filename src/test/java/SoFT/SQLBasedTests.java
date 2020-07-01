package SoFT;

import SoFT.base.SoFTBaseTest;
import SoFT.data.DataStructures;
import SoFT.data.EnumTypes;
import org.testng.Assert;
import org.testng.annotations.*;
import se.soprasteria.automatedtesting.webdriver.helpers.driver.AutomationDriver;

import java.sql.*;

public class SQLBasedTests extends SoFTBaseTest {
    DataStructures mainDataStructure;
    String owner = "PERIKLIS";

    @Test(dataProvider = "getDriver", groups = {"standard"}, priority = 0)
    public void VerifyAllResenar(AutomationDriver driver) throws SQLException, ClassNotFoundException {
        try {
            mainDataStructure = null;
            mainDataStructure = new DataStructures(owner, true );
            Assert.assertTrue(loginPageInterface.performLogin(), "loginPage.performLogin() failed");
            Assert.assertTrue(mainPage.isPageLoaded(), "mainPage.isPageLoaded() failed");
            Assert.assertTrue(mainPage.SoFTPrepare(EnumTypes.Menuchoice.RESENAR), "mainPage.SoFTPrepare() failed");
            for (int i = 0; i < mainDataStructure.resenarList.size(); i++) {
                if (!mainDataStructure.resenarList.get(i).RESENAR_CREATED.equals("Y"))
                {
                    Assert.assertTrue(sqlResenarPage.FindResenar(mainDataStructure.resenarList.get(i)), "sqlResenarPage.FindResenar() failed");
                }
            }
            mainDataStructure.UpdateRecords(DataStructures.Tables.TBL_RESENAR);
        } catch (AssertionError err) {
            mainDataStructure.UpdateRecords(DataStructures.Tables.TBL_RESENAR);
        }
        catch (SQLIntegrityConstraintViolationException ex)
        {

        }
    }


    @Test(dataProvider = "getDriver", groups = {"standard"}, priority = 0)
    public void VerifyResenar(AutomationDriver driver) throws SQLException, ClassNotFoundException {
        try {
            mainDataStructure = null;
            mainDataStructure = new DataStructures(owner, false);
            Assert.assertTrue(loginPageInterface.performLogin(), "loginPage.performLogin() failed");
            Assert.assertTrue(mainPage.isPageLoaded(), "mainPage.isPageLoaded() failed");
            Assert.assertTrue(mainPage.SoFTPrepare(EnumTypes.Menuchoice.RESENAR), "mainPage.SoFTPrepare() failed");
            for (int i = 0; i < mainDataStructure.resenarList.size(); i++) {
                if (!mainDataStructure.resenarList.get(i).RESENAR_CREATED.equals("Y"))
                {
                    Assert.assertTrue(sqlResenarPage.FindResenar(mainDataStructure.resenarList.get(i)), "sqlResenarPage.FindResenar() failed");
                }
            }
            mainDataStructure.UpdateRecords(DataStructures.Tables.TBL_RESENAR);
        } catch (AssertionError err) {
            mainDataStructure.UpdateRecords(DataStructures.Tables.TBL_RESENAR);
        }
        catch (SQLIntegrityConstraintViolationException ex)
        {

        }
    }

    @Test(dataProvider = "getDriver", groups = {"standard"}, priority = 0)
    public void AddResenar(AutomationDriver driver) throws SQLException, ClassNotFoundException {
        try {
            mainDataStructure = null;
            mainDataStructure = new DataStructures(owner, false );
            Assert.assertTrue(loginPageInterface.performLogin(), "loginPage.performLogin() failed");
            Assert.assertTrue(mainPage.isPageLoaded(), "mainPage.isPageLoaded() failed");
            Assert.assertTrue(mainPage.SoFTPrepare(EnumTypes.Menuchoice.RESENAR), "mainPage.SoFTPrepare() failed");
            for (int i = 0; i < mainDataStructure.resenarList.size(); i++) {
                Assert.assertTrue(sqlResenarPage.AddResenar(mainDataStructure.resenarList.get(i)), "sqlResenarPage.AddResenar");
            }
            mainDataStructure.UpdateRecords(DataStructures.Tables.TBL_RESENAR);
        } catch (AssertionError err) {
            mainDataStructure.UpdateRecords(DataStructures.Tables.TBL_RESENAR);
        }
        catch (SQLIntegrityConstraintViolationException ex)
        {

        }
    }


    @Test(dataProvider = "getDriver", groups = {"standard"}, priority = 0)
    public void AddArenden(AutomationDriver driver) throws SQLException, ClassNotFoundException {
        try {
            mainDataStructure = null;
            mainDataStructure = new DataStructures(owner, false );
            Assert.assertTrue(loginPageInterface.performLogin(), "loginPage.performLogin() failed.");
            Assert.assertTrue(mainPage.isPageLoaded(), "mainPage.isPageLoaded() failed.");
            for (int i = 0; i < mainDataStructure.resenarList.size(); i++) {
                if(mainDataStructure.resenarList.get(i).arendeList.size() == 0 && mainDataStructure.resenarList.get(i).RESENAR_CREATE_FTJARENDE.equals("Y")) //mainDataStructure.resenarList.get(i).RESENAR_CREATE_FTJARENDE
                {
                    Assert.assertTrue(mainPage.SoFTPrepare(EnumTypes.Menuchoice.RESENAR), "mainPage.SoFTPrepare() failed");
                    Assert.assertTrue(sqlArendePage.CreateFardtjanstArende(mainDataStructure.resenarList.get(i)), "sqlArendePage.CreateFardtjanstArende() failed");
                    sleep(2000);
                    Assert.assertTrue(sqlArendePage.CreateFardtjansArendeDocument(mainDataStructure.resenarList.get(i), EnumTypes.DocumentTypes.FOTO), "sqlArendePage.CreateFardtjansArendeDocument() failed");
                    sleep(2000);
                    Assert.assertTrue(sqlArendePage.CreateFardtjanstForeskrift(mainDataStructure.resenarList.get(i)), "sqlArendePage.CreateFardtjanstForeskrift() failed");
                    sleep(2000);
                    Assert.assertTrue(sqlArendePage.ModifyFardtjandsArende(mainDataStructure.resenarList.get(i)), "sqlArendePage.ModifyFardtjandsArende()");
                    mainDataStructure.UpdateRecords(DataStructures.Tables.TBL_ARENDEN);
                }
            }
            mainDataStructure.UpdateRecords(DataStructures.Tables.TBL_ARENDEN);
        } catch (AssertionError err) {
            mainDataStructure.UpdateRecords(DataStructures.Tables.TBL_ARENDEN);
        }
        catch (SQLIntegrityConstraintViolationException ex)
        {

        }

    }

    @Test(dataProvider = "getDriver", groups = {"standard"}, priority = 1)
    public void ApproveArenden(AutomationDriver driver) throws SQLException, ClassNotFoundException {
        try {
            mainDataStructure = null;
            mainDataStructure = new DataStructures(owner, false );
            Assert.assertTrue(loginPageInterface.performLogin(), "loginPage.performLogin() failed");
            Assert.assertTrue(mainPage.isPageLoaded(), "mainPage.isPageLoaded() failed");
            for (int i = 0; i < mainDataStructure.resenarList.size(); i++) {
                if(mainDataStructure.resenarList.get(i).arendeList.size() > 0 && mainDataStructure.resenarList.get(i).arendeList.get(0).ARENDE_STATUS.equals("NONE"))
                {
                    Assert.assertTrue(mainPage.SoFTPrepare(EnumTypes.Menuchoice.RESENAR), "mainPage.SoFTPrepare() failed");
                    Assert.assertTrue(sqlArendePage.DecideFardtjanstArende(mainDataStructure.resenarList.get(i)), "fardtjanstPage.DecideFardtjanstArende() failed");
                }
                mainDataStructure.UpdateRecords(DataStructures.Tables.TBL_ARENDEN);
            }
            mainDataStructure.UpdateRecords(DataStructures.Tables.TBL_ARENDEN);
        } catch (AssertionError err) {
            mainDataStructure.UpdateRecords(DataStructures.Tables.TBL_ARENDEN);
        }
        catch (SQLIntegrityConstraintViolationException ex)
        {

        }
    }

    @Test(dataProvider = "getDriver", groups = {"standard"}, priority = 3)
    public void GetFardtjansttillstandData(AutomationDriver driver) throws SQLException, ClassNotFoundException {
        try {
            mainDataStructure = null;
            mainDataStructure = new DataStructures(owner, false);
            Assert.assertTrue(loginPageInterface.performLogin(), "loginPage.performLogin() failed");
            Assert.assertTrue(mainPage.isPageLoaded(), "mainPage.isPageLoaded() failed");
            for (int i = 0; i < mainDataStructure.resenarList.size(); i++) {
                if(mainDataStructure.resenarList.get(i).arendeList.size() > 0 && mainDataStructure.resenarList.get(i).arendeList.get(0).ARENDE_STATUS.equals("BEVLD") && !mainDataStructure.resenarList.get(i).arendeList.get(0).GetTillstandData())
                {
                    Assert.assertTrue(mainPage.SoFTPrepare(EnumTypes.Menuchoice.RESENAR), "mainPage.SoFTPrepare() failed");
                    Assert.assertTrue(sqlArendePage.GetFTJTillstandId(mainDataStructure.resenarList.get(i)), "fardtjanstPage.GetFTJTillstandId() failed");
                    sleep(2000);
                    mainDataStructure.UpdateRecords(DataStructures.Tables.TBL_INFO);
                }
            }
            mainDataStructure.UpdateRecords(DataStructures.Tables.TBL_INFO);

        } catch (AssertionError err) {
            mainDataStructure.UpdateRecords(DataStructures.Tables.TBL_INFO);
        }
        catch (SQLIntegrityConstraintViolationException ex)
        {

        }
    }

    @Test(dataProvider = "getDriver", groups = {"standard"}, priority = 3)
    public void GetFTJFardbevisData(AutomationDriver driver) throws SQLException, ClassNotFoundException {
        try {
            mainDataStructure = null;
            mainDataStructure = new DataStructures(owner, false );
            Assert.assertTrue(loginPageInterface.performLogin(), "loginPage.performLogin() failed");
            Assert.assertTrue(mainPage.isPageLoaded(), "mainPage.isPageLoaded() failed");
            for (int i = 0; i < mainDataStructure.resenarList.size(); i++) {
                if(mainDataStructure.resenarList.get(i).arendeList.size() > 0 && mainDataStructure.resenarList.get(i).arendeList.get(0).ARENDE_STATUS.equals("BEVLD") && !mainDataStructure.resenarList.get(i).arendeList.get(0).GetFardbevisData())
                {
                    Assert.assertTrue(mainPage.SoFTPrepare(EnumTypes.Menuchoice.RESENAR), "mainPage.SoFTPrepare() failed");
                    Assert.assertTrue(sqlArendePage.GetFTJFardbevisData(mainDataStructure.resenarList.get(i)), "fardtjanstPage.GetFTJTillstandId() failed");
                    mainDataStructure.UpdateRecords(DataStructures.Tables.TBL_INFO);
                }
            }
            mainDataStructure.UpdateRecords(DataStructures.Tables.TBL_INFO);
        } catch (AssertionError err) {
            mainDataStructure.UpdateRecords(DataStructures.Tables.TBL_INFO);
        }
        catch (SQLIntegrityConstraintViolationException ex)
        {

        }
    }
}
