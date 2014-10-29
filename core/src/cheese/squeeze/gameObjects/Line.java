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
		Vector2 direction = getPoint2().sub(getPoint1());
		// normalize this vector
		Vector2 difference = direction.scl(relativePosition/(direction.len()));
		return this.getPoint1().add(difference);
	}
	
	public void setNeighbour(Line line) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	public Vector2 getNextIntersection(Vector2 from, Vector2 direction) {
		Entry<Float, Line> nextLine = getNeighbourEntry(from, direction);
		if (nextLine == null)
			return null;
		return nextLine.getValue().getAbsolutePosition(nextLine.getKey());
	}

	private Entry<Float, Line> getNeighbourEntry(Vector2 from, Vector2 direction) {
		float relPos1 = getRelativePosition(from);
		float relPos2 = getRelativePosition(from.add(direction));
		Entry<Float, Line> nextLine = ((relPos2 - relPos1) > 0) ? 
				neighbours.higherEntry(relPos1) : neighbours.lowerEntry(relPos1);
		return nextLine;
	}
	
	public Line getNeighbour(Vector2 from, Vector2 direction) {
		Entry<Float, Line> neighbourEntry = getNeighbourEntry(from, direction);
		if (neighbourEntry == null)
			return null;
		return neighbourEntry.getValue();
	}

	public Vector2 getEndPoint(Vector2 from, Vector2 direction) {
		float relPos1 = getRelativePosition(from);
		float relPos2 = getRelativePosition(from.add(direction));
		return ((relPos2 - relPos1) >= 0) ? 
				getPoint2() : getPoint1();
	}
}
