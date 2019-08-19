package strategies;

import gui.Board;
import swarm.SwarmAgent;

public abstract class Strategy {
	private static final int CAPACITY_MEMORY = 0;
	
	public int getMemoryCapacity() {
		return CAPACITY_MEMORY;
	}
	
	public abstract void logic(Board board, SwarmAgent agent);
}
