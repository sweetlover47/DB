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

public class AdvancedSearchResult extends JFrame {
    private JButton backToMainFrameButton;
    private JPanel totalPanel;
    private JScrollPane resultScrollPanel;
    private JPanel listPanel;
    private JButton previousButton;
    private JButton nextButton;
    private JTextField searchField;
    private JButton searchButton;
    private JLabel title;
    private JLabel agency;
    private JLabel date;
    private JLabel numOrders;
    private final static int numExcursionsOnList = 3;
    private int page = 0;
    private List<Excursion> searchedExcursions;

    public AdvancedSearchResult(Controller controller, Long id, List<Excursion> excursionsResult) {
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
searchedExcursions = excursionsResult;
        List<Excursion> excursionList = excursionsResult.subList(0, Math.min(controller.getExcursions().size(), numExcursionsOnList));
        if (excursionList.size() < numExcursionsOnList) {
            nextButton.setEnabled(false);
        }
        fillListPanel(excursionList);

        backToMainFrameButton.addActionListener(e -> {
            dispose();
            controller.clearAdvancedSearchDatas();
            new MainFrame(controller, id);
        });
        searchButton.addActionListener(e -> {
            String query = searchField.getText().toLowerCase();
            if (query.isEmpty()) {
                searchedExcursions = excursionsResult;
                page = 0;
                previousButton.setEnabled(false);
                nextButton.setEnabled(excursionsResult.size() > numExcursionsOnList);
                listPanel.removeAll();
                fillListPanel(excursionsResult.subList(0, numExcursionsOnList));
                validate();
                repaint();
                return;
            }
            searchedExcursions = new ArrayList<>();
            for (Excursion ex : excursionsResult) {
                if (ex.getTitle().contains(query)) {
                    searchedExcursions.add(ex);
                }
            }
            page = 0;
            previousButton.setEnabled(false);
            listPanel.removeAll();
            fillListPanel(searchedExcursions.subList(0, Math.min(numExcursionsOnList, searchedExcursions.size())));
            nextButton.setEnabled(searchedExcursions.size() > numExcursionsOnList);
            validate();
            repaint();
        });
        nextButton.addActionListener(e -> {
            page++;
            //boolean isSearching = excursionList.equals(searchedExcursions);
            final List<Excursion> updateExcursionList = searchedExcursions
                    .subList(
                            page * numExcursionsOnList,
                            Math.min((page + 1) * numExcursionsOnList, searchedExcursions.size())
                    );
            if ((page + 1) * numExcursionsOnList >= searchedExcursions.size()) {
                nextButton.setEnabled(false);
            }
            previousButton.setEnabled(true);
            listPanel.removeAll();
            fillListPanel(updateExcursionList);
            validate();
            repaint();
        });
        previousButton.addActionListener(e -> {
            page--;
            //boolean isSearching = excursionList.equals(searchedExcursions);
            final List<Excursion> updateExcursionList = searchedExcursions//(isSearching ? searchedExcursions : excursionsResult)
                    .subList(
                            Math.max(page * numExcursionsOnList, 0),
                            (page + 1) * numExcursionsOnList
                    );
            if (page == 0) {
                previousButton.setEnabled(false);
            }
            nextButton.setEnabled(true);
            listPanel.removeAll();
            fillListPanel(updateExcursionList);
            validate();
            repaint();
        });
    }

    private void fillListPanel(List<Excursion> excursionList) {
        listPanel.setLayout(new GridLayout(excursionList.size(), 1));
        int i = 0;
        for (Excursion e : excursionList) {
            final JPanel panel2 = getPanelTemplate(e);
            panel2.setBackground((i++ % 2 == 0) ? new Color(242, 242, 242) : new Color(220, 220, 220));
            listPanel.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));

        }
        validate();
        repaint();
    }

    private JPanel getPanelTemplate(Excursion e) {
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        title = new JLabel();
        title.setText(e.getTitle());
        panel2.add(title, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        agency = new JLabel();
        agency.setText("Агенство: " + e.getAgency().getName());
        panel2.add(agency, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        date = new JLabel();
        date.setText("Дата проведения:" + e.getDate().toString());
        panel2.add(date, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        numOrders = new JLabel();
        numOrders.setText("Количество заказов:" + e.getNumOrders());
        panel2.add(numOrders, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        return panel2;
    }
}
