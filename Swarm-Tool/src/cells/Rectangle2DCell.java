package cells;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * Rectangle2DCell defines the Rectangles that serve as visual representations for the Cells of the active layer.
 * 
 * @see java.awt.geom.Rectangle2D.Double
 */
@SuppressWarnings("serial")
public class Rectangle2DCell extends Rectangle2D.Double {
	/** The Cell of {@link #cells} that corresponds to the layer that is currently active */
	private Cell cellActive;
	/** The array of Cells that this Rectangle is to represent for each possible active layer */
	private Cell[] cells;
	
	/**
	 * 
	 * @param x - The x-coordinate at which the left side of the Rectangle is to be drawn
	 * @param y - The y-coordinate at which the upper side of the Rectangle is to be drawn
	 * @param size - The on-screen width and height with which the Rectangle is to be drawn
	 * @param cells - The array of Cells that the Rectangle is represent for each possible active layer
	 * @param indexLayer - The index of the layer that is to be considered active initially
	 * 
	 * @see java.awt.geom.Rectangle2D.Double#Double(double, double, double, double)
	 */
	public Rectangle2DCell(double x, double y, double size, Cell[] cells, int indexLayer) {
		super(x, y, size, size);
		
		this.cells = cells;
		
		setLayer(indexLayer);
	}

	/**
	 * Sets {@link #cellActive} to the Cell in {@link #cells} that corresponds to the given index
	 * @param indexLayer - The index of the layer which is now to be considered active
	 */
	public void setLayer(int indexLayer) {
		cellActive = cells[Math.max(0, Math.min(cells.length, indexLayer))];
	}
	
	/**
	 * Draws the Rectangle on-screen in the appropriate location and with the appropriate Color, as decided by {@link #cellActive}
	 * @param helperGraphics2D
	 */
	public void draw(Graphics2D helperGraphics2D) {
		helperGraphics2D.setColor(cellActive.getColor());
		helperGraphics2D.fill(this);
	}
}