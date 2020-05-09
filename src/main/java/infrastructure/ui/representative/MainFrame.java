package infrastructure.ui.representative;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import models.entity.Excursion;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class MainFrame extends JFrame {
    private JButton createStatementButton;
    private JPanel totalPanel;
    private JButton редактироватьВедомостьButton;
    private JButton удалитьВедомостьButton;
    private JButton привязатьГрузКСкладуButton;
    private JButton редактироватьСвязьГрузаКButton;
    private JComboBox<Integer> groupsComboBox;
    private JButton расселитьButton;
    private JButton составитьФинОтчетButton;
    private JButton просмотретьПопулярныеЭкскурсииButton;
    private JButton просмотретьКачетсвенныеАгенстваButton;

    public MainFrame(Controller controller) {
        setTitle("Main frame");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        Integer[] groupsList = controller.getGroups().toArray(new Integer[0]);
        groupsComboBox.setModel(new DefaultComboBoxModel<>(groupsList));
        validate();
        repaint();
        createStatementButton.addActionListener(e -> new CreateStatement(controller));
        редактироватьВедомостьButton.addActionListener(e -> new EditStatement(controller));
        удалитьВедомостьButton.addActionListener(e -> new DeleteStatement(controller));
        привязатьГрузКСкладуButton.addActionListener(e -> new AddCargoToWarehouse(controller));
        редактироватьСвязьГрузаКButton.addActionListener(e -> new EditCargoToWarehouse(controller));
        расселитьButton.addActionListener(e -> new SettleGroup(controller, (Integer) groupsComboBox.getSelectedItem()));
        составитьФинОтчетButton.addActionListener(e -> new ViewFinReport(controller));
        просмотретьПопулярныеЭкскурсииButton.addActionListener(e -> new PopularExcursions(controller));
        просмотретьКачетсвенныеАгенстваButton.addActionListener(e -> new QualityAgencies(controller));
    }

}
