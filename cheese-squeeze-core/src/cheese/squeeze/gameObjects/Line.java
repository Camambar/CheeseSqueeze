package cheese.squeeze.gameObjects;

import com.badlogic.gdx.math.Vector2;

public class Line {
	
    private Vector2 point1;
    private Vector2 point2;
    private boolean drawable;
    
    public Line(){
    	drawable = false;
    }
	
	
    public void onClick(int screenX, int screenY) {
    	if(point1 != null) {
    		point2 = new Vector2(screenX,screenY);
    		
    	}
    	else {
    		point1 = new Vector2(screenX,screenY);
    	}
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


	public void update(float delta) {
		if(point1 != null && point2 != null) {
			drawable = true;
		}
	}
	
	public boolean isdrawable() {
		return drawable;
	}


	public void clear() {
		this.drawable = false;
		point1 = null;
		point2 = null;
		
	}


}
