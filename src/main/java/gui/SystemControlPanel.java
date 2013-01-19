package gui;

import agents.ManagerAgent;
import agents.MonitoringParams;
import agents.StatusMachineAgent;
import jade.core.AID;
import machine.MachineStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SystemControlPanel extends JFrame {
    private ManagerAgent managerAgent;
    
    private JCheckBox onOffMachineCheckBox;
    private JCheckBox startStopMachineCheckBox;
    private JCheckBox temperatureMonitoringCheckBox;
    private JCheckBox machineTimeMonitoringCheckBox;
    private JCheckBox machineOperationTimeMonitoringCheckBox;
    private JCheckBox coolantLevelMonitoringCheckBox;
    private JCheckBox cuttingForceMonitoringCheckBox;
    private JCheckBox vibrationMonitoringCheckBox;

    private JScrollPane tableScroll;
    private JTable machineListTable;
    private MachineTableModel model;

    private JTextArea logArea;
    private AgentsTreePanel agentsTree;
    private JScrollPane treeScroll;

    private JLabel titleLabel;
    private JLabel workingLabel;
    private JLabel measurementParametersLabel;
    private JLabel logLabel;

    private JButton addMachineAgentButton;

    private Logger logger;
    
    public SystemControlPanel(ManagerAgent agent) {
        managerAgent = agent;
        initComponents();
        createLayout();
    }
    
    private void initComponents() {
        logger = LoggerFactory.getLogger(getClass());

        machineListTable = new JTable();
        model = new MachineTableModel();
        machineListTable.setModel(model);
        ListSelectionModel listSelectionModel = machineListTable.getSelectionModel();
        listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if (machineListTable.getSelectedRow() != -1) {
                    AID selectedAgent = model.getSelectedAgent(machineListTable.getSelectedRow());
                    managerAgent.getMachineStatus(selectedAgent);
                }
            }
        });
        tableScroll = new JScrollPane(machineListTable);
        logArea = new JTextArea();

        agentsTree = new AgentsTreePanel(managerAgent);
        treeScroll = new JScrollPane(agentsTree);

        titleLabel = new JLabel("System monitorowania");
        workingLabel = new JLabel("obróbka");
        measurementParametersLabel = new JLabel("pomiar parametrów");
        logLabel = new JLabel("Log aplikacji");

        onOffMachineCheckBox = new JCheckBox("on/off");
        onOffMachineCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (onOffMachineCheckBox.isSelected()) {
                    managerAgent.sendMessageToMachine(getSelectedAgent().getLocalName(), MachineStatus.ON);
                } else {
                    managerAgent.sendMessageToMachine(getSelectedAgent().getLocalName(), MachineStatus.OFF);
                    clearCheckBoxesForMonitoring();
                }
            }
        });
        startStopMachineCheckBox = new JCheckBox("start/stop");
        startStopMachineCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (startStopMachineCheckBox.isSelected()) {
                    managerAgent.sendMessageToMachine(getSelectedAgent().getLocalName(), MachineStatus.START);
                } else {
                    managerAgent.sendMessageToMachine(getSelectedAgent().getLocalName(), MachineStatus.STOP);
                }
            }
        });
        temperatureMonitoringCheckBox = new JCheckBox("temperatura");
        temperatureMonitoringCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (temperatureMonitoringCheckBox.isSelected()) {
                    managerAgent.createMonitoringAgent(getSelectedAgent(), MonitoringParams.TEMPERATURE);
                } else {
                    managerAgent.destroyMonitoringAgent(getSelectedAgent().getLocalName(),
                            MonitoringParams.TEMPERATURE.getAgent());
                }
            }
        });
        machineTimeMonitoringCheckBox = new JCheckBox("czas pracy obrabiarki");
        machineTimeMonitoringCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (machineTimeMonitoringCheckBox.isSelected()) {
                    managerAgent.createMonitoringAgent(getSelectedAgent(), MonitoringParams.MACHINE_TIME);
                } else {
                    managerAgent.destroyMonitoringAgent(getSelectedAgent().getLocalName(),
                            MonitoringParams.MACHINE_TIME.getAgent());
                }
            }
        });
        machineOperationTimeMonitoringCheckBox = new JCheckBox("czas skrawania");
        machineOperationTimeMonitoringCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (machineOperationTimeMonitoringCheckBox.isSelected()) {
                    managerAgent.createMonitoringAgent(getSelectedAgent(), MonitoringParams.MACHINE_OPERATION_TIME);
                } else {
                    managerAgent.destroyMonitoringAgent(getSelectedAgent().getLocalName(),
                            MonitoringParams.MACHINE_OPERATION_TIME.getAgent());
                }
            }
        });
        coolantLevelMonitoringCheckBox = new JCheckBox("poziom chłodziwa");
        coolantLevelMonitoringCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (coolantLevelMonitoringCheckBox.isSelected()) {
                    managerAgent.createMonitoringAgent(getSelectedAgent(), MonitoringParams.COOLANT_LEVEL);
                } else {
                    managerAgent.destroyMonitoringAgent(getSelectedAgent().getLocalName(),
                            MonitoringParams.COOLANT_LEVEL.getAgent());
                }
            }
        });
        cuttingForceMonitoringCheckBox = new JCheckBox("siła skrawania");
        cuttingForceMonitoringCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (cuttingForceMonitoringCheckBox.isSelected())
                    managerAgent.createMonitoringAgent(getSelectedAgent(), MonitoringParams.CUTTING_FORCE);
                else
                    managerAgent.destroyMonitoringAgent(getSelectedAgent().getLocalName(),
                            MonitoringParams.CUTTING_FORCE.getAgent());
            }
        });
        vibrationMonitoringCheckBox = new JCheckBox("drgania");
        vibrationMonitoringCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (vibrationMonitoringCheckBox.isSelected())
                    managerAgent.createMonitoringAgent(getSelectedAgent(), MonitoringParams.VIBRATION);
                else
                    managerAgent.destroyMonitoringAgent(getSelectedAgent().getLocalName(),
                            MonitoringParams.VIBRATION.getAgent());
            }
        });

        addMachineAgentButton = new JButton("Dodaj agenta maszyny");
        addMachineAgentButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                NewMachineAgentPanel addAgentPanel = new NewMachineAgentPanel(managerAgent);
                addAgentPanel.setSize(350, 90);
                addAgentPanel.setVisible(true);
            }
        });
    }

    public void setMonitoringStatuses(StatusMachineAgent status) {
        temperatureMonitoringCheckBox.setSelected(status.isTemperatureMonitoring());
        machineTimeMonitoringCheckBox.setSelected(status.isMachineTimeMonitoring());
        machineOperationTimeMonitoringCheckBox.setSelected(status.isMachineOperationTimeMonitoring());
        coolantLevelMonitoringCheckBox.setSelected(status.isCoolantLevelMonitoring());
        cuttingForceMonitoringCheckBox.setSelected(status.isCuttingForceMonitoring());
        vibrationMonitoringCheckBox.setSelected(status.isVibrationMonitoring());
    }

    private void clearCheckBoxesForMonitoring() {
        startStopMachineCheckBox.setSelected(false);
        temperatureMonitoringCheckBox.setSelected(false);
        machineTimeMonitoringCheckBox.setSelected(false);
        machineOperationTimeMonitoringCheckBox.setSelected(false);
        coolantLevelMonitoringCheckBox.setSelected(false);
        cuttingForceMonitoringCheckBox.setSelected(false);
        vibrationMonitoringCheckBox.setSelected(false);
    }
    
    private void createLayout() {
        GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(titleLabel)
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(tableScroll)
                                        .addComponent(addMachineAgentButton))
                                .addGap(10)
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(workingLabel)
                                        .addComponent(onOffMachineCheckBox)
                                        .addComponent(startStopMachineCheckBox)
                                        .addComponent(measurementParametersLabel)
                                        .addComponent(temperatureMonitoringCheckBox)
                                        .addComponent(machineTimeMonitoringCheckBox)
                                        .addComponent(machineOperationTimeMonitoringCheckBox)
                                        .addComponent(coolantLevelMonitoringCheckBox)
                                        .addComponent(cuttingForceMonitoringCheckBox)
                                        .addComponent(vibrationMonitoringCheckBox))
                                .addGap(10)
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(logLabel)
                                        .addGap(5)
                                        .addComponent(treeScroll))
                                .addContainerGap()
                        ));

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(titleLabel)
                        .addGroup(layout.createParallelGroup()
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(tableScroll)
                                        .addGap(5)
                                        .addComponent(addMachineAgentButton))
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(workingLabel)
                                        .addGap(10)
                                        .addComponent(onOffMachineCheckBox)
                                        .addComponent(startStopMachineCheckBox)
                                        .addGap(10)
                                        .addComponent(measurementParametersLabel)
                                        .addGap(10)
                                        .addComponent(temperatureMonitoringCheckBox)
                                        .addComponent(machineTimeMonitoringCheckBox)
                                        .addComponent(machineOperationTimeMonitoringCheckBox)
                                        .addComponent(coolantLevelMonitoringCheckBox)
                                        .addComponent(cuttingForceMonitoringCheckBox)
                                        .addComponent(vibrationMonitoringCheckBox))
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(logLabel)
                                        .addComponent(treeScroll)))
                        .addContainerGap()
        );
    }
    
    private AID getSelectedAgent() {
        return model.getSelectedAgent(machineListTable.getSelectedRow());
    }

    public void setModel(List<AID> aids) {
        model.setAgents(aids);
        setAgentsInTree(aids);
    }

    private void setAgentsInTree(List<AID> aids) {
        for (AID aid : aids) {
            agentsTree.addNewAgent(aid);
        }
    }
}
