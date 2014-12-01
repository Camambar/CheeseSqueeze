package cheese.squeeze.gameObjects;

import java.util.Map.Entry;
import java.util.TreeMap;

import javax.naming.OperationNotSupportedException;


import com.badlogic.gdx.math.Vector2;

public abstract class Line {
	
    private Vector2 point1;
    private Vector2 point2;
    private boolean drawable;
    protected TreeMap<Float, Line> neighbours = new TreeMap<Float, Line>();
    
    public Line() {
    	drawable = false;
    }
    
    public Line(Vector2 p1,Vector2 p2) {
    	this.point1 = p1;
    	this.point2 = p2;
    	drawable = true;
    }
    
    public float getX1() {
        return point1.x;
    }

    public float getY1() {
        return point1.y;
    }
    
    public float getX2() {
        return point2.x;
    }

    public float getY2() {
        return point2.y;
    }

	public Vector2 getPoint1() {
		return point1;
	}

	public void setPoint1(Vector2 point1) {
		this.point1 = point1;
	}

	public Vector2 getPoint2() {
		return point2;
	}

	public void setPoint2(Vector2 point2) {
		this.point2 = point2;
	}

	public boolean isdrawable() {
		return drawable;
	}

	public void setDrawable(boolean drawable) {
		this.drawable = drawable;
	}
	
	public void clear() {
		this.drawable = false;
		setPoint1(null);
		setPoint2(null);	
	}
	
	public float getRelativePosition(Vector2 point) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	public Vector2 getAbsolutePosition(float relativePosition) {
		Vector2 direction = getPoint2().cpy().sub(getPoint1());
		// normalize this vector
		Vector2 difference = direction.cpy().scl(relativePosition/(direction.len()));
		return this.getPoint1().cpy().add(difference);
	}
	
	public void setNeighbour(Line line) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean equals(Object e) {
		Line l = (Line) e;
		return l.getPoint1().equals(getPoint1())&& l.getPoint2().equals(getPoint2());
		
	}
	
	public Vector2 getNextIntersection(Vector2 from, Vector2 direction) {
		Entry<Float, Line> nextNeighbour = getNeighbourEntry(from, direction);
		if (nextNeighbour == null)
			return null;
		return getAbsolutePosition(nextNeighbour.getKey());
	}
	
	private Entry<Float, Line> getNeighbourEntry(Vector2 from, Vector2 direction) {
		float relPos1 = getRelativePosition(from);
		float relPos2 = getRelativePosition(from.cpy().add(direction));
		Entry<Float, Line> nextLine = ((relPos2 - relPos1) >= 0) ? 
				neighbours.higherEntry(relPos1) : neighbours.lowerEntry(relPos1);
		//		Entry<Float, Line> 	nextLine = null;
		return nextLine;
	}
	
	public Line getNeighbour(Vector2 from, Vector2 direction) {
		Entry<Float, Line> nextNeighbour = getNeighbourEntry(from, direction);
		if (nextNeighbour == null)
			return null;
		return nextNeighbour.getValue();
	}

	public Vector2 getEndPoint(Vector2 from, Vector2 direction) {
		float relPos1 = getRelativePosition(from);
		float relPos2 = getRelativePosition(from.cpy().add(direction));
		return ((relPos2 - relPos1) >= 0) ? 
				getPoint2() : getPoint1();
	}
	
	
	public abstract Line clone();
	
}
