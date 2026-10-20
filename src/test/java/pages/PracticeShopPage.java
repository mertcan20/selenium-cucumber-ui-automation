package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public final class PracticeShopPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    public PracticeShopPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }
    public void signIn(String email, String password) {
        enter("email", email); enter("password", password); click("sign-in");
    }
    public void add(int quantity) { enter("quantity", String.valueOf(quantity)); click("add"); }
    public void remove() { click("remove"); }
    public void checkout() { click("checkout"); }
    public boolean shopVisible() { return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("shop"))).isDisplayed(); }
    public boolean shopHidden() { return !driver.findElement(By.id("shop")).isDisplayed(); }
    public String text(String id) { return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id))).getText(); }
    private void enter(String id, String value) {
        var input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
        input.clear(); input.sendKeys(value);
    }
    private void click(String id) { wait.until(ExpectedConditions.elementToBeClickable(By.id(id))).click(); }
}
