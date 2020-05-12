package infrastructure.ui;

import models.entity.*;
import repository.Repository;

import javax.swing.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

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
    private JComboBox comboBox15;
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
    private JComboBox comboBox33;
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
    private JComboBox comboBox54;
    private JButton удалитьКомнатуButton;
    private JComboBox comboBox55;
    private JButton удалитьПоездкуButton;
    private JButton изменитьПоездкуButton;

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
                    case 0://agency
                        List<Agency> agencyList = repository.getAgencies();
                        comboBox12.setModel(getAgenciesModel(agencyList));
                        изменитьАгенствоButton.addActionListener(e -> repository.alterAgency(agencyList.get(comboBox12.getSelectedIndex()), textField8.getText()));
                        break;
                    case 1://airplane

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