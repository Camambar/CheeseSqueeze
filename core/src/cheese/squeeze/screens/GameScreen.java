package cheese.squeeze.screens;

import java.util.ArrayList;

import cheese.squeeze.game.CSGame;
import cheese.squeeze.game.CSGame.GameState;
import cheese.squeeze.game.Level;
import cheese.squeeze.gameLogic.GameBoard;
import cheese.squeeze.gameworld.GameRenderer;
import cheese.squeeze.helpers.AssetLoader;
import cheese.squeeze.helpers.InputHelper;
import cheese.squeeze.tweenAccessors.MusicAccessor;
import cheese.squeeze.ui.PopUpButton;
import cheese.squeeze.ui.SimpleButton;
import cheese.squeeze.ui.SimpleButtonListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {
	
	private GameRenderer renderer;
	private GameBoard board;
	//private SimpleButton homeBtn;
	//private SimpleButton gameOverPopUp;
	//private SimpleButton completedPopUp;
	private SimpleButton gameOverPopUp;
	private SimpleButton completedPopUp;
	private Level currentLevel;
	
    public GameScreen(final CSGame game,Level l) {
        Gdx.app.log("GameScreen", "Attached");
        CSGame.currentState = GameState.PLAYING;
        //Calculate the starting positions
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        currentLevel = l;
        float gameWidth = 136;
        float gameHeight = screenHeight / (screenWidth / gameWidth);

        int midPointY = (int) (gameHeight / 2);
        
		board = new GameBoard((int) gameWidth,(int) gameHeight,currentLevel);
			
    	ArrayList<SimpleButton> buttons = new ArrayList<SimpleButton>();   
    	
    	SimpleButton homeBtn = new SimpleButton(new SimpleButtonListener() {
    		
			@Override
			public void pushButtonListener(SimpleButton btn) {
				game.setScreen(new MenuScreen(game));
			}
		},AssetLoader.home.getX(),AssetLoader.home.getY(),AssetLoader.home.getWidth(),AssetLoader.home.getHeight(),AssetLoader.home,AssetLoader.home);
    	
    	gameOverPopUp = new PopUpButton(new SimpleButtonListener() {
			@Override
			public void pushButtonListener(SimpleButton btn) {
				//TODO Current level select 
				dispose();
				game.setScreen(new GameScreen(game,currentLevel));
			}
		},(gameWidth/2)-((gameWidth/2)/2),midPointY-(gameHeight/8), gameWidth/2,(gameHeight/4)+4,AssetLoader.failed,AssetLoader.failed,GameState.GAMEOVER);
    	
    	completedPopUp = new PopUpButton(new SimpleButtonListener() {
			@Override
			public void pushButtonListener(SimpleButton btn) {
				//TODO next level select
				dispose();
				if(currentLevel.getNextLevel()!=null) {
					game.setScreen(new GameScreen(game,currentLevel.getNextLevel()));
				}
			
			}
		},(gameWidth/2)-((gameWidth/2)/2),midPointY-(gameHeight/8), gameWidth/2,(gameHeight/4)+4,AssetLoader.completed,AssetLoader.completed,GameState.WON);
    	

    	
    	buttons.add(homeBtn);
    	buttons.add(gameOverPopUp);
    	buttons.add(completedPopUp);
    	
    	renderer = new GameRenderer(board,midPointY,(int) gameHeight,(int) gameWidth);
    	
    	//TODO make a class to handle the audio.
    	MusicAccessor.play(AssetLoader.gameSound);
    	
    	Gdx.input.setInputProcessor(new InputHelper(board,buttons));
    }

    @Override
    public void render(float delta) {
    	System.out.println(CSGame.currentState);
		switch (CSGame.currentState) {
			case GAMEOVER:
				MusicAccessor.play(AssetLoader.defeatSound);
				board.pause();
				renderer.render();
				renderer.renderPopUp(gameOverPopUp);
				pause();
				break;
			case PLAYING:
				MusicAccessor.play(AssetLoader.gameSound);
		    	board.update(delta);
		    	renderer.render();
		    	break;
			case WON:
				MusicAccessor.play(AssetLoader.victorySound);
				renderer.render();
				renderer.renderPopUp(completedPopUp);
				pause();
				break;
			case PAUSE:
		
		}
    	
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
