package strategies;

import agents.SwarmAgent;
import gui.Board;

public abstract class Strategy {
	public abstract void logic(Board board, SwarmAgent agent);
}
