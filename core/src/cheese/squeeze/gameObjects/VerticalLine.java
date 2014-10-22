package cheese.squeeze.gameObjects;

import com.badlogic.gdx.math.Vector2;

public class VerticalLine extends Line {
	
   
    
    public VerticalLine(){
    	super();
    }
    
    
    public VerticalLine(float y1,float y2, float x){
    	super(new Vector2(x,y1),new Vector2(x,y2));
    }
	



}
