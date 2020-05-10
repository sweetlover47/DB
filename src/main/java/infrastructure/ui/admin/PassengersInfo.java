package infrastructure.ui.admin;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import models.entity.Cargo;
import models.entity.Flight;
import models.entity.Hotel;
import models.entity.Tourist;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class PassengersInfo extends JFrame {
    private JPanel totalPanel;
    private JTabbedPane tabbedPane1;
    private JPanel touristPanel;
    private JPanel hotelsPanel;
    private JPanel cargoPanel;

    public PassengersInfo(Controller controller, Flight flight) {
        setTitle("Main frame");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);

        List<Tourist> touristList = controller.getPassengersList(flight);
        List<Hotel> hotelList = controller.getPassengerHotels(flight);
        List<Cargo> cargoList = controller.getPassengerCargos(flight);
        fillListPanel1(touristList, touristPanel);
        fillListPanel2(hotelList, hotelsPanel);
        fillListPanel3(cargoList, cargoPanel);
    }


    private void fillListPanel1(List<Tourist> objects, JPanel listPanel) {
        listPanel.removeAll();
        if (objects.isEmpty()) {
            validate();
            repaint();
            return;
        }
        listPanel.setLayout(new GridLayoutManager(objects.size(), 1, new Insets(0, 0, 0, 0), -1, -1));
        int i = 0;
        for (Object t : objects) {
            JPanel cargoTemplate = getCargoPanelTemplate(t, listPanel);
            cargoTemplate.setBackground((i % 2 == 0) ? new Color(242, 242, 242) : new Color(220, 220, 220));
            listPanel.add(cargoTemplate, new GridConstraints(i++, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        }
        validate();
        repaint();
    }

    private void fillListPanel2(List<Hotel> objects, JPanel listPanel) {
        listPanel.removeAll();
        if (objects.isEmpty()) {
            validate();
            repaint();
            return;
        }
        listPanel.setLayout(new GridLayoutManager(objects.size(), 1, new Insets(0, 0, 0, 0), -1, -1));
        int i = 0;
        for (Object t : objects) {
            JPanel cargoTemplate = getCargoPanelTemplate(t, listPanel);
            cargoTemplate.setBackground((i % 2 == 0) ? new Color(242, 242, 242) : new Color(220, 220, 220));
            listPanel.add(cargoTemplate, new GridConstraints(i++, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        }
        validate();
        repaint();
    }

    private void fillListPanel3(List<Cargo> objects, JPanel listPanel) {
        listPanel.removeAll();
        if (objects.isEmpty()) {
            validate();
            repaint();
            return;
        }
        listPanel.setLayout(new GridLayoutManager(objects.size(), 1, new Insets(0, 0, 0, 0), -1, -1));
        int i = 0;
        for (Object t : objects) {
            JPanel cargoTemplate = getCargoPanelTemplate(t, listPanel);
            cargoTemplate.setBackground((i % 2 == 0) ? new Color(242, 242, 242) : new Color(220, 220, 220));
            listPanel.add(cargoTemplate, new GridConstraints(i++, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        }
        validate();
        repaint();
    }

    private JPanel getCargoPanelTemplate(Object t, JPanel listPanel) {
        String text1 = "", text2 = "", text3 = "";
        if (t instanceof Tourist){
            text1 = ((Tourist)t).getName();
        }
        if (t instanceof Hotel){
            text1 = "Отель \""+((Hotel)t).getTitle()+"\"";
        }
        if (t instanceof Cargo){
            text1 = "Груз " + ((Cargo)t).getId();
            text2 = "Тип: " + ((Cargo)t).getKind();
            text3 = "Номер ведомости: " + ((Cargo)t).getStatement().getId();
        }
        JPanel template = new JPanel();
        template.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        listPanel.add(template, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText(text1);
        template.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText(text2);
        template.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText(text3);
        template.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        return template;
    }
}
