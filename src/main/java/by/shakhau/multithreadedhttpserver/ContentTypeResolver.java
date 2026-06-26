package by.shakhau.multithreadedhttpserver;

import java.util.HashMap;
import java.util.Map;

public class ContentTypeResolver {

    public static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";
    private static final Map<String, String> EXTENSION_CONTENT_TYPES = new HashMap<>();

    static {
        EXTENSION_CONTENT_TYPES.put(".html", "text/html; charset=UTF-8");
        EXTENSION_CONTENT_TYPES.put(".htm", "text/html; charset=UTF-8");
        EXTENSION_CONTENT_TYPES.put(".css", "text/css");
        EXTENSION_CONTENT_TYPES.put(".js", "application/javascript");
        EXTENSION_CONTENT_TYPES.put(".json", "application/json");
        EXTENSION_CONTENT_TYPES.put(".xml", "application/xml");
        EXTENSION_CONTENT_TYPES.put(".txt", "text/plain");
        EXTENSION_CONTENT_TYPES.put(".png", "image/png");
        EXTENSION_CONTENT_TYPES.put(".jpg", "image/jpeg");
        EXTENSION_CONTENT_TYPES.put(".jpeg", "image/jpeg");
        EXTENSION_CONTENT_TYPES.put(".gif", "image/gif");
        EXTENSION_CONTENT_TYPES.put(".svg", "image/svg+xml");
        EXTENSION_CONTENT_TYPES.put(".ico", "image/x-icon");
    }

    public static String getContentType(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) {
            return DEFAULT_CONTENT_TYPE;
        }

        String extension = fileName.substring(dotIndex + 1).toLowerCase();
        return EXTENSION_CONTENT_TYPES.getOrDefault(extension, DEFAULT_CONTENT_TYPE);
    }
}
