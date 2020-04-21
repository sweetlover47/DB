package infrastructure.ui.tourist;

import api.Controller;
import models.entity.Tourist;

import javax.swing.*;

import java.awt.*;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class Authorization extends JFrame {
    private JPanel totalPanel;
    private JTextField answerField;
    private JButton okButton;
    private JLabel questionLabel;

    public Authorization(Controller controller) {
        setTitle("Authorization");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        okButton.addActionListener(e -> {
            if (answerField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Пожалуйста, введите данные паспорта");
                return;
            }
            Tourist tourist = controller.getTouristByPassport(answerField.getText());
            if (tourist != null) {
                dispose();
                new MainFrame(controller, tourist.getId());
                return;
            }
            dispose();
            new Registration(controller, answerField.getText());
        });
    }

}
