package infrastructure.ui.representative;

import api.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import models.entity.Hotel;
import models.entity.Room;
import models.entity.Tourist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;

import static api.Main.SCREEN_HEIGHT;
import static api.Main.SCREEN_WIDTH;

public class SettleGroup extends JFrame {
    private JPanel totalPanel;
    /*  private JComboBox hotelBox;
      private JComboBox roomBox;
      private JPanel template;*/
    private JPanel listPanel;
    private JButton okButton;
    private int group;
    private List<Room> chosenRoomList = new ArrayList<>();

    public SettleGroup(Controller controller, Integer group) {
        this.group = group;
        setTitle("MainFrame");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);

        List<Tourist> touristList = controller.getTouristListByGroup(group);

        List<Hotel> hotelList = controller.getHotelList();
        String[] hotels = new String[hotelList.size()];
        int i = 0;
        for (Hotel h : hotelList)
            hotels[i++] = h.getTitle();
        fillListPanel(touristList, hotels, controller, hotelList);
        okButton.addActionListener(e -> {
            chosenRoomList.clear();
            Component[] components = listPanel.getComponents();
            for (int i1 = 0; i1 < components.length; ++i1) {
                JComboBox box = (JComboBox) ((JPanel) components[i1]).getComponent(4);
                JComboBox h = (JComboBox) ((JPanel) components[i1]).getComponent(0);
                Tourist t = controller.getTouristListByGroup(group).get(i1);
                Room chosenRoom;
                try {
                    chosenRoom = controller.getFreeRoomsByHotel(hotelList.get(h.getSelectedIndex()), t, group).get(box.getSelectedIndex());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Вы не для всех туристов выбрали комнату!");
                    return;
                }
                chosenRoomList.add(chosenRoom);
            }
            dispose();
            controller.setTouristsToHotels(group, chosenRoomList);
        });
    }

    private void fillListPanel(List<Tourist> tourists, String[] hotels, Controller controller, List<Hotel> hotelList) {
        listPanel.removeAll();
        listPanel.setLayout(new GridLayout(tourists.size(), 1));
        int i = 0;
        for (Tourist t : tourists) {
            JPanel cargoTemplate = getCargoPanelTemplate(t, hotels, controller, hotelList);
            cargoTemplate.setBackground((i++ % 2 == 0) ? new Color(242, 242, 242) : new Color(220, 220, 220));
            listPanel.add(cargoTemplate, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        }
        validate();
        repaint();
    }

    private JPanel getCargoPanelTemplate(Tourist t, String[] hotels, Controller controller, List<Hotel> hotelList) {
        JPanel template = new JPanel();
        template.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        listPanel.add(template, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        JComboBox hotelBox = new JComboBox(new DefaultComboBoxModel(hotels));
        JComboBox roomBox = new JComboBox();
        hotelBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                hotelBox.setSelectedItem(e.getItem());
                List<Room> freeRooms = controller.getFreeRoomsByHotel(hotelList.get(hotelBox.getSelectedIndex()), t, group);
                int j = 0;
                Integer[] roomNums = new Integer[freeRooms.size()];
                for (Room r : freeRooms)
                    roomNums[j++] = r.getRoomNumber();
                roomBox.setModel(new DefaultComboBoxModel(roomNums));
                validate();
                repaint();
            }
        });
        template.add(hotelBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Гостиница");
        template.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText(t.getName());
        template.add(label2, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Комната");
        template.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        template.add(roomBox, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        return template;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        totalPanel = new JPanel();
        totalPanel.setLayout(new GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        totalPanel.setOpaque(true);
        totalPanel.setPreferredSize(new Dimension(300, 220));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        totalPanel.add(panel1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        listPanel = new JPanel();
        listPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        scrollPane1.setViewportView(listPanel);
        final Spacer spacer1 = new Spacer();
        totalPanel.add(spacer1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, 1, null, new Dimension(10, -1), null, 0, false));
        final Spacer spacer2 = new Spacer();
        totalPanel.add(spacer2, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, 1, null, new Dimension(10, -1), null, 0, false));
        final Spacer spacer3 = new Spacer();
        totalPanel.add(spacer3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 10), null, 0, false));
        final Spacer spacer4 = new Spacer();
        totalPanel.add(spacer4, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 10), null, 0, false));
        okButton = new JButton();
        okButton.setEnabled(true);
        okButton.setText("Сохранить");
        totalPanel.add(okButton, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return totalPanel;
    }

}
