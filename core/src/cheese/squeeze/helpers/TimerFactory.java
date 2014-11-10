package cheese.squeeze.helpers;

import java.util.ArrayList;

import cheese.squeeze.game.GameState;
import cheese.squeeze.game.ReportStatus;

public class TimerFactory {
	
	
	private static ArrayList<Timer> timers = new ArrayList<Timer>();

	public static Timer getNewTimer(GameState state) {
		Timer t = new Timer(state);
		timers.add(t);
		return t;
	}
	
	public static ArrayList<Timer> getTimers() {
		return new ArrayList<Timer>(timers);
	}
	
	public static Timer getRunningTimer(ReportStatus state) {
		for(Timer t : timers) {
			if (t.getState().equals(state) && t.isRunning()) {
				return t;
			}
		}
		return null;
	}
}
