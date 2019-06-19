package cells;

import java.awt.Color;
import java.awt.Graphics2D;

public interface Cell {
	public Color getColor();
	public void setColor(Color colorFill);
	public void draw(Graphics2D helperGraphics2D);
}
