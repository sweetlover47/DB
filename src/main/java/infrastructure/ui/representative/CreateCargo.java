package infrastructure.ui.representative;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import models.entity.Cargo;
import models.entity.Flight;
import models.entity.Warehouse;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class CreateCargo extends JFrame {
    private JPanel totalPanel;
    private JButton createCargoButton;
    private JComboBox warehouseComboBox;
    private JComboBox startD;
    private JComboBox startM;
    private JComboBox<Integer> startY;
    private JComboBox endY;
    private JComboBox endM;
    private JComboBox<Integer> endD;
    private JComboBox flightComboBox;
    private JTextField kindField;
    private Cargo c;

    public CreateCargo(Controller controller) {
        setTitle("MainFrame");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);

        List<Warehouse> warehouses = controller.getWarehouseList();
        List<Long> warehouseIds = new ArrayList<>();
        for (Warehouse w : warehouses)
            warehouseIds.add(w.getId());
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addElement("Не выбрано");
        for (Long w : warehouseIds)
            model.addElement(w);
        warehouseComboBox.setModel(model);

        List<Flight> flightList = controller.getFlightList();
        List<Long> flightIds = new ArrayList<>();
        for (Flight f : flightList)
            flightIds.add(f.getId());
        DefaultComboBoxModel model2 = new DefaultComboBoxModel();
        model2.addElement("Не выбрано");
        for (Long f : flightIds)
            model2.addElement(f);
        flightComboBox.setModel(model2);

        List<Integer> startDays = new ArrayList<>();
        List<Integer> endDays = new ArrayList<>();
        for (int i = 1; i < 32; ++i) {
            startDays.add(i);
            endDays.add(i);
        }
        DefaultComboBoxModel edModel = new DefaultComboBoxModel();
        edModel.addElement("-");
        for (Integer i : endDays)
            edModel.addElement(i);
        startD.setModel(new DefaultComboBoxModel(startDays.toArray()));
        endD.setModel(edModel);
        validate();
        repaint();
        createCargoButton.addActionListener(e -> {
            int sd = Integer.parseInt(String.valueOf(startD.getSelectedItem()));
            String sm = (String) startM.getSelectedItem();
            int sy = Integer.parseInt((String) startY.getSelectedItem());
            if (sm.equals("February") && sd > 28) {
                if (sd > 29 && sy % 4 == 0) {
                    JOptionPane.showMessageDialog(null, "В февраля меньше 30 дней");
                    return;
                }
                if (sd == 29 && sy % 4 != 0) {
                    JOptionPane.showMessageDialog(null, "Это не високосный год, исправьте, пожалуйста");
                    return;
                }
            }
            int monthIndexStart = startM.getSelectedIndex();
            Calendar calendarDate = new GregorianCalendar(sy, monthIndexStart, sd);
            Long dateIn = calendarDate.getTimeInMillis();
            Long dateOut = null;
            try {
                int ed = Integer.parseInt(String.valueOf(endD.getSelectedItem()));
                String em = (String) endM.getSelectedItem();
                int ey = Integer.parseInt((String) endY.getSelectedItem());
                if (em.equals("February") && ed > 28) {
                    if (ed > 29 && ey % 4 == 0) {
                        JOptionPane.showMessageDialog(null, "В февраля меньше 30 дней");
                        return;
                    }
                    if (ed == 29 && ey % 4 != 0) {
                        JOptionPane.showMessageDialog(null, "Это не високосный год, исправьте, пожалуйста");
                        return;
                    }
                }
                int monthIndexEnd = endM.getSelectedIndex();
                calendarDate = new GregorianCalendar(ey, monthIndexEnd, ed);
                dateOut = calendarDate.getTimeInMillis();
                if (dateIn > dateOut) {
                    JOptionPane.showMessageDialog(null, "Нельзя поставить дату начала поездки позже даты конца");
                    return;
                }
            } catch (NumberFormatException ignored) {

            }
            Long w = Objects.equals(warehouseComboBox.getSelectedItem(), "Не выбрано") ? null : (Long) warehouseComboBox.getSelectedItem();
            Long f = Objects.equals(flightComboBox.getSelectedItem(), "Не выбрано") ? null : (Long) flightComboBox.getSelectedItem();
            if (kindField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Заполните поле для типа груза");
                return;
            }
            controller.addNewCargo(w, dateIn, dateOut, f, kindField.getText());
            dispose();
        });
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
        totalPanel.setPreferredSize(new Dimension(443, 269));
        createCargoButton = new JButton();
        createCargoButton.setText("Добавить груз");
        totalPanel.add(createCargoButton, new GridConstraints(7, 1, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Склад");
        totalPanel.add(label1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        warehouseComboBox = new JComboBox();
        totalPanel.add(warehouseComboBox, new GridConstraints(1, 2, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Дата поставки");
        totalPanel.add(label2, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Дата отбытия");
        totalPanel.add(label3, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        startD = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        startD.setModel(defaultComboBoxModel1);
        totalPanel.add(startD, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        totalPanel.add(startM, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        startY = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("2018");
        defaultComboBoxModel3.addElement("2019");
        defaultComboBoxModel3.addElement("2020");
        defaultComboBoxModel3.addElement("2021");
        defaultComboBoxModel3.addElement("2022");
        startY.setModel(defaultComboBoxModel3);
        totalPanel.add(startY, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        endY = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel4 = new DefaultComboBoxModel();
        defaultComboBoxModel4.addElement("-");
        defaultComboBoxModel4.addElement("2018");
        defaultComboBoxModel4.addElement("2019");
        defaultComboBoxModel4.addElement("2020");
        defaultComboBoxModel4.addElement("2021");
        defaultComboBoxModel4.addElement("2022");
        endY.setModel(defaultComboBoxModel4);
        totalPanel.add(endY, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        endM = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel5 = new DefaultComboBoxModel();
        defaultComboBoxModel5.addElement("-");
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
        totalPanel.add(endM, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        endD = new JComboBox();
        totalPanel.add(endD, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Рейс");
        totalPanel.add(label4, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        flightComboBox = new JComboBox();
        totalPanel.add(flightComboBox, new GridConstraints(4, 2, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        totalPanel.add(spacer1, new GridConstraints(3, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, 1, null, new Dimension(10, -1), null, 0, false));
        final Spacer spacer2 = new Spacer();
        totalPanel.add(spacer2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, 1, null, new Dimension(10, -1), null, 0, false));
        final Spacer spacer3 = new Spacer();
        totalPanel.add(spacer3, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 10), null, 0, false));
        final Spacer spacer4 = new Spacer();
        totalPanel.add(spacer4, new GridConstraints(8, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 10), null, 0, false));
        final Spacer spacer5 = new Spacer();
        totalPanel.add(spacer5, new GridConstraints(6, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 20), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Тип груза:");
        totalPanel.add(label5, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        kindField = new JTextField();
        totalPanel.add(kindField, new GridConstraints(5, 2, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return totalPanel;
    }

}
