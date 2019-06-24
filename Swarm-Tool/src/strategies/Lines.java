package strategies;

import java.awt.Color;

import cells.CellDisplayBase;
import cells.CellDisplayPolarity;
import cells.Cell;
import cells.CellDisplay;
import gui.Board;
import gui.GUI;
import other.SwarmAgent;

public class Lines extends AbstractStrategy {
	public static final int COUNT_POLARITIES = 4;
	/*
	 * Author: Zakary Gray
	 * Description: Agent logic and layer 2 logic for an end goal of all layer 1 cells forming straight lines of black and white. The stable state for this goal is a pyramid.
	 */
	//MODIFICATION #7
	boolean adjustCellColor = false;

	@Override
	public int getCountPolarities() {
		return COUNT_POLARITIES;
	}
	
	@Override
	public Color determinePolarity(Board board, int row, int col) {
		//Layer 2 in this sense shows 4 colors. one for each variation of straight lines. In the final, solved state,
		//the 4 colors indicate the 4 faces of the pyramid.
		GUI gui = board.getGui();
		CellDisplay[][] layer1 = board.getLayer(1);

		Cell[] neighbors = Board.getNeighbors((CellDisplayBase[][])layer1, row, col);
		int chosenPolarity = 0, cornerCount = 0, edgeCount = 0, vertical = 0, horizontal = 0;
		for(int index = 0; index < neighbors.length; index++){
			if(neighbors[index] != null) {
				if(index%2==0) {
					if (neighbors[index].getColor().equals(Color.BLACK)){
						cornerCount++;
					}
				}
				else {
					if (neighbors[index].getColor().equals(Color.BLACK)){
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

		if(layer1[row][col].getColor().equals(Color.BLACK)) {
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
		if(layer1[row][col].getColor().equals(Color.BLACK))	{
			if(chosenPolarity == 0) {
				if(row%2==0) {
					return gui.getPolarityColor(1);
				}

				return gui.getPolarityColor(2);
			}
			else if(chosenPolarity == 1) {
				if(row%2==0) {
					return gui.getPolarityColor(1);
				}

				return gui.getPolarityColor(2);
			}
			else if(chosenPolarity == 2) {
				if(col%2==0) {
					return gui.getPolarityColor(4);
				}

				return gui.getPolarityColor(3);
			}

			if(col%2==0) {
				return gui.getPolarityColor(4);
			}

			return gui.getPolarityColor(3);
		}
		if(chosenPolarity == 0)	{
			if(row%2==0) {
				return gui.getPolarityColor(2);
			}

			return gui.getPolarityColor(1);
		}
		else if(chosenPolarity == 1) {
			if(row%2==0) {
				return gui.getPolarityColor(2);
			}

			return gui.getPolarityColor(1);
		}
		else if(chosenPolarity == 2) {
			if(col%2==0) {
				return gui.getPolarityColor(3);
			}

			return gui.getPolarityColor(4);
		}
		if(col%2==0) {
			return gui.getPolarityColor(3);
		}

		return gui.getPolarityColor(4);
	}

	@Override
	//this is the exact same logic as the checkerboard, but with edgeCount and cornerCount flipped.
	public void logic(SwarmAgent agent, CellDisplayBase[][] layer1, CellDisplayPolarity[][] layer2, Cell[] neighbors, double cellSize) {
		int cornerCount = 0;
		int edgeCount = 0;
		//if (Math.random() < 0.1) {
		for(int index = 0; index<neighbors.length; index++) {
			if(neighbors[index] != null) {
				if(index%2==0) {
					if (neighbors[index].getColor().equals(Color.BLACK)) {
						edgeCount++;
					}
				}
				else {
					if (neighbors[index].getColor().equals(Color.BLACK)) {
						cornerCount++;
					}
				}
			}
		}
		//MODIFICATION #2: 99.6% follow rules and 0.3% flip no matter what.
		//Random rand = new Random();
		//int probabilityNum = rand.nextInt(300);
		//if(probabilityNum >0) {
		if(cornerCount>edgeCount) {
			if(layer1[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].getColor().equals(Color.BLACK)) {
				cornerCount = 0;
				edgeCount = 0;
				adjustCellColor = false; //Leave the cell Black
			}
			else {
				layer1[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].flipColor();
				updatePolarityCell(layer2[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].getBoard(), agent);
				cornerCount = 0;
				edgeCount = 0;
				adjustCellColor = true; //Flip the Cell from White to Black
			}
		}
		else if(edgeCount>cornerCount) {
			if(layer1[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].getColor().equals(Color.BLACK)) {
				layer1[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].flipColor();
				updatePolarityCell(layer2[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].getBoard(), agent);

				cornerCount = 0;
				edgeCount = 0;
				adjustCellColor = true; //Flip the Cell from Black to White
			}
			else {
				cornerCount = 0;
				edgeCount = 0;
				adjustCellColor = false; //Leave the Cell White
			}
		}
		else {
			double flipCoin = Math.random();
			if (flipCoin >.5) {
				layer1[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].flipColor();
				updatePolarityCell(layer2[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].getBoard(), agent);

				cornerCount = 0;
				edgeCount = 0;
				adjustCellColor = true; //Flip the Cell
			}
		}
		//}
		//else {
		//	layer1[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].flipColor();
		//	layer2[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)] = Layer2(layer1, cellSize, (int)agent.getCenterX()/cellSize, (int)agent.getCenterY()/cellSize, neighbors);
		//}

		//MODIFICATION #7:
		//If the cell was flipped reset the layer 4 cell to white
		if(adjustCellColor) {
			layer1[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].getBoard().resetToWhite(agent, cellSize);
		}
		//If the cell does not need changed, darken the purple
		else {
			layer1[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].getBoard().addPurple(agent, cellSize); 
		}

	}

}
