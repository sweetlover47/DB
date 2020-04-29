package infrastructure.ui.tourist;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
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
        if (excursionList.size() < numExcursionsOnList)
            nextButton.setEnabled(false);
        fillListPanel(excursionList, controller);

        myInfoButton.addActionListener(e -> new Profile(controller, id));
        myTripsButton.addActionListener(e -> new MyTrips(controller.getTripList(id)));
        takeCargoButton.addActionListener(e -> new PlanCargoTrip(controller, id));
        planTripButton.addActionListener(e -> new PlanRestTrip(controller, id, controller.getFromCacheJoinedExcursions()));
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
        advanceSearchButton.addActionListener(e -> {

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

}
