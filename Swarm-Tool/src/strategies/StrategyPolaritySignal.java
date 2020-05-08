package strategies;

import gui.Board;
import swarm.SwarmAgent;

public class StrategyPolaritySignal extends StrategyPolarity {
	private static final StrategyPolaritySignal STRATEGY = new StrategyPolaritySignal();
	
	public static StrategyPolaritySignal get() {
		return STRATEGY;
	}
	
	private StrategyPolaritySignal() {
		
	}
	
	@Override
	public int determineDesiredPolarity(Board board, SwarmAgent agent) {
		int indexRowAgent = board.calculateAgentRow(agent);
		int indexColumnAgent = board.calculateAgentColumn(agent);
		
		for(SwarmAgent agentSignalTransmitter : board.getSignalTransmitterAgents()) {
			if(Math.hypot(indexRowAgent - board.calculateAgentRow(agentSignalTransmitter), indexColumnAgent - board.calculateAgentColumn(agentSignalTransmitter)) <= agentSignalTransmitter.getSignalRange()) {
				return 0;
			}
		}
		
		return 1;
	}
}