package strategies;

import cells.Cell;
import cells.CellDisplayCorrectness;
import cells.CellDisplayPolarity;
import gui.Board;
import other.SwarmAgent;

//Goal strategies are children of AbstractStrategy
public abstract class Strategy {
	public abstract int getStateCount();
	public abstract int getPolarityCount();
	
	protected final void applyCellState(Board board, int indexRow, int indexColumn, int indexState) {
		Cell cellBase = board.getBaseLayer()[indexRow][indexColumn];
		
		if(cellBase.getState() == indexState) {
			board.getPersistenceLayer()[indexRow][indexColumn].shiftState();
		}
		else {
			cellBase.setState(indexState);
			updatePolarityCell(board, indexRow, indexColumn);
			board.getPersistenceLayer()[indexRow][indexColumn].reset();
		}
	}
	
	protected abstract int determineStatePolarity(Board board, int indexRow, int indexColumn);
	
	public final CellDisplayPolarity createPolarityCell(Board board, int indexRow, int indexColumn) {
		double sizeCell = board.getCellSize();
		return new CellDisplayPolarity((indexRow * sizeCell), (indexColumn * sizeCell), sizeCell, determineStatePolarity(board, indexRow, indexColumn), board);
	}
	
	public final void updatePolarityCell(Board board, int indexRow, int indexColumn) {
		Cell cellPolarity = board.getPolarityLayer()[indexRow][indexColumn];
		int indexPolarity = determineStatePolarity(board, indexRow, indexColumn);
		
		if(cellPolarity.getState() != indexPolarity) {
			cellPolarity.setState(indexPolarity);
			updateCorrectnessCell(board, indexRow, indexColumn);
		}
	}
	
	public int determinePolarityCorrectness(Board board, int indexRow, int indexColumn) {
		if(board.getPolarityLayer()[indexRow][indexColumn].getState() == board.getGui().getDominantPolarity()) {
			return 1;
		}
		
		return 0;
	}
	
	public final CellDisplayCorrectness createCorrectnessCell(Board board, int indexRow, int indexColumn) {
		double sizeCell = board.getCellSize();
		return new CellDisplayCorrectness((indexRow * sizeCell), (indexColumn * sizeCell), sizeCell, determinePolarityCorrectness(board, indexRow, indexColumn), board);
	}
	
	public final void updateCorrectnessCell(Board board, int indexRow, int indexColumn) {
		board.getCorrectnessLayer()[indexRow][indexColumn].setState(determinePolarityCorrectness(board, indexRow, indexColumn));
	}
	
	public abstract void logic(Board board, SwarmAgent agent);
}