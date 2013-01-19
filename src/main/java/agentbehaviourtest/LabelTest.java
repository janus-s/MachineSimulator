package agentbehaviourtest;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: janus
 * Date: 12-11-07
 * Time: 08:26
 * To change this template use File | Settings | File Templates.
 */
public class LabelTest {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 80);
        createLayout(frame);
        frame.setVisible(true);
    }

    private static void createLayout(JFrame frame) {
        ImageIcon icon = createImageIcon("computer_icon.jpeg", "some picture");
        icon = new ImageIcon(icon.getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH));

        JLabel label = new JLabel("Janusz", icon, JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.RIGHT);

        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label)
                .addContainerGap());

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label)
                .addContainerGap());
    }

    private static ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = LabelTest.class.getClassLoader().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
