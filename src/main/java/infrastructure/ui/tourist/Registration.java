package infrastructure.ui.tourist;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class Registration extends JFrame {
    private JTextField nameField;
    private JTextField ageField;
    private JTextField passportField;
    private JRadioButton mRadioButton;
    private JRadioButton fRadioButton;
    private JButton button;
    private JPanel totalPanel;
    private JSpinner spinner1;
    private JLabel passportLabel;

    public Registration(Controller controller, String passport) {
        passportField.setText(passport);
        setTitle("Registration");
        setContentPane(totalPanel);
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        spinner1.setModel(new SpinnerNumberModel(18, 14, 100, 1));
        button.addActionListener(e -> {
            if (nameField.getText().isEmpty() || passportField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Заполните поля");
                return;
            }
            String s;
            if (mRadioButton.isSelected())
                s = "m";
            else if (fRadioButton.isSelected())
                s = "f";
            else {
                JOptionPane.showMessageDialog(null, "Выберите пол");
                return;
            }

            controller.putNewTourist(nameField.getText(), passportField.getText(), s, (Integer) spinner1.getValue());
            dispose();
            new MainFrame(controller, controller.getTouristByPassport(passportField.getText()).getId());
        });
    }

}
