package agentbehaviourtest;

import java.util.Date;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.behaviours.WakerBehaviour;

public class RequestAgent extends Agent {
	private static final long serialVersionUID = 2984992650678620014L;
	private static int counter = 0;

	@Override
	protected void setup() {
		super.setup();
//		addBehaviour(new BehaviourTest());
//		addBehaviour(new SimpleBehaviourTest());
		addBehaviour(new CyclicBehavoiurTest());
	}
	
	class BehaviourTest extends Behaviour {
		@Override
		public boolean done() {
			if (counter >= 100) {
				return true;
			}
			return false;
		}
		
		@Override
		public void action() {
			counter++;
			System.out.println("Test Behaviour" + counter);
			
		}
	}
	
	class SimpleBehaviourTest extends SimpleBehaviour {

		@Override
		public boolean done() {
			if (counter >= 100) {
				return true;
			}
			return false;
		}
		
		@Override
		public void action() {
			counter++;
			System.out.println("Test Simple Behaviour: " + counter);
		}
	}
	
	class CyclicBehavoiurTest extends CyclicBehaviour {
		private int value = 0;
		@Override
		public void action() {
			System.out.println("Cyclic Behaviour Test: " + value++);

		}
		
	}
	
	class WakerBehaviourTest extends WakerBehaviour {

		public WakerBehaviourTest(Agent a, Date wakeupDate) {
			super(a, wakeupDate);
			// TODO Auto-generated constructor stub
		}

	}
}
