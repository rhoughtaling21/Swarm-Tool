package patterns;

import cells.Cell;
import cells.CellDisplayCorrectness;
import cells.CellDisplayPolarity;
import gui.Board;
import strategies.Strategy;

//Goal strategies are children of AbstractStrategy
public abstract class Pattern {
	public abstract int getStateCount();
	public abstract int getPolarityCount();
	
	public abstract Strategy getDefaultStrategy();
	
	public final void applyCellState(Board board, int indexRow, int indexColumn, int indexState) {
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
	
	protected abstract int computeStatePolarity(Board board, int indexRow, int indexColumn);
	
	public final CellDisplayPolarity createPolarityCell(Board board, int indexRow, int indexColumn) {
		double sizeCell = board.getCellSize();
		return new CellDisplayPolarity((indexRow * sizeCell), (indexColumn * sizeCell), sizeCell, computeStatePolarity(board, indexRow, indexColumn), board);
	}
	
	public final void updatePolarityCell(Board board, int indexRow, int indexColumn) {
		Cell cellPolarity = board.getPolarityLayer()[indexRow][indexColumn];
		int indexPolarity = computeStatePolarity(board, indexRow, indexColumn);
		
		if(cellPolarity.getState() != indexPolarity) {
			cellPolarity.setState(indexPolarity);
			updateCorrectnessCell(board, indexRow, indexColumn);
		}
	}
	
	public final void applyCellPolarity(Board board, int indexRow, int indexColumn, int indexPolarity) {
		Cell cellPolarity = board.getPolarityLayer()[indexRow][indexColumn];
		
		if(cellPolarity.getState() == indexPolarity) {
			board.getPersistenceLayer()[indexRow][indexColumn].shiftState();
		}
		else {
			cellPolarity.setState(indexPolarity);
			board.getBaseLayer()[indexRow][indexColumn].setState(computePolarityState(board, indexRow, indexColumn));
			board.getPersistenceLayer()[indexRow][indexColumn].reset();
			updateCorrectnessCell(board, indexRow, indexColumn);
		}
	}
	
	protected abstract int computePolarityState(Board board, int indexRow, int indexColumn);
	
	public int computePolarityCorrectness(Board board, int indexRow, int indexColumn) {
		if(board.getPolarityLayer()[indexRow][indexColumn].getState() == board.getGui().getDominantPolarity()) {
			return 1;
		}
		
		return 0;
	}
	
	public final CellDisplayCorrectness createCorrectnessCell(Board board, int indexRow, int indexColumn) {
		double sizeCell = board.getCellSize();
		return new CellDisplayCorrectness((indexRow * sizeCell), (indexColumn * sizeCell), sizeCell, computePolarityCorrectness(board, indexRow, indexColumn), board);
	}
	
	public final void updateCorrectnessCell(Board board, int indexRow, int indexColumn) {
		board.getCorrectnessLayer()[indexRow][indexColumn].setState(computePolarityCorrectness(board, indexRow, indexColumn));
	}
}