package api;

import models.entity.Agency;
import models.entity.Excursion;
import models.entity.Tourist;
import models.entity.Trip;
import service.Service;

import java.sql.Timestamp;
import java.util.List;

public class Controller {
    private Service service;

    public Controller(Service service) {
        this.service = service;
    }

    public List<Excursion> getExcursions() {
        return service.getExcursions();
    }

    public Tourist getTouristByPassport(String passport) {
        return service.getTouristByPassport(passport);
    }

    public void putNewTourist(String name, String passport, String sex, int age) {
        service.putNewTourist(name, passport, sex, age);
    }

    public int updateTourist(Long id, String name, int age, String passport, String sex) {
        return service.updateTourist(id, name, age, passport, sex);
    }

    public Tourist getTouristById(Long id) {
        return service.getTouristById(id);
    }

    public void addNewCargoTrip(Long id, String statement, String country, Timestamp dateIn, Timestamp dateOut) {
        service.addNewCargoTrip(id, statement, country, dateIn, dateOut);
    }

    public List<Trip> getTripList(Long id) {
        return service.getTripList(id);
    }

    public void setToCacheJoinedExcursions(Long id) {
        service.setToCacheJoinedExcursions(id);
    }

    public List<Excursion> getFromCacheJoinedExcursions() {
        return service.getFromCacheJoinedExcursions();
    }

    public void addNewRestTrip(Long id, String country, Timestamp dateIn, Timestamp dateOut, List<Excursion> joinedExcursions) {
        service.addNewRestTrip(id, country, dateIn, dateOut, joinedExcursions);
    }

    public void clearJoinedExcursion() {
        service.clearJoinedExcursion();
    }

    public List<Agency> getAgencies() {
        return service.getAgencies();
    }

    public void setToCacheSelectedAgency(List<Agency> selectedAgency) {
        service.setToCacheSelectedAgency(selectedAgency);
    }
}
