package ru.leverx.pets.servlet;

import org.json.JSONObject;
import ru.leverx.pets.dto.PersonRequestDto;
import ru.leverx.pets.dto.PersonResponseDto;
import ru.leverx.pets.parser.UrlCase;
import ru.leverx.pets.service.PersonService;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static java.lang.Long.parseLong;
import static ru.leverx.pets.exception.ExceptionMessages.WRONG_PATH_MESSAGE;
import static ru.leverx.pets.parser.UrlParser.parseUrl;
import static ru.leverx.pets.servlet.ServletNames.PERSON_SERVLET_NAME;
import static ru.leverx.pets.servlet.ServletNames.PERSON_SERVLET_VALUE;
import static ru.leverx.pets.utils.HTTPUtils.getRequestBodyData;
import static ru.leverx.pets.utils.HTTPUtils.sendResponse;
import static ru.leverx.pets.utils.HTTPUtils.sendResponseError;
import static ru.leverx.pets.utils.HTTPUtils.toJSON;
import static ru.leverx.pets.validator.DataValidator.validateData;

@WebServlet(name = PERSON_SERVLET_NAME, value = PERSON_SERVLET_VALUE)
public class PersonServlet extends HttpServlet {

    private PersonService personService;

    @Override
    public void init() {
        ServletContext servletContext = getServletContext();
        personService = (PersonService) servletContext.getAttribute(PersonService.class.getName());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> parsedUrl = parseUrl(request);

        if (parsedUrl.get(0).equals(UrlCase.ID.toString())) {
            PersonResponseDto personById = personService.getPersonById(parseLong(parsedUrl.get(1)));
            String personByIdJSON = toJSON(personById);
            sendResponse(response, personByIdJSON);
        } else if (parsedUrl.get(0).equals(UrlCase.ALL.toString())) {
            String allPersonJSON = toJSON(personService.getAllPerson());
            sendResponse(response, allPersonJSON);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestData = getRequestBodyData(request);

        JSONObject obj = new JSONObject(requestData);
        PersonRequestDto personRequestDto = new PersonRequestDto(
                obj.getString("firstName"),
                obj.getString("lastName")
        );
        validateData(personRequestDto);

        String addedPersonJSON = toJSON(personService.addPerson(personRequestDto));

        sendResponse(response, addedPersonJSON);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> parsedUrl = parseUrl(request);

        if (parsedUrl.get(0).equals(UrlCase.ID.toString())) {
            personService.deletePersonById(parseLong(parsedUrl.get(1)));
            sendResponse(response, "Person with id = " + parsedUrl.get(1) + " was deleted.");
        } else {
            sendResponseError(response, 400, WRONG_PATH_MESSAGE);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> parsedUrl = parseUrl(request);

        if (parsedUrl.get(0).equals(UrlCase.ID.toString())) {
            String requestData = getRequestBodyData(request);

            JSONObject obj = new JSONObject(requestData);

            PersonRequestDto personRequestDto = new PersonRequestDto(
                    obj.getString("firstName"),
                    obj.getString("lastName")
            );
            validateData(personRequestDto);

            String updatedPersonJSON = toJSON(personService.updatePerson(parseLong(parsedUrl.get(1)), personRequestDto));

            sendResponse(response, updatedPersonJSON);
        } else if (parsedUrl.get(0).equals(UrlCase.ALL.toString())) {   // поменять местами
            sendResponseError(response, 400, WRONG_PATH_MESSAGE);
        }
    }
}