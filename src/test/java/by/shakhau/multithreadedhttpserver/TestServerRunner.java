package by.shakhau.multithreadedhttpserver;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import static by.shakhau.multithreadedhttpserver.TestConst.PORT;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestServerRunner {

    private HttpServer server;

    @BeforeAll
    void startServer() {
        server = new HttpServer(PORT, 20);

        var thread = new Thread(() -> {
            try {
                server.start();
            } catch (Exception ignored) {
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    @AfterAll
    void stopServer() {
        server.stop();
    }
}
