package ru.leverx.pets.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONObject;
import ru.leverx.pets.dto.PetDto;
import ru.leverx.pets.parser.UrlCase;
import ru.leverx.pets.service.PetService;
import ru.leverx.pets.validator.DataValidator;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.util.stream.Collectors.joining;
import static ru.leverx.pets.exception.ExceptionMessages.WRONG_PATH_MESSAGE;
import static ru.leverx.pets.parser.UrlParser.parseUrl;
import static ru.leverx.pets.utils.Constants.CONTENT_TYPE;

@WebServlet(name = "PetServlet", value = "/pets/*")
public class PetServlet extends HttpServlet {

    private PetService petService;
    private ObjectWriter writer;

    @Override
    public void init() {
        ServletContext servletContext = getServletContext();
        petService = (PetService) servletContext.getAttribute(PetService.class.getName());
        writer = new ObjectMapper().writerWithDefaultPrettyPrinter();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> parsedUrl = parseUrl(request);

        PrintWriter responseBody = response.getWriter();
        response.setContentType(CONTENT_TYPE);

        if (parsedUrl.get(0).equals(UrlCase.ID.toString())) {
            String petByIdJSON = writer
                    .writeValueAsString(petService.getPetById(parseInt(parsedUrl.get(1))));
            responseBody.println(petByIdJSON);
        } else if (parsedUrl.get(0).equals(UrlCase.ALL.toString())) {
            String allPetsJSON = writer
                    .writeValueAsString(petService.getAllPets());
            responseBody.println(allPetsJSON);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> parsedUrl = parseUrl(request);

        PrintWriter responseBody = response.getWriter();
        response.setContentType(CONTENT_TYPE);

        if (parsedUrl.get(0).equals(UrlCase.ID.toString())) {
            petService.deletePetById(parseInt(parsedUrl.get(1)));
            responseBody.println("Pet with id = " + parsedUrl.get(1) + " was deleted.");
        } else {
            response.sendError(400, WRONG_PATH_MESSAGE);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter responseBody = response.getWriter();
        response.setContentType(CONTENT_TYPE);
        String requestData = request.getReader().lines().collect(joining());

        JSONObject obj = new JSONObject(requestData);
        PetDto petDto = new PetDto(
                obj.getString("name"),
                obj.getString("type"),
                obj.getLong("personId")
        );
        DataValidator.validateData(petDto);

        String addedPetJSON = writer
                .writeValueAsString(petService.addPet(petDto));
        responseBody.println(addedPetJSON);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> parsedUrl = parseUrl(request);

        PrintWriter responseBody = response.getWriter();
        response.setContentType(CONTENT_TYPE);

        if (parsedUrl.get(0).equals(UrlCase.ID.toString())) {
            String requestData = request.getReader().lines().collect(joining());
            JSONObject obj = new JSONObject(requestData);
            PetDto petDto = new PetDto(
                    obj.getString("name"),
                    obj.getString("type"),
                    obj.getLong("personId")
            );
            DataValidator.validateData(petDto);

            String updatedPetJSON = writer
                    .writeValueAsString(petService.updatePet(parseLong(parsedUrl.get(1)), petDto));
            responseBody.println(updatedPetJSON);
        } else if (parsedUrl.get(0).equals(UrlCase.ALL.toString())) {
            response.sendError(400, WRONG_PATH_MESSAGE);
        }
    }
}