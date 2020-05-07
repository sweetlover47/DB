package infrastructure.ui.representative;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import models.entity.Cargo;
import models.entity.Statement;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class EditStatement extends JFrame {
    private JComboBox<Serializable> statementBox;
    private JPanel totalPanel;
    private JButton showButton;
    private JPanel contentPanel;
    private JTextField weightField;
    private JTextField wrapField;
    private JTextField insuranceField;
    private JPanel listPanel;
    private JPanel cargoPanel;
    private JButton removeButton;
    private JComboBox freeCargoBox;
    private JButton addButton;
    private JButton okButton;
    private Statement statement;
    private List<Cargo> addedCargos = new ArrayList<>();
    List<Cargo> freeCargoList;

    public EditStatement(Controller controller) {
        setTitle("Edit statement");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);

        DefaultComboBoxModel<Serializable> model1 = new DefaultComboBoxModel<>();
        model1.addElement("-");
        List<Statement> statementList = controller.getStatementList();
        Long[] statementIds = new Long[statementList.size()];
        int i = 0;
        for (Statement s : statementList)
            statementIds[i++] = s.getId();
        for (i = 0; i < statementList.size(); ++i)
            model1.addElement(statementIds[i]);
        statementBox.setModel(model1);
        showButton.addActionListener(e -> {
            if (Objects.equals(statementBox.getSelectedItem(), "-")) {
                contentPanel.setVisible(false);
                return;
            }
            contentPanel.setVisible(true);
            statement = statementList.get(statementBox.getSelectedIndex() - 1);
            weightField.setText(String.valueOf(statement.getWeight()));
            wrapField.setText(String.valueOf(statement.getCostWrap()));
            insuranceField.setText(String.valueOf(statement.getCostInsurance()));
            freeCargoList = controller.getFreeCargos();
            Long[] cargos = new Long[freeCargoList.size()];
            int j = 0;
            for (Cargo c : freeCargoList)
                cargos[j++] = c.getId();
            freeCargoBox.setModel(new DefaultComboBoxModel<>(cargos));

            addedCargos = controller.getCargosByStatementId(statement.getId());
            fillListPanel(addedCargos);
            validate();
            repaint();

            addButton.addActionListener(r -> {
                Cargo c = freeCargoList.get(freeCargoBox.getSelectedIndex());
                for (Cargo cargo : addedCargos)
                    if (cargo.getId() == c.getId())
                        return;
                addedCargos.add(c);
                listPanel.removeAll();
                fillListPanel(addedCargos);
                validate();
                repaint();
            });
        });
        okButton.addActionListener(e -> {
            if (wrapField.getText().isEmpty() || weightField.getText().isEmpty() || insuranceField.getText().isEmpty() || addedCargos.isEmpty()){
                JOptionPane.showMessageDialog(null, "заполните все поля");
                return;
            }
            float insurance, wrap, weight;
            try {
                weight = Float.parseFloat(weightField.getText());
                wrap = Float.parseFloat(wrapField.getText());
                insurance = Float.parseFloat(insuranceField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Неверно заполнены поля");
                return;
            }
            dispose();
            controller.editStatement(statement.getId(), weight, wrap, insurance, addedCargos);
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
        totalPanel.setLayout(new GridLayoutManager(6, 4, new Insets(0, 0, 0, 0), -1, -1));
        totalPanel.setMinimumSize(new Dimension(400, 302));
        totalPanel.setPreferredSize(new Dimension(500, 305));
        statementBox = new JComboBox();
        totalPanel.add(statementBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayoutManager(5, 3, new Insets(0, 0, 0, 0), -1, -1));
        contentPanel.setEnabled(true);
        contentPanel.setVisible(false);
        totalPanel.add(contentPanel, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        weightField = new JTextField();
        weightField.setText("");
        contentPanel.add(weightField, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Стоимость упаковки:");
        contentPanel.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        wrapField = new JTextField();
        contentPanel.add(wrapField, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Стоимость страхования:");
        contentPanel.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        insuranceField = new JTextField();
        contentPanel.add(insuranceField, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Груз");
        contentPanel.add(label3, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Вес(кг) :");
        contentPanel.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPanel.add(panel1, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 120), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setHorizontalScrollBarPolicy(31);
        panel1.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        listPanel = new JPanel();
        listPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        scrollPane1.setViewportView(listPanel);
        freeCargoBox = new JComboBox();
        contentPanel.add(freeCargoBox, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addButton = new JButton();
        addButton.setText("+");
        contentPanel.add(addButton, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        totalPanel.add(spacer1, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 20), null, 0, false));
        final Spacer spacer2 = new Spacer();
        totalPanel.add(spacer2, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, 1, null, new Dimension(10, -1), null, 0, false));
        final Spacer spacer3 = new Spacer();
        totalPanel.add(spacer3, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 10), null, 0, false));
        final Spacer spacer4 = new Spacer();
        totalPanel.add(spacer4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, 1, null, new Dimension(10, -1), null, 0, false));
        final Spacer spacer5 = new Spacer();
        totalPanel.add(spacer5, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 10), null, 0, false));
        showButton = new JButton();
        showButton.setHorizontalAlignment(0);
        showButton.setText("Применить");
        totalPanel.add(showButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        okButton = new JButton();
        okButton.setText("Сохранить изменения");
        totalPanel.add(okButton, new GridConstraints(4, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return totalPanel;
    }

}
