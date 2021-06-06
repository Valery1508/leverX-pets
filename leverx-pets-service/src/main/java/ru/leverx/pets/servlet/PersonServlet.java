package ru.leverx.pets.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import ru.leverx.pets.dto.PersonRequestDto;
import ru.leverx.pets.service.PersonService;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

import static java.lang.Long.parseLong;
import static java.util.stream.Collectors.joining;
import static ru.leverx.pets.Constants.CONTENT_TYPE;
import static ru.leverx.pets.Constants.WRONG_PATH_MESSAGE;
import static ru.leverx.pets.parser.UrlParser.getParsedUrl;
import static ru.leverx.pets.validator.DataValidator.validateData;

@WebServlet(name = "PersonServlet", value = "/person/*")    //http://localhost:8080/person
public class PersonServlet extends HttpServlet {

    private PersonService personService;
    private ObjectMapper mapper;

    @Override
    public void init() {
        ServletContext servletContext = getServletContext();
        personService = (PersonService) servletContext.getAttribute(PersonService.class.getName());
        mapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String parsedUrl = getParsedUrl(request);

        PrintWriter out = response.getWriter();
        response.setContentType(CONTENT_TYPE);

        if (Objects.nonNull(parsedUrl)) {
            String personByIdJSON = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(personService.getPersonById(parseLong(parsedUrl)));
            out.println(personByIdJSON);
        } else {
            String allPersonJSON = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(personService.getAllPerson());
            out.println(allPersonJSON);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType(CONTENT_TYPE);
        String requestData = request.getReader().lines().collect(joining());

        JSONObject obj = new JSONObject(requestData);
        PersonRequestDto personRequestDto = new PersonRequestDto(
                obj.getString("firstName"),
                obj.getString("lastName")
        );
        validateData(personRequestDto);

        String addedPersonJSON = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(personService.addPerson(personRequestDto));
        out.println(addedPersonJSON);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String parsedUrl = getParsedUrl(request);

        PrintWriter out = response.getWriter();
        response.setContentType(CONTENT_TYPE);

        if (Objects.nonNull(parsedUrl)) {
            String allPersonJSON = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(personService.deletePersonById(parseLong(parsedUrl)));
            out.println(allPersonJSON);
        } else {
            response.sendError(400, WRONG_PATH_MESSAGE);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String parsedUrl = getParsedUrl(request);

        PrintWriter out = response.getWriter();
        response.setContentType(CONTENT_TYPE);

        if (Objects.nonNull(parsedUrl)){
            String requestData = request.getReader().lines().collect(joining());
            JSONObject obj = new JSONObject(requestData);
            PersonRequestDto personRequestDto = new PersonRequestDto(
                    obj.getString("firstName"),
                    obj.getString("lastName")
            );
            validateData(personRequestDto);

            String updatedPersonJSON = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(personService.updatePerson(parseLong(parsedUrl), personRequestDto));
            out.println(updatedPersonJSON);
        } else {
            response.sendError(400, WRONG_PATH_MESSAGE);
        }
    }
}