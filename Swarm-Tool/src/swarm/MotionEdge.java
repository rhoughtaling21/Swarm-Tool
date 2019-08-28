package swarm;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import gui.Board;

public class MotionEdge extends Motion {
	private static boolean atEdge(Board board, int indexLine) {
		return indexLine == 0 || indexLine == (board.getBreadth() - 1);
	}
	
	private static boolean atHorizontalEdge(SwarmAgent agent) {
		Board board = agent.getBoard();
		return atEdge(board, board.calculateAgentColumn(agent));
	}
	
	private static boolean atVerticalEdge(SwarmAgent agent) {
		Board board = agent.getBoard();
		return atEdge(board, board.calculateAgentRow(agent));
	}
	
	@Override
	protected void randomizeVelocityVector(SwarmAgent agent, double[] componentsVectorVelocity) {
		ArrayList<Integer> indicesAxes = new ArrayList<Integer>(2);
		
		if(atHorizontalEdge(agent)) {
			indicesAxes.add(0);
		}
		
		if(atVerticalEdge(agent)) {
			indicesAxes.add(1);
		}
		
		if(!indicesAxes.isEmpty()) {
			componentsVectorVelocity[indicesAxes.get(ThreadLocalRandom.current().nextInt(indicesAxes.size()))] = generateRandomVelocityVectorComponent();
		}
	}
	
	@Override
	protected boolean updateVelocity(SwarmAgent agent) {
		return !(atHorizontalEdge(agent) || atVerticalEdge(agent)) || super.updateVelocity(agent);
	}
	
	@Override
	protected void initializeVelocityVector(SwarmAgent agent, double[] componentsVectorVelocityInitial) {
		componentsVectorVelocityInitial[ThreadLocalRandom.current().nextInt(componentsVectorVelocityInitial.length)] = MAGNITUDE_COMPONENT_VECTOR_VELOCITY_MAXIMUM;
	}
}