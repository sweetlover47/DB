package repository;

import models.entity.*;

import java.sql.Timestamp;
import java.util.List;

public interface Repository {
    List<Excursion> getExcursions();

    Tourist getTouristByPassport(String passport);

    void putNewTourist(String name, String passport, String sex, int age);

    int updateTourist(Long id, String name, int age, String passport, String sex);

    Tourist getTouristById(Long id);

    void addNewCargoTrip(Long id, String statement, String country, Timestamp dateIn, Timestamp dateOut);

    List<Trip> getTripList(Long id);

    Excursion getExcursionById(Long id);

    void addNewRestTrip(Long id, String country, Timestamp dateIn, Timestamp dateOut, List<Excursion> joinedExcursions);

    List<Agency> getAgencies();

    List<Excursion> getResultOfAdvancedSearching(List<Agency> selectedAgency, Timestamp selectedDateIn, Timestamp selectedDateOut, int sortProperties, String ordersMethodName, int ordersCount);

    List<Integer> getGroups();

    List<Cargo> getFreeCargos();

    void addNewStatement(List<Cargo> addedCargos, float weight, float wrap, float insurance);

    List<Warehouse> getWarehouseList();

    List<Flight> getFlightList();

    void addNewCargo(Long warehouseId, Long dateIn, Long dateOut, Long flightId, String kind);

    List<Statement> getStatementList();

    List<Cargo> getCargosByStatementId(long id);

    void editStatement(long id, float weight, float wrap, float insurance, List<Cargo> addedCargos);

    void removeStatement(long id);

    List<Cargo> getCargosWithoutWarehouse();

    void addCargoToWarehouse(Cargo cargo, Warehouse warehouse);

    List<Cargo> getCargosWithWarehouse();

    void editCargoToWarehouse(Cargo cargo, Warehouse warehouse);

    List<Tourist> getTouristByGroup(Integer group);

    List<Hotel> getHotelList();

    List<Room> getFreeRoomsByHotel(Hotel hotel, Tourist t, int group);

    void setTouristsToHotels(Integer group, List<Room> chosenRoomList);

    List<Float> getFinancialReportForAllPeriod();

    List<Float> getFinancialReportForPeriod(long dateIn, long dateOut);
}
