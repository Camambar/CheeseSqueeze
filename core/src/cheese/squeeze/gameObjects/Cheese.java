package cheese.squeeze.gameObjects;

import com.badlogic.gdx.math.Vector2;

public class Cheese implements Goal{
	
	private Vector2 position;
	private int tickets;
	private Line l;
	
	/**
	 * 
	 * @param point2
	 */
	public Cheese(Line l,Vector2 point2,int tickets) {
		this.setPosition(point2);
		this.l = l;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public void activate() {
		if(tickets>0) {
			tickets--;
		}
		else {
			//TODO replace goal with trap
		}
	}

}
