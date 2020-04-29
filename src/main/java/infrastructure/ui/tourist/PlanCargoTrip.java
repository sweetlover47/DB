package infrastructure.ui.tourist;

import api.Controller;
import models.entity.Tourist;

import javax.swing.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class PlanCargoTrip extends JFrame {
    private JPanel totalPanel;
    private JTextField statementField;
    private JComboBox startD;
    private JComboBox startM;
    private JComboBox startY;
    private JComboBox endD;
    private JComboBox endM;
    private JComboBox endY;
    private JButton OKButton;
    private JPanel panel;
    private JTextField countryField;
    private JLabel lCountry;

    public PlanCargoTrip(Controller controller, Long id) {
        setTitle("MainFrame");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);

        OKButton.addActionListener(e -> {
            int sd = Integer.parseInt((String) startD.getSelectedItem());
            String sm = (String) startM.getSelectedItem();
            int sy = Integer.parseInt((String) startY.getSelectedItem());
            int ed = Integer.parseInt((String) endD.getSelectedItem());
            String em = (String) endM.getSelectedItem();
            int ey = Integer.parseInt((String) endY.getSelectedItem());
            if (sm.equals("February") && sd > 28 || em.equals("February") && ed > 28) {
                if (sd > 29 && sy % 4 == 0 || ed > 29 && ey % 4 == 0) {
                    JOptionPane.showMessageDialog(null, "В февраля меньше 30 дней");
                    return;
                }
                if (sd == 29 && sy % 4 != 0 || ed == 29 && ey % 4 != 0) {
                    JOptionPane.showMessageDialog(null, "Это не високосный год, исправьте, пожалуйста");
                    return;
                }
            }
            if (statementField.getText().isEmpty() || countryField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Заполните все поля");
                return;
            }
            dispose();
            int monthIndexStart = startM.getSelectedIndex();
            Calendar calendarDate = new GregorianCalendar(sy, monthIndexStart, sd);
            long dateIn = calendarDate.getTimeInMillis();
            int monthIndexEnd = endM.getSelectedIndex();
            calendarDate = new GregorianCalendar(ey, monthIndexEnd, ed);
            long dateOut = calendarDate.getTimeInMillis();
            controller.addNewCargoTrip(
                    id,
                    statementField.getText(),
                    countryField.getText(),
                    new Timestamp(dateIn),
                    new Timestamp(dateOut)
            );
        });
    }

}
