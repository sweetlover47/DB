package infrastructure.ui.representative;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import models.entity.Agency;
import models.entity.Excursion;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class QualityAgencies extends JFrame {
    private JPanel totalPanel;
    private JPanel listPanel;

    public QualityAgencies(Controller controller) {
        Map<Agency, Integer> agencyMap = controller.getQualityAgencies();
        fillListPanel(agencyMap);
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

    private void fillListPanel(Map<Agency, Integer> excursions) {
        listPanel.removeAll();
        listPanel.setLayout(new GridLayout(excursions.size(), 1));
        int i = 0;
        int row = 0;
        for (Map.Entry<Agency, Integer> entry : excursions.entrySet()) {
            JPanel cargoTemplate = getCargoPanelTemplate(entry);
            cargoTemplate.setBackground((i++ % 2 == 0) ? new Color(242, 242, 242) : new Color(220, 220, 220));
            listPanel.add(cargoTemplate, new GridConstraints(row++, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        }
        validate();
        repaint();
    }

    private JPanel getCargoPanelTemplate(Map.Entry<Agency, Integer> entry) {
        JPanel template = new JPanel();
        template.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        listPanel.add(template, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Агенство " + entry.getKey().getName());
        template.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Cоотношение заказов к кол-ву экскурсий " + entry.getValue());
        template.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        return template;
    }

}
