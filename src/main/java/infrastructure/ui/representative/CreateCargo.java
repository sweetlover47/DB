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

}
