package infrastructure.ui.admin;

import api.Controller;
import models.entity.Flight;
import models.entity.Tourist;
import models.entity.Warehouse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class MainFrame extends JFrame {
    private JButton получитьСписокТуристовЗаButton;
    private JButton получитьСведенияОКонкретномButton;
    private JButton получитьСписокГостиницСButton;
    private JComboBox comboBox1;
    private JButton посмотретьРейсСамолетаНаButton;
    private JButton статистикаГрузооборотаСкладаButton;
    private JButton статистикаПоВидамГрузаButton;
    private JButton рентабельностьButton;
    private JButton сведениеОПассажирахРейсаButton;
    private JComboBox comboBox2;
    private JPanel totalPanel;
    private JComboBox comboBox3;

    public MainFrame(Controller controller) {
        setTitle("Main frame");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        List<Tourist> touristList = controller.getTouristList();
        List<Flight> flightList = controller.getFlightList();
        List<Warehouse> warehouseList = controller.getWarehouseList();
        int i = 0;
        String[] touristNames = new String[touristList.size()];
        Long[] flightIds = new Long[flightList.size()];
        Long[] warehouseIds = new Long[warehouseList.size()];
        for (Tourist t : touristList)
            touristNames[i++] = t.getName();
        i = 0;
        for (Flight f : flightList)
            flightIds[i++] = f.getId();
        i = 0;
        for (Warehouse w : warehouseList)
            warehouseIds[i++] = w.getId();
        comboBox1.setModel(new DefaultComboBoxModel(touristNames));
        comboBox2.setModel(new DefaultComboBoxModel(flightIds));
        comboBox3.setModel(new DefaultComboBoxModel(warehouseIds));
        получитьСписокТуристовЗаButton.addActionListener(e -> new TouristListInCountry(controller));
        рентабельностьButton.addActionListener(e -> {
            List<Float> floatList = controller.getFinancialReportForAllPeriod();
            float income = 0.0f, expense = 0.0f;
            for (Float f : floatList) {
                if (f >= 0) {
                    income += f;
                } else {
                    expense += f;
                }
            }
            JOptionPane.showMessageDialog(null, "Рентабельность (д/р): " + Math.abs(income / expense));
        });
        посмотретьРейсСамолетаНаButton.addActionListener(e -> new GetFlightForDate(controller));
        сведениеОПассажирахРейсаButton.addActionListener(e -> new PassengersInfo(controller, flightList.get(comboBox2.getSelectedIndex())));
        статистикаПоВидамГрузаButton.addActionListener(e -> new CargoKindStatistic(controller));
        получитьСведенияОКонкретномButton.addActionListener(e -> new TouristStatistic(controller, touristList.get(comboBox1.getSelectedIndex())));
        получитьСписокГостиницСButton.addActionListener(e -> new HotelTookRooms(controller));
        статистикаГрузооборотаСкладаButton.addActionListener(e -> new WarehouseStatistic(controller, (Long) comboBox3.getSelectedItem()));
    }
}
