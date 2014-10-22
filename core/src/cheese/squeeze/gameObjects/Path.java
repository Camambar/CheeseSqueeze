package cheese.squeeze.gameObjects;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class Path {
	
	private List<Vector2> directions;
	
	public Path() {
		
	}
	
	public Path(List<Vector2> directions) {
		this.setDirections(directions);
	}

	public List<Vector2> getDirections() {
		return directions;
	}

	public void setDirections(List<Vector2> directions) {
		this.directions = directions;
	}
	
	

}
