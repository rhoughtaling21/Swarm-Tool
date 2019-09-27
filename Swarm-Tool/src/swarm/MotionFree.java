package swarm;

public class MotionFree extends Motion {
	private static final Motion MOTION = new MotionFree();
	
	public static Motion get() {
		return MOTION;
	}
	
	private MotionFree() {
		
	}
	
	@Override
	protected void generateVelocityVector(SwarmAgent agent, double[] componentsVectorVelocity) {
		for(int indexComponent = 0; indexComponent < componentsVectorVelocity.length; indexComponent++) {
			componentsVectorVelocity[indexComponent] = generateRandomVelocityVectorComponent();
		}
	}
}