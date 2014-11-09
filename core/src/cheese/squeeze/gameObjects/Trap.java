package cheese.squeeze.gameObjects;

import cheese.squeeze.game.CSGame;
import cheese.squeeze.game.GameState;
import cheese.squeeze.helpers.AssetLoader;
import cheese.squeeze.tweenAccessors.MusicAccessor;
import cheese.squeeze.tweenAccessors.SoundAccessor;

import com.badlogic.gdx.math.Vector2;

public class Trap implements Goal{

	private Vector2 position;
	private boolean snapped = false;
	private Line l;
	
	/**
	 * The vector is the position of the end of the line were the trap must come
	 * @param point2
	 */
	public Trap(Line l) {
		this.setPosition(l.getPoint2());
		this.l = l;
	}

	public Trap() {
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	@Override
	public void activate() {
		snapped = true;
		SoundAccessor.play(AssetLoader.death);
		CSGame.currentState = GameState.GAMEOVER;
	}

	public boolean isSnapped() {
		return snapped;
	}
	
	public Line getLine() {
		return l;
	}

	public void setLine(Line l) {
		this.setPosition(l.getPoint2());
		this.l = l;
	}
	
	public Trap clone() {
		return new Trap(l);
	}

}
