package strategies;

import cells.Cell;
import cells.CellDisplayBase;
import cells.CellDisplayPolarity;
import gui.Board;
import other.SwarmAgent;

//Goal strategies are children of AbstractStrategy
public abstract class AbstractStrategy {
	public abstract int getStateCount();
	public abstract int getPolarityCount();
	
	public CellDisplayPolarity createPolarityCell(Board board, SwarmAgent agent) {
		double sizeCell = board.getCellSize();
		return createPolarityCell(board, (int)(agent.getCenterX() / sizeCell), (int)(agent.getCenterY() / sizeCell));
	}
	
	public final CellDisplayPolarity createPolarityCell(Board board, int row, int col) {
		double sizeCell = board.getCellSize();
		return new CellDisplayPolarity((row * sizeCell), (col * sizeCell), sizeCell, determinePolarity(board, row, col), board);
	}
	
	public void updatePolarityCell(Board board, SwarmAgent agent) {
		double sizeCell = board.getCellSize();
		updatePolarityCell(board, (int)(agent.getCenterX() / sizeCell), (int)(agent.getCenterY() / sizeCell));
	}
	
	public final void updatePolarityCell(Board board, int row, int col) {
		board.getLayer(2)[row][col].setState(determinePolarity(board, row, col));
	}
	
	public abstract int determinePolarity(Board board, int row, int col);
	
	public abstract void logic (SwarmAgent agent, CellDisplayBase[][] layer1, CellDisplayPolarity[][] layer2, Cell[] neighbors, double cellSize);
}