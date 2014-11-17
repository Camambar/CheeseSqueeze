package cheese.squeeze.game;


import com.badlogic.gdx.math.Vector2;

import cheese.squeeze.gameObjects.Cheese;
import cheese.squeeze.gameObjects.HorizontalLine;
import cheese.squeeze.gameObjects.Trap;
import cheese.squeeze.gameObjects.VerticalLine;


public enum Level {
	
	/*
	 * MAKING LEVELS;
	 * -Leave the nothing at the top.
	 * -first argument is the speed of the mouse
	 * -second argument is the positions of the preset horizontal lines
	 * 		-> x-coord:the row positions from 1 till #vlines -1 
	 * 		-> y-coord:the position in the row from 1 till 15
	 * 		e.g.:	- new Vector2(1,1) -> will place the hline in the FIRST row,on the FIRST place.
	 * 				- new Vector2(2,1) -> will place the hline in the SECOND row,on the FIRST place.
	 * 				- new Vector2(1,2) -> will place the hline in the FIRST row,on the SECOND place.
	 * TODO complete the list
	 */				
	
	NOTHING(),
	
	LEVEL12(0.2f,new Vector2[]{}
	,new VerticalLine[]{new VerticalLine(new Trap()),new VerticalLine(new Trap()),new VerticalLine(new Trap()),new VerticalLine(new Cheese(5)),new VerticalLine(new Trap()),new VerticalLine(new Trap()),new VerticalLine(new Trap())}
	,new int[]{1,3,6,7,6},4,false),
	
	LEVEL11(0.4f,new Vector2[]{}
		,new VerticalLine[]{new VerticalLine(new Trap()),new VerticalLine(new Cheese(5)),new VerticalLine(new Trap()),new VerticalLine(new Trap()),new VerticalLine(new Trap())}
		,new int[]{1,3,5,2,4},3,LEVEL12,false),
		
		
	LEVEL10(0.4f,new Vector2[]{}
		,new VerticalLine[]{new VerticalLine(new Trap()),new VerticalLine(new Cheese(5)),new VerticalLine(new Trap()),new VerticalLine(new Trap()),new VerticalLine(new Trap())}
		,new int[]{2,3,5,1,4},2,LEVEL11,false),
		
		
	LEVEL9(0.4f,new Vector2[]{}
		,new VerticalLine[]{new VerticalLine(new Trap()),new VerticalLine(new Trap()),new VerticalLine(new Cheese(5)),new VerticalLine(new Trap()),new VerticalLine(new Trap())}
		,new int[]{1,5,2,4,3},2,LEVEL10,false),
		
	LEVEL8(0.4f,new Vector2[]{}
		,new VerticalLine[]{new VerticalLine(new Trap()),new VerticalLine(new Trap()),new VerticalLine(new Trap()),new VerticalLine(new Trap()),new VerticalLine(new Cheese(5))}
		,new int[]{3,1,2,5,2},2,LEVEL9,false),
		
		
	LEVEL7(0.6f,new Vector2[]{}
		,new VerticalLine[]{new VerticalLine(new Cheese(5)),new VerticalLine(new Trap()),new VerticalLine(new Trap()),new VerticalLine(new Trap()),new VerticalLine(new Trap())}
		,new int[]{4,2,5,4,3},1,LEVEL8,false),
		
	
        LEVEL6(0.6f,new Vector2[]{new Vector2(1,3),new Vector2(1,4),new Vector2(2,2)}
		,new VerticalLine[]{new VerticalLine(new Trap()),new VerticalLine(new Trap()),new VerticalLine(new Trap()),new VerticalLine(new Cheese(5))}
		,new int[]{1,4,3,4,2},1,LEVEL7,false),
			
