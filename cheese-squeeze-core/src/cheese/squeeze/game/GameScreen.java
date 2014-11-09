package cheese.squeeze.game;

import cheese.squeeze.gameworld.GameRenderer;
import cheese.squeeze.gameworld.GameWorld;
import cheese.squeeze.helpers.InputHelper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class GameScreen implements Screen {
	
	private GameWorld world;
	private GameRenderer renderer;
	
    public GameScreen() {
        Gdx.app.log("GameScreen", "Attached");
        //Calculate the starting positions
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 136;
        float gameHeight = screenHeight / (screenWidth / gameWidth);

        int midPointY = (int) (gameHeight / 2);
        
        
    	world = new GameWorld(midPointY);
    	renderer = new GameRenderer(world,midPointY,(int) gameHeight,(int) gameWidth);
    	
    	 Gdx.input.setInputProcessor(new InputHelper(world.getLine()));
    }

    @Override
    public void render(float delta) {
    	world.update(delta);
    	renderer.render();
    	//Print the fps
    	Gdx.app.log("GameScreen FPS", (1/delta) + "");
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("GameScreen", "resizing");
    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show called");
    }

    @Override
    public void hide() {
        Gdx.app.log("GameScreen", "hide called");     
    }

    @Override
    public void pause() {
        Gdx.app.log("GameScreen", "pause called");        
    }

    @Override
    public void resume() {
        Gdx.app.log("GameScreen", "resume called");       
    }

    @Override
    public void dispose() {
        // Leave blank
    }

}
