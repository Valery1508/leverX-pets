package ru.leverx.pets.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import ru.leverx.pets.dao.PetDao;
import ru.leverx.pets.dto.PetDto;
import ru.leverx.pets.service.PetService;
import ru.leverx.pets.service.impl.PetServiceImpl;

import javax.servlet.ServletException;
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

@WebServlet("/pets")     //либо аннотация, либо в web.xml
public class PetServlet extends HttpServlet {

    private final static String CONTENT_TYPE = "application/json;charset=UTF-8";

    private PetDao petDao;
    private PetService petService;
    private ObjectMapper mapper;

    public void init() {
        petDao = new PetDao();
        petService = new PetServiceImpl();
        mapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        response.setContentType("application/json;charset=UTF-8");

        if (Objects.nonNull(request.getParameter("id"))) {
            //pw.println(petService.getPetById(Integer.parseInt(req.getParameter("id"))));
            String petByIdJSON = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(petService.getPetById(parseInt(request.getParameter("id"))));
            pw.println(petByIdJSON);
        } else {
            //pw.println(getPets(req, resp));
            //pw.println(petService.getAllPets());
            String allPetsJSON = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(petService.getAllPets());
            pw.println(allPetsJSON);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*try {
            deletePet(req, resp);
            pw.println("pet was deleted successfully");
        } catch (SQLException throwables) {
            /*throwables.printStackTrace();
            throw new ServletException(throwables);
        }*/
        PrintWriter pw = response.getWriter();
        response.setContentType(CONTENT_TYPE);

        String allPetJSON = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(petService.deletePetById(parseInt(request.getParameter("id"))));
        pw.println(allPetJSON);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        response.setContentType(CONTENT_TYPE);
        String requestData = request.getReader().lines().collect(joining());

        JSONObject obj = new JSONObject(requestData);
        PetDto petDto = new PetDto(
                obj.getString("name"),
                obj.getString("type"),
                obj.getLong("personId")
        );
        String addedPetJSON = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(petService.addPet(petDto));
        pw.println(addedPetJSON);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        response.setContentType(CONTENT_TYPE);
        String requestData = request.getReader().lines().collect(joining());

        JSONObject obj = new JSONObject(requestData);
        PetDto petDto = new PetDto(
                obj.getString("name"),
                obj.getString("type"),
                obj.getLong("personId")
        );
        String updatedPetJSON = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(petService.updatePet(parseLong(request.getParameter("id")), petDto));
        pw.println(updatedPetJSON);
    }
}