package ru.leverx.pets.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.leverx.pets.entity.Person;

import java.util.List;

public class PersonDao {

    public Person getPersonById(long id) {
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
    }

    @SuppressWarnings("unchecked")
    public List<Person> getAllPerson() {

        Transaction transaction = null;
        List<Person> persons = null;
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

}
