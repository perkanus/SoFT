package SoFT.sitemodel.fardtjanst;

import SoFT.data.ArendeData;
import SoFT.data.EnumTypes;
import org.openqa.selenium.WebElement;

import java.io.UnsupportedEncodingException;

public interface FardtjanstI {
    public abstract boolean isPageLoaded ();
    public abstract boolean CreateFardtjanstArende(ArendeData arendeData) throws UnsupportedEncodingException;
    public abstract boolean CreateFardtjansArendeDocument(ArendeData arendeData, EnumTypes.DocumentTypes documenttype, String stringFoto);
    public abstract boolean CreateFardtjanstForeskrift(ArendeData arendeData);
    public abstract boolean ModifyFardtjandsArende(ArendeData arendeData, String stringFoto);
    public abstract boolean DecideFardtjanstArende(ArendeData arendeData);
    public abstract boolean GetFTJTillstandId(ArendeData arendeData);
    public abstract boolean GetFTJFardbevisData(ArendeData arendeData);

    public abstract void drawBorder(String xpath);
    public abstract void drawBorder(WebElement element_node);
}
