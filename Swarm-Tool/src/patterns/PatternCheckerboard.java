package patterns;

import gui.Board;
import strategies.Strategy;
import strategies.StrategyStatePatternCheckerboard;

public class PatternCheckerboard extends Pattern {
	private static final int COUNT_STATES = 2;
	private static final int COUNT_POLARITIES = 2;
	private static final Strategy STRATEGY_DEFAULT = StrategyStatePatternCheckerboard.get();

	@Override
	public int getStateCount() {
		return COUNT_STATES;
	}
	
	@Override
	public int getPolarityCount() {
		return COUNT_POLARITIES;
	}
	
	@Override
	public Strategy getDefaultStrategy() {
		return STRATEGY_DEFAULT;
	}
	
	@Override
	public int computeStatePolarity(Board board, int indexRow, int indexColumn) {
		boolean spaced = indexRow % 2 == indexColumn % 2;
		
		if(board.getBaseLayer()[indexRow][indexColumn].getState() == 0) {
			if(spaced) {
				return 1;
			}
			
			return 0;
		}
		
		if(spaced) {
			return 0;
		}
		
		return 1;
	}
	
	@Override
	protected int computePolarityState(Board board, int indexRow, int indexColumn) {
		boolean spaced = indexRow % 2 == indexColumn % 2;
		
		if(board.getPolarityLayer()[indexRow][indexColumn].getState() == 0) {
			if(spaced) {
				return 1;
			}
			
			return 0;
		}
		
		if(spaced) {
			return 0;
		}
		
		return 1;
	}
}