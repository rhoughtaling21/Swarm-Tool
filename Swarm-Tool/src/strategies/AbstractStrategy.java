package strategies;

import cells.Cell;
import cells.GenericCell;

import other.SwarmAgent;
//Goal strategies are children of AbstractStrategy
public abstract class AbstractStrategy {

	public abstract Cell Layer2 (Cell[][] layer1, int cellSize, int row, int col, GenericCell[] neighbor);
	//MODIFICATION
	//new parameter, agent, is added to the method
	public abstract Cell Layer2 (SwarmAgent agent, Cell[][] layer1, int cellSize, int row, int col, GenericCell[] neighbor);
	public abstract void logic (SwarmAgent agent, Cell[][] layer1, Cell[][] layer2, GenericCell[] neighbors, Cell cell, int cellSize);
	
	//MODIFICATION #7:
	//By Morgan Might
	//July 5, 2018
	//This method will adjust the 4th layer
	//public abstract Cell Layer4 (SwarmAgent agent, Cell[][] layer1, int cellSize, int row, int col, GenericCell[] neighbor);
}

