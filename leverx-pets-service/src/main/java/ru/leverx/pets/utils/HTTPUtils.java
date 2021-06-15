package ru.leverx.pets.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static java.util.stream.Collectors.joining;
import static ru.leverx.pets.utils.Constants.CONTENT_TYPE;

public class HTTPUtils {
    private static final ObjectWriter writer = new ObjectMapper().writerWithDefaultPrettyPrinter();

    public static void sendResponse(HttpServletResponse response, String responseJSON) throws IOException {
        response.setContentType(CONTENT_TYPE);

        PrintWriter responseBody = response.getWriter();
        responseBody.println(responseJSON);
    }

    public static void sendResponseError(HttpServletResponse response, int errorCode, String errorMessage) throws IOException {
        response.setContentType(CONTENT_TYPE);
        response.sendError(errorCode, errorMessage);
    }

    public static String toJSON(Object value) throws JsonProcessingException {
        return writer
                .writeValueAsString(value);
    }

    public static String getRequestBodyData(HttpServletRequest request) throws IOException {
        return request.getReader()
                .lines()
                .collect(joining());
    }
}
