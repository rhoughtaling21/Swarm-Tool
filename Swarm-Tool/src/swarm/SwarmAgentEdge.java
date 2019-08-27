package swarm;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import gui.Board;
import strategies.Strategy;

@SuppressWarnings("serial")
public class SwarmAgentEdge extends SwarmAgent {
	private boolean searching;
	private int indexAxis;
	
	public SwarmAgentEdge(double x, double y, double size, Color colorFill, Board board, Strategy strategy) {
		this(x, y, size, colorFill, board, strategy, 0);
	}

	public SwarmAgentEdge(double x, double y, double size, Color colorFill, Board board, Strategy strategy,	double rangeSignal) {
		super(x, y, size, colorFill, board, strategy, rangeSignal);
		
		searching = true;
		
		updateAxis();
		
		if(searching) {
			double[] componentsVectorVelocityInitial = new double[2];
			Random generatorNumbersRandom = ThreadLocalRandom.current();
			
			double component = board.getCellSize() * 0.5;
			if(generatorNumbersRandom.nextDouble() < 0.5) {
				component *= -1;
			}
			
			componentsVectorVelocityInitial[indexAxis = generatorNumbersRandom.nextInt(2)] = component;
			
			setVelocity(componentsVectorVelocityInitial[0], componentsVectorVelocityInitial[1]);
		}
	}

	@Override
	public void randomizeVelocityVector() {
		if(!searching) {
			double[] componentsVectorVelocity = new double[2];
			
			componentsVectorVelocity[indexAxis] = generateRandomVelocityVectorComponent();
			
			setVelocity(componentsVectorVelocity[0], componentsVectorVelocity[1]);
		}
	}
	
	@Override
	public void step() {
		super.step();
		
		updateAxis();
	}
	
	private boolean atVerticalEdge() {
		int indexRow = board.calculateAgentRow(this);
		
		return indexRow == 0 || indexRow == board.getBreadth() - 1;
	}
	
	private boolean atHorizontalEdge() {
		int indexColumn = board.calculateAgentColumn(this);
		
		return indexColumn == 0 || indexColumn == board.getBreadth() - 1;
	}
	
	private void updateAxis() {
		boolean atEdgeHorizontal = atHorizontalEdge();
		boolean atEdgeVertical = atVerticalEdge();
		
		if(searching) {
			if(searching = !(atEdgeHorizontal || atEdgeVertical)) {
				return;
			}
		}
		
		ArrayList<Integer> indicesAxes = new ArrayList<Integer>(2);
		
		if(atEdgeHorizontal) {
			indicesAxes.add(0);
		}
			
		if(atEdgeVertical) {
			indicesAxes.add(1);
		}

		int indexAxisPrevious = indexAxis;
		indexAxis = indicesAxes.get(ThreadLocalRandom.current().nextInt(indicesAxes.size()));
				
		if(indexAxis != indexAxisPrevious) {
			randomizeVelocityVector();
		}
	}
}
