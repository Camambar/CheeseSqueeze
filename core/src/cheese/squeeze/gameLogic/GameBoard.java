package cheese.squeeze.gameLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import cheese.squeeze.game.CSGame;
import cheese.squeeze.game.GameState;
import cheese.squeeze.game.Level;
import cheese.squeeze.game.ReportStatus;
import cheese.squeeze.gameObjects.Cheese;
import cheese.squeeze.gameObjects.HorizontalLine;
import cheese.squeeze.gameObjects.Line;
import cheese.squeeze.gameObjects.Mouse;
import cheese.squeeze.gameObjects.Trap;
import cheese.squeeze.gameObjects.VerticalLine;
import cheese.squeeze.helpers.AssetLoader;
import cheese.squeeze.helpers.Timer;
import cheese.squeeze.helpers.TimerFactory;
import cheese.squeeze.structures.LinkedList;
import cheese.squeeze.tweenAccessors.SoundAccessor;

public class GameBoard {

	
	private List<VerticalLine> vlines;
	private TreeMap<Float,LinkedList<HorizontalLine>> hlines;
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
	private List<Mouse> miceBackup;
	private static int MAX_HLINES = 15;
	private float step;
	private float start;
	private float end;
	private Level level;
	private HorizontalLine effectLine;
	private float multip;
	private int currentMouse;
	private boolean presetLines;
	private boolean fl = false;
	private ArrayList<HorizontalLine> updateReqLines = new ArrayList<HorizontalLine>();
	private ReportStatus status;
	
	

	public GameBoard(float width, float height,Level l) {
		
		this.level = l;
		status = new ReportStatus(GameState.FIRSTLINE,level);
		this.width = width;
		this.height = height;
		cam = new OrthographicCamera();
		cam.setToOrtho(true,width, height);
		this.multip = l.getMultip();
		currentMouse = 0;
		//all the lines
		if(l.isRandomLines()){
			makeVerticalLinesRandom();
		}
		else {
			makeVerticalLines();
		}
		
		gesturedLine = new HorizontalLine();
		hlines = makeHlineMap();
		
		
		
		
		multip = l.getMultip();
		
		mice = new ArrayList<Mouse>();
		addHlines();
		
	}
	


	private void addHlines() {
		presetLines = true;
		if(level.getHlines().length > 0) {
			for(Vector2 vec : level.getHlines()) {
				int i =1;
				for(Entry<Float, LinkedList<HorizontalLine>> e : this.hlines.entrySet()) {
					if(i == vec.y && e.getValue().isValidPosition((int)vec.x)) {
						float x = vlines.get(((int)vec.x)-1).getX1();
						HorizontalLine line = new HorizontalLine(e.getKey(),x,x+0.01f);
						this.fitHorizontalLineBetweenVertivalLines(line);
						line.setUpdateRequired(false);
						this.addHLine(line);
					}
					i++;
				}
			}
		}
		presetLines = false;
	}



	private TreeMap<Float, LinkedList<HorizontalLine>> makeHlineMap() {
		TreeMap<Float,LinkedList<HorizontalLine>> map = new TreeMap<Float,LinkedList<HorizontalLine>>();
		int amntSteps = (int) ((end-start)/step);
		for(int i = 1; i < amntSteps;i++){
			map.put(start + (i*step), new LinkedList<HorizontalLine>(level.getVlines().length-1));
		}
		return map;
	}
	

	private void makeMice() {
		mice = new ArrayList<Mouse>();
		for (currentMouse=0 ;currentMouse < level.getNbMouse();currentMouse++) {
			Mouse mouse = new Mouse(level.getSpeed()*multip,vlines.get(level.getMouseLine()[currentMouse]-1));
			mice.add(mouse);
		}

	}
	
	private void makeVerticalLines() {
		this.goals = new ArrayList<Cheese>();
		this.traps = new ArrayList<Trap>();
		vlines = new ArrayList<VerticalLine>();
		
		float totw = (width-20)/(level.getAmountVlines()-1);
		int i = 0;
		for(VerticalLine vl : level.getVlines()) {
			vl.setPoint1(new Vector2(10+(totw*i),15));
			vl.setPoint2(new Vector2(10+(totw*i),height-20));
			VerticalLine nline = vl.clone();
			
			if(vl.getGoal() instanceof Trap) {
				nline.setGoal(vl.getGoal().clone());
				traps.add((Trap) nline.getGoal());
			}
			else {
				nline.setGoal(vl.getGoal().clone());
				goals.add((Cheese) nline.getGoal());
			}
			vlines.add(nline);
			i++;
		}
		this.step = (vlines.get(0).getY2() - vlines.get(0).getY1())/(this.MAX_HLINES+1);
		this.start = vlines.get(0).getY1();
		this.end = vlines.get(0).getY2();
		startPositions();
	}

