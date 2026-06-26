package by.shakhau.multithreadedhttpserver;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import static by.shakhau.multithreadedhttpserver.TestConst.PORT;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HttpServerStatusTest extends TestServerRunner {

    @Test
    void shouldReturn200ForRoot() throws Exception {
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
        }
    }

    @Test
    void shouldReturn404ForMissingResource() throws Exception {
        try (var socket = new Socket("localhost", PORT)) {
            OutputStream output = socket.getOutputStream();

            output.write("""
                    GET /missing.html HTTP/1.1
                    Host: localhost
                    
                    """.getBytes());

            output.flush();

            var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String status = reader.readLine();

            assertTrue(status.contains("404"));
        }
    }
}
