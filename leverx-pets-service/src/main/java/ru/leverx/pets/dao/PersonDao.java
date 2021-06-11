package ru.leverx.pets.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.leverx.pets.dto.PersonRequestDto;
import ru.leverx.pets.entity.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static java.util.Collections.emptyList;

public class PersonDao {
    //TODO around pattern

   /* private final EntityManager entityManager;

    public PersonDao() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit");   //TODO rename in persistence.xml
        this.entityManager = emf.createEntityManager();
    }

    public Person savePerson(Person person){  //TODO "save" & "Optional"
        entityManager.getTransaction().begin();

        entityManager.persist(person);

        entityManager.getTransaction().commit();
        //TODO rollback

        return person;  //TODO it is void in docs
    }

    public Person getPersonById(long id) {
        entityManager.getTransaction().begin();

        Person foundPerson = entityManager.find(Person.class, id);
entityManager.detach(foundPerson);
        entityManager.getTransaction().commit();
        //TODO rollback

        return foundPerson;
    }

    public List<Person> getAllPerson() {
        entityManager.getTransaction().begin();

        List<Person> foundPerson = entityManager.createQuery("FROM Person", Person.class)
                .getResultList();

        entityManager.getTransaction().commit();


        return foundPerson;

    }

    public void deletePersonById(long id) {
        entityManager.getTransaction().begin();

        Person foundPerson = entityManager.find(Person.class, id);
        entityManager.remove(foundPerson);

        entityManager.getTransaction().commit();

        //return foundPerson;
    }

    public void updatePerson(Person person) {

    }*/

    public Person getPersonById(long id) {  //возвращать Optional
        Transaction transaction = null;
        Person person = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            person = session.get(Person.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return person;
        //return objMethd("getById", id);
    }



    @SuppressWarnings("unchecked")
    public List<Person> getAllPerson() {

        Transaction transaction = null;
        List<Person> persons = emptyList();    //emptyList вместо null
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            persons = session.createQuery("from Person").getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return persons;
    }

    public void deletePersonById(long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Person person = session.get(Person.class, id);
            if (person != null) {
                session.delete(person);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Person savePerson(Person person) { //вернуть человека,а не воид
        Transaction transaction = null;
        Person savedPerson = null;;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            long id = (long) session.save(person);
            savedPerson = session.get(Person.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return savedPerson;
    }

    public void updatePerson(Person person) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(person);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}

/*public Person objMethd(String caseS, long id){
        Transaction transaction = null;
        Person person = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            switch (caseS){
                case "save":
                    func(person);
                    break;
                case "getById":
                    consumer(person, id);
                    break;
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return person;
    }*/

// test
    /*Consumer<Object> consumer = (obj) -> {
        Session::save;
        //Session::save(o.);
            Transaction transaction = null;
            Person person = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                person = session.x(Object.class);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
            return person;
    };*/

    /*public void func(Object object){
        Consumer<Session> consumer = (s) -> {
            s.save(object);
            //Session::save(o.);
            *//*Transaction transaction = null;
            Person person = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                person = session.x(Object.class);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
            return person;*//*
        };
    }*/

    /*public void func1(Object object, long id){
        Consumer<Session> consumer = (s) -> {
            s.get(String.valueOf(object), id);
            //Session::save(o.);
            *//*Transaction transaction = null;
            Person person = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                person = session.x(Object.class);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
            return person;*//*
        };
    }*/