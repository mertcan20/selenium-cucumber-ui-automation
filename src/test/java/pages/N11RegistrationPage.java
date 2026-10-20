package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import support.RegistrationUser;
import java.time.Duration;
import java.net.URI;

public final class N11RegistrationPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public N11RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void open(String baseUrl) { driver.get(URI.create(baseUrl).resolve("/uye-ol").toString()); }

    public void fill(RegistrationUser user) {
        enter("firstName", user.firstName());
        enter("lastName", user.lastName());
        enter("email", user.email());
        enter("gsmNumber", user.phone());
        enter("password", user.password());
        // Consent and submission belong to the operator on the live site.
        // Do not select marketing consent or bypass SMS/CAPTCHA.
    }

    private void enter(String id, String value) {
        var field = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
        field.clear();
        if (!value.isEmpty()) field.sendKeys(value);
    }
}
