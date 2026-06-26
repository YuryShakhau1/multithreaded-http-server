package by.shakhau.multithreadedhttpserver;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpRequestTest {

    @Test
    void shouldParseRequestLine() {
        String rawRequest = """
                GET /index.html HTTP/1.1
                Host: localhost
                User-Agent: JUnit
                
                """;

        String requestLine = rawRequest.lines().findFirst().orElseThrow();

        HttpRequest request = HttpRequest.parse(requestLine);

        assertEquals("GET", request.getMethod());
        assertEquals("/index.html", request.getPath());
    }
}
