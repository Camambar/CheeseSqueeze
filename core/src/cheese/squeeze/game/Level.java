package cheese.squeeze.game;


import cheese.squeeze.gameObjects.Cheese;
import cheese.squeeze.gameObjects.HorizontalLine;
import cheese.squeeze.gameObjects.Trap;
import cheese.squeeze.gameObjects.VerticalLine;


public enum Level {
	
        LEVEL4(1f,new HorizontalLine[]{}
			,new VerticalLine[]{new VerticalLine(new Cheese(5)),new VerticalLine(new Trap()),new VerticalLine(new Trap()),new VerticalLine(new Trap())}
			,new int[]{4,2,3,2,1},1),
			
        LEVEL3(1f,new HorizontalLine[]{}
			,new VerticalLine[]{new VerticalLine(new Trap()),new VerticalLine(new Trap()),new VerticalLine(new Cheese(4)),new VerticalLine(new Trap())}
			,new int[]{1,2,3,4},1,LEVEL4),
	
	
        LEVEL2(.8f,new HorizontalLine[]{}
			,new VerticalLine[]{new VerticalLine(new Trap()),new VerticalLine(new Trap()),new VerticalLine(new Cheese(4))}
			,new int[]{1,2,1,3},1,LEVEL3),
	
		LEVEL1(.1f,new HorizontalLine[]{}
			,new VerticalLine[]{new VerticalLine(new Trap()),new VerticalLine(new Cheese(4)),new VerticalLine(new Trap())}
			,new int[]{1,2,3,4},2,LEVEL2);
        /*
        LEVEL1(.6f,new HorizontalLine[]{}
		,new VerticalLine[]{new VerticalLine(new Trap()),new VerticalLine(new Cheese(3)),new VerticalLine(new Trap())}
		,new int[]{1,2,3},1,LEVEL2);
         */

	
	private int amountTraps;
	private int amountGoals;
	private HorizontalLine[] hlines;
	private int amountVlines;
	private float speed;
	private Level nextLevel;
	private int nbMouse;
	private boolean randomLines = true;
	private VerticalLine[] vlines;
	private int[] mouseOnLine;
	private float multipl = 1; //TODO can change
	
	
	Level(float speed, HorizontalLine[] hlines,VerticalLine[] vlines, int[] mouseOnLine,int nbMouse) {
        this.amountVlines = vlines.length;
        this.hlines = hlines;
        this.speed = speed;
        this.nextLevel = null;
        this.nbMouse = nbMouse;
        this.randomLines = false;
        this.vlines = vlines;
        this.mouseOnLine = mouseOnLine;
	}
	

	Level(float speed, HorizontalLine[] hlines,VerticalLine[] vlines,int[] mouseOnLine, int nbMouse,Level nextLevel) {
        this.amountVlines = vlines.length;
        this.hlines = hlines;
        this.speed = speed;
        this.nextLevel = null;
        this.nbMouse = nbMouse;
        this.randomLines = false;
        this.vlines = vlines;
        this.nextLevel = nextLevel;
        this.mouseOnLine = mouseOnLine;
	}
	
	Level(int nbTraps,int nbGoals,int nbVlines,float speed, HorizontalLine[] hlines, int nbMouse) {
        this.amountTraps = nbTraps;
        this.amountGoals = nbGoals;
        this.amountVlines = nbVlines;
        this.hlines = hlines;
        this.speed = speed;
        this.nextLevel = null;
        this.nbMouse = nbMouse;
    }

	Level(int nbTraps,int nbGoals,int nbVlines,float speed, HorizontalLine[] hlines,int nbMouse,Level nextLevel) {
        this.amountTraps = nbTraps;
        this.amountGoals = nbGoals;
        this.amountVlines = nbVlines;
        this.hlines = hlines;
        this.speed = speed;
        this.nextLevel = nextLevel;
        this.nbMouse = nbMouse;
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

	public boolean isRandomLines() {
		return randomLines;
	}

	public VerticalLine[] getVlines() {
		return vlines;
	}
	
	public int getNbMouse() {
		return nbMouse;
	}

	public Level getNextLevel() {
		return nextLevel;
	}


	public int[] getMouseLine() {
		return mouseOnLine;
	}


	public float getMultip() {
		return multipl;
	}
	
}
