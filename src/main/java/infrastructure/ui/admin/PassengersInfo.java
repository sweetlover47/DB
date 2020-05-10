package infrastructure.ui.admin;

import api.Controller;
import models.entity.Flight;

import javax.swing.*;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class PassengersInfo extends JFrame {
    private JPanel totalPanel;
    private JPanel listPanel;

    public PassengersInfo(Controller controller, Flight flight) {
        setTitle("Main frame");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
