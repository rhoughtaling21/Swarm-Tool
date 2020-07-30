package swarm;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import gui.Board;

public class MotionEdge extends Motion {
	private static final MotionEdge MOTION = new MotionEdge();
	
	private static boolean atEdge(Board board, double position) {
		double sizeCell = board.getCellSize();
		return position < sizeCell || position > (Board.SCALE_BOARD - sizeCell);
	}
	
	private static boolean atEdge(SwarmAgent agent, double position) {
		return atEdge(agent.getBoard(), position);
	}
	
	private static boolean atHorizontalEdge(SwarmAgent agent) {
		return atEdge(agent, agent.getCenterY());
	}
	
	private static boolean atVerticalEdge(SwarmAgent agent) {
		return atEdge(agent, agent.getCenterX());
	}
	
	private static boolean leavingEdge(SwarmAgent agent) {
		Board board = agent.getBoard();
		Point2D velocity = agent.getVelocity();
		
		if(velocity.getX() == 0) {
			double positionY = agent.getCenterY();
			
			return atEdge(board, positionY) && !atEdge(board, positionY + velocity.getY());
		}
		
		double positionX = agent.getCenterX();
		
		return atEdge(board, positionX) && !atEdge(board, positionX + velocity.getX());
	}
	
	public static Motion get() {
		return MOTION;
	}
	
	private MotionEdge() {
		
	}
	
	@Override
	protected void generateVelocityVector(SwarmAgent agent, double[] componentsVectorVelocity) {
		ArrayList<Integer> indicesAxes = new ArrayList<Integer>(2);
		
		if(atHorizontalEdge(agent)) {
			indicesAxes.add(0);
		}
		
		if(atVerticalEdge(agent)) {
			indicesAxes.add(1);
		}
		
		componentsVectorVelocity[indicesAxes.get(ThreadLocalRandom.current().nextInt(indicesAxes.size()))] = generateRandomVelocityVectorComponent();
	}
	
	@Override
	protected boolean accelerate(SwarmAgent agent) {
		return (atHorizontalEdge(agent) || atVerticalEdge(agent)) && (leavingEdge(agent) || super.accelerate(agent));
	}
	
	@Override
	protected void initializeVelocityVector(SwarmAgent agent, double[] componentsVectorVelocityInitial) {
		componentsVectorVelocityInitial[ThreadLocalRandom.current().nextInt(componentsVectorVelocityInitial.length)] = MAGNITUDE_COMPONENT_VECTOR_VELOCITY_MAXIMUM;
	}
}