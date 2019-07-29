package strategies;

import cells.CellDisplayCorrectness;
import cells.CellDisplayPolarity;
import gui.Board;
import other.SwarmAgent;

//Goal strategies are children of AbstractStrategy
public abstract class AbstractStrategy {
	public abstract int getStateCount();
	public abstract int getPolarityCount();
	
	protected void setCellState(Board board, int indexRow, int indexColumn, int indexState) {
		if(board.getBaseLayer()[indexRow][indexColumn].getState() == indexState) {
			board.getPersistenceLayer()[indexRow][indexColumn].shiftState();
		}
		else {
			board.getBaseLayer()[indexRow][indexColumn].setState(indexState);
			board.getPolarityLayer()[indexRow][indexColumn].setState(determinePolarity(board, indexRow, indexColumn));
		}
	}
	
	protected void setCellPolarity(Board board, int indexRow, int indexColumn, int indexPolarity) {
		if(board.getPolarityLayer()[indexRow][indexColumn].getState() == indexPolarity) {
			board.getPersistenceLayer()[indexRow][indexColumn].shiftState();
		}
		else {
			board.getPolarityLayer()[indexRow][indexColumn].setState(indexPolarity);
			board.getBaseLayer()[indexRow][indexColumn].setState(determineState(board, indexRow, indexColumn));
		}
	}
	
	protected int determineState(Board board, int indexRow, int indexColumn) {
		return 0;
	}
	
	protected final void updateBaseCell(Board board, int indexRow, int indexColumn) {
		board.getBaseLayer()[indexRow][indexColumn].setState(determineState(board, indexRow, indexColumn));
	}
	
	public abstract int determinePolarity(Board board, int indexRow, int indexColumn);
	
	public final CellDisplayPolarity createPolarityCell(Board board, int indexRow, int indexColumn) {
		double sizeCell = board.getCellSize();
		return new CellDisplayPolarity((indexRow * sizeCell), (indexColumn * sizeCell), sizeCell, determinePolarity(board, indexRow, indexColumn), board);
	}
	
	public final void updatePolarityCell(Board board, int indexRow, int indexColumn) {
		board.getPolarityLayer()[indexRow][indexColumn].setState(determinePolarity(board, indexRow, indexColumn));
	}
	
	public int determineCorrectness(Board board, int indexRow, int indexColumn) {
		if(board.getPolarityLayer()[indexRow][indexColumn].getState() == board.getGui().getDominantPolarity()) {
			return 1;
		}
		
		return 0;
	}
	
	public final CellDisplayCorrectness createCorrectnessCell(Board board, int indexRow, int indexColumn) {
		double sizeCell = board.getCellSize();
		return new CellDisplayCorrectness((indexRow * sizeCell), (indexColumn * sizeCell), sizeCell, determineCorrectness(board, indexRow, indexColumn), board);
	}
	
	public final void updateCorrectnessCell(Board board, int indexRow, int indexColumn) {
		board.getCorrectnessLayer()[indexRow][indexColumn].setState(determineCorrectness(board, indexRow, indexColumn));
	}
	
	public abstract void logic (Board board, SwarmAgent agent);
}