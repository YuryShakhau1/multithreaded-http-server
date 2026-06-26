package by.shakhau.multithreadedhttpserver;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static by.shakhau.multithreadedhttpserver.TestConst.PORT;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LargePayloadTest {

    private HttpServer server;

    @BeforeAll
    void startServer() {
        server = new HttpServer(PORT, 10);

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

    @Test
    void shouldReadLargeResource() throws Exception {
        try (var socket = new Socket("localhost", PORT)) {
            OutputStream output = socket.getOutputStream();

            output.write("""
                    GET /large.txt HTTP/1.1
                    Host: localhost
                    
                    """.getBytes());

            output.flush();
            InputStream input = socket.getInputStream();
            byte[] bytes = input.readAllBytes();

            assertTrue(bytes.length > 1024 * 1024);
        }
    }
}
