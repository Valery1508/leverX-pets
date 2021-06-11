package ru.leverx.pets.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.leverx.pets.entity.Pet;

import java.util.List;

import static java.util.Collections.emptyList;

public class PetDao {

    public void savePet(Pet pet) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(pet);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updatePet(Pet pet) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(pet);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deletePetById(long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Pet pet = session.get(Pet.class, id);
            if (pet != null) {
                session.delete(pet);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public /*Optional*/Pet getPetById(long id) {
        Transaction transaction = null;
        Pet pet = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            pet = session.get(Pet.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return pet;
    }

    @SuppressWarnings("unchecked")
    public List<Pet> getAllPets() {

        Transaction transaction = null;
        List<Pet> pets = emptyList();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            pets = session.createQuery("from Pet").getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return pets;
    }
}