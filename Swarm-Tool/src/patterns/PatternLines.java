package patterns;

import cells.Cell;
import gui.Board;
import strategies.Strategy;
import strategies.StrategyStatePatternLines;

/*
 * Author: Zakary Gray
 * Description: Agent logic and layer 2 logic for an end goal of all layer 1 cells forming straight lines of black and white. The stable state for this goal is a pyramid.
 */
public class PatternLines extends Pattern {
	private static final int COUNT_STATES = 2;
	private static final int COUNT_POLARITIES = 4;
	private static final Strategy STRATEGY_DEFAULT = StrategyStatePatternLines.get();
	

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
		//Layer 2 in this sense shows 4 colors. one for each variation of straight lines. In the final, solved state,
		//the 4 colors indicate the 4 faces of the pyramid.
		Cell[][] layerBase = board.getBaseLayer();

		Cell[] neighbors = board.getNeighbors(layerBase, indexRow, indexColumn);
		int indexPolarityApparent = 0, countCornersBlack = 0, countEdgesBlack = 0, vertical = 0, horizontal = 0;
		for(int index = 0; index < neighbors.length; index++){
			if(neighbors[index] != null) {
				if(neighbors[index].getState() == 0) {
					if(index % 2 == 0) {
						countCornersBlack++;
					}
					else {
						countEdgesBlack++;
						
						if(index == 1 || index == 7) {
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
	
	/**
	 * I can't even begin to reverse-engineer the formula, so for now this'll just guess until it's found the correct state
	 */
	@Override
	protected int computePolarityState(Board board, int indexRow, int indexColumn) {
		Cell cell = board.getBaseLayer()[indexRow][indexColumn];
		int indexStateCurrent = cell.getState();
		
		int indexPolarity = board.getPolarityLayer()[indexRow][indexColumn].getState();
		
		int indexState;
		for(indexState = 0; indexState < COUNT_STATES - 1; indexState++) {
			cell.setState(indexState);
			if(computeStatePolarity(board, indexRow, indexColumn) == indexPolarity) {
				break;
			}
		}
		cell.setState(indexStateCurrent);
		return indexState;
	}
}