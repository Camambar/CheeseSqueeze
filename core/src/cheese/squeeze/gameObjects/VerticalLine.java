package cheese.squeeze.gameObjects;

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


}
