package service;

import models.entity.*;
import repository.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public List<Integer> getGroups() {
        return repository.getGroups();
    }

    public List<Cargo> getFreeCargos() {
        return repository.getFreeCargos();
    }

    public void addNewStatement(List<Cargo> addedCargos, float weight, float wrap, float insurance) {
        repository.addNewStatement(addedCargos, weight, wrap, insurance);
    }

    public List<Warehouse> getWarehouseList() {
        return repository.getWarehouseList();
    }

    public List<Flight> getFlightList() {
        return repository.getFlightList();
    }

    public void addNewCargo(Long warehouseId, Long dateIn, Long dateOut, Long flightId, String kind) {
        repository.addNewCargo(warehouseId, dateIn, dateOut, flightId, kind);
    }

    public List<Statement> getStatementList() {
        return repository.getStatementList();
    }

    public List<Cargo> getCargosByStatementId(long id) {
        return repository.getCargosByStatementId(id);
    }

    public void editStatement(long id, float weight, float wrap, float insurance, List<Cargo> addedCargos) {
        repository.editStatement(id, weight, wrap, insurance, addedCargos);

    }

    public void removeStatement(long id) {
        repository.removeStatement(id);
    }

    public List<Cargo> getCargosWithoutWarehouse() {
        return repository.getCargosWithoutWarehouse();
    }

    public void addCargoToWarehouse(Cargo cargo, Warehouse warehouse) {
        repository.addCargoToWarehouse(cargo, warehouse);
    }

    public List<Cargo> getCargosWithWarehouse() {
        return repository.getCargosWithWarehouse();
    }

    public void editCargoToWarehouse(Cargo cargo, Warehouse warehouse) {
        repository.editCargoToWarehouse(cargo, warehouse);
    }

    public List<Tourist> getTouristByGroup(Integer group) {
        return repository.getTouristByGroup(group);
    }

    public List<Hotel> getHotelList() {
        return repository.getHotelList();
    }

    public List<Room> getFreeRoomsByHotel(Hotel hotel, Tourist t, int group) {
        return repository.getFreeRoomsByHotel(hotel, t, group);
    }

    public void setTouristsToHotels(Integer group, List<Room> chosenRoomList) {
        repository.setTouristsToHotels(group, chosenRoomList);
    }

    public List<Float> getFinancialReportForAllPeriod() {
        return repository.getFinancialReportForAllPeriod();
    }

    public List<Float> getFinancialReportForPeriod(long dateIn, long dateOut) {
        return repository.getFinancialReportForPeriod(dateIn, dateOut);

    }

    public List<Excursion> getPopularExcursions() {
        return repository.getPopularExcursions();
    }

    public Map<Agency, Integer> getQualityAgencies() {
        return repository.getQualityAgencies();
    }

    public List<Tourist> getTouristList() {
        return repository.getTouristList();
    }

    public List<Tourist> getRestTouristListAllTime(String country) {
        return repository.getRestTouristListAllTime(country);
    }

    public List<Tourist> getCargoTouristListAllTime(String country) {
        return repository.getCargoTouristListAllTime(country);
    }

    public List<String> getCountries() {
        return repository.getCountries();
    }

    public List<Tourist> getRestTouristListPeriod(String country, long dateIn, long dateOut) {
        return repository.getRestTouristListPeriod(country, dateIn, dateOut);
    }

    public List<Tourist> getCargoTouristListPeriod(String country, long dateIn, long dateOut) {
        return repository.getCargoTouristListPeriod(country, dateIn, dateOut);
    }

    public List<Flight> getFlightListForDate(long dateIn) {
        return repository.getFlightListForDate(dateIn);
    }
}
