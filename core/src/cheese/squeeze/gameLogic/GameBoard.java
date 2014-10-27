package cheese.squeeze.gameLogic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import cheese.squeeze.gameObjects.Cheese;
import cheese.squeeze.gameObjects.HorizontalLine;
import cheese.squeeze.gameObjects.Mouse;
import cheese.squeeze.gameObjects.Trap;
import cheese.squeeze.gameObjects.VerticalLine;
import cheese.squeeze.helpers.AssetLoader;
import cheese.squeeze.tweenAccessors.SoundAccessor;

public class GameBoard {

	
	private List<VerticalLine> vlines;
	private List<HorizontalLine> hlines;
	private List<Cheese> goals;
	private List<Trap> traps;
	private OrthographicCamera cam;
	private HorizontalLine gesturedLine;
	private Vector2 beginPosition;
	public final int beginRadius = 5;
	private float width;
	private float height;
	private List<Vector2> startPositions;
	private List<Mouse> mice;
	private static int MAX_HLINES = 20;
	private float step;
	private float start;
	private float end;
	
	

	public GameBoard(int amountVLines,int amountGoals,int amountTraps,float width, float height) throws Exception {
		if(amountGoals+amountTraps != amountVLines) {
			throw new Exception("The amount of lines must be equal to the amount of traps and goals");
		}
		this.width = width;
		this.height = height;
		
		
		cam = new OrthographicCamera();
		cam.setToOrtho(true,width, height);
		
		//all the lines
		makeVerticalLines(amountVLines);
		gesturedLine = new HorizontalLine();
		hlines = new ArrayList<HorizontalLine>();
		
		//mouse stuff
		makeMice();
		
		//trap stuff goal stuff
		makeTrapsGoals(amountTraps,amountGoals);

		

	}
	
	private void makeTrapsGoals(int amountTraps, int amountGoals) {
		this.goals = new ArrayList<Cheese>();
		this.traps = new ArrayList<Trap>();
		boolean set = false;
		for(VerticalLine vl : vlines) {
			double rand = Math.random();
			
			if(goals.size() < amountGoals && rand <= 0.5){
				goals.add(new Cheese(vl.getPoint2()));
				set = true;
			}
			else if(traps.size() < amountTraps){
				traps.add(new Trap(vl.getPoint2()));
				set = true;
			}
			else if(!set && goals.size() < amountGoals){
				goals.add(new Cheese(vl.getPoint2()));
				set = true;
			}
			set = false;
			
		}
		
	}

	private void makeMice() {
		mice = new ArrayList<Mouse>();
		ArrayList<Vector2> path= new ArrayList<Vector2>();
		//path.add(new Vector2(68,15));
		//path.add(new Vector2(68,128));
		Mouse mouse = new Mouse(startPositions.get(0).x,startPositions.get(0).y,path,10);
		mice.add(mouse);	
	}

	private void makeVerticalLines(int amountVLines) {
		vlines = new ArrayList<VerticalLine>();
		float totw = (width-20)/(amountVLines-1);
		for(int i=0;i<amountVLines;i++) {
			vlines.add(new VerticalLine(15,height-20,10+(totw*i)));
		}
		this.step = (vlines.get(0).getY2() - vlines.get(0).getY1())/this.MAX_HLINES;
		this.start = vlines.get(0).getY1();
		this.end = vlines.get(0).getY2();
		startPositions();
	}
	
	public List<VerticalLine> getVLines() {
		return vlines;
	}
	
	public void addHLine(HorizontalLine line) {
		//modifyLocation(line);
		SoundAccessor.play(AssetLoader.chalk);
		long id = AssetLoader.chalk.play();
		AssetLoader.chalk.setLooping(id, false);
		hlines.add(line);
		
	}

	public List<HorizontalLine> getHLines() {
		return hlines;
	}
	
	/**
	 * A horizontal line consists of two points, given the two points, the
	 * closest vertical lines are searched and the location of the points is modified to
	 * the position of the corresponding vertical lines.
	 * 
	 * @param l the horizontal line, that is modified after executing the method.
	 */
	public void fitHorizontalLineBetweenVertivalLines(HorizontalLine l) {
		
		//TODO backtrack algo need optimization
		//calculating the closest bars
		float x1;
		float x2;
		if(l.getX1() < l.getX2()) {
			x1 = l.getX1();
			x2 = l.getX2();
		}
		else if (l.getX1() == l.getX2()) {
			x2 = (float) (l.getX1()+0.1);
			x1 = l.getX2();
		}
		else {
			x2 = l.getX1();
			x1 = l.getX2();
		}
		float minDistLeft = width;
		float minDistRight = width;
		VerticalLine vlLeft = null;
		VerticalLine vlRight = null;
		Iterator<VerticalLine> itr = vlines.iterator();
		while(itr.hasNext()) {
			VerticalLine current = itr.next();
			if(Math.abs(current.getX1() - x1) < minDistLeft) {
				
				if(current != vlRight) {
					minDistLeft = Math.abs(current.getX1() - x1);
					vlLeft = current;
					itr = vlines.iterator();
				}
				else if (minDistRight > Math.abs(current.getX1() - x1)) {
					minDistLeft = Math.abs(current.getX1() - x1);
					vlLeft = current;
					minDistRight = width;
					vlRight = null;
					itr= vlines.iterator();
				}
				
			}
			if(Math.abs(current.getX1() - x2) < minDistRight) {
				
				if(current != vlLeft) {
					minDistRight = Math.abs(current.getX1() - x2);
					vlRight = current;
					itr = vlines.iterator();
				}
				else if ( Math.abs(current.getX1() - x2) < minDistLeft) {
					minDistRight = Math.abs(current.getX1() - x2);
					vlRight = current;
					minDistLeft = width;
					vlLeft = null;
					itr= vlines.iterator();
				}
				
			}
		}
		
		float multiple = multipleOfPosition(l.getY1());
		if(hasElementWithSamenYCoordinate(multiple)) {
			//TODO make this a bit nicer
			//If there is a point with an other y coord on the other side 
			//l is set to y coord 0,0 in the same row
			l.setPoint1(new Vector2(vlLeft.getX1(),-10));
			l.setPoint2(new Vector2(vlRight.getX2(),-10));
			//l.setPoint1(null);
			//l.setPoint2(null);
		}
		else {
			l.setPoint1(new Vector2(vlLeft.getX1(),multiple));
			l.setPoint2(new Vector2(vlRight.getX2(),multiple));
		}
	}
	
