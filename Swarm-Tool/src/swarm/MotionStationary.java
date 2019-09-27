package swarm;

public class MotionStationary extends Motion {
	private static final Motion MOTION = new MotionStationary();
	
	public static Motion get() {
		return MOTION;
	}
	
	private MotionStationary() {
		
	}
	
	@Override
	protected void generateVelocityVector(SwarmAgent agent, double[] componentsVectorVelocity) {
		
	}
}