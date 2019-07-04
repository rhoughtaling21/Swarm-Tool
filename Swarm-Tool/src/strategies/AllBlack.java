package strategies;

import cells.CellDisplayBase;
import gui.Board;
import other.SwarmAgent;
/*
 * Author: Zakary Gray
 * Description: Agent logic and layer 2 logic for an end goal of all layer 1 cells being black
 */
public class AllBlack extends AbstractStrategy {
	private static final int COUNT_STATES = 2;
	private static final int COUNT_POLARITIES = 2;

	@Override
	public int getStateCount() {
		return COUNT_STATES;
	}
	
	@Override
	public int getPolarityCount() {
		return COUNT_POLARITIES;
	}

	@Override
	public int determinePolarity(Board board, int row, int col) {
		if(board.getBaseLayer()[row][col].getState() == 0) { //if the layer 1 cell is black
			return 0;
		}

		//if the layer 1 cell is white
		return 1;
	}

	@Override
	public void logic(Board board, SwarmAgent agent) {
		int indexRow = board.getAgentRow(agent);
		int indexColumn = board.getAgentColumn(agent);
		CellDisplayBase cell = board.getBaseLayer()[indexRow][indexColumn];
		
		if(cell.getState() == 0) {
			board.addPurple(agent); 
		}
		else {
			cell.shiftState();
			updatePolarityCell(board, indexRow, indexColumn);
			board.resetToWhite(agent); //needs to be static????
		}
	}

}

