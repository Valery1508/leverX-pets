package ru.leverx.pets.parser;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class UrlParser {
    private final String SLASH = "/";

    public static String getParsedUrl(HttpServletRequest request) {
        String stroka1 = request.getPathInfo(); // "/4"

        String result;
        if (Objects.isNull(stroka1) || stroka1.isEmpty()) {
            return null;
        } else {
            result = stroka1.substring(1);
        }
        return result;
    }
}
