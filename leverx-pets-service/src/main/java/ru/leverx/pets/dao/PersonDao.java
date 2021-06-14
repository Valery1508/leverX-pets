package ru.leverx.pets.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import ru.leverx.pets.entity.Person;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static ru.leverx.pets.exception.ExceptionMessages.SESSION_MESSAGE;

public class PersonDao {

    public Optional<Person> getPersonById(long id) {
        Transaction transaction = null;
        Person person;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            person = session.get(Person.class, id);

            transaction.commit();
        } catch (Exception e) {
            if (nonNull(transaction)) {
                transaction.rollback();
            }
            throw new TransactionException(SESSION_MESSAGE);
        }
        return Optional.ofNullable(person);
    }


    @SuppressWarnings("unchecked")
    public List<Person> getAllPerson() {
        Transaction transaction = null;
        List<Person> persons;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            persons = session.createQuery("from Person").getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (nonNull(transaction)) {
                transaction.rollback();
            }
            throw new TransactionException(SESSION_MESSAGE);
        }
        return persons;
    }

    public void deletePersonById(long id) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Person person = session.get(Person.class, id);
            if (nonNull(person)) {
                session.delete(person);
            }

            transaction.commit();
        } catch (Exception e) {
            if (nonNull(transaction)) {
                transaction.rollback();
            }
            throw new TransactionException(SESSION_MESSAGE);
        }
    }

    public Person savePerson(Person person) {
        Transaction transaction = null;
        Person savedPerson;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            long id = (long) session.save(person);
            savedPerson = session.get(Person.class, id);

            transaction.commit();
        } catch (Exception e) {
            if (nonNull(transaction)) {
                transaction.rollback();
            }
            throw new TransactionException(SESSION_MESSAGE);
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
            if (nonNull(transaction)) {
                transaction.rollback();
            }
            throw new TransactionException(SESSION_MESSAGE);
        }
    }
}