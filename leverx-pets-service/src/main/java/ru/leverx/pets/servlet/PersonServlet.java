package ru.leverx.pets.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONObject;
import ru.leverx.pets.dto.PersonRequestDto;
import ru.leverx.pets.parser.UrlCase;
import ru.leverx.pets.service.PersonService;
import ru.leverx.pets.validator.DataValidator;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static java.lang.Long.parseLong;
import static java.util.stream.Collectors.joining;
import static ru.leverx.pets.exception.ExceptionMessages.WRONG_PATH_MESSAGE;
import static ru.leverx.pets.parser.UrlParser.parseUrl;
import static ru.leverx.pets.utils.Constants.CONTENT_TYPE;

@WebServlet(name = "PersonServlet", value = "/person/*")
public class PersonServlet extends HttpServlet {

    private PersonService personService;
    private ObjectWriter writer;

    @Override
    public void init() {
        ServletContext servletContext = getServletContext();
        personService = (PersonService) servletContext.getAttribute(PersonService.class.getName());
        writer = new ObjectMapper().writerWithDefaultPrettyPrinter();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> parsedUrl = parseUrl(request);

        PrintWriter responseBody = response.getWriter();  //TODO
        response.setContentType(CONTENT_TYPE);

        if (parsedUrl.get(0).equals(UrlCase.ID.toString())) {
            String personByIdJSON = writer
                    .writeValueAsString(personService.getPersonById(parseLong(parsedUrl.get(1))));
            responseBody.println(personByIdJSON);
        } else if (parsedUrl.get(0).equals(UrlCase.ALL.toString())) {
            String allPersonJSON = writer
                    .writeValueAsString(personService.getAllPerson());
            responseBody.println(allPersonJSON);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter responseBody = response.getWriter();
        response.setContentType(CONTENT_TYPE);
        String requestData = request.getReader().lines().collect(joining());

        JSONObject obj = new JSONObject(requestData);
        PersonRequestDto personRequestDto = new PersonRequestDto(
                obj.getString("firstName"),
                obj.getString("lastName")
        );
        DataValidator.validateData(personRequestDto);

        String addedPersonJSON = writer
                .writeValueAsString(personService.addPerson(personRequestDto));
        responseBody.println(addedPersonJSON);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> parsedUrl = parseUrl(request);

        PrintWriter responseBody = response.getWriter();
        response.setContentType(CONTENT_TYPE);

        if (parsedUrl.get(0).equals(UrlCase.ID.toString())) {
            personService.deletePersonById(parseLong(parsedUrl.get(1)));
            responseBody.println("Person with id = " + parsedUrl.get(1) + " was deleted.");
        } else {
            response.sendError(400, WRONG_PATH_MESSAGE);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> parsedUrl = parseUrl(request);

        PrintWriter responseBody = response.getWriter();
        response.setContentType(CONTENT_TYPE);

        if (parsedUrl.get(0).equals(UrlCase.ID.toString())) {
            String requestData = request
                    .getReader()
                    .lines()
                    .collect(joining());

            JSONObject obj = new JSONObject(requestData);

            PersonRequestDto personRequestDto = new PersonRequestDto(
                    obj.getString("firstName"),
                    obj.getString("lastName")
            );
            DataValidator.validateData(personRequestDto);

            String updatedPersonJSON = writer
                    .writeValueAsString(personService.updatePerson(parseLong(parsedUrl.get(1)), personRequestDto));
            responseBody.println(updatedPersonJSON);
        } else if (parsedUrl.get(0).equals(UrlCase.ALL.toString())) {
            response.sendError(400, WRONG_PATH_MESSAGE);
        }
    }
}