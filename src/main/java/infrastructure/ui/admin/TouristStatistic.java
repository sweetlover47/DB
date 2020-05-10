package infrastructure.ui.admin;

import api.Controller;
import models.entity.Tourist;

import javax.swing.*;

import java.util.List;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class TouristStatistic extends JFrame{
    private JComboBox comboBox1;
    private JButton показатьButton;
    private JPanel totalPanel;
    private JPanel listPanel;

    public TouristStatistic(Controller controller, Tourist t) {
        setTitle("Main frame");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);

        List<String> countries = controller.getCountries(t);
        comboBox1.setModel(new DefaultComboBoxModel(countries.toArray(new String[0])));
        показатьButton.addActionListener(e -> {
            List<Object[]> datesHotel = controller.getDatesHotelForTouristByCountry(t, (String) comboBox1.getSelectedItem());

        });
    }
}
