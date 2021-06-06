package ru.leverx.pets.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import ru.leverx.pets.dto.PetDto;
import ru.leverx.pets.service.PetService;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.util.stream.Collectors.joining;
import static ru.leverx.pets.parser.UrlParser.getParsedUrl;
import static ru.leverx.pets.Constants.CONTENT_TYPE;
import static ru.leverx.pets.Constants.WRONG_PATH_MESSAGE;
import static ru.leverx.pets.validator.DataValidator.validateData;

@WebServlet(name = "PetServlet", value = "/pets/*")  //http://localhost:8080/pets
public class PetServlet extends HttpServlet {

    private PetService petService;
    private ObjectMapper mapper;

    @Override
    public void init() {
        ServletContext servletContext = getServletContext();
        petService = (PetService) servletContext.getAttribute(PetService.class.getName());
        mapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String parsedUrl = getParsedUrl(request);

        PrintWriter out = response.getWriter();
        response.setContentType(CONTENT_TYPE);

        if (Objects.nonNull(parsedUrl)) {
            String petByIdJSON = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(petService.getPetById(parseInt(parsedUrl)));
            out.println(petByIdJSON);
        } else {
            String allPetsJSON = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(petService.getAllPets());
            out.println(allPetsJSON);
        }

        /*if (Objects.nonNull(request.getParameter("id"))) {
            String petByIdJSON = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(petService.getPetById(parseInt(request.getParameter("id"))));
            pw.println(petByIdJSON);
        } else {
            String allPetsJSON = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(petService.getAllPets());
            pw.println(allPetsJSON);
        }*/
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String parsedUrl = getParsedUrl(request);

        PrintWriter out = response.getWriter();
        response.setContentType(CONTENT_TYPE);

        if (Objects.nonNull(parsedUrl)) {
            String allPetJSON = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(petService.deletePetById(parseInt(parsedUrl)));
            out.println(allPetJSON);
        } else {
            response.sendError(400, WRONG_PATH_MESSAGE);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType(CONTENT_TYPE);
        String requestData = request.getReader().lines().collect(joining());

        JSONObject obj = new JSONObject(requestData);
        PetDto petDto = new PetDto(
                obj.getString("name"),
                obj.getString("type"),
                obj.getLong("personId")
        );
        validateData(petDto);

        String addedPetJSON = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(petService.addPet(petDto));
        out.println(addedPetJSON);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String parsedUrl = getParsedUrl(request);

        PrintWriter out = response.getWriter();
        response.setContentType(CONTENT_TYPE);

        if (Objects.nonNull(parsedUrl)) {
            String requestData = request.getReader().lines().collect(joining());
            JSONObject obj = new JSONObject(requestData);
            PetDto petDto = new PetDto(
                    obj.getString("name"),
                    obj.getString("type"),
                    obj.getLong("personId")
            );
            validateData(petDto);

            String updatedPetJSON = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(petService.updatePet(parseLong(parsedUrl), petDto));
            out.println(updatedPetJSON);
        } else {
            response.sendError(400, WRONG_PATH_MESSAGE);
        }
    }
}