package pages;

import io.cucumber.java.de.Wenn;
import org.checkerframework.checker.signature.qual.FieldDescriptor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;
import utilities.ElementUtils;
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

    @FindBy(id = "loginButton")
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
        WaitUtils.waitForVisibility(passwordInput, 10).clear();
        passwordInput.sendKeys(password);
    }

    public void clickLoginButton() {
        ElementUtils.clickWithRetry(loginButton, 5);
    }

    public WebElement getMyAccountButton() {
        return myAccountButton;
    }

    public boolean isLogoutButtonDisplayed() {
        return WaitUtils.waitForVisibility(logoutButton, 10).isDisplayed();
    }
}

