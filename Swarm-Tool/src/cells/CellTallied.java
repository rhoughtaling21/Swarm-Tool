package cells;

import gui.Board;

/**
 * CellTallied is an abstract parent class that defines those Cells for which the frequency of each possible state should be measured.
 * 
 * @see Cell
 */
public abstract class CellTallied extends Cell {
	/** The array that stores the number of Cells of this type that are in each possible state */
	private int[] frequencies;
	
	/**
	 * Creates a new Cell of type CellTallied that is initially in the given state and that considers itself a part of the given Board
	 * @param indexState - The (integer) state that this Cell is to be in initially
	 * @param board - The Board to which this Cell will be attached
	 * @param frequencies - An array in which the number of Cells of this type that are in each possible state should be stored
	 * 
	 * @see Cell#Cell(int, Board)
	 */
	public CellTallied(int indexState, Board board, int[] frequencies) {
		super(indexState, board);
		
		this.frequencies = frequencies;
	}
	
	/**
	 * Creates a new Cell of type CellTallied that is initially in the given state and that considers itself a part of the given Board
	 * @param indexState - The (integer) state that this Cell is to be in initially
	 * @param board - The Board to which this Cell will be attached
	 * @param frequencies - An array in which the number of Cells of this type that are in each possible state should be stored
	 * @param frequenciesInitial - An array in which the number of Cells of this type that are <i>initially</i> in each possible state should be stored
	 * 
	 * @see #CellTallied(int, Board, int[])
	 */
	public CellTallied(int indexState, Board board, int[] frequencies, int[] frequenciesInitial) {
		this(indexState, board, frequencies);
		
		frequenciesInitial[indexState]++;
	}

	/**
	 * {@inheritDoc}<br/>
	 * --------------------<br/>
	 * Decrements the frequency of Cells of this type that are in the current state, as stored in {@link #frequencies}
	 */
	@Override
	protected void leaveState() {
		frequencies[indexState]--;
	}
	
	/**
	 * {@inheritDoc}<br/>
	 * --------------------<br/>
	 * Increments the frequency of Cells of this type that are in the current state, as stored in {@link #frequencies}
	 */
	@Override
	protected void enterState() {
		frequencies[indexState]++;
	}
}