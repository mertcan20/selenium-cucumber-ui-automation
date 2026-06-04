package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.junit.Assert;
import pages.N11Page;
import utilities.BrowserUtils;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.WaitUtils;

public class N11step {

    N11Page n11Page = new N11Page();

    @Given("Kullanıcı Anasayfaya gider.")
    public void kullaniciAnasayfayaGider() {
        Driver.getDriver().get(ConfigReader.getProperty("baseUrl"));
    }


    @When("Ana sayfanın yüklendiği doğrulanır.")
    public void anaSayfaninYuklendigiDogrulanir() {

        String actualTitle = Driver.getDriver().getTitle();
        System.out.println("Title = " + actualTitle);

        Assert.assertTrue(actualTitle.contains("n11"));

    }

    @And("Kullanıcı ana sayfada Giriş Yap butonunu bulur ve tıklar.")
    public void kullaniciAnaSayfadaGirisYapButonunuBulurVeTiklar() {
        n11Page.clickLoginMenuButton();
    }

    @And("Giriş sayfasının yüklendiği doğrulanır.")
    public void girisSayfasininYuklendigiDogrulanir() {
        WaitUtils.waitForPageToLoad(10);
        String actualUrl = Driver.getDriver().getCurrentUrl();
        String expectedUrl = "https://www.n11.com/giris-yap";
        Assert.assertEquals(expectedUrl, actualUrl);
    }

    @And("Kullanıcı giriş sayfasında geçerli bir kullanıcı adı  girer.")
    public void kullaniciGirisSayfasindaGecerliBirKullaniciAdiGirer() {
        n11Page.enterEmail(ConfigReader.getProperty("username"));
    }

    @And("Kullanıcı giriş sayfasında geçerli bir şifre girer.")
    public void kullaniciGirisSayfasindaGecerliBirSifreGirer() {
        n11Page.enterPassword(ConfigReader.getProperty("password"));
    }

    @And("Kullanıcı {string} butonuna tıklar.")
    public void kullaniciButonunaTiklar(String buttonName) {
        n11Page.clickLoginButton();
    }

    @And("Kullanıcının hesabına başarıyla giriş yapıldığı doğrulanır.")
    public void kullanicininHesabinaBasariylaGirisYapildigiDogrulanir() {
        BrowserUtils.hoverOver(n11Page.getMyAccountButton());
        Assert.assertTrue(n11Page.isLogoutButtonDisplayed());
    }
}