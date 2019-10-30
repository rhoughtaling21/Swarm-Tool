package cells;
/*		Author: Morgan Might
 * 		Description: This is the constructor for the cells displayed on Layer4. 
 * 		Parameters: Cell is made up of an x coordinate, a y coordinate, a fixed size, and a color and for this class (layer 1) the cells can only be either 
 * 					black or white.
 * 					setColor is made up of an object color which determines what color each cell can be.
 */

import java.awt.Color;

import gui.Board;

public class CellPersistence extends Cell {
	private static final int COUNT_STATES_PERSISTENCE = 50;
	private static final int STATE_FINAL = COUNT_STATES_PERSISTENCE - 1;
	private static final Color COLOR_INITIAL = Color.WHITE;
	private static final Color COLOR_FINAL = new Color(60, 0, 60);
	private static final Color[] COLORS = new Color[COUNT_STATES_PERSISTENCE];
	
	private static Color computeCellColor(int indexState) {
		if(COLORS[indexState] == null) {
			COLORS[indexState] = new Color(COLOR_INITIAL.getRed() - (((COLOR_INITIAL.getRed() - COLOR_FINAL.getRed()) / COUNT_STATES_PERSISTENCE) * indexState), COLOR_INITIAL.getGreen() - (((COLOR_INITIAL.getGreen() - COLOR_FINAL.getGreen()) / COUNT_STATES_PERSISTENCE) * indexState), COLOR_INITIAL.getBlue() - (((COLOR_INITIAL.getBlue() - COLOR_FINAL.getBlue()) / COUNT_STATES_PERSISTENCE) * indexState));
		}
		
		return COLORS[indexState];
	}
	
	public CellPersistence(Board board) {
		super(0, board);
		
		updateState();
	}
	
	public void shiftState() {
		if(indexState < STATE_FINAL) {
			setState(indexState + 1);
		}
	}
	
	public void resetState() {
		setState(0);
	}
	
	@Override
	protected Color determineColor() {
		return computeCellColor(indexState);
	}
}