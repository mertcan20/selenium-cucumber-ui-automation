package runner;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.junit.*;
import org.openqa.selenium.By;
import pages.N11Page;
import pages.N11RegistrationPage;
import support.RegistrationUser;
import utilities.Driver;

import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/** Exercises our page objects against a controlled HTTP fixture, NOT live N11. */
public class N11JourneyTestRunner {
    private HttpServer server;
    private final Map<String, String> registered = new HashMap<>();
    private final AtomicInteger loginRequests = new AtomicInteger();

    @Before
    public void start() throws Exception {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/", this::handle);
        server.start();
    }

    @After
    public void cleanup() {
        try { Driver.closeDriver(); }
        finally { if (server != null) server.stop(0); }
    }

    @Test
    public void registeredUserCanLoginInFreshBrowserAndWrongPasswordIsRejected() {
        RegistrationUser user = new RegistrationUser("Demo", "Tester", "journey@example.test", "5550000000", "DemoTest123");
        var registration = new N11RegistrationPage(Driver.getDriver());
        registration.open(url());
        registration.fill(user);
        Assert.assertTrue("Registration email must be the scenario-owned email",
            user.email().equals(Driver.getDriver().findElement(By.id("email")).getAttribute("value")));
        Assert.assertFalse("Page object must not accept agreements automatically",
            Driver.getDriver().findElement(By.id("agreement")).isSelected());
        Assert.assertTrue("Filling alone must not create an account", registered.isEmpty());

        // Simulates the operator only on this non-binding, local fixture.
        Driver.getDriver().findElement(By.id("agreement")).click();
        Driver.getDriver().findElement(By.id("register")).click();
        new N11Page().waitForAuthenticatedAccount(Duration.ofSeconds(5));

        Driver.closeDriver();
        Driver.getDriver().get(url() + "/account");
        Assert.assertTrue("A fresh browser must not inherit the registration session",
            Driver.getDriver().getCurrentUrl().endsWith("/giris-yap"));
        N11Page login = new N11Page();
        login.enterEmail(user.email());
        login.enterPassword(user.password());
        login.clickLoginButton();
        login.waitForAuthenticatedAccount(Duration.ofSeconds(5));
        Assert.assertEquals("Exactly one credential submission expected", 1, loginRequests.get());

        Driver.closeDriver();
        Driver.getDriver().get(url() + "/giris-yap");
        N11Page rejectedLogin = new N11Page();
        rejectedLogin.enterEmail(user.email());
        rejectedLogin.enterPassword("WrongTest123");
        rejectedLogin.clickLoginButton();
        Assert.assertEquals("Invalid credentials", Driver.getDriver().findElement(By.id("error")).getText());
        Assert.assertTrue("Wrong password must not open an authenticated menu",
            Driver.getDriver().findElements(By.cssSelector("a[title='Hesabım']")).isEmpty());
    }

    private String url() { return "http://127.0.0.1:" + server.getAddress().getPort(); }

    private void handle(HttpExchange exchange) throws java.io.IOException {
        String path = exchange.getRequestURI().getPath();
        switch (path) {
            case "/uye-ol" -> html(exchange, """
                <html><meta charset="utf-8"><form action="/register" method="post">
                <input id="firstName" name="firstName"><input id="lastName" name="lastName">
                <input id="email" name="email"><input id="gsmNumber" name="gsmNumber">
                <input id="password" name="password" type="password">
                <label><input id="agreement" name="agreement" type="checkbox" required>Local fixture consent only</label>
                <button id="register">Register</button></form></html>
                """);
            case "/register" -> {
                Map<String, String> form = form(exchange);
                if (!form.containsKey("agreement")) { html(exchange, "Missing consent"); return; }
                registered.putAll(form);
                exchange.getResponseHeaders().set("Set-Cookie", "session=fixture; Path=/; HttpOnly");
                redirect(exchange, "/account");
            }
            case "/login" -> {
                Map<String, String> form = form(exchange);
                loginRequests.incrementAndGet();
                if (!registered.isEmpty() && registered.get("email").equals(form.get("email"))
                    && registered.get("password").equals(form.get("password"))) {
                    exchange.getResponseHeaders().set("Set-Cookie", "session=fixture; Path=/; HttpOnly");
                    redirect(exchange, "/account");
                } else html(exchange, "<html><p id='error'>Invalid credentials</p></html>");
            }
            case "/account" -> {
                String cookie = exchange.getRequestHeaders().getFirst("Cookie");
                if (cookie == null || !cookie.contains("session=fixture")) redirect(exchange, "/giris-yap");
                else html(exchange, "<html><meta charset='utf-8'><a href='/account' title='Hesabım'>Hesabım</a> <a href='/logout' title='Çıkış Yap'>Çıkış Yap</a></html>");
            }
            default -> html(exchange, """
                <html><meta charset="utf-8"><form action="/login" method="post" id="form">
                <input id="email" name="email"><input id="password" name="password" type="password" hidden>
                <button aria-label="Giriş Yap" id="login">Giriş Yap</button></form>
                <script>document.getElementById('form').onsubmit=e=>{
                const field=document.getElementById('password');if(field.hidden){e.preventDefault();field.hidden=false;}};</script></html>
                """);
        }
    }

    private Map<String, String> form(HttpExchange exchange) throws java.io.IOException {
        Map<String, String> result = new HashMap<>();
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        for (String pair : body.split("&")) {
            String[] parts = pair.split("=", 2);
            result.put(URLDecoder.decode(parts[0], StandardCharsets.UTF_8),
                parts.length > 1 ? URLDecoder.decode(parts[1], StandardCharsets.UTF_8) : "");
        }
        return result;
    }

    private void html(HttpExchange exchange, String html) throws java.io.IOException {
        byte[] bytes = html.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
        exchange.sendResponseHeaders(200, bytes.length);
        try (var output = exchange.getResponseBody()) { output.write(bytes); }
    }

    private void redirect(HttpExchange exchange, String location) throws java.io.IOException {
        exchange.getResponseHeaders().set("Location", location);
        exchange.sendResponseHeaders(303, -1);
        exchange.close();
    }
}
