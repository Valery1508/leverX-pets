package ru.leverx.pets.parser;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class UrlParser {

    public static String getParsedUrl(HttpServletRequest request) {
        String requestData = request.getPathInfo();

        String result;
        if (Objects.isNull(requestData) || requestData.isEmpty()) {
            return null;
        } else {
            result = requestData.substring(1);
        }
        return result;
    }
}
