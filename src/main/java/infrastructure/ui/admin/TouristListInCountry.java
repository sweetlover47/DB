package infrastructure.ui.admin;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import models.entity.Tourist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class TouristListInCountry extends JFrame {
    private JTabbedPane tabbedPane1;
    private JCheckBox box11;
    private JCheckBox box12;
    private JComboBox startD;
    private JComboBox startM;
    private JComboBox startY;
    private JComboBox endD;
    private JComboBox endM;
    private JComboBox endY;
    private JButton applyButton;
    private JCheckBox box21;
    private JCheckBox box22;
    private JPanel totalListPanel;
    private JPanel periodListPanel;
    private JPanel totalPanel;
    private JComboBox countriesBox;
    private JButton применитьButton;
    private JLabel periodL;
    private JLabel totalL;
    private JLabel totalRatio;
    private JLabel periodRatio;
    private List<Tourist> totalRestTouristList;
    private List<Tourist> totalCargoTouristList;
    private List<Tourist> periodRestTouristList;
    private List<Tourist> periodCargoTouristList;
    String country;
    private long dateIn, dateOut;
    public TouristListInCountry(Controller controller) {
        setTitle("Main frame");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);

        List<String> countriesList = controller.getCountries();
        countriesBox.setModel(new DefaultComboBoxModel(countriesList.toArray(new String[0])));
        применитьButton.addActionListener(e -> {
            tabbedPane1.setEnabled(true);
            box11.setEnabled(true);
            box12.setEnabled(true);
            country = (String) countriesBox.getSelectedItem();
            totalRestTouristList = controller.getRestTouristListAllTime(country);
            totalCargoTouristList = controller.getCargoTouristListAllTime(country);
            totalL.setText("Количество: " + (totalRestTouristList.size() + totalCargoTouristList.size()));
            totalRatio.setText("Соотношение: " + ((float)totalRestTouristList.size() / totalCargoTouristList.size()));
            drawTotalPanel();
        });

        applyButton.addActionListener(e -> {
            box21.setEnabled(true);
            box22.setEnabled(true);
            if (parseDate()) return;
            periodRestTouristList = controller.getRestTouristListPeriod(country, dateIn, dateOut);
            periodCargoTouristList = controller.getCargoTouristListPeriod(country, dateIn, dateOut);
            periodL.setText("Количество: " + (periodRestTouristList.size() + periodCargoTouristList.size()));
            periodRatio.setText("Соотношение: " + ((float)periodRestTouristList.size() / periodCargoTouristList.size()));
            drawPeriodPanel();
        });
        box11.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                totalRestTouristList = controller.getRestTouristListAllTime(country);
                totalL.setText("Количество: " + (totalRestTouristList.size() + totalCargoTouristList.size()));
            }
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                totalRestTouristList.clear();
                totalL.setText("Количество: " + (totalRestTouristList.size() + totalCargoTouristList.size()));
            }
            drawTotalPanel();
        });
        box12.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                totalCargoTouristList = controller.getCargoTouristListAllTime(country);
                totalL.setText("Количество: " + (totalRestTouristList.size() + totalCargoTouristList.size()));
            }
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                totalCargoTouristList.clear();
                totalL.setText("Количество: " + (totalRestTouristList.size() + totalCargoTouristList.size()));
            }
            drawTotalPanel();
        });
        box21.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                periodRestTouristList = controller.getRestTouristListPeriod(country, dateIn, dateOut);
                periodL.setText("Количество: " + (periodRestTouristList.size() + periodCargoTouristList.size()));
            }
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                periodRestTouristList.clear();
                periodL.setText("Количество: " + (periodRestTouristList.size() + periodCargoTouristList.size()));
            }
            drawPeriodPanel();
        });
        box22.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                periodCargoTouristList = controller.getCargoTouristListPeriod(country, dateIn, dateOut);
                periodL.setText("Количество: " + (periodRestTouristList.size() + periodCargoTouristList.size()));
            }
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                periodCargoTouristList.clear();
                periodL.setText("Количество: " + (periodRestTouristList.size() + periodCargoTouristList.size()));
            }
            drawPeriodPanel();
        });
    }

    private boolean parseDate() {
        int sd = Integer.parseInt((String) startD.getSelectedItem());
        String sm = (String) startM.getSelectedItem();
        int sy = Integer.parseInt((String) startY.getSelectedItem());
        int ed = Integer.parseInt((String) endD.getSelectedItem());
        String em = (String) endM.getSelectedItem();
        int ey = Integer.parseInt((String) endY.getSelectedItem());
        if (sm.equals("February") && sd > 28 || em.equals("February") && ed > 28) {
            if (sd > 29 && sy % 4 == 0 || ed > 29 && ey % 4 == 0) {
                JOptionPane.showMessageDialog(null, "В февраля меньше 30 дней");
                return true;
            }
            if (sd == 29 && sy % 4 != 0 || ed == 29 && ey % 4 != 0) {
                JOptionPane.showMessageDialog(null, "Это не високосный год, исправьте, пожалуйста");
                return true;
            }
        }
        int monthIndexStart = startM.getSelectedIndex();
        Calendar calendarDate = new GregorianCalendar(sy, monthIndexStart, sd);
        dateIn = calendarDate.getTimeInMillis();
        int monthIndexEnd = endM.getSelectedIndex();
        calendarDate = new GregorianCalendar(ey, monthIndexEnd, ed);
        dateOut = calendarDate.getTimeInMillis();
        if (dateIn > dateOut) {
            JOptionPane.showMessageDialog(null, "Нельзя поставить дату начала поездки позже даты конца");
            return true;
        }
        return false;
    }

    private void drawTotalPanel() {
        totalListPanel.removeAll();
        totalListPanel.setLayout(new GridLayout(totalCargoTouristList.size() + totalRestTouristList.size(), 1));
        fillTotalListPanel(totalRestTouristList, "Отдыхающий турист");
        fillTotalListPanel(totalCargoTouristList, "Турист с грузом");
        validate();
        repaint();
    }

    private void drawPeriodPanel() {
        periodListPanel.removeAll();
        periodListPanel.setLayout(new GridLayout(periodCargoTouristList.size() + periodRestTouristList.size(), 1));
        fillPeriodListPanel(periodRestTouristList, "Отдыхающий турист");
        fillPeriodListPanel(periodCargoTouristList, "Турист с грузом");
        validate();
        repaint();
    }

    private void fillTotalListPanel(List<Tourist> tourists, String type) {
        int i = 0;
        for (Tourist t : tourists) {
            JPanel cargoTemplate = getCargoPanelTemplate(t, type, totalListPanel);
            cargoTemplate.setBackground((i++ % 2 == 0) ? new Color(242, 242, 242) : new Color(220, 220, 220));
            totalListPanel.add(cargoTemplate, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        }
    }

    private void fillPeriodListPanel(List<Tourist> tourists, String type) {
        int i = 0;
        for (Tourist t : tourists) {
            JPanel cargoTemplate = getCargoPanelTemplate(t, type, periodListPanel);
            cargoTemplate.setBackground((i++ % 2 == 0) ? new Color(242, 242, 242) : new Color(220, 220, 220));
            periodListPanel.add(cargoTemplate, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        }
    }

    private JPanel getCargoPanelTemplate(Tourist t, String type, JPanel listPanel) {
        JPanel template = new JPanel();
        template.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        listPanel.add(template, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText(t.getName());
        template.add(label2, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText(type);
        template.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        return template;
    }

}
