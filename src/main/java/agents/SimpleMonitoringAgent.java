package agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import machine.Machine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import static services.BundleService.message;

public class SimpleMonitoringAgent extends Agent {
    private Machine machine;
    private String currentParameterValue;
    private String parameterName;
    private String statement;
    private Logger logger;

    @Override
	protected void setup() {
		super.setup();
        logger = LoggerFactory.getLogger(getClass());
        logger.info(message("create.monitoring.agent", getLocalName()));
        machine = (Machine) getArguments()[0];
        parameterName = (String) getArguments()[1];
        statement = (String) getArguments()[2];
        if (machine == null) {
            doDelete();
        } else {
            registerService();
		    addBehaviour(new MonitoringBehaviour());
            addBehaviour(new DoDeleteBehaviour());
        }
	}

    private void registerService() {
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setName(getLocalName());
        sd.setType("monitoringAgent");
        dfd.addServices(sd);
        dfd.setName(getAID());
        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            logger.warn(message("fipa.exception.register.service.failure", getLocalName()), e);
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

    class MonitoringBehaviour extends CyclicBehaviour {
        private Method getterForParam;
		@Override
		public void action() {
            try {
                TimeUnit.SECONDS.sleep(1);
                if (!machine.isWork()) {
                    doDelete();
                    return;
                }
                getterForParam = machine.getClass().getMethod("get" + parameterName);
                String parameter = (String) getterForParam.invoke(machine);
                if (parameter != null && !parameter.equals(currentParameterValue)) {
                    currentParameterValue = parameter;
                    System.out.println(statement + parameter);
                }
            } catch (NoSuchMethodException e) {
                logger.info(message("no.such.method.exception.no.method", parameterName), e);
            } catch (InterruptedException e) {
                logger.info(message("interrupted.exception.can.not.sleep.thread"), e);
            } catch (Exception e) {
                logger.info(message("exception.can.not.use.method", parameterName), e);
            }

		}
	}

    class DoDeleteBehaviour extends CyclicBehaviour  {

        @Override
        public void action() {
            ACLMessage message = receive();
            if (message == null) {
                block();
            } else {
                if (message.getContent().equals("doDelete")) {
                    doDelete();
                }
            }
        }
    }
}
