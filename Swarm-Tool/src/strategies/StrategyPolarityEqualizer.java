package strategies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

import cells.Cell;
import cells.CellTalliedPolarity;
import gui.Board;
import swarm.SwarmAgent;

public class StrategyPolarityEqualizer extends StrategyPolarity {
	static final int CAPACITY_MEMORY = 20;
	static final int THRESHOLD_COUNT_MEMORIES_MINIMUM = 10;
	static final int THRESHOLD_POLARITY_MINIMUM_NEIGHBORING = 3;
	static final HashSet<Integer> INDICES_POLARITIES_EXCLUDED = new HashSet<Integer>(Arrays.asList(new Integer[]{1}));
	
	private static final StrategyPolarityEqualizer STRATEGY = new StrategyPolarityEqualizer();
	
	public static StrategyPolarityEqualizer get() {
		return STRATEGY;
	}
	
	private StrategyPolarityEqualizer() {
		
	}
	
	@Override
	public int getMemoryCapacity() {
		return CAPACITY_MEMORY;
	}
	
	@Override
	public int determineDesiredPolarity(Board board, SwarmAgent agent) {
		int indexRow = board.calculateAgentRow(agent);
		int indexColumn = board.calculateAgentColumn(agent);
		
		CellTalliedPolarity[][] layerPolarity = board.getPolarityLayer();
		int indexPolarityCurrent = layerPolarity[indexRow][indexColumn].getState();
		
		if(agent.getMemoryCount() >= THRESHOLD_COUNT_MEMORIES_MINIMUM) {
			int countPolarities = board.getPattern().getPolarityCount();
			
			HashSet<Integer> indicesPolaritiesMinimum = new HashSet<Integer>(countPolarities);
			int frequencyPolaritiesMinimum = agent.getMemoryCapacity();
			
			int frequencyPolarity;
			for(int indexPolarity = 0; indexPolarity < countPolarities; indexPolarity++) {
				frequencyPolarity = agent.getMemoryPolarityFrequency(indexPolarity);
				
				if(frequencyPolarity <= frequencyPolaritiesMinimum) {
					if(frequencyPolarity < frequencyPolaritiesMinimum) {
						indicesPolaritiesMinimum.clear();
						
						frequencyPolaritiesMinimum = frequencyPolarity;
					}
					
					indicesPolaritiesMinimum.add(indexPolarity);
				}
			}
			
			if(!indicesPolaritiesMinimum.contains(indexPolarityCurrent)) {
				indicesPolaritiesMinimum.removeAll(INDICES_POLARITIES_EXCLUDED);
				
				ArrayList<Integer> indicesPolaritiesMinimumNeighboring = new ArrayList<Integer>(indicesPolaritiesMinimum);
				Cell[] neighbors = board.getNeighbors(layerPolarity, indexRow, indexColumn);
				int indexPolarityNeighboring;
				for (int indexNeighbor = 0; indexNeighbor < neighbors.length; indexNeighbor++) {
					if (neighbors[indexNeighbor] != null) {
						indexPolarityNeighboring = neighbors[indexNeighbor].getState();
						
						if(indicesPolaritiesMinimum.contains(indexPolarityNeighboring)) {
							indicesPolaritiesMinimumNeighboring.add(indexPolarityNeighboring);
						}
					}
				}
				
				for(Integer indexPolarity : indicesPolaritiesMinimum) {
					for(int indexRemoval = 0; indexRemoval < THRESHOLD_POLARITY_MINIMUM_NEIGHBORING; indexRemoval++) {
						indicesPolaritiesMinimumNeighboring.remove(Integer.valueOf(indexPolarity));
					}
				}
				
				if(indicesPolaritiesMinimumNeighboring.size() > 0) {
					return indicesPolaritiesMinimumNeighboring.get(ThreadLocalRandom.current().nextInt(indicesPolaritiesMinimumNeighboring.size()));
				}
			}
		}
		
		return indexPolarityCurrent;
	}
	
	@Override
	public void logic(Board board, SwarmAgent agent) {
		int indexPolarity = determineDesiredPolarity(board, agent);
		
		board.getPattern().applyCellPolarity(board, board.calculateAgentRow(agent), board.calculateAgentColumn(agent), indexPolarity);
		agent.seePolarity(indexPolarity);
	}
}
