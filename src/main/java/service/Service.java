package service;

import models.entity.Agency;
import models.entity.Excursion;
import models.entity.Tourist;
import models.entity.Trip;
import repository.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Service {
    private final Repository repository;
    private List<Excursion> joinedExcursions = new ArrayList<>();
    private List<Agency> selectedAgency = new ArrayList<>();
    private Timestamp selectedDateIn;
    private Timestamp selectedDateOut;
    private int sortProperties = 0;
    private String ordersMethodName = "";
    private int ordersCount = 0;

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

    public List<Trip> getTripList(Long id) {
        return repository.getTripList(id);
    }

    public void setToCacheJoinedExcursions(Long id) {
        Excursion excursion = repository.getExcursionById(id);
        for (Excursion ex : joinedExcursions)
            if (ex.getId().equals(excursion.getId()))
                return;
        joinedExcursions.add(excursion);
    }

    public List<Excursion> getFromCacheJoinedExcursions() {
        return joinedExcursions;
    }

    public void addNewRestTrip(Long id, String country, Timestamp dateIn, Timestamp dateOut, List<Excursion> joinedExcursions) {
        repository.addNewRestTrip(id, country, dateIn, dateOut, joinedExcursions);
    }

    public void clearJoinedExcursion() {
        joinedExcursions.clear();
    }

    public List<Agency> getAgencies() {
        return repository.getAgencies();
    }

    public void setToCacheSelectedAgency(List<Agency> selectedAgency) {
        this.selectedAgency.clear();
        this.selectedAgency.addAll(selectedAgency);
    }

    public void setToCacheSelectedDate(long dateIn, long dateOut) {
        selectedDateIn = new Timestamp(dateIn);
        selectedDateOut = new Timestamp(dateOut);
    }

    public void setToCacheSortProperties(int sortProperties) {
        this.sortProperties = sortProperties;
    }

    public void setToCacheStatementForOrders(String methodName, int numOrders) {
        ordersMethodName = methodName;
        ordersCount = numOrders;
    }

    public List<Excursion> getResultOfAdvancedSearching() {
        return repository.getResultOfAdvancedSearching(selectedAgency, selectedDateIn, selectedDateOut, sortProperties, ordersMethodName, ordersCount);
    }

    public void clearAdvancedSearchDatas() {
        ordersCount = 0;
        ordersMethodName = "";
        selectedDateOut = null;
        selectedDateIn = null;
        selectedAgency = new ArrayList<>();
        sortProperties = 0;
    }
}
