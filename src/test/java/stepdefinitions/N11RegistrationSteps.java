package stepdefinitions;

import io.cucumber.java.en.*;
import pages.N11Page;
import pages.N11RegistrationPage;
import support.RegistrationUser;
import utilities.ConfigReader;
import utilities.Driver;
import java.net.URI;
import java.time.Duration;

public class N11RegistrationSteps {
    private RegistrationUser user;
    private N11Page account;

    @Given("yeni kullanıcı bilgileri ortam değişkenlerinden hazırlanır")
    public void prepareUser() { user = RegistrationUser.fromEnvironment(); }

    @Given("N11 kayıt formu açılır")
    public void openRegistration() {
        new N11RegistrationPage(Driver.getDriver()).open(ConfigReader.getProperty("baseUrl"));
    }

    @When("kayıt formu yeni kullanıcı bilgileriyle doldurulur")
    public void fillRegistration() { new N11RegistrationPage(Driver.getDriver()).fill(user); }

    @When("kullanıcı üyelik onayını ve gerekli doğrulamaları tamamlar")
    public void waitForOperator() {
        System.out.println("N11: Kayıt formunu inceleyin; üyelik onayı, gönderim ve gerekiyorsa SMS/CAPTCHA adımlarını tarayıcıda tamamlayın. Script hesap menüsünü bekliyor.");
        long seconds = Long.parseLong(System.getProperty("n11.verificationTimeoutSeconds", "180"));
        account = new N11Page();
        account.waitForAuthenticatedAccount(Duration.ofSeconds(seconds));
    }

    @Then("yeni hesap için oturum açıldığı doğrulanır")
    public void assertAccount() { account.waitForAuthenticatedAccount(Duration.ofSeconds(20)); }

    @When("kayıt tarayıcısı kapatılıp temiz tarayıcıda giriş sayfası açılır")
    public void freshLogin() {
        Driver.closeDriver();
        Driver.getDriver().get(URI.create(ConfigReader.getProperty("baseUrl")).resolve("/giris-yap").toString());
        account = new N11Page();
    }

    @When("aynı yeni kullanıcının e-posta ve şifresiyle giriş yapılır")
    public void signInWithCreatedUser() {
        account.enterEmail(user.email());
        account.enterPassword(user.password());
        account.clickLoginButton();
    }
}
