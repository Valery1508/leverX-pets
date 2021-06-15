package ru.leverx.pets.parser;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class UrlParser {

    public static List<String> parseUrl(HttpServletRequest request) {
        String requestData = request.getPathInfo();

        List<String> result = new java.util.ArrayList<>(emptyList());
        if (StringUtils.isBlank(requestData)) {
            result.add(UrlCase.ALL.toString());
        } else if (isNumeric(requestData.substring(1))) {
            result.add(UrlCase.ID.toString());
            result.add(requestData.substring(1));
        }
        return result;
    }
}
