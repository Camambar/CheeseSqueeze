package cheese.squeeze.game.desktop;

import java.awt.Toolkit;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import cheese.squeeze.game.CSGame;
import cheese.squeeze.tweenAccessors.ActionResolver;

public class DesktopLauncher implements ActionResolver{
	public static void main (String[] arg) {
		Toolkit.getDefaultToolkit().getImage("res/drawableIcon.png");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
	    config.title = "Cheese Squeeze";
	    config.width = 600;
	    config.height = 800;
		new LwjglApplication(new CSGame(), config);
	}


	@Override
	public void reportAnalytics(String action, String cat, long value) {
		// TODO Auto-generated method stub
		
	}
}
