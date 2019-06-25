package strategies;

import java.awt.Color;

import cells.CellDisplayBase;
import cells.CellDisplayPolarity;
import cells.Cell;
import cells.CellDisplay;
import gui.Board;
import gui.GUI;
import other.SwarmAgent;

public class DiagonalLines extends AbstractStrategy {
	public static final int COUNT_POLARITIES = 3;
	/*
	 * Author Morgan Might
	 * Created 5/24/2018
	 * This strategy creates diagonal lines that are made up of black, gray, and white Cells. New Rules
	 * are involved to keep track of polarities and to change the color of cell rather than just flip
	 * since there are now three colors involved.
	 */

	@Override
	public int getCountPolarities() {
		return COUNT_POLARITIES;
	}
	
	@Override
	public Color determinePolarity(Board board, int row, int col) {
		//Layer 2 in this sense shows 3 colors. one for each variation of diagonal lines. 
		
		GUI gui = board.getGui();
		CellDisplay[][] layer1 = board.getLayer(1);
		//If the Cell's color is Black. Based on its location determine polarity of the Cell
		if(layer1[row][col].getColor().equals(Color.BLACK)) {
			//Polarity
			if((row + col)%3 == 0) {
				return gui.getPolarityColor(1);
			}
			
			if((row + col)%3 == 1) {
				return gui.getPolarityColor(3);
			}
			
			return gui.getPolarityColor(2);
		}
		
		//If the Cell's color is Gray. Based on its location determine polarity of the Cell
		if(layer1[row][col].getColor().equals(Color.GRAY)) {
			//Polarity
			if((row + col)%3 == 0) {
				return gui.getPolarityColor(2);
			}
			
			if((row + col)%3 == 1) {
				return gui.getPolarityColor(1);
			}
			
			return gui.getPolarityColor(3);
		}
		
		//If the Cell's color is White. Based on its location determine polarity of the Cell
		//Polarity
		if((row + col)%3 == 0) {
			return gui.getPolarityColor(3);
		}
			
		if((row + col)%3 == 1) {
			return gui.getPolarityColor(2);
		}
			
		return gui.getPolarityColor(1);
	}
	
