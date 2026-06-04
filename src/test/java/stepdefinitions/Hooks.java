package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.WaitUtils;

public class Hooks {

    @Before
    public void setUp() {
        Driver.getDriver().get(ConfigReader.getProperty("baseUrl"));
        WaitUtils.waitForPageToLoad(10);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            final byte[] screenshot =
                    ((TakesScreenshot) Driver.getDriver())
                            .getScreenshotAs(OutputType.BYTES);

            scenario.attach(screenshot, "image/png", "Failure Screenshot");
        }

        Driver.closeDriver();
    }
}