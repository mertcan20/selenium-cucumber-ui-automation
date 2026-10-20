package support;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

/** A controlled browser fixture, not a production authentication/payment service. */
public final class LocalSite {
    private static final ThreadLocal<HttpServer> SERVERS = new ThreadLocal<>();
    private LocalSite() { }
    public static void start() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        byte[] html;
        try (var input = LocalSite.class.getResourceAsStream("/site/index.html")) {
            if (input == null) throw new IllegalStateException("Local fixture missing");
            html = input.readAllBytes();
        }
        server.createContext("/", exchange -> {
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
            exchange.sendResponseHeaders(200, html.length);
            try (var output = exchange.getResponseBody()) { output.write(html); }
        });
        server.start();
        SERVERS.set(server);
    }
    public static String url() { return "http://127.0.0.1:" + SERVERS.get().getAddress().getPort(); }
    public static void stop() {
        HttpServer server = SERVERS.get();
        try { if (server != null) server.stop(0); }
        finally { SERVERS.remove(); }
    }
}
