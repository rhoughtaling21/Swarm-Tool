package strategies;

import cells.CellDisplayBase;

import java.util.concurrent.ThreadLocalRandom;

import cells.Cell;
import gui.Board;		//MODIFICATION #7 
import other.SwarmAgent;

public class StrategyCheckerboard extends Strategy {
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
	public int determineStatePolarity(Board board, int row, int col) {
		if(board.getBaseLayer()[row][col].getState() == 1) {
			if(col % 2 == row % 2) {
				return 0;
			}
			
			return 1;
		}
		
		if(col % 2 == row % 2) {
			return 1;
		}
		
		return 0;
	}

	@Override
	public void logic(Board board, SwarmAgent agent) {
		int indexRow = board.calculateAgentRow(agent);
		int indexColumn = board.calculateAgentColumn(agent);
		
		int countCorners = 0;
		int countEdges = 0;
		CellDisplayBase[][] layerBase = board.getBaseLayer();
		Cell[] neighbors = Board.getNeighbors(layerBase, indexRow, indexColumn);
		for(int index = 0; index < neighbors.length; index++)	{
			if(neighbors[index] != null) {
				if(neighbors[index].getState() == 0) {
					if(index % 2 == 0) {
						countCorners++;
					}
					else {
						countEdges++;
					}
				}
			}
		}
		
		int indexState = board.getPolarityLayer()[indexRow][indexColumn].getState();
		
		//MODIFICATION #2: use if statement to lower probability of agents following the rigid rules
		if(ThreadLocalRandom.current().nextInt(300) > 0) {
			if(countCorners > countEdges) { //if more corners are black than edges, you should be black in the center
				indexState = 0;
			}
			else if(countEdges > countCorners) { //if more edges are black than corners, the center should be white
				indexState = 1;
			}
			else if(ThreadLocalRandom.current().nextDouble() < .5) { //and if tied, flip a coin
				indexState = flip(indexState);
			}
		}
		else {
			indexState = flip(indexState);
		}
		
		applyCellState(board, indexRow, indexColumn, indexState);
	}
	
	public int flip(int indexState) {
		if(indexState == 0) {
			return 1;
		}
		
		return 0;
	}
}