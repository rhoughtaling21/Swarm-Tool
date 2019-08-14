package cells;
/*		Author: Morgan Might
 * 		Description: This is the constructor for the cells displayed on Layer4. 
 * 		Parameters: Cell is made up of an x coordinate, a y coordinate, a fixed size, and a color and for this class (layer 1) the cells can only be either 
 * 					black or white.
 * 					setColor is made up of an object color which determines what color each cell can be.
 */

import java.awt.Color;

import gui.Board;

@SuppressWarnings("serial")
public class CellDisplayPersistence extends CellDisplay {
	private static final int COUNT_STATES_PERSISTENCE = 50;
	private static final Color COLOR_INITIAL = Color.WHITE;
	private static final Color COLOR_FINAL = new Color(60, 0, 60);
	private static final Color[] COLORS = new Color[COUNT_STATES_PERSISTENCE];
	
	public CellDisplayPersistence(double x, double y, double size, Board board) {
		super(x, y, size, board);
		indexState = -1;
		reset();
	}

	@Override
	public void setState(int indexState) {
		if(indexState < COUNT_STATES_PERSISTENCE) {
			this.indexState = indexState;
			
			colorFill = computeColor(indexState);
		}
	}
	
	public void shiftState() {
		setState(indexState + 1);
	}
	
	public void reset() {
		setState(0);
	}
	
	public Color computeColor(int indexState) {
		if(COLORS[indexState] == null) {
			COLORS[indexState] = new Color(COLOR_INITIAL.getRed() - (((COLOR_INITIAL.getRed() - COLOR_FINAL.getRed()) / COUNT_STATES_PERSISTENCE) * indexState), COLOR_INITIAL.getGreen() - (((COLOR_INITIAL.getGreen() - COLOR_FINAL.getGreen()) / COUNT_STATES_PERSISTENCE) * indexState), COLOR_INITIAL.getBlue() - (((COLOR_INITIAL.getBlue() - COLOR_FINAL.getBlue()) / COUNT_STATES_PERSISTENCE) * indexState));
		}
		
		return COLORS[indexState];
	}
}