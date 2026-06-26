package by.shakhau.multithreadedhttpserver;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static by.shakhau.multithreadedhttpserver.TestConst.PORT;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HttpServerConcurrencyTest extends TestServerRunner {

    @Test
    void shouldHandle50ConcurrentRequests() throws Exception {
        int requests = 50;
        var latch = new CountDownLatch(requests);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int i = 0; i < requests; i++) {
            futures.add(
                    CompletableFuture.runAsync(() -> {
                        try (var socket = new Socket("localhost", PORT)) {
                            OutputStream output = socket.getOutputStream();

                            output.write("""
                                    GET / HTTP/1.1
                                    Host: localhost
                                    
                                    """.getBytes());

                            output.flush();

                            var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            String status = reader.readLine();

                            assertTrue(status.contains("200"));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        latch.countDown();
                    })
            );
        }

        latch.await(10, TimeUnit.SECONDS);

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }
}
