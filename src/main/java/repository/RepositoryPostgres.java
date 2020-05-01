package repository;

import models.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class RepositoryPostgres implements Repository {
    EntityManagerFactory emf;

    public RepositoryPostgres() {
        emf = Persistence.createEntityManagerFactory("models");
    }

    @Override
    public List<Excursion> getExcursions() {
        EntityManager entityManager = emf.createEntityManager();
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

    //TODO: вместо создания брать имеющегося туриста и менять его
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
                .createQuery("select t from tourist t where t.id = :id", Tourist.class)
                .setParameter("id", id)
                .getSingleResult();
        entityManager.close();
        return tourist;
    }

    @Override
    public void addNewCargoTrip(Long id, String statement, String country, Timestamp dateIn, Timestamp dateOut) {
        EntityManager entityManager = emf.createEntityManager();
        List<Cargo> cargo = entityManager
                .createQuery("select c from Cargo c where c.statement.id = :statement", Cargo.class)
                .setParameter("statement", Integer.parseInt(statement))
                .getResultList();
        entityManager.getTransaction().begin();
        Tourist tourist = entityManager.find(Tourist.class, id);
        Trip trip = new Trip();
        trip.setCountry(country);
        trip.setDate_in(dateIn);
        trip.setDate_out(dateOut);
        trip.setTourist(tourist);
        trip.setRoom(null);
        trip.setCargos(new ArrayList<>());
        trip.getCargos().addAll(cargo);
        entityManager.persist(trip);
        cargo.forEach(c -> c.setOwnerTrip(trip));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void addNewRestTrip(Long id, String country, Timestamp dateIn, Timestamp dateOut, List<Excursion> joinedExcursions) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Tourist tourist = entityManager.find(Tourist.class, id);
            Trip trip = new Trip();
            trip.setCountry(country);
            trip.setDate_in(dateIn);
            trip.setDate_out(dateOut);
            trip.setTourist(tourist);
            trip.setRoom(null);
            trip.setExcursions(new ArrayList<>());
            List<Long> joinedExcursionsId = new ArrayList<>();
            joinedExcursions.forEach(e -> joinedExcursionsId.add(e.getId()));
            List<Excursion> excursionList = entityManager
                    .createQuery("select e from Excursion e", Excursion.class)
                    .getResultStream()
                    .filter(excursion -> joinedExcursionsId.contains(excursion.getId()))
                    .collect(Collectors.toList());
            excursionList.forEach(entityManager::merge);
            trip.getExcursions().addAll(excursionList);
            entityManager.persist(trip);
            excursionList.forEach(e->e.getParticipatingTourists().add(trip));
            entityManager.getTransaction().commit();
            entityManager.close();
        }
        catch (Exception e){
         entityManager.getTransaction().rollback();
         e.printStackTrace();
        }
    }

    @Override
    public List<Agency> getAgencies() {
        EntityManager entityManager = emf.createEntityManager();
        List<Agency> agencyList = entityManager
                .createQuery("select a from Agency a")
                .getResultList();
        entityManager.close();
        return agencyList == null ? new ArrayList<>() : agencyList;
    }

    @Override
    public List<Trip> getTripList(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        List<Trip> tripList = entityManager
                .createQuery("select t from trip t where t.tourist.id = :id")
                .setParameter("id", id)
                .getResultList();
        entityManager.getTransaction().commit();
        return tripList;
    }

    @Override
    public Excursion getExcursionById(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        Excursion e = entityManager
                .createQuery("select e from Excursion e where e.id = :id", Excursion.class)
                .setParameter("id", id)
                .getSingleResult();
        entityManager.close();
        return e;
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
