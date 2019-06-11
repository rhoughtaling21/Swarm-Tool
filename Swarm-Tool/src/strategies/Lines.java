package strategies;

import java.awt.Color;

import cells.Cell;
import cells.GenericCell;
import gui.Board;
import gui.GUI;
import other.SwarmAgent;

public class Lines extends AbstractStrategy{
	/*
	 * Author: Zakary Gray
	 * Description: Agent logic and layer 2 logic for an end goal of all layer 1 cells forming straight lines of black and white. The stable state for this goal is a pyramid.
	 */
	
	//MODIFICATION #7
	boolean adjustCellColor = false;
	
	//This method does nothing
	@Override
	public Cell Layer2(SwarmAgent agent, Cell[][] layer1, int cellSize, int row, int col, GenericCell[] neighbor) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Cell Layer2(Cell[][] layer1, int cellSize, int row, int col, GenericCell[] neighbors) {
		//Layer 2 in this sense shows 4 colors. one for each variation of straight lines. In the final, solved state,
		//the 4 colors indicate the 4 faces of the pyramid.
		Cell layer2 = new Cell(0,0,0,Color.RED);
		int[] listOfPolarities = new int[4];
		int max = listOfPolarities[0], chosenPolarity = 0, cornerCount = 0, edgeCount = 0, vertical = 0, horizontal = 0;
		for(int index = 0; index<neighbors.length; index++)
		{
			if(neighbors[index] != null)
			{
				if(index%2==0)
				{
					if (neighbors[index].getColor() == Color.BLACK){
						cornerCount++;
					}
				}
				else
				{
					if (neighbors[index].getColor() == Color.BLACK){
						edgeCount++;
						if(index==1||index==5)
						{
							vertical++;
						}
						if(index==3||index==7)
						{
							horizontal++;
						}
					}
				}
			}
			else
			{

			}
		}
		if(layer1[row][col].getColor()==Color.BLACK)
		{
			edgeCount+=2;
		}
		if (edgeCount>cornerCount)
		{
			if(vertical>horizontal)
			{
				chosenPolarity = 2;//Top and bottom rows white and middle row black
			}
			else
			{
				chosenPolarity = 0;//Left and right column white and middle column black
			}
		}
		else
		{
			if(vertical>horizontal)
			{
				chosenPolarity = 1;// Top and bottom rows black and middle row white
			}
			else
			{
				chosenPolarity = 3;//Left and right column black and middle column white
			}
		}
		//This section is necessary to allow for the alternating colors of layer 1 to be classified as the same polarity. 
		//For example, a black row then a white row then a black row are the same polarity, but appear opposite on a case-by-case basis
		if(layer1[row][col].getColor()==Color.BLACK)
		{
			if(chosenPolarity == 0)
			{
				if(row%2==0)
				{
					layer2 = new Cell(row*cellSize, col*cellSize, cellSize, GUI.getPolarity1());
				}
				else
				{
					layer2 = new Cell(row*cellSize, col*cellSize, cellSize, GUI.getPolarity2());
				}
			}
			else if(chosenPolarity == 1)
			{
				if(row%2==0)
				{
					layer2 = new Cell(row*cellSize, col*cellSize, cellSize, GUI.getPolarity1());
				}
				else
				{
					layer2 = new Cell(row*cellSize, col*cellSize, cellSize, GUI.getPolarity2());
				}
			}
			else if(chosenPolarity == 2)
			{
				if(col%2==0)
				{
					layer2 = new Cell(row*cellSize, col*cellSize, cellSize, GUI.getPolarity4());
				}
				else
				{
					layer2 = new Cell(row*cellSize, col*cellSize, cellSize, GUI.getPolarity3());
				}
			}
			else
			{
				if(col%2==0)
				{
					layer2 = new Cell(row*cellSize, col*cellSize, cellSize, GUI.getPolarity4());
				}
				else
				{
					layer2 = new Cell(row*cellSize, col*cellSize, cellSize, GUI.getPolarity3());
				}
			}
		}
		else
		{
			if(chosenPolarity == 0)
			{
				if(row%2==0)
				{
					layer2 = new Cell(row*cellSize, col*cellSize, cellSize, GUI.getPolarity2());
				}
				else
				{
					layer2 = new Cell(row*cellSize, col*cellSize, cellSize, GUI.getPolarity1());
				}
			}
			else if(chosenPolarity == 1)
			{
				if(row%2==0)
				{
					layer2 = new Cell(row*cellSize, col*cellSize, cellSize, GUI.getPolarity2());
				}
				else
				{
					layer2 = new Cell(row*cellSize, col*cellSize, cellSize, GUI.getPolarity1());
				}
			}
			else if(chosenPolarity == 2)
			{
				if(col%2==0)
				{
					layer2 = new Cell(row*cellSize, col*cellSize, cellSize, GUI.getPolarity3());
				}
				else
				{
					layer2 = new Cell(row*cellSize, col*cellSize, cellSize, GUI.getPolarity4());
				}
			}
			else
			{
				if(col%2==0)
				{
					layer2 = new Cell(row*cellSize, col*cellSize, cellSize, GUI.getPolarity3());
				}
				else
				{
					layer2 = new Cell(row*cellSize, col*cellSize, cellSize, GUI.getPolarity4());
				}
			}
		}

		return layer2;
	}

