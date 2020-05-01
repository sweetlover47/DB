package infrastructure.ui.tourist;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import models.entity.Agency;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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
    private JButton найтиButton;
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
        setTitle("MainFrame");
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
            final DefaultComboBoxModel<String> defaultComboBoxModel2 = new DefaultComboBoxModel<String>();
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
            defaultComboBoxModel3.addElement(2020);
            defaultComboBoxModel3.addElement(2021);
            defaultComboBoxModel3.addElement(2022);
            defaultComboBoxModel3.addElement(2023);
            defaultComboBoxModel3.addElement(2024);
            startY.setModel(defaultComboBoxModel3);
            morePanel.add(startY, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            endD = new JComboBox<>();
            endD.setModel(defaultComboBoxModel1);
            morePanel.add(endD, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            endY = new JComboBox<>();
            endY.setModel(defaultComboBoxModel3);
            morePanel.add(endY, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            endM = new JComboBox<>();
            endM.setModel(defaultComboBoxModel2);
            morePanel.add(endM, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            agencyRemoveButton = new JButton();
            agencyRemoveButton.setText("Применить");
            morePanel.add(agencyRemoveButton, new GridConstraints(2, 0, 1, 4, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            validate();
            repaint();
        });
        sortMoreButton.addActionListener(e -> {
            morePanel.removeAll();
            morePanel.setLayout(new GridLayoutManager(5, 3, new Insets(0, 0, 0, 0), -1, -1));
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
            titleAscRadioButton.setText("<html>в алфавитном<br\\>порядке");
            morePanel.add(titleAscRadioButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            titleDescRadioButton = new JRadioButton();
            titleDescRadioButton.setText("<html>в обратном<br\\>порядке");
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
            morePanel.add(sortApplyButton, new GridConstraints(4, 0, 1, 3, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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

            validate();
            repaint();
        });
        backToMainFrameButton.addActionListener(e -> {
            dispose();
            new MainFrame(controller, id);
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

}
