package cheese.squeeze.gameObjects;

import com.badlogic.gdx.math.Vector2;

public interface Goal {
	

	public Vector2 getPosition();

	public void setPosition(Vector2 position);
	
	public void activate();
	
	public Line getLine();

	public void setLine(Line l);
	
}
