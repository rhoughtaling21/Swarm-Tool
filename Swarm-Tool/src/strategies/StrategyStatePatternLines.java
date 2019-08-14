package strategies;

import agents.SwarmAgent;
import cells.Cell;
import cells.CellDisplayBase;
import gui.Board;

public class StrategyStatePatternLines extends StrategyState {
	@Override
	public int determineDesiredState(Board board, SwarmAgent agent) {
		int indexRow = board.calculateAgentRow(agent);
		int indexColumn = board.calculateAgentColumn(agent);
		
		int countStates = board.getPattern().getStateCount();
		
		CellDisplayBase[][] layerBase = board.getBaseLayer();
		CellDisplayBase cellBase = layerBase[indexRow][indexColumn];
		
		Cell[] neighbors = board.getNeighbors(board.getBaseLayer(), indexRow, indexColumn);
		
		int countCornersBlack = 0;
		int countEdgesBlack = 0;
		
		for(int index = 0; index < neighbors.length; index++) {
			if(neighbors[index] != null) {
				if(neighbors[index].getState() == 0) {
					if(index % 2 == 0) {
						countEdgesBlack++;
					}
					else {
						countCornersBlack++;
					}
				}
			}
		}
		
		//MODIFICATION #2: 99.6% follow rules and 0.3% flip no matter what.
		int indexState = cellBase.getState();
		if(countCornersBlack > countEdgesBlack) {
			if(indexState > 0) {
				indexState = (indexState + 1) % countStates;
			}
		}
		else if(countEdgesBlack > countCornersBlack) {
			if(indexState == 0) {
				indexState = (indexState + 1) % countStates;
			}
		}
		else {
			if (Math.random() < .5) {
				indexState = (indexState + 1) % countStates;
			}
		}

		return indexState;
	}
}
