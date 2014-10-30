package cheese.squeeze.gameObjects;

import java.util.Map.Entry;

import com.badlogic.gdx.math.Vector2;

public class VerticalLine extends Line {
	
   
    
    public VerticalLine(){
    	super();
    }
    
    
    public VerticalLine(float y1,float y2, float x){
    	super(new Vector2(x,y1),new Vector2(x,y2));
    }
	
    @Override
	public float getRelativePosition(Vector2 point) {
		return Math.abs(point.y - this.getY1());
	}

	@Override
	public void setNeighbour(Line line) {
		if (!(line instanceof HorizontalLine)) {
			super.setNeighbour(line);
		} else {
			float intersection = getRelativePosition(line.getPoint1());
			neighbours.put(intersection, line);
		}
	}

//	@Override
//	public Vector2 getNextIntersection(Vector2 from, Vector2 direction) {
//		Entry<Float, Line> neighbourEntry = getNeighbourEntry(from);
//		if (neighbourEntry == null)
//			return null;
//		return getAbsolutePosition(neighbourEntry.getKey());
//	}
//	
//	private Entry<Float, Line> getNeighbourEntry(Vector2 from) {
//		float relPos1 = getRelativePosition(from);
//		float relPos2 = getRelativePosition(from.cpy().add(new Vector2(0,1)));
//		Entry<Float, Line> nextLine = ((relPos2 - relPos1) >= 0) ? 
//				neighbours.ceilingEntry(relPos1) : neighbours.floorEntry(relPos1);
//		return nextLine;
//	}
//	
//	@Override
//	public Line getNeighbour(Vector2 from, Vector2 direction) {
//		Entry<Float, Line> neighbourEntry = getNeighbourEntry(from);
//		if (neighbourEntry == null)
//			return null;
//		return neighbourEntry.getValue();
//	}

}
