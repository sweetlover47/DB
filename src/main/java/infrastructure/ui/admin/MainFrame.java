package infrastructure.ui.admin;

import api.Controller;
import models.entity.Flight;
import models.entity.Tourist;

import javax.swing.*;
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
        int i = 0;
        String[] touristNames = new String[touristList.size()];
        Long[] flightIds = new Long[flightList.size()];
        for (Tourist t : touristList)
            touristNames[i++] = t.getName();
        i = 0;
        for (Flight f : flightList)
            flightIds[i++] = f.getId();
        comboBox1.setModel(new DefaultComboBoxModel(touristNames));
        comboBox2.setModel(new DefaultComboBoxModel(flightIds));

        получитьСписокТуристовЗаButton.addActionListener(e -> new TouristListInCountry(controller));
    }
}
