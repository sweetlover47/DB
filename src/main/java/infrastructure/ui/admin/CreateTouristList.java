package infrastructure.ui.admin;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import models.entity.Tourist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class CreateTouristList extends JFrame {
    private JComboBox comboBox1;
    private JButton button1;
    private JPanel addedTouristsList;
    private JButton сохранитьButton;
    private JPanel totalPanel;
    private List<Tourist> addedTourist = new ArrayList<>();
    private List<Tourist> allTourists;

    public CreateTouristList(Controller controller) {
        setTitle("Create tourist list");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);

        allTourists = controller.getTouristListWithoutGroup();
        comboBox1.setModel(getTouristModel(allTourists));
        validate();
        repaint();

        button1.addActionListener(e -> {
            Tourist t = allTourists.get(comboBox1.getSelectedIndex());
            if (addedTourist.contains(t)) return;
            addedTourist.add(t);
            fillAddedTouristListPanel();
        });
        сохранитьButton.addActionListener(e -> {
            int group = controller.setNewGroup(addedTourist);
            JOptionPane.showMessageDialog(null, "Была сформирована группа " + group);
            dispose();
        });
    }

    private void fillAddedTouristListPanel() {
        addedTouristsList.removeAll();
        if (addedTourist.isEmpty()) {
            validate();
            repaint();
            return;
        }
        addedTouristsList.setLayout(new GridLayoutManager(addedTourist.size() + 1, 1, new Insets(0, 0, 0, 0), -1, -1));
        int i = 0;
        for (Tourist t : addedTourist) {
            final JPanel templatePanel = new JPanel();
            templatePanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
            addedTouristsList.add(templatePanel, new GridConstraints(i++, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
            final JLabel label1 = new JLabel();
            label1.setText(t.getName());
            templatePanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            final Spacer spacer2 = new Spacer();
            templatePanel.add(spacer2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
            JButton removeButton = new JButton();
            removeButton.setText("-");
            removeButton.addActionListener(e -> {
                addedTourist.remove(t);
                fillAddedTouristListPanel();
            });
            templatePanel.add(removeButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        }
        final Spacer spacer3 = new Spacer();
        addedTouristsList.add(spacer3, new GridConstraints(i, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        validate();
        repaint();
    }

    private DefaultComboBoxModel getTouristModel(List<Tourist> tourists) {
        String[] ids = new String[tourists.size()];
        int i = 0;
        for (Tourist a : tourists)
            ids[i++] = a.getName();
        return new DefaultComboBoxModel(ids);
    }

}
