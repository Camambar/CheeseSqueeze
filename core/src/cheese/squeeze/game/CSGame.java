package cheese.squeeze.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import cheese.squeeze.helpers.AssetLoader;
import cheese.squeeze.helpers.Timer;
import cheese.squeeze.helpers.TimerFactory;
import cheese.squeeze.screens.GameScreen;
import cheese.squeeze.screens.SplashScreen;
import cheese.squeeze.tweenAccessors.ActionResolver;
import cheese.squeeze.tweenAccessors.MusicAccessor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


/**
 * This class ini the game.
 * @author maxdekoninck
 *
 */
public class CSGame extends Game {

	public static GameState currentState;
	public static Level currentLevel = Level.LEVEL1;
	private ActionResolver action;
	public  final static String version = "1.5";
	public static final String updateURL = "http://camambar.github.io/CheeseSqueeze/";
	public static final String checkVersionURL = "http://camambar.github.io/CheeseSqueeze/version";
	private AssetLoader loader;

	public CSGame(ActionResolver action) {
		this.action = action;
		//action.reportAnalytics("test", "cat", 3);
	}

	public CSGame() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void create() {

		//LOAD LEVEL
		
		loadLevel();
		 

		Gdx.app.log("CSGame", "created");
		MusicAccessor musicA = new MusicAccessor();

		// set to setScreen(new GameScreen()); to start the game immediatly!
		//setScreen(new GameScreen());

		this.loader = new AssetLoader();
		loader.loadSplashScreen();
		setScreen(new SplashScreen(this,loader));
	}

	public ActionResolver getActionResolver() {
		return this.action;
	}

	public void saveLevel() {
		Preferences prefs = Gdx.app.getPreferences("CurrentLevel");
		prefs.putString("level", currentLevel.toString());
		prefs.flush();
		System.out.println("level " + currentLevel.toString() + " saved");
	}

	public void saveLevel(Level l) {
		Preferences prefs = Gdx.app.getPreferences("CurrentLevel");
		prefs.putString("level", l.toString());
		prefs.flush();
		System.out.println("level " + l.toString() + " saved");
	}
	
	public void loadLevel() {
		/*
		Preferences prefs = Gdx.app.getPreferences("CurrentLevel");
		if (prefs.contains("level")) {
			String l = prefs.getString("level");
			currentLevel = Level.valueOf(l);
		}
		System.out.println("level " + currentLevel.toString() + " loaded");
		*/
	}

	public void analytics() {
		TimerFactory.stopAll();
		ArrayList<Timer> t = TimerFactory.getTimers();
		HashMap<Level,HashMap<GameState,Integer>> m = Report.map;
		System.out.println("Timers: " + t);
		System.out.println("Levels: " + m);
		System.out.println("Score: " +Report.score.entrySet());

		if(action!=null) {
			for(Timer timer : t) {
				action.reportAnalytics(timer.getState().getLevel().toString(), timer.getState().getGameState().toString(), timer.getSeconds());
				//System.out.println(timer.getSeconds());
			}
			for(Entry<Level, HashMap<GameState, Integer>> e: Report.map.entrySet()) {
				for (Entry<GameState, Integer> e2 : e.getValue().entrySet()) {
					action.reportAnalytics(e.getKey().toString(),e2.getKey().toString(),e2.getValue());
				}
			}
			for(Entry<Level, Integer> e : Report.score.entrySet()) {
				action.reportAnalytics("SCORE",e.getKey().toString(),e.getValue());
			}


		}

		TimerFactory.clear();
		Report.clear();
	}

	@Override
	public void dispose() {
		analytics();
		loader.dispose();
		super.dispose();

	}

}
