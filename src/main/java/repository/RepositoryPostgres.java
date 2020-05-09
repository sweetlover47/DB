package repository;

import models.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.persistence.criteria.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
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
            excursionList.forEach(e -> e.getParticipatingTourists().add(trip));
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
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
    public List<Excursion> getResultOfAdvancedSearching(List<Agency> selectedAgency, Timestamp selectedDateIn, Timestamp selectedDateOut, int sortProperties, String ordersMethodName, int ordersCount) {
        EntityManager entityManager = emf.createEntityManager();

        List<Long> selectedAgencyId = new ArrayList<>();
        selectedAgency.forEach(e -> selectedAgencyId.add(e.getId()));
        List<Agency> agencyList = entityManager
                .createQuery("select a from Agency a", Agency.class)
                .getResultStream()
                .filter(agency -> selectedAgencyId.contains(agency.getId()))
                .collect(Collectors.toList());
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Excursion> criteriaQuery = criteriaBuilder.createQuery(Excursion.class);
        Root<Excursion> excursionRoot = criteriaQuery.from(Excursion.class);

        Predicate whereTotalPredicate = getWherePredicate(selectedAgency, selectedDateIn, selectedDateOut, ordersMethodName, ordersCount, agencyList, criteriaBuilder, excursionRoot);
        List<Order> orderList = getOrderList(sortProperties, criteriaBuilder, excursionRoot);

        if (orderList.isEmpty())
            criteriaQuery.select(excursionRoot).where(whereTotalPredicate);
        else
            criteriaQuery.select(excursionRoot).where(whereTotalPredicate).orderBy(orderList);

        List<Excursion> result = entityManager.createQuery(criteriaQuery).getResultList();
        return result;
    }

    private List<Order> getOrderList(int sortProperties, CriteriaBuilder criteriaBuilder, Root<Excursion> excursionRoot) {
        int titleProperty = sortProperties % 4;
        Order titleOrder = null;
        if (titleProperty > 0)
            titleOrder = (titleProperty == 2) ? criteriaBuilder.desc(excursionRoot.get("title")) : criteriaBuilder.asc(excursionRoot.get("title"));
        int popularityProperty = (sortProperties % 16) / 4;
        Order popularityOrder = null;
        if (popularityProperty > 0)
            popularityOrder = (popularityProperty == 2) ? criteriaBuilder.desc(excursionRoot.get("numOrders")) : criteriaBuilder.asc(excursionRoot.get("numOrders"));
        int dateProperty = (sortProperties % 64) / 16;
        Order dateOrder = null;
        if (dateProperty > 0)
            dateOrder = (dateProperty == 2) ? criteriaBuilder.desc(excursionRoot.get("date")) : criteriaBuilder.asc(excursionRoot.get("date"));
        int agencyProperty = (sortProperties / 64);
        Order agencyOrder = null;
        if (agencyProperty > 0)
            agencyOrder = (agencyProperty == 2) ? criteriaBuilder.desc(excursionRoot.join("agency").get("name")) : criteriaBuilder.asc(excursionRoot.join("agency").get("name"));
        List<Order> orderList = new ArrayList<>();
        orderList.add(titleOrder);
        orderList.add(popularityOrder);
        orderList.add(dateOrder);
        orderList.add(agencyOrder);
        orderList.removeIf(Objects::isNull);
        return orderList;
    }

    private Predicate getWherePredicate(List<Agency> selectedAgency, Timestamp selectedDateIn, Timestamp selectedDateOut, String ordersMethodName, int ordersCount, List<Agency> agencyList, CriteriaBuilder criteriaBuilder, Root<Excursion> excursionRoot) {
        CriteriaBuilder.In<Agency> inAgency = null;
        Predicate betweenDate = null;
        Predicate ordersPredicate = null;
        if (!selectedAgency.isEmpty()) {
            inAgency = criteriaBuilder.in(excursionRoot.get("agency"));
            agencyList.forEach(inAgency::value);
        }
        if (selectedDateIn != null && selectedDateOut != null)
            betweenDate = criteriaBuilder.between(excursionRoot.get("date"), selectedDateIn, selectedDateOut);
        if (!ordersMethodName.isEmpty() && ordersCount > 0) {
            Method ordersMethod = null;
            try {
                ordersMethod = CriteriaBuilder.class.getMethod(ordersMethodName, Expression.class, (ordersMethodName.equals("equal")) ? Object.class : Number.class);
                ordersPredicate = (Predicate) ordersMethod.invoke(criteriaBuilder, (Expression) excursionRoot.get("numOrders"), ordersCount);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        Predicate whereTotalPredicate = criteriaBuilder.and();
        if (inAgency != null)
            whereTotalPredicate = criteriaBuilder.and(inAgency);
        if (betweenDate != null)
            whereTotalPredicate = criteriaBuilder.and(whereTotalPredicate, betweenDate);
        if (ordersPredicate != null)
            whereTotalPredicate = criteriaBuilder.and(whereTotalPredicate, ordersPredicate);
        return whereTotalPredicate;
    }

    @Override
    public List<Integer> getGroups() {
        EntityManager entityManager = emf.createEntityManager();
        List<Integer> list = entityManager
                .createQuery("select t.group from trip t where t.group <> 0", Integer.class)
                .getResultStream()
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        entityManager.close();
        return list;
    }

    @Override
    public List<Cargo> getFreeCargos() {
        EntityManager entityManager = emf.createEntityManager();
        List<Cargo> cargoList = entityManager
                .createQuery("select c from Cargo c where c.statement IS NULL or c.statement.id = 0", Cargo.class)
                .getResultList();
        entityManager.close();
        return cargoList;
    }

    @Override
    public void addNewStatement(List<Cargo> addedCargos, float weight, float wrap, float insurance) {
        EntityManager entityManager = emf.createEntityManager();
        List<Long> addedCargosId = new ArrayList<>();
        for (Cargo c : addedCargos)
            addedCargosId.add(c.getId());
        List<Cargo> cargoList = entityManager
                .createQuery("select c from Cargo c", Cargo.class)
                .getResultStream()
                .filter(c -> addedCargosId.contains(c.getId()))
                .collect(Collectors.toList());
        try {
            entityManager.getTransaction().begin();
            Transaction t = new Transaction();
            t.set_income(true);
            t.setName("Хранение груза");
            t.setSum(weight + wrap + insurance);
            entityManager.persist(t);
            Statement statement = new Statement();
            statement.setCargos(cargoList);
            statement.setCostInsurance(insurance);
            statement.setCostWrap(wrap);
            statement.setWeight(weight);
            statement.setCount(addedCargos.size());
            statement.setTransaction(t);
            entityManager.persist(statement);
            for (Cargo c : cargoList) {
                c.setStatement(statement);
                entityManager.merge(c);
            }
            entityManager.getTransaction().commit();
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        entityManager.close();
    }

    @Override
    public List<Warehouse> getWarehouseList() {
        EntityManager entityManager = emf.createEntityManager();
        List<Warehouse> warehouseList = entityManager
                .createQuery("select w from warehouse w", Warehouse.class)
                .getResultList();
        entityManager.close();
        return warehouseList;
    }

    @Override
    public List<Flight> getFlightList() {
        EntityManager entityManager = emf.createEntityManager();
        List<Flight> flightList = entityManager
                .createQuery("select f from Flight f", Flight.class)
                .getResultList();
        entityManager.close();
        return flightList;
    }

    @Override
    public void addNewCargo(Long warehouseId, Long dateIn, Long dateOut, Long flightId, String kind) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            Warehouse warehouse = warehouseId == null ? null : entityManager
                    .createQuery("select w from warehouse w where w.id = :id", Warehouse.class)
                    .setParameter("id", warehouseId)
                    .getSingleResult();
            Flight flight = flightId == null ? null : entityManager
                    .createQuery("select f from Flight f where f.id = :id", Flight.class)
                    .setParameter("id", flightId)
                    .getSingleResult();
            Cargo c = new Cargo();
            c.setStatement(null);
            c.setDate_in(new Timestamp(dateIn));
            c.setDate_out(dateOut == null ? null : new Timestamp(dateOut));
            c.setWarehouse(warehouse);
            c.setKind(kind);
            c.setFlight(flight);
            entityManager.getTransaction().begin();
            entityManager.persist(c);
            entityManager.getTransaction().commit();
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        entityManager.close();
    }

    @Override
    public List<Statement> getStatementList() {
        EntityManager entityManager = emf.createEntityManager();
        List<Statement> statementList = entityManager
                .createQuery("select s from Statement s")
                .getResultList();
        entityManager.close();
        return statementList == null ? new ArrayList<>() : statementList;
    }

    @Override
    public List<Cargo> getCargosByStatementId(long id) {
        EntityManager entityManager = emf.createEntityManager();
        List<Cargo> cargoList = entityManager
                .createQuery("select c from Cargo c where c.statement IS NOT NULL AND c.statement.id = :id", Cargo.class)
                .setParameter("id", id)
                .getResultList();
        entityManager.close();
        return cargoList;
    }

    @Override
    public void editStatement(long id, float weight, float wrap, float insurance, List<Cargo> addedCargos) {
        EntityManager entityManager = emf.createEntityManager();
        Statement statement = entityManager
                .createQuery("select s from Statement s where s.id = :id", Statement.class)
                .setParameter("id", id)
                .getSingleResult();
        try {
            entityManager.getTransaction().begin();
            Transaction t = statement.getTransaction();
            List<Cargo> cargoList = entityManager
                    .createQuery("select c from Cargo c where c.statement IS NOT NULL AND c.statement.id = :id", Cargo.class)
                    .setParameter("id", id)
                    .getResultList();
            t.setSum(wrap + weight + insurance);
            statement.setCargos(addedCargos);
            statement.setTransaction(t);
            statement.setCount(addedCargos.size());
            statement.setWeight(weight);
            statement.setCostWrap(wrap);
            statement.setCostInsurance(insurance);
            entityManager.merge(t);
            entityManager.merge(statement);
            for (Cargo c : cargoList) {  //delete statement from each cargo
                c.setStatement(null);
                entityManager.merge(c);
            }
            for (Cargo c : addedCargos) {   //set statement to new cargos
                c.setStatement(statement);
                entityManager.merge(c);
            }
            entityManager.getTransaction().commit();
        } catch (RollbackException ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
        entityManager.close();
    }

    @Override
    public void removeStatement(long id) {
        EntityManager entityManager = emf.createEntityManager();
        Statement statement = entityManager
                .createQuery("select s from Statement s where s.id = :id", Statement.class)
                .setParameter("id", id)
                .getSingleResult();
        try {
            entityManager.getTransaction().begin();
            List<Cargo> cargoList = statement.getCargos();
            Transaction transaction = statement.getTransaction();
            for (Cargo c : cargoList) {
                c.setStatement(null);
                entityManager.merge(c);
            }
            entityManager.remove(transaction);
            entityManager.remove(statement);
            entityManager.getTransaction().commit();
        } catch (RollbackException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }

        entityManager.close();
    }

    @Override
    public List<Cargo> getCargosWithoutWarehouse() {
        EntityManager entityManager = emf.createEntityManager();
        List<Cargo> cargoList = entityManager
                .createQuery("select c from Cargo c where c.warehouse IS NULL")
                .getResultList();
        entityManager.close();
        return cargoList;
    }

    @Override
    public void addCargoToWarehouse(Cargo cargo, Warehouse warehouse) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            cargo.setWarehouse(warehouse);
            entityManager.merge(cargo);
            entityManager.getTransaction().commit();
        } catch (RollbackException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
    }

    @Override
    public List<Cargo> getCargosWithWarehouse() {
        EntityManager entityManager = emf.createEntityManager();
        List<Cargo> cargoList = entityManager
                .createQuery("select c from Cargo c where c.warehouse IS NOT NULL")
                .getResultList();
        entityManager.close();
        return cargoList;
    }

    @Override
    public void editCargoToWarehouse(Cargo cargo, Warehouse warehouse) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            cargo.setWarehouse(warehouse);
            entityManager.merge(cargo);
            entityManager.getTransaction().commit();
        } catch (RollbackException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
    }

    @Override
    public List<Tourist> getTouristByGroup(Integer group) {
        EntityManager entityManager = emf.createEntityManager();
        List<Tourist> touristList = entityManager
                .createQuery("select t.tourist from trip t where t.group = :group", Tourist.class)
                .setParameter("group", group)
                .getResultList();
        entityManager.close();
        return touristList;
    }

    @Override
    public List<Hotel> getHotelList() {
        EntityManager entityManager = emf.createEntityManager();
        List<Hotel> tripList = entityManager
                .createQuery("select h from Hotel h", Hotel.class)
                .getResultList();
        entityManager.close();
        return tripList;
    }

    @Override
    public List<Room> getFreeRoomsByHotel(Hotel hotel, Tourist t, int group) {
        EntityManager entityManager = emf.createEntityManager();
        List<Room> rooms = entityManager
                .createQuery("select r from Room r where r.hotel = :hotel", Room.class)
                .setParameter("hotel", hotel)
                .getResultList();
        Trip trip = entityManager
                .createQuery("select t from trip t where t.tourist.id = :id AND t.group = :group", Trip.class)
                .setParameter("id", t.getId())
                .setParameter("group", group)
                .getSingleResult();
        try {
            entityManager.getTransaction().begin();
            Timestamp in = trip.getDate_in();
            Timestamp out = trip.getDate_out();
            List<Room> removedRooms = new ArrayList<>();
            for (Room r : rooms) {
                for (Trip roomTrip : r.getTripList()) {
                    if (roomTrip.getDate_in().getTime() < out.getTime() && roomTrip.getDate_out().getTime() > in.getTime() || roomTrip.getDate_out().getTime() > in.getTime() && roomTrip.getDate_in().getTime() < out.getTime()) {
                        removedRooms.add(r);
                        break;
                    }
                }
            }
            rooms.removeAll(removedRooms);
            entityManager.getTransaction().commit();
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        entityManager.close();
        return rooms;
    }

    @Override
    public void setTouristsToHotels(Integer group, List<Room> chosenRoomList) {
        List<Tourist> touristList = getTouristByGroup(group);
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            for (int i = 0; i < chosenRoomList.size(); ++i) {
                Trip t = entityManager
                        .createQuery("select t from trip t where t.group = :group AND t.tourist = :tourist", Trip.class)
                        .setParameter("group", group)
                        .setParameter("tourist", touristList.get(i))
                        .getSingleResult();
                t.setRoom(chosenRoomList.get(i));
                entityManager.merge(t);
            }
            entityManager.getTransaction().commit();
        } catch (RollbackException ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
    }

    @Override
    public List<Float> getFinancialReportForAllPeriod() {
        EntityManager entityManager = emf.createEntityManager();
        List<Transaction> excursions = entityManager
                .createQuery("select t from trans t where t.name = 'Экскурсия'", Transaction.class)
                .getResultList();
        List<Transaction> living = entityManager
                .createQuery("select t from trans t where t.name = 'Проживание'", Transaction.class)
                .getResultList();
        List<Transaction> cargos = entityManager
                .createQuery("select t from trans t where t.name = 'Хранение груза'", Transaction.class)
                .getResultList();
        List<Transaction> fly = entityManager
                .createQuery("select t from trans t where t.name = 'Перелет'", Transaction.class)
                .getResultList();
        return getResultList(entityManager, excursions, living, cargos, fly);
    }

    private List<Float> getResultList(EntityManager entityManager, List<Transaction> excursions, List<Transaction> living, List<Transaction> cargos, List<Transaction> fly) {
        boolean e, l, c, f;
        try {
            entityManager.getTransaction().begin();
            e = false;
            l = true;
            c = true;
            f = true;
            List<Float> list = new ArrayList<>();
            float sum = 0.0f;
            for (Transaction t : excursions)
                sum += t.getSum();
            if (!e) sum *= -1;
            list.add(sum);
            sum = 0.0f;
            for (Transaction t : living)
                sum += t.getSum();
            if (!l) sum *= -1;
            list.add(sum);
            sum = 0.0f;
            for (Transaction t : cargos)
                sum += t.getSum();
            if (!c) sum *= -1;
            list.add(sum);
            sum = 0.0f;
            for (Transaction t : fly)
                sum += t.getSum();
            if (!f) sum *= -1;
            list.add(sum);
            entityManager.getTransaction().commit();
            entityManager.close();
            return list;
        } catch (RollbackException ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
            entityManager.close();
            return null;
        }
    }

    @Override
    public List<Float> getFinancialReportForPeriod(long dateIn, long dateOut) {
        EntityManager entityManager = emf.createEntityManager();
        List<Transaction> excursionTransactions = entityManager
                .createQuery("select t from trans t join Excursion e on t = e.transaction where e.date BETWEEN  :in AND :out", Transaction.class)
                .setParameter("in", new Timestamp(dateIn))
                .setParameter("out", new Timestamp(dateOut))
                .getResultList();
        List<Transaction> livingTransactions = entityManager
                .createQuery("select t from trans t join Room r on t.room = r join trip tr on tr.room = r where (tr.date_in between :in and :out) and (tr.date_out between :in and :out)", Transaction.class)
                .setParameter("in", new Timestamp(dateIn))
                .setParameter("out", new Timestamp(dateOut))
                .getResultList();
        entityManager.getTransaction().begin();
        List<Transaction> cargosTransactions = entityManager
                .createQuery("select t from trans t join Statement s on s.transaction = t join Cargo c on c.statement = s where (c.date_in BETWEEN  :in AND :out) and (c.date_out between :in and :out)", Transaction.class)
                .setParameter("in", new Timestamp(dateIn))
                .setParameter("out", new Timestamp(dateOut))
                .getResultList();
        entityManager.getTransaction().commit();
        List<Transaction> flyTransactions = entityManager
                .createQuery("select t from trans t join Flight f on f.transaction = t where f.date BETWEEN  :in AND :out", Transaction.class)
                .setParameter("in", new Timestamp(dateIn))
                .setParameter("out", new Timestamp(dateOut))
                .getResultList();
        return getResultList(entityManager, excursionTransactions, livingTransactions, cargosTransactions, flyTransactions);
    }

    @Override
    public List<Trip> getTripList(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        List<Trip> tripList = entityManager
                .createQuery("select t from trip t where t.tourist.id = :id", Trip.class)
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
