package cells;

import java.awt.Color;

import gui.Board;

@SuppressWarnings("serial")
public class CellDisplayCorrectness extends CellDisplay {
	private static final Color COLOR_CORRECT = new Color(0, 180, 0);
	private static final Color COLOR_INCORRECT = new Color(180, 0, 0);
	
	public CellDisplayCorrectness(double x, double y, double size, int indexState, Board board) {
		super(x, y, size, board);
		setState(indexState);
	}

	@Override
	public void setState(int indexState) {
		this.indexState = indexState;
		
		if(indexState == 1) {
			colorFill = COLOR_CORRECT;
		}
		else {
			colorFill = COLOR_INCORRECT;
		}
	}
}