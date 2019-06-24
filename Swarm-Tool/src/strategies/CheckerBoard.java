package strategies;

import java.awt.Color;
import java.util.Random;

import cells.CellDisplayBase;
import cells.CellDisplayPolarity;
import cells.Cell;
import cells.CellDisplay;
import gui.Board;		//MODIFICATION #7 
import gui.GUI;
import other.SwarmAgent;

public class CheckerBoard extends AbstractStrategy {
	public static final int COUNT_POLARITIES = 2;
	//MODIFICATION #7: boolean keeps track of whether or not the cell needed flipped or changed
	//by Morgan Might
	//July 5, 2018
	boolean adjustCellColor = false;

	@Override
	public int getCountPolarities() {
		return COUNT_POLARITIES;
	}
	
	@Override
	public Color determinePolarity(Board board, int row, int col) {
		GUI gui = board.getGui();
		CellDisplay[][] layer1 = board.getLayer(1);
	
		if(layer1[row][col].getColor().equals(Color.WHITE)) {
			if(col%2 == row%2) {
				return gui.getPolarityColor(1);
			}
			
			return gui.getPolarityColor(2);
		}
		
		if(col%2 == row%2) {
			return gui.getPolarityColor(2);
		}
		
		return gui.getPolarityColor(1);
	}

	@Override
	public void logic(SwarmAgent agent, CellDisplayBase[][] layer1, CellDisplayPolarity[][] layer2, Cell[] neighbors, double cellSize) {
		int cornerCount = 0;
		int edgeCount = 0;
		
		for(int index = 0; index<neighbors.length; index++)	{
			if(neighbors[index] != null) {
				if(index%2==0) {
					if (neighbors[index].getColor().equals(Color.BLACK)){//if on the corner...
						cornerCount++;// cornerCount increases
					}
				}
				else {
					if (neighbors[index].getColor().equals(Color.BLACK)){//if on the edge...
						edgeCount++;//edgeCount in creases
					}
				}
			}
		}
		//MODIFICATION #2: use if statement to lower probability of agents following the rigid rules
		Random rand = new Random();
		int probabilityNum = rand.nextInt(300);
		if(probabilityNum >0) {
			//System.out.println("Good: " + probabilityNum);
			//goodCount++;
			if(cornerCount>edgeCount) { //if more corners are black than edges, you should be black in the center
				if(layer1[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].getColor().equals(Color.BLACK)) {
					cornerCount = 0;
					edgeCount = 0;
					adjustCellColor = false; //Leave the cell black
				}
				else {
					layer1[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].flipColor();
					layer2[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].flipColor();
					cornerCount = 0;
					edgeCount = 0;
					adjustCellColor = true; //Flip cell from white to black
				}
			}
			else if(edgeCount>cornerCount) { //if more edges are black than corners, the center should be white
				if(layer1[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].getColor().equals(Color.BLACK)) {
					layer1[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].flipColor();
					layer2[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].flipColor();
					cornerCount = 0;
					edgeCount = 0;
					adjustCellColor = true; //Flip cell from black to white
				}
				else {
					cornerCount = 0;
					edgeCount = 0;
					adjustCellColor = false; //Leave the cell white
				}
			}
			else {
				double flipCoin = Math.random();// and if you are tied, like if all are black
				if (flipCoin >.5) { // flip a coin
					layer1[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].flipColor();
					layer2[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].flipColor();
					cornerCount = 0;
					edgeCount = 0;
					adjustCellColor = true; //The cell will flip
				}
			}
		}
		else {
			layer1[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].flipColor();
			layer2[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].flipColor();
			adjustCellColor = true; //The cell will flip
		}	
		
		//MODIFICATION #7: based on if the cell needed to flip, adjust the 4th layer
		//by Morgan Might 
		//July 5, 2018
		
		//If the cell was flipped reset the layer 4 cell to white
		if(adjustCellColor) {
			layer1[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].getBoard().resetToWhite(agent, cellSize); //needs to be static????
		}
		//If the cell does not need changed, darken the purple
		else {
			layer1[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].getBoard().addPurple(agent, cellSize); 
		}
	}

}

