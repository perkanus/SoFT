package SoFT.sitemodel.loginpage;

import se.soprasteria.automatedtesting.webdriver.helpers.base.baseconfig.config.User;

public interface LoginPage_Interface {
    public abstract boolean isPageLoaded ();
    public abstract boolean performLogin();
}
