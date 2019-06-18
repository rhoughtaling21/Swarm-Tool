package strategies;

import cells.Cell;
import cells.CellDisplayBase;
import cells.CellDisplayPolarity;
import gui.Board;
import other.SwarmAgent;

//Goal strategies are children of AbstractStrategy
public abstract class AbstractStrategy {
	public abstract CellDisplayPolarity Layer2 (CellDisplayBase[][] layer1, double cellSize, int row, int col, Cell[] neighbor);
	//MODIFICATION
	//new parameter, agent, is added to the method
	public abstract CellDisplayPolarity Layer2 (SwarmAgent agent, CellDisplayBase[][] layer1, double cellSize, int row, int col, Cell[] neighbor);
	public abstract void logic (SwarmAgent agent, CellDisplayBase[][] layer1, CellDisplayPolarity[][] layer2, Cell[] neighbors, double cellSize);
	
	//MODIFICATION #7:
	//By Morgan Might
	//July 5, 2018
	//This method will adjust the 4th layer
	//public abstract Cell Layer4 (SwarmAgent agent, Cell[][] layer1, int cellSize, int row, int col, GenericCell[] neighbor);
}

