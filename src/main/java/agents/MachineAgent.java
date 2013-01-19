package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import machine.AgentParameters;
import machine.Machine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static services.BundleService.message;

/**
 * User: janus
 * Date: 12-10-17
 * Time: 12:37
 */
public class MachineAgent extends Agent {
    private Machine machine;
    private Logger logger;

    private void runMachine() {
        machine = new Machine();
    }

    private void offMachine() {
        machine.off();
    }

    private void startMachine() {
        machine.start();
    }

    private void stopMachine() {
        machine.stop();
    }

    private void destroyAgent(String localName) {
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
            logger.error(message("fipa.exception.can.not.find.monitoring.agent"), e);
            return null;
        }
    }

    @Override
    protected void setup() {
        logger = LoggerFactory.getLogger(getClass());
        machine = new Machine();
        registerService();
        addBehaviour(new CreateMonitoringAgentBehaviour());
        logger.info(message("create.new.machine.agent", getLocalName()));
    }

    private void registerService() {
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setName(getLocalName());
        sd.setType("machineAgent");
        dfd.addServices(sd);
        dfd.setName(getAID());
        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            logger.error(message("fipa.exception.register.service.failure", getLocalName()), e);
        }
    }

    @Override
    protected void takeDown() {
        super.takeDown();
        unregisterServices();
    }

    private void unregisterServices() {
        try {
            DFService.deregister(this);
        } catch (FIPAException e) {
            logger.error(message("fipa.exception.unregister.service.failure", getLocalName()), e);
        }
    }

    @Override
    public void doDelete() {
        super.doDelete();
        logger.info(message("agent.do.delete", getLocalName()));
    }

    class GetStatusBehaviour extends CyclicBehaviour {
        boolean isWaitingForStatus = false;
        DFAgentDescription[] agentDescriptions;
        StatusMachineAgent status = new StatusMachineAgent();
        AID requestSender;
        int answers = 0;

        @Override
        public void action() {
            if (!isWaitingForStatus) {
                ACLMessage reply = receive();
                if (reply == null) {
                    block();
                } else {
                    if (reply.getContent().equals("getMachineStatus")) {
                        requestSender = reply.getSender();
                        agentDescriptions = findMonitoringAgents();
                        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
                        for (DFAgentDescription agentDescription : agentDescriptions) {
                            message.addReceiver(agentDescription.getName());
                        }
                        message.setContent("getMachineStatus");
                        send(message);
                    }
                }
            } else {
                ACLMessage replyFromMonitoringAgent = receive();
                if (replyFromMonitoringAgent == null) {
                    block();
                } else {
                    if (replyFromMonitoringAgent.getContent().equals("machineStatus")) {
                        try {
                            setStatus(replyFromMonitoringAgent.getContent(), (Boolean) replyFromMonitoringAgent.getContentObject());
                            answers += 1;
                        } catch (UnreadableException e) {
                            logger.error(message("unreadable.exception.can.not.read.content.object.from.message"), e);
                        }
                    }
                }
                if (agentDescriptions.length == answers) {
                    try {
                        ACLMessage answer = new ACLMessage(ACLMessage.INFORM);
                        answer.addReceiver(requestSender);
                        answer.setContent("machineStatus");
                        answer.setContentObject(status);
                        send(answer);
                    } catch (IOException e) {
                        logger.error(message("io.exception.can.not.write.object.to.message"), e);
                    }
                }
            }
        }

        private void setStatus(String param, Boolean isMonitored) {
            if (param.equals("Temp"))
                status.setTemperatureMonitoring(isMonitored);
            if (param.equals("Period"))
                status.setMachineTimeMonitoring(isMonitored);
            if (param.equals("OperationPeriod"))
                status.setMachineOperationTimeMonitoring(isMonitored);
            if (param.equals("CoolantLevel"))
                status.setCoolantLevelMonitoring(isMonitored);
            if (param.equals("CuttingForce"))
                status.setCuttingForceMonitoring(isMonitored);
            if (param.equals("Vibration"))
                status.setVibrationMonitoring(isMonitored);
        }

        private DFAgentDescription[] findMonitoringAgents() {
            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType("monitoringAgent");
            template.addServices(sd);
            try {
                DFAgentDescription[] result = DFService.search(MachineAgent.this, template);
                return result;
            } catch (FIPAException e) {
                logger.warn(message("fipa.exception.can.not.find.any.monitoring.agents", getLocalName()), e);
                return null;
            }
        }
    }

    class CreateMonitoringAgentBehaviour extends CyclicBehaviour {

        @Override
        public void action() {
            ACLMessage reply = receive();
            if (reply == null) {
                block();
            } else {
                if (reply.getConversationId() != null && reply.getConversationId().equals("createMonitoringAgent")) {
                    try {
                        AgentParameters parameters = (AgentParameters) reply.getContentObject();
                        createMonitoringAgent(parameters.getAgentName(), parameters.getParamName(), parameters.getComment());
                    } catch (UnreadableException e) {
                        logger.error(message("unreadable.exception.can.not.read.content.object.from.message"), e);
                    } finally {
                        block();
                    }
                } else {
                    block();
                }
            }
        }

        private void createMonitoringAgent(String name, String param, String comment) {
            AgentContainer container = getContainerController();
            try {
                AgentController controller = container.createNewAgent(
                        name,
                        SimpleMonitoringAgent.class.getName(),
                        new Object[]{machine, param, comment}
                );
                controller.start();
            } catch (Exception e) {
                logger.warn(message("exception.can.not.create.monitoring.agent", name));
                throw new RuntimeException(e);
            }
        }
    }
}
