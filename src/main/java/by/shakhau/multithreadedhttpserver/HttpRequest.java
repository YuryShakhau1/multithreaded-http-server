package by.shakhau.multithreadedhttpserver;

import lombok.Getter;

@Getter
public class HttpRequest {

    private final String method;
    private final String path;

    public HttpRequest(String method, String path) {
        this.method = method;
        this.path = path;
    }

    public static HttpRequest parse(String requestLine) {
        String[] parts = requestLine.split(" ");
        String method = parts[0];
        String path = parts[1];
        return new HttpRequest(method, path);
    }
}
