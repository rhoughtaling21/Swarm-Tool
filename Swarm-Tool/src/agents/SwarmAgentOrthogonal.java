package agents;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

import gui.Board;
import strategies.Strategy;

@SuppressWarnings("serial")
public class SwarmAgentOrthogonal extends SwarmAgent {
	public SwarmAgentOrthogonal(double x, double y, double size, Color colorFill, Board board, Strategy strategy) {
		super(x, y, size, colorFill, board, strategy);
	}
	
	@Override
	public void randomizeVelocity() {
		double[] componentsVectorVelocity = new double[2];
		componentsVectorVelocity[ThreadLocalRandom.current().nextInt(2)] = generateRandomVelocityVectorComponent();
		
		setVelocity(componentsVectorVelocity[0], componentsVectorVelocity[1]);
	}
}