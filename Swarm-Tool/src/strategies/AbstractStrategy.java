package strategies;

import cells.CellDisplayPolarity;
import gui.Board;
import other.SwarmAgent;

//Goal strategies are children of AbstractStrategy
public abstract class AbstractStrategy {
	public abstract int getStateCount();
	public abstract int getPolarityCount();

	public final CellDisplayPolarity createPolarityCell(Board board, int row, int col) {
		double sizeCell = board.getCellSize();
		return new CellDisplayPolarity((row * sizeCell), (col * sizeCell), sizeCell, determinePolarity(board, row, col), board);
	}
	
	public final void updatePolarityCell(Board board, int row, int col) {
		board.getPolarityLayer()[row][col].setState(determinePolarity(board, row, col));
	}
	
	public abstract int determinePolarity(Board board, int row, int col);
	
	public abstract void logic (Board board, SwarmAgent agent);
}