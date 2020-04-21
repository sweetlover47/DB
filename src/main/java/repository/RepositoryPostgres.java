package repository;

import models.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

public class RepositoryPostgres implements Repository {
    EntityManagerFactory emf;

    public RepositoryPostgres() {
        emf = Persistence.createEntityManagerFactory("models");
    }

    @Override
    public List<Excursion> getExcursions() {
        EntityManager entityManager = emf.createEntityManager();
        // entityManager.getTransaction().begin();
        List<Excursion> list = entityManager
                .createQuery("select e from Excursion e", Excursion.class)
                .getResultList();
        entityManager.close();
        return list;
    }

    @Override
    public Tourist getTouristByPassport(String passport) {
        EntityManager entityManager = emf.createEntityManager();
        Tourist tourist = entityManager
                .createQuery("select t from tourist t where t.passport = :passport ", Tourist.class)
                .setParameter("passport", passport)
                .getResultStream()
                .findFirst()
                .orElse(null);
        entityManager.close();
        return tourist;
    }

    @Override
    public void putNewTourist(String name, String passport, String sex, int age) {
        Tourist tourist = new Tourist();
        tourist.setName(name);
        tourist.setPassport(passport);
        tourist.setSex(sex);
        tourist.setAge(age);
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(tourist);
        entityManager.getTransaction().commit();
        entityManager.close();
        return;
    }

    @Override
    public int updateTourist(Long id, String name, int age, String passport, String sex) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            if (!isUniquePassport(id, passport, entityManager))
                throw new NoSuchElementException();
            Tourist newTourist = new Tourist();
            newTourist.setId(id);
            newTourist.setName(name);
            newTourist.setAge(age);
            newTourist.setPassport(passport);
            newTourist.setSex(sex);
            entityManager.getTransaction().begin();
            entityManager.merge(newTourist);
            entityManager.getTransaction().commit();
        } catch (NoSuchElementException e) {
            entityManager.getTransaction().rollback();
            entityManager.close();
            return -1;
        }
        entityManager.close();
        return 0;
    }

    @Override
    public Tourist getTouristById(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        Tourist tourist = entityManager
                .createQuery("select t from tourist t where id = :id", Tourist.class)
                .setParameter("id", id)
                .getSingleResult();
        entityManager.close();
        return tourist;
    }

    @Override
    public void addNewCargoTrip(Long id, String statement, String country, Timestamp dateIn, Timestamp dateOut) {
        EntityManager entityManager = emf.createEntityManager();
        Cargo cargo = entityManager
                .createQuery("select c from Cargo c where c.statement.id = :statement", Cargo.class)
                .setParameter("statement", Integer.parseInt(statement))
                .getSingleResult();
        entityManager.getTransaction().begin();
        Tourist tourist = entityManager.find(Tourist.class, id);
        tourist.getCargos().add(cargo);
        tourist = entityManager.merge(tourist);
        entityManager.getTransaction().commit();
        entityManager.close();


        Trip trip = new Trip();
        trip.setCountry(country);
        trip.setDate_in(dateIn);
        trip.setDate_out(dateOut);
        trip.setTourist(tourist);
        trip.setRoom(new Room());
        entityManager = emf.createEntityManager();
        //entityManager.getTransaction().begin();
        entityManager.persist(trip);
        System.out.println(trip.getTourist().toString());
        entityManager.getTransaction().commit();
        entityManager.close();


        /*
        EntityManager entityManager = emf.createEntityManager();
        Cargo cargo = entityManager
                .createQuery("select c from Cargo c where c.statement.id = :statement", Cargo.class)
                .setParameter("statement", Integer.parseInt(statement))
                .getSingleResult();
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().commit();
            entityManager.close();
            entityManager = emf.createEntityManager();
        }

        Tourist tourist = getTouristById(id);
        Trip trip = new Trip();
        trip.setCountry(country);
        trip.setDate_in(dateIn);
        trip.setDate_out(dateOut);
        trip.setTourist(tourist);
        entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        Room room = entityManager.createQuery("select r from Room r where r.id = 1", Room.class).getSingleResult();
        trip.setRoom(room);
        tourist.getCargos().add(cargo);
        tourist.getTripList().add(trip);
        try {
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        try {
            entityManager = emf.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(tourist);
            entityManager.getTransaction().commit();
            entityManager.getTransaction().begin();
            entityManager.persist(trip);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        entityManager.close();
        */
    }

    private boolean isUniquePassport(Long id, String passport, EntityManager entityManager) {
        Long otherId = entityManager
                .createQuery("select id from tourist where passport = :passport", Long.class)
                .setParameter("passport", passport)
                .getResultStream()
                .findFirst()
                .orElse((long) -1);
        return (otherId == -1 || otherId.equals(id));
    }

}
