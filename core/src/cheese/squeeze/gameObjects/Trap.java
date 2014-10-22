package cheese.squeeze.gameObjects;

import com.badlogic.gdx.math.Vector2;

public class Trap {

	private Vector2 position;
	
	/**
	 * The vector is the position of the end of the line were the trap must come
	 * @param point2
	 */
	public Trap(Vector2 point2) {
		this.setPosition(point2);
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

}
