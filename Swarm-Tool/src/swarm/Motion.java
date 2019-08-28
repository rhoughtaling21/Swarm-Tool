package swarm;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Motion {
	public static final double MAGNITUDE_COMPONENT_VECTOR_VELOCITY_MAXIMUM = 0.5;
	private static final double MAGNITUDE_COMPONENT_VECTOR_VELOCITY_MINIMUM = 0;
	private static final double MAGNITUDE_COMPONENT_VECTOR_VELOCITY_RANGE = MAGNITUDE_COMPONENT_VECTOR_VELOCITY_MAXIMUM - MAGNITUDE_COMPONENT_VECTOR_VELOCITY_MINIMUM;
	private static final double FREQUENCY_ACCELERATION = 0.1;
	
	protected static final double generateRandomVelocityVectorComponent() {
		Random generatorNumbersRandom = ThreadLocalRandom.current();
		
		double componentVectorVelocity = MAGNITUDE_COMPONENT_VECTOR_VELOCITY_MINIMUM + (MAGNITUDE_COMPONENT_VECTOR_VELOCITY_RANGE * generatorNumbersRandom.nextDouble());
		
		if(generatorNumbersRandom.nextDouble() < 0.5) {
			componentVectorVelocity *= -1;
		}
		
		return componentVectorVelocity;
	}
	
	protected abstract void randomizeVelocityVector(SwarmAgent agent, double[] componentsVectorVelocity);
	
	final void randomizeVelocityVector(SwarmAgent agent) {
		double[] componentsVectorVelocity = new double[2];
		randomizeVelocityVector(agent, componentsVectorVelocity);
		agent.setVelocityCells(componentsVectorVelocity[0], componentsVectorVelocity[1]);
	}
	
	protected boolean updateVelocity(SwarmAgent agent) {
		return ThreadLocalRandom.current().nextDouble() < FREQUENCY_ACCELERATION;
	}
	
	final void questionVelocity(SwarmAgent agent) {
		if(updateVelocity(agent)) {
			randomizeVelocityVector(agent);
		}
	}
	
	protected void initializeVelocityVector(SwarmAgent agent, double[] componentsVectorVelocityInitial) {
		randomizeVelocityVector(agent, componentsVectorVelocityInitial);
	}
	
	final void initializeVelocityVector(SwarmAgent agent) {
		double[] componentsVectorVelocityInitial = new double[2];
		initializeVelocityVector(agent, componentsVectorVelocityInitial);
		agent.setVelocityCells(componentsVectorVelocityInitial[0], componentsVectorVelocityInitial[1]);
	}
}