package agents;

import java.awt.Color;

import gui.Board;
import strategies.Strategy;

@SuppressWarnings("serial")
public class SwarmAgentSignalTransmitter extends SwarmAgent {
	private double rangeSignal;
	
	public SwarmAgentSignalTransmitter(double x, double y, double size, Color colorFill, Board board, Strategy strategy, double rangeSignal) {
		super(Board.SCALE_BOARD / 2, Board.SCALE_BOARD / 2, size, colorFill, board, strategy);
		this.rangeSignal = rangeSignal;
	}
	
	public double getSignalRange() {
		return rangeSignal;
	}
	
	@Override
	public void step() {
		
	}
}