package ru.leverx.pets.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import ru.leverx.pets.entity.Pet;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static ru.leverx.pets.exception.ExceptionMessages.SESSION_MESSAGE;

public class PetDao {

    public Pet savePet(Pet pet) {
        Transaction transaction = null;
        Pet savedPet;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            long id = (long) session.save(pet);
            savedPet = session.get(Pet.class, id);

            transaction.commit();
        } catch (Exception e) {
            if (nonNull(transaction)) {
                transaction.rollback();
            }
            throw new TransactionException(SESSION_MESSAGE);
        }
        return savedPet;
    }

    public void updatePet(Pet pet) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.update(pet);

            transaction.commit();
        } catch (Exception e) {
            if (nonNull(transaction)) {
                transaction.rollback();
            }
            throw new TransactionException(SESSION_MESSAGE);
        }
    }

    public void deletePetById(long id) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Pet pet = session.get(Pet.class, id);
            if (nonNull(pet)) {
                session.delete(pet);
            }

            transaction.commit();
        } catch (Exception e) {
            if (nonNull(transaction)) {
                transaction.rollback();
            }
            throw new TransactionException(SESSION_MESSAGE);
        }
    }

    public Optional<Pet> getPetById(long id) {
        Transaction transaction = null;
        Pet pet;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            pet = session.get(Pet.class, id);

            transaction.commit();
        } catch (Exception e) {
            if (nonNull(transaction)) {
                transaction.rollback();
            }
            throw new TransactionException(SESSION_MESSAGE);
        }
        return Optional.ofNullable(pet);
    }

    @SuppressWarnings("unchecked")
    public List<Pet> getAllPets() {
        Transaction transaction = null;
        List<Pet> pets;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            pets = session.createQuery("from Pet").getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (nonNull(transaction)) {
                transaction.rollback();
            }
            throw new TransactionException(SESSION_MESSAGE);
        }
        return pets;
    }
}