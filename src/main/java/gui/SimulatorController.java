package gui;

import agents.ControllerAgent;
import machine.Machine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulatorController extends JFrame {
    private ControllerAgent controllerAgent;

    private JCheckBox onOffMachineCheckBox;
    private JCheckBox startStopMachineCheckBox;
    private JCheckBox temperatureMonitoringCheckBox;
    private JCheckBox machineTimeMonitoringCheckBox;
    private JCheckBox machineOperationTimeMonitoringCheckBox;
    private JCheckBox coolantLevelMonitoringCheckBox;
    private JCheckBox cuttingForceMonitoringCheckBox;
    private JCheckBox vibrationMonitoringCheckBox;

    public SimulatorController(ControllerAgent agent) {
        controllerAgent = agent;
        initComponents();
        createLayout();
    }

    private void initComponents() {
        onOffMachineCheckBox = new JCheckBox("on/off");
        onOffMachineCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (onOffMachineCheckBox.isSelected()) {
                    controllerAgent.runMachine();
                } else {
                    controllerAgent.offMachine();
                    clearCheckBoxesForMonitoring();
                }
            }
        });
        startStopMachineCheckBox = new JCheckBox("start/stop");
        startStopMachineCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (startStopMachineCheckBox.isSelected()) {
                    controllerAgent.startMachine();
                } else {
                    controllerAgent.stopMachine();
                }
            }
        });
        temperatureMonitoringCheckBox = new JCheckBox("pomiar temperatury");
        temperatureMonitoringCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (temperatureMonitoringCheckBox.isSelected()) {
                    controllerAgent.createTemperatureAgent();
                } else {
                    controllerAgent.destroyAgent("TemperatureAgent");
                }
            }
        });
        machineTimeMonitoringCheckBox = new JCheckBox("pomiar czasu pracy obrabiarki");
        machineTimeMonitoringCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (machineTimeMonitoringCheckBox.isSelected()) {
                    controllerAgent.createWorkAgent();
                } else {
                    controllerAgent.destroyAgent("WorkAgent");
                }
            }
        });
        machineOperationTimeMonitoringCheckBox = new JCheckBox("pomiar czasu skrawania");
        machineOperationTimeMonitoringCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (machineOperationTimeMonitoringCheckBox.isSelected()) {
                    controllerAgent.createOperationAgent();
                } else {
                    controllerAgent.destroyAgent("OperationAgent");
                }
            }
        });
        coolantLevelMonitoringCheckBox = new JCheckBox("pomiar poziomu chłodziwa");
        coolantLevelMonitoringCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (coolantLevelMonitoringCheckBox.isSelected()) {
                    controllerAgent.createCoolantAgent();
                } else {
                    controllerAgent.destroyAgent("CoolantAgent");
                }
            }
        });
        cuttingForceMonitoringCheckBox = new JCheckBox("pomiar siły skrawania");
        vibrationMonitoringCheckBox = new JCheckBox("pomiar drgań");
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
                        .addComponent(onOffMachineCheckBox)
                        .addComponent(startStopMachineCheckBox)
                        .addComponent(temperatureMonitoringCheckBox)
                        .addComponent(machineTimeMonitoringCheckBox)
                        .addComponent(machineOperationTimeMonitoringCheckBox)
                        .addComponent(coolantLevelMonitoringCheckBox)
                        .addComponent(cuttingForceMonitoringCheckBox)
                        .addComponent(vibrationMonitoringCheckBox)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                    .addComponent(onOffMachineCheckBox)
                    .addComponent(startStopMachineCheckBox)
                    .addGap(10)
                    .addComponent(temperatureMonitoringCheckBox)
                    .addComponent(machineTimeMonitoringCheckBox)
                    .addComponent(machineOperationTimeMonitoringCheckBox)
                    .addComponent(coolantLevelMonitoringCheckBox)
                    .addComponent(cuttingForceMonitoringCheckBox)
                    .addComponent(vibrationMonitoringCheckBox)
        );
    }

}
