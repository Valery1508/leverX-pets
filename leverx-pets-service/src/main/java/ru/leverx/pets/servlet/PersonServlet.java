package ru.leverx.pets.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import ru.leverx.pets.dao.PersonDao;
import ru.leverx.pets.dto.PersonRequestDto;
import ru.leverx.pets.service.PersonService;
import ru.leverx.pets.service.impl.PersonServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

import static java.lang.Long.parseLong;
import static java.util.stream.Collectors.joining;

@WebServlet(name = "PersonServlet", value = "/person")
public class PersonServlet extends HttpServlet {

    private final static String CONTENT_TYPE = "application/json;charset=UTF-8";

    private PersonDao personDao;
    private PersonService personService;
    private ObjectMapper mapper;

    public void init() {
        personDao = new PersonDao();
        personService = new PersonServiceImpl();
        mapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        response.setContentType(CONTENT_TYPE);

        if (Objects.nonNull(request.getParameter("id"))) {
            String personByIdJSON = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(personService.getPersonById(parseLong(request.getParameter("id"))));
            pw.println(personByIdJSON);
        } else {
            String allPersonJSON = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(personService.getAllPerson());
            pw.println(allPersonJSON);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        response.setContentType(CONTENT_TYPE);
        String requestData = request.getReader().lines().collect(joining());

        JSONObject obj = new JSONObject(requestData);
        PersonRequestDto personRequestDto = new PersonRequestDto(
                obj.getString("firstName"),
                obj.getString("lastName")
        );
        String addedPersonJSON = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(personService.addPerson(personRequestDto));
        pw.println(addedPersonJSON);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        response.setContentType(CONTENT_TYPE);

        String allPersonJSON = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(personService.deletePersonById(parseLong(request.getParameter("id"))));
        pw.println(allPersonJSON);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        response.setContentType(CONTENT_TYPE);
        String requestData = request.getReader().lines().collect(joining());

        JSONObject obj = new JSONObject(requestData);
        PersonRequestDto personRequestDto = new PersonRequestDto(
                obj.getString("firstName"),
                obj.getString("lastName")
        );
        String updatedPersonJSON = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(personService.updatePerson(parseLong(request.getParameter("id")), personRequestDto));
        pw.println(updatedPersonJSON);
    }
}
