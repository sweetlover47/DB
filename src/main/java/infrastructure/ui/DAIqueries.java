package infrastructure.ui;

import models.entity.Airplane;
import repository.Repository;

import javax.swing.*;
import java.util.List;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class DAIqueries extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel totalPanel;
    private JTabbedPane deleteTabPane;
    private JTabbedPane addTabPane;
    private JTabbedPane alterTabPane;
    private JTextField textField1;
    private JButton создатьАгенствоButton;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton создатьСамолетButton;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JTextField textField5;
    private JButton создатьЭкскурсиюButton;
    private JComboBox comboBox5;
    private JComboBox comboBox6;
    private JComboBox comboBox7;
    private JComboBox comboBox8;
    private JButton создатьРейсButton;
    private JTextField textField6;
    private JButton создатьОтельButton;
    private JComboBox comboBox9;
    private JComboBox comboBox10;
    private JButton создатьПассажираButton;
    private JTextField textField7;
    private JComboBox comboBox11;
    private JButton создатьКомнатуButton;
    private JComboBox comboBox12;
    private JTextField textField8;
    private JButton изменитьАгенствоButton;
    private JComboBox comboBox13;
    private JTextField textField9;
    private JCheckBox грузовойCheckBox;
    private JCheckBox грузовойCheckBox1;
    private JButton изменитьСамолетButton;
    private JTextField textField10;
    private JTextField textField11;
    private JComboBox comboBox14;
    private JComboBox comboBox15;
    private JComboBox comboBox16;
    private JComboBox comboBox17;
    private JComboBox comboBox18;
    private JComboBox comboBox19;
    private JComboBox comboBox20;
    private JTextField textField12;
    private JButton изменитьГрузButton;
    private JComboBox comboBox21;
    private JTextField textField13;
    private JComboBox comboBox22;
    private JComboBox comboBox23;
    private JComboBox comboBox24;
    private JButton изменитьЭкскурсиюButton;
    private JComboBox comboBox25;
    private JComboBox comboBox26;
    private JComboBox comboBox27;
    private JComboBox comboBox28;
    private JButton изменитьРейсButton;
    private JTextField textField14;
    private JButton изменитьОтельButton;
    private JComboBox comboBox29;
    private JComboBox comboBox30;
    private JComboBox comboBox31;
    private JButton изменитьПассажираButton;
    private JComboBox comboBox32;
    private JComboBox comboBox33;
    private JComboBox comboBox34;
    private JButton изменитьКомнатуButton;
    private JComboBox comboBox35;
    private JTextField textField15;
    private JComboBox comboBox36;
    private JComboBox comboBox37;
    private JComboBox comboBox38;
    private JComboBox comboBox39;
    private JComboBox comboBox40;
    private JComboBox comboBox41;
    private JComboBox comboBox42;
    private JComboBox comboBox43;
    private JComboBox comboBox44;
    private JButton удалитьАгенствоButton;
    private JComboBox comboBox45;
    private JButton удалитьСамолетButton;
    private JComboBox comboBox46;
    private JButton удалитьТуристаButton;
    private JComboBox comboBox47;
    private JButton удалитьГрузButton;
    private JComboBox comboBox48;
    private JButton удалитьЭкскурсиюButton;
    private JComboBox comboBox49;
    private JButton удалитьРейсButton;
    private JComboBox comboBox50;
    private JButton удалитьОтельButton;
    private JButton удалитьПассажираButton;
    private JComboBox comboBox51;
    private JComboBox comboBox52;
    private JComboBox comboBox53;
    private JComboBox comboBox54;
    private JButton удалитьКомнатуButton;
    private JComboBox comboBox55;
    private JButton удалитьПоездкуButton;

    public DAIqueries(Repository repository) {  //for good it will be controller
        setTitle("MainFrame");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        addTabPane.addChangeListener(r -> {
            if (r.getSource() instanceof JTabbedPane) {
                JTabbedPane pane = (JTabbedPane) r.getSource();
                switch (pane.getSelectedIndex()) {
                    case 0://agency
                        создатьАгенствоButton.addActionListener(e -> repository.addAgency(textField1.getText()));
                        break;
                    case 1://airplane
                        создатьСамолетButton.addActionListener(e -> {
                            try {
                                repository.addAirplane(
                                        textField2.getText(),
                                        textField3.getText(),
                                        textField4.getText(),
                                        грузовойCheckBox1.isSelected()
                                        );
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Введите данные корректно");
                            }
                        });
                        break;
                    case 2://excursion
                        break;
                    case 3://flight
                        List<Airplane> airplaneList = repository.getAirplaneList();
                        comboBox8.setModel(getAirplanesModel(airplaneList));
                        break;
                    case 4://hotel

                        break;
                    case 5://passenger

                        break;
                    case 6://room


                }
            }
        });
    }

    private DefaultComboBoxModel getAirplanesModel(List<Airplane> airplanes) {
        Long[] ids = new Long[airplanes.size()];
        int i = 0;
        for (Airplane a : airplanes)
            ids[i++] = a.getId();
        return new DefaultComboBoxModel(ids);
    }
}
