package agentbehaviourtest;

import javax.swing.*;
import javax.swing.text.BadLocationException;

import static java.awt.EventQueue.invokeLater;

/**
 * User: janus
 * Date: 12-12-11
 * Time: 13:58
 */
public class LogTestRunner {

    public static void main(String[] args) {
        invokeLater(new Runnable() {

            public void run() {
                LogTest gui = null;
                try {
                    gui = new LogTest();
                } catch (BadLocationException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                gui.setSize(250, 500);
                gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gui.setVisible(true);
            }
        });
    }
}
