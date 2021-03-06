package repository;

import models.entity.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface Repository {
    List<Excursion> getExcursions();

    Tourist getTouristByPassport(String passport);

    void putNewTourist(String name, String passport, String sex, int age);

    int updateTourist(Long id, String name, int age, String passport, String sex);

    Tourist getTouristById(Long id);

    void addNewCargoTrip(Long id, String statement, String country, Timestamp dateIn, Timestamp dateOut);

    Map<Hotel, Integer> getHotelTookRooms(long dateIn, long dateOut);

    List<Hotel> getPassengerHotels(Flight f);

    List<Tourist> getCargoTouristListPeriod(String country, long dateIn, long dateOut);

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

    List<Excursion> getPopularExcursions();

    Map<Agency, Integer> getQualityAgencies();

    List<Tourist> getTouristList();

    List<Tourist> getRestTouristListAllTime(String country);

    List<Tourist> getCargoTouristListAllTime(String country);

    List<String> getCountries();

    List<Tourist> getRestTouristListPeriod(String country, long dateIn, long dateOut);

    List<Flight> getFlightListForDate(long dateIn);

    List<Object[]> getPassengersInfo(Flight flight);

    List<Tourist> getPassengersList(Flight flight);

    List<Cargo> getPassengerCargos(Flight flight);

    Map<String, Float> getSpecificKinds();

    List<String> getCountries(Tourist t);

    List<Object[]> getDatesForTouristByCountry(Tourist t, String country);

    List<Cargo> getCargosForTourist(Tourist t, String country);

    List<Excursion> getExcursionsForTourist(Tourist t, String country);

    Map<Flight, Integer> getWarehouseStatistic(long dateIn, long dateOut, long warehouseId);

    float getCargosWeight(Flight f);

    List<Airplane> getAirplaneList();

    void addAgency(String text);

    void addAirplane(String countSpace, String cargoWeight, String volumeWeight, boolean isCargoplane);

    void addExcursion(Agency agency, Long date, String title);

    void addFlight(Airplane airplane, Long date);

    void addHotel(String title);

    void addPassenger(Flight flight, Tourist tourist);

    void addRoom(Hotel hotel, String num);

    void alterAgency(Agency agency, String newTitle);

    void alterAirplane(Airplane airplane, String seats, String cargo, String volume, boolean isCargoplane);

    List<Cargo> getCargoList();

    void alterCargo(Cargo cargo, Warehouse warehouse, Flight flight, Long dateIn, Long dateOut, String kind);

    void alterExcursion(Excursion excursion, Agency agency, Long date, String title);

    void alterFlight(Flight flight, Airplane airplane, Long date);

    void alterHotel(Hotel hotel, String title);

    List<Passenger> getPassengerList();

    void alterPassenger(Passenger passenger, Flight flight, Tourist tourist);

    List<Room> getRoomList();

    void alterRoom(Room room, Hotel hotel, String num);

    List<Trip> getTripList();

    void alterTrip(Trip trip, String country, Long dateIn, Long dateOut, Room room);

    void deleteAgency(Agency agency);

    void deleteAirplane(Airplane airplane);

    void deleteTourist(Tourist tourist);

    void deleteCargo(Cargo cargo);

    void deleteExcursion(Excursion excursion);

    void deleteFlight(Flight flight);

    void deleteHotel(Hotel hotel);

    void deletePassenger(Tourist tourist, Flight flight);

    Map<Room, Integer> getRoomNumbersForHotel(Hotel hotel);

    void deleteRoom(Room room);

    void deleteTrip(Trip trip);

    List<Tourist> getTouristListWithoutGroup();

    int setNewGroup(List<Tourist> addedTourist);
}
