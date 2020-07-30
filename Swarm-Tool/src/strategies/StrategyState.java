package strategies;

import gui.Board;
import swarm.SwarmAgent;

public abstract class StrategyState extends Strategy {
	public abstract int determineDesiredState(Board board, SwarmAgent agent);
	
	@Override
	public void logic(Board board, SwarmAgent agent) {
		board.getPattern().applyCellState(board, board.calculateAgentRow(agent), board.calculateAgentColumn(agent), determineDesiredState(board, agent));
	}
}
