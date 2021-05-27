package ru.leverx.pets.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.leverx.pets.dao.PersonDao;
import ru.leverx.pets.service.PersonService;
import ru.leverx.pets.service.impl.PersonServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

@WebServlet(name = "PersonServlet", value = "/person")
public class PersonServlet extends HttpServlet {
    private PersonDao personDao;
    private PersonService personService;

    public void init() {
        personDao = new PersonDao();
        personService = new PersonServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        response.setContentType("application/json;charset=UTF-8");
        ObjectMapper mapper = new ObjectMapper();

        if (Objects.nonNull(request.getParameter("id"))) {
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(personService.getPersonById(Integer.parseInt(request.getParameter("id"))));
            pw.println(jsonString);
        } else {
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(personService.getAllPerson());
            pw.println(jsonString);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