	private void makeVerticalLinesRandom() {
		this.goals = new ArrayList<Cheese>();
		this.traps = new ArrayList<Trap>();
		vlines = new ArrayList<VerticalLine>();
		float totw = (width-20)/(level.getAmountVlines()-1);
		boolean set = false;
		for(int i=0;i<level.getAmountVlines();i++) {
			VerticalLine vl = new VerticalLine(15,height-20,10+(totw*i));
			
			double rand = Math.random();
			
			if(goals.size() < level.getAmountGoals() && rand <= 0.5){
				//TODO cheese will be 4 always 1!!
				Cheese c = new Cheese(vl,4);
				vl.setGoal(c);
				goals.add(c);
				set = true;
			}
			else if(traps.size() < level.getAmountTraps()){
				Trap t = new Trap(vl);
				vl.setGoal(t);
				traps.add(t);
				set = true;
			}
			else if(!set && goals.size() < level.getAmountGoals()){
				//TODO cheese will be 4 always 1!!
				Cheese c = new Cheese(vl,4);
				vl.setGoal(c);
				goals.add(c);
				set = true;
			}
			vlines.add(vl);
			set = false;
		}
		this.step = (vlines.get(0).getY2() - vlines.get(0).getY1())/(this.MAX_HLINES+1);
		this.start = vlines.get(0).getY1();
		this.end = vlines.get(0).getY2();
		startPositions();
	}
	
	public List<VerticalLine> getVLines() {
		return vlines;
	}
	
	public void addHLine(HorizontalLine line) {
		Timer t = TimerFactory.getRunningTimer(status);
		
		int row = betweenLines(line.getX1());
		if(!isOcupiedPosition(line.getY1(),row)) {
			if(!presetLines) {
				if(t!=null) {
					fl =true;
					t.stop();
				}
				SoundAccessor.play(AssetLoader.chalk);
				
				effectLine = line;
			}
			
			hlines.get(line.getY1()).addElement(line, row);
			updateReqLines.add(line);
			for (VerticalLine l : vlines) {
				if (l.getX1() == line.getX1()) {
					l.setNeighbour(line);
					line.setNeighbour(l);
				}
				if (l.getX1() == line.getX2()) {
					l.setNeighbour(line);
					line.setNeighbour(l);
				}
			}
			for(Mouse m : mice) {
				if(!m.isOnHorizontalLine()) {
					m.updatePath();
				}
				
			}
		}
	}

	public TreeMap<Float,LinkedList<HorizontalLine>> getHLinesMap() {
		return hlines;
	}
	
	public ArrayList<HorizontalLine> getHLines() {
		ArrayList<HorizontalLine> list = new ArrayList<HorizontalLine>();
		for (Entry<Float,LinkedList<HorizontalLine>> e : hlines.entrySet()) {
			List<HorizontalLine> l = e.getValue().getElements();
			list.addAll(l);
		}
		return list;
	}
	
	public ArrayList<HorizontalLine> getHGuideLines() {
		ArrayList<HorizontalLine> list = new ArrayList<HorizontalLine>();
		for(Entry<Float,LinkedList<HorizontalLine>> e : hlines.entrySet()) {
			LinkedList<HorizontalLine> l = e.getValue();
			for (int i =1; i < l.getSize()+1; i++ ) {
				if(!isOcupiedPosition(e.getKey(),i)){
					list.add(new HorizontalLine(e.getKey(),vlines.get(i-1).getX1(),vlines.get(i).getX1()));
				}
			}

		}
		return list;
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
		//l.setPoint1(new Vector2(vlLeft.getX1(),l.getY1()));
		//l.setPoint1(new Vector2(vlLeft.getX1(),l.getY1()));
		if(vlRight != null && vlLeft != null) {
			l.setPoint1(new Vector2(vlLeft.getX1(),multiple));
			l.setPoint2(new Vector2(vlRight.getX2(),multiple));
		}
		
	}
	

	private float multipleOfPosition(float y1) {
		//TODO make sure the resutl is not larger then the longest y position.
		Float floor = hlines.floorKey(y1);
		Float ceil = hlines.ceilingKey(y1);
		
		if(ceil != null && floor != null) {
			if(Math.abs(ceil - y1) < Math.abs(floor - y1)) {
				return ceil;
			}
			return floor;
		}
		else if (ceil == null) {
			return floor;
		}
		else {
			return ceil;
		}
		
		
		/*OlD STUFF
		int amntSteps = (int) ((y1-start)/step);
		float result = start + (amntSteps*step);
		if(result >= end){
			return end-step;
		}
		if(result <= start) {
			return start+step;
		}
		return result;
		*/
		
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
		gesturedLine.setPoint1(new Vector2(point1.x,(point1.y)));
		gesturedLine.setPoint2(new Vector2(point2.x,(point2.y)));
		fitHorizontalLineBetweenVertivalLines(gesturedLine);
		if(!isOcupiedPosition(gesturedLine.getY1(),betweenLines(gesturedLine.getX1()))) {
			this.gesturedLine = gesturedLine;
		}
		else {
			this.gesturedLine = null;
		}
	}
	
	public ArrayList<Float> getYPositions() {
		return new ArrayList<Float>(hlines.keySet());
	}
	
