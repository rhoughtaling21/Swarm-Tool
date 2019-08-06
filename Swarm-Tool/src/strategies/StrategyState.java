package strategies;

import agents.SwarmAgent;
import gui.Board;

public abstract class StrategyState extends Strategy {
	public abstract int determineDesiredState(Board board, SwarmAgent agent);
	
	@Override
	public void logic(Board board, SwarmAgent agent) {
		board.getActiveStrategy().applyCellState(board, board.calculateAgentRow(agent), board.calculateAgentColumn(agent), determineDesiredState(board, agent));
	}
}
