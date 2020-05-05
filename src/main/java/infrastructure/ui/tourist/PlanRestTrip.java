package infrastructure.ui.tourist;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import models.entity.Excursion;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class PlanRestTrip extends JFrame {
    private JPanel totalPanel;
    private JTextField countryField;
    private JComboBox startD;
    private JComboBox startM;
    private JComboBox startY;
    private JComboBox endD;
    private JComboBox endM;
    private JComboBox endY;
    private JButton ОКButton;
    private JPanel listPanel;
    private JScrollPane scrollPanel;
    private JButton button1;
    private JComboBox addExcursion;
    private JPanel excursionTemplatePanel;
    private JButton button2;

    public PlanRestTrip(Controller controller, Long id, List<Excursion> joinedExcursions) {
        setTitle("Plan Trip with Excursions");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
        fillListPanel(joinedExcursions);
        String[] titleExcursions = new String[controller.getExcursions().size()];
        int i = 0;
        for (Excursion ex : controller.getExcursions())
            titleExcursions[i++] = ex.getTitle();
        addExcursion.setModel(new DefaultComboBoxModel(titleExcursions));
        button1.addActionListener(e -> {
            Excursion newExcursion = controller.getExcursions().get(addExcursion.getSelectedIndex());
            for (Excursion excursion : joinedExcursions)
                if (excursion.getId().equals(newExcursion.getId()))
                    return;
            joinedExcursions.add(newExcursion);
            listPanel.removeAll();
            fillListPanel(joinedExcursions);
            validate();
            repaint();
        });
        ОКButton.addActionListener(e -> {
            if (countryField.getText().isEmpty() || joinedExcursions.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Заполните все поля");
                return;
            }
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
            dispose();
            controller.addNewRestTrip(id, countryField.getText(), new Timestamp(dateIn), new Timestamp(dateOut), joinedExcursions);
            controller.clearJoinedExcursion();
        });
    }

    private void fillListPanel(List<Excursion> joinedExcursions) {
        listPanel.setLayout(new GridLayout(joinedExcursions.size(), 1));
        int i = 0;
        for (Excursion ex : joinedExcursions) {
            JPanel excursionPanel = getExcursionTemplatePanel(
                    ex.getTitle(),
                    ex.getId(),
                    joinedExcursions
            );
            excursionPanel.setBackground((i++ % 2 == 0) ? new Color(242, 242, 242) : new Color(220, 220, 220));
            listPanel.add(excursionPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        }
    }

    private JPanel getExcursionTemplatePanel(String title, long id, List<Excursion> joinedExcursions) {
        excursionTemplatePanel = new JPanel();
        excursionTemplatePanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), 5, 10));
        final JLabel label4 = new JLabel();
        label4.setText(title);
        excursionTemplatePanel.add(label4, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        button2 = new JButton();
        button2.setText("-");
        button2.addActionListener(e -> {
            for (Excursion excursion : joinedExcursions)
                if (excursion.getId().equals(id)) {
                    joinedExcursions.remove(excursion);
                    break;
                }
            listPanel.removeAll();
            fillListPanel(joinedExcursions);
            validate();
            repaint();
        });
        excursionTemplatePanel.add(button2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(30, -1), null, 0, false));
        return excursionTemplatePanel;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        totalPanel = new JPanel();
        totalPanel.setLayout(new GridLayoutManager(9, 6, new Insets(0, 0, 0, 0), -1, -1));
        totalPanel.setPreferredSize(new Dimension(460, 270));
        final JLabel label1 = new JLabel();
        label1.setText("Страна посещения");
        totalPanel.add(label1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        countryField = new JTextField();
        totalPanel.add(countryField, new GridConstraints(1, 2, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("С:");
        totalPanel.add(label2, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        startM = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("January");
        defaultComboBoxModel1.addElement("February");
        defaultComboBoxModel1.addElement("March");
        defaultComboBoxModel1.addElement("April");
        defaultComboBoxModel1.addElement("May");
        defaultComboBoxModel1.addElement("June");
        defaultComboBoxModel1.addElement("July");
        defaultComboBoxModel1.addElement("August");
        defaultComboBoxModel1.addElement("September");
        defaultComboBoxModel1.addElement("October");
        defaultComboBoxModel1.addElement("November");
        defaultComboBoxModel1.addElement("December");
        startM.setModel(defaultComboBoxModel1);
        totalPanel.add(startM, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        startY = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("2020");
        defaultComboBoxModel2.addElement("2021");
        defaultComboBoxModel2.addElement("2022");
        defaultComboBoxModel2.addElement("2023");
        defaultComboBoxModel2.addElement("2024");
        startY.setModel(defaultComboBoxModel2);
        totalPanel.add(startY, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("По:");
        totalPanel.add(label3, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        endD = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("1");
        defaultComboBoxModel3.addElement("2");
        defaultComboBoxModel3.addElement("3");
        defaultComboBoxModel3.addElement("4");
        defaultComboBoxModel3.addElement("5");
        defaultComboBoxModel3.addElement("6");
        defaultComboBoxModel3.addElement("7");
        defaultComboBoxModel3.addElement("8");
        defaultComboBoxModel3.addElement("9");
        defaultComboBoxModel3.addElement("10");
        defaultComboBoxModel3.addElement("11");
        defaultComboBoxModel3.addElement("12");
        defaultComboBoxModel3.addElement("13");
        defaultComboBoxModel3.addElement("14");
        defaultComboBoxModel3.addElement("15");
        defaultComboBoxModel3.addElement("16");
        defaultComboBoxModel3.addElement("17");
        defaultComboBoxModel3.addElement("18");
        defaultComboBoxModel3.addElement("19");
        defaultComboBoxModel3.addElement("20");
        defaultComboBoxModel3.addElement("21");
        defaultComboBoxModel3.addElement("22");
        defaultComboBoxModel3.addElement("23");
        defaultComboBoxModel3.addElement("24");
        defaultComboBoxModel3.addElement("25");
        defaultComboBoxModel3.addElement("26");
        defaultComboBoxModel3.addElement("27");
        defaultComboBoxModel3.addElement("28");
        defaultComboBoxModel3.addElement("29");
        defaultComboBoxModel3.addElement("30");
        defaultComboBoxModel3.addElement("31");
        endD.setModel(defaultComboBoxModel3);
        totalPanel.add(endD, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        endM = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel4 = new DefaultComboBoxModel();
        defaultComboBoxModel4.addElement("January");
        defaultComboBoxModel4.addElement("February");
        defaultComboBoxModel4.addElement("March");
        defaultComboBoxModel4.addElement("April");
        defaultComboBoxModel4.addElement("May");
        defaultComboBoxModel4.addElement("June");
        defaultComboBoxModel4.addElement("July");
        defaultComboBoxModel4.addElement("August");
        defaultComboBoxModel4.addElement("September");
        defaultComboBoxModel4.addElement("October");
        defaultComboBoxModel4.addElement("November");
        defaultComboBoxModel4.addElement("December");
        endM.setModel(defaultComboBoxModel4);
        totalPanel.add(endM, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        endY = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel5 = new DefaultComboBoxModel();
        defaultComboBoxModel5.addElement("2020");
        defaultComboBoxModel5.addElement("2021");
        defaultComboBoxModel5.addElement("2022");
        defaultComboBoxModel5.addElement("2023");
        defaultComboBoxModel5.addElement("2024");
        endY.setModel(defaultComboBoxModel5);
        totalPanel.add(endY, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        startD = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel6 = new DefaultComboBoxModel();
        defaultComboBoxModel6.addElement("1");
        defaultComboBoxModel6.addElement("2");
        defaultComboBoxModel6.addElement("3");
        defaultComboBoxModel6.addElement("4");
        defaultComboBoxModel6.addElement("5");
        defaultComboBoxModel6.addElement("6");
        defaultComboBoxModel6.addElement("7");
        defaultComboBoxModel6.addElement("8");
        defaultComboBoxModel6.addElement("9");
        defaultComboBoxModel6.addElement("10");
        defaultComboBoxModel6.addElement("11");
        defaultComboBoxModel6.addElement("12");
        defaultComboBoxModel6.addElement("13");
        defaultComboBoxModel6.addElement("14");
        defaultComboBoxModel6.addElement("15");
        defaultComboBoxModel6.addElement("16");
        defaultComboBoxModel6.addElement("17");
        defaultComboBoxModel6.addElement("18");
        defaultComboBoxModel6.addElement("19");
        defaultComboBoxModel6.addElement("20");
        defaultComboBoxModel6.addElement("21");
        defaultComboBoxModel6.addElement("22");
        defaultComboBoxModel6.addElement("23");
        defaultComboBoxModel6.addElement("24");
        defaultComboBoxModel6.addElement("25");
        defaultComboBoxModel6.addElement("26");
        defaultComboBoxModel6.addElement("27");
        defaultComboBoxModel6.addElement("28");
        defaultComboBoxModel6.addElement("29");
        defaultComboBoxModel6.addElement("30");
        defaultComboBoxModel6.addElement("31");
        startD.setModel(defaultComboBoxModel6);
        totalPanel.add(startD, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        totalPanel.add(panel1, new GridConstraints(4, 1, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(250, 70), null, 0, false));
        scrollPanel = new JScrollPane();
        scrollPanel.setHorizontalScrollBarPolicy(31);
        panel1.add(scrollPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        listPanel = new JPanel();
        listPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        scrollPanel.setViewportView(listPanel);
        ОКButton = new JButton();
        ОКButton.setText("ОК");
        totalPanel.add(ОКButton, new GridConstraints(7, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        totalPanel.add(spacer1, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 10), null, 0, false));
        final Spacer spacer2 = new Spacer();
        totalPanel.add(spacer2, new GridConstraints(8, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 10), null, 0, false));
        final Spacer spacer3 = new Spacer();
        totalPanel.add(spacer3, new GridConstraints(3, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, 1, null, new Dimension(10, -1), null, 0, false));
        final Spacer spacer4 = new Spacer();
        totalPanel.add(spacer4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, 1, null, new Dimension(10, -1), null, 0, false));
        button1 = new JButton();
        button1.setText("+");
        totalPanel.add(button1, new GridConstraints(5, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addExcursion = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel7 = new DefaultComboBoxModel();
        defaultComboBoxModel7.addElement("экскурсии");
        addExcursion.setModel(defaultComboBoxModel7);
        totalPanel.add(addExcursion, new GridConstraints(5, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        totalPanel.add(spacer5, new GridConstraints(6, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 10), null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return totalPanel;
    }
}
