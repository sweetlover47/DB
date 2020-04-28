package infrastructure.ui.tourist;

import api.Controller;
import models.entity.Excursion;
import models.entity.Tourist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class MainFrame extends JFrame {
    private JButton myInfoButton;
    private JButton myTripsButton;
    private JButton takeCargoButton;
    private JButton planTripButton;
    private JTextField searchField;
    private JButton searchButton;
    private JButton advanceSearchButton;
    private JPanel totalPanel;
    private JScrollPane scrollPanel;
    private JPanel listPanel;
    private JPanel templatePanel;
    private JLabel title;
    private JLabel agency;
    private JLabel countOrders;
    private JLabel date;
    private JPanel bigPanel;

    public MainFrame(Controller controller, Long id) {
        Tourist tourist = controller.getTouristById(id);
        setTitle("MainFrame");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        List<Excursion> excursionList = controller.getExcursions();
        listPanel.setLayout(new GridLayout(excursionList.size(), 1));
        int i = 0;
        for (Excursion excursion : excursionList) {
            JPanel excursionPanel = getExcursionPanelTemplate(
                    excursion.getTitle(),
                    excursion.getAgency().getName(),
                    String.valueOf(excursion.getNumOrders()),
                    excursion.getDate().toString()
            );
            excursionPanel.setBackground((i++ % 2 == 0) ? new Color(242,242,242) : new Color (152,152,152));
            excursionPanel.setPreferredSize(new Dimension(-1, 70));
            listPanel.add(excursionPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        }

        myInfoButton.addActionListener(e -> new Profile(controller, tourist.getId()));
        myTripsButton.addActionListener(e -> new MyTrips(controller.getTripList(tourist)));
        takeCargoButton.addActionListener(e -> new PlanCargoTrip(controller, tourist.getId()));
    }

    private JPanel getExcursionPanelTemplate(String t, String a, String o, String d) {
        templatePanel = new JPanel();
        templatePanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        title = new JLabel();
        title.setText(t);
        templatePanel.add(title, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        agency = new JLabel();
        agency.setText("Агенство " + a);
        templatePanel.add(agency, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        countOrders = new JLabel();
        countOrders.setText("Количество заказов: " + o);
        templatePanel.add(countOrders, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        date = new JLabel();
        date.setText("Дата проведения экскурсии: " + d);
        templatePanel.add(date, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        return templatePanel;
    }

}
