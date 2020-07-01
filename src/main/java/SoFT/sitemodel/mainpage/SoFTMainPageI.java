package SoFT.sitemodel.mainpage;

import SoFT.data.EnumTypes;
import SoFT.data.ArendeData;
import org.openqa.selenium.WebElement;

public interface SoFTMainPageI {
    public abstract boolean isPageLoaded ();
    public abstract boolean SoFTPrepare(EnumTypes.Menuchoice menu);
    public abstract boolean ResenarPrepare(EnumTypes.ResenarMenuchoise menu, ArendeData arendeData);
    public abstract void drawBorder(String xpath);
    public abstract void drawBorder(WebElement element_node);
}
