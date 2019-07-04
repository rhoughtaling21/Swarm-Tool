package strategies;

import java.awt.Color;

import cells.Cell;
import cells.CellDisplay;
import gui.Board;
import gui.GUI;
import other.SwarmAgent;

public class DiagonalLines extends AbstractStrategy {
	private static final int COUNT_STATES = 3;
	private static final int COUNT_POLARITIES = 3;
	/*
	 * Author Morgan Might
	 * Created 5/24/2018
	 * This strategy creates diagonal lines that are made up of black, gray, and white Cells. New Rules
	 * are involved to keep track of polarities and to change the color of cell rather than just flip
	 * since there are now three colors involved.
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
		//Layer 2 in this sense shows 3 colors. one for each variation of diagonal lines. 

		CellDisplay[][] layer1 = board.getBaseLayer();
		//If the Cell's color is Black. Based on its location determine polarity of the Cell
		if(layer1[row][col].getState() == 0) {
			//Polarity
			if((row + col)%3 == 0) {
				return 0;
			}

			if((row + col)%3 == 1) {
				return 2;
			}

			return 1;
		}

		//If the Cell's color is Gray. Based on its location determine polarity of the Cell
		if(layer1[row][col].getState() == 2) {
			//Polarity
			if((row + col)%3 == 0) {
				return 1;
			}

			if((row + col)%3 == 1) {
				return 0;
			}

			return 2;
		}

		//If the Cell's color is White. Based on its location determine polarity of the Cell
		//Polarity
		if((row + col)%3 == 0) {
			return 2;
		}

		if((row + col)%3 == 1) {
			return 1;
		}

		return 0;
	}

//	//UPDATED: If there are an equal amount of red and blue cells there will be a coin flip. If there is more
//	// red than yellow or more blue than yellow the first layer will change to create more yellow polarity.
//	//
//	//Added 6/13/2018 by Morgan Might
//	@Override
//	public void logic(Board board, SwarmAgent agent) {
//		int indexRow = board.getAgentRow(agent);
//		int indexColumn = board.getAgentColumn(agent);
//		Cell cellBase = board.getBaseLayer()[indexRow][indexColumn];
//		CellDisplay[][] layer2 = board.getPolarityLayer();
//
//		int indexStatePrevious = cellBase.getState();
//		int indexState = indexStatePrevious;
//
//		//MODIFICATION #9:
//		//added 7/10 by Morgan Might
//		//Must get a count of the neighboring cells' polarity to grow certain color polarity
//		int indexPolarityPrevious = layer2[indexRow][indexColumn].getState();
//		int indexPolarity = indexPolarityPrevious;
//
//		//	
//		//COLLECT INFO	
//		//
//		int[] countsPolaritiesNeighboring = new int[COUNT_POLARITIES]; //These keep track of what the neighbors currently hold
//		int[] countsPolaritiesNeighboringCross = new int[COUNT_POLARITIES];	//These keep track of how many of each color appear in the cell above, below, left and right
//		int countNeighbors = 0;  //Keeps track of how many cells surround the current cell
//		int countNeighborsCross = 0;
//
//		Cell[] neighbors = Board.getNeighbors(layer2, indexRow, indexColumn);
//		int polarityNeighbor;
//
//		for (int indexNeighbor = 0; indexNeighbor < neighbors.length; indexNeighbor++) {
//			if (neighbors[indexNeighbor] != null) {
//				countNeighbors++;
//
//				polarityNeighbor = neighbors[indexNeighbor].getState();
//				
//				//Get Polarity of Neighbors MODIFICATION #9
//				if(polarityNeighbor < countsPolaritiesNeighboring.length) {
//					countsPolaritiesNeighboring[polarityNeighbor]++;
//
//					//Collect the Cross Counts
//					if((indexNeighbor % 2) == 1) {
//						countNeighborsCross++;
//						countsPolaritiesNeighboringCross[polarityNeighbor]++;
//					}
//				}
//			}
//		}
//
//		if(agent.getAgentStatus()) {
//			System.out.println("--- Special Agent Behavior ---");
//			System.out.println("Polarity: " + indexPolarityPrevious);
//			System.out.println("Neighbors:: [Red]: " + countsPolaritiesNeighboring[0] + "  [Blue]: " + countsPolaritiesNeighboring[1] + "  [Yellow]: " + countsPolaritiesNeighboring[2]);
//			System.out.println("Cross:: [Red]: " + countsPolaritiesNeighboringCross[0] + "  [Blue]: " + countsPolaritiesNeighboringCross[1] + "  [Yellow]: " + countsPolaritiesNeighboringCross[2]);
//		}
//		
//		boolean searching = true;
//
//		//
//		//DETERMINE NEW POLARITY	
//		//
//		//RULE #1
//		//If the cell is surrounded by one color
//		for(int indexPolarityNeighboringCross = 0; indexPolarityNeighboringCross < countsPolaritiesNeighboringCross.length; indexPolarityNeighboringCross++) {
//			if(countsPolaritiesNeighboringCross[indexPolarityNeighboringCross] >= countNeighborsCross - 1) {
//				indexPolarity = indexPolarityNeighboringCross;
//				
//				if(agent.getAgentStatus()) {
//					System.out.println("Utilized RULE 1&2 (Cross Plurality: " + indexPolarityNeighboringCross + ")...");
//				}
//				
//				searching = false;
//				break;
//			}
//		}
//
//		if(searching) {
//			//RULE #3 (was 2)
//			//If the neighboring cells are ONLY 2 COLORS, match one of those colors or leave as is
//			for(int indexPolarityNeighboring = 0; indexPolarityNeighboring < countsPolaritiesNeighboring.length; indexPolarityNeighboring++) {
//				if(countsPolaritiesNeighboring[indexPolarityNeighboring] == 0) {
//					if(indexPolarityPrevious == indexPolarityNeighboring) {
//						indexPolarity = (indexPolarityPrevious + 1 + (int)((COUNT_POLARITIES - 1) * Math.random())) % COUNT_POLARITIES;
//					}
//					
//					if(agent.getAgentStatus()) {
//						System.out.println("Utilized RULE 3 (Missing Polarity: " + indexPolarityNeighboring + ")...");
//					}
//					
//					searching = false;
//					break;
//				}
//			}
//		}
//
//		//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//
//		//IF the agent array is full
//		//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//
//		if(agent.isMemoryFull()) {
//			if(agent.getAgentStatus()) {
//				System.out.println("AgentMemory::  [Red]: " + agent.getPolarityCount(0) + "  [Blue]: " + agent.getPolarityCount(1) + "  [Yellow]: " + agent.getPolarityCount(2));
//			}
//			
//			if(searching) {
//				//Rule #4
//				//If there are 2 colors in the cross section, make sure the center matches one of them
//				for(int indexPolarityNeighboringCross = 0; indexPolarityNeighboringCross < countsPolaritiesNeighboringCross.length; indexPolarityNeighboringCross++) {
//					if(countsPolaritiesNeighboringCross[indexPolarityNeighboringCross] == 0) {
//						if(indexPolarityPrevious == indexPolarityNeighboringCross) {
//							indexPolarity = (indexPolarityPrevious + 1 + (int)((COUNT_POLARITIES - 1) * Math.random())) % COUNT_POLARITIES;
//						}
//
//						if(agent.getAgentStatus()) {
//							System.out.println("Utilized RULE 4 (Missing Cross Polarity: " + indexPolarityNeighboringCross + ")...");
//						}
//						
//						searching = false;
//						break;
//					}
//				}
//
//				if(searching) {
//					//RULE #5
//					//If all 3 colors are in the cross section, make sure the center matches the majority
//					for(int indexPolarityNeighboringCross = 0; indexPolarityNeighboringCross < countsPolaritiesNeighboringCross.length; indexPolarityNeighboringCross++) {
//						if(countsPolaritiesNeighboringCross[indexPolarityNeighboringCross] >= 2) {
//							indexPolarity = indexPolarityNeighboringCross;
//							
//							if(agent.getAgentStatus()) {
//								System.out.println("Utilized RULE 5 (Cross Plurality: " + indexPolarityNeighboringCross + ")...");
//							}
//							
//							searching = false;
//							break;
//						}
//					}
//				}
//			}
//			
//			searching = false;
//			
//			//RULE #6
//			//Look at the agents array of memory
//			//If any one color is too apparent
//			//MODIFICATION #10
//			//Updated 7/18 by Morgan Might
//			//Now the constraints can be altered based on the dominant polarity chosen
//			for(int indexPolaritySeen = 0; indexPolaritySeen < COUNT_POLARITIES; indexPolaritySeen++) {
//				if(agent.getPolarityCount(indexPolaritySeen) >= 18) {
//					if(agent.getAgentStatus()) {
//						System.out.println("More than 18 of " + indexPolaritySeen + " in memory; searching anew...");
//					}
//					
//					searching = true;
//					break;
//				}
//			}
//
//			if(searching) {
//				int indexPolarityDominant = board.getGui().getDominantPolarity();
//				int countPolarityDominant = agent.getPolarityCount(indexPolarityDominant);
//				int countPolaritySeen;
//				int sumPolaritiesOther = 0;
//				
//				for(int indexPolaritySeen = 0; indexPolaritySeen < COUNT_POLARITIES; indexPolaritySeen++) {
//					if(indexPolaritySeen != indexPolarityDominant) {
//						if((countPolaritySeen = agent.getPolarityCount(indexPolaritySeen)) > countPolarityDominant) {
//							if(agent.getAgentStatus()) {
//								System.out.println("Utilized RULE 6 (I remember more " + indexPolaritySeen + " (" + countPolaritySeen + ") than " + indexPolarityDominant + " (" + countPolarityDominant + "))...");
//							}
//							
//							if(countsPolaritiesNeighboring[indexPolarityDominant] >= 2) {
//								indexPolarity = indexPolarityDominant;
//							}
//							else if(agent.getAgentStatus()) {
//								System.out.println("but there's not enough " + indexPolarityDominant + " around to warrant a change");
//							}
//
//							searching = false;
//							break;
//						}
//
//						sumPolaritiesOther += countPolaritySeen;
//					}
//				}
//
//				if(searching && (sumPolaritiesOther < countPolarityDominant)) {
//					int indexPolarityOtherTwo;
//					for(int indexPolarityOtherOne = 0; indexPolarityOtherOne < (COUNT_POLARITIES - 1); indexPolarityOtherOne++) {
//						if(indexPolarityOtherOne == indexPolarityDominant) {
//							indexPolarityOtherOne++;
//						}
//						
//						indexPolarityOtherTwo = (indexPolarityOtherOne + 1) % COUNT_POLARITIES;
//						if(indexPolarityOtherTwo == indexPolarityDominant) {
//							indexPolarityOtherTwo = (indexPolarityOtherTwo + 1) % COUNT_POLARITIES;
//						}
//
//						if((countsPolaritiesNeighboring[indexPolarityOtherOne] >= 2) && (countsPolaritiesNeighboring[indexPolarityOtherOne] > countsPolaritiesNeighboring[indexPolarityOtherTwo])) {
//							indexPolarity = indexPolarityOtherOne;
//
//							if(agent.getAgentStatus()) {
//								System.out.println("Utilized RULE 7 (I remember more " + indexPolarityDominant + " (" + countPolarityDominant + ") than everything else combined, and there's more " + indexPolarityOtherOne + " around...");
//							}
//							
//							searching = false;
//							break;
//						}
//					}
//
//					if((searching) && (countsPolaritiesNeighboring[(indexPolarityDominant + 1) % COUNT_POLARITIES] == countsPolaritiesNeighboring[(indexPolarityDominant + 2) % COUNT_POLARITIES])) {
//						indexPolarity = (indexPolarityDominant + 1 + (int)(2 * Math.random())) % COUNT_POLARITIES;
//						
//						if(agent.getAgentStatus()) {
//							System.out.println("Utilized RULE 7 (I remember more " + indexPolarityDominant + " (" + countPolarityDominant + ") than everything else combined, and there's no majority around...");
//						}
//					}
//				}
//			}
//		}
//
//		//DETERMINE NEW COLOR	
//		//MODIFICATION #9 continued
//		//Based on the new polarity color change the Cell color (Black, Gray, or White)
//		if(indexPolarity == 0) {
//			if((indexRow+indexColumn)%3 == 0) {
//				indexState = 0;
//			}
//			else if((indexRow+indexColumn)%3 == 1) {
//				indexState = 2;
//			}
//			else if((indexRow+indexColumn)%3 == 2) {
//				indexState = 1;
//			}
//		}
//		else if(indexPolarity == 1) {
//			if((indexRow+indexColumn)%3 == 2) {
//				indexState = 0;
//			}
//			else if((indexRow+indexColumn)%3 == 0) {
//				indexState = 2;
//			}
//			else {
//				indexState = 1;
//			}
//		}
//		else {
//			if((indexRow+indexColumn)%3 == 1) {
//				indexState = 0;
//			}
//			else if((indexRow+indexColumn)%3 == 2) {
//				indexState = 2;
//			}
//			else if((indexRow+indexColumn)%3 == 0) {
//				indexState = 1;
//			}
//		}
//
//		//MODIFICATION #7: based on if the cell needed to flip, adjust the 4th layer
//		//by Morgan Might 
//		//July 5, 2018
//		if(indexState == indexStatePrevious) {
//			//If the cell does not need changed, darken the purple
//			board.addPurple(agent);
//		}
//		else {
//			cellBase.setState(indexState);
//			updatePolarityCell(board, indexRow, indexColumn);
//			//If the cell was flipped reset the layer 4 cell to white
//			board.resetToWhite(agent);
//			
//			if(agent.getAgentStatus()) {
//				System.out.println("Polarity set to " + indexPolarity);
//			}
//		}
//		
//		if(agent.getAgentStatus()) {
//			System.out.println();
//		}
//		
//		agent.seePolarity(indexPolarity);
//	}
	
	//UPDATED: If there are an equal amount of red and blue cells there will be a coin flip. If there is more
		// red than yellow or more blue than yellow the first layer will change to create more yellow polarity.
		//
		//Added 6/13/2018 by Morgan Might
		@Override
		public void logic(Board board, SwarmAgent agent) {
			int totalCells = board.getTotalNumCells();
			int row = board.getAgentRow(agent);
			int col = board.getAgentColumn(agent);
			
			//MODIFICATION #7:
			int oldColor = board.getBaseLayer()[row][col].getState();
			int newColor = 4;
			
			//MODIFICATION #9:
			//added 7/10 by Morgan Might
			//Must get a count of the neighboring cells' polarity to grow certain color polarity
			int newPolarityColor = 4;
			int oldPolarityColor = board.getPolarityLayer()[row][col].getState();
		
		//	
		//COLLECT INFO	
		//
			int redPolCount = 0, bluePolCount = 0, yellowPolCount = 0; 			//These keep track of what the neighbors currently hold
			int redCrossCount = 0, blueCrossCount = 0, yellowCrossCount = 0;	//These keep track of how many of each color appear in the cell above, below, left and right
			int neighborCount = 0;  //Keeps track of how many cells surround the current cell
			Cell[] neighbors = board.getNeighbors(board.getPolarityLayer(), row, col);
			for (int index = 0; index < neighbors.length; index++) {
				if (neighbors[index] != null) {
					neighborCount++;
					//Get Polarity of Neighbors MODIFICATION #9
					if(neighbors[index].getState() == 0) {
						redPolCount++;
					}
					else if(neighbors[index].getState() == 1) {
						bluePolCount++;
					}
					else if(neighbors[index].getState() == 2) {
						yellowPolCount++;
					}
					//Collect the Cross Counts
					if(index == 1 || index == 3 || index == 5 || index == 7) {
						if (neighbors[index].getState() == 0) {
							redCrossCount++;
						}
						else if (neighbors[index].getState() == 1) {
							blueCrossCount++;
						}
						else if (neighbors[index].getState() == 2) {
							yellowCrossCount++;
						}
					}
				}
			}
		//
		//DETERMINE NEW POLARITY	
		//
			//If the agent's memory array is not yet filled or the goal is a single polarity
			if(!agent.isMemoryFull() || board.getGui().togglePolarity) {
				//RULE #1
				//If the cell is surrounded by one color
				if(redPolCount == neighborCount) {	//Surrounded by RED
					newPolarityColor = 0;
				}
				else if(bluePolCount == neighborCount) {	//Surrounded by BLUE
					newPolarityColor = 1;
				}
				else if(yellowPolCount == neighborCount) {	//Surrounded by YELLOW
					newPolarityColor = 2;
				}
				//Rule #2 (was 3)
				//If the ALL or ALL-BUT_ONE of the colors in the Cross Position are the same
				//RED in the cross area (consider the sides and corners)
				else if (redCrossCount >= 3 || (neighborCount == 5 && redCrossCount >= 2) || (neighborCount == 3 && redCrossCount == 2)) {
					newPolarityColor = 0;
				}
				//BLUE in the cross area (consider the sides and corners)
				else if (blueCrossCount >= 3 || (neighborCount == 5 && blueCrossCount >= 2) || (neighborCount == 3 && blueCrossCount == 2)) {
					newPolarityColor = 1;
				}
				//YELLOW in the cross area (consider the sides and corners)
				else if (yellowCrossCount >= 3 || (neighborCount == 5 && yellowCrossCount >= 2) || (neighborCount == 3 && yellowCrossCount == 2)) {
					newPolarityColor = 2;
				}
				//RULE #3 (was 2)
				//If the neighboring cells are ONLY 2 COLORS, match one of those colors or leave as is
				//If there is no RED, only yellow and blue
				else if (redPolCount == 0) { 
					if(oldPolarityColor == 0) {
						//RANDOMLY choose YELLOW or BLUE
						double random = Math.random();
						if(random < 0.5) {
							newPolarityColor = 1;
						}
						else {
							newPolarityColor = 2;
						}
					}
					else {
						newPolarityColor = oldPolarityColor;
					}
				}
				//If there is no BLUE, only yellow and red
				else if (bluePolCount == 0) { 
					if(oldPolarityColor == 1) {
						//RANDOMLY choose YELLOW or RED
						double random = Math.random();
						if(random < 0.5) {
							newPolarityColor = 2;
						}
						else {
							newPolarityColor = 0;
						}
					}
					else {
						newPolarityColor = oldPolarityColor;
					}
				}
				//If there is no YELLOW, only blue and red
				else if (yellowPolCount == 0) {
					if(oldPolarityColor == 2) {
						//RANDOMLY choose RED or BLUE
						double random = Math.random();
						if(random < 0.5) {
							newPolarityColor = 0;
						}
						else {
							newPolarityColor = 2;
						}
					}
					else {
						newPolarityColor = oldPolarityColor;
					}
				}
				//LAST RULE
				else {
					newPolarityColor = oldPolarityColor;
				}
			}
			//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//
			//IF the agent array is full
			//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//	//
			else {
				//RULE #1
				//If the cell is surrounded by one color
				if(redPolCount == neighborCount) {	//Surrounded by RED
					newPolarityColor = 0;
				}
				else if(bluePolCount == neighborCount) {	//Surrounded by BLUE
					newPolarityColor = 1;
				}
				else if(yellowPolCount == neighborCount) {	//Surrounded by YELLOW
					newPolarityColor = 2;
				}
				//Rule #2 (was 3)
				//If the ALL or All-BUT-ONE of the colors in the Cross Position are the same
				//RED in the cross area (consider the sides and corners)
				else if (redCrossCount >= 3 || (neighborCount == 5 && redCrossCount >= 2) || (neighborCount == 3 && redCrossCount == 2)) {
					newPolarityColor = 0;
				}
				//BLUE in the cross area (consider the sides and corners)
				else if (blueCrossCount >= 3 || (neighborCount == 5 && blueCrossCount >= 2) || (neighborCount == 3 && blueCrossCount == 2)) {
					newPolarityColor = 1;
				}
				//YELLOW in the cross area (consider the sides and corners)
				else if (yellowCrossCount >= 3 || (neighborCount == 5 && yellowCrossCount >= 2) || (neighborCount == 3 && yellowCrossCount == 2)) {
					newPolarityColor = 2;
				}
				
				//RULE #3 (was 2)
				//If the neighboring cells are ONLY 2 COLORS, match one of those colors or leave as is
				//If there is no RED, only yellow and blue
				else if (redPolCount == 0) { 
					if(oldPolarityColor == 0) {
						//RANDOMLY choose YELLOW or BLUE
						double random = Math.random();
						if(random < 0.5) {
							newPolarityColor = 2;
						}
						else {
							newPolarityColor = 1;
						}
					}
					else {
						newPolarityColor = oldPolarityColor;
					}
				}
				//If there is no BLUE, only yellow and red
				else if (bluePolCount == 0) { 
					if(oldPolarityColor == 1) {
						//RANDOMLY choose YELLOW or RED
						double random = Math.random();
						if(random < 0.5) {
							newPolarityColor = 2;
						}
						else {
							newPolarityColor = 0;
						}
					}
					else {
						newPolarityColor = oldPolarityColor;
					}
				}
				//If there is no YELLOW, only blue and red
				else if (yellowPolCount == 0) {
					if(oldPolarityColor == 2) {
						//RANDOMLY choose RED or BLUE
						double random = Math.random();
						if(random < 0.5) {
							newPolarityColor = 0;
						}
						else {
							newPolarityColor = 1;
						}
					}
					else {
						newPolarityColor = oldPolarityColor;
					}
				}
				
				//Rule #4
				//If there are 2 colors in the cross section, make sure the center matches one of them
				//2 RED and 2 BLUE
				else if(redCrossCount == 2 && blueCrossCount == 2) {
					if(oldPolarityColor == 2) {
						//RANDOMLY choose RED or BLUE
						double random = Math.random();
						if(random < 0.5) {
							newPolarityColor = 0;
						}
						else {
							newPolarityColor = 1;
						}
					}
					else {
						newPolarityColor = oldPolarityColor;
					}
				}
				//2 YELLOW and 2 BLUE
				else if(yellowCrossCount == 2 && blueCrossCount == 2) {
					if(oldPolarityColor == 0) {
						//RANDOMLY choose YELLOW or BLUE
						double random = Math.random();
						if(random < 0.5) {
							newPolarityColor = 2;
						}
						else {
							newPolarityColor = 1;
						}
					}
					else {
						newPolarityColor = oldPolarityColor;
					}
				}
				//2 RED and 2 YELLOW
				else if(redCrossCount == 2 && yellowCrossCount == 2) {
					if(oldPolarityColor == 1) {
						//RANDOMLY choose RED or YELLOW
						double random = Math.random();
						if(random < 0.5) {
							newPolarityColor = 0;
						}
						else {
							newPolarityColor = 2;
						}
					}
					else {
						newPolarityColor = oldPolarityColor;
					}
				}
				//RULE #5
				//If all 3 colors are in the cross section, make sure the center matches the majority
				//2 RED and 1 BLUE and 1 YELLOW in the cross
				else if(redCrossCount == 2 && blueCrossCount == 1 && yellowCrossCount == 1) {
					newPolarityColor = 0;
				}
				//1 RED and 2 BLUE and 1 YELLOW in the cross
				else if(redCrossCount == 1 && blueCrossCount == 2 && yellowCrossCount == 1) {
					newPolarityColor = 1;
				}
				//1 RED and 1 BLUE and 2 YELLOW in the cross
				else if(redCrossCount == 1 && blueCrossCount == 1 && yellowCrossCount == 2) {
					newPolarityColor = 2;
				}
				//This should prevent the white cells
				else {
					newPolarityColor = oldPolarityColor;
				}
				//RULE #6
				//Look at the agents array of memory
				//If any one color is too apparent
			//MODIFICATION #10
				//Updated 7/18 by Morgan Might
				//Now the constraints can be altered based on the dominant polarity chosen
				if(agent.getPolarityCount(0) >= 18 || agent.getPolarityCount(1) >= 18 || agent.getPolarityCount(2) >= 18) {
					//YELLOW is the dominant polarity
					if(board.getGui().getDominantPolarity() == 2) {
						if (agent.getPolarityCount(0) > agent.getPolarityCount(2)) {
							if(yellowPolCount >=2) {
								newPolarityColor = 2;
							}
						}
						else if (agent.getPolarityCount(1) > agent.getPolarityCount(2)) {
							if(yellowPolCount >=2) {
								newPolarityColor = 2;
							}
						}
						else if (agent.getPolarityCount(0) + agent.getPolarityCount(1) < agent.getPolarityCount(2)) {
							if(redPolCount >=2 && redPolCount > bluePolCount) {
								newPolarityColor = 0;
							}
							else if(bluePolCount >=2 && redPolCount < bluePolCount) {
								newPolarityColor = 1;
							}
							else if(redPolCount >=2 && redPolCount == bluePolCount) {
								//RANDOMLY choose RED or BLUE
								double random = Math.random();
								if(random < 0.5) {
									newPolarityColor = 0;
								}
								else {
									newPolarityColor = 1;
								}
							}
						}
					}
					//BLUE is the dominant polarity
					else if(board.getGui().getDominantPolarity() == 1) {
						if (agent.getPolarityCount(0) > agent.getPolarityCount(1)) {
							if(bluePolCount >=2) {
								newPolarityColor = 1;
							}
						}
						else if (agent.getPolarityCount(2) > agent.getPolarityCount(1)) {
							if(bluePolCount >=2) {
								newPolarityColor = 1;
							}
						}
						else if (agent.getPolarityCount(0) + agent.getPolarityCount(2) < agent.getPolarityCount(1)) {
							if(redPolCount >=2 && redPolCount > yellowPolCount) {
								newPolarityColor = 0;
							}
							else if(yellowPolCount >=2 && redPolCount < yellowPolCount) {
								newPolarityColor = 2;
							}
							else if(redPolCount >=2 && redPolCount == yellowPolCount) {
								//RANDOMLY choose RED or YELLOW
								double random = Math.random();
								if(random < 0.5) {
									newPolarityColor = 0;
								}
								else {
									newPolarityColor = 2;
								}
							}
						}
					}
					//RED is the dominant polarity
					else if(board.getGui().getDominantPolarity() == 0) {
						if (agent.getPolarityCount(1) > agent.getPolarityCount(0)) {
							if(redPolCount >=2) {
								newPolarityColor = 0;
							}
						}
						else if (agent.getPolarityCount(2) > agent.getPolarityCount(0)) {
							if(redPolCount >=2) {
								newPolarityColor = 0;
							}
						}
						else if (agent.getPolarityCount(2) + agent.getPolarityCount(1) < agent.getPolarityCount(0)) {
							if(yellowPolCount >=2 && yellowPolCount > bluePolCount) {
								newPolarityColor = 2;
							}
							else if(bluePolCount >=2 && yellowPolCount < bluePolCount) {
								newPolarityColor = 1;
							}
							else if(yellowPolCount >=2 && yellowPolCount == bluePolCount) {
								//RANDOMLY choose YELLOW or BLUE
								double random = Math.random();
								if(random < 0.5) {
									newPolarityColor = 2;
								}
								else {
									newPolarityColor = 1;
								}
							}
						}
					}
					
				}
			}
			
		//DETERMINE NEW COLOR	
			//MODIFICATION #9 continued
			//Based on the new polarity color change the Cell color (Black, Gray, or White)
			if(newPolarityColor == 0) {
				if((row+col)%3 == 0) {
					newColor = 0;
				}
				else if((row+col)%3 == 1) {
					newColor = 2;
				}
				else if((row+col)%3 == 2) {
					newColor = 1;
				}
			}
			if(newPolarityColor == 1) {
				if((row+col)%3 == 2) {
					newColor = 0;
				}
				else if((row+col)%3 == 0) {
					newColor = 2;
				}
				else if((row+col)%3 == 1) {
					newColor = 1;
				}
			}
			if(newPolarityColor == 2) {
				if((row+col)%3 == 1) {
					newColor = 0;
				}
				else if((row+col)%3 == 2) {
					newColor = 2;
				}
				else if((row+col)%3 == 0) {
					newColor = 1;
				}
			}
			
			//MODIFICATION #7: based on if the cell needed to flip, adjust the 4th layer
			//by Morgan Might 
			//July 5, 2018
			
			//MODIFICATION #9
			board.getBaseLayer()[row][col].setState(newColor);
			updatePolarityCell(board, row, col);
			agent.seePolarity(newPolarityColor);
			//If the cell was flipped reset the layer 4 cell to white
			if(newColor != oldColor) {
				board.resetToWhite(agent); //needs to be static????
			}
			//If the cell does not need changed, darken the purple
			else {
				board.addPurple(agent); 
			}
		}
}