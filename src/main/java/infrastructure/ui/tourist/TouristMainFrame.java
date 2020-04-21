package infrastructure.ui.tourist;

import api.Controller;
import models.entity.Excursion;
import models.entity.Tourist;

import javax.swing.*;
import javax.swing.plaf.metal.MetalScrollBarUI;
import java.awt.*;
import java.util.List;

public class TouristMainFrame extends JFrame {
    private static final Color lightest = new Color(249, 251, 239);
    private static final Color lighter = new Color(226, 225, 203);
    private static final Color middle = new Color(169, 12, 0);
    private static final Color darker = new Color(89, 6, 0);
    private static final Color darkest = new Color(68, 2, 0);

    public TouristMainFrame(Controller controller, Tourist me) {
        setTitle("Home page");
        setPreferredSize(new Dimension(1000, 600));
        setLocation(450, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Внешняя панель, лежит панель поиска и панель списка туров
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        totalPanel.setPreferredSize(new Dimension(1000, 600));
        totalPanel.setBackground(darker);

        //Верхняя панель с поиском
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(totalPanel.getBackground());
        topPanel.setPreferredSize(new Dimension(1000, 50));
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        totalPanel.add(topPanel, BorderLayout.NORTH);

        //Кнопка профиля в topPanel
        JButton profile = new JButton("Я");
        profile.setPreferredSize(new Dimension(60, 30));
        profile.setEnabled(false);
        profile.setContentAreaFilled(false);
        topPanel.add(profile);

        //
        JButton myTrips = new JButton("Мои поездки");
        myTrips.setPreferredSize(new Dimension(150, 30));
        myTrips.setBackground(darkest);
        myTrips.setForeground(lightest);
        myTrips.addActionListener(e -> {

        });
        topPanel.add(myTrips);

        //
        JButton newTrip = new JButton("Поехать!");
        newTrip.setPreferredSize(new Dimension(150, 30));
        newTrip.setBackground(darkest);
        newTrip.setForeground(lightest);
        newTrip.addActionListener(e -> {
            new PlanNewTrip(controller);
        });
        topPanel.add(newTrip);

        //Поиск
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setBackground(totalPanel.getBackground());
        searchPanel.setPreferredSize(new Dimension(975, 40));
        totalPanel.add(searchPanel);

        JTextField searchField = new JTextField(40);
        searchField.setPreferredSize(new Dimension(880, 30));
        searchField.setBackground(darkest);
        searchField.setForeground(lighter);
        searchPanel.add(searchField, BorderLayout.EAST);

        JButton searchButton = new JButton("Поиск");
        searchButton.setPreferredSize(new Dimension(80, 20));
        searchButton.addActionListener(e -> {

        });
        searchPanel.add(searchButton, BorderLayout.WEST);

        JButton advancedSearchButton = new JButton("Расширенный поиск");
        advancedSearchButton.setContentAreaFilled(false);
        advancedSearchButton.setPreferredSize(new Dimension(80, 20));
        advancedSearchButton.addActionListener(e -> {

        });
        searchPanel.add(advancedSearchButton, BorderLayout.WEST);

        //Список туров состоит из скролл панели, в которой хранятся панели туров
        JPanel listPanel = new JPanel(new GridBagLayout());
        listPanel.setBackground(totalPanel.getBackground());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;

        constraints.insets = new Insets(5, 5, 5, 5);

        JScrollPane scrollPane = new JScrollPane(listPanel);
        ///
        scrollPane.getVerticalScrollBar().setUI(new MetalScrollBarUI() {
            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle tb) {
                g.setColor(middle);
                if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
                    g.fillRect(tb.x, tb.y, tb.width, tb.height);
                } else {
                    g.fillRect(tb.x, tb.y, tb.width, tb.height);
                }
            }

            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle tb) {
                g.setColor(totalPanel.getBackground());
                g.fillRect(tb.x, tb.y, tb.width, tb.height);
            }
        });
        ///
        scrollPane.setPreferredSize(new Dimension(975, 450));
        scrollPane.setLayout(new ScrollPaneLayout());
        scrollPane.setHorizontalScrollBar(null);
        totalPanel.add(scrollPane);

        List<Excursion> excursions = controller.getExcursions();
        constraints.weighty = 1.0 / excursions.size();
        int place = 0;
        for (Excursion ex : excursions) {
            JPanel excursionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            excursionPanel.setBackground(darker);
            excursionPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 8, 2));
            excursionPanel.setPreferredSize(new Dimension(950, 60));
            constraints.gridx = 1;
            constraints.gridy = place++;

            JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            titlePanel.setPreferredSize(new Dimension(700, 50));
            titlePanel.setBackground(excursionPanel.getBackground());
            JLabel title = new JLabel(ex.getTitle());
            Font titleFont = new Font("Times New Roman", Font.BOLD, 22);
            title.setForeground(lighter);
            title.setFont(titleFont);
            titlePanel.add(title);
            excursionPanel.add(titlePanel);

            JPanel signupPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            signupPanel.setPreferredSize(new Dimension(220, 50));
            signupPanel.setBackground(excursionPanel.getBackground());
            JButton signup = new JButton("Хочу поехать!");
            signup.setPreferredSize(new Dimension(200, 30));
            signup.setBackground(darkest);
            signup.setForeground(lightest);
            signupPanel.add(signup);
            excursionPanel.add(signupPanel);

            listPanel.add(excursionPanel, constraints);
        }

        add(totalPanel);
        pack();
        setVisible(true);


    }
}