	private boolean hasElementWithSamenYCoordinate(float ycoord) {
		for(HorizontalLine l : hlines) {
			if(ycoord < l.getY1()+step && ycoord > l.getY1()-step){
				return true;
			}
		}
		return false;
	}

	private float multipleOfPosition(float y1) {
		//TODO make sure the resutl is not larger then the longest y position.
		
		// 25
		// begin bij 10
		// stappen van 2
		// 10 12 14 16 19 20 22 24 26
		// neem dichtste 24 of 26.
		
		int amntSteps = (int) ((y1-start)/step);
		float result = start + (amntSteps*step);
		if(result >= end){
			return end;
		}
		
		return result;
	}

	public OrthographicCamera getCamera() {
		return cam;
	}

	public HorizontalLine getGesturedLine() {
		return gesturedLine;
	}

	/**
	 * The gesture line is a line that is partially transparent to indicate where the actual line is going.
	 * @post the given line is also fitted between two horizontal lines.
	 * 			|	fitHorizontalLineBetweenVertivalLines(gesturedLine)
	 * @param gesturedLine
	 */
	public void setGesturedLine(HorizontalLine gesturedLine) {
		Vector3 point1 = cam.unproject(new Vector3(gesturedLine.getX1(),gesturedLine.getY1(),0));
		Vector3 point2 = cam.unproject(new Vector3(gesturedLine.getX2(),gesturedLine.getY2(),0));
		gesturedLine.setPoint1(new Vector2(point1.x,point1.y));
		gesturedLine.setPoint2(new Vector2(point2.x,point2.y));
		fitHorizontalLineBetweenVertivalLines(gesturedLine);
		this.gesturedLine = gesturedLine;
	}
	
	 public void setGesturedLineDragged(HorizontalLine gesturedLine) {
	        Vector3 point1 = cam.unproject(new Vector3(gesturedLine.getX1(),gesturedLine.getY1(),0));
	        Vector3 point2 = cam.unproject(new Vector3(gesturedLine.getX2(),gesturedLine.getY2(),0));
	        gesturedLine.setPoint1(new Vector2(gesturedLine.getX1(),point1.y));
	        gesturedLine.setPoint2(new Vector2(gesturedLine.getX2(),point2.y));
	        fitHorizontalLineBetweenVertivalLines(gesturedLine);
	        this.gesturedLine = gesturedLine;
	    }
	

	public Vector2 getBeginPosition() {
		return beginPosition;
	}

	/**
	 * Set the clicked begin position to draw a red circle.
	 * @param beginPosition
	 */
	public void setClickedPosition(Vector2 beginPosition) {
		/*
		if (beginPosition!=null) {
			Vector3 point1 = cam.unproject(new Vector3(beginPosition.x,beginPosition.y,0));
			this.beginPosition = new Vector2(point1.x,point1.y);
		}
		else {
			this.beginPosition = null;
		}
		*/
	}

	/**
	 * check if the coordinates are in the area of the begin position.
	 * @param screenX
	 * @param screenY
	 * @return	true if so
	 * 				otherwise false.
	 */
	public boolean isClearable(int screenX, int screenY) {
		/*
		Vector3 point1 = cam.unproject(new Vector3(screenX,screenY,0));
		if(getBeginPosition() == null) {
			return true;
		}
		if(Math.abs(point1.x - getBeginPosition().x) < beginRadius && 
				Math.abs(point1.y - getBeginPosition().y) < beginRadius) {
			setClickedPosition(null);
			return true;
		}
		setClickedPosition(null);
		*/
		return false;
	}

	/**
	 * calculate the start positions of the mouse. These are the beginnings of the vertical lines.
	 */
	private void startPositions() {
		startPositions = new ArrayList<Vector2>();
		for(VerticalLine l : vlines) {
			startPositions.add(l.getPoint1());
		}
		
	}
	
	public List<Vector2> getStartPositions() {
		return startPositions;
	}
	
	/**
	 * Update the game board, this will update all the mice positions.
	 * @param delta
	 */
	public void update(float delta) {
		for(Mouse m: mice) {
			m.update(delta);
		}
		Gdx.app.log("GameBoard", "update");
	}

	public List<Mouse> getMice() {
		return mice;
	}

	public List<Trap> getTraps() {
		return traps;
	}

	public List<Cheese> getGoals() {
		return goals;
	}

	public Vector2 unProject(int screenX, int screenY) {
		Vector3 vec = cam.unproject(new Vector3(screenX,screenY,0));
		return new Vector2(vec.x,vec.y);
	}
	
	
}
