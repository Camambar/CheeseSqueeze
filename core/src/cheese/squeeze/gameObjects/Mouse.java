package cheese.squeeze.gameObjects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cheese.squeeze.helpers.AssetLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Mouse {

	private Vector2 position;
	private Vector2 velocity;
	private ArrayList<Vector2> path;
	private float speed;
	private float tolerance = 3;
	private int waypoint = 0;
	
	private Vector2 absolutePosition;

	private float rotation;
	private Vector2 mouseNose = AssetLoader.mouseNose;
	
	public Mouse(float x, float y,ArrayList<Vector2> path,float speed){
		position = new Vector2(x,y);
		this.speed = speed;
		this.path = path;
		//position = new Vector2(x-(mouseNose.x), y- (mouseNose.y));
		
		velocity = new Vector2(0, 0);
	}
	
	public Mouse(float x, float y,float speed){
		position = new Vector2(x,y);
		this.speed = speed;
		this.path = new ArrayList<Vector2>();
		//position = new Vector2(x-(mouseNose.x), y- (mouseNose.y));
		
		velocity = new Vector2(0, 0);
	}

	public void update(float delta) {
		if(path.size() != 0) {
			float angle = (float) Math.atan2(path.get(waypoint).y - getY(), path.get(waypoint).x - getX());
			velocity.set((float) Math.cos(angle) * speed, (float) Math.sin(angle) * speed);
	
			setPosition(getX() + velocity.x * delta, getY() + velocity.y * delta);
			setRotation(angle * MathUtils.radiansToDegrees);
	
			if(isWaypointReached()) {
				setPosition(path.get(waypoint).x, path.get(waypoint).y);
				if(waypoint + 1 >= path.size())
					waypoint = 0;
				else
					waypoint++;
			}
		}
	}
	
	public void setPath(ArrayList<Vector2> nPath) {
		this.path = nPath;
	}

	private boolean isWaypointReached() {
		return path.get(waypoint).x - getX() <= speed / tolerance * Gdx.graphics.getDeltaTime() && path.get(waypoint).y - getY() <= speed / tolerance * Gdx.graphics.getDeltaTime();
	}

	private void setRotation(float f) {
		this.rotation = f;
		
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}
	
	private void setPosition(float x,float y) {
		this.position.set(x, y);
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
	
	//public void changeNextWayPoints(ArrayList<Vector2> newWaypoints) {
	//	this.setPath(nPath);
	//}

}
