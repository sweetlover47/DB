package infrastructure.ui.representative;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import models.entity.Excursion;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class PopularExcursions extends JFrame {
    private JPanel totalPanel;
    private JPanel listPanel;

    public PopularExcursions(Controller controller) {
        List<Excursion> excursionList = controller.getPopularExcursions();
        fillListPanel(excursionList);
        setTitle("Popular excursions");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void fillListPanel(List<Excursion> excursions) {
        listPanel.removeAll();
        listPanel.setLayout(new GridLayout(excursions.size(), 1));
        int i = 0;
        int row = 0;
        for (Excursion t : excursions) {
            JPanel cargoTemplate = getCargoPanelTemplate(t);
            cargoTemplate.setBackground((i++ % 2 == 0) ? new Color(242, 242, 242) : new Color(220, 220, 220));
            listPanel.add(cargoTemplate, new GridConstraints(row++, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        }
        validate();
        repaint();
    }

    private JPanel getCargoPanelTemplate(Excursion t) {
        JPanel template = new JPanel();
        template.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        listPanel.add(template, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Экскурсия " + t.getTitle());
        template.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Количество заказов: " + t.getNumOrders());
        template.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Агенство:" + t.getAgency().getName());
        template.add(label3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        return template;
    }

}
