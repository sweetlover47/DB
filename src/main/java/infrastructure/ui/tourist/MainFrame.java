package infrastructure.ui.tourist;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import models.entity.Excursion;
import models.entity.Tourist;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class MainFrame extends JFrame {
    private JButton myInfoButton;
    private JButton myTripsButton;
    private JButton takeCargoButton;
    private JButton planTripButton;
    private JTextField searchField;
    private JButton searchButton;
    private JButton advanceSearchButton;
    private JPanel totalPanel;
    private JScrollPane scrollPanel;
    private JPanel listPanel;
    private JButton previousButton;
    private JButton nextButton;
    private JPanel templatePanel;
    private JLabel title;
    private JLabel agency;
    private JLabel countOrders;
    private JLabel date;
    private JPanel bigPanel;
    private JButton joinExcursion;
    private final static int numExcursionsOnList = 5;
    private int page = 0;
    private List<Excursion> searchedExcursions;

    public MainFrame(Controller controller, Long id) {
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

        List<Excursion> excursionList = (searchedExcursions != null) ? searchedExcursions : controller.getExcursions().subList(0, Math.min(controller.getExcursions().size(), numExcursionsOnList));
        if (excursionList.size() < numExcursionsOnList) {
            nextButton.setEnabled(false);
        }
        fillListPanel(excursionList, controller);

        myInfoButton.addActionListener(e -> new Profile(controller, id));
        myTripsButton.addActionListener(e -> new MyTrips(controller.getTripList(id)));
        takeCargoButton.addActionListener(e -> new PlanCargoTrip(controller, id));
        planTripButton.addActionListener(e -> new PlanRestTrip(controller, id, controller.getFromCacheJoinedExcursions()));
        advanceSearchButton.addActionListener(e -> {
            dispose();
            new AdvanceSearchExcursions(controller, id);
        });
        nextButton.addActionListener(e -> {
            page++;
            boolean isSearching = excursionList.equals(searchedExcursions);
            final List<Excursion> updateExcursionList = (isSearching ? searchedExcursions : controller.getExcursions())
                    .subList(
                            page * numExcursionsOnList,
                            Math.min((page + 1) * numExcursionsOnList, controller.getExcursions().size())
                    );
            if ((page + 1) * numExcursionsOnList > controller.getExcursions().size()) {
                nextButton.setEnabled(false);
            }
            previousButton.setEnabled(true);
            listPanel.removeAll();
            fillListPanel(updateExcursionList, controller);
            validate();
            repaint();
        });
        previousButton.addActionListener(e -> {
            page--;
            boolean isSearching = excursionList.equals(searchedExcursions);
            final List<Excursion> updateExcursionList = (isSearching ? searchedExcursions : controller.getExcursions())
                    .subList(
                            Math.max(page * numExcursionsOnList, 0),
                            (page + 1) * numExcursionsOnList
                    );
            if (page == 0) {
                previousButton.setEnabled(false);
            }
            nextButton.setEnabled(true);
            listPanel.removeAll();
            fillListPanel(updateExcursionList, controller);
            validate();
            repaint();
        });
        searchButton.addActionListener(e -> {
            String query = searchField.getText().toLowerCase();
            if (query.isEmpty()) {
                searchedExcursions = null;
                page = 0;
                previousButton.setEnabled(false);
                nextButton.setEnabled(controller.getExcursions().size() > numExcursionsOnList);
                listPanel.removeAll();
                fillListPanel(controller.getExcursions().subList(0, numExcursionsOnList), controller);
                validate();
                repaint();
                return;
            }
            searchedExcursions = new ArrayList<>();
            for (Excursion ex : controller.getExcursions()) {
                if (ex.getTitle().contains(query)) {
                    searchedExcursions.add(ex);
                }
            }
            page = 0;
            previousButton.setEnabled(false);
            listPanel.removeAll();
            fillListPanel(searchedExcursions.subList(0, Math.min(numExcursionsOnList, searchedExcursions.size())), controller);
            nextButton.setEnabled(searchedExcursions.size() > numExcursionsOnList);
            validate();
            repaint();
        });
    }

    private void fillListPanel(List<Excursion> excursionList, Controller controller) {
        listPanel.setLayout(new GridLayout(excursionList.size(), 1));
        int i = 0;
        for (Excursion excursion : excursionList) {
            JPanel excursionPanel = getExcursionPanelTemplate(
                    excursion.getTitle(),
                    excursion.getAgency().getName(),
                    String.valueOf(excursion.getNumOrders()),
                    excursion.getDate().toString(),
                    controller,
                    excursion.getId()
            );
            excursionPanel.setBackground((i++ % 2 == 0) ? new Color(242, 242, 242) : new Color(220, 220, 220));
            excursionPanel.setPreferredSize(new Dimension(-1, 80));
            listPanel.add(excursionPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        }
    }

    private JPanel getExcursionPanelTemplate(String t, String a, String o, String d, Controller controller, Long id) {
        Font titleFont = new Font("Calibri", Font.BOLD, 18);
        templatePanel = new JPanel();
        templatePanel.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        title = new JLabel();
        title.setText(t);
        title.setFont(titleFont);
        templatePanel.add(title, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        agency = new JLabel();
        agency.setText("Агенство " + a);
        templatePanel.add(agency, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        countOrders = new JLabel();
        countOrders.setText("Количество заказов: " + o);
        templatePanel.add(countOrders, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        date = new JLabel();
        date.setText("Дата проведения экскурсии: " + d);
        templatePanel.add(date, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        joinExcursion = new JButton("Хочу поехать!");
        joinExcursion.setContentAreaFilled(false);
        joinExcursion.addActionListener(e -> {
            controller.setToCacheJoinedExcursions(id);
            JOptionPane.showMessageDialog(null, "Экскурсия добавлена в вашу планируемую поездку!");
        });
        templatePanel.add(joinExcursion, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        return templatePanel;
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
        totalPanel.setLayout(new GridLayoutManager(13, 4, new Insets(0, 0, 0, 0), -1, -1));
        totalPanel.setMaximumSize(new Dimension(2147483647, 500));
        totalPanel.setMinimumSize(new Dimension(599, 200));
        totalPanel.setPreferredSize(new Dimension(800, 500));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        totalPanel.add(panel1, new GridConstraints(8, 2, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        scrollPanel = new JScrollPane();
        panel1.add(scrollPanel, new GridConstraints(0, 0, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        listPanel = new JPanel();
        listPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        scrollPanel.setViewportView(listPanel);
        final Spacer spacer1 = new Spacer();
        totalPanel.add(spacer1, new GridConstraints(1, 3, 9, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, 1, null, new Dimension(20, -1), null, 0, false));
        final Spacer spacer2 = new Spacer();
        totalPanel.add(spacer2, new GridConstraints(1, 0, 9, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, 1, null, new Dimension(20, -1), null, 0, false));
        final Spacer spacer3 = new Spacer();
        totalPanel.add(spacer3, new GridConstraints(12, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 10), null, 0, false));
        final Spacer spacer4 = new Spacer();
        totalPanel.add(spacer4, new GridConstraints(0, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 10), null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        totalPanel.add(panel2, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        searchField = new JTextField();
        panel2.add(searchField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        searchButton = new JButton();
        searchButton.setText("Поиск");
        panel2.add(searchButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        advanceSearchButton = new JButton();
        advanceSearchButton.setContentAreaFilled(false);
        advanceSearchButton.setText("Расширенные");
        panel2.add(advanceSearchButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        totalPanel.add(panel3, new GridConstraints(1, 2, 5, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 40), null, 0, false));
        myInfoButton = new JButton();
        myInfoButton.setText("Мои данные");
        panel4.add(myInfoButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        myTripsButton = new JButton();
        myTripsButton.setText("Мои поездки");
        panel4.add(myTripsButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        takeCargoButton = new JButton();
        takeCargoButton.setText("Забрать груз");
        panel4.add(takeCargoButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        planTripButton = new JButton();
        planTripButton.setText("Запланировать поездку");
        panel4.add(planTripButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel4.add(spacer5, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        totalPanel.add(spacer6, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 10), null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        totalPanel.add(panel5, new GridConstraints(11, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        panel5.add(spacer7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        previousButton = new JButton();
        previousButton.setEnabled(false);
        previousButton.setText("<- Предыдущие");
        panel5.add(previousButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer8 = new Spacer();
        panel5.add(spacer8, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, 1, null, new Dimension(500, -1), null, 0, false));
        nextButton = new JButton();
        nextButton.setText("Следующие ->");
        panel5.add(nextButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer9 = new Spacer();
        panel5.add(spacer9, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer10 = new Spacer();
        totalPanel.add(spacer10, new GridConstraints(10, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 10), null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return totalPanel;
    }
}
