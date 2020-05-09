package infrastructure.ui.representative;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class ViewFinReport extends JFrame {
    private JPanel totalPanel;
    private JTabbedPane tabbedPane1;
    private JLabel excursionLabeltotal;
    private JLabel livingLabeltotal;
    private JLabel cargoLabeltotal;
    private JLabel flyLabeltotal;
    private JComboBox startD;
    private JComboBox startM;
    private JComboBox startY;
    private JComboBox endD;
    private JComboBox endM;
    private JComboBox endY;
    private JButton applyButton;
    private JLabel totalLabeltotal;
    private JLabel eLabel;
    private JLabel lLabel;
    private JLabel cLabel;
    private JLabel fLabel;
    private JLabel tLabel;
    private JCheckBox box11;
    private JCheckBox box12;
    private JCheckBox box21;
    private JCheckBox box22;
    private List<Float> totalList;
    private List<Float> periodList;
    private float totalSum = 0.0f;
    private float periodSum = 0.0f;

    public ViewFinReport(Controller controller) {
        setTitle("MainFrame");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
        totalList = controller.getFinancialReportForAllPeriod();
        totalSum = totalList.stream().reduce(0.0f, Float::sum);
        paintResult(excursionLabeltotal, livingLabeltotal, cargoLabeltotal, flyLabeltotal, totalLabeltotal, totalList);
        validate();
        repaint();
        applyButton.addActionListener(e -> {
            box21.setEnabled(true);
            box22.setEnabled(true);
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
            periodList = controller.getFinancialReportForPeriod(dateIn, dateOut);
            periodSum = periodList.stream().reduce(0.0f, Float::sum);
            paintResult(eLabel, lLabel, cLabel, fLabel, tLabel, periodList);
            validate();
            repaint();
        });
        box11.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                excursionLabeltotal.setText(String.valueOf(totalList.get(0)));
                totalSum += totalList.get(0);
                totalLabeltotal.setText(String.valueOf(totalSum));
            }
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                excursionLabeltotal.setText("+0.0");
                totalSum -= totalList.get(0);
                totalLabeltotal.setText(String.valueOf(totalSum));
            }
            validate();
            repaint();
        });
        box12.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                cargoLabeltotal.setText("+" + totalList.get(2));
                totalSum += totalList.get(2);
                totalLabeltotal.setText(String.valueOf(totalSum));
            }
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                cargoLabeltotal.setText("+0.0");
                totalSum -= totalList.get(2);
                totalLabeltotal.setText(String.valueOf(totalSum));
            }
            validate();
            repaint();
        });
        box21.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                eLabel.setText(String.valueOf(periodList.get(0)));
                periodSum += periodList.get(0);
                tLabel.setText(String.valueOf(periodSum));
            }
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                eLabel.setText("+0.0");
                periodSum -= periodList.get(0);
                tLabel.setText(String.valueOf(periodSum));
            }
            validate();
            repaint();
        });
        box22.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                cLabel.setText("+" + periodList.get(2));
                periodSum += periodList.get(2);
                tLabel.setText(String.valueOf(periodSum));
            }
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                cLabel.setText("+0.0");
                periodSum -= periodList.get(2);
                tLabel.setText(String.valueOf(periodSum));
            }
            validate();
            repaint();
        });
    }

    private void paintResult(JLabel exLavel, JLabel liLabel, JLabel caLabel, JLabel flLabel, JLabel itoLabel, List<Float> list) {
        exLavel.setText(
                String.valueOf(list.get(0) >= 0 ? "+" + list.get(0) : list.get(0))
        );
        liLabel.setText(
                String.valueOf(list.get(1) >= 0 ? "+" + list.get(1) : list.get(1))
        );
        caLabel.setText(
                String.valueOf(list.get(2) >= 0 ? "+" + list.get(2) : list.get(2))
        );
        flLabel.setText(
                String.valueOf(list.get(3) >= 0 ? "+" + list.get(3) : list.get(3))
        );
        float total = list.stream().reduce(0.0f, Float::sum);
        itoLabel.setText(
                total >= 0 ? "+" + String.valueOf(total) : String.valueOf(total)
        );
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
        totalPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        totalPanel.setPreferredSize(new Dimension(450, 280));
        tabbedPane1 = new JTabbedPane();
        totalPanel.add(tabbedPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(7, 2, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("За весь период", panel1);
        final JLabel label1 = new JLabel();
        label1.setText("Экскурсии:");
        panel1.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Проживание:");
        panel1.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Хранение груза:");
        panel1.add(label3, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Перелет");
        panel1.add(label4, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        excursionLabeltotal = new JLabel();
        excursionLabeltotal.setText("Label");
        panel1.add(excursionLabeltotal, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        livingLabeltotal = new JLabel();
        livingLabeltotal.setText("Label");
        panel1.add(livingLabeltotal, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cargoLabeltotal = new JLabel();
        cargoLabeltotal.setText("Label");
        panel1.add(cargoLabeltotal, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        flyLabeltotal = new JLabel();
        flyLabeltotal.setText("Label");
        panel1.add(flyLabeltotal, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JSeparator separator1 = new JSeparator();
        panel1.add(separator1, new GridConstraints(5, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 10), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Итого:");
        panel1.add(label5, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        totalLabeltotal = new JLabel();
        totalLabeltotal.setText("total");
        panel1.add(totalLabeltotal, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        box11 = new JCheckBox();
        box11.setSelected(true);
        box11.setText("Отдыхающие");
        panel2.add(box11, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        box12 = new JCheckBox();
        box12.setSelected(true);
        box12.setText("С грузом");
        panel2.add(box12, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(4, 4, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("За выбранный период", panel3);
        final JLabel label6 = new JLabel();
        label6.setText("С:");
        panel3.add(label6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        startD = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("1");
        defaultComboBoxModel1.addElement("2");
        defaultComboBoxModel1.addElement("3");
        defaultComboBoxModel1.addElement("4");
        defaultComboBoxModel1.addElement("5");
        defaultComboBoxModel1.addElement("6");
        defaultComboBoxModel1.addElement("7");
        defaultComboBoxModel1.addElement("8");
        defaultComboBoxModel1.addElement("9");
        defaultComboBoxModel1.addElement("10");
        defaultComboBoxModel1.addElement("11");
        defaultComboBoxModel1.addElement("12");
        defaultComboBoxModel1.addElement("13");
        defaultComboBoxModel1.addElement("14");
        defaultComboBoxModel1.addElement("15");
        defaultComboBoxModel1.addElement("16");
        defaultComboBoxModel1.addElement("17");
        defaultComboBoxModel1.addElement("18");
        defaultComboBoxModel1.addElement("19");
        defaultComboBoxModel1.addElement("20");
        defaultComboBoxModel1.addElement("21");
        defaultComboBoxModel1.addElement("22");
        defaultComboBoxModel1.addElement("23");
        defaultComboBoxModel1.addElement("24");
        defaultComboBoxModel1.addElement("25");
        defaultComboBoxModel1.addElement("26");
        defaultComboBoxModel1.addElement("27");
        defaultComboBoxModel1.addElement("28");
        defaultComboBoxModel1.addElement("29");
        defaultComboBoxModel1.addElement("30");
        defaultComboBoxModel1.addElement("31");
        startD.setModel(defaultComboBoxModel1);
        panel3.add(startD, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        startM = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("January");
        defaultComboBoxModel2.addElement("February");
        defaultComboBoxModel2.addElement("March");
        defaultComboBoxModel2.addElement("April");
        defaultComboBoxModel2.addElement("May");
        defaultComboBoxModel2.addElement("June");
        defaultComboBoxModel2.addElement("July");
        defaultComboBoxModel2.addElement("August");
        defaultComboBoxModel2.addElement("September");
        defaultComboBoxModel2.addElement("October");
        defaultComboBoxModel2.addElement("November");
        defaultComboBoxModel2.addElement("December");
        startM.setModel(defaultComboBoxModel2);
        panel3.add(startM, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        startY = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("2018");
        defaultComboBoxModel3.addElement("2019");
        defaultComboBoxModel3.addElement("2020");
        defaultComboBoxModel3.addElement("2021");
        defaultComboBoxModel3.addElement("2022");
        defaultComboBoxModel3.addElement("2023");
        defaultComboBoxModel3.addElement("2024");
        startY.setModel(defaultComboBoxModel3);
        panel3.add(startY, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("По:");
        panel3.add(label7, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        endD = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel4 = new DefaultComboBoxModel();
        defaultComboBoxModel4.addElement("1");
        defaultComboBoxModel4.addElement("2");
        defaultComboBoxModel4.addElement("3");
        defaultComboBoxModel4.addElement("4");
        defaultComboBoxModel4.addElement("5");
        defaultComboBoxModel4.addElement("6");
        defaultComboBoxModel4.addElement("7");
        defaultComboBoxModel4.addElement("8");
        defaultComboBoxModel4.addElement("9");
        defaultComboBoxModel4.addElement("10");
        defaultComboBoxModel4.addElement("11");
        defaultComboBoxModel4.addElement("12");
        defaultComboBoxModel4.addElement("13");
        defaultComboBoxModel4.addElement("14");
        defaultComboBoxModel4.addElement("15");
        defaultComboBoxModel4.addElement("16");
        defaultComboBoxModel4.addElement("17");
        defaultComboBoxModel4.addElement("18");
        defaultComboBoxModel4.addElement("19");
        defaultComboBoxModel4.addElement("20");
        defaultComboBoxModel4.addElement("21");
        defaultComboBoxModel4.addElement("22");
        defaultComboBoxModel4.addElement("23");
        defaultComboBoxModel4.addElement("24");
        defaultComboBoxModel4.addElement("25");
        defaultComboBoxModel4.addElement("26");
        defaultComboBoxModel4.addElement("27");
        defaultComboBoxModel4.addElement("28");
        defaultComboBoxModel4.addElement("29");
        defaultComboBoxModel4.addElement("30");
        defaultComboBoxModel4.addElement("31");
        endD.setModel(defaultComboBoxModel4);
        panel3.add(endD, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        endM = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel5 = new DefaultComboBoxModel();
        defaultComboBoxModel5.addElement("January");
        defaultComboBoxModel5.addElement("February");
        defaultComboBoxModel5.addElement("March");
        defaultComboBoxModel5.addElement("April");
        defaultComboBoxModel5.addElement("May");
        defaultComboBoxModel5.addElement("June");
        defaultComboBoxModel5.addElement("July");
        defaultComboBoxModel5.addElement("August");
        defaultComboBoxModel5.addElement("September");
        defaultComboBoxModel5.addElement("October");
        defaultComboBoxModel5.addElement("November");
        defaultComboBoxModel5.addElement("December");
        endM.setModel(defaultComboBoxModel5);
        panel3.add(endM, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        endY = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel6 = new DefaultComboBoxModel();
        defaultComboBoxModel6.addElement("2018");
        defaultComboBoxModel6.addElement("2019");
        defaultComboBoxModel6.addElement("2020");
        defaultComboBoxModel6.addElement("2021");
        defaultComboBoxModel6.addElement("2022");
        defaultComboBoxModel6.addElement("2023");
        defaultComboBoxModel6.addElement("2024");
        endY.setModel(defaultComboBoxModel6);
        panel3.add(endY, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        applyButton = new JButton();
        applyButton.setText("Применить:");
        panel3.add(applyButton, new GridConstraints(2, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(7, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel4, new GridConstraints(3, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Экскурсии:");
        panel4.add(label8, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("Проживание:");
        panel4.add(label9, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("Хранение груза:");
        panel4.add(label10, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setText("Перелет:");
        panel4.add(label11, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        eLabel = new JLabel();
        eLabel.setText("");
        panel4.add(eLabel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lLabel = new JLabel();
        lLabel.setText("");
        panel4.add(lLabel, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cLabel = new JLabel();
        cLabel.setText("");
        panel4.add(cLabel, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fLabel = new JLabel();
        fLabel.setText("");
        panel4.add(fLabel, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JSeparator separator2 = new JSeparator();
        panel4.add(separator2, new GridConstraints(5, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 10), null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setText("Итого:");
        panel4.add(label12, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tLabel = new JLabel();
        tLabel.setText("");
        panel4.add(tLabel, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel4.add(panel5, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        box21 = new JCheckBox();
        box21.setEnabled(false);
        box21.setSelected(true);
        box21.setText("Отдыхающие");
        panel5.add(box21, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        box22 = new JCheckBox();
        box22.setEnabled(false);
        box22.setSelected(true);
        box22.setText("С грузом");
        panel5.add(box22, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return totalPanel;
    }

}
