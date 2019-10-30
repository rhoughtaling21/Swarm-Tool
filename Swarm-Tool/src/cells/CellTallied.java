package cells;

import gui.Board;

public abstract class CellTallied extends Cell {
	private int[] frequencies;
	
	public CellTallied(int indexState, Board board, int[] frequencies) {
		super(indexState, board);
		
		this.frequencies = frequencies;
	}
	
	public CellTallied(int indexState, Board board, int[] frequencies, int[] frequenciesInitial) {
		this(indexState, board, frequencies);
		
		frequenciesInitial[indexState]++;
	}

	@Override
	protected void leaveState() {
		frequencies[indexState]--;
	}
	
	@Override
	protected void enterState() {
		frequencies[indexState]++;
	}
}