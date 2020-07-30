package cells;

import java.awt.Color;

import gui.Board;

/**
 * Cell is an abstract parent class that defines the elements that a Cell of any layer must possess:
 * 	<ul>
 * 		<li>a changeable state (represented as an integer)</li>
 * 		<li>a color that corresponds to that state</li>
 * 	</ul>
 */
public abstract class Cell {
	/* Instance Variables */
	/** The Cell's current state, represented as an integer */
	protected int indexState;
	/** The Cell's current Color */
	protected Color colorFill;
	/** The Board to which the Cell is attached */
	protected Board board;
	
	/**
	 * Creates a new Cell of type Cell that is initially in the given state and that considers itself a part of the given Board<br/><br/>
	 * <b><i>Note:</i></b> All subclasses should call {@link #updateState()} at the <span style="text-decoration: underline">end</span> of their respective constructors
	 * @param indexState - The (integer) state that this Cell is to be in initially
	 * @param board - The Board to which this Cell will be attached
	 */
	public Cell(int indexState, Board board) {
		this.board = board;
		
		setStateValue(indexState);
	}
	
	/**
	 * Returns {@link #indexState}
	 * @return <b>{@link #indexState}</b>
	 */
	public final int getState() {
		return indexState;
	}
	
	/**
	 * Returns {@link #colorFill}
	 * @return <b>{@link #colorFill}</b>
	 */
	public final Color getColor() {
		return colorFill;
	}
	
	/**
	 * Sets {@link #indexState} to the given value
	 * @param indexState - The Cell's new state, as represented by an integer
	 */
	private void setStateValue(int indexState) {
		this.indexState = indexState;
	}
	
	/**
	 * <i>Template Method</i><br/><br/>
	 * 
	 * Shifts the Cell from its current state into the given state
	 * @param indexState - The state to which the Cell is to shift
	 */
	public final void setState(int indexState) {
		leaveState();
		setStateValue(indexState);
		updateState();
	}
	
	/**
	 * <i>Hook Method</i><br/><br/>
	 * 
	 * Makes any necessary preparations for the Cell to leave its current state
	 */
	protected void leaveState() {
		
	}
	
	/**
	 * <i>Template Method</i><br/><br/>
	 * 
	 * Performs the necessary changes to account for the Cell having changed its state, including updating its color
	 */
	protected final void updateState() {
		updateColor();
		enterState();
	}
	
	/**
	 * Updates {@link #colorFill}, ensuring that it corresponds to the Cell's current state
	 * 
	 * @see #determineColor()
	 */
	public final void updateColor() {
		this.colorFill = determineColor();
	}
	
	/**
	 * <i>Hook Method</i><br/><br/>
	 * 
	 * Makes any necessary changes to accommodate the Cell having changed its state <span style="text-decoration: underline">except</span> for updating its color
	 * 
	 * @see #updateState()
	 */
	protected void enterState() {
		
	}
	
	/**
	 * Computes and returns the Color that is presently associated with the Cell's current state
	 * @return - The Color that currently corresponds to the Cell's state
	 */
	protected abstract Color determineColor();
}