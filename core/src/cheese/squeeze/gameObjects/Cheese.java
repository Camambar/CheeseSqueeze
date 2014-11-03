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
		this.setLine(l);
	}
	
	public Cheese(int tickets) {
		this.tickets = tickets;
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
			tickets = 0;
			CSGame.currentState=GameState.WON;
		}
	}

	public Line getLine() {
		return l;
	}

	public void setLine(Line l) {
		this.setPosition(l.getPoint2());
		this.l = l;
	}



}
