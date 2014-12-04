package cheese.squeeze.gameObjects;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class PathCalculator {
	
	private List<VerticalLine> vlines;
	private List<HorizontalLine> hlines;

	public PathCalculator(List<VerticalLine> vlines, List<HorizontalLine> hlines) {
		this.vlines = vlines;
		this.hlines = hlines;
	}
	
	
	public Path getPathFrom(VerticalLine line) {
		Path p = new Path();
		ArrayList<Vector2> list = new ArrayList<Vector2>();
		//list.add(line.getPoint1());
		VerticalLine currentLine = line;
		Vector2 currentPos = line.getPoint1();
		HorizontalLine intersect = getFirstIntersection(currentLine,currentPos);
		while(intersect !=null) {
			
			if(intersect.getX1() == currentLine.getX1()) {
				list.add(intersect.getPoint1());
				list.add(intersect.getPoint2());
				currentLine = getNewLineOnPosition(intersect.getPoint1());
				currentPos = intersect.getPoint2();
			}
			else {
				list.add(intersect.getPoint2());
				list.add(intersect.getPoint1());
				currentLine = getNewLineOnPosition(intersect.getPoint2());
				currentPos = intersect.getPoint1();
			}
			
			intersect = getFirstIntersection(currentLine,currentPos);
		}
		list.add(currentLine.getPoint2());
		p.setDirections(list);
		return p;
	}
	
	public VerticalLine getNewLineOnPosition(Vector2 position) {
		for(VerticalLine vl : vlines) {
			if(vl.getX1() == position.x) {
				return vl;
			}
		}
		return null;
	}
	
	/**
	 * return null if there are non
	 * @param line
	 * @param position
	 * @return
	 */
	public HorizontalLine getFirstIntersection(VerticalLine line,Vector2 position){
		HorizontalLine hline = null;
		for(HorizontalLine l : hlines) {
			if(l.getX1() == line.getX1() || l.getX2() == line.getX1()){
				if(l.getY1() > position.y) {
					if(hline == null) {
						hline = l;
					}
					else if(l.getY1() < hline.getY1()) {
						hline = l;
					}
				}
			}
		}
		return hline;
	}


	public List<Vector2> getNextPosition(Vector2 position) {
		ArrayList<Vector2> list = new ArrayList<Vector2>();
		HorizontalLine line = getFirstIntersection(getNewLineOnPosition(position),position);
		VerticalLine vline = getNewLineOnPosition(position);
		if(line == null) {
			list.add(vline.getPoint2());
		}
		else {
			if(line.getX1() == vline.getX1()){
				list.add(line.getPoint1());
				list.add(line.getPoint2());
			}
			else {
				list.add(line.getPoint2());
				list.add(line.getPoint1());
			}
		}
		return list;
		
	}

}
