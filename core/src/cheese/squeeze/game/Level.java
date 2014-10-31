package cheese.squeeze.game;

import java.util.ArrayList;

import cheese.squeeze.gameObjects.HorizontalLine;

public enum Level {
	
	LEVEL2(4,1,5,0.6f,new HorizontalLine[]{}), LEVEL1(2,1,3,0.4f,new HorizontalLine[]{},LEVEL2);
	
	private int amountTraps;
	private int amountGoals;
	private HorizontalLine[] hlines;
	private int amountVlines;
	private float speed;
	private Level nextLevel;
	
	public Level getNextLevel() {
		return nextLevel;
	}
	
	Level(int nbTraps,int nbGoals,int nbVlines,float speed, HorizontalLine[] hlines) {
        this.amountTraps = nbTraps;
        this.amountGoals = nbGoals;
        this.amountVlines = nbVlines;
        this.hlines = hlines;
        this.speed = speed;
        this.nextLevel = null;
    }

	Level(int nbTraps,int nbGoals,int nbVlines,float speed, HorizontalLine[] hlines,Level nextLevel) {
        this.amountTraps = nbTraps;
        this.amountGoals = nbGoals;
        this.amountVlines = nbVlines;
        this.hlines = hlines;
        this.speed = speed;
        this.nextLevel = nextLevel;
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
