package strategies;

import cells.CellDisplayCorrectness;
import cells.CellDisplayPolarity;
import gui.Board;
import other.SwarmAgent;

//Goal strategies are children of AbstractStrategy
public abstract class AbstractStrategy {
	public abstract int getStateCount();
	public abstract int getPolarityCount();

	public final CellDisplayPolarity createPolarityCell(Board board, int indexRow, int indexColumn) {
		double sizeCell = board.getCellSize();
		return new CellDisplayPolarity((indexRow * sizeCell), (indexColumn * sizeCell), sizeCell, determinePolarity(board, indexRow, indexColumn), board);
	}
	
	public final void updatePolarityCell(Board board, int indexRow, int indexColumn) {
		board.getPolarityLayer()[indexRow][indexColumn].setState(determinePolarity(board, indexRow, indexColumn));
	}
	
	public abstract int determinePolarity(Board board, int indexRow, int indexColumn);
	
	public final CellDisplayCorrectness createCorrectnessCell(Board board, int indexRow, int indexColumn) {
		double sizeCell = board.getCellSize();
		return new CellDisplayCorrectness((indexRow * sizeCell), (indexColumn * sizeCell), sizeCell, determineCorrectness(board, indexRow, indexColumn), board);
	}
	
	public final void updateCorrectnessCell(Board board, int indexRow, int indexColumn) {
		board.getCorrectnessLayer()[indexRow][indexColumn].setState(determineCorrectness(board, indexRow, indexColumn));
	}
	
	public int determineCorrectness(Board board, int indexRow, int indexColumn) {
		if(board.getPolarityLayer()[indexRow][indexColumn].getState() == board.getGui().getDominantPolarity()) {
			return 1;
		}
		
		return 0;
	}
	
	public abstract void logic (Board board, SwarmAgent agent);
}