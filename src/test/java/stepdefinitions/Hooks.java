package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import support.LocalSite;
import utilities.ConfigReader;
import utilities.Driver;

public class Hooks {
    @Before("@local")
    public void startLocal() throws Exception { LocalSite.start(); }

    @Before("@external and not @register-login")
    public void validateExternalAccount() {
        ConfigReader.getProperty("username");
        ConfigReader.getProperty("password");
    }

    @Before("@register-login")
    public void validateRegistrationMode() {
        if (Boolean.parseBoolean(ConfigReader.getProperty("headless"))) {
            throw new IllegalStateException("Register/login requires -Dheadless=false for operator consent and verification.");
        }
    }

    @After
    public void cleanup(Scenario scenario) {
        try {
            if (scenario.isFailed() && Driver.isStarted() && !scenario.getSourceTagNames().contains("@register-login")) {
                try {
                    scenario.attach(((TakesScreenshot) Driver.getDriver())
                        .getScreenshotAs(OutputType.BYTES), "image/png", "Failure screenshot");
                } catch (RuntimeException e) { scenario.log("Screenshot unavailable: " + e.getClass().getSimpleName()); }
            }
        } finally {
            try { Driver.closeDriver(); }
            finally { LocalSite.stop(); }
        }
    }
}