	@Override
	//this is the exact same logic as the checkerboard, but with edgeCount and cornerCount flipped.
	public void logic(SwarmAgent agent, Cell[][] layer1, Cell[][] layer2, GenericCell[] neighbors, Cell cell, int cellSize) {
		int cornerCount = 0;
		int edgeCount = 0;
		//if (Math.random() < 0.1) {
		for(int index = 0; index<neighbors.length; index++)
		{
			if(neighbors[index] != null)
			{
				if(index%2==0)
				{
					if (neighbors[index].getColor() == Color.BLACK){
						edgeCount++;
					}
				}
				else
				{
					if (neighbors[index].getColor() == Color.BLACK){
						cornerCount++;
					}
				}
			}
			else
			{

			}
		}
		//MODIFICATION #2: 99.6% follow rules and 0.3% flip no matter what.
		//Random rand = new Random();
		//int probabilityNum = rand.nextInt(300);
		//if(probabilityNum >0) {
			if(cornerCount>edgeCount)
			{
				if(layer1[(int)agent.getCenterX()/cellSize][(int)agent.getCenterY()/cellSize].getColor() == Color.BLACK)
				{
					cornerCount = 0;
					edgeCount = 0;
					adjustCellColor = false; //Leave the cell Black
				}
				else
				{
					layer1[(int)agent.getCenterX()/cellSize][(int)agent.getCenterY()/cellSize].flipColor();
					layer2[(int)agent.getCenterX()/cellSize][(int)agent.getCenterY()/cellSize] = Layer2(agent, layer1, cellSize, (int)agent.getCenterX()/cellSize, (int)agent.getCenterY()/cellSize, neighbors);
					cornerCount = 0;
					edgeCount = 0;
					adjustCellColor = true; //Flip the Cell from White to Black
				}
			}
			else if(edgeCount>cornerCount)
			{
				if(layer1[(int)agent.getCenterX()/cellSize][(int)agent.getCenterY()/cellSize].getColor() == Color.BLACK)
				{
					layer1[(int)agent.getCenterX()/cellSize][(int)agent.getCenterY()/cellSize].flipColor();
					layer2[(int)agent.getCenterX()/cellSize][(int)agent.getCenterY()/cellSize] = Layer2(agent, layer1, cellSize, (int)agent.getCenterX()/cellSize, (int)agent.getCenterY()/cellSize, neighbors);

					cornerCount = 0;
					edgeCount = 0;
					adjustCellColor = true; //Flip the Cell from Black to White
				}
				else
				{
					cornerCount = 0;
					edgeCount = 0;
					adjustCellColor = false; //Leave the Cell White
				}
			}
			else
			{
				double flipCoin = Math.random();
				if (flipCoin >.5)
				{
					layer1[(int)agent.getCenterX()/cellSize][(int)agent.getCenterY()/cellSize].flipColor();
					layer2[(int)agent.getCenterX()/cellSize][(int)agent.getCenterY()/cellSize] = Layer2(agent, layer1, cellSize, (int)agent.getCenterX()/cellSize, (int)agent.getCenterY()/cellSize, neighbors);

					cornerCount = 0;
					edgeCount = 0;
					adjustCellColor = true; //Flip the Cell
				}
			}
		//}
		//else {
		//	layer1[(int)agent.getCenterX()/cellSize][(int)agent.getCenterY()/cellSize].flipColor();
		//	layer2[(int)agent.getCenterX()/cellSize][(int)agent.getCenterY()/cellSize] = Layer2(layer1, cellSize, (int)agent.getCenterX()/cellSize, (int)agent.getCenterY()/cellSize, neighbors);
		//}
		
			//MODIFICATION #7:
			//If the cell was flipped reset the layer 4 cell to white
			if(adjustCellColor) {
				Board.resetToWhite(agent, cellSize);
			}
			//If the cell does not need changed, darken the purple
			else {
				Board.addPurple(agent, cellSize); 
			}

	}

}
