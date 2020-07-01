package SoFT.sitemodel.SQL.RESENAR;

import SoFT.data.DataStructures;
import org.openqa.selenium.WebElement;

public interface SQLResenarI_Interface {
    public abstract boolean isPageLoaded();
    public abstract void drawBorder(String xpath);
    public abstract void drawBorder(WebElement element_node);
    public abstract void drawBorder(String xpath, String color, int width);
    public abstract void drawBorder(WebElement element_node, String color, int width);
    public abstract boolean AddResenar(DataStructures.Resenar resenar);
    public abstract boolean FindResenar(DataStructures.Resenar resenar);
}
