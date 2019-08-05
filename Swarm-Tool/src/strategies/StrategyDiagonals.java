package strategies;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import cells.Cell;
import gui.Board;
import other.SwarmAgent;

public class StrategyDiagonals extends Strategy {
	private static final int COUNT_STATES = 3;
	private static final int COUNT_POLARITIES = 3;
	private static final int[][] TABLE_STATES = {{0, 2, 1}, {2, 1, 0}, {1, 0, 2}};
	/*
	 * Author Morgan Might
	 * Created 5/24/2018
	 * This strategy creates diagonal lines that are made up of black, gray, and white Cells. New Rules
	 * are involved to keep track of polarities and to change the color of cell rather than just flip
	 * since there are now three colors involved.
	 */

	@Override
	public int getStateCount() {
		return COUNT_STATES;
	}

	@Override
	public int getPolarityCount() {
		return COUNT_POLARITIES;
	}

	@Override
	public int determineStatePolarity(Board board, int indexRow, int indexColumn) {
		//Layer 2 in this sense shows 3 colors. one for each variation of diagonal lines. 

		return TABLE_STATES[board.getBaseLayer()[indexRow][indexColumn].getState()][(indexRow + indexColumn) % 3];
	}

	//UPDATED: If there are an equal amount of red and blue cells there will be a coin flip. If there is more
	// red than yellow or more blue than yellow the first layer will change to create more yellow polarity.
	//
	//Added 6/13/2018 by Morgan Might
	@Override
	public void logic(Board board, SwarmAgent agent) {
		int indexRow = board.calculateAgentRow(agent);
		int indexColumn = board.calculateAgentColumn(agent);

		//MODIFICATION #9:
		//added 7/10 by Morgan Might
		//Must get a count of the neighboring cells' polarity to grow certain color polarity
		int indexPolarity = determineDesiredPolarity(board, agent);

		//DETERMINE NEW COLOR	
		//MODIFICATION #9 continued
		//Based on the new polarity color change the Cell color (Black, Gray, or White)
		applyCellState(board, indexRow, indexColumn, TABLE_STATES[indexPolarity][(indexRow + indexColumn) % 3]);
		agent.seePolarity(indexPolarity);
	}
	
	public int determineDesiredPolarity(Board board, SwarmAgent agent) {
		int indexRow = board.calculateAgentRow(agent);
		int indexColumn = board.calculateAgentColumn(agent);

		//	
		//COLLECT INFO	
		//
		int[] frequenciesPolaritiesNeighboring = new int[COUNT_POLARITIES]; //These keep track of what the neighbors currently hold
		int[] frequenciesPolaritiesNeighboringCross = new int[COUNT_POLARITIES]; //These keep track of how many of each color appear in the cell above, below, left and right
		int countNeighborsCross = 0;
		int indexPolarityNeighboring;
		Cell[] neighbors = Board.getNeighbors(board.getPolarityLayer(), indexRow, indexColumn);
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
		
		int indexPolarity;
		
		//RULE #6
		//Look at the agents array of memory
		//If any one color is too apparent
		//MODIFICATION #10
		//Updated 7/18 by Morgan Might
		//Now the constraints can be altered based on the dominant polarity chosen
		if(board.getGui().getEquilibriumMode()) {
			LoopRule6:
			for(indexPolarity = 0; indexPolarity < COUNT_POLARITIES; indexPolarity++) {
				if(agent.getPolarityCount(indexPolarity) >= SwarmAgent.THRESHOLD_POLARITY_DOMINANT_MEMORY) {
					int indexPolarityDominant = board.getGui().getDominantPolarity();
					
					boolean metThresholdNeighboring = frequenciesPolaritiesNeighboring[indexPolarityDominant] >= SwarmAgent.THRESHOLD_POLARITY_DOMINANT_NEIGHBORING;
					int countMemoryPolarity;
					int countMemoryPolarityDominant = agent.getPolarityCount(indexPolarityDominant);
					int sumMemoryPolarities = 0;
					for(indexPolarity = 0; indexPolarity < COUNT_POLARITIES; indexPolarity++) {
						if(indexPolarity != indexPolarityDominant) {
							if((countMemoryPolarity = agent.getPolarityCount(indexPolarity)) > countMemoryPolarityDominant) {
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
						int frequencyPolarityMaximum = SwarmAgent.THRESHOLD_POLARITY_DOMINANT_NEIGHBORING;
						ArrayList<Integer> indicesPolaritiesFrequent = new ArrayList<Integer>(COUNT_POLARITIES - 1);
						for(indexPolarity = 0; indexPolarity < COUNT_POLARITIES; indexPolarity++) {
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
		for(indexPolarity = 0; indexPolarity < COUNT_POLARITIES; indexPolarity++) {
			if(frequenciesPolaritiesNeighboringCross[indexPolarity] >= thresholdPolaritiesNeighboringCross) {
				return indexPolarity;
			}
		}
		
		int indexPolarityCurrent = board.getBaseLayer()[indexRow][indexColumn].getState();
		
		for(indexPolarity = 0; indexPolarity < COUNT_POLARITIES; indexPolarity++) {
			if(frequenciesPolaritiesNeighboring[indexPolarity] == 0) {
				if(indexPolarity == indexPolarityCurrent) {
					return (indexPolarity + 1 + ThreadLocalRandom.current().nextInt(COUNT_POLARITIES - 1)) % COUNT_POLARITIES;
				}
				
				return indexPolarityCurrent;
			}
		}
		
		for(indexPolarity = 0; indexPolarity < COUNT_POLARITIES; indexPolarity++) {
			if(frequenciesPolaritiesNeighboringCross[indexPolarity] == 0) {
				if(indexPolarity == indexPolarityCurrent) {
					return (indexPolarity + 1 + ThreadLocalRandom.current().nextInt(COUNT_POLARITIES - 1)) % COUNT_POLARITIES;
				}
				
				return indexPolarityCurrent;
			}
		}
		
		for(indexPolarity = 0; indexPolarity < COUNT_POLARITIES; indexPolarity++) {
			if(frequenciesPolaritiesNeighboringCross[indexPolarity] == 2) {
				return indexPolarity;
			}
		}
		
		return board.getBaseLayer()[indexRow][indexColumn].getState();
	}
}