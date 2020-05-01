package infrastructure.ui.tourist;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import models.entity.Excursion;
import models.entity.Tourist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class AdvancedSearchResult extends JFrame {
    private JButton вернутьсяНаГлавныйЭкранButton;
    private JPanel totalPanel;
    private JScrollPane resultScrollPanel;
    private JPanel listPanel;
    private JLabel title;
    private JLabel agency;
    private JLabel date;
    private JLabel numOrders;

    public AdvancedSearchResult(Controller controller, Long id, List<Excursion> excursionsResult) {
        Tourist tourist = controller.getTouristById(id);
        setTitle("MainFrame");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        fillListPanel(excursionsResult);

        вернутьсяНаГлавныйЭкранButton.addActionListener(e -> {
            dispose();
            new MainFrame(controller, id);
        });
    }

    private void fillListPanel(List<Excursion> excursionList) {
        listPanel.setLayout(new GridLayout(excursionList.size(), 1));
        int i = 0;
        for (Excursion e : excursionList) {
            final JPanel panel2 = new JPanel();
            panel2.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
            panel2.setBackground((i++ % 2 == 0) ? new Color(242, 242, 242) : new Color(220, 220, 220));
            listPanel.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
            title = new JLabel();
            title.setText(e.getTitle());
            panel2.add(title, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            agency = new JLabel();
            agency.setText("Агенство: " + e.getAgency().getName());
            panel2.add(agency, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            date = new JLabel();
            date.setText("Дата проведения:" + e.getDate().toString());
            panel2.add(date, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            numOrders = new JLabel();
            numOrders.setText("Количество заказов:" + e.getNumOrders());
            panel2.add(numOrders, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        }
        validate();
        repaint();
    }
}
