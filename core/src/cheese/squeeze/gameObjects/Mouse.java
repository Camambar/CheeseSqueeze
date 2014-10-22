package cheese.squeeze.gameObjects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cheese.squeeze.helpers.AssetLoader;

import com.badlogic.gdx.math.Vector2;

public class Mouse {

	private Vector2 position;
	private Vector2 velocity;
	private Vector2 acceleration;
	private Vector2 absolutePosition;

	private float rotation;
	private PathCalculator directions;
	private Vector2 mouseNose = AssetLoader.mouseNose;
	
	public Mouse(float x, float y,PathCalculator directions){
		absolutePosition = new Vector2(x,y);
		position = new Vector2(x-(mouseNose.x), y- (mouseNose.y));
		velocity = new Vector2(0, 50);
		acceleration = new Vector2(0, 0);
		
		this.directions = directions;
	}

	public void update(float delta) {
		List<Vector2> v = null;
		if (v == null) {
			v = directions.getNextPosition(absolutePosition);
		}
		if(v.size()==1) {
			if(Math.round(v.get(0).x) == Math.round(this.getXAbs())) {
				velocity = new Vector2(0, 10);
			}
			else if(Math.round(v.get(0).y) == Math.round(this.getYAbs()) && v.get(0).x > this.getXAbs()) {
				velocity = new Vector2(0, 10);
			}
			else if(Math.round(v.get(0).y) == Math.round(this.getYAbs()) && v.get(0).x <= this.getXAbs()){
				velocity = new Vector2(0, -10);
			}
			else {
				velocity = new Vector2(0, 0);
			}
			v = null;
		}
		else {
			if(Math.round(v.get(0).x) == Math.round(this.getXAbs())) {
				velocity = new Vector2(0, 10);
			}
			else if(Math.round(v.get(0).y) == Math.round(this.getYAbs()) && v.get(0).x > this.getXAbs()) {
				velocity = new Vector2(0, 10);
			}
			else if(Math.round(v.get(0).y) == Math.round(this.getYAbs()) && v.get(0).x <= this.getXAbs()){
				velocity = new Vector2(0, -10);
			}
			else {
				velocity = new Vector2(0, 0);
			}
			v.remove(0);
		}
		
		/**
		if(directions != null) {
		Iterator<Vector2> ite = directions.getDirections().iterator();
		if(v == null) {
			v = ite.next();
		}
		if(Math.round(v.y) == Math.round(this.getY())) {
			//TODO rotate, check left or right
			// velocity = new Vector2(-10, 0);
			if(!ite.hasNext()) {
				velocity = new Vector2(0, 0);
			}
			else {
				velocity = new Vector2(10, 0);
				v = ite.next();
			}
		}
		if(Math.round(v.x) == Math.round(this.getX())){
			
			if(!ite.hasNext()) {
				velocity = new Vector2(0,0);
			}
			else {
				velocity = new Vector2(0, 10);
				v = ite.next();
			}
		}
		**/
		//velocity.add(acceleration.cpy().scl(delta));
		position.add(velocity.cpy().scl(delta));
		
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}
	
	public float getXAbs() {
		return this.absolutePosition.x;
	}
	
	public float getYAbs() {
		return this.absolutePosition.y;
	}

	public float getRotation() {
		return rotation;
	}
	

	public Vector2 getPosition() {
		return position;
	}

}
