package infrastructure.ui.representative;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import models.entity.Cargo;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class CreateStatement extends JFrame {
    private JPanel totalPanel;
    private JComboBox<Long> cargoComboBox;
    private JButton addButton;
    private JButton removeButton;
    private JTextField weightField;
    private JTextField wrapField;
    private JTextField insuranceField;
    private JButton сохранитьButton;
    private JPanel listPanel;
    private JButton хочуСоздатьНовыйГрузButton;
    private JPanel cargoPanel;
    private List<Cargo> addedCargos = new ArrayList<>();
    List<Cargo> freeCargoList;

    public CreateStatement(Controller controller) {
        setTitle("MainFrame");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);

        freeCargoList = controller.getFreeCargos();
        Long[] cargos = new Long[freeCargoList.size()];
        int i = 0;
        for (Cargo c : freeCargoList)
            cargos[i++] = c.getId();
        cargoComboBox.setModel(new DefaultComboBoxModel<>(cargos));
        listPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));

        addButton.addActionListener(e -> {
            Cargo c = freeCargoList.get(cargoComboBox.getSelectedIndex());
            for (Cargo cargo : addedCargos)
                if (cargo.getId() == c.getId())
                    return;
            addedCargos.add(c);
            listPanel.removeAll();
            fillListPanel(addedCargos);
            validate();
            repaint();
        });
        сохранитьButton.addActionListener(e -> {
            if (addedCargos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Нельзя создать ведомость без груза");
                return;
            }
            if (insuranceField.getText().isEmpty() || wrapField.getText().isEmpty() || weightField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Заполните все поля");
                return;
            }
            float weight, insurance, wrap;
            try {
                weight = Float.parseFloat(weightField.getText());
                insurance = Float.parseFloat(insuranceField.getText());
                wrap = Float.parseFloat(wrapField.getText());
                if (weight <= 0)
                    throw new NumberFormatException("вес " + weightField.getText());
                if (insurance < 0)
                    throw new NumberFormatException("страхование " + insuranceField.getText());
                if (wrap <= 0)
                    throw new NumberFormatException("упаковка " + wrapField.getText());
                float result = weight * insurance * wrap;
                if (Float.isInfinite(result) || Float.isNaN(result))
                    throw new NumberFormatException("Вы ввели слишком большое число");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Неверно введены данные: " + ex.getMessage());
                return;
            }
            controller.addNewStatement(addedCargos, weight, wrap, insurance);
            dispose();
        });
        хочуСоздатьНовыйГрузButton.addActionListener(e -> new CreateCargo(controller));
        cargoComboBox.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                freeCargoList = controller.getFreeCargos();
                Long[] cargos = new Long[freeCargoList.size()];
                int i = 0;
                for (Cargo c : freeCargoList)
                    cargos[i++] = c.getId();
                cargoComboBox.setModel(new DefaultComboBoxModel<>(cargos));
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });
    }

    private void fillListPanel(List<Cargo> addedCargos) {
        listPanel.removeAll();
        listPanel.setLayout(new GridLayout(addedCargos.size(), 1));
        int i = 0;
        for (Cargo c : addedCargos) {
            JPanel cargoTemplate = getCargoPanelTemplate(c);
            cargoTemplate.setBackground((i++ % 2 == 0) ? new Color(242, 242, 242) : new Color(220, 220, 220));
            listPanel.add(cargoTemplate, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        }
    }

    private JPanel getCargoPanelTemplate(Cargo c) {
        cargoPanel = new JPanel();
        cargoPanel.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        listPanel.add(cargoPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Номер груза: " + c.getId());
        cargoPanel.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Тип груза: " + c.getKind());
        cargoPanel.add(label3, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Дата прибытия: " + c.getDate_in().toString());
        cargoPanel.add(label4, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        removeButton = new JButton();
        removeButton.setText("-");
        removeButton.setPreferredSize(new Dimension(30, -1));
        removeButton.addActionListener(e -> {
            addedCargos.remove(c);
            fillListPanel(addedCargos);
            validate();
            repaint();
        });
        cargoPanel.add(removeButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        return cargoPanel;
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
        totalPanel.setLayout(new GridLayoutManager(10, 5, new Insets(0, 0, 0, 0), -1, -1));
        totalPanel.setPreferredSize(new Dimension(420, 300));
        final JLabel label1 = new JLabel();
        label1.setText("Груз:");
        totalPanel.add(label1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cargoComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("груз1");
        defaultComboBoxModel1.addElement("груз2");
        defaultComboBoxModel1.addElement("г");
        defaultComboBoxModel1.addElement("р");
        defaultComboBoxModel1.addElement("у");
        defaultComboBoxModel1.addElement("з");
        cargoComboBox.setModel(defaultComboBoxModel1);
        totalPanel.add(cargoComboBox, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addButton = new JButton();
        addButton.setHorizontalTextPosition(11);
        addButton.setText("+");
        totalPanel.add(addButton, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(40, -1), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        totalPanel.add(panel1, new GridConstraints(3, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 50), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setHorizontalScrollBarPolicy(31);
        panel1.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        listPanel = new JPanel();
        listPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        scrollPane1.setViewportView(listPanel);
        final JLabel label2 = new JLabel();
        label2.setText("Вес (кг):");
        totalPanel.add(label2, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        weightField = new JTextField();
        totalPanel.add(weightField, new GridConstraints(4, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Стоимость упаковки:");
        totalPanel.add(label3, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        wrapField = new JTextField();
        totalPanel.add(wrapField, new GridConstraints(5, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Стоимость страховки:");
        totalPanel.add(label4, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        insuranceField = new JTextField();
        totalPanel.add(insuranceField, new GridConstraints(6, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer1 = new Spacer();
        totalPanel.add(spacer1, new GridConstraints(4, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, 1, null, new Dimension(10, -1), null, 0, false));
        final Spacer spacer2 = new Spacer();
        totalPanel.add(spacer2, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, 1, null, new Dimension(10, -1), null, 0, false));
        final Spacer spacer3 = new Spacer();
        totalPanel.add(spacer3, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 10), null, 0, false));
        final Spacer spacer4 = new Spacer();
        totalPanel.add(spacer4, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 20), null, 0, false));
        сохранитьButton = new JButton();
        сохранитьButton.setText("Сохранить");
        totalPanel.add(сохранитьButton, new GridConstraints(8, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        totalPanel.add(spacer5, new GridConstraints(9, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 10), null, 0, false));
        хочуСоздатьНовыйГрузButton = new JButton();
        хочуСоздатьНовыйГрузButton.setContentAreaFilled(false);
        хочуСоздатьНовыйГрузButton.setText("Хочу создать новый груз!");
        totalPanel.add(хочуСоздатьНовыйГрузButton, new GridConstraints(2, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return totalPanel;
    }

}
