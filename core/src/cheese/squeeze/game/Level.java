package cheese.squeeze.game;

import java.util.ArrayList;

import cheese.squeeze.gameObjects.HorizontalLine;

public enum Level {
	
	LEVEL1(2,1,3,0.4f,new HorizontalLine[]{}),LEVEL2(4,1,5,0.6f,new HorizontalLine[]{});
	
	private int amountTraps;
	private int amountGoals;
	private HorizontalLine[] hlines;
	private int amountVlines;
	private float speed;
	
	Level(int nbTraps,int nbGoals,int nbVlines,float speed, HorizontalLine[] hlines) {
        this.amountTraps = nbTraps;
        this.amountGoals = nbGoals;
        this.amountVlines = nbVlines;
        this.hlines = hlines;
        this.speed = speed;
    }

	public int getAmountTraps() {
		return amountTraps;
	}

	public int getAmountGoals() {
		return amountGoals;
	}

	public HorizontalLine[] getHlines() {
		return hlines;
	}

	public int getAmountVlines() {
		return amountVlines;
	}

	public float getSpeed() {
		return speed;
	}

}
