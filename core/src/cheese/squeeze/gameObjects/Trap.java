package cheese.squeeze.gameObjects;

import cheese.squeeze.game.CSGame;
import cheese.squeeze.game.CSGame.GameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Trap implements Goal{

	private Vector2 position;
	
	/**
	 * The vector is the position of the end of the line were the trap must come
	 * @param point2
	 */
	public Trap(Line l,Vector2 point2) {
		this.setPosition(point2);
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public void activate() {
		CSGame.currentState = GameState.GAMEOVER;
	}

}
