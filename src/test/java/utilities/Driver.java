package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.time.Duration;

public final class Driver {
    private static final ThreadLocal<WebDriver> DRIVERS = new ThreadLocal<>();
    private Driver() { }
    public static WebDriver getDriver() {
        if (DRIVERS.get() == null) {
            boolean headless = Boolean.parseBoolean(ConfigReader.getProperty("headless"));
            WebDriver driver;
            switch (ConfigReader.getProperty("browser").toLowerCase(java.util.Locale.ROOT)) {
                case "firefox" -> {
                    FirefoxOptions options = new FirefoxOptions();
                    if (headless) options.addArguments("-headless");
                    driver = new FirefoxDriver(options);
                }
                case "edge" -> {
                    EdgeOptions options = new EdgeOptions();
                    if (headless) options.addArguments("--headless=new");
                    options.addArguments("--window-size=1440,1000");
                    driver = new EdgeDriver(options);
                }
                case "chrome" -> {
                    ChromeOptions options = new ChromeOptions();
                    if (headless) options.addArguments("--headless=new");
                    options.addArguments("--window-size=1440,1000", "--disable-notifications");
                    driver = new ChromeDriver(options);
                }
                default -> throw new IllegalArgumentException("Unsupported browser");
            }
            DRIVERS.set(driver);
            driver.manage().timeouts().implicitlyWait(Duration.ZERO);
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            if (Boolean.parseBoolean(ConfigReader.getProperty("maximize"))) driver.manage().window().maximize();
        }
        return DRIVERS.get();
    }
    public static boolean isStarted() { return DRIVERS.get() != null; }
    public static void closeDriver() {
        WebDriver driver = DRIVERS.get();
        try { if (driver != null) driver.quit(); }
        finally { DRIVERS.remove(); }
    }
}