	private int betweenLines(float x) {
		int i = 0;
		for(VerticalLine vl : vlines) {
			if(x < vl.getX1()) {
				return i;
			}
			i++;
		}
		return 0;
	}
	 
	
	 public void setGesturedLineDragged(HorizontalLine gesturedLine) {
	        Vector3 point1 = cam.unproject(new Vector3(gesturedLine.getX1(),gesturedLine.getY1(),0));
	        Vector3 point2 = cam.unproject(new Vector3(gesturedLine.getX2(),gesturedLine.getY2(),0));
	        gesturedLine.setPoint1(new Vector2(point1.x,(point1.y)));
	        gesturedLine.setPoint2(new Vector2(point1.x,(point2.y)));
	        fitHorizontalLineBetweenVertivalLines(gesturedLine);
			if(!isOcupiedPosition(gesturedLine.getY1(),betweenLines(gesturedLine.getX1()))) {
				this.gesturedLine = gesturedLine;
			}
			else {
				this.gesturedLine = null;
			}
	    }
	

	private boolean isOcupiedPosition(float y,int betweenlines) {
		LinkedList<HorizontalLine> ll = hlines.get(y);
		if(ll != null && !ll.isValidPosition(betweenlines)) {
			return true;
		}
		return false;
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
		//System.out.println("UPDATE BOARD");
		Iterator<HorizontalLine> itrH = updateReqLines.iterator();
		while(itrH.hasNext()) {
			HorizontalLine l = itrH.next();
			if(l.updateNeeded) {
				l.update();
			}
			else {
				itrH.remove();
			}
			
		}
		int counter = 0;
		Iterator<Mouse> itr = mice.iterator();
		while(itr.hasNext()) {
			Mouse m = itr.next();
			if(!m.isEnded()) {
				m.update(delta);
			}
			else {
				itr.remove();
				this.currentMouse+=1;
				counter++;
			}
		}
		
		for(int i = 0; i< counter;i++) {
			//this.multip= currentMouse * multip;
			if(currentMouse + i > level.getMouseLine().length){
				if(mice.size() == 0) {
					CSGame.currentState = GameState.GAMEOVER;
				}
				//else if() {
					// maar 1 muis toevoegen ipv counter aantal
				//}
			}
			else {
				mice.add(new Mouse(level.getSpeed()*this.multip,vlines.get(level.getMouseLine()[currentMouse-1]-1)));
			}
			
		}
		//Gdx.app.log("GameBoard", "update");
	}
	
	public ArrayList<VerticalLine> getNextMouseLine() {
		ArrayList<VerticalLine> list = new ArrayList<VerticalLine>();
		if(level.getMouseLine().length > currentMouse) {
			if (CSGame.currentState.equals(GameState.COUNTDOWN)) {
				for(int i = 0 ; i<level.getNbMouse();i++) {
					list.add(vlines.get(level.getMouseLine()[currentMouse+i]-1));
				}
			}
			else {
				list.add(vlines.get(level.getMouseLine()[currentMouse]-1));
			}
			
			
		}
		return list;
	}
	
	private Line getRandomLine(){
		int size = 0;
		ArrayList<VerticalLine> lines = new ArrayList<VerticalLine>();
		for(VerticalLine l: vlines){
			if(l.getGoal() instanceof Trap) {
				size ++;
				lines.add(l);
			}
		}
		double nb = Math.random() * size;
		return lines.get((int) nb);
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

	public void pause() {
		miceBackup = new ArrayList<Mouse>(mice);
		mice = new ArrayList<Mouse>();
	}
	
	public void resume() {
		mice = new ArrayList<Mouse>(miceBackup);
	}
	
	public void dispose() {
		this.currentMouse = 0;
		this.hlines = null;
		if (!fl) {
			TimerFactory.remove(status);
		}
		
	}



	public boolean isTutorial() {
		return level.isTutorial();
	}

	public void tutorialEnded() {
		//mouse stuff
		makeMice();
	}



	public float getTutorialPositionX() {
		return vlines.get(0).getX1()+(vlines.get(1).getX1()-vlines.get(0).getX1())/2;
	}
	
	public float getTutorialPositionY() {
		return hlines.higherKey(hlines.higherKey(hlines.firstKey()));
	}



	public int getScore() {
		int amount = MAX_HLINES*10*(Math.round(vlines.size()/2-0.1f));
		for (Entry<Float, LinkedList<HorizontalLine>> hl : hlines.entrySet()) {
			amount -= hl.getValue().getElements().size()*10;
		}
		return amount;
	}



	public void start() {
		TimerFactory.getNewTimer(status).start();
		makeMice();
	}
	
	public HorizontalLine getEffectLine() {
		if(effectLine!=null) {
			HorizontalLine l = effectLine.clone();
			effectLine = null;
			return l;
		}
		return null;
	}
	
	public float getDistBetweenVlines() {
		return vlines.get(1).getX1()-vlines.get(0).getX1();
	}
	
}
