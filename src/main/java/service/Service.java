package service;

import models.entity.Excursion;
import models.entity.Tourist;
import models.entity.Trip;
import repository.Repository;

import java.sql.Timestamp;
import java.util.List;

public class Service {
    private final Repository repository;

    public Service(Repository repository) {
        this.repository = repository;
    }

    public List<Excursion> getExcursions() {
        return repository.getExcursions();
    }

    public Tourist getTouristByPassport(String passport) {
        return repository.getTouristByPassport(passport);
    }

    public void putNewTourist(String name, String passport, String sex, int age) {
        repository.putNewTourist(name, passport, sex, age);
    }

    public int updateTourist(Long id, String name, int age, String passport, String sex) {
        return repository.updateTourist(id, name, age, passport, sex);
    }

    public Tourist getTouristById(Long id) {
        return repository.getTouristById(id);
    }

    public void addNewCargoTrip(Long id, String statement, String country, Timestamp dateIn, Timestamp dateOut) {
        repository.addNewCargoTrip(id, statement, country, dateIn, dateOut);
    }

    public List<Trip> getTripList(Tourist tourist) {
        return repository.getTripList(tourist);
    }
}
