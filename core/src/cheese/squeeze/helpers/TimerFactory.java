package cheese.squeeze.helpers;

import java.util.ArrayList;
import java.util.Iterator;

import cheese.squeeze.game.GameState;
import cheese.squeeze.game.Level;
import cheese.squeeze.game.ReportStatus;

public class TimerFactory {
	
	
	private static ArrayList<Timer> timers = new ArrayList<Timer>();

	public static Timer getNewTimer(ReportStatus state) {
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

	public static void stopAll() {
		for(Timer t : timers) {
			if(t.isRunning()) {
				t.stop();
			}
			
		}
	}

	public static void pauseAll() {
		for(Timer t : timers) {
			t.pause();
		}
	}
	
	public static void resumeAll() {
		for(Timer t : timers) {
			t.resume();
		}
	}

	public static void clear() {
		timers = new ArrayList<Timer>();
	}

	public static void pauseAll(Level currentLevel) {
		for(Timer t : timers) {
			if(t.getState().getLevel().equals(currentLevel)) {
				t.pause();
			}
			
		}
		
	}

	public static void remove(ReportStatus state) {
		//FIXME buggy
		Iterator<Timer> itr = timers.iterator();
		while(itr.hasNext()) {
			if (itr.next().getState().equals(state)) {
				itr.remove();
			}
		}
		
	}
}
