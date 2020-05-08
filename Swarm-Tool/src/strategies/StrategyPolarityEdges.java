package strategies;

import cells.Cell;
import gui.Board;
import swarm.SwarmAgent;

public class StrategyPolarityEdges extends StrategyPolarity {
	static final int THRESHOLD_NEIGHBORING_POLARITY_DOMINANT = 2;
	
	private static final StrategyPolarityEdges STRATEGY = new StrategyPolarityEdges();
	
	public static StrategyPolarityEdges get() {
		return STRATEGY;
	}
	
	private StrategyPolarityEdges() {
		
	}
	
	@Override
	public int determineDesiredPolarity(Board board, SwarmAgent agent) {
		int indexRow = board.calculateAgentRow(agent);
		int indexColumn = board.calculateAgentColumn(agent);
		Cell[][] layerPolarity = board.getPolarityLayer();
		
		if(isEdge(board, indexRow, indexColumn)) {
			int indexPolarityDominant = board.getGui().getDominantPolarity();
			
			int countNeighboringPolarityDominant = 0;
			for(Cell neighbor : board.getNeighbors(layerPolarity, indexRow, indexColumn)) {
				if(neighbor != null && neighbor.getState() == indexPolarityDominant) {
					countNeighboringPolarityDominant++;
				}
			}
			
			if(countNeighboringPolarityDominant >= THRESHOLD_NEIGHBORING_POLARITY_DOMINANT) {
				return indexPolarityDominant;
			}
		}
		
		return layerPolarity[indexRow][indexColumn].getState();
	}

	private static boolean isEdge(Board board, int indexRow, int indexColumn) {
		if(indexRow == 0 || indexColumn == 0) {
			return true;
		}
		
		int indexVectorMaximum = board.getBreadth() - 1;
		return indexRow == indexVectorMaximum || indexColumn == indexVectorMaximum;
	}
}
