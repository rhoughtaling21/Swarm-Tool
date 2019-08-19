package strategies;

import gui.Board;
import swarm.SwarmAgent;

public class StrategyPolaritySignal extends StrategyPolarity {
	SwarmAgent[] agentsSignalTransmitter;
	
	public StrategyPolaritySignal(SwarmAgent[] agentsSignalTransmitter) {
		this.agentsSignalTransmitter = agentsSignalTransmitter;
	}
	
	@Override
	public int determineDesiredPolarity(Board board, SwarmAgent agent) {
		int indexRowAgent = board.calculateAgentRow(agent);
		int indexColumnAgent = board.calculateAgentColumn(agent);
		
		for(SwarmAgent agentSignalTransmitter : agentsSignalTransmitter) {
			if(Math.hypot(indexRowAgent - board.calculateAgentRow(agentSignalTransmitter), indexColumnAgent - board.calculateAgentColumn(agentSignalTransmitter)) <= agentSignalTransmitter.getSignalRange()) {
				return 0;
			}
		}
		
		
		return 1;
	}
}