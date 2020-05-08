package swarm;

import strategies.Strategy;
import strategies.StrategyPolarityAlternator;
import strategies.StrategyPolarityEdges;
import strategies.StrategyPolarityEqualizer;
import strategies.StrategyPolarityHybridDiagonalsEqualizer;
import strategies.StrategyPolarityPatternDiagonals;
import strategies.StrategyPolaritySignal;
import strategies.StrategyRandom;

public class RegisterDefinitionsAgent {
	private static final DefinitionAgent[] REGISTRY = {new DefinitionAgentStandard(), new DefinitionAgentSpecial(), new DefinitionAgentAlternator(), new DefinitionAgentEdger(), new DefinitionAgentEqualizer(), new DefinitionAgentRandomStandardEqualizer(), new DefinitionAgentHybridDiagonalsEqualizer(), new DefinitionAgentSignalTransmitter(), new DefinitionAgentSignalReceiver()};
	private static final int SIZE = REGISTRY.length;
	private static String[] entries;
	
	public static int getSize() {
		return SIZE;
	}
	
	public static String[] getEntries() {
		if(entries == null) {
			entries = new String[SIZE];
			
			for(int indexEntry = 0; indexEntry < entries.length; indexEntry++) {
				entries[indexEntry] = REGISTRY[indexEntry].getName().toLowerCase();
			}
		}
		
		return entries;
	}
	
	public static DefinitionAgent[] getRegistry() {
		return REGISTRY;
	}
	
	private RegisterDefinitionsAgent() {
		
	}
	
	public static abstract class DefinitionAgent {
		public boolean isSlow() {
			return false;
		}
		
		public int getMode() {
			return 0;
		}
		
		public abstract String getName();
		public abstract Motion getMotion();
		public abstract Strategy getStrategy();
	}
	
	private static class DefinitionAgentStandard extends DefinitionAgent {
		private static final String NAME = "Standard";
		private static final Motion MOTION = MotionFree.get();
		
		@Override
		public String getName() {
			return NAME;
		}
		
		@Override
		public Motion getMotion() {
			return MOTION;
		}
		
		@Override
		public Strategy getStrategy() {
			return null;
		}
	}
	
	private static class DefinitionAgentSpecial extends DefinitionAgent {
		private static final String NAME = "Special";
		private static final Motion MOTION = MotionFree.get();
		
		@Override
		public String getName() {
			return NAME;
		}
		
		@Override
		public Motion getMotion() {
			return MOTION;
		}
		
		@Override
		public Strategy getStrategy() {
			return null;
		}
	}
	
	private static class DefinitionAgentAlternator extends DefinitionAgent {
		private static final String NAME = "Alternator";
		private static final Motion MOTION = MotionOrthogonal.get();
		private static final Strategy STRATEGY = StrategyPolarityAlternator.get();
		
		@Override
		public String getName() {
			return NAME;
		}
		
		@Override
		public Motion getMotion() {
			return MOTION;
		}
		
		@Override
		public Strategy getStrategy() {
			return STRATEGY;
		}
	}
	
	private static class DefinitionAgentEdger extends DefinitionAgent {
		private static final String NAME = "Edger";
		private static final Motion MOTION = MotionEdge.get();
		private static final Strategy STRATEGY = StrategyPolarityEdges.get();
		
		@Override
		public String getName() {
			return NAME;
		}
		
		@Override
		public Motion getMotion() {
			return MOTION;
		}
		
		@Override
		public Strategy getStrategy() {
			return STRATEGY;
		}
	}
	
	private static class DefinitionAgentEqualizer extends DefinitionAgent {
		private static final String NAME = "Equalizer";
		private static final Motion MOTION = MotionFree.get();
		private static final Strategy STRATEGY = StrategyPolarityEqualizer.get();
		
		@Override
		public String getName() {
			return NAME;
		}
		
		@Override
		public Motion getMotion() {
			return MOTION;
		}
		
		@Override
		public Strategy getStrategy() {
			return STRATEGY;
		}
	}
	
	private static class DefinitionAgentRandomStandardEqualizer extends DefinitionAgent {
		private static final String NAME = "Diagonals/Equalizer";
		private static final Motion MOTION = MotionFree.get();
		private static final Strategy STRATEGY = new StrategyRandom(new Strategy[]{StrategyPolarityPatternDiagonals.get(), StrategyPolarityEqualizer.get()}, new int[] {2, 1});
		
		@Override
		public String getName() {
			return NAME;
		}
		
		@Override
		public Motion getMotion() {
			return MOTION;
		}
		
		@Override
		public Strategy getStrategy() {
			return STRATEGY;
		}
	}
	
	private static class DefinitionAgentHybridDiagonalsEqualizer extends DefinitionAgent {
		private static final String NAME = "Diagonals-Equalizer";
		private static final Motion MOTION = MotionFree.get();
		private static final Strategy STRATEGY = StrategyPolarityHybridDiagonalsEqualizer.get();
		
		@Override
		public String getName() {
			return NAME;
		}
		
		@Override
		public Motion getMotion() {
			return MOTION;
		}
		
		@Override
		public Strategy getStrategy() {
			return STRATEGY;
		}
	}
	
	private static abstract class DefinitionAgentSignal extends DefinitionAgent {
		private static final Strategy STRATEGY = StrategyPolaritySignal.get();
		
		@Override
		public int getMode() {
			return 1;
		}
		
		@Override
		public Strategy getStrategy() {
			return STRATEGY;
		}
	}
	
	private static class DefinitionAgentSignalTransmitter extends DefinitionAgentSignal {
		private static final String NAME = "Transmitter";
		private static final Motion MOTION = MotionStationary.get();

		@Override
		public String getName() {
			return NAME;
		}
		
		@Override
		public Motion getMotion() {
			return MOTION;
		}
	}
	
	private static class DefinitionAgentSignalReceiver extends DefinitionAgentSignal {
		private static final String NAME = "Receiver";
		private static final Motion MOTION = MotionFree.get();
		
		@Override
		public String getName() {
			return NAME;
		}
		
		@Override
		public Motion getMotion() {
			return MOTION;
		}
	}
}