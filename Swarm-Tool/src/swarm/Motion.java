package swarm;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Motion {
	protected static final double MAGNITUDE_COMPONENT_VECTOR_VELOCITY_MAXIMUM = 0.5;
	private static final double MAGNITUDE_COMPONENT_VECTOR_VELOCITY_MINIMUM = 0;
	private static final double MAGNITUDE_COMPONENT_VECTOR_VELOCITY_RANGE = MAGNITUDE_COMPONENT_VECTOR_VELOCITY_MAXIMUM - MAGNITUDE_COMPONENT_VECTOR_VELOCITY_MINIMUM;
	private static final double FREQUENCY_ACCELERATION = 0.1;
	
	protected static final double generateRandomVelocityVectorComponent() {
		ThreadLocalRandom generatorNumbersRandom = ThreadLocalRandom.current();
		
		double componentVectorVelocity = MAGNITUDE_COMPONENT_VECTOR_VELOCITY_MINIMUM + (generatorNumbersRandom.nextDouble(MAGNITUDE_COMPONENT_VECTOR_VELOCITY_RANGE));
		
		if(generatorNumbersRandom.nextDouble() < 0.5) {
			componentVectorVelocity *= -1;
		}
		
		return componentVectorVelocity;
	}
	
	protected abstract void generateVelocityVector(SwarmAgent agent, double[] componentsVectorVelocity);
	
	protected final void updateVelocity(SwarmAgent agent) {
		double[] componentsVectorVelocity = new double[2];
		generateVelocityVector(agent, componentsVectorVelocity);
		agent.setVelocityCells(componentsVectorVelocity[0], componentsVectorVelocity[1]);
	}
	
	protected boolean accelerate(SwarmAgent agent) {
		return ThreadLocalRandom.current().nextDouble() < FREQUENCY_ACCELERATION;
	}
	
	final void questionVelocity(SwarmAgent agent) {
		if(accelerate(agent)) {
			updateVelocity(agent);
		}
	}
	
	protected void initializeVelocityVector(SwarmAgent agent, double[] componentsVectorVelocityInitial) {
		generateVelocityVector(agent, componentsVectorVelocityInitial);
	}
	
	final void initializeVelocityVector(SwarmAgent agent) {
		double[] componentsVectorVelocityInitial = new double[2];
		initializeVelocityVector(agent, componentsVectorVelocityInitial);
		agent.setVelocityCells(componentsVectorVelocityInitial[0], componentsVectorVelocityInitial[1]);
	}
}