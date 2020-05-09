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
            addedCargos = controller.getCargosByStatementId(statement.getId());
            List<Cargo> boxCargos = new ArrayList<>();
            boxCargos.addAll(addedCargos);
            boxCargos.addAll(freeCargoList);
            Long[] cargos = new Long[boxCargos.size()];
            int j = 0;
            for (Cargo c : boxCargos)
                cargos[j++] = c.getId();
            freeCargoBox.setModel(new DefaultComboBoxModel<>(cargos));

            fillListPanel(addedCargos);
            validate();
            repaint();

            addButton.addActionListener(r -> {
                Cargo c = boxCargos.get(freeCargoBox.getSelectedIndex());
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
            if (wrapField.getText().isEmpty() || weightField.getText().isEmpty() || insuranceField.getText().isEmpty() || addedCargos.isEmpty()) {
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


}
