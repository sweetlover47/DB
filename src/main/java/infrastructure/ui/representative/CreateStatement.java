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
        setTitle("Create statement");
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

}
