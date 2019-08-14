package strategies;

import agents.SwarmAgent;
import agents.SwarmAgentSignalTransmitter;
import gui.Board;

public class StrategyPolaritySignal extends StrategyPolarity {
	SwarmAgentSignalTransmitter[] agentsSignalTransmitter;
	
	public StrategyPolaritySignal(SwarmAgentSignalTransmitter[] agentsSignalTransmitter) {
		this.agentsSignalTransmitter = agentsSignalTransmitter;
	}
	
	@Override
	public int determineDesiredPolarity(Board board, SwarmAgent agent) {
		int indexRowAgent = board.calculateAgentRow(agent);
		int indexColumnAgent = board.calculateAgentColumn(agent);
		
		for(SwarmAgentSignalTransmitter agentSignalTransmitter : agentsSignalTransmitter) {
			if(Math.hypot(indexRowAgent - board.calculateAgentRow(agentSignalTransmitter), indexColumnAgent - board.calculateAgentColumn(agentSignalTransmitter)) <= agentSignalTransmitter.getSignalRange()) {
				return 0;
			}
		}
		
		
		return 1;
	}
}