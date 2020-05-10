package api;

import models.entity.*;
import service.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

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

    public void setToCacheSelectedDate(long dateIn, long dateOut) {
        service.setToCacheSelectedDate(dateIn, dateOut);
    }

    public void setToCacheSortProperties(int sortProperties) {
        service.setToCacheSortProperties(sortProperties);
    }

    public void setToCacheStatementForOrders(String methodName, int numOrders) {
        service.setToCacheStatementForOrders(methodName, numOrders);
    }

    public List<Excursion> getResultOfAdvancedSearching() {
        return service.getResultOfAdvancedSearching();
    }

    public void clearAdvancedSearchDatas() {
        service.clearAdvancedSearchDatas();
    }

    public List<Integer> getGroups() {
        return service.getGroups();
    }

    public List<Cargo> getFreeCargos() {
        return service.getFreeCargos();
    }

    public void addNewStatement(List<Cargo> addedCargos, float weight, float wrap, float insurance) {
        service.addNewStatement(addedCargos, weight, wrap, insurance);
    }

    public List<Warehouse> getWarehouseList() {
        return service.getWarehouseList();
    }

    public List<Flight> getFlightList() {
        return service.getFlightList();
    }

    public void addNewCargo(Long warehouseId, Long dateIn, Long dateOut, Long flightId, String kind) {
        service.addNewCargo(warehouseId, dateIn, dateOut, flightId, kind);
    }

    public List<Statement> getStatementList() {
        return service.getStatementList();
    }

    public List<Cargo> getCargosByStatementId(long id) {
        return service.getCargosByStatementId(id);
    }

    public void editStatement(long id, float weight, float wrap, float insurance, List<Cargo> addedCargos) {
        service.editStatement(id, weight, wrap, insurance, addedCargos);

    }

    public void removeStatement(long id) {
        service.removeStatement(id);
    }

    public List<Cargo> getCargosWithoutWarehouse() {
        return service.getCargosWithoutWarehouse();
    }

    public void addCargoToWarehouse(Cargo cargo, Warehouse warehouse) {
        service.addCargoToWarehouse(cargo, warehouse);
    }

    public List<Cargo> getCargosWithWarehouse() {
        return service.getCargosWithWarehouse();
    }

    public void editCargoToWarehouse(Cargo cargo, Warehouse warehouse) {
        service.editCargoToWarehouse(cargo, warehouse);
    }

    public List<Tourist> getTouristListByGroup(Integer group) {
        return service.getTouristByGroup(group);
    }

    public List<Hotel> getHotelList() {
        return service.getHotelList();
    }

    public List<Room> getFreeRoomsByHotel(Hotel hotel, Tourist t, int group) {
        return service.getFreeRoomsByHotel(hotel, t, group);
    }

    public void setTouristsToHotels(Integer group, List<Room> chosenRoomList) {
        service.setTouristsToHotels(group, chosenRoomList);
    }

    public List<Float> getFinancialReportForAllPeriod() {
        return service.getFinancialReportForAllPeriod();
    }

    public List<Float> getFinancialReportForPeriod(long dateIn, long dateOut) {
        return service.getFinancialReportForPeriod(dateIn, dateOut);

    }

    public List<Excursion> getPopularExcursions() {
        return service.getPopularExcursions();
    }

    public Map<Agency, Integer> getQualityAgencies() {
        return service.getQualityAgencies();
    }

    public List<Tourist> getTouristList() {
        return service.getTouristList();
    }

    public List<Tourist> getRestTouristListAllTime(String country) {
        return service.getRestTouristListAllTime(country);
    }

    public List<Tourist> getCargoTouristListAllTime(String country) {
        return service.getCargoTouristListAllTime(country);
    }

    public List<String> getCountries() {
        return service.getCountries();
    }

    public List<Tourist> getRestTouristListPeriod(String country, long dateIn, long dateOut) {
        return service.getRestTouristListPeriod(country, dateIn, dateOut);
    }

    public List<Tourist> getCargoTouristListPeriod(String country, long dateIn, long dateOut) {
        return service.getCargoTouristListPeriod(country, dateIn, dateOut);
    }

    public List<Flight> getFlightListForDate(long dateIn) {
        return service.getFlightListForDate(dateIn);
    }
}
