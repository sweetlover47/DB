package infrastructure.ui;

import models.entity.*;
import repository.Repository;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class DAIqueries extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel totalPanel;
    private JTabbedPane deleteTabPane;
    private JTabbedPane addTabPane;
    private JTabbedPane alterTabPane;
    private JTextField textField1;
    private JButton создатьАгенствоButton;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton создатьСамолетButton;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JTextField textField5;
    private JButton создатьЭкскурсиюButton;
    private JComboBox comboBox5;
    private JComboBox comboBox6;
    private JComboBox comboBox7;
    private JComboBox comboBox8;
    private JButton создатьРейсButton;
    private JTextField textField6;
    private JButton создатьОтельButton;
    private JComboBox comboBox9;
    private JComboBox comboBox10;
    private JButton создатьПассажираButton;
    private JTextField textField7;
    private JComboBox comboBox11;
    private JButton создатьКомнатуButton;
    private JComboBox comboBox12;
    private JTextField textField8;
    private JButton изменитьАгенствоButton;
    private JComboBox comboBox13;
    private JTextField textField9;
    private JCheckBox грузовойCheckBox;
    private JCheckBox грузовойCheckBox1;
    private JButton изменитьСамолетButton;
    private JTextField textField10;
    private JTextField textField11;
    private JComboBox comboBox14;
    private JComboBox comboBox16;
    private JComboBox comboBox17;
    private JComboBox comboBox18;
    private JComboBox comboBox19;
    private JComboBox comboBox20;
    private JTextField textField12;
    private JButton изменитьГрузButton;
    private JComboBox comboBox21;
    private JTextField textField13;
    private JComboBox comboBox22;
    private JComboBox comboBox23;
    private JComboBox comboBox24;
    private JButton изменитьЭкскурсиюButton;
    private JComboBox comboBox25;
    private JComboBox comboBox26;
    private JComboBox comboBox27;
    private JComboBox comboBox28;
    private JButton изменитьРейсButton;
    private JTextField textField14;
    private JButton изменитьОтельButton;
    private JComboBox comboBox29;
    private JComboBox comboBox30;
    private JComboBox comboBox31;
    private JButton изменитьПассажираButton;
    private JComboBox comboBox32;
    private JComboBox comboBox34;
    private JButton изменитьКомнатуButton;
    private JComboBox comboBox35;
    private JTextField textField15;
    private JComboBox comboBox36;
    private JComboBox comboBox37;
    private JComboBox comboBox38;
    private JComboBox comboBox39;
    private JComboBox comboBox40;
    private JComboBox comboBox41;
    private JComboBox comboBox42;
    private JComboBox comboBox43;
    private JComboBox comboBox44;
    private JButton удалитьАгенствоButton;
    private JComboBox comboBox45;
    private JButton удалитьСамолетButton;
    private JComboBox comboBox46;
    private JButton удалитьТуристаButton;
    private JComboBox comboBox47;
    private JButton удалитьГрузButton;
    private JComboBox comboBox48;
    private JButton удалитьЭкскурсиюButton;
    private JComboBox comboBox49;
    private JButton удалитьРейсButton;
    private JComboBox comboBox50;
    private JButton удалитьОтельButton;
    private JButton удалитьПассажираButton;
    private JComboBox comboBox51;
    private JComboBox comboBox52;
    private JComboBox comboBox53;
    private JButton удалитьКомнатуButton;
    private JComboBox comboBox55;
    private JButton удалитьПоездкуButton;
    private JButton изменитьПоездкуButton;
    private JComboBox comboBox15;
    private JComboBox comboBox56;
    private JComboBox comboBox57;
    private JComboBox comboBox58;
    private JComboBox comboBox59;
    private JComboBox comboBox60;
    private JTextField textField16;

    private Long dateIn, dateOut;

    public DAIqueries(Repository repository) {  //for good it will be controller
        setTitle("MainFrame");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        создатьАгенствоButton.addActionListener(e -> repository.addAgency(textField1.getText()));

        tabbedPane1.addChangeListener(e -> {
            if (e.getSource() instanceof JTabbedPane) {
                JTabbedPane pane = (JTabbedPane) e.getSource();
                if (pane.getSelectedIndex() == 0) {
                    addTabPane.setSelectedIndex(0);
                } else if (pane.getSelectedIndex() == 1) {
                    alterTabPane.setSelectedIndex(0);
                    List<Agency> agencyList = repository.getAgencies();
                    comboBox12.setModel(getAgenciesModel(agencyList));
                    изменитьАгенствоButton.addActionListener(r -> repository.alterAgency(agencyList.get(comboBox12.getSelectedIndex()), textField8.getText()));
                } else if (pane.getSelectedIndex() == 2) {

                }
            }
        });

        addTabPane.addChangeListener(r -> {
            if (r.getSource() instanceof JTabbedPane) {
                JTabbedPane pane = (JTabbedPane) r.getSource();
                switch (pane.getSelectedIndex()) {
                    case 0://agency
                        создатьАгенствоButton.addActionListener(e -> repository.addAgency(textField1.getText()));
                        break;
                    case 1://airplane
                        создатьСамолетButton.addActionListener(e -> {
                            try {
                                repository.addAirplane(
                                        textField2.getText(),
                                        textField3.getText(),
                                        textField4.getText(),
                                        грузовойCheckBox1.isSelected()
                                );
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Введите данные корректно");
                            }
                        });
                        break;
                    case 2://excursion
                        List<Agency> agencyList = repository.getAgencies();
                        comboBox1.setModel(getAgenciesModel(agencyList));
                        создатьЭкскурсиюButton.addActionListener(e -> {
                            if (parseDate(comboBox2, comboBox3, comboBox4, null, null, null)) return;
                            repository.addExcursion(agencyList.get(comboBox1.getSelectedIndex()), dateIn, textField5.getText());
                        });
                        break;
                    case 3://flight
                        List<Airplane> airplaneList = repository.getAirplaneList();
                        comboBox8.setModel(getAirplanesModel(airplaneList));
                        создатьРейсButton.addActionListener(e -> {
                            if (parseDate(comboBox5, comboBox6, comboBox7, null, null, null)) return;
                            repository.addFlight(airplaneList.get(comboBox8.getSelectedIndex()), dateIn);
                        });
                        break;
                    case 4://hotel
                        создатьОтельButton.addActionListener(e -> {
                            repository.addHotel(textField6.getText());
                        });
                        break;
                    case 5://passenger
                        List<Flight> flightList = repository.getFlightList();
                        List<Tourist> touristList = repository.getTouristList();
                        comboBox9.setModel(getFlightModel(flightList));
                        comboBox10.setModel(getTouristModel(touristList));
                        создатьПассажираButton.addActionListener(e -> {
                            repository.addPassenger(flightList.get(comboBox9.getSelectedIndex()), touristList.get(comboBox10.getSelectedIndex()));
                        });
                        break;
                    case 6://room
                        List<Hotel> hotelList = repository.getHotelList();
                        comboBox11.setModel(getHotelModel(hotelList));
                        создатьКомнатуButton.addActionListener(e -> {
                            repository.addRoom(hotelList.get(comboBox11.getSelectedIndex()), textField7.getText());
                        });
                        break;
                }
            }
        });

        alterTabPane.addChangeListener(r -> {
            if (r.getSource() instanceof JTabbedPane) {
                JTabbedPane pane = (JTabbedPane) r.getSource();
                switch (pane.getSelectedIndex()) {
                    case 0: {//agency
                        List<Agency> agencyList = repository.getAgencies();
                        comboBox12.setModel(getAgenciesModel(agencyList));
                        изменитьАгенствоButton.addActionListener(e -> repository.alterAgency(agencyList.get(comboBox12.getSelectedIndex()), textField8.getText()));
                        break;
                    }
                    case 1: { //airplane
                        List<Airplane> airplaneList = repository.getAirplaneList();
                        comboBox13.setModel(getAirplanesModel(airplaneList));
                        изменитьСамолетButton.addActionListener(e -> {
                            try {
                                repository.alterAirplane(
                                        airplaneList.get(comboBox13.getSelectedIndex()),
                                        textField9.getText(),
                                        textField10.getText(),
                                        textField11.getText(),
                                        грузовойCheckBox.isSelected()
                                );
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Введите данные корректно");
                            }
                        });
                        break;
                    }
                    case 2: { //cargo
                        List<Cargo> cargoList = repository.getCargoList();
                        comboBox14.setModel(getCargoModel(cargoList));
                        comboBox14.addItemListener(e -> {
                            if (e.getStateChange() == ItemEvent.SELECTED) {
                                List<Warehouse> warehouseList = repository.getWarehouseList();
                                comboBox16.setModel(getWarehouseModel(warehouseList));
                                List<Flight> flightList = repository.getFlightList();
                                comboBox17.setModel(getFlightModel(flightList));
                            }
                        });
                        изменитьГрузButton.addActionListener(e -> {
                            if (textField12.getText().isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Заполните все поля");
                                return;
                            }
                            if (parseDate(comboBox18, comboBox19, comboBox20, comboBox15, comboBox56, comboBox57))
                                return;
                            repository.alterCargo(
                                    cargoList.get(comboBox14.getSelectedIndex()),
                                    repository.getWarehouseList().get(comboBox16.getSelectedIndex()), //так делать плохо, но слишком много лишнего кода для нормального способа
                                    repository.getFlightList().get(comboBox17.getSelectedIndex()),
                                    dateIn,
                                    dateOut,
                                    textField12.getText()
                            );
                        });
                        break;
                    }
                    case 3: {//excursion
                        List<Excursion> excursionList = repository.getExcursions();
                        comboBox58.setModel(getExcursionModel(excursionList));
                        List<Agency> agencyList = repository.getAgencies();
                        comboBox21.setModel(getAgenciesModel(agencyList));
                        изменитьЭкскурсиюButton.addActionListener(e -> {
                            if (parseDate(comboBox22, comboBox23, comboBox24, null, null, null)) return;
                            if (textField13.getText().isEmpty()) {
                                JOptionPane.showMessageDialog(null, "некорректно введены данные");
                                return;
                            }
                            repository.alterExcursion(
                                    excursionList.get(comboBox58.getSelectedIndex()),
                                    agencyList.get(comboBox21.getSelectedIndex()),
                                    dateIn,
                                    textField13.getText()
                            );
                        });
                        break;
                    }
                    case 4: {//flight
                        List<Airplane> airplaneList = repository.getAirplaneList();
                        comboBox28.setModel(getAirplanesModel(airplaneList));
                        List<Flight> flightList = repository.getFlightList();
                        comboBox59.setModel(getFlightModel(flightList));
                        изменитьРейсButton.addActionListener(e -> {
                            if (parseDate(comboBox25, comboBox26, comboBox27, null, null, null)) return;
                            repository.alterFlight(flightList.get(comboBox59.getSelectedIndex()), airplaneList.get(comboBox28.getSelectedIndex()), dateIn);
                        });
                        break;
                    }
                    case 5: {//hotel
                        List<Hotel> hotelList = repository.getHotelList();
                        comboBox60.setModel(getHotelModel(hotelList));
                        изменитьОтельButton.addActionListener(e -> repository.alterHotel(hotelList.get(comboBox60.getSelectedIndex()), textField14.getText()));
                        break;
                    }
                    case 6: {//passenger
                        List<Passenger> passengerList = repository.getPassengerList();
                        List<Flight> flightList = repository.getFlightList();
                        List<Tourist> touristList = repository.getTouristList();
                        comboBox30.setModel(getPassengerModel(passengerList));
                        comboBox31.setModel(getFlightModel(flightList));
                        comboBox29.setModel(getTouristModel(touristList));
                        изменитьПассажираButton.addActionListener(e -> {
                            repository.alterPassenger(
                                    passengerList.get(comboBox30.getSelectedIndex()),
                                    flightList.get(comboBox31.getSelectedIndex()),
                                    touristList.get(comboBox29.getSelectedIndex()));
                        });
                        break;
                    }
                    case 7: { //room
                        List<Room> roomList = repository.getRoomList();
                        List<Hotel> hotelList = repository.getHotelList();
                        comboBox34.setModel(getHotelModel(hotelList));
                        comboBox32.setModel(getRoomModel(roomList));
                        изменитьКомнатуButton.addActionListener(e -> {
                            try {
                                repository.alterRoom(
                                        roomList.get(comboBox32.getSelectedIndex()),
                                        hotelList.get(comboBox34.getSelectedIndex()),
                                        textField16.getText()
                                );
                            } catch (Exception exception) {
                                JOptionPane.showMessageDialog(null, "Некорректно введены данные");
                            }
                        });
                        break;
                    }
                    case 8: {
                        List<Trip> tripList = repository.getTripList();
                        comboBox35.setModel(getTripModel(tripList));
                        List<Room> roomList = repository.getRoomList();
                        comboBox43.setModel(getRoomModel(roomList));
                        изменитьПоездкуButton.addActionListener(e -> {
                            if (parseDate(comboBox36, comboBox38, comboBox40, comboBox37, comboBox39, comboBox41))
                                return;
                            if (textField15.getText().isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Некорректно введены данные");
                            }
                            repository.alterTrip(
                                    tripList.get(comboBox35.getSelectedIndex()),
                                    textField15.getText(),
                                    dateIn,
                                    dateOut,
                                    roomList.get(comboBox43.getSelectedIndex())
                            );
                        });
                        break;
                    }
                }
            }
        });

        deleteTabPane.addChangeListener(r -> {
            if (r.getSource() instanceof JTabbedPane) {
                JTabbedPane pane = (JTabbedPane) r.getSource();
                switch (pane.getSelectedIndex()) {
                    case 0: {//agency
                        List<Agency> agencyList = repository.getAgencies();
                        comboBox44.setModel(getAgenciesModel(agencyList));
                        удалитьАгенствоButton.addActionListener(e -> repository.deleteAgency(agencyList.get(comboBox44.getSelectedIndex())));
                        break;
                    }
                    case 1: {//airplane
                        List<Airplane> airplaneList = repository.getAirplaneList();
                        comboBox45.setModel(getAirplanesModel(airplaneList));
                        удалитьСамолетButton.addActionListener(e -> repository.deleteAirplane(airplaneList.get(comboBox45.getSelectedIndex())));
                        break;
                    }
                    case 2: { //tourist
                        List<Tourist> touristList = repository.getTouristList();
                        comboBox46.setModel(getTouristModel(touristList));
                        удалитьТуристаButton.addActionListener(e -> repository.deleteTourist(touristList.get(comboBox46.getSelectedIndex())));
                        break;
                    }
                    case 3: { //cargo
                        List<Cargo> cargoList = repository.getCargoList();
                        comboBox47.setModel(getCargoModel(cargoList));
                        удалитьГрузButton.addActionListener(e -> repository.deleteCargo(cargoList.get(comboBox47.getSelectedIndex())));
                        break;
                    }
                    case 4: {//excursion
                        List<Excursion> agencyList = repository.getExcursions();
                        comboBox48.setModel(getExcursionModel(agencyList));
                        удалитьЭкскурсиюButton.addActionListener(e -> repository.deleteExcursion(agencyList.get(comboBox48.getSelectedIndex())));
                        break;
                    }
                    case 5: {//flight
                        List<Flight> airplaneList = repository.getFlightList();
                        comboBox49.setModel(getFlightModel(airplaneList));
                        удалитьРейсButton.addActionListener(e -> repository.deleteFlight(airplaneList.get(comboBox49.getSelectedIndex())));
                        break;
                    }
                    case 6: {//hotel
                        List<Hotel> hotelList = repository.getHotelList();
                        comboBox50.setModel(getHotelModel(hotelList));
                        удалитьОтельButton.addActionListener(e -> repository.deleteHotel(hotelList.get(comboBox50.getSelectedIndex())));
                        break;
                    }
                    case 7: {//passenger
                        List<Tourist> touristList = repository.getTouristList();
                        List<Flight> flightList = repository.getFlightList();
                        comboBox51.setModel(getFlightModel(flightList));
                        comboBox52.setModel(getTouristModel(touristList));
                        удалитьПассажираButton.addActionListener(e -> repository.deletePassenger(touristList.get(comboBox52.getSelectedIndex()), flightList.get(comboBox51.getSelectedIndex())));
                        break;
                    }
                    case 8: {//room
                        List<Room> hotelList = repository.getRoomList();
                        comboBox53.setModel(getRoomModel(hotelList));
                        удалитьКомнатуButton.addActionListener(e -> repository.deleteRoom(hotelList.get(comboBox53.getSelectedIndex())));
                        break;
                    }
                    case 9: {//trip
                        List<Trip> tripList = repository.getTripList();
                        comboBox55.setModel(getTripModel(tripList));
                        удалитьКомнатуButton.addActionListener(e -> repository.deleteTrip(tripList.get(comboBox55.getSelectedIndex())));
                        break;
                    }
                }
            }
        });
    }

    private DefaultComboBoxModel getAirplanesModel(List<Airplane> airplanes) {
        Long[] ids = new Long[airplanes.size()];
        int i = 0;
        for (Airplane a : airplanes)
            ids[i++] = a.getId();
        return new DefaultComboBoxModel(ids);
    }

    private DefaultComboBoxModel getAgenciesModel(List<Agency> agencies) {
        String[] ids = new String[agencies.size()];
        int i = 0;
        for (Agency a : agencies)
            ids[i++] = a.getName();
        return new DefaultComboBoxModel(ids);
    }

    private DefaultComboBoxModel getTouristModel(List<Tourist> tourists) {
        String[] ids = new String[tourists.size()];
        int i = 0;
        for (Tourist a : tourists)
            ids[i++] = a.getName();
        return new DefaultComboBoxModel(ids);
    }

    private DefaultComboBoxModel getFlightModel(List<Flight> flights) {
        Long[] ids = new Long[flights.size()];
        int i = 0;
        for (Flight a : flights)
            ids[i++] = a.getId();
        return new DefaultComboBoxModel(ids);
    }

    private DefaultComboBoxModel getHotelModel(List<Hotel> flights) {
        String[] ids = new String[flights.size()];
        int i = 0;
        for (Hotel a : flights)
            ids[i++] = a.getTitle();
        return new DefaultComboBoxModel(ids);
    }

    private DefaultComboBoxModel getCargoModel(List<Cargo> flights) {
        Long[] ids = new Long[flights.size()];
        int i = 0;
        for (Cargo a : flights)
            ids[i++] = a.getId();
        return new DefaultComboBoxModel(ids);
    }

    private DefaultComboBoxModel getWarehouseModel(List<Warehouse> flights) {
        Long[] ids = new Long[flights.size()];
        int i = 0;
        for (Warehouse a : flights)
            ids[i++] = a.getId();
        return new DefaultComboBoxModel(ids);
    }

    private DefaultComboBoxModel getExcursionModel(List<Excursion> flights) {
        String[] ids = new String[flights.size()];
        int i = 0;
        for (Excursion a : flights)
            ids[i++] = a.getTitle();
        return new DefaultComboBoxModel(ids);
    }

    private DefaultComboBoxModel getPassengerModel(List<Passenger> flights) {
        Long[] ids = new Long[flights.size()];
        int i = 0;
        for (Passenger a : flights)
            ids[i++] = a.getId();
        return new DefaultComboBoxModel(ids);
    }

    private DefaultComboBoxModel getRoomModel(List<Room> flights) {
        Long[] ids = new Long[flights.size()];
        int i = 0;
        for (Room a : flights)
            ids[i++] = a.getId();
        return new DefaultComboBoxModel(ids);
    }

    private DefaultComboBoxModel getTripModel(List<Trip> flights) {
        Long[] ids = new Long[flights.size()];
        int i = 0;
        for (Trip a : flights)
            ids[i++] = a.getId();
        return new DefaultComboBoxModel(ids);
    }

    private DefaultComboBoxModel getRoomNumbersModel(Map<Room, Integer> map) {
        Long[] ids = new Long[map.size()];
        int i = 0;
        for (Map.Entry<Room, Integer> entry : map.entrySet())
            ids[i++] = Long.valueOf(entry.getValue());
        return new DefaultComboBoxModel(ids);
    }

    private boolean parseDate(JComboBox startD, JComboBox startM, JComboBox startY, JComboBox endD, JComboBox endM, JComboBox endY) {
        dateIn = null;
        dateOut = null;
        int sd = Integer.parseInt(String.valueOf(startD.getSelectedItem()));
        String sm = (String) startM.getSelectedItem();
        int sy = Integer.parseInt((String) startY.getSelectedItem());
        if (sm.equals("February") && sd > 28) {
            if (sd > 29 && sy % 4 == 0) {
                JOptionPane.showMessageDialog(null, "В февраля меньше 30 дней");
                return true;
            }
            if (sd == 29 && sy % 4 != 0) {
                JOptionPane.showMessageDialog(null, "Это не високосный год, исправьте, пожалуйста");
                return true;
            }
        }
        int monthIndexStart = startM.getSelectedIndex();
        Calendar calendarDate = new GregorianCalendar(sy, monthIndexStart, sd);
        dateIn = calendarDate.getTimeInMillis();
        dateOut = null;
        try {
            int ed = Integer.parseInt(String.valueOf(endD.getSelectedItem()));
            String em = (String) endM.getSelectedItem();
            int ey = Integer.parseInt((String) endY.getSelectedItem());
            if (em.equals("February") && ed > 28) {
                if (ed > 29 && ey % 4 == 0) {
                    JOptionPane.showMessageDialog(null, "В февраля меньше 30 дней");
                    return true;
                }
                if (ed == 29 && ey % 4 != 0) {
                    JOptionPane.showMessageDialog(null, "Это не високосный год, исправьте, пожалуйста");
                    return true;
                }
            }
            int monthIndexEnd = endM.getSelectedIndex();
            calendarDate = new GregorianCalendar(ey, monthIndexEnd, ed);
            dateOut = calendarDate.getTimeInMillis();
            if (dateIn > dateOut) {
                JOptionPane.showMessageDialog(null, "Нельзя поставить дату начала поездки позже даты конца");
                return true;
            }
        } catch (Exception ignored) {

        }
        return false;
    }

}