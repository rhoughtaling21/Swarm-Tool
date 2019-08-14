package strategies;

import java.awt.geom.Point2D;

import agents.SwarmAgent;
import cells.Cell;
import gui.Board;

public class StrategyPolarityAlternator extends StrategyPolarity {
	@Override
	public int determineDesiredPolarity(Board board, SwarmAgent agent) {
		int indexRow = board.calculateAgentRow(agent);
		int indexColumn = board.calculateAgentColumn(agent);
		
		Cell[][] layerPolarity = board.getPolarityLayer();
		Cell[] neighbors = board.getNeighbors(layerPolarity, indexRow, indexColumn);
		
		int indexStateNeighboring;
		Point2D velocity = agent.getVelocity();
		if(Math.abs(velocity.getX()) > Math.abs(velocity.getY())) {
			if(neighbors[3] != null && neighbors[5] != null && (indexStateNeighboring = neighbors[3].getState()) == neighbors[5].getState()) {
				return indexStateNeighboring;
			}
		}
		else {
			if(neighbors[1] != null && neighbors[7] != null && (indexStateNeighboring = neighbors[1].getState()) == neighbors[7].getState()) {
				return indexStateNeighboring;
			}
		}
		
		return layerPolarity[indexRow][indexColumn].getState();
	}
}