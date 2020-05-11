package infrastructure.ui.admin;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import models.entity.Agency;
import models.entity.Hotel;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class HotelTookRooms extends JFrame {
    private JPanel totalPanel;
    private JPanel listPanel;
    private JComboBox startD;
    private JComboBox startM;
    private JComboBox startY;
    private JComboBox endD;
    private JComboBox endM;
    private JComboBox endY;
    private JButton OKButton;

    public HotelTookRooms(Controller controller) {
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
            Map<Hotel, Integer> countTookRooms = controller.getHotelTookRooms(dateIn, dateOut);
            fillListPanel(countTookRooms);
        });
    }
    private void fillListPanel(Map<Hotel, Integer> excursions) {
        listPanel.removeAll();
        if (excursions.isEmpty()) return;
        listPanel.setLayout(new GridLayout(excursions.size(), 1));
        int i = 0;
        int row = 0;
        for (Map.Entry<Hotel, Integer> entry : excursions.entrySet()) {
            JPanel cargoTemplate = getCargoPanelTemplate(entry);
            cargoTemplate.setBackground((i++ % 2 == 0) ? new Color(242, 242, 242) : new Color(220, 220, 220));
            listPanel.add(cargoTemplate, new GridConstraints(row++, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        }
        validate();
        repaint();
    }

    private JPanel getCargoPanelTemplate(Map.Entry<Hotel, Integer> entry) {
        JPanel template = new JPanel();
        template.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        listPanel.add(template, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Отель \""+entry.getKey().getTitle()+"\"");
        template.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Количество занятых номеров " + entry.getValue());
        template.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        return template;
    }

}
