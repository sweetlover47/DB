package infrastructure.ui.representative;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import models.entity.Cargo;
import models.entity.Warehouse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class EditCargoToWarehouse extends JFrame {
    private JPanel totalPanel;
    private JComboBox cargoBox;
    private JButton applyButton;
    private JComboBox warehouseBox;
    private JButton saveButton;
    private JLabel label;

    public EditCargoToWarehouse(Controller controller) {
        setTitle("Edit warehouse for cargo");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);

        List<Cargo> cargoList = controller.getCargosWithWarehouse();
        Long[] cargoIds = new Long[cargoList.size()];
        int i = 0;
        for (Cargo c : cargoList)
            cargoIds[i++] = c.getId();
        cargoBox.setModel(new DefaultComboBoxModel(cargoIds));

        List<Warehouse> warehouseList = controller.getWarehouseList();
        Long[] warehouseIds = new Long[warehouseList.size()];
        i = 0;
        for (Warehouse w : warehouseList)
            warehouseIds[i++] = w.getId();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addElement("-");
        for (Long id : warehouseIds)
            model.addElement(id);
        warehouseBox.setModel(model);

        applyButton.addActionListener(e -> {
            warehouseBox.setVisible(true);
            label.setVisible(true);
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                if (warehouseBox.isVisible()) {
                    controller.editCargoToWarehouse(
                            cargoList.get(cargoBox.getSelectedIndex()),
                            Objects.equals(warehouseBox.getSelectedItem(), "-") ? null : warehouseList.get(warehouseBox.getSelectedIndex() - 1));
                }
            }
        });
    }

}
