package by.shakhau.multithreadedhttpserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HttpResponse {

    public static void sendText(
            OutputStream output,
            int status,
            String statusText,
            String body
    ) throws IOException {

        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);

        String headers =
                "HTTP/1.1 " + status + " " + statusText + "\r\n" +
                        "Content-Type: text/plain; charset=UTF-8\r\n" +
                        "Content-Length: " + bytes.length + "\r\n" +
                        "Connection: close\r\n" +
                        "\r\n";

        output.write(headers.getBytes(StandardCharsets.UTF_8));
        output.write(bytes);
        output.flush();
    }

    public static void sendBytes(
            OutputStream output,
            int status,
            String statusText,
            String contentType,
            byte[] body) throws IOException {
        String headers =
                "HTTP/1.1 " + status + " " + statusText + "\r\n" +
                        "Content-Type: " + contentType + "\r\n" +
                        "Content-Length: " + body.length + "\r\n" +
                        "Connection: close\r\n" +
                        "\r\n";

        output.write(headers.getBytes(StandardCharsets.UTF_8));
        output.write(body);
        output.flush();
    }
}
