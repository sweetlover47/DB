package infrastructure.ui.tourist;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import models.entity.Tourist;

import javax.swing.*;

import java.awt.*;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class Profile extends JFrame {
    private JButton редактироватьButton;
    private JPanel totalPanel;
    private JPanel changingPanel;
    private JLabel lName;
    private JLabel lAge;
    private JLabel lPassport;
    private JRadioButton мRadioButton;
    private JRadioButton жRadioButton;
    private JPanel changeSex;
    private JLabel lSex = new JLabel();
    private JTextField nameField = new JTextField();
    private JTextField ageField = new JTextField();
    private JTextField passportField = new JTextField();

    public Profile(Controller controller, Long id) {
        Tourist tourist = controller.getTouristById(id);
        String name = tourist.getName();
        if (name.length() > 20) {
            String[] fio = name.split(" ");
            name = "<html>" + fio[0] + "<br>" + fio[1] + "<br>" + fio[2];
        }
        lName.setText(name);
        lAge.setText(String.valueOf(tourist.getAge()));
        lPassport.setText(tourist.getPassport());
        lSex.setText(tourist.getSex());
        changingPanel.remove(changeSex);
        changingPanel.setLayout(new GridLayout(4, 1));
        changingPanel.add(lSex);
        setTitle("Profile");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);


        редактироватьButton.addActionListener(e -> {
            if (редактироватьButton.getText().equals("Редактировать")) {
                final Tourist updatedTourist = controller.getTouristById(id);
                changingPanel.remove(lName);
                changingPanel.remove(lAge);
                changingPanel.remove(lPassport);
                changingPanel.remove(lSex);
                changingPanel.setLayout(new GridLayout(4, 1));
                nameField.setText(updatedTourist.getName());
                ageField.setText(String.valueOf(updatedTourist.getAge()));
                passportField.setText(updatedTourist.getPassport());
                changingPanel.add(nameField);
                changingPanel.add(ageField);
                changingPanel.add(passportField);
                мRadioButton.setSelected(false);
                жRadioButton.setSelected(false);
                changingPanel.add(changeSex);
                редактироватьButton.setText("Сохранить");
                validate();
                repaint();
            }


            //if button 'save'
            else {
                String sex;
                if (мRadioButton.isSelected()) sex = "m";
                else if (жRadioButton.isSelected()) sex = "f";
                else {
                    JOptionPane.showMessageDialog(null, "Выберите пол");
                    return;
                }
                int r = controller.updateTourist(
                        id,
                        nameField.getText(),
                        Integer.parseInt(ageField.getText()),
                        passportField.getText(),
                        sex
                );
                final Tourist updatedTourist = controller.getTouristById(id);
                if (r == -1) {
                    JOptionPane.showMessageDialog(null, "Неверно заполнены данные");
                    return;
                }
                changingPanel.remove(nameField);
                changingPanel.remove(ageField);
                changingPanel.remove(passportField);
                changingPanel.remove(changeSex);
                changingPanel.setLayout(new GridLayout(4, 1));

                String touristName = updatedTourist.getName();
                if (touristName.length() > 20) {
                    String[] fio = touristName.split(" ");
                    touristName = "<html>" + fio[0] + "<br>" + fio[1] + "<br>" + fio[2];
                }

                lName.setText(touristName);
                lAge.setText(String.valueOf(updatedTourist.getAge()));
                lPassport.setText(updatedTourist.getPassport());
                lSex.setText(updatedTourist.getSex());
                changingPanel.add(lName);
                changingPanel.add(lAge);
                changingPanel.add(lPassport);
                changingPanel.add(lSex);
                редактироватьButton.setText("Редактировать");
                validate();
                repaint();
            }
        });
    }

}
