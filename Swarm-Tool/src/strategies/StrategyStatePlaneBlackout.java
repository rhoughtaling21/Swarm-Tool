package strategies;

import agents.SwarmAgent;
import gui.Board;

public class StrategyStatePlaneBlackout extends StrategyState {
	@Override
	public int determineDesiredState(Board board, SwarmAgent agent) {
		return 0;
	}
}
