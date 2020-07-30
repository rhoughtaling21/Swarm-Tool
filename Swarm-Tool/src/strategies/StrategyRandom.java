package strategies;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import gui.Board;
import swarm.SwarmAgent;

public class StrategyRandom extends Strategy {
	private int capacityMemoryMaximum;
	private int sumWeights;
	private int[] weights;
	private Strategy[] strategies;
	
	public StrategyRandom(Strategy[] strategies, int[] weights) {
		this.strategies = strategies;
		
		if(weights.length != strategies.length) {
			weights = Arrays.copyOf(weights, strategies.length);
		}
		
		this.weights = weights;
		
		sumWeights = 0;
		for(int indexWeight = 0; indexWeight < weights.length; indexWeight++) {
			sumWeights += weights[indexWeight];
		}
		
		capacityMemoryMaximum = 0;
		
		int capacityMemoryStrategy;
		for(int indexStrategy = 0; indexStrategy < strategies.length; indexStrategy++) {
			if(weights[indexStrategy] > 0) {
				capacityMemoryStrategy = strategies[indexStrategy].getMemoryCapacity();
				
				if(capacityMemoryStrategy > capacityMemoryMaximum) {
					capacityMemoryMaximum = capacityMemoryStrategy;
				}
			}
		}
	}
	
	@Override
	public int getMemoryCapacity() {
		return capacityMemoryMaximum;
	}
	
	@Override
	public void logic(Board board, SwarmAgent agent) {
        int indexStrategyWeighted = ThreadLocalRandom.current().nextInt(sumWeights);
        
        int sumWeights = 0;
        for (int indexStrategy = 0; indexStrategy < strategies.length; indexStrategy++) {
            sumWeights += weights[indexStrategy];
            
            if (sumWeights > indexStrategyWeighted) {
            	strategies[indexStrategy].logic(board, agent);
            	
            	break;
            }
        }
	}
}