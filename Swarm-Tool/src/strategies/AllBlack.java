package strategies;

import java.awt.Color;

import cells.CellDisplayBase;
import cells.CellDisplayPolarity;
import cells.Cell;
import gui.Board;
import gui.GUI;
import other.SwarmAgent;
/*
 * Author: Zakary Gray
 * Description: Agent logic and layer 2 logic for an end goal of all layer 1 cells being black
 */
public class AllBlack extends AbstractStrategy {
	public static final int COUNT_POLARITIES = 2;
	private boolean adjustCellColor = false; //MODIFICATION #7

	@Override
	public int getCountPolarities() {
		return COUNT_POLARITIES;
	}

	@Override
	public Color determinePolarity(Board board, int row, int col) {
		GUI gui = board.getGui();

		if(board.getLayer(1)[row][col].getColor().equals(Color.BLACK)) { //if the layer 1 cell is black
			return gui.getPolarityColor(1);
		}

		//if the layer 1 cell is white
		return gui.getPolarityColor(2);
	}

	@Override
	public void logic(SwarmAgent agent, CellDisplayBase[][] layer1, CellDisplayPolarity[][] layer2, Cell[] neighbors, double cellSize) {
		if(layer1[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].getColor().equals(Color.BLACK)) {
			adjustCellColor = false; //Leave the cell Black
		}
		else {
			layer1[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].flipColor();
			updatePolarityCell(layer2[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].getBoard(), agent);
			adjustCellColor = true; //Change cell from white to black
		}

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

