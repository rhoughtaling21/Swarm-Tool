package cells;

import java.awt.Color;

import gui.Board;

public class CellCorrectness extends Cell {
	private static final Color COLOR_CORRECT = new Color(0, 180, 0);
	private static final Color COLOR_INCORRECT = new Color(180, 0, 0);
	
	public CellCorrectness(int indexState, Board board) {
		super(indexState, board);
		
		updateState();
	}

	@Override
	public Color determineColor() {
		if(indexState == 1) {
			return COLOR_CORRECT;
		}
		
		return COLOR_INCORRECT;
	}
}