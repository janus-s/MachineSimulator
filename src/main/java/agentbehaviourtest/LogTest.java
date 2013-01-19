package agentbehaviourtest;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * User: janus
 * Date: 12-12-11
 * Time: 13:58
 */
public class LogTest extends JFrame {
    private JTextPane textPane;
    private JTextField firstTextField;
    private JTextField secondTextField;
    private JScrollPane scroll;

    private long count = 1;

    public LogTest() throws BadLocationException {
        initComponent();
        createLayout();
    }

    private void initComponent() {
        textPane = new JTextPane();
        scroll = new JScrollPane(textPane);
        firstTextField = new JTextField();
        firstTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    writeText(firstTextField.getText(), 1);
                    clearText(1);
                }
            }
        });
        secondTextField = new JTextField();
        secondTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    writeText(secondTextField.getText(), 2);
                    clearText(2);
                }
            }
        });
    }

    private void createLayout() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(scroll)
                                .addComponent(firstTextField)
                                .addComponent(secondTextField))
                        .addContainerGap()
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scroll)
                        .addGap(10)
                        .addComponent(firstTextField, 22, 22, 22)
                        .addGap(10)
                        .addComponent(secondTextField, 22, 22, 22)
                        .addContainerGap()
        );
    }

    private void writeText(String text, int numberAgent) {
        try {
            SimpleAttributeSet set = new SimpleAttributeSet();
            StyleConstants.setBold(set, true);
            StyleConstants.setItalic(set, true);
            if (numberAgent == 1)
                StyleConstants.setForeground(set, Color.red);
            else if (numberAgent == 2)
                StyleConstants.setForeground(set, Color.blue);
            StyleConstants.setFontSize(set, 16);

            Document doc = textPane.getStyledDocument();
            doc.insertString(doc.getLength(), count++ + ": " + text + "\n", set);
        } catch (BadLocationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void clearText(int agentNumber) {
        if (agentNumber == 1)
            firstTextField.setText("");
        else if (agentNumber == 2)
            secondTextField.setText("");
    }
}
