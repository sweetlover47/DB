package infrastructure.ui;

import api.Controller;
import infrastructure.ui.representative.MainFrame;
import infrastructure.ui.tourist.Authorization;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class ChooseRole extends JFrame {

    private JPanel totalPanel;
    private JButton туристButton;
    private JButton туристическаяФирмаButton;
    private JButton администраторButton;
    private JButton dieButton;

    public ChooseRole(Controller controller) {
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setTitle("Choose your role");
        setContentPane(totalPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        туристButton.addActionListener(e -> {
            dispose();
            new Authorization(controller);
        });
        туристическаяФирмаButton.addActionListener(e -> {
            dispose();
            new MainFrame(controller);
        });
        администраторButton.addActionListener(e -> {
            dispose();
            new infrastructure.ui.admin.MainFrame(controller);
        });
        dieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new DAIqueries(controller.getService().getRepository());
            }
        });
    }

}
