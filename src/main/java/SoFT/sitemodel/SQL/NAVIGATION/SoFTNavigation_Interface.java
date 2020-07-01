package SoFT.sitemodel.SQL.NAVIGATION;

import SoFT.SoFTTestNavigation;
import SoFT.data.DataStructures;
import org.openqa.selenium.WebElement;

public interface SoFTNavigation_Interface {
    public abstract boolean isPageLoaded();
    public abstract void drawBorder(String xpath);
    public abstract void drawBorder(WebElement element_node);
    public abstract void drawBorder(String xpath, String color, int width);
    public abstract void drawBorder(WebElement element_node, String color, int width);
    public abstract boolean TopNavigation(SoFTTestNavigation.TopNavigation topNavigation);

}
