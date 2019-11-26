package cells;

import java.awt.Color;

import gui.Board;

/**
 * CellPersistence defines the Cells of the Persistence Layer, each of which is to display how recently the state of the corresponding Cell in the Board's Base layer was changed.
 * 
 * @see Cell
 */
public class CellPersistence extends Cell {
	/* Constants */
	/** The maximum number of recent agent-steps that are to be recorded -- ({@value #COUNT_STATES_PERSISTENCE}) */
	private static final int COUNT_STATES_PERSISTENCE = 50;
	/** The maximum value of {@link #indexState} -- ({@value #STATE_FINAL}) */
	private static final int STATE_FINAL = COUNT_STATES_PERSISTENCE - 1;
	/** The Color that signifies that the state of the underlying Base Cell was changed by the most recent agent-step -- (White) */
	private static final Color COLOR_INITIAL = Color.WHITE;
	/** The Color that signifies that the state of the underlying Base Cell has not been changed by the past {@link #COUNT_STATES_PERSISTENCE} agent-steps -- (Purple) */
	private static final Color COLOR_FINAL = new Color(60, 0, 60);
	/** The dynamically populated array of Colors that correspond to each possible state */
	private static final Color[] COLORS = new Color[COUNT_STATES_PERSISTENCE];
	
	/**
	 * Calculates or retrieves the Color that corresponds to the given integer state as needed
	 * @param indexState - The (integer) state for which the Cell's color should be calculated or retrieved
	 * @return - The Color between {@link #COLOR_INITIAL} and {@link #COLOR_FINAL} that is proportional to the position of the given state between 0 and {@link #STATE_FINAL}, as stored in {@link #COLORS} 
	 */
	private static Color computeCellColor(int indexState) {
		indexState = Math.max(0, Math.min(STATE_FINAL, indexState));
		
		if(COLORS[indexState] == null) {
			COLORS[indexState] = new Color(COLOR_INITIAL.getRed() - (((COLOR_INITIAL.getRed() - COLOR_FINAL.getRed()) / COUNT_STATES_PERSISTENCE) * indexState), COLOR_INITIAL.getGreen() - (((COLOR_INITIAL.getGreen() - COLOR_FINAL.getGreen()) / COUNT_STATES_PERSISTENCE) * indexState), COLOR_INITIAL.getBlue() - (((COLOR_INITIAL.getBlue() - COLOR_FINAL.getBlue()) / COUNT_STATES_PERSISTENCE) * indexState));
		}
		
		return COLORS[indexState];
	}
	
	/**
	 * Creates a new Cell of type CellPersistence that is in the default state, 0, and that considers itself a part of the given Board
	 * @param board - The Board to which this Cell will be attached
	 * 
	 * @see Cell#Cell(int, Board)
	 */
	public CellPersistence(Board board) {
		super(0, board);
		
		updateState();
	}
	
	/**
	 * Shifts the Cell to the next state, if {@link #indexState} has not already reached {@link #STATE_FINAL}
	 * 
	 * @see #setState(int)
	 */
	public void shiftState() {
		if(indexState < STATE_FINAL) {
			setState(indexState + 1);
		}
	}
	
	/**
	 * Sets the Cell's state back to the default, 0
	 * 
	 * @see #setState(int)
	 */
	public void resetState() {
		setState(0);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see #computeCellColor(int)
	 */
	@Override
	protected Color determineColor() {
		return computeCellColor(indexState);
	}
}