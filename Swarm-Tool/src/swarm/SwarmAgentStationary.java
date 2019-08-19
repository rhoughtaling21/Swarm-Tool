package swarm;

import java.awt.Color;

import gui.Board;
import strategies.Strategy;

@SuppressWarnings("serial")
public class SwarmAgentStationary extends SwarmAgent {
	public SwarmAgentStationary(double x, double y, double size, Color colorFill, Board board, Strategy strategy) {
		super((Board.SCALE_BOARD - size) / 2, (Board.SCALE_BOARD - size) / 2, size, colorFill, board, strategy, 0);
	}
	
	public SwarmAgentStationary(double x, double y, double size, Color colorFill, Board board, Strategy strategy, double rangeSignal) {
		super((Board.SCALE_BOARD - size) / 2, (Board.SCALE_BOARD - size) / 2, size, colorFill, board, strategy, rangeSignal);
	}
	
	@Override
	public void step() {
		
	}
}