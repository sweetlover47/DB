package infrastructure.ui.representative;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import models.entity.Cargo;
import models.entity.Warehouse;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class AddCargoToWarehouse extends JFrame {
    private JPanel totalPanel;
    private JComboBox cargoBox;
    private JComboBox warehouseBox;
    private JButton okButton;
    private JLabel l;

    public AddCargoToWarehouse(Controller controller) {
        setTitle("Add cargo to warehouse");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);

        List<Cargo> cargoList = controller.getCargosWithoutWarehouse();
        Long[] cargoIds = new Long[cargoList.size()];
        int i = 0;
        for (Cargo c : cargoList)
            cargoIds[i++] = c.getId();

        List<Warehouse> warehouseList = controller.getWarehouseList();
        Long[] warehouseIds = new Long[warehouseList.size()];
        i = 0;
        for (Warehouse w : warehouseList)
            warehouseIds[i++] = w.getId();

        cargoBox.setModel(new DefaultComboBoxModel(cargoIds));
        warehouseBox.setModel(new DefaultComboBoxModel(warehouseIds));

        okButton.addActionListener(e -> {
            controller.addCargoToWarehouse(
                    cargoList.get(cargoBox.getSelectedIndex()),
                    warehouseList.get(warehouseBox.getSelectedIndex())
            );
            dispose();
        });
    }

}
