package cheese.squeeze.game;

import cheese.squeeze.helpers.AssetLoader;
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
	
	public static enum GameState {
		MENU,GAMEOVER,PLAYING,WON,PAUSE;
	}
	
	public CSGame(ActionResolver action) {
		this.action = action;
	}

	public CSGame() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void create() {
		Gdx.app.log("CSGame", "created");
		MusicAccessor musicA = new MusicAccessor();
		AssetLoader.loadSplashScreen();
		// set to setScreen(new GameScreen()); to start the game immediatly!
		//setScreen(new GameScreen());
		if(action!=null) {
			action.setTrackerScreenName("Test");
		}
		
		setScreen(new SplashScreen(this));
	}
	
    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }

}
