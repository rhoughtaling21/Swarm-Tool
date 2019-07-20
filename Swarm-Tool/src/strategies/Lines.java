package strategies;

import cells.CellDisplayBase;
import cells.Cell;
import cells.CellDisplay;
import gui.Board;
import gui.GUI;
import other.SwarmAgent;

public class Lines extends AbstractStrategy {
	public static final int COUNT_STATES = 2;
	public static final int COUNT_POLARITIES = 4;
	/*
	 * Author: Zakary Gray
	 * Description: Agent logic and layer 2 logic for an end goal of all layer 1 cells forming straight lines of black and white. The stable state for this goal is a pyramid.
	 */

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
		//Layer 2 in this sense shows 4 colors. one for each variation of straight lines. In the final, solved state,
		//the 4 colors indicate the 4 faces of the pyramid.
		GUI gui = board.getGui();
		CellDisplay[][] layer1 = board.getBaseLayer();

		Cell[] neighbors = Board.getNeighbors((CellDisplayBase[][])layer1, row, col);
		int chosenPolarity = 0, cornerCount = 0, edgeCount = 0, vertical = 0, horizontal = 0;
		for(int index = 0; index < neighbors.length; index++){
			if(neighbors[index] != null) {
				if(index%2==0) {
					if (neighbors[index].getState() == 0) {
						cornerCount++;
					}
				}
				else {
					if (neighbors[index].getState() == 0){
						edgeCount++;
						if(index==1||index==5) {
							vertical++;
						}
						if(index==3||index==7) {
							horizontal++;
						}
					}
				}
			}
		}

		if(layer1[row][col].getState() == 0) {
			edgeCount+=2;
		}
		if (edgeCount>cornerCount) {
			if(vertical>horizontal) {
				chosenPolarity = 2;//Top and bottom rows white and middle row black
			}
			else {
				chosenPolarity = 0;//Left and right column white and middle column black
			}
		}
		else {
			if(vertical>horizontal) {
				chosenPolarity = 1;// Top and bottom rows black and middle row white
			}
			else {
				chosenPolarity = 3;//Left and right column black and middle column white
			}
		}
		//This section is necessary to allow for the alternating colors of layer 1 to be classified as the same polarity. 
		//For example, a black row then a white row then a black row are the same polarity, but appear opposite on a case-by-case basis
		if(layer1[row][col].getState() == 0)	{
			if(chosenPolarity == 0) {
				if(row%2==0) {
					return 0;
				}

				return 1;
			}
			else if(chosenPolarity == 1) {
				if(row%2==0) {
					return 0;
				}

				return 1;
			}
			else if(chosenPolarity == 2) {
				if(col%2==0) {
					return 3;
				}

				return 2;
			}

			if(col%2==0) {
				return 3;
			}

			return 2;
		}
		if(chosenPolarity == 0)	{
			if(row%2==0) {
				return 1;
			}

			return 0;
		}
		else if(chosenPolarity == 1) {
			if(row%2==0) {
				return 1;
			}

			return 0;
		}
		else if(chosenPolarity == 2) {
			if(col%2==0) {
				return 2;
			}

			return 3;
		}
		if(col%2==0) {
			return 2;
		}

		return 3;
	}

	@Override
	//this is the exact same logic as the checkerboard, but with edgeCount and cornerCount flipped.
	public void logic(Board board, SwarmAgent agent) {
		int indexRow = board.getAgentRow(agent);
		int indexColumn = board.getAgentColumn(agent);
		
		CellDisplayBase[][] layer1 = board.getBaseLayer();
		CellDisplayBase cell = layer1[indexRow][indexColumn];
		
		Cell[] neighbors = Board.getNeighbors(board.getBaseLayer(), indexRow, indexColumn);
		
		int cornerCount = 0;
		int edgeCount = 0;
		
		for(int index = 0; index<neighbors.length; index++) {
			if(neighbors[index] != null) {
				if(index%2==0) {
					if (neighbors[index].getState() == 0) {
						edgeCount++;
					}
				}
				else {
					if (neighbors[index].getState() == 0) {
						cornerCount++;
					}
				}
			}
		}
		
		boolean adjustCellColor = false;
		
		//MODIFICATION #2: 99.6% follow rules and 0.3% flip no matter what.
		//Random rand = new Random();
		//int probabilityNum = rand.nextInt(300);
		//if(probabilityNum >0) {
		if(cornerCount > edgeCount) {
			if(layer1[indexRow][indexColumn].getState() == 0) {
				cornerCount = 0;
				edgeCount = 0;
			}
			else {
				cell.shiftState();
				updatePolarityCell(board, indexRow, indexColumn);
				cornerCount = 0;
				edgeCount = 0;
				adjustCellColor = true; //Flip the Cell from White to Black
			}
		}
		else if(edgeCount > cornerCount) {
			if(cell.getState() == 0) {
				cell.shiftState();
				updatePolarityCell(board, indexRow, indexColumn);

				cornerCount = 0;
				edgeCount = 0;
				adjustCellColor = true; //Flip the Cell from Black to White
			}
			else {
				cornerCount = 0;
				edgeCount = 0;
			}
		}
		else {
			if (Math.random() > .5) {
				cell.shiftState();
				updatePolarityCell(board, indexRow, indexColumn);

				cornerCount = 0;
				edgeCount = 0;
				adjustCellColor = true; //Flip the Cell
			}
		}

		//MODIFICATION #7:
		//If the cell was flipped reset the layer 4 cell to white
		if(adjustCellColor) {
			board.resetToWhite(agent);
		}
		//If the cell does not need changed, darken the purple
		else {
			board.addPurple(agent); 
		}
	}
}
