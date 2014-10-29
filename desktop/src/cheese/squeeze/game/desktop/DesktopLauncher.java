package cheese.squeeze.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import cheese.squeeze.game.CSGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
	    config.title = "Cheese Squeeze";
	    config.width = 600;
	    config.height = 800;
		new LwjglApplication(new CSGame(), config);
	}
}
