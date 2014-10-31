package cheese.squeeze.gameObjects;

import cheese.squeeze.game.CSGame;
import cheese.squeeze.game.CSGame.GameState;
import cheese.squeeze.helpers.AssetLoader;
import cheese.squeeze.tweenAccessors.MusicAccessor;
import cheese.squeeze.tweenAccessors.SoundAccessor;

import com.badlogic.gdx.math.Vector2;

public class Cheese implements Goal{
	
	private Vector2 position;
	private int tickets;
	private Line l;
	
	/**
	 * 
	 * @param point2
	 */
	public Cheese(Line l,int tickets) {
		this.setPosition(l.getPoint2());
		this.tickets = tickets;
		this.l = l;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public int getTickets() {
		return tickets;
	}
	
	@Override
	public void activate() {
		SoundAccessor.play(AssetLoader.pop);
		if(tickets>1) {
			tickets--;
		}
		else {
			//TODO replace goal with trap
			CSGame.currentState=GameState.WON;
		}
	}

}
