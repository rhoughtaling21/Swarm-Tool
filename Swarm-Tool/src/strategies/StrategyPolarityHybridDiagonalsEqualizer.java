package strategies;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import cells.Cell;
import gui.Board;
import swarm.SwarmAgent;

public class StrategyPolarityHybridDiagonalsEqualizer extends StrategyPolarity {
	static final int CAPACITY_MEMORY = Math.max(StrategyPolarityPatternDiagonals.CAPACITY_MEMORY, StrategyPolarityEqualizer.CAPACITY_MEMORY);
	static final int THRESHOLD_POLARITY_DOMINANT_MEMORY = StrategyPolarityPatternDiagonals.THRESHOLD_POLARITY_DOMINANT_MEMORY;
	static final int THRESHOLD_POLARITY_DOMINANT_NEIGHBORING = StrategyPolarityPatternDiagonals.THRESHOLD_POLARITY_DOMINANT_NEIGHBORING;
	
	private static final StrategyPolarityHybridDiagonalsEqualizer STRATEGY = new StrategyPolarityHybridDiagonalsEqualizer();
	private static final StrategyPolarityEqualizer STRATEGY_EQUALIZER = StrategyPolarityEqualizer.get();
	
	public static StrategyPolarityHybridDiagonalsEqualizer get() {
		return STRATEGY;
	}
	
	private StrategyPolarityHybridDiagonalsEqualizer() {
		
	}
	
	@Override
	public int getMemoryCapacity() {
		return CAPACITY_MEMORY;
	}
	
	@Override
	public int determineDesiredPolarity(Board board, SwarmAgent agent) {
		int indexRow = board.calculateAgentRow(agent);
		int indexColumn = board.calculateAgentColumn(agent);
		
		int countPolarities = board.getPattern().getPolarityCount();

		//	
		//COLLECT INFO	
		//
		int[] frequenciesPolaritiesNeighboring = new int[countPolarities]; //These keep track of what the neighbors currently hold
		int[] frequenciesPolaritiesNeighboringCross = new int[countPolarities]; //These keep track of how many of each color appear in the cell above, below, left and right
		int countNeighborsCross = 0;
		int indexPolarityNeighboring;
		Cell[] neighbors = board.getNeighbors(board.getPolarityLayer(), indexRow, indexColumn);
		for (int indexNeighbor = 0; indexNeighbor < neighbors.length; indexNeighbor++) {
			if (neighbors[indexNeighbor] != null) {
				//Get Polarity of Neighbors MODIFICATION #9
				frequenciesPolaritiesNeighboring[indexPolarityNeighboring = neighbors[indexNeighbor].getState()]++;

				//Collect the Cross Counts
				if(indexNeighbor % 2 == 1) {
					countNeighborsCross++;
					frequenciesPolaritiesNeighboringCross[indexPolarityNeighboring]++;
				}
			}
		}
		
		int indexPolarityCurrent = board.getPolarityLayer()[indexRow][indexColumn].getState();
		
		int indexPolarity;
		
		//RULE #6
		//Look at the agents array of memory
		//If any one color is too apparent
		//MODIFICATION #10
		//Updated 7/18 by Morgan Might
		//Now the constraints can be altered based on the dominant polarity chosen
		if(board.getGui().getEquilibriumMode()) {
			LoopRule6:
			for(indexPolarity = 0; indexPolarity < countPolarities; indexPolarity++) {
				if(agent.getMemoryPolarityFrequency(indexPolarity) >= THRESHOLD_POLARITY_DOMINANT_MEMORY) {
					int indexPolarityDominant = board.getGui().getDominantPolarity();
					
					boolean metThresholdNeighboring = frequenciesPolaritiesNeighboring[indexPolarityDominant] >= THRESHOLD_POLARITY_DOMINANT_NEIGHBORING;
					int countMemoryPolarity;
					int countMemoryPolarityDominant = agent.getMemoryPolarityFrequency(indexPolarityDominant);
					int sumMemoryPolarities = 0;
					for(indexPolarity = 0; indexPolarity < countPolarities; indexPolarity++) {
						if(indexPolarity != indexPolarityDominant) {
							if((countMemoryPolarity = agent.getMemoryPolarityFrequency(indexPolarity)) > countMemoryPolarityDominant) {
								if(metThresholdNeighboring) {
									return indexPolarityDominant;
								}
								
								break LoopRule6;
							}
							
							sumMemoryPolarities += countMemoryPolarity;
						}
					}
					
					if(sumMemoryPolarities < countMemoryPolarityDominant) {
						int frequencyPolarity;
						int frequencyPolarityMaximum = THRESHOLD_POLARITY_DOMINANT_NEIGHBORING;
						ArrayList<Integer> indicesPolaritiesFrequent = new ArrayList<Integer>(countPolarities - 1);
						for(indexPolarity = 0; indexPolarity < countPolarities; indexPolarity++) {
							if(indexPolarity != indexPolarityDominant) {
								if((frequencyPolarity = frequenciesPolaritiesNeighboring[indexPolarity]) >= frequencyPolarityMaximum) {
									if(frequencyPolarity > frequencyPolarityMaximum) {
										frequencyPolarityMaximum = frequencyPolarity;
										indicesPolaritiesFrequent.clear();
									}
									
									indicesPolaritiesFrequent.add(indexPolarity);
								}
							}
						}
						
						if(!indicesPolaritiesFrequent.isEmpty()) {
							return indicesPolaritiesFrequent.get(ThreadLocalRandom.current().nextInt(indicesPolaritiesFrequent.size()));
						}
					}
					
					break;
				}
			}
		}
		
		int thresholdPolaritiesNeighboringCross = countNeighborsCross - 1;
		for(indexPolarity = 0; indexPolarity < countPolarities; indexPolarity++) {
			if(frequenciesPolaritiesNeighboringCross[indexPolarity] >= thresholdPolaritiesNeighboringCross) {
				return indexPolarity;
			}
		}
		
		int indexPolarityMinimum = STRATEGY_EQUALIZER.determineDesiredPolarity(board, agent);
		if(indexPolarityMinimum != indexPolarityCurrent) {
			return indexPolarityMinimum;
		}
		
		for(indexPolarity = 0; indexPolarity < countPolarities; indexPolarity++) {
			if(frequenciesPolaritiesNeighboring[indexPolarity] == 0) {
				if(indexPolarity == indexPolarityCurrent) {
					return (indexPolarity + 1 + ThreadLocalRandom.current().nextInt(countPolarities - 1)) % countPolarities;
				}
				
				return indexPolarityCurrent;
			}
		}
		
		for(indexPolarity = 0; indexPolarity < countPolarities; indexPolarity++) {
			if(frequenciesPolaritiesNeighboringCross[indexPolarity] == 0) {
				if(indexPolarity == indexPolarityCurrent) {
					return (indexPolarity + 1 + ThreadLocalRandom.current().nextInt(countPolarities - 1)) % countPolarities;
				}
				
				return indexPolarityCurrent;
			}
		}
		
		for(indexPolarity = 0; indexPolarity < countPolarities; indexPolarity++) {
			if(frequenciesPolaritiesNeighboringCross[indexPolarity] == 2) {
				return indexPolarity;
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
