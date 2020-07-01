package SoFT.sitemodel.resenar;

import SoFT.data.ArendeData;
import org.openqa.selenium.WebElement;

public interface ResenarI {
    public abstract boolean isPageLoaded ();
    public abstract void drawBorder(String xpath);
    public abstract void drawBorder(WebElement element_node);
    public abstract boolean AddResenar(ArendeData arendeData);
    public abstract boolean VerifyResenar(ArendeData arendeData);
    public abstract boolean FindResenar(ArendeData arendeData);
}
