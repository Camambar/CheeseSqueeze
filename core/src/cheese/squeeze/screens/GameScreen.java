package cheese.squeeze.screens;

import java.util.ArrayList;

import cheese.squeeze.game.CSGame;
import cheese.squeeze.game.CSGame.GameState;
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
	
    public GameScreen(CSGame game) {
        Gdx.app.log("GameScreen", "Attached");
        CSGame.currentState = GameState.PLAYING;
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
    	ArrayList<SimpleButton> buttons = new ArrayList<SimpleButton>();   
    	
    	SimpleButton homeBtn = new SimpleButton(new SimpleButtonListener() {
    		
			@Override
			public void pushButtonListener(SimpleButton btn) {
				
			}
		},AssetLoader.home.getX(),AssetLoader.home.getY(),AssetLoader.home.getWidth(),AssetLoader.home.getHeight(),AssetLoader.home,AssetLoader.home);
    	
    	gameOverPopUp = new PopUpButton(new SimpleButtonListener() {
			@Override
			public void pushButtonListener(SimpleButton btn) {
				System.out.println("game over ok");
			}
		},(gameWidth/2)-((gameWidth/2)/2),midPointY-(gameHeight/4), gameWidth/2,gameHeight/2,AssetLoader.failed,AssetLoader.failed,GameState.GAMEOVER);
    	
    	completedPopUp = new PopUpButton(new SimpleButtonListener() {
			@Override
			public void pushButtonListener(SimpleButton btn) {
				System.out.println("game Won oke");
			}
		},(gameWidth/2)-((gameWidth/2)/2),midPointY-(gameHeight/4), gameWidth/2,gameHeight/2,AssetLoader.completed,AssetLoader.completed,GameState.WON);
    	

    	
    	buttons.add(homeBtn);
    	buttons.add(gameOverPopUp);
    	buttons.add(completedPopUp);
    	
    	renderer = new GameRenderer(board,midPointY,(int) gameHeight,(int) gameWidth);
    	
    	//TODO make a class to handle the audio.
    	MusicAccessor.play(AssetLoader.gameSound);
    	
    	Gdx.input.setInputProcessor(new InputHelper(board,game,buttons));
    }

    @Override
    public void render(float delta) {
    	System.out.println(CSGame.currentState);
		switch (CSGame.currentState) {
			case GAMEOVER:
				renderer.render();
				renderer.renderPopUp(gameOverPopUp);
				pause();
				break;
			case PLAYING:
		    	board.update(delta);
		    	renderer.render();
		    	break;
			case WON:
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
