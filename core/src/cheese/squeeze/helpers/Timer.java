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
	private boolean isPause =false;
	
	
	public Timer(ReportStatus state) {
		this.state = state;
		this.startTime = 0;
		this.stopTime = 0;
		this.totalTime = 0;
	}
	
	public void start() {
		startTime = System.nanoTime();
	}
	
	public void pause() {
		if (!isStopped && !isPause) {
			stopTime = System.nanoTime();
			totalTime = totalTime + (stopTime - startTime);
			isPause = true;
		}
		System.out.println(toString() +" pauzed");
		
	}
	
	public void resume() {
		if(!isStopped) {
			startTime = System.nanoTime();
			stopTime = 0;
		}
		System.out.println(toString() +" resumed");
	}
	
	public void stop() {
		if(!isStopped && !isPause) {
			isStopped = true;
			stopTime = System.nanoTime();
			totalTime = totalTime + (stopTime - startTime);
		}
		else if (isPause) {
			isStopped = true;
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
		return (long) (totalTime / 1000000000.0);
		
	}
	
	public long getMinutes() {
		return TimeUnit.NANOSECONDS.toMinutes(this.totalTime);
	}
	
	public ReportStatus getState() {
		return this.state;
	}

	public long getMiliSeconds() {
		return TimeUnit.NANOSECONDS.toMillis(this.totalTime);
	}
}
