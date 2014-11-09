package cheese.squeeze.game;

import cheese.squeeze.helpers.AssetLoader;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class CSGame extends Game {

	@Override
	public void create() {
		Gdx.app.log("ZBGame", "created");
		AssetLoader.load();
		setScreen(new GameScreen());
	}
	
    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }

}
