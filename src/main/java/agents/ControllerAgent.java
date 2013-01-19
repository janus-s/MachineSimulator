package agents;

import gui.SimulatorController;
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import machine.Machine;

import javax.swing.*;
import java.awt.*;

import static services.BundleService.message;

public class ControllerAgent extends GuiAgent {
    private Machine machine;
    private SimulatorController gui;

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {
    }

    @Override
    protected void setup() {
        super.setup();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
          } catch(Exception e) {
            throw new RuntimeException(e);
          }

        EventQueue.invokeLater(new Runnable() {

            public void run() {
                gui = new SimulatorController(ControllerAgent.this);
                gui.setSize(300, 300);
                gui.setVisible(true);
//                gui.setExtendedState(Frame.MAXIMIZED_BOTH);
            }
        });
    }

    public void runMachine() {
        machine = new Machine();
    }

    public void offMachine() {
        machine.off();
        machine = null;
    }

    public void startMachine() {
        machine.start();
    }

    public void stopMachine() {
        machine.stop();
    }

    public void createTemperatureAgent() {
        AgentContainer containerController = getContainerController();
        try {
            AgentController a = containerController.createNewAgent(
                    "TemperatureAgent",
                    SimpleMonitoringAgent.class.getName(),
                    new Object[]{machine, "Temp", message("machine.temperature.value")});
            a.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void destroyAgent(String localName) {
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(findMonitoringAgent(localName));
        msg.setContent("doDelete");
        send(msg);
    }

    private AID findMonitoringAgent(String localName) {
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setName(localName);
        sd.setType("monitoringAgent");
        template.addServices(sd);
        try {
            DFAgentDescription[] result = DFService.search(this, template);
            return result[0].getName();
        } catch (FIPAException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void createWorkAgent() {
        AgentContainer containerController = getContainerController();
        try {
            AgentController agentController = containerController.createNewAgent(
                    "WorkAgent",
                    SimpleMonitoringAgent.class.getName(),
                    new Object[]{machine, "Period", message("machine.time.work")});
            agentController.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createOperationAgent() {
        AgentContainer containerController = getContainerController();
        try {
            AgentController agentController = containerController.createNewAgent(
                    "OperationAgent",
                    SimpleMonitoringAgent.class.getName(),
                    new Object[]{machine, "OperationPeriod", message("machine.operation.period")});
            agentController.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createCoolantAgent() {
        AgentContainer containerController = getContainerController();
        try {
            AgentController agentController = containerController.createNewAgent(
                    "CoolantAgent",
                    SimpleMonitoringAgent.class.getName(),
                    new Object[]{machine, "CoolantLevel", message("machine.coolant.level")});
            agentController.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
