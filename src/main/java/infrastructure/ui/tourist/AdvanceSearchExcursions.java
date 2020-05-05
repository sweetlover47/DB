package infrastructure.ui.tourist;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import models.entity.Agency;
import models.entity.Excursion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class AdvanceSearchExcursions extends JFrame {
    private JPanel totalPanel;
    private JPanel propertiesPanel;
    private JButton agencyMoreButton;
    private JButton dateMoreButton;
    private JButton ordersMoreButton;
    private JButton sortMoreButton;
    private JButton backToMainFrameButton;
    private JButton searchButton;
    private JComboBox<String> agencyComboBox;
    private JScrollPane agencyScrollPanel;
    private JPanel agencyListPanel;
    private JButton agencyApplyButton;
    private JButton agencyRemoveButton;
    private JPanel morePanel;
    private JComboBox<String> operationOrders;
    private JTextField numOrdersField;
    private JButton ordersApplyButton;
    private JRadioButton titleAscRadioButton;
    private JRadioButton titleDescRadioButton;
    private JRadioButton popularityAscRadioButton;
    private JRadioButton popularityDescRadioButton;
    private JRadioButton earlyDateRadioButton;
    private JRadioButton lateDateRadioButton;
    private JButton sortApplyButton;
    private JComboBox<Integer> startD;
    private JComboBox<String> startM;
    private JComboBox<Integer> startY;
    private JComboBox<Integer> endD;
    private JComboBox<String> endM;
    private JComboBox<Integer> endY;
    private JPanel panelForScrollPanel;

    public AdvanceSearchExcursions(Controller controller, Long id) {
        setTitle("Advanced search for excursions");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        agencyMoreButton.addActionListener(e -> {
            morePanel.removeAll();
            morePanel.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
            List<Agency> agencyList = controller.getAgencies();
            String[] agencyNames = new String[agencyList.size()];
            int i = 0;
            for (Agency agency : agencyList)
                agencyNames[i++] = agency.getName();

            List<Agency> selectedAgency = new ArrayList<>();
            agencyComboBox = new JComboBox<>();
            agencyComboBox.setModel(new DefaultComboBoxModel<>(agencyNames));
            morePanel.add(agencyComboBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            JButton addAgencyButton = new JButton("+");
            addAgencyButton.addActionListener(r -> {
                if (selectedAgency.contains(agencyList.get(agencyComboBox.getSelectedIndex())))
                    return;
                selectedAgency.add(agencyList.get(agencyComboBox.getSelectedIndex()));
                repaintListPanel(selectedAgency);
            });
            morePanel.add(addAgencyButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            panelForScrollPanel = new JPanel();
            panelForScrollPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
            morePanel.add(panelForScrollPanel, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
            agencyScrollPanel = new JScrollPane();
            panelForScrollPanel.add(agencyScrollPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
            agencyListPanel = new JPanel();
            agencyListPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
            agencyScrollPanel.setViewportView(agencyListPanel);
            agencyApplyButton = new JButton();
            agencyApplyButton.setText("Применить");
            morePanel.add(agencyApplyButton, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            agencyApplyButton.addActionListener(r -> {
                controller.setToCacheSelectedAgency(selectedAgency);
                morePanel.removeAll();
                validate();
                repaint();
            });
            validate();
            repaint();
        });
        dateMoreButton.addActionListener(e -> {
            morePanel.removeAll();
            morePanel.setLayout(new GridLayoutManager(3, 4, new Insets(0, 0, 0, 0), -1, -1));
            final JLabel label7 = new JLabel();
            label7.setText("С:");
            morePanel.add(label7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            final JLabel label8 = new JLabel();
            label8.setText("По:");
            morePanel.add(label8, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            startD = new JComboBox<>();
            final DefaultComboBoxModel<Integer> defaultComboBoxModel1 = new DefaultComboBoxModel<>();
            for (int i = 1; i < 32; i++)
                defaultComboBoxModel1.addElement(i);
            startD.setModel(defaultComboBoxModel1);
            morePanel.add(startD, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            startM = new JComboBox<>();
            final DefaultComboBoxModel<String> defaultComboBoxModel2 = new DefaultComboBoxModel<>();
            defaultComboBoxModel2.addElement("January");
            defaultComboBoxModel2.addElement("February");
            defaultComboBoxModel2.addElement("March");
            defaultComboBoxModel2.addElement("April");
            defaultComboBoxModel2.addElement("May");
            defaultComboBoxModel2.addElement("June");
            defaultComboBoxModel2.addElement("July");
            defaultComboBoxModel2.addElement("August");
            defaultComboBoxModel2.addElement("September");
            defaultComboBoxModel2.addElement("October");
            defaultComboBoxModel2.addElement("November");
            defaultComboBoxModel2.addElement("December");
            startM.setModel(defaultComboBoxModel2);
            morePanel.add(startM, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            startY = new JComboBox<>();
            final DefaultComboBoxModel<Integer> defaultComboBoxModel3 = new DefaultComboBoxModel<>();
            defaultComboBoxModel3.addElement(2018);
            defaultComboBoxModel3.addElement(2019);
            defaultComboBoxModel3.addElement(2020);
            defaultComboBoxModel3.addElement(2021);
            defaultComboBoxModel3.addElement(2022);
            defaultComboBoxModel3.addElement(2023);
            defaultComboBoxModel3.addElement(2024);
            startY.setModel(defaultComboBoxModel3);
            morePanel.add(startY, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            final DefaultComboBoxModel<Integer> defaultComboBoxModel4 = new DefaultComboBoxModel<>();
            for (int i = 1; i < 32; i++)
                defaultComboBoxModel4.addElement(i);
            endD = new JComboBox<>();
            endD.setModel(defaultComboBoxModel4);
            morePanel.add(endD, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            final DefaultComboBoxModel<Integer> defaultComboBoxModel5 = new DefaultComboBoxModel<>();
            defaultComboBoxModel5.addElement(2018);
            defaultComboBoxModel5.addElement(2019);
            defaultComboBoxModel5.addElement(2020);
            defaultComboBoxModel5.addElement(2021);
            defaultComboBoxModel5.addElement(2022);
            defaultComboBoxModel5.addElement(2023);
            defaultComboBoxModel5.addElement(2024);
            endY = new JComboBox<>();
            endY.setModel(defaultComboBoxModel5);
            morePanel.add(endY, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            final DefaultComboBoxModel<String> defaultComboBoxModel6 = new DefaultComboBoxModel<>();
            defaultComboBoxModel6.addElement("January");
            defaultComboBoxModel6.addElement("February");
            defaultComboBoxModel6.addElement("March");
            defaultComboBoxModel6.addElement("April");
            defaultComboBoxModel6.addElement("May");
            defaultComboBoxModel6.addElement("June");
            defaultComboBoxModel6.addElement("July");
            defaultComboBoxModel6.addElement("August");
            defaultComboBoxModel6.addElement("September");
            defaultComboBoxModel6.addElement("October");
            defaultComboBoxModel6.addElement("November");
            defaultComboBoxModel6.addElement("December");
            endM = new JComboBox<>();
            endM.setModel(defaultComboBoxModel6);
            morePanel.add(endM, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            JButton dateApplyButton = new JButton();
            dateApplyButton.setText("Применить");
            dateApplyButton.addActionListener(r -> {
                int sd = (int) startD.getSelectedItem();
                String sm = (String) startM.getSelectedItem();
                int sy = (int) startY.getSelectedItem();
                int ed = (int) endD.getSelectedItem();
                String em = (String) endM.getSelectedItem();
                int ey = (int) endY.getSelectedItem();
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
                controller.setToCacheSelectedDate(dateIn, dateOut);
                morePanel.removeAll();
                validate();
                repaint();
            });
            morePanel.add(dateApplyButton, new GridConstraints(2, 0, 1, 4, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            validate();
            repaint();
        });
        sortMoreButton.addActionListener(e -> {
            morePanel.removeAll();
            morePanel.setLayout(new GridLayoutManager(6, 3, new Insets(0, 0, 0, 0), -1, -1));
            final JLabel label7 = new JLabel();
            label7.setText("По названию");
            morePanel.add(label7, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            final JLabel label8 = new JLabel();
            label8.setText("По популярности");
            morePanel.add(label8, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            final JLabel label9 = new JLabel();
            label9.setText("По дате");
            morePanel.add(label9, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            titleAscRadioButton = new JRadioButton();
            titleAscRadioButton.setText("<html>в алфавитном<br>порядке");
            morePanel.add(titleAscRadioButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            titleDescRadioButton = new JRadioButton();
            titleDescRadioButton.setText("<html>в обратном<br>порядке");
            morePanel.add(titleDescRadioButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            popularityAscRadioButton = new JRadioButton();
            popularityAscRadioButton.setText("по возрастанию");
            morePanel.add(popularityAscRadioButton, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            popularityDescRadioButton = new JRadioButton();
            popularityDescRadioButton.setText("по убыванию");
            morePanel.add(popularityDescRadioButton, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            earlyDateRadioButton = new JRadioButton();
            earlyDateRadioButton.setText("более ранние");
            morePanel.add(earlyDateRadioButton, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            lateDateRadioButton = new JRadioButton();
            lateDateRadioButton.setText("более поздние");
            morePanel.add(lateDateRadioButton, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            sortApplyButton = new JButton();
            sortApplyButton.setText("Применить");
            morePanel.add(sortApplyButton, new GridConstraints(5, 0, 1, 3, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            final JLabel label10 = new JLabel();
            label10.setText("Не обязательно выбирать все пункты");
            morePanel.add(label10, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            ButtonGroup buttonGroup;
            buttonGroup = new ButtonGroup();
            buttonGroup.add(titleAscRadioButton);
            buttonGroup.add(titleDescRadioButton);
            buttonGroup = new ButtonGroup();
            buttonGroup.add(popularityAscRadioButton);
            buttonGroup.add(popularityDescRadioButton);
            buttonGroup = new ButtonGroup();
            buttonGroup.add(earlyDateRadioButton);
            buttonGroup.add(lateDateRadioButton);
            //Add sort by agency name
            JLabel label11 = new JLabel("<html>По названию<br>агенства:");
            morePanel.add(label11, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            JRadioButton agencyTitleAscRadioButton = new JRadioButton();
            agencyTitleAscRadioButton.setText("<html>в алфавитном<br>порядке");
            morePanel.add(agencyTitleAscRadioButton, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            JRadioButton agencyTitleDescRadioButton = new JRadioButton();
            agencyTitleDescRadioButton.setText("<html>в обратном<br>порядке");
            morePanel.add(agencyTitleDescRadioButton, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            buttonGroup = new ButtonGroup();
            buttonGroup.add(agencyTitleAscRadioButton);
            buttonGroup.add(agencyTitleDescRadioButton);
            //
            sortApplyButton.addActionListener(r -> {
                int sortProperties = 0;
                //title
                if (titleAscRadioButton.isSelected())
                    sortProperties += 1;
                else if (titleDescRadioButton.isSelected())
                    sortProperties += 2;
                //popularity
                if (popularityAscRadioButton.isSelected())
                    sortProperties += 4;
                else if (popularityDescRadioButton.isSelected())
                    sortProperties += 8;
                //date
                if (earlyDateRadioButton.isSelected())
                    sortProperties += 16;
                else if (lateDateRadioButton.isSelected())
                    sortProperties += 32;
                //agency name
                if (agencyTitleAscRadioButton.isSelected())
                    sortProperties += 64;
                else if (agencyTitleDescRadioButton.isSelected())
                    sortProperties += 128;
                controller.setToCacheSortProperties(sortProperties);
                morePanel.removeAll();
                validate();
                repaint();
            });
            validate();
            repaint();
        });
        ordersMoreButton.addActionListener(e -> {
            morePanel.removeAll();
            morePanel.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
            final JLabel label7 = new JLabel();
            label7.setText("Выберите:");
            morePanel.add(label7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            operationOrders = new JComboBox<>();
            final DefaultComboBoxModel<String> defaultComboBoxModel1 = new DefaultComboBoxModel<>();
            defaultComboBoxModel1.addElement("больше");
            defaultComboBoxModel1.addElement("больше либо равно");
            defaultComboBoxModel1.addElement("меньше");
            defaultComboBoxModel1.addElement("меньше либо равно");
            defaultComboBoxModel1.addElement("в точности");
            operationOrders.setModel(defaultComboBoxModel1);
            morePanel.add(operationOrders, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            final JLabel label8 = new JLabel();
            label8.setText("Укажите число заказов");
            morePanel.add(label8, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            numOrdersField = new JTextField();
            morePanel.add(numOrdersField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
            ordersApplyButton = new JButton();
            ordersApplyButton.setText("Применить");
            morePanel.add(ordersApplyButton, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            ordersApplyButton.addActionListener(r -> {
                int numOrders;
                try {
                    numOrders = Integer.parseInt(numOrdersField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Введите целочисленное значение в поле \"Число заказов\".");
                    return;
                }
                String methodName = "";
                int selectedIndex = operationOrders.getSelectedIndex();
                if (selectedIndex == 0) {//method names
                    methodName = "gt";
                } else if (selectedIndex == 1) {
                    methodName = "ge";
                } else if (selectedIndex == 2) {
                    methodName = "lt";
                } else if (selectedIndex == 3) {
                    methodName = "le";
                } else if (selectedIndex == 4) {
                    methodName = "equal";
                } else {
                    JOptionPane.showMessageDialog(null, "Произошла непонятная ошибка. Перезапустите приложение.");
                    dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                }
                controller.setToCacheStatementForOrders(methodName, numOrders);
                morePanel.removeAll();
                validate();
                repaint();
            });

            validate();
            repaint();
        });
        backToMainFrameButton.addActionListener(e -> {
            dispose();
            controller.clearAdvancedSearchDatas();
            new MainFrame(controller, id);
        });
        searchButton.addActionListener(e -> {
            List<Excursion> excursionsResult = controller.getResultOfAdvancedSearching();
            dispose();
            new AdvancedSearchResult(controller, id, excursionsResult);
        });
    }

    private void repaintListPanel(List<Agency> agencyList) {
        agencyListPanel.removeAll();
        agencyListPanel.setLayout(new GridLayoutManager(agencyList.isEmpty() ? 1 : agencyList.size(), 2, new Insets(0, 0, 0, 0), -1, -1));
        int i = 0;
        for (Agency agency : agencyList) {
            final JLabel label = new JLabel();
            label.setText(agency.getName());
            agencyListPanel.add(label, new GridConstraints(i, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            agencyRemoveButton = new JButton();
            agencyRemoveButton.setText("-");
            agencyRemoveButton.addActionListener(e -> {
                agencyList.remove(agency);
                repaintListPanel(agencyList);
            });
            agencyListPanel.add(agencyRemoveButton, new GridConstraints(i, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(30, -1), null, 0, false));
            i++;
        }
        validate();
        repaint();
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
        totalPanel.setLayout(new GridLayoutManager(8, 4, new Insets(0, 0, 0, 0), -1, -1));
        totalPanel.setPreferredSize(new Dimension(700, 320));
        final JLabel label1 = new JLabel();
        label1.setText("Выберите характеристику, которую хотите уточнить");
        totalPanel.add(label1, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Подробнее:");
        totalPanel.add(label2, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        totalPanel.add(panel1, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, -1), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        propertiesPanel = new JPanel();
        propertiesPanel.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        scrollPane1.setViewportView(propertiesPanel);
        final JLabel label3 = new JLabel();
        label3.setText("Агенство");
        propertiesPanel.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        agencyMoreButton = new JButton();
        agencyMoreButton.setText(">");
        propertiesPanel.add(agencyMoreButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(30, -1), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Дата проведения экскурсии");
        propertiesPanel.add(label4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dateMoreButton = new JButton();
        dateMoreButton.setText(">");
        propertiesPanel.add(dateMoreButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(30, -1), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Сортировать");
        propertiesPanel.add(label5, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sortMoreButton = new JButton();
        sortMoreButton.setText(">");
        propertiesPanel.add(sortMoreButton, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(30, -1), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Количество заказов");
        propertiesPanel.add(label6, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ordersMoreButton = new JButton();
        ordersMoreButton.setText(">");
        propertiesPanel.add(ordersMoreButton, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(30, -1), null, 0, false));
        morePanel = new JPanel();
        morePanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        totalPanel.add(morePanel, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        totalPanel.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 10), null, 0, false));
        final Spacer spacer2 = new Spacer();
        totalPanel.add(spacer2, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, 1, null, new Dimension(10, -1), null, 0, false));
        final Spacer spacer3 = new Spacer();
        totalPanel.add(spacer3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, 1, null, new Dimension(10, -1), null, 0, false));
        final Spacer spacer4 = new Spacer();
        totalPanel.add(spacer4, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 10), null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("ВАЖНО! Если хотите сохранить то, что выбрали, не забудьте нажать \"Применить\"");
        totalPanel.add(label7, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        backToMainFrameButton = new JButton();
        backToMainFrameButton.setContentAreaFilled(false);
        backToMainFrameButton.setText("Вернуться на главную страницу");
        totalPanel.add(backToMainFrameButton, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchButton = new JButton();
        searchButton.setText("Найти");
        totalPanel.add(searchButton, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        totalPanel.add(spacer5, new GridConstraints(4, 1, 2, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 20), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return totalPanel;
    }
}
