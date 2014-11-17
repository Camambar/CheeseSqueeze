package cheese.squeeze.gameObjects;

import java.util.Iterator;
import java.util.List;

import cheese.squeeze.helpers.AssetLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

//TODO: direction bijhouden, en veranderen als we aan een gotopoint belanden.
public class Mouse {

	private Vector2 position;
	private Vector2 velocity;
	
	private float speed;
	private float tolerance = 0.02f;
	
	private Vector2 absolutePosition;

	private float rotation;
	private Vector2 mouseNose = AssetLoader.mouseNose;
	
	private Line currentLine;
	private Line nextLine;
	private Vector2 goToOrientation;
	private Vector2 nextGoToPoint;
	private boolean ended = false;
	
	public Mouse(float speed, Line line){
		float x = line.getX1();
		float y = line.getY1();
		//float y = 0;
		position = new Vector2(x,y);
		this.currentLine = line;
		this.speed = speed;
		//position = new Vector2(x-(mouseNose.x), y- (mouseNose.y));
		
		velocity = new Vector2(0, 0);
		goToOrientation = new Vector2(0, 1);
		updatePath();
	}
	
	public boolean isOnHorizontalLine() {
		if(currentLine instanceof HorizontalLine) {
			return true;
		}
		return false;
	}

	public void update(float delta) {
		//System.out.println("nextGoToPoint is (" + nextGoToPoint.x + ", " + nextGoToPoint.y + ").");
		if (nextGoToPoint != null) {
			if(atIntersection()) {
				//System.out.println("intersection reached!");
				//the mouse stands now at the previous nextGoToPoint
				setPosition(nextGoToPoint.x, nextGoToPoint.y);
				//nextGoToPoint is yet to be determined
				nextGoToPoint = null;
				//This mouse is now on the new line. 
				//If there is no next line, the mouse stays on this line.
				currentLine = (nextLine != null) ? nextLine : currentLine;
				//nextLine is yet to be determined.
				nextLine = null;
				if (currentLine instanceof VerticalLine){
					goToOrientation = new Vector2(0, 1);
				} else if (currentLine instanceof HorizontalLine) {
					if (getPosition().equals(currentLine.getPoint1()))
						goToOrientation = currentLine.getPoint2().cpy().sub(currentLine.getPoint1());
					else
						goToOrientation = currentLine.getPoint1().cpy().sub(currentLine.getPoint2());
				}
				//updateVelocityDirection();
				//pick a new destination
				updatePath();
			}
			//set the mouses new speed.
			if (atIntersection()) {
				//The mouse ran into something with a dead end.
				((VerticalLine) currentLine).getGoal().activate();
				velocity.set(Vector2.Zero);
				ended = true;
			} else {
				updateVelocityDirection();
			}
			//move the mouse.
			updateVelocityDirection();
		//	setPosition(getX() + velocity.x * delta, getY() + velocity.y * delta);
			setPosition(getX() + velocity.x * 1, getY() + velocity.y * 1);
		//System.out.println(this.rotation);
		}		
	}

	private void updateVelocityDirection() {
		if(!ended) {
			float angle = (float) Math.atan2(nextGoToPoint.y - getY(), nextGoToPoint.x - getX());
			velocity.set((float) Math.cos(angle) * speed, (float) Math.sin(angle) * speed);
			//set the mouses angle.
			setRotation(angle * MathUtils.radiansToDegrees);
		}
		else {
			setRotation(90);
		}
	}
	
	public void updatePath() {
		Vector2 nextIntersection = currentLine.getNextIntersection(getPosition(), goToOrientation);
		nextLine = currentLine.getNeighbour(getPosition(), goToOrientation);
		if (nextIntersection == null) {
			nextGoToPoint = currentLine.getEndPoint(getPosition(), velocity);
		} else {
			nextGoToPoint = nextIntersection;
		}
	}

	private boolean atIntersection() {
		float dynTolerance = speed / tolerance * Gdx.graphics.getDeltaTime();
		//System.out.println("dyn tol: " + dynTolerance);
		return Math.abs(nextGoToPoint.x - getX()) <= dynTolerance 
				&& Math.abs(nextGoToPoint.y - getY()) <= dynTolerance;
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

	public boolean isEnded() {
		return ended ;
	}

	public float getSpeed() {
		// TODO Auto-generated method stub
		return velocity.x + velocity.y;
	}
	
	//public void changeNextWayPoints(ArrayList<Vector2> newWaypoints) {
	//	this.setPath(nPath);
	//}

}
