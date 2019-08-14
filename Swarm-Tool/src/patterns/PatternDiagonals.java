package patterns;

import gui.Board;
import strategies.Strategy;
import strategies.StrategyPolarityPatternDiagonals;

/*
 * Author Morgan Might
 * Created 5/24/2018
 * This strategy creates diagonal lines that are made up of black, gray, and white Cells. New Rules
 * are involved to keep track of polarities and to change the color of cell rather than just flip
 * since there are now three colors involved.
 */
public class PatternDiagonals extends Pattern {
	private static final int COUNT_STATES = 3;
	private static final int COUNT_POLARITIES = 3;
	private static final Strategy STRATEGY_DEFAULT = new StrategyPolarityPatternDiagonals();
	private static final int[][] TABLE_STATES = {{0, 2, 1}, {2, 1, 0}, {1, 0, 2}};

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
		//Layer 2 in this sense shows 3 colors. one for each variation of diagonal lines. 

		return TABLE_STATES[board.getBaseLayer()[indexRow][indexColumn].getState()][(indexRow + indexColumn) % 3];
	}
	
	@Override
	public int computePolarityState(Board board, int indexRow, int indexColumn) {
		//Layer 2 in this sense shows 3 colors. one for each variation of diagonal lines. 

		return TABLE_STATES[board.getPolarityLayer()[indexRow][indexColumn].getState()][(indexRow + indexColumn) % 3];
	}
}