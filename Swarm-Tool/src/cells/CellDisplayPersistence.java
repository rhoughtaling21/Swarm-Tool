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
	
	public CellDisplayPersistence(double x, double y, double size, Board board) {
		super(x, y, size, board);
		indexState = -1;
		setState(0);
	}

	@Override
	public void setState(int indexState) {
		indexState = Math.min(indexState, COUNT_STATES_PERSISTENCE);
		
		if(this.indexState != indexState) {
			this.indexState = indexState;
			
			int valueHue = 255 - (((255 - 60) / COUNT_STATES_PERSISTENCE) * indexState);
			colorFill = new Color(valueHue, 255 - (((255) / COUNT_STATES_PERSISTENCE) * indexState), valueHue);
		}
	}
	
	public void shiftState() {
		setState(indexState + 1);
	}
}