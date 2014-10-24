package cheese.squeeze.screens;

import cheese.squeeze.game.CSGame;
import cheese.squeeze.gameLogic.GameBoard;
import cheese.squeeze.gameworld.GameRenderer;
import cheese.squeeze.helpers.AssetLoader;
import cheese.squeeze.helpers.InputHelper;
import cheese.squeeze.ui.SimpleButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {
	
	private GameRenderer renderer;
	private GameBoard board;
	
    public GameScreen(CSGame game) {
        Gdx.app.log("GameScreen", "Attached");
        //Calculate the starting positions
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        
        float gameWidth = 136;
        float gameHeight = screenHeight / (screenWidth / gameWidth);

        int midPointY = (int) (gameHeight / 2);
        
    	try {
			board = new GameBoard(3, 2, 1, (int) gameWidth,(int) gameHeight);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	renderer = new GameRenderer(board,midPointY,(int) gameHeight,(int) gameWidth);
    	
    	//TODO make a class to handle the audio.
    	AssetLoader.menuSound.stop(0);
    	
    	AssetLoader.menuSound.stop(1);
    	AssetLoader.menuSound.stop(2);
    	AssetLoader.menuSound.stop(3);
    	AssetLoader.menuSound.stop(4);
    	AssetLoader.gameSound.play();
    	
    	Gdx.input.setInputProcessor(new InputHelper(board,game));
    }

    @Override
    public void render(float delta) {
    	board.update(delta);
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
