package infrastructure.ui.representative;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class ViewFinReport extends JFrame {
    private JPanel totalPanel;
    private JTabbedPane tabbedPane1;
    private JLabel excursionLabeltotal;
    private JLabel livingLabeltotal;
    private JLabel cargoLabeltotal;
    private JLabel flyLabeltotal;
    private JComboBox startD;
    private JComboBox startM;
    private JComboBox startY;
    private JComboBox endD;
    private JComboBox endM;
    private JComboBox endY;
    private JButton applyButton;
    private JLabel totalLabeltotal;
    private JLabel eLabel;
    private JLabel lLabel;
    private JLabel cLabel;
    private JLabel fLabel;
    private JLabel tLabel;
    private JCheckBox box11;
    private JCheckBox box12;
    private JCheckBox box21;
    private JCheckBox box22;
    private List<Float> totalList;
    private List<Float> periodList;
    private float totalSum = 0.0f;
    private float periodSum = 0.0f;

    public ViewFinReport(Controller controller) {
        setTitle("MainFrame");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
        totalList = controller.getFinancialReportForAllPeriod();
        totalSum = totalList.stream().reduce(0.0f, Float::sum);
        paintResult(excursionLabeltotal, livingLabeltotal, cargoLabeltotal, flyLabeltotal, totalLabeltotal, totalList);
        validate();
        repaint();
        applyButton.addActionListener(e -> {
            box21.setEnabled(true);
            box22.setEnabled(true);
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
            periodList = controller.getFinancialReportForPeriod(dateIn, dateOut);
            periodSum = periodList.stream().reduce(0.0f, Float::sum);
            paintResult(eLabel, lLabel, cLabel, fLabel, tLabel, periodList);
            validate();
            repaint();
        });
        box11.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                excursionLabeltotal.setText(String.valueOf(totalList.get(0)));
                totalSum += totalList.get(0);
                totalLabeltotal.setText(String.valueOf(totalSum));
            }
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                excursionLabeltotal.setText("+0.0");
                totalSum -= totalList.get(0);
                totalLabeltotal.setText(String.valueOf(totalSum));
            }
            validate();
            repaint();
        });
        box12.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                cargoLabeltotal.setText("+" + totalList.get(2));
                totalSum += totalList.get(2);
                totalLabeltotal.setText(String.valueOf(totalSum));
            }
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                cargoLabeltotal.setText("+0.0");
                totalSum -= totalList.get(2);
                totalLabeltotal.setText(String.valueOf(totalSum));
            }
            validate();
            repaint();
        });
        box21.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                eLabel.setText(String.valueOf(periodList.get(0)));
                periodSum += periodList.get(0);
                tLabel.setText(String.valueOf(periodSum));
            }
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                eLabel.setText("+0.0");
                periodSum -= periodList.get(0);
                tLabel.setText(String.valueOf(periodSum));
            }
            validate();
            repaint();
        });
        box22.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                cLabel.setText("+" + periodList.get(2));
                periodSum += periodList.get(2);
                tLabel.setText(String.valueOf(periodSum));
            }
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                cLabel.setText("+0.0");
                periodSum -= periodList.get(2);
                tLabel.setText(String.valueOf(periodSum));
            }
            validate();
            repaint();
        });
    }

    private void paintResult(JLabel exLavel, JLabel liLabel, JLabel caLabel, JLabel flLabel, JLabel itoLabel, List<Float> list) {
        exLavel.setText(
                String.valueOf(list.get(0) >= 0 ? "+" + list.get(0) : list.get(0))
        );
        liLabel.setText(
                String.valueOf(list.get(1) >= 0 ? "+" + list.get(1) : list.get(1))
        );
        caLabel.setText(
                String.valueOf(list.get(2) >= 0 ? "+" + list.get(2) : list.get(2))
        );
        flLabel.setText(
                String.valueOf(list.get(3) >= 0 ? "+" + list.get(3) : list.get(3))
        );
        float total = list.stream().reduce(0.0f, Float::sum);
        itoLabel.setText(
                total >= 0 ? "+" + String.valueOf(total) : String.valueOf(total)
        );
    }

}
