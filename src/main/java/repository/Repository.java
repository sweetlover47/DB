package repository;

import models.entity.Excursion;
import models.entity.Tourist;
import models.entity.Trip;

import java.sql.Timestamp;
import java.util.List;

public interface Repository {
    List<Excursion> getExcursions();

    Tourist getTouristByPassport(String passport);

    void putNewTourist(String name, String passport, String sex, int age);

    int updateTourist(Long id, String name, int age, String passport, String sex);

    Tourist getTouristById(Long id);

    void addNewCargoTrip(Long id, String statement, String country, Timestamp dateIn, Timestamp dateOut);

    List<Trip> getTripList(Tourist tourist);
}
