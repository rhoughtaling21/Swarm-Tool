package strategies;

import gui.Board;
import swarm.SwarmAgent;

public class StrategyStatePatternBlackout extends StrategyState {
	@Override
	public int determineDesiredState(Board board, SwarmAgent agent) {
		return 0;
	}
}
