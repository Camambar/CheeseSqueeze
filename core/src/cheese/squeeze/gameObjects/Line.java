package cheese.squeeze.gameObjects;

import com.badlogic.gdx.math.Vector2;

public abstract class Line {
	
    private Vector2 point1;
    private Vector2 point2;
    private boolean drawable;
    
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

}
