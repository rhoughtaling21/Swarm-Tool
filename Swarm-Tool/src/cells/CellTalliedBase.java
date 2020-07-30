package cells;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

import gui.Board;

/**
 * CellTalliedBase defines the Cells of the Base Layer, each of which is to display its own state and keep track of how many Base Cells are in each state.
 * 
 * @see CellTallied
 */
public class CellTalliedBase extends CellTallied {
	/** The array that contains the Colors that correspond to each possible state */
	private static final Color[] COLORS_BASE = {Color.BLACK, Color.WHITE, Color.GRAY};
	/** The number of possible states (the length of {@link #COLORS_BASE}) */
	private static final int COUNT_STATES_MAXIMUM = COLORS_BASE.length;
	
	/**
	 * Returns {@link #COUNT_STATES_MAXIMUM}
	 * @return - {@link #COUNT_STATES_MAXIMUM}
	 */
	public static int getMaximumStateCount() {
		return COUNT_STATES_MAXIMUM;
	}
	
	/**
	 * Retrieves the Color that corresponds to the given integer state
	 * @param indexState - The (integer) state for which the Cell's color should be calculated or retrieved
	 * @return - The appropriate Color from {@link #COLORS_BASE}
	 */
	public static Color retrieveCellColor(int indexState) {
		return COLORS_BASE[Math.max(0, Math.min(COUNT_STATES_MAXIMUM, indexState))];
	}
	
	/**
	 * Creates a new Cell of type CellTalliedBase that is initially in the given state and that considers itself a part of the given Board
	 * @param indexState - The (integer) state that this Cell is to be in initially
	 * @param board - The Board to which this Cell will be attached
	 * 
	 * @see CellTallied#CellTallied(int, Board, int[], int[])
	 */
	public CellTalliedBase(int indexState, Board board) {
		super(indexState, board, board.getColorFrequencies(), board.getInitialColorFrequencies());
		
		updateState();
	}
	
	/**
	 * Creates a new Cell of type CellTalliedBase that is initially in a random state and that considers itself a part of the given Board
	 * @param board - The Board to which this Cell will be attached
	 * 
	 * @see #CellTalliedBase(int, Board)
	 */
	public CellTalliedBase(Board board) {
		this(ThreadLocalRandom.current().nextInt(board.getPattern().getStateCount()), board);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see #retrieveCellColor(int)
	 */
	@Override
	protected Color determineColor() {
		return retrieveCellColor(indexState);
	}
	
	/**
	 * Shifts the Cell into the next possible state
	 */
	public void shiftState() {
		setState((indexState + 1) % board.getPattern().getStateCount());
	}
}