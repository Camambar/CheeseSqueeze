package cheese.squeeze.gameObjects;

import java.util.Iterator;
import java.util.List;

import cheese.squeeze.helpers.AssetLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Mouse {

	private Vector2 position;
	private Vector2 velocity;
	
	private float speed;
	private float tolerance = 1;
	
	private Vector2 absolutePosition;

	private float rotation;
	private Vector2 mouseNose = AssetLoader.mouseNose;
	
	private Line currentLine;
	private Vector2 nextGoToPoint;
	
	public Mouse(float speed, Line line){
		float x = line.getX1();
		float y = line.getY1();
		position = new Vector2(x,y);
		this.currentLine = line;
		this.speed = speed;
		//position = new Vector2(x-(mouseNose.x), y- (mouseNose.y));
		
		velocity = new Vector2(0, 0);
		updatePath();
	}

	public void update(float delta) {
		System.out.println("nextGoToPoint is (" + nextGoToPoint.x + ", " + nextGoToPoint.y + ").");
		if (nextGoToPoint != null) {
			if(atIntersection()) {
				System.out.println("intersection reached!");
				//the mouse stands now at the previous nextGoToPoint
				setPosition(nextGoToPoint.x, nextGoToPoint.y);
				//nextGoToPoint is yet to be determined
				nextGoToPoint = null;
				//calculate the next line the mouse would be on.
				Line nextLine = currentLine.getNeighbour(getPosition(), velocity);
				//This mouse is now on the new line. 
				//If there is no next line, the mouse stays on this line.
				currentLine = (nextLine != null) ? nextLine : currentLine;
				updateVelocityDirection();
				//pick a new destination
				updatePath();
			}
			//set the mouses new speed.
			if (atIntersection()) {
				//The mouse ran into something with a dead end.
				velocity.set(Vector2.Zero);
			} else {
				updateVelocityDirection();
			}
			//move the mouse.
//			setPosition(getX() + velocity.x * delta, getY() + velocity.y * delta);
			setPosition(getX() + velocity.x * 1, getY() + velocity.y * 1);
		}		
	}

	private void updateVelocityDirection() {
		float angle = (float) Math.atan2(nextGoToPoint.y - getY(), nextGoToPoint.x - getX());
		velocity.set((float) Math.cos(angle) * speed, (float) Math.sin(angle) * speed);
		//set the mouses angle.
		setRotation(angle * MathUtils.radiansToDegrees);
	}
	
	public void updatePath() {
		Vector2 nextIntersection = currentLine.getNextIntersection(getPosition(), velocity);
		if (nextIntersection == null) {
			nextGoToPoint = currentLine.getEndPoint(getPosition(), velocity);
		} else {
			nextGoToPoint = nextIntersection;
		}
	}

	private boolean atIntersection() {
		return nextGoToPoint.x - getX() <= speed / tolerance * Gdx.graphics.getDeltaTime() && nextGoToPoint.y - getY() <= speed / tolerance * Gdx.graphics.getDeltaTime();
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
