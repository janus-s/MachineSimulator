package agents;

import gui.SystemControlPanel;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import machine.AgentParameters;
import machine.MachineStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManagerAgent extends GuiAgent {
    private List<AID> machineAgents = new ArrayList<AID>();
    private SystemControlPanel gui;
    private Logger logger;

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {
    }

    @Override
    protected void setup() {
        super.setup();
        logger = LoggerFactory.getLogger(getClass());

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
          } catch(Exception e) {
            logger.warn("Nie udało się ustawić wyglądu aplikacji", e);
            throw new RuntimeException(e);
          }

        EventQueue.invokeLater(new Runnable() {

            public void run() {
                gui = new SystemControlPanel(ManagerAgent.this);
                gui.setSize(800, 700);
                gui.setVisible(true);
//                gui.setExtendedState(Frame.MAXIMIZED_BOTH);
            }
        });

        addBehaviour(new SearchAgentsBehaviour(this, 1000));
        addBehaviour(new SetStatusBehaviour());
    }

    public void sendMessageToMachine(String machineName, MachineStatus status) {
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(findMachineAgent(machineName));
        trySetStatusMachine(message, status);
        send(message);
    }

    private void trySetStatusMachine(ACLMessage message, MachineStatus status) {
        try {
            message.setContentObject(status);
        } catch (IOException e) {
            logger.error("Nie powiodła się próba ustawienia statusu maszyny w wiadomości. Sender: " + getLocalName(), e);
        }
    }

    public void createMonitoringAgent(AID machineAgent, MonitoringParams params) {
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(machineAgent);
        message.setConversationId("createMonitoringAgent");
        trySetAgentParameter(message, new AgentParameters(machineAgent.getLocalName(), params));
        send(message);
    }

    private void trySetAgentParameter(ACLMessage msg, AgentParameters parameters) {
        try {
            msg.setContentObject(parameters);
        } catch (IOException e) {
            logger.error("Nie powiodła się próba wstawienia do wiadomości parametrów agenta, sender: " + getLocalName(), e);
        }
    }

    public void createMachineAgent(String machineName) {
        AgentContainer container = getContainerController();
        try {
            AgentController controller = container.createNewAgent(
                    machineName + "Agent",
                    MachineAgent.class.getName(),
                    null
            );
            controller.start();
        } catch (Exception e) {
            logger.warn("Nie powiodła się próba utworzenia agenta dla maszyny " + machineName);
            throw new RuntimeException(e);
        }
    }

    public void destroyMachineAgent(String localName) {
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(findMachineAgent(localName));
        msg.setContent("doDelete");
        send(msg);
    }

    public void destroyMonitoringAgent(String machineName, String monitoringAgentName) {
        //TODO wysłać wiadomość do agenta maszyny aby zakończył pracę dany agent monitorujący
    }

    private AID findMachineAgent(String machineName) {
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setName(machineName + "Agent");
        sd.setType("machineAgent");
        template.addServices(sd);
        try {
            DFAgentDescription[] result = DFService.search(this, template);
            return result[0].getName();
        } catch (FIPAException e) {
            logger.warn("Nie powiodła się próba zlokalizowania agenta " + machineName + "Agent przez agenta " + getLocalName(), e);
            return null;
        }
    }

    public void getMachineStatus(AID selectedAgent) {
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(selectedAgent);
        message.setContent("getMachineStatus");
        send(message);
    }

    class SearchAgentsBehaviour extends TickerBehaviour {
        Agent agent;

        SearchAgentsBehaviour(Agent agent, long period) {
            super(agent, period);
            this.agent = agent;
        }

        @Override
        protected void onTick() {
            if (gui != null)
                gui.setModel(convertAgentDescriptionToAID(findMachineAgents()));
        }

        private DFAgentDescription[] findMachineAgents() {
            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType("machineAgent");
            template.addServices(sd);
            try {
                DFAgentDescription[] result = DFService.search(agent, template);
                return result;
            } catch (FIPAException e) {
                logger.warn("Nie powiodała się próba odnalezienia agentów maszyny przez agenta " + getLocalName(), e);
                return null;
            }
        }

        private List<AID> convertAgentDescriptionToAID(DFAgentDescription[] agentList) {
            List<AID> agents = new ArrayList<AID>();
            for (DFAgentDescription description : agentList) {
                agents.add(description.getName());
            }
            return agents;
        }

    }

    class SetStatusBehaviour extends CyclicBehaviour {

        @Override
        public void action() {
            ACLMessage reply = receive();
            if (reply == null) {
                block();
            } else {
                if (reply.getContent().equals("machineStatus")) {
                    try {
                        StatusMachineAgent status = (StatusMachineAgent) reply.getContentObject();
                        gui.setMonitoringStatuses(status);
                    } catch (UnreadableException e) {
                        logger.error("Nie powiodła się próba odczytania zawartości wiadomości przez agenta "+ getLocalName(), e);
                    } finally {
                        block();
                    }
                }
            }
        }
    }
}