	@Override
	public CellDisplayPolarity createPolarityCell(Board board, SwarmAgent agent) {
//		//Layer 2 in this sense shows 3 colors. one for each variation of diagonal lines. 
//		CellDisplayPolarity layer2 = new CellDisplayPolarity(0, 0, 0, Color.RED, layer1[row][col].getBoard());
//		int polarityNum = 0;
//		
//		
//		//MODIFICATION FOR SET UP BUTTON 
//		//Will allow DiagonalLines to be the original goal
//		//Updated: 7/17/18
//		//By Morgan Might
//		//For displaying purposes:
//		//These if statements keep track of the counters of the polarities
//		if(layer1[row][col].getBoard().getGui().diagonalLineStart && layer1[row][col].getBoard().stepCount < 1) {
//			layer1[row][col].getBoard().getGui().setPolOneCount(layer1[row][col].getBoard().getGui().getPolOneCount());
//			layer1[row][col].getBoard().getGui().setPolTwoCount(layer1[row][col].getBoard().getGui().getPolTwoCount());
//			layer1[row][col].getBoard().getGui().setPolThreeCount(layer1[row][col].getBoard().getGui().getPolTwoCount());
//		}
//		else {
//			if(layer1[row][col].getPolarityColor() == layer1[row][col].getBoard().getGui().getPolarity1()) {
//				layer1[row][col].getBoard().getGui().setPolOneCount(layer1[row][col].getBoard().getGui().getPolOneCount() - 1);
//			}
//			else if(layer1[row][col].getPolarityColor() == layer1[row][col].getBoard().getGui().getPolarity2()) {
//				layer1[row][col].getBoard().getGui().setPolTwoCount(layer1[row][col].getBoard().getGui().getPolTwoCount() - 1);
//			}
//			else if(layer1[row][col].getPolarityColor() == layer1[row][col].getBoard().getGui().getPolarity3()) {
//				layer1[row][col].getBoard().getGui().setPolThreeCount(layer1[row][col].getBoard().getGui().getPolThreeCount() - 1);
//			}
//		}		
//		
//		//If the Cell's color is Black. Based on its location determine polarity of the Cell
//		if(layer1[row][col].getColor() == Color.BLACK) {
//			//Polarity
//			if((row + col)%3 ==0) {
//				layer2 = new CellDisplayPolarity(row*cellSize, col*cellSize, cellSize, layer1[row][col].getBoard().getGui().getPolarity1(), layer1[row][col].getBoard());
//				polarityNum = 1;
//			}
//			else if((row + col)%3 ==1) {
//				layer2 = new CellDisplayPolarity(row*cellSize, col*cellSize, cellSize, layer1[row][col].getBoard().getGui().getPolarity3(), layer1[row][col].getBoard());
//				polarityNum = 3;
//			}
//			else if((row + col)%3 ==2) {
//				layer2 = new CellDisplayPolarity(row*cellSize, col*cellSize, cellSize, layer1[row][col].getBoard().getGui().getPolarity2(), layer1[row][col].getBoard());
//				polarityNum = 2;
//			}
//			
//		}
//		//If the Cell's color is Gray. Based on its location determine polarity of the Cell
//		else if(layer1[row][col].getColor() == Color.GRAY) {
//			//Polarity
//			if((row + col)%3 ==0) {
//				layer2 = new CellDisplayPolarity(row*cellSize, col*cellSize, cellSize, layer1[row][col].getBoard().getGui().getPolarity2(), layer1[row][col].getBoard());
//				polarityNum = 2;
//			}
//			else if((row + col)%3 ==1) {
//				layer2 = new CellDisplayPolarity(row*cellSize, col*cellSize, cellSize, layer1[row][col].getBoard().getGui().getPolarity1(), layer1[row][col].getBoard());
//				polarityNum = 1;
//			}
//			else if((row + col)%3 ==2) {
//				layer2 = new CellDisplayPolarity(row*cellSize, col*cellSize, cellSize, layer1[row][col].getBoard().getGui().getPolarity3(), layer1[row][col].getBoard());
//				polarityNum = 3;
//			}
//		}
//		//If the Cell's color is White. Based on its location determine polarity of the Cell
//		else if(layer1[row][col].getColor() == Color.WHITE) {
//			//Polarity
//			if((row + col)%3 ==0) {
//				layer2 = new CellDisplayPolarity(row*cellSize, col*cellSize, cellSize, layer1[row][col].getBoard().getGui().getPolarity3(), layer1[row][col].getBoard());
//				polarityNum = 3;
//			}
//			else if((row + col)%3 ==1) {
//				layer2 = new CellDisplayPolarity(row*cellSize, col*cellSize, cellSize, layer1[row][col].getBoard().getGui().getPolarity2(), layer1[row][col].getBoard());
//				polarityNum = 2;
//			}
//			else if((row + col)%3 ==2) {
//				layer2 = new CellDisplayPolarity(row*cellSize, col*cellSize, cellSize, layer1[row][col].getBoard().getGui().getPolarity1(), layer1[row][col].getBoard());
//				polarityNum = 1;
//			}
//		}
//		//Update the polarity count, set the Cell's polarity color, and add the most recent Cell's polarity
//		//to the agent's array of recent polarities it has seen
//		if(polarityNum == 1) {
//			agent.setLayer2Array(layer1[row][col].getBoard().getGui().getPolarity1());
//			layer1[row][col].getBoard().getGui().setPolOneCount(layer1[row][col].getBoard().getGui().getPolOneCount() + 1);
//			layer1[row][col].setPolarityColor(layer1[row][col].getBoard().getGui().getPolarity1());
//		}
//		else if(polarityNum == 2) {
//			agent.setLayer2Array(Color.BLUE);
//			layer1[row][col].getBoard().getGui().setPolTwoCount(layer1[row][col].getBoard().getGui().getPolTwoCount() + 1);
//			layer1[row][col].setPolarityColor(layer1[row][col].getBoard().getGui().getPolarity2());
//		}
//		if(polarityNum == 3) {
//			agent.setLayer2Array(Color.YELLOW);
//			layer1[row][col].getBoard().getGui().setPolThreeCount(layer1[row][col].getBoard().getGui().getPolThreeCount() + 1);
//			layer1[row][col].setPolarityColor(layer1[row][col].getBoard().getGui().getPolarity3());
//		}
//		//Display the comparison's in the labels
//		layer1[row][col].getBoard().getGui().setLblComparisons();
//		return layer2;
		
		
		CellDisplayPolarity cellPolarity = super.createPolarityCell(board, agent);
		agent.setLayer2Array(cellPolarity.getColor());
		return cellPolarity;
	}
	
