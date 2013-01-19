package gui;

import agents.ManagerAgent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: janus
 * Date: 12-11-02
 * Time: 16:21
 */
public class NewMachineAgentPanel extends JFrame {
    private ManagerAgent managerAgent;

    private JLabel nameLabel;
    private JTextField nameField;
    private JButton addNewAgentButton;

    public NewMachineAgentPanel(ManagerAgent agent) {
        managerAgent = agent;
        initComponents();
        createLayout();
    }

    private void initComponents() {
        nameLabel = new JLabel("Nazwa agenta:");
        nameField = new JTextField();
        addNewAgentButton = new JButton("Dodaj nowego agenta");
        addNewAgentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                managerAgent.createMachineAgent(nameField.getText());
                setVisible(false);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
    }

    private void createLayout() {
        GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(nameLabel)
                        .addComponent(nameField)
                        .addContainerGap())
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(addNewAgentButton)
                        .addContainerGap()));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup()
                        .addComponent(nameLabel)
                        .addComponent(nameField))
                .addComponent(addNewAgentButton)
                .addContainerGap());
    }
}
