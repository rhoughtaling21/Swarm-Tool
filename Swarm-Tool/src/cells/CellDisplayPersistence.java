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
	int persistenceValue;
	
	public CellDisplayPersistence(double x, double y, double size, Color colorFill, Board board, int persistenceValue) {
		super(x, y, size, colorFill, board);
		this.persistenceValue = persistenceValue;
	}
	
	public int getPersistenceValue() {
		return persistenceValue;
	}
	
	@Override
	public void setColor(Color colorFill) {
		this.colorFill = colorFill;
	}
	
	public void setPersistenceValue(int num) {
		persistenceValue = num;
	}
}
