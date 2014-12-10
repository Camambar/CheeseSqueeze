package cheese.squeeze.game.desktop;

import java.awt.Toolkit;

import cheese.squeeze.game.CSGame;
import cheese.squeeze.tweenAccessors.ActionResolver;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher implements ActionResolver{
	public static void main (String[] arg) {
		Toolkit.getDefaultToolkit().getImage("res/drawableIcon.png");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
	    config.title = "Cheese Squeeze";
	    config.width = 600;
	    config.height = 800;
		//config.width =480;
		//config.height =854;
	    
	    new LwjglApplication(new CSGame(new DesktopLauncher()), config);
	}


	@Override
	public void reportAnalytics(String action, String cat, long value) {
		System.out.println("reporting action: " + action + " cat: " + cat + " val: " + value);	
	}
	
	boolean signedInStateGPGS = false;

	@Override
	public boolean getSignedInGPGS() {
		System.out.println("logged in state: " + signedInStateGPGS);
		return signedInStateGPGS;
	}

	@Override
	public void loginGPGS() {
		System.out.println("loginGPGS");
		signedInStateGPGS = true;
	}

	@Override
	public void submitScoreGPGS(int score) {
		System.out.println("submitScoreGPGS " + score);
	}

	@Override
	public void getLeaderboardGPGS() {
		System.out.println("getLeaderboardGPGS");
	}


	@Override
	public void unlockAchievementGPGS(String achievementId) {
		System.out.println("Unlocked achievement: " + achievementId);
	}


	@Override
	public void getAchievementsGPGS() {
		System.out.println("getAchievementGPGS");
	}

}
