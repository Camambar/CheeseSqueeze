package cheese.squeeze.screens;

import java.util.ArrayList;

import cheese.squeeze.game.CSGame;
import cheese.squeeze.game.GameState;
import cheese.squeeze.game.Level;
import cheese.squeeze.game.Report;
import cheese.squeeze.game.ReportStatus;
import cheese.squeeze.gameLogic.GameBoard;
import cheese.squeeze.gameworld.GameRenderer;
import cheese.squeeze.helpers.AssetLoader;
import cheese.squeeze.helpers.InputHelper;
import cheese.squeeze.helpers.Timer;
import cheese.squeeze.helpers.TimerFactory;
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
	private SimpleButton gameTutorial;
	private InputHelper input;
	
	public GameScreen(final CSGame game) {
		currentLevel = CSGame.currentLevel;
		init(game);
	}

	public GameScreen(final CSGame game,Level l) {
		currentLevel = l;
		init(game);
    }
	
	private void init(final CSGame game) {
        Gdx.app.log("GameScreen", "Attached");
        //Calculate the starting positions
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
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
				Report.report(currentLevel, GameState.GAMEOVER);
				dispose();
				game.setScreen(new GameScreen(game,currentLevel));
			}
		},(gameWidth/2)-((gameWidth/2)/2),midPointY-(gameHeight/8), gameWidth/2,(gameHeight/4)+4,AssetLoader.failed,AssetLoader.failed,GameState.GAMEOVER);
    	
    	completedPopUp = new PopUpButton(new SimpleButtonListener() {
			@Override
			public void pushButtonListener(SimpleButton btn) {
				//TODO next level select
				
				Report.report(currentLevel, GameState.WON);
				dispose();
				if(currentLevel.getNextLevel()!=null) {
					CSGame.currentLevel = currentLevel.getNextLevel();
					game.setScreen(new GameScreen(game,currentLevel.getNextLevel()));
				}
			
			}
		},(gameWidth/2)-((gameWidth/2)/2),midPointY-(gameHeight/8), gameWidth/2,(gameHeight/4)+4,AssetLoader.completed,AssetLoader.completed,GameState.WON);
    	
    	
    	
    	buttons.add(homeBtn);
    	buttons.add(gameOverPopUp);
    	buttons.add(completedPopUp);
    	if(currentLevel.isTutorial()) {
    		CSGame.currentState = GameState.TUTORIAL;
    		TimerFactory.getNewTimer(new ReportStatus(GameState.TUTORIAL,currentLevel)).start();
    		gameTutorial = new PopUpButton(new SimpleButtonListener() {
    			@Override
    			public void pushButtonListener(SimpleButton btn) {
    				//TODO Current level select 
    				currentLevel.setTutorial(false);
    		    	Timer t = TimerFactory.getRunningTimer(new ReportStatus(GameState.TUTORIAL,currentLevel));
    		    	t.stop();
    		    	CSGame.currentState = GameState.PLAYING;
    	            TimerFactory.getNewTimer(new ReportStatus(GameState.PLAYING,currentLevel)).start();
    				board.tutorialEnded();
    	            input.virtualTouchDown((int)gameTutorial.getScreenX(), (int) gameTutorial.getScreenY());
    			}
    		},board.getTutorialPositionX()-10,board.getTutorialPositionY()-10, (gameHeight/10),(gameHeight/10),AssetLoader.dot,AssetLoader.dot,GameState.TUTORIAL);
    		//,
    		buttons.add(gameTutorial);
    	}
    	else {
    		CSGame.currentState = GameState.PLAYING;
            TimerFactory.getNewTimer(new ReportStatus(GameState.PLAYING,currentLevel)).start();

    	}
    	
    	renderer = new GameRenderer(board,midPointY,(int) gameHeight,(int) gameWidth);
    	
    	//TODO make a class to handle the audio.
    	MusicAccessor.play(AssetLoader.gameSound);
    	
    	input = new InputHelper(board,buttons);
    	Gdx.input.setInputProcessor(input);
		
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
				//renderer.renderScoreFinalLOSE(board.getScore());
				pause();
				break;
			case PLAYING:
				MusicAccessor.play(AssetLoader.gameSound);
		    	board.update(delta);
		    	renderer.render();
		    	//renderer.renderScore(board.getScore());
		    	break;
			case WON:
				MusicAccessor.play(AssetLoader.victorySound);
				renderer.render();
				renderer.renderPopUp(completedPopUp);
				//renderer.renderScore(board.getScore());
				//renderer.renderScoreFinalWIN(board.getScore());
				pause();
				break;
			case TUTORIAL:
				renderer.render();
				renderer.renderTutorial(gameTutorial);
				//board.tutorial();
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
    	TimerFactory.pauseAll();
        Gdx.app.log("GameScreen", "pause called");        
    }

    @Override
    public void resume() {
    	TimerFactory.resumeAll();
        Gdx.app.log("GameScreen", "resume called");       
    }

    @Override
    public void dispose() {
    	Timer t = TimerFactory.getRunningTimer(new ReportStatus(GameState.PLAYING,currentLevel));
    	if(t!= null) {
        	t.getState().setState(CSGame.currentState);
        	t.stop();
    	}

    }

}
