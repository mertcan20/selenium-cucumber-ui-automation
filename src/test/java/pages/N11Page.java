package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;
import utilities.WaitUtils;

public class N11Page {

    public N11Page() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy (linkText = "Üye Ol")
    private WebElement uyeOlbutton;

    @FindBy(id = "firstName")
    private WebElement firstName;

    @FindBy(id = "lastName")
    private WebElement lastName;

    @FindBy(id = "email")
    private WebElement eMail;

    @FindBy(id = "gsmNumber")
    private WebElement phoneNo;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(xpath = "//*[text()='Erkek']")
    private WebElement erkekCheckbox;

    @FindBy(id = "agreement")
    private WebElement agreementCheckbox;

    @FindBy(id = "offer")
    private WebElement offerCheckbox;

    @FindBy(className = "submit-button")
    private WebElement submitButton;

    @FindBy(linkText = "Giriş Yap")
    private WebElement loginMenuButton;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(css = "button[aria-label='Giriş Yap'], #loginButton")
    private WebElement loginButton;

    @FindBy(css = "a[title='Hesabım']")
    private WebElement myAccountButton;

    @FindBy(css = "a[title='Çıkış Yap']")
    private WebElement logoutButton;

    public void clickLoginMenuButton() {
        WaitUtils.waitForClickability(loginMenuButton, 10).click();
    }

    public void enterEmail(String email) {
        WaitUtils.waitForVisibility(emailInput, 10).clear();
        emailInput.sendKeys(email);
    }

    public void enterPassword(String password) {
        // Current public login first asks for email, then reveals the password stage.
        boolean passwordVisible = Driver.getDriver().findElements(By.id("password"))
            .stream().anyMatch(WebElement::isDisplayed);
        if (!passwordVisible) clickLoginButton();
        WaitUtils.waitForVisibility(passwordInput, 10).clear();
        passwordInput.sendKeys(password);
    }

    public void clickLoginButton() {
        WaitUtils.waitForClickability(loginButton, 10).click();
    }

    public WebElement getMyAccountButton() {
        return myAccountButton;
    }

    public boolean isLogoutButtonDisplayed() {
        return WaitUtils.waitForVisibility(logoutButton, 10).isDisplayed();
    }

    public void waitForAuthenticatedAccount(Duration timeout) {
        new WebDriverWait(Driver.getDriver(), timeout).until(driver -> {
            var accounts = driver.findElements(By.cssSelector("a[title='Hesabım']"));
            if (accounts.isEmpty() || !accounts.get(0).isDisplayed()) return false;
            new Actions(driver).moveToElement(accounts.get(0)).perform();
            return driver.findElements(By.cssSelector("a[title='Çıkış Yap']"))
                .stream().anyMatch(WebElement::isDisplayed);
        });
    }
}

