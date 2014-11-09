package cheese.squeeze.gameworld;

import cheese.squeeze.gameObjects.Line;
import cheese.squeeze.gameObjects.Mouse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class GameWorld {
	
	private Mouse mouse;
	private Line line;
	
	public GameWorld(int midPointY) {
		mouse = new Mouse(33,midPointY - 5, 17, 12);
		line = new Line();
	}

	public void update(float delta) {
		mouse.update(delta);
		line.update(delta);
		Gdx.app.log("GameWorld", "update");

	}
	
	public Line getLine() {
		return line;
	}
	
	public Mouse getMouse() {
		return mouse;
	}

}
