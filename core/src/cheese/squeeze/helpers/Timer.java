package cheese.squeeze.helpers;

import java.util.concurrent.TimeUnit;

import cheese.squeeze.game.GameState;
import cheese.squeeze.game.ReportStatus;

public class Timer {
	
	
	private ReportStatus state;
	private long startTime;
	private long stopTime;
	private long totalTime;
	private boolean isStopped = false;
	
	
	protected Timer(ReportStatus state) {
		this.state = state;
		this.startTime = 0;
		this.stopTime = 0;
		this.totalTime = 0;
	}
	
	public void start() {
		startTime = System.nanoTime();
	}
	
	public void pauze() {
		//TODO
		
	}
	
	public void resume() {
		//TODO
	}
	
	public void stop() {
		if(!isStopped) {
			isStopped = true;
			stopTime = System.nanoTime();
			totalTime = stopTime - startTime;
		}

	}
	
	public long getTime() {
		return 0;
	}
	
	
	@Override
	public String toString() {
		return String.valueOf(this.getSeconds())+ " sec of "+ state.toString() + "state." ;
	}
	
	public boolean isRunning() {
		return !isStopped;
	}
	
	public long getSeconds() {
		return TimeUnit.NANOSECONDS.toSeconds(this.totalTime);
	}
	
	public long getMinutes() {
		return TimeUnit.NANOSECONDS.toMinutes(this.totalTime);
	}
	
	public ReportStatus getState() {
		return this.state;
	}

}
