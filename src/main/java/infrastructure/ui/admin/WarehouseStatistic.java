package infrastructure.ui.admin;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import models.entity.Flight;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class WarehouseStatistic extends JFrame {
    private JPanel totalPanel;
    private JPanel listPanel;
    private JComboBox startD;
    private JComboBox startM;
    private JComboBox startY;
    private JComboBox endD;
    private JComboBox endM;
    private JComboBox endY;
    private JButton OKButton;
    private JLabel cargoplane;
    private JLabel passengerplane;

    public WarehouseStatistic(Controller controller, long warehouseId) {
        setTitle("Main frame");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);


        OKButton.addActionListener(e -> {
            int sd = Integer.parseInt((String) startD.getSelectedItem());
            String sm = (String) startM.getSelectedItem();
            int sy = Integer.parseInt((String) startY.getSelectedItem());
            int ed = Integer.parseInt((String) endD.getSelectedItem());
            String em = (String) endM.getSelectedItem();
            int ey = Integer.parseInt((String) endY.getSelectedItem());
            if (sm.equals("February") && sd > 28 || em.equals("February") && ed > 28) {
                if (sd > 29 && sy % 4 == 0 || ed > 29 && ey % 4 == 0) {
                    JOptionPane.showMessageDialog(null, "В февраля меньше 30 дней");
                    return;
                }
                if (sd == 29 && sy % 4 != 0 || ed == 29 && ey % 4 != 0) {
                    JOptionPane.showMessageDialog(null, "Это не високосный год, исправьте, пожалуйста");
                    return;
                }
            }
            int monthIndexStart = startM.getSelectedIndex();
            Calendar calendarDate = new GregorianCalendar(sy, monthIndexStart, sd);
            long dateIn = calendarDate.getTimeInMillis();
            int monthIndexEnd = endM.getSelectedIndex();
            calendarDate = new GregorianCalendar(ey, monthIndexEnd, ed);
            long dateOut = calendarDate.getTimeInMillis();
            if (dateIn > dateOut) {
                JOptionPane.showMessageDialog(null, "Нельзя поставить дату начала поездки позже даты конца");
                return;
            }
            Map<Flight, Integer> flightCargoNumMap = controller.getWarehouseStatistic(dateIn, dateOut, warehouseId);
            int cargoCount = 0;
            List<Float> weight = new ArrayList<>();
            int i = 0;
            Set<Flight> entry = flightCargoNumMap.keySet();
            for (Flight f : entry) {
                if (f.getAirplane().is_cargoplane())
                    cargoCount++;
                float sum = controller.getCargosWeight(f);
                weight.add(sum);
            }
            cargoplane.setText(String.valueOf(cargoCount));
            passengerplane.setText(String.valueOf(flightCargoNumMap.size() - cargoCount));
            fillListPanel(flightCargoNumMap, weight);
        });
    }

    private void fillListPanel(Map<Flight, Integer> excursions, List<Float> weight) {
        listPanel.removeAll();
        if (excursions.isEmpty()) {
            validate();
            repaint();
            return;
        }
        listPanel.setLayout(new GridLayout(excursions.size(), 1));
        int i = 0;
        int row = 0;
        for (Map.Entry<Flight, Integer> entry : excursions.entrySet()) {
            JPanel cargoTemplate = getCargoPanelTemplate(entry, weight.get(row));
            cargoTemplate.setBackground((i++ % 2 == 0) ? new Color(242, 242, 242) : new Color(220, 220, 220));
            listPanel.add(cargoTemplate, new GridConstraints(row++, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        }
        validate();
        repaint();
    }

    private JPanel getCargoPanelTemplate(Map.Entry<Flight, Integer> entry, Float weight) {
        JPanel template = new JPanel();
        template.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        listPanel.add(template, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Рейс " + entry.getKey().getId());
        template.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Количетсво груза: " + entry.getValue());
        template.add(label3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Вес " + weight);
        template.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        return template;
    }
}
