package cells;

import java.awt.Color;

import gui.Board;

/**
 * CellCorrectness defines the Cells of the Correctness Layer, each of which is to display whether or not the corresponding Cell in the Board's Polarity Layer is in the state that is currently considered 'correct'.
 * 
 * @see Cell
 */
public class CellCorrectness extends Cell {
	/* Constants */
	/** The Color that signifies that the underlying Polarity Cell <span style="text-decoration:underline"><i>is</i></span> in the 'correct' state -- (Green) */
	private static final Color COLOR_CORRECT = new Color(0, 180, 0); //Green
	/** The Color that signifies that the underlying Polarity Cell <span style="text-decoration:underline">is <i>not</i></span> in the 'correct' state -- (Red) */
	private static final Color COLOR_INCORRECT = new Color(180, 0, 0); //Red
	
	/**
	 * Creates a new Cell of type CellCorrectness that is initially in the given state and that considers itself a part of the given Board
	 * @param indexState - The (integer) state that this Cell is to be in initially
	 * @param board - The Board to which this Cell will be attached
	 * 
	 * @see Cell#Cell(int, Board)
	 */
	public CellCorrectness(int indexState, Board board) {
		super(indexState, board);
		
		updateState();
	}

	/**
	 * {@inheritDoc}<br/>
	 * --------------------<br/>
	 * Returns {@link #COLOR_CORRECT} when {@link #indexState} is equal to 1 ('correct') and {@link #COLOR_INCORRECT} when it is not
	 */
	@Override
	public Color determineColor() {
		if(indexState == 1) {
			return COLOR_CORRECT;
		}
		
		return COLOR_INCORRECT;
	}
}