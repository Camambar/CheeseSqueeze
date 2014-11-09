package cheese.squeeze.helpers;

import java.util.ArrayList;

public class TimerFactory {
	
	
	ArrayList<Timer> timers = new ArrayList<Timer>();
	
	public TimerFactory() {
		
	}

	public Timer getNewTimer() {
		return timers.get(0);
	}
	
}
