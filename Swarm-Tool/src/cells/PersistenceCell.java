package cells;
/*		Author: Morgan Might
 * 		Description: This is the constructor for the cells displayed on Layer4. 
 * 		Parameters: Cell is made up of an x coordinate, a y coordinate, a fixed size, and a color and for this class (layer 1) the cells can only be either 
 * 					black or white.
 * 					setColor is made up of an object color which determines what color each cell can be.
 */

import java.awt.Color;

@SuppressWarnings("serial")
public class PersistenceCell extends GenericCell{
	
	int persistenceNum;
	
	public PersistenceCell(double x, double y, double size, Color c, int persistenceNum) {
		super(x,y,size,c);
		this.persistenceNum = persistenceNum;
	}
	
	public Color getColor() {
		return cellColor;
	}
	
	public void setColor(Color newColor) {
		cellColor = newColor;
	}
	
	public void setPersistenceNum(int num) {
		persistenceNum = num;
	}
	
	public int getPersistenceNum() {
		return persistenceNum;
	}

}
