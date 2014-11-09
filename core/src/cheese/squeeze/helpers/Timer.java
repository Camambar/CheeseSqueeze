package cheese.squeeze.helpers;

import cheese.squeeze.game.GameState;

public class Timer {
	
	
	private GameState state;
	private long startTime;
	private long stopTime;
	private long totalTime;
	
	public Timer(GameState state) {
		this.state = state;
	}
	
	public void start() {
		startTime = System.nanoTime();
	}
	
	public void pauze() {
		stopTime = System.nanoTime();
	}
	
	public void resume() {
		
	}
	
	public void stop() {
		
	}
	
	public long getTime() {
		return 0;
	}
	
	
	@Override
	public String toString() {
		return null;
	}
	
	

}