        LEVEL5(0.6f,new Vector2[]{}
		,new VerticalLine[]{new VerticalLine(new Cheese(5)),new VerticalLine(new Trap()),new VerticalLine(new Trap()),new VerticalLine(new Trap())}
		,new int[]{4,2,3,2,1},1,LEVEL6,false),
	
	
        LEVEL4(0.6f,new Vector2[]{}
		,new VerticalLine[]{new VerticalLine(new Trap()),new VerticalLine(new Trap()),new VerticalLine(new Cheese(5)),new VerticalLine(new Trap())}
		,new int[]{1,2,3,4,1},1,LEVEL5,false),

			
		LEVEL3(0.8f,new Vector2[]{new Vector2(1,7),new Vector2(1,1)}
		,new VerticalLine[]{new VerticalLine(new Trap()),new VerticalLine(new Trap()),new VerticalLine(new Cheese(5))}
		,new int[]{2,3,1,2,1},1,LEVEL4,false),
	
		LEVEL2(0.72f,new Vector2[]{new Vector2(1,15)}
		,new VerticalLine[]{new VerticalLine(new Cheese(5)),new VerticalLine(new Trap()),new VerticalLine(new Trap())}
		,new int[]{3,2,3,1,2},1,LEVEL3,false),
        
        LEVEL1(0.5f,new Vector2[]{}
		,new VerticalLine[]{new VerticalLine(new Trap()),new VerticalLine(new Cheese(5)),new VerticalLine(new Trap())}
		,new int[]{1,2,3,1,2},1,LEVEL2,true);
        
	
	private int amountTraps;
	private int amountGoals;
	private Vector2[] hlines;
	private int amountVlines;
	private float speed;
	private Level nextLevel;
	private int nbMouse;
	private boolean randomLines = true;
	private VerticalLine[] vlines;
	private int[] mouseOnLine;
	private float multipl = 1; //TODO can change
	private boolean tutorial;
	
	
	Level() {
		
	}
	
	Level(float speed, Vector2[] hlines,VerticalLine[] vlines, int[] mouseOnLine,int nbMouse, boolean tutorial) {
        this.amountVlines = vlines.length;
        this.hlines = hlines;
        this.speed = speed;
        this.nextLevel = null;
        this.nbMouse = nbMouse;
        this.randomLines = false;
        this.vlines = vlines;
        this.mouseOnLine = mouseOnLine;
        this.tutorial = tutorial;
	}
	

	Level(float speed, Vector2[] hlines,VerticalLine[] vlines,int[] mouseOnLine, int nbMouse,Level nextLevel, boolean tutorial) {
        this.amountVlines = vlines.length;
        this.hlines = hlines;
        this.speed = speed;
        this.nextLevel = null;
        this.nbMouse = nbMouse;
        this.randomLines = false;
        this.vlines = vlines;
        this.nextLevel = nextLevel;
        this.mouseOnLine = mouseOnLine;
        this.tutorial = tutorial;
	}
	
	Level(int nbTraps,int nbGoals,int nbVlines,float speed, Vector2[] hlines, int nbMouse,boolean tutorial) {
        this.amountTraps = nbTraps;
        this.amountGoals = nbGoals;
        this.amountVlines = nbVlines;
        this.hlines = hlines;
        this.speed = speed;
        this.nextLevel = null;
        this.nbMouse = nbMouse;
        this.tutorial = tutorial;
    }

	Level(int nbTraps,int nbGoals,int nbVlines,float speed, Vector2[] hlines,int nbMouse,Level nextLevel,boolean tutorial) {
        this.amountTraps = nbTraps;
        this.amountGoals = nbGoals;
        this.amountVlines = nbVlines;
        this.hlines = hlines;
        this.speed = speed;
        this.nextLevel = nextLevel;
        this.nbMouse = nbMouse;
        this.tutorial = tutorial;
    }


	public int getAmountTraps() {
		return amountTraps;
	}

	public int getAmountGoals() {
		return amountGoals;
	}

	public Vector2[] getHlines() {
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

	public boolean isTutorial() {
		return tutorial;
	}
	
	public void setTutorial(boolean t) {
		this.tutorial = t;
	}
	
}
