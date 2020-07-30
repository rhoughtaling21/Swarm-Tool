package swarm;

import java.util.concurrent.ThreadLocalRandom;

public class MotionOrthogonal extends Motion {
	private static final Motion MOTION = new MotionOrthogonal();
	
	public static Motion get() {
		return MOTION;
	}
	
	private MotionOrthogonal() {
		
	}
	
	@Override
	protected void generateVelocityVector(SwarmAgent agent, double[] componentsVectorVelocity) {
		componentsVectorVelocity[ThreadLocalRandom.current().nextInt(componentsVectorVelocity.length)] = generateRandomVelocityVectorComponent();
	}
}