package infrastructure.ui.tourist;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import models.entity.Excursion;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class PlanRestTrip extends JFrame {
    private JPanel totalPanel;
    private JTextField countryField;
    private JComboBox startD;
    private JComboBox startM;
    private JComboBox startY;
    private JComboBox endD;
    private JComboBox endM;
    private JComboBox endY;
    private JButton ОКButton;
    private JPanel listPanel;
    private JScrollPane scrollPanel;
    private JButton button1;
    private JComboBox addExcursion;
    private JPanel excursionTemplatePanel;
    private JButton button2;

    public PlanRestTrip(Controller controller, Long id, List<Excursion> joinedExcursions) {
        setTitle("Plan Trip with Excursions");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
        fillListPanel(joinedExcursions);
        String[] titleExcursions = new String[controller.getExcursions().size()];
        int i = 0;
        for (Excursion ex : controller.getExcursions())
            titleExcursions[i++] = ex.getTitle();
        addExcursion.setModel(new DefaultComboBoxModel(titleExcursions));
        button1.addActionListener(e -> {
            Excursion newExcursion = controller.getExcursions().get(addExcursion.getSelectedIndex());
            for (Excursion excursion : joinedExcursions)
                if (excursion.getId().equals(newExcursion.getId()))
                    return;
            joinedExcursions.add(newExcursion);
            listPanel.removeAll();
            fillListPanel(joinedExcursions);
            validate();
            repaint();
        });
        ОКButton.addActionListener(e -> {
            if (countryField.getText().isEmpty() || joinedExcursions.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Заполните все поля");
                return;
            }
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
            int monthIndexStart = startM.getSelectedIndex();
            Calendar calendarDate = new GregorianCalendar(sy, monthIndexStart, sd);
            long dateIn = calendarDate.getTimeInMillis();
            int monthIndexEnd = endM.getSelectedIndex();
            calendarDate = new GregorianCalendar(ey, monthIndexEnd, ed);
            long dateOut = calendarDate.getTimeInMillis();
            if (dateIn > dateOut) {
                JOptionPane.showMessageDialog(null, "Нельзя поставить дату начала поездки позже даты конца");
                return;
            }
            dispose();
            controller.addNewRestTrip(id, countryField.getText(), new Timestamp(dateIn), new Timestamp(dateOut), joinedExcursions);
            controller.clearJoinedExcursion();
        });
    }

    private void fillListPanel(List<Excursion> joinedExcursions) {
        listPanel.setLayout(new GridLayout(joinedExcursions.size(), 1));
        int i = 0;
        for (Excursion ex : joinedExcursions) {
            JPanel excursionPanel = getExcursionTemplatePanel(
                    ex.getTitle(),
                    ex.getId(),
                    joinedExcursions
            );
            excursionPanel.setBackground((i++ % 2 == 0) ? new Color(242, 242, 242) : new Color(220, 220, 220));
            listPanel.add(excursionPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        }
    }

    private JPanel getExcursionTemplatePanel(String title, long id, List<Excursion> joinedExcursions) {
        excursionTemplatePanel = new JPanel();
        excursionTemplatePanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), 5, 10));
        final JLabel label4 = new JLabel();
        label4.setText(title);
        excursionTemplatePanel.add(label4, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        button2 = new JButton();
        button2.setText("-");
        button2.addActionListener(e -> {
            for (Excursion excursion : joinedExcursions)
                if (excursion.getId().equals(id)) {
                    joinedExcursions.remove(excursion);
                    break;
                }
            listPanel.removeAll();
            fillListPanel(joinedExcursions);
            validate();
            repaint();
        });
        excursionTemplatePanel.add(button2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(30, -1), null, 0, false));
        return excursionTemplatePanel;
    }

}
