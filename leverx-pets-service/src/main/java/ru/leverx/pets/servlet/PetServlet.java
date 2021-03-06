package ru.leverx.pets.servlet;

import org.json.JSONObject;
import ru.leverx.pets.dto.PetDto;
import ru.leverx.pets.parser.UrlCase;
import ru.leverx.pets.service.PetService;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static ru.leverx.pets.exception.ExceptionMessages.WRONG_PATH_MESSAGE;
import static ru.leverx.pets.parser.UrlParser.parseUrl;
import static ru.leverx.pets.servlet.ServletNames.PET_SERVLET_NAME;
import static ru.leverx.pets.servlet.ServletNames.PET_SERVLET_VALUE;
import static ru.leverx.pets.utils.HTTPUtils.getRequestBodyData;
import static ru.leverx.pets.utils.HTTPUtils.sendResponse;
import static ru.leverx.pets.utils.HTTPUtils.sendResponseError;
import static ru.leverx.pets.utils.HTTPUtils.toJSON;
import static ru.leverx.pets.validator.DataValidator.validateData;

@WebServlet(name = PET_SERVLET_NAME, value = PET_SERVLET_VALUE)
public class PetServlet extends HttpServlet {

    private PetService petService;

    @Override
    public void init() {
        ServletContext servletContext = getServletContext();
        petService = (PetService) servletContext.getAttribute(PetService.class.getName());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> parsedUrl = parseUrl(request);

        if (parsedUrl.get(0).equals(UrlCase.ID.toString())) {
            String petByIdJSON = toJSON(petService.getPetById(parseInt(parsedUrl.get(1))));
            sendResponse(response, petByIdJSON);
        } else if (parsedUrl.get(0).equals(UrlCase.ALL.toString())) {
            String allPetsJSON = toJSON(petService.getAllPets());
            sendResponse(response, allPetsJSON);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> parsedUrl = parseUrl(request);

        if (parsedUrl.get(0).equals(UrlCase.ID.toString())) {
            petService.deletePetById(parseInt(parsedUrl.get(1)));
            sendResponse(response, "Pet with id = " + parsedUrl.get(1) + " was deleted.");
        } else {
            sendResponseError(response, 400, WRONG_PATH_MESSAGE);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String requestData = getRequestBodyData(request);

        JSONObject obj = new JSONObject(requestData);
        PetDto petDto = new PetDto(
                obj.getString("name"),
                obj.getString("type"),
                obj.getLong("personId")
        );
        validateData(petDto);

        String addedPetJSON = toJSON(petService.addPet(petDto));

        sendResponse(response, addedPetJSON);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> parsedUrl = parseUrl(request);

        if (parsedUrl.get(0).equals(UrlCase.ID.toString())) {
            String requestData = getRequestBodyData(request);

            JSONObject obj = new JSONObject(requestData);
            PetDto petDto = new PetDto(
                    obj.getString("name"),
                    obj.getString("type"),
                    obj.getLong("personId")
            );
            validateData(petDto);

            String updatedPetJSON = toJSON(petService.updatePet(parseLong(parsedUrl.get(1)), petDto));

            sendResponse(response, updatedPetJSON);
        } else if (parsedUrl.get(0).equals(UrlCase.ALL.toString())) {
            sendResponseError(response, 400, WRONG_PATH_MESSAGE);
        }
    }
}