package agentbehaviourtest;

import javax.swing.*;
import java.awt.*;

import static java.awt.EventQueue.*;

/**
 * Created by IntelliJ IDEA.
 * User: janus
 * Date: 12-11-07
 * Time: 07:57
 * To change this template use File | Settings | File Templates.
 */
public class TreeTestRunner {

    public static void main(String[] args) {
        invokeLater(new Runnable() {

            public void run() {
                TreeTest gui = new TreeTest();
                gui.setSize(250, 500);
                gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                gui.setLocation(dim.width/2-gui.getSize().width/2, dim.height/2-gui.getSize().height/2);
                gui.setVisible(true);
            }
        });
    }
}
