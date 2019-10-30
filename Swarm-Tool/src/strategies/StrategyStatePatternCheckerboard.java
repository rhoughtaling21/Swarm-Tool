package strategies;

import java.util.concurrent.ThreadLocalRandom;

import cells.Cell;
import cells.CellTalliedBase;
import gui.Board;
import swarm.SwarmAgent;

public class StrategyStatePatternCheckerboard extends StrategyState {
	private static final Strategy STRATEGY = new StrategyStatePatternCheckerboard();
	
	public static Strategy get() {
		return STRATEGY;
	}
	
	private StrategyStatePatternCheckerboard() {
		
	}
	
	@Override
	public int determineDesiredState(Board board, SwarmAgent agent) {
		int indexRow = board.calculateAgentRow(agent);
		int indexColumn = board.calculateAgentColumn(agent);
		
		int countCorners = 0;
		int countEdges = 0;
		CellTalliedBase[][] layerBase = board.getBaseLayer();
		Cell[] neighbors = board.getNeighbors(layerBase, indexRow, indexColumn);
		for(int index = 0; index < neighbors.length; index++)	{
			if(neighbors[index] != null) {
				if(neighbors[index].getState() == 0) {
					if(index % 2 == 0) {
						countCorners++;
					}
					else {
						countEdges++;
					}
				}
			}
		}
		
		int indexState = board.getPolarityLayer()[indexRow][indexColumn].getState();
		
		//MODIFICATION #2: use if statement to lower probability of agents following the rigid rules
		if(ThreadLocalRandom.current().nextInt(300) > 0) {
			if(countCorners > countEdges) { //if more corners are black than edges, you should be black in the center
				indexState = 0;
			}
			else if(countEdges > countCorners) { //if more edges are black than corners, the center should be white
				indexState = 1;
			}
			else if(ThreadLocalRandom.current().nextDouble() < .5) { //and if tied, flip a coin
				indexState = flip(indexState);
			}
		}
		else {
			indexState = flip(indexState);
		}
		
		return indexState;
	}

	private static int flip(int indexState) {
		if(indexState == 0) {
			return 1;
		}
		
		return 0;
	}
}
