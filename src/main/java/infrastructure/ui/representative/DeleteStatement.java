package infrastructure.ui.representative;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import models.entity.Statement;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class DeleteStatement extends JFrame {
    private JComboBox comboBox1;
    private JPanel totalPanel;
    private JButton removeButton;

    public DeleteStatement(Controller controller) {
        setTitle("Delete statement");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);

        List<Statement> statementList = controller.getStatementList();
        Long[] statementIds = new Long[statementList.size()];
        int i = 0;
        for (Statement s : statementList)
            statementIds[i++] = s.getId();
        comboBox1.setModel(new DefaultComboBoxModel(statementIds));

        removeButton.addActionListener(e -> {
            long id = (long) comboBox1.getSelectedItem();
            controller.removeStatement(id);
            dispose();
        });

    }

}
