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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter pw = resp.getWriter();
        String action = req.getServletPath();
        /*switch (action) {
                case "/insert":
                    insertPet(req, resp);
                    break;
                case "/delete":
                    deletePet(req, resp);
                    break;
                case "/update":
                    updateUser(request, response);
                    break;*/

        resp.setContentType("application/json;charset=UTF-8");

        ObjectMapper mapper = new ObjectMapper();

        if (Objects.nonNull(req.getParameter("id"))) {
            //pw.println(petService.getPetById(Integer.parseInt(req.getParameter("id"))));
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(petService.getPetById(Integer.parseInt(req.getParameter("id"))));
            pw.println(jsonString);
        } else {
            //pw.println(getPets(req, resp));
            //pw.println(petService.getAllPets());
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(petService.getAllPets());
            pw.println(jsonString);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter pw = resp.getWriter();
        /*try {
            deletePet(req, resp);
            pw.println("pet was deleted successfully");
        } catch (SQLException throwables) {
            /*throwables.printStackTrace();
            throw new ServletException(throwables);
        }*/
        pw.println(petService.deletePetById(Integer.parseInt(req.getParameter("id"))));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        resp.setContentType(CONTENT_TYPE);
        String requestData = req.getReader().lines().collect(joining());

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

    /*private void updatePet(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");

        User user = new User(id, name, email, country);
        userDao.updateUser(user);
        response.sendRedirect("list");
    }*/
}