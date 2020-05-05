package infrastructure.ui.tourist;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import models.entity.Trip;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class MyTrips extends JFrame {
    private JPanel totalPanel;
    private JPanel listPanel;
    private JScrollPane scrollPanel;

    public MyTrips(List<Trip> myTrips) {
        setTitle("My Trips");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
        if (!myTrips.isEmpty()) {
            listPanel.setLayout(new GridLayout(myTrips.size(), 1));
            listPanel.setPreferredSize(new Dimension(220, myTrips.size() * 100));
        } else {
            listPanel.setLayout(new GridLayout(1, 1));
            listPanel.setPreferredSize(new Dimension(220, 100));
            listPanel.add(new JLabel("Вы пока не путешествовали"));
        }
        int i = 0;
        for (Trip trip : myTrips) {
            JPanel tripPanel = new JPanel();
            tripPanel.setLayout(new GridLayout(3, 1, 10, 5));
            //tripPanel.setPreferredSize(new Dimension(, 50));
            tripPanel.setBackground((i++ % 2 == 0) ? new Color(242, 242, 242) : new Color(220, 220, 220));
            JLabel lCountry = new JLabel("Страна: " + trip.getCountry());
            JLabel lDateIn = new JLabel("С " + trip.getDate_in().toString());
            JLabel lDateOut = new JLabel("По " + trip.getDate_out().toString());
            tripPanel.add(lCountry);
            tripPanel.add(lDateIn);
            tripPanel.add(lDateOut);
            listPanel.add(tripPanel);
        }
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
        totalPanel.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        totalPanel.setPreferredSize(new Dimension(250, 150));
        scrollPanel = new JScrollPane();
        scrollPanel.setHorizontalScrollBarPolicy(31);
        totalPanel.add(scrollPanel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        listPanel = new JPanel();
        listPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        listPanel.setPreferredSize(new Dimension(220, 500));
        scrollPanel.setViewportView(listPanel);
        final Spacer spacer1 = new Spacer();
        totalPanel.add(spacer1, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        totalPanel.add(spacer2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        totalPanel.add(spacer3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        totalPanel.add(spacer4, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return totalPanel;
    }
}
