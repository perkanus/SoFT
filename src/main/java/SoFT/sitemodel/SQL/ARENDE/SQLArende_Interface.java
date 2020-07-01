package SoFT.sitemodel.SQL.ARENDE;

import SoFT.data.ArendeData;
import SoFT.data.DataStructures;
import SoFT.data.EnumTypes;
import org.openqa.selenium.WebElement;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public interface SQLArende_Interface {
    public abstract boolean isPageLoaded();
    public abstract boolean CreateFardtjanstArende(DataStructures.Resenar resenar) throws UnsupportedEncodingException, SQLException, ClassNotFoundException;
    public abstract boolean CreateFardtjansArendeDocument(DataStructures.Resenar resenar, EnumTypes.DocumentTypes documenttype);
    public abstract boolean CreateFardtjanstForeskrift(DataStructures.Resenar resenar);
    public abstract boolean ModifyFardtjandsArende(DataStructures.Resenar resenar);
    public abstract boolean DecideFardtjanstArende(DataStructures.Resenar resenar);
    public abstract boolean GetFTJTillstandId(DataStructures.Resenar resenar) throws SQLException, ClassNotFoundException;
    public abstract boolean GetFTJFardbevisData(DataStructures.Resenar resenar);

    public abstract void drawBorder(String xpath);
    public abstract void drawBorder(WebElement element_node);
}
