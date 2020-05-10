package infrastructure.ui.admin;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import models.entity.Flight;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class GetFlightForDate extends JFrame{
    private JComboBox startD;
    private JComboBox startM;
    private JComboBox startY;
    private JPanel listPanel;
    private JButton применитьButton;
    private JPanel totalPanel;
    private long dateIn;

    public GetFlightForDate(Controller controller) {
        setTitle("Main frame");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);


        применитьButton.addActionListener(e -> {
            if (parseDate()) return;
            fillListPanel(controller.getFlightListForDate(dateIn));
        });
    }

    private void fillListPanel(List<Flight> flights) {
        listPanel.removeAll();
        listPanel.setLayout(new GridLayoutManager(flights.size(), 1, new Insets(0, 0, 0, 0), -1, -1));
        int i = 0;
        for (Flight t : flights) {
            JPanel cargoTemplate = getCargoPanelTemplate(t);
            cargoTemplate.setBackground((i % 2 == 0) ? new Color(242, 242, 242) : new Color(220, 220, 220));
            listPanel.add(cargoTemplate, new GridConstraints(i++, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        }
        validate();
        repaint();
    }

    private JPanel getCargoPanelTemplate(Flight t) {
        JPanel template = new JPanel();
        template.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        listPanel.add(template, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Рейс: " + t.getId());
        template.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Самолет " + (t.getAirplane().is_cargoplane() ? "грузовой" : "пассажирский"));
        template.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Дата и время: " + t.getDate());
        template.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        return template;
    }

    private boolean parseDate() {
        int sd = Integer.parseInt((String) startD.getSelectedItem());
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
        return false;
    }

}
