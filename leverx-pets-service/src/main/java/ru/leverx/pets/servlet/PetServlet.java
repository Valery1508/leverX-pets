package ru.leverx.pets.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.var;
import ru.leverx.pets.dao.PetDao;
import ru.leverx.pets.entity.Pet;
import ru.leverx.pets.entity.PetType;
import ru.leverx.pets.service.PetService;
import ru.leverx.pets.service.impl.PetServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@WebServlet("/pets")     //либо аннотация, либо в web.xml
public class PetServlet extends HttpServlet {

    private PetDao petDao;
    private PetService petService;

    public void init() {
        petDao = new PetDao();
        petService = new PetServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter pw = resp.getWriter();
        String action = req.getServletPath();
        /*switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertPet(req, resp);
                    break;
                case "/delete":
                    deletePet(req, resp);
                    break;
                case "/edit":
                    showEditForm(request, response);
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
        doPost(req, resp);
    }

    private void insertPet(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String name = request.getParameter("name");
        PetType type = PetType.valueOf(request.getParameter("type"));

        Pet newPet = new Pet(name, type);
        petDao.savePet(newPet);
        response.sendRedirect("list");
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


   /////////////////////////
    private List<Pet> getPets(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        return petDao.getAllPets();
    }
    private Pet getPetById(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        return petDao.getPetById(id);
    }
    private List<Pet> deletePet(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        petDao.deletePetById(id);
        return petDao.getAllPets();
    }
}