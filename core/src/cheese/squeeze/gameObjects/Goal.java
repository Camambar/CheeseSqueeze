package cheese.squeeze.gameObjects;

import com.badlogic.gdx.math.Vector2;

public class Goal {
	
	private Vector2 position;

	/**
	 * 
	 * @param point2
	 */
	public Goal(Vector2 point2) {
		this.setPosition(point2);
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

}