	public void updatePolarityCell(Board board, SwarmAgent agent) {
		double sizeCell = board.getCellSize();
		int indexRow = (int)(agent.getCenterX() / sizeCell);
		int indexColumn = (int)(agent.getCenterY() / sizeCell);
		Color colorPolarity = determinePolarity(board, indexRow, indexColumn);
		board.getLayer(2)[indexRow][indexColumn].setColor(colorPolarity);
		agent.setLayer2Array(colorPolarity);
	}
	
	//UPDATED: If there are an equal amount of red and blue cells there will be a coin flip. If there is more
	// red than yellow or more blue than yellow the first layer will change to create more yellow polarity.
	//
	//Added 6/13/2018 by Morgan Might
	@Override
	public void logic(SwarmAgent agent, CellDisplayBase[][] layer1, CellDisplayPolarity[][] layer2, Cell[] neighbors, double cellSize) {
		int totalCells = layer1[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].getBoard().numCellsOnSide*layer1[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].getBoard().numCellsOnSide;
		int row = (int)(agent.getCenterX() / cellSize);
		int col = (int)(agent.getCenterY() / cellSize);
		
		agent.printColorsInArray();
		
		//MODIFICATION #7:
		Color oldColor = layer1[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].getColor();
		Color newColor = Color.GREEN;
		
		
		//MODIFICATION #9:
		//added 7/10 by Morgan Might
		//Must get a count of the neighboring cells' polarity to grow certain color polarity
		Color newPolarityColor = Color.WHITE;
		Color oldPolarityColor = layer2[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].getColor();
	
	//	
	//COLLECT INFO	
	//
		int redPolCount = 0, bluePolCount = 0, yellowPolCount = 0; 			//These keep track of what the neighbors currently hold
		int redCrossCount = 0, blueCrossCount = 0, yellowCrossCount = 0;	//These keep track of how many of each color appear in the cell above, below, left and right
		int neighborCount = 0;  //Keeps track of how many cells surround the current cell
		boolean cellAbove = false, cellBelow = false, cellLeft = false, cellRight = false; //Keep track of cells around so you may tell which side its on, or what corner
		
		neighbors = Board.getNeighbors(layer2, row, col);
		
		for (int index = 0; index < neighbors.length; index++) {
			if (neighbors[index] != null) {
				neighborCount++;
				//Get Polarity of Neighbors MODIFICATION #9
				if(neighbors[index].getColor().equals(Color.RED)) {
					redPolCount++;
				}
				else if(neighbors[index].getColor().equals(Color.BLUE)) {
					bluePolCount++;
				}
				else if(neighbors[index].getColor().equals(Color.YELLOW)) {
					yellowPolCount++;
				}
				//Collect the Cross Counts
				if(index == 1 || index == 3 || index == 5 || index == 7) {
					if (neighbors[index].getColor().equals(Color.RED)) {
						redCrossCount++;
					}
					else if (neighbors[index].getColor().equals(Color.BLUE)) {
						blueCrossCount++;
					}
					else if (neighbors[index].getColor().equals(Color.YELLOW)) {
						yellowCrossCount++;
					}
				}
				//Which side is it on??
				if(index == 1) { cellAbove = true; }
				else if(index == 3) { cellRight = true; }
				else if(index == 5) { cellBelow = true; }
				else if(index == 7) { cellLeft = true; }
			}
		}
	//
	//DETERMINE NEW POLARITY	
	//
		//If the agent's memory array is not yet filled or the goal is a single polarity
		if(!agent.getColorArrayFilled() || layer1[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].getBoard().getGui().togglePolarity) {
			//RULE #1
			//If the cell is surrounded by one color
			if(redPolCount == neighborCount) {	//Surrounded by RED
				newPolarityColor = Color.RED;
			}
			else if(bluePolCount == neighborCount) {	//Surrounded by BLUE
				newPolarityColor = Color.BLUE;
			}
			else if(yellowPolCount == neighborCount) {	//Surrounded by YELLOW
				newPolarityColor = Color.YELLOW;
			}
			//Rule #2 (was 3)
			//If the ALL or ALL-BUT_ONE of the colors in the Cross Position are the same
			//RED in the cross area (consider the sides and corners)
			else if (redCrossCount >= 3 || (neighborCount == 5 && redCrossCount >= 2) || (neighborCount == 3 && redCrossCount == 2)) {
				newPolarityColor = Color.RED;
			}
			//BLUE in the cross area (consider the sides and corners)
			else if (blueCrossCount >= 3 || (neighborCount == 5 && blueCrossCount >= 2) || (neighborCount == 3 && blueCrossCount == 2)) {
				newPolarityColor = Color.BLUE;
			}
			//YELLOW in the cross area (consider the sides and corners)
			else if (yellowCrossCount >= 3 || (neighborCount == 5 && yellowCrossCount >= 2) || (neighborCount == 3 && yellowCrossCount == 2)) {
				newPolarityColor = Color.YELLOW;
			}
			//RULE #3 (was 2)
			//If the neighboring cells are ONLY 2 COLORS, match one of those colors or leave as is
			//If there is no RED, only yellow and blue
			else if (redPolCount == 0) { 
				if(oldPolarityColor.equals(Color.RED)) {
					//RANDOMLY choose YELLOW or BLUE
					if(Math.random() < 0.5) {
						newPolarityColor = Color.YELLOW;
					}
					else {
						newPolarityColor = Color.BLUE;
					}
				}
				else {
					newPolarityColor = oldPolarityColor;
				}
			}
			//If there is no BLUE, only yellow and red
			else if (bluePolCount == 0) { 
				if(oldPolarityColor.equals(Color.BLUE)) {
					//RANDOMLY choose YELLOW or RED
					double random = Math.random();
					if(random < 0.5) {
						newPolarityColor = Color.YELLOW;
					}
					else {
						newPolarityColor = Color.RED;
					}
				}
				else {
					newPolarityColor = oldPolarityColor;
				}
			}
			//If there is no YELLOW, only blue and red
			else if (yellowPolCount == 0) {
				if(oldPolarityColor.equals(Color.YELLOW)) {
					//RANDOMLY choose RED or BLUE
					double random = Math.random();
					if(random < 0.5) {
						newPolarityColor = Color.RED;
					}
					else {
						newPolarityColor = Color.BLUE;
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
				newPolarityColor = Color.RED;
			}
			else if(bluePolCount == neighborCount) {	//Surrounded by BLUE
				newPolarityColor = Color.BLUE;
			}
			else if(yellowPolCount == neighborCount) {	//Surrounded by YELLOW
				newPolarityColor = Color.YELLOW;
			}
			//Rule #2 (was 3)
			//If the ALL or All-BUT-ONE of the colors in the Cross Position are the same
			//RED in the cross area (consider the sides and corners)
			else if (redCrossCount >= 3 || (neighborCount == 5 && redCrossCount >= 2) || (neighborCount == 3 && redCrossCount == 2)) {
				newPolarityColor = Color.RED;
			}
			//BLUE in the cross area (consider the sides and corners)
			else if (blueCrossCount >= 3 || (neighborCount == 5 && blueCrossCount >= 2) || (neighborCount == 3 && blueCrossCount == 2)) {
				newPolarityColor = Color.BLUE;
			}
			//YELLOW in the cross area (consider the sides and corners)
			else if (yellowCrossCount >= 3 || (neighborCount == 5 && yellowCrossCount >= 2) || (neighborCount == 3 && yellowCrossCount == 2)) {
				newPolarityColor = Color.YELLOW;
			}
			
			//RULE #3 (was 2)
			//If the neighboring cells are ONLY 2 COLORS, match one of those colors or leave as is
			//If there is no RED, only yellow and blue
			else if (redPolCount == 0) { 
				if(oldPolarityColor.equals(Color.RED)) {
					//RANDOMLY choose YELLOW or BLUE
					if(Math.random() < 0.5) {
						newPolarityColor = Color.YELLOW;
					}
					else {
						newPolarityColor = Color.BLUE;
					}
				}
				else {
					newPolarityColor = oldPolarityColor;
				}
			}
			//If there is no BLUE, only yellow and red
			else if (bluePolCount == 0) { 
				if(oldPolarityColor == Color.BLUE) {
					//RANDOMLY choose YELLOW or RED
					if(Math.random() < 0.5) {
						newPolarityColor = Color.YELLOW;
					}
					else {
						newPolarityColor = Color.RED;
					}
				}
				else {
					newPolarityColor = oldPolarityColor;
				}
			}
			//If there is no YELLOW, only blue and red
			else if (yellowPolCount == 0) {
				if(oldPolarityColor.equals(Color.YELLOW)) {
					//RANDOMLY choose RED or BLUE
					if(Math.random() < 0.5) {
						newPolarityColor = Color.RED;
					}
					else {
						newPolarityColor = Color.BLUE;
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
				if(oldPolarityColor.equals(Color.YELLOW)) {
					//RANDOMLY choose RED or BLUE
					if(Math.random() < 0.5) {
						newPolarityColor = Color.RED;
					}
					else {
						newPolarityColor = Color.BLUE;
					}
				}
				else {
					newPolarityColor = oldPolarityColor;
				}
			}
			//2 YELLOW and 2 BLUE
			else if(yellowCrossCount == 2 && blueCrossCount == 2) {
				if(oldPolarityColor.equals(Color.RED)) {
					//RANDOMLY choose YELLOW or BLUE
					if(Math.random() < 0.5) {
						newPolarityColor = Color.YELLOW;
					}
					else {
						newPolarityColor = Color.BLUE;
					}
				}
				else {
					newPolarityColor = oldPolarityColor;
				}
			}
			//2 RED and 2 YELLOW
			else if(redCrossCount == 2 && yellowCrossCount == 2) {
				if(oldPolarityColor.equals(Color.BLUE)) {
					//RANDOMLY choose RED or YELLOW
					if(Math.random() < 0.5) {
						newPolarityColor = Color.RED;
					}
					else {
						newPolarityColor = Color.YELLOW;
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
				newPolarityColor = Color.RED;
			}
			//1 RED and 2 BLUE and 1 YELLOW in the cross
			else if(redCrossCount == 1 && blueCrossCount == 2 && yellowCrossCount == 1) {
				newPolarityColor = Color.BLUE;
			}
			//1 RED and 1 BLUE and 2 YELLOW in the cross
			else if(redCrossCount == 1 && blueCrossCount == 1 && yellowCrossCount == 2) {
				newPolarityColor = Color.YELLOW;
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
			if(agent.getRedCount() >= 18 || agent.getBlueCount() >= 18 || agent.getYellowCount() >= 18) {
				//YELLOW is the dominant polarity
				if(layer1[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].getBoard().getGui().newDominantPolarity == "YELLOW") {
					if (agent.getRedCount() > agent.getYellowCount()) {
						if(yellowPolCount >=2) {
							newPolarityColor = Color.YELLOW;
						}
					}
					else if (agent.getBlueCount() > agent.getYellowCount()) {
						if(yellowPolCount >=2) {
							newPolarityColor = Color.YELLOW;
						}
					}
					else if (agent.getRedCount() + agent.getBlueCount() < agent.getYellowCount()) {
						if(redPolCount >=2 && redPolCount > bluePolCount) {
							newPolarityColor = Color.RED;
						}
						else if(bluePolCount >=2 && redPolCount < bluePolCount) {
							newPolarityColor = Color.BLUE;
						}
						else if(redPolCount >=2 && redPolCount == bluePolCount) {
							//RANDOMLY choose RED or BLUE
							if(Math.random() < 0.5) {
								newPolarityColor = Color.RED;
							}
							else {
								newPolarityColor = Color.BLUE;
							}
						}
					}
				}
				//BLUE is the dominant polarity
				else if(layer1[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].getBoard().getGui().newDominantPolarity == "BLUE") {
					if (agent.getRedCount() > agent.getBlueCount()) {
						if(bluePolCount >=2) {
							newPolarityColor = Color.BLUE;
						}
					}
					else if (agent.getYellowCount() > agent.getBlueCount()) {
						if(bluePolCount >=2) {
							newPolarityColor = Color.BLUE;
						}
					}
					else if (agent.getRedCount() + agent.getYellowCount() < agent.getBlueCount()) {
						if(redPolCount >=2 && redPolCount > yellowPolCount) {
							newPolarityColor = Color.RED;
						}
						else if(yellowPolCount >=2 && redPolCount < yellowPolCount) {
							newPolarityColor = Color.YELLOW;
						}
						else if(redPolCount >=2 && redPolCount == yellowPolCount) {
							//RANDOMLY choose RED or YELLOW
							double random = Math.random();
							if(random < 0.5) {
								newPolarityColor = Color.RED;
							}
							else {
								newPolarityColor = Color.YELLOW;
							}
						}
					}
				}
				//RED is the dominant polarity
				else if(layer1[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].getBoard().getGui().newDominantPolarity == "RED") {
					if (agent.getBlueCount() > agent.getRedCount()) {
						if(redPolCount >=2) {
							newPolarityColor = Color.RED;
						}
					}
					else if (agent.getYellowCount() > agent.getRedCount()) {
						if(redPolCount >=2) {
							newPolarityColor = Color.RED;
						}
					}
					else if (agent.getYellowCount() + agent.getBlueCount() < agent.getRedCount()) {
						if(yellowPolCount >=2 && yellowPolCount > bluePolCount) {
							newPolarityColor = Color.YELLOW;
						}
						else if(bluePolCount >=2 && yellowPolCount < bluePolCount) {
							newPolarityColor = Color.BLUE;
						}
						else if(yellowPolCount >=2 && yellowPolCount == bluePolCount) {
							//RANDOMLY choose YELLOW or BLUE
							if(Math.random() < 0.5) {
								newPolarityColor = Color.YELLOW;
							}
							else {
								newPolarityColor = Color.BLUE;
							}
						}
					}
				}
				
			}
		}
		
	//DETERMINE NEW COLOR	
		//MODIFICATION #9 continued
		//Based on the new polarity color change the Cell color (Black, Gray, or White)
		if(newPolarityColor.equals(Color.RED)) {
			if((row+col)%3 == 0) {
				newColor = Color.BLACK;
			}
			else if((row+col)%3 == 1) {
				newColor = Color.GRAY;
			}
			else if((row+col)%3 == 2) {
				newColor = Color.WHITE;
			}
		}
		if(newPolarityColor.equals(Color.BLUE)) {
			if((row+col)%3 == 2) {
				newColor = Color.BLACK;
			}
			else if((row+col)%3 == 0) {
				newColor = Color.GRAY;
			}
			else if((row+col)%3 == 1) {
				newColor = Color.WHITE;
			}
		}
		if(newPolarityColor.equals(Color.YELLOW)) {
			if((row+col)%3 == 1) {
				newColor = Color.BLACK;
			}
			else if((row+col)%3 == 2) {
				newColor = Color.GRAY;
			}
			else if((row+col)%3 == 0) {
				newColor = Color.WHITE;
			}
		}
		
		
		
		//MODIFICATION #7: based on if the cell needed to flip, adjust the 4th layer
		//by Morgan Might 
		//July 5, 2018
		
		//MODIFICATION #9
		layer1[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].setColor(newColor);
		updatePolarityCell(layer2[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].getBoard(), agent);
		//If the cell was flipped reset the layer 4 cell to white
		if(newColor != oldColor) {
			layer1[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].getBoard().resetToWhite(agent, cellSize); //needs to be static????
		}
		//If the cell does not need changed, darken the purple
		else {
			layer1[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].getBoard().addPurple(agent, cellSize); 
		}
	}
	
		
}
