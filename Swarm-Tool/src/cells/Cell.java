package cells;

import java.awt.Color;

import gui.Board;

public abstract class Cell {
	protected int indexState;
	protected Color colorFill;
	protected Board board;
	
	public Cell(int indexState, Board board) {
		this.board = board;
		
		setStateValue(indexState);
	}
	
	public final int getState() {
		return indexState;
	}
	
	public final Color getColor() {
		return colorFill;
	}
	
	private void setStateValue(int indexState) {
		this.indexState = indexState;
	}
	
	public final void setState(int indexState) {
		leaveState();
		setStateValue(indexState);
		updateState();
	}
	
	protected void leaveState() {
		
	}
	
	protected final void updateState() {
		updateColor();
		enterState();
	}
	
	public final void updateColor() {
		this.colorFill = determineColor();
	}
	
	protected void enterState() {
		
	}
	
	protected abstract Color determineColor();
}