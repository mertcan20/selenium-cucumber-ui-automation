package stepdefinitions;

import io.cucumber.java.en.*;
import org.junit.Assert;
import pages.PracticeShopPage;
import support.LocalSite;
import utilities.Driver;

public class PracticeShopSteps {
    private PracticeShopPage page;
    @Given("the practice shop is open")
    public void open() { Driver.getDriver().get(LocalSite.url()); page = new PracticeShopPage(Driver.getDriver()); }
    @When("I sign in with {string} and {string}")
    public void login(String email, String password) { page.signIn(email, password); }
    @Then("the shop is visible")
    public void visible() { Assert.assertTrue("Shop must be visible", page.shopVisible()); }
    @Then("the login error is {string}")
    public void error(String expected) { Assert.assertEquals(expected, page.text("error")); Assert.assertTrue(page.shopHidden()); }
    @When("I add {int} items")
    public void add(int count) { page.add(count); }
    @When("I remove the items")
    public void remove() { page.remove(); }
    @Then("the cart has {int} items and total {string}")
    public void cart(int count, String total) {
        Assert.assertEquals(String.valueOf(count), page.text("cart"));
        Assert.assertEquals(total, page.text("total"));
    }
    @When("I check out")
    public void checkout() { page.checkout(); }
    @Then("the shop notice is {string}")
    public void notice(String expected) { Assert.assertEquals(expected, page.text("notice")); }
    @Then("the stock is {int} and order count is {int}")
    public void stock(int stock, int orders) {
        Assert.assertEquals(String.valueOf(stock), page.text("stock"));
        Assert.assertEquals(String.valueOf(orders), page.text("orders"));
    }
}
