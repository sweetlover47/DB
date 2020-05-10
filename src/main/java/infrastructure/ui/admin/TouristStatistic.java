package infrastructure.ui.admin;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import models.entity.Cargo;
import models.entity.Excursion;
import models.entity.Tourist;

import javax.swing.*;

import java.awt.*;
import java.util.List;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class TouristStatistic extends JFrame{
    private JComboBox comboBox1;
    private JButton показатьButton;
    private JPanel totalPanel;
    private JPanel hotelListPanel;
    private JPanel exListPanel;
    private JPanel cargoListPanel;

    public TouristStatistic(Controller controller, Tourist t) {
        setTitle("Main frame");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);

        List<String> countries = controller.getCountries(t);
        comboBox1.setModel(new DefaultComboBoxModel(countries.toArray(new String[0])));
        показатьButton.addActionListener(e -> {
            List<Object[]> datesHotel = controller.getDatesHotelForTouristByCountry(t, (String) comboBox1.getSelectedItem());
            List<Cargo> cargoList = controller.getCargosForTourist(t,(String) comboBox1.getSelectedItem());
            List<Excursion> excursionList = controller.getExcursionsForTourist(t,(String) comboBox1.getSelectedItem());
            fillHotelListPanel(datesHotel);
            fillCargoListPanel(cargoList);
            fillExcursionListPanel(excursionList);
            validate();
            repaint();
        });
    }

    private void fillHotelListPanel(List<Object[]> datesHotel) {
        hotelListPanel.removeAll();
        if (datesHotel.isEmpty()) return;
        hotelListPanel.setLayout(new GridLayoutManager(datesHotel.size(), 1, new Insets(0, 0, 0, 0), -1, -1));
        int i = 0;
        for (Object[] obj : datesHotel) {
            JPanel cargoTemplate = getHotelDatePanelTemplate(obj);
            cargoTemplate.setBackground((i % 2 == 0) ? new Color(242, 242, 242) : new Color(220, 220, 220));
            hotelListPanel.add(cargoTemplate, new GridConstraints(i++, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        }
    }

    private JPanel getHotelDatePanelTemplate(Object[] obj) {
        JPanel template = new JPanel();
        template.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        hotelListPanel.add(template, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Отель: " + obj[2]);
        template.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("С " + obj[0]);
        template.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("По " + obj[1]);
        template.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        return template;
    }

    private void fillCargoListPanel(List<Cargo> datesHotel) {
        cargoListPanel.removeAll();
        if (datesHotel.isEmpty()) return;
        cargoListPanel.setLayout(new GridLayoutManager(datesHotel.size(), 1, new Insets(0, 0, 0, 0), -1, -1));
        int i = 0;
        for (Cargo obj : datesHotel) {
            JPanel cargoTemplate = getCargoPanelTemplate(obj);
            cargoTemplate.setBackground((i % 2 == 0) ? new Color(242, 242, 242) : new Color(220, 220, 220));
            cargoListPanel.add(cargoTemplate, new GridConstraints(i++, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        }
    }

    private JPanel getCargoPanelTemplate(Cargo obj) {
        JPanel template = new JPanel();
        template.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        cargoListPanel.add(template, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Груз №" + obj.getId());
        template.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Ведомость " + obj.getStatement().getId() + " на складе №" + obj.getWarehouse().getId());
        template.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Тип " + obj.getKind());
        template.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        return template;
    }

    private void fillExcursionListPanel(List<Excursion> datesHotel) {
        exListPanel.removeAll();
        if (datesHotel.isEmpty()) return;
        exListPanel.setLayout(new GridLayoutManager(datesHotel.size(), 1, new Insets(0, 0, 0, 0), -1, -1));
        int i = 0;
        for (Excursion obj : datesHotel) {
            JPanel cargoTemplate = getExcursionPanelTemplate(obj);
            cargoTemplate.setBackground((i % 2 == 0) ? new Color(242, 242, 242) : new Color(220, 220, 220));
            exListPanel.add(cargoTemplate, new GridConstraints(i++, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        }
    }

    private JPanel getExcursionPanelTemplate(Excursion ex) {
        JPanel template = new JPanel();
        template.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        exListPanel.add(template, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText(ex.getTitle());
        template.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Дата: " + ex.getDate());
        template.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Агенство: " + ex.getAgency().getName());
        template.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        return template;
    }


}
