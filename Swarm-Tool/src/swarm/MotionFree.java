package swarm;

public class MotionFree extends Motion {
	@Override
	protected void randomizeVelocityVector(SwarmAgent agent, double[] componentsVectorVelocity) {
		for(int indexComponent = 0; indexComponent < componentsVectorVelocity.length; indexComponent++) {
			componentsVectorVelocity[indexComponent] = generateRandomVelocityVectorComponent();
		}
	}
}