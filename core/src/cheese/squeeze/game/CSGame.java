package cheese.squeeze.game;

import java.util.ArrayList;
import java.util.HashMap;

import cheese.squeeze.helpers.AssetLoader;
import cheese.squeeze.helpers.Timer;
import cheese.squeeze.helpers.TimerFactory;
import cheese.squeeze.screens.GameScreen;
import cheese.squeeze.screens.SplashScreen;
import cheese.squeeze.tweenAccessors.ActionResolver;
import cheese.squeeze.tweenAccessors.MusicAccessor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;


/**
 * This class ini the game.
 * @author maxdekoninck
 *
 */
public class CSGame extends Game {

	public static GameState currentState;
	public static Level currentLevel = Level.LEVEL1;
	private ActionResolver action;
	
	public CSGame(ActionResolver action) {
		this.action = action;
	}

	public CSGame() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void create() {
		Gdx.app.log("CSGame", "created");
		TimerFactory.getNewTimer(GameState.GAMESTART).start();
		MusicAccessor musicA = new MusicAccessor();
		AssetLoader.loadSplashScreen();
		// set to setScreen(new GameScreen()); to start the game immediatly!
		//setScreen(new GameScreen());
		if(action!=null) {
			action.setTrackerScreenName("Test");
		}
		
		setScreen(new SplashScreen(this));
	}
	
	public ActionResolver getActionResolver() {
		return this.action;
	}
	
    @Override
    public void dispose() {
        super.dispose();
        TimerFactory.getRunningTimer(GameState.GAMESTART).stop();
        ArrayList<Timer> t = TimerFactory.getTimers();
        for(Timer ti : t) {
        	System.out.println(ti.toString());
        }
        
        HashMap<Level,HashMap<GameState,Integer>> m = Report.map;
        
        AssetLoader.dispose();
    }

}
