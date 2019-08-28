package swarm;

import java.util.concurrent.ThreadLocalRandom;

public class MotionOrthogonal extends Motion {
	@Override
	protected void randomizeVelocityVector(SwarmAgent agent, double[] componentsVectorVelocity) {
		componentsVectorVelocity[ThreadLocalRandom.current().nextInt(componentsVectorVelocity.length)] = generateRandomVelocityVectorComponent();
	}
}