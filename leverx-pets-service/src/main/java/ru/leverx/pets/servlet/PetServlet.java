package ru.leverx.pets.servlet;

import ru.leverx.pets.dao.PetDao;
import ru.leverx.pets.entity.Pet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/pets")     //либо аннотация, либо в web.xml
public class PetServlet extends HttpServlet {

    private PetDao petDao;

    public void init(){
        petDao = new PetDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        /*pw.println("<html>");
        pw.println("<h1>Hello! It's pet page :)</h1>");
        pw.println("</html>");*//*
        pw.println("hi, it is simple text");
        *//*String id = req.getParameter("id");
        pw.println("Id is: " + id);*//*
        pw.close();
        //super.doGet(req, resp);*/
        String action = req.getServletPath();
        try {
            switch (action) {
                /*case "/new":
                    showNewForm(request, response);
                    break;*/
                case "/insert":
                    insertPet(req, resp);
                    break;
                /*case "/delete":
                    deleteUser(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateUser(request, response);
                    break;*/
                default:
                    pw.println(getPets(req, resp));
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    private List<Pet> getPets(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        return petDao.getAllPets();

    }

    /*private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = userDao.getUser(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        request.setAttribute("user", existingUser);
        dispatcher.forward(request, response);

    }*/

    private void insertPet(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String name = request.getParameter("name");
        String type = /*PetType.valueOf(*/request.getParameter("type");

        Pet newPet = new Pet(name, type);
        petDao.savePet(newPet);
        response.sendRedirect("list");
    }

    /*private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");

        User user = new User(id, name, email, country);
        userDao.updateUser(user);
        response.sendRedirect("list");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userDao.deleteUser(id);
        response.sendRedirect("list");
    }*/
}