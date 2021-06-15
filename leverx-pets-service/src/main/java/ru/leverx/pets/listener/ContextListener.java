package ru.leverx.pets.listener;

import ru.leverx.pets.dao.PersonDao;
import ru.leverx.pets.dao.PetDao;
import ru.leverx.pets.mapper.PersonMapper;
import ru.leverx.pets.mapper.PetMapper;
import ru.leverx.pets.service.PersonService;
import ru.leverx.pets.service.PetService;
import ru.leverx.pets.service.impl.PersonServiceImpl;
import ru.leverx.pets.service.impl.PetServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    private PetService petService;
    private PersonService personService;

    private PetDao petDao;
    private PersonDao personDao;

    private PetMapper petMapper;
    private PersonMapper personMapper;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        initDAOs();
        initMappers();
        initServices();

        ServletContext context = sce.getServletContext();

        context.setAttribute(PetService.class.getName(), petService);
        context.setAttribute(PersonService.class.getName(), personService);

        context.setAttribute(PetDao.class.getName(), petDao);
        context.setAttribute(PersonDao.class.getName(), personDao);

        context.setAttribute(PetMapper.class.getName(), petMapper);
        context.setAttribute(PersonMapper.class.getName(), personMapper);
    }

    public void initDAOs() {
        petDao = new PetDao();
        personDao = new PersonDao();
    }

    public void initMappers() {
        petMapper = new PetMapper();
        personMapper = new PersonMapper();
    }

    public void initServices() {
        personService = new PersonServiceImpl(personDao, personMapper);
        petService = new PetServiceImpl(petDao, petMapper, personService);
    }
}