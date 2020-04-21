package infrastructure.ui.tourist;

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
            tripPanel.setBackground((i++ % 2 == 0) ? new Color(242, 242, 242) : new Color(150, 150, 150));
            JLabel lCountry = new JLabel("Страна: " + trip.getCountry());
            JLabel lDateIn = new JLabel("С " + trip.getDate_in().toString());
            JLabel lDateOut = new JLabel("По " + trip.getDate_out().toString());
            tripPanel.add(lCountry);
            tripPanel.add(lDateIn);
            tripPanel.add(lDateOut);
            listPanel.add(tripPanel);
        }
    }
}
