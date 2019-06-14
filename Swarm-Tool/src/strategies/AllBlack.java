package strategies;

import java.awt.Color;

import cells.CellDisplayBase;
import cells.CellDisplayPolarity;
import cells.Cell;
import cells.CellDisplay;
import gui.Board;
import gui.GUI;
import other.SwarmAgent;
/*
 * Author: Zakary Gray
 * Description: Agent logic and layer 2 logic for an end goal of all layer 1 cells being black
 */
public class AllBlack extends AbstractStrategy{
	
	boolean adjustCellColor = false; //MODIFICATION #7

	//This method does nothing	
	@Override
	public CellDisplayPolarity Layer2(SwarmAgent agent, CellDisplayBase[][] layer1, int cellSize, int row, int col, Cell[] neighbor) {
		return null;
	}

	@Override
	public CellDisplayPolarity Layer2(CellDisplayBase[][] layer1, int cellSize, int row, int col, Cell[] neighbor) {
		CellDisplayPolarity layer2 = new CellDisplayPolarity(0,0,0,Color.RED);
		if(layer1[row][col].getColor() == Color.BLACK)
			//if the layer 1 cell is black
		{
			layer2 = new CellDisplayPolarity(row*cellSize, col*cellSize, cellSize, GUI.getPolarity1());
		}
		else
			//if the layer 1 cell is white
		{
			layer2 = new CellDisplayPolarity(row*cellSize, col*cellSize, cellSize, GUI.getPolarity2());
		}



		return layer2;
	}

	@Override
	public void logic(SwarmAgent agent, CellDisplayBase[][] layer1, CellDisplayPolarity[][] layer2, Cell[] neighbors, int cellSize) {

		if(layer1[(int)agent.getCenterX()/cellSize][(int)agent.getCenterY()/cellSize].getColor() == Color.BLACK)
		{
			adjustCellColor = false; //Leave the cell Black
		}
		else
		{
			layer1[(int)agent.getCenterX()/cellSize][(int)agent.getCenterY()/cellSize].flipColor();
			layer2[(int)agent.getCenterX()/cellSize][(int)agent.getCenterY()/cellSize] = Layer2(agent, layer1, cellSize, (int)agent.getCenterX()/cellSize, (int)agent.getCenterY()/cellSize, neighbors);
			adjustCellColor = true; //Change cell from white to black
		}
		
		//If the cell was flipped reset the layer 4 cell to white
				if(adjustCellColor) {
					Board.resetToWhite(agent, cellSize); //needs to be static????
				}
				//If the cell does not need changed, darken the purple
				else {
					Board.addPurple(agent, cellSize); 
				}

	}

}

