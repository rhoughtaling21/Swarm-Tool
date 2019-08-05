package strategies;

import cells.CellDisplayBase;
import cells.Cell;
import cells.CellDisplay;
import gui.Board;
import other.SwarmAgent;

public class StrategyLines extends Strategy {
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
	public int determineStatePolarity(Board board, int indexRow, int indexColumn) {
		//Layer 2 in this sense shows 4 colors. one for each variation of straight lines. In the final, solved state,
		//the 4 colors indicate the 4 faces of the pyramid.
		CellDisplay[][] layerBase = board.getBaseLayer();

		Cell[] neighbors = Board.getNeighbors(layerBase, indexRow, indexColumn);
		int indexPolarityApparent = 0, countCornersBlack = 0, countEdgesBlack = 0, vertical = 0, horizontal = 0;
		for(int index = 0; index < neighbors.length; index++){
			if(neighbors[index] != null) {
				if(neighbors[index].getState() == 0) {
					if(index % 2 == 0) {
						countCornersBlack++;
					}
					else {
						countEdgesBlack++;
						
						if(index == 1 || index == 5) {
							vertical++;
						}
						else {
							horizontal++;
						}
					}
				}
			}
		}

		if(layerBase[indexRow][indexColumn].getState() == 0) {
			countEdgesBlack += 2;
		}
		
		if (countEdgesBlack > countCornersBlack) {
			if(vertical > horizontal) {
				indexPolarityApparent = 2; //Top and bottom rows white and middle row black
			}
			else {
				indexPolarityApparent = 0; //Left and right column white and middle column black
			}
		}
		else {
			if(vertical > horizontal) {
				indexPolarityApparent = 1; // Top and bottom rows black and middle row white
			}
			else {
				indexPolarityApparent = 3; //Left and right column black and middle column white
			}
		}
		
		//This section is necessary to allow for the alternating colors of layer 1 to be classified as the same polarity. 
		//For example, a black row then a white row then a black row are the same polarity, but appear opposite on a case-by-case basis
		if(layerBase[indexRow][indexColumn].getState() == 0) {
			if(indexPolarityApparent <= 1) {
				if(indexRow % 2 == 0) {
					return 0;
				}
				
				return 1;
			}
			
			if(indexColumn % 2 == 0) {
				return 3;
			}
			
			return 2;
		}
		
		if(indexPolarityApparent <= 1)	{
			if(indexRow % 2 == 0) {
				return 1;
			}

			return 0;
		}
		
		if(indexColumn % 2 == 0) {
			return 2;
		}

		return 3;
	}

	@Override
	//this is the exact same logic as the checkerboard, but with edgeCount and cornerCount flipped.
	public void logic(Board board, SwarmAgent agent) {
		int indexRow = board.calculateAgentRow(agent);
		int indexColumn = board.calculateAgentColumn(agent);
		
		CellDisplayBase[][] layerBase = board.getBaseLayer();
		CellDisplayBase cellBase = layerBase[indexRow][indexColumn];
		
		Cell[] neighbors = Board.getNeighbors(board.getBaseLayer(), indexRow, indexColumn);
		
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
				indexState = (indexState + 1) % COUNT_STATES;
			}
		}
		else if(countEdgesBlack > countCornersBlack) {
			if(indexState == 0) {
				indexState = (indexState + 1) % COUNT_STATES;
			}
		}
		else {
			if (Math.random() < .5) {
				indexState = (indexState + 1) % COUNT_STATES;
			}
		}

		applyCellState(board, indexRow, indexColumn, indexState);
	}
}
