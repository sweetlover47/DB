package infrastructure.ui;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import infrastructure.ui.tourist.Authorization;

import javax.swing.*;

import java.awt.*;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class ChooseRole extends JFrame {

    private JPanel totalPanel;
    private JButton туристButton;
    private JButton туристическаяФирмаButton;
    private JButton представительствоButton;
    private JButton администраторButton;

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
    }

}
