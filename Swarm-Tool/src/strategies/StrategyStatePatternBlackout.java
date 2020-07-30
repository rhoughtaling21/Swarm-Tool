package strategies;

import gui.Board;
import swarm.SwarmAgent;

public class StrategyStatePatternBlackout extends StrategyState {
	private static final StrategyStatePatternBlackout STRATEGY = new StrategyStatePatternBlackout();
	
	public static StrategyStatePatternBlackout get() {
		return STRATEGY;
	}
	
	private StrategyStatePatternBlackout() {
		
	}
	
	@Override
	public int determineDesiredState(Board board, SwarmAgent agent) {
		return 0;
	}
}
