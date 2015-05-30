package chess;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class Results {

	private Map<Integer, ChessBoardLayout> completedLayouts;
	private int attempts;

	public Results() {
		completedLayouts = new HashMap<Integer, ChessBoardLayout>();
		attempts = 0;
	}

	public void addLayout(ChessBoardLayout chessBoardLayout) {
		attempts++;
		chessBoardLayout.setCompletionAttempt(attempts);
		Integer hash = Integer.valueOf(chessBoardLayout.hashCode());
		ChessBoardLayout previous = completedLayouts.get(hash);

		if (previous == null) {
			completedLayouts.put(hash, chessBoardLayout);
		}
	}
	
	public void printLayouts(PrintStream out) {
		int counter = 0;
		for(ChessBoardLayout chessBoardLayout : completedLayouts.values()) {
			out.println(chessBoardLayout.getLayoutText());
			if(counter++ > 9)
				break;
		}
	}

	public int getNumUniqueLayouts() {
		return completedLayouts.size();
	}

	public int getNumAttempts() {
		return attempts;
	}

}
