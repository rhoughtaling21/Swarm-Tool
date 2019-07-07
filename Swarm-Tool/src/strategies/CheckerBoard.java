package strategies;

import java.util.Random;

import cells.CellDisplayBase;
import cells.CellDisplayPolarity;
import cells.Cell;
import cells.CellDisplay;
import gui.Board;		//MODIFICATION #7 
import other.SwarmAgent;

public class CheckerBoard extends AbstractStrategy {
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
		if(board.getBaseLayer()[row][col].getState() == 1) {
			if(col%2 == row%2) {
				return 0;
			}
			
			return 1;
		}
		
		if(col%2 == row%2) {
			return 1;
		}
		
		return 0;
	}

	@Override
	public void logic(Board board, SwarmAgent agent) {
		int indexRow = board.getAgentRow(agent);
		int indexColumn = board.getAgentColumn(agent);
		
		int cornerCount = 0;
		int edgeCount = 0;
		CellDisplayBase[][] layer1 = board.getBaseLayer();
		Cell[] neighbors = Board.getNeighbors(layer1, indexRow, indexColumn);
		for(int index = 0; index < neighbors.length; index++)	{
			if(neighbors[index] != null) {
				if(index%2==0) {
					if (neighbors[index].getState() == 0){//if on the corner...
						cornerCount++;// cornerCount increases
					}
				}
				else if(neighbors[index].getState() == 0) {//if on the edge...
					edgeCount++;
				}
			}
		}
		
		boolean adjustCellColor = false;
		
		//MODIFICATION #2: use if statement to lower probability of agents following the rigid rules
		Random rand = new Random();
		int probabilityNum = rand.nextInt(300);
		if(probabilityNum > 0) {
			if(cornerCount>edgeCount) { //if more corners are black than edges, you should be black in the center
				if(layer1[indexRow][indexColumn].getState() == 0) {
					cornerCount = 0;
					edgeCount = 0;
				}
				else {
					layer1[indexRow][indexColumn].shiftState();
					updatePolarityCell(layer1[indexRow][indexColumn].getBoard(), indexRow, indexColumn);
					cornerCount = 0;
					edgeCount = 0;
					adjustCellColor = true; //Flip cell from white to black
				}
			}
			else if(edgeCount>cornerCount) { //if more edges are black than corners, the center should be white
				if(layer1[indexRow][indexColumn].getState() == 0) {
					layer1[indexRow][indexColumn].shiftState();
					updatePolarityCell(layer1[indexRow][indexColumn].getBoard(), indexRow, indexColumn);
					cornerCount = 0;
					edgeCount = 0;
					adjustCellColor = true; //Flip cell from black to white
				}
				else {
					cornerCount = 0;
					edgeCount = 0;
				}
			}
			else {
				// and if you are tied, like if all are black
				if (Math.random() < .5) { // flip a coin
					layer1[indexRow][indexColumn].shiftState();
					updatePolarityCell(layer1[indexRow][indexColumn].getBoard(), indexRow, indexColumn);
					cornerCount = 0;
					edgeCount = 0;
					adjustCellColor = true; //The cell will flip
				}
			}
		}
		else {
			layer1[indexRow][indexColumn].shiftState();
			updatePolarityCell(layer1[indexRow][indexColumn].getBoard(), indexRow, indexColumn);
			adjustCellColor = true; //The cell will flip
		}	
		
		//MODIFICATION #7: based on if the cell needed to flip, adjust the 4th layer
		//by Morgan Might 
		//July 5, 2018
		
		//If the cell was flipped reset the layer 4 cell to white
		if(adjustCellColor) {
			board.resetToWhite(agent); //needs to be static????
		}
		//If the cell does not need changed, darken the purple
		else {
			board.addPurple(agent); 
		}
	}
}