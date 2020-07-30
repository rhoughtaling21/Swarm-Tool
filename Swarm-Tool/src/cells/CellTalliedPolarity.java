package cells;

import java.awt.Color;

import gui.Board;

/**
 * CellTalliedPolarity defines the Cells of the Polarity Layer, each of which is to display the 'polarity' of state of the corresponding Cell in the Board's Base Layer
 * and keep track of how many Cells have each 'polarity'.
 * 
 * @see CellTallied
 */
public class CellTalliedPolarity extends CellTallied {
	/** The array that contains the Colors that correspond to each possible polarity */
	private Color[] colorsPolarity;
	
	/**
	 * Creates a new Cell of type CellTalliedPolarity that is initially in the given state and that considers itself a part of the given Board
	 * @param indexState - The (integer) state that this Cell is to be in initially
	 * @param board - The Board to which this Cell will be attached
	 * 
	 * @see CellTallied#CellTallied(int, Board, int[])
	 */
	public CellTalliedPolarity(int indexState, Board board) {
		super(indexState, board, board.getPolarityFrequencies());
		
		colorsPolarity = board.getGui().getPolarityColors();
		
		updateState();
	}
	
	/**
	 * {@inheritDoc}<br/>
	 * --------------------<br/>
	 * Retrieves the appropriate Color from {@link #colorsPolarity}
	 */
	@Override
	protected Color determineColor() {
		return colorsPolarity[indexState];
	}
}