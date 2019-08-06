package planes;

import gui.Board;
import strategies.Strategy;
import strategies.StrategyStatePlaneBlackout;
/*
 * Author: Zakary Gray
 * Description: Agent logic and layer 2 logic for an end goal of all layer 1 cells being black
 */
public class PlaneBlackout extends Plane {
	private static final int COUNT_STATES = 2;
	private static final int COUNT_POLARITIES = 2;
	private static final Strategy STRATEGY_DEFAULT = new StrategyStatePlaneBlackout();

	@Override
	public int getStateCount() {
		return COUNT_STATES;
	}
	
	@Override
	public int getPolarityCount() {
		return COUNT_POLARITIES;
	}
	
	public Strategy getDefaultStrategy() {
		return STRATEGY_DEFAULT;
	}
	
	@Override
	public int computeStatePolarity(Board board, int indexRow, int indexColumn) {
		if(board.getBaseLayer()[indexRow][indexColumn].getState() == 0) { //if the layer 1 cell is black
			return 0;
		}

		//if the layer 1 cell is white
		return 1;
	}
	
	@Override
	protected int computePolarityState(Board board, int indexRow, int indexColumn) {
		if(board.getPolarityLayer()[indexRow][indexColumn].getState() == 0) {
			return 0;
		}
		
		return 1;
	}
}