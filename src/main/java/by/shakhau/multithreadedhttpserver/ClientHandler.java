package by.shakhau.multithreadedhttpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;


public class ClientHandler implements Runnable {

    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                OutputStream output = socket.getOutputStream()) {

            String requestLine = reader.readLine();
            if (requestLine == null || requestLine.isBlank()) {
                return;
            }

            HttpRequest request = HttpRequest.parse(requestLine);
            while (reader.ready()) {
                reader.readLine();
            }

            if (!request.getMethod().equals("GET")) {
                HttpResponse.sendText(output, 405, "Method Not Allowed", "Only GET method is supported");
                return;
            }

            if (request.getPath().equals("/")) {
                HttpResponse.sendText(output, 200, "OK", "Hello from Custom Server");
                return;
            }

            String resource = request.getPath().substring(1);
            InputStream file = getClass().getClassLoader().getResourceAsStream(resource);
            if (file == null) {
                HttpResponse.sendText(output, 404, "Not Found", "404 Not Found");
                return;
            }

            byte[] bytes = file.readAllBytes();
            HttpResponse.sendBytes(
                    output, 200, "OK", ContentTypeResolver.getContentType(resource), bytes);
        } catch (Exception e) {
            try {
                HttpResponse.sendText(
                        socket.getOutputStream(),
                        500,
                        "Internal Server Error",
                        "500 Internal Server Error");
            } catch (IOException ignored) {
            }
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }
}
