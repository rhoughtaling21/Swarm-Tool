package strategies;

import agents.SwarmAgent;
import gui.Board;

public abstract class StrategyPolarity extends Strategy {
	public abstract int determineDesiredPolarity(Board board, SwarmAgent agent);
	
	@Override
	public void logic(Board board, SwarmAgent agent) {
		board.getPattern().applyCellPolarity(board, board.calculateAgentRow(agent), board.calculateAgentColumn(agent), determineDesiredPolarity(board, agent));
	}
}
