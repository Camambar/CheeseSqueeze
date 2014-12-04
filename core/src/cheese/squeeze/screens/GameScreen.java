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
import cheese.squeeze.ui.RatingButton;
import cheese.squeeze.ui.SimpleButton;
import cheese.squeeze.ui.SimpleButtonListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {
	
	private GameRenderer renderer;
	private GameBoard board;
	//private SimpleButton homeBtn;
	//private SimpleButton gameOverPopUp;
	//private SimpleButton completedPopUp;
	private SimpleButton gameOverPopUp;
	private SimpleButton gameTutorial;
	private RatingButton rt;
	private InputHelper input;
	private CSGame game;
	private ReportStatus status;
	private Level currentLevel;
	private long actionBeginTime;
	
	public GameScreen(final CSGame game) {
		currentLevel = CSGame.currentLevel;
		init(game);
		this.game = game;
	}

	public GameScreen(final CSGame game,Level l) {
		CSGame.currentLevel = l;
		currentLevel = CSGame.currentLevel;
		init(game);
		this.game = game;
    }
	
	private void init(final CSGame game) {
    	game.saveLevel();
    	actionBeginTime = System.nanoTime();
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
    	
    	
    	SimpleButton restartBtn = new SimpleButton(new SimpleButtonListener() {
    		
			@Override
			public void pushButtonListener(SimpleButton btn) {
				if(CSGame.currentState.equals(GameState.PLAYING)) {
					Report.report(currentLevel, GameState.RESTART);
					Timer t = TimerFactory.getRunningTimer(status);
					t.getState().setState(GameState.RESTART);
					t.stop();
					board.dispose();
					game.analytics();
					game.setScreen(new GameScreen(game,currentLevel));
					
				}
			}
		},gameWidth-14,1,AssetLoader.restart.getWidth(),AssetLoader.restart.getHeight(),AssetLoader.restart,AssetLoader.restart);
    	//XXX X position
    	
    	gameOverPopUp = new PopUpButton(new SimpleButtonListener() {
			@Override
			public void pushButtonListener(SimpleButton btn) {
				//TODO Current level select 

				game.setScreen(new GameScreen(game,currentLevel));
				dispose();
			}
		},(gameWidth/2)-((gameWidth/2)/2),midPointY-(gameHeight/8), gameWidth/2,(gameHeight/4)+4,AssetLoader.failed,AssetLoader.failed,GameState.GAMEOVER);
    	
    	
    	rt = new RatingButton(new SimpleButtonListener() {
			@Override
			public void pushButtonListener(SimpleButton btn) {
				//stuff
				// change game state to wonscreen CSGame.currentState = GameState.PLAYING;
				if(currentLevel.getNextLevel()!=null) {
					//CSGame.currentLevel = currentLevel.getNextLevel();
					//status = new ReportStatus(GameState.WON,currentLevel);
					Report.reportScore(currentLevel, rt.getPoints());
					game.leaderboards();
					game.analytics();
					game.setScreen(new GameScreen(game,currentLevel.getNextLevel()));
					
					dispose();
				}
	    		
			}		
		},(gameWidth/2)-((gameWidth/2)/2),midPointY-(gameHeight/8), gameWidth/2,(gameHeight/4)+4, AssetLoader.starEmpty,
				AssetLoader.starFull,5,AssetLoader.ratingboard,AssetLoader.cont,GameState.WON) ;
    	
    	
    	buttons.add(homeBtn);
    	buttons.add(restartBtn);
    	buttons.add(gameOverPopUp);
    	buttons.add(rt);
    	if(currentLevel.isTutorial()) {
    		CSGame.currentState = GameState.TUTORIAL;
    		TimerFactory.getNewTimer(new ReportStatus(GameState.TUTORIAL,currentLevel)).start();
    		gameTutorial = new PopUpButton(new SimpleButtonListener() {
    			@Override
    			public void pushButtonListener(SimpleButton btn) {
    				//TODO Current level select 
    				//currentLevel.setTutorial(false);
    		    	Timer t = TimerFactory.getRunningTimer(new ReportStatus(GameState.TUTORIAL,currentLevel));
    		    	t.stop();
    		    	CSGame.currentState = GameState.PLAYING;
    		    	status = new ReportStatus(GameState.PLAYING,currentLevel);
    	            TimerFactory.getNewTimer(status).start();
    				board.tutorialEnded();
    	            input.virtualTouchDown(Gdx.input.getX(), Gdx.input.getY());
    			}
    		},board.getTutorialPositionX()-10,board.getTutorialPositionY()-10, (gameHeight/10),(gameHeight/10),AssetLoader.dot,AssetLoader.dot,GameState.TUTORIAL);
    		//,
    		buttons.add(gameTutorial);
    	}
    	else {
    		CSGame.currentState = GameState.COUNTDOWN;
    		/*
    		CSGame.currentState = GameState.PLAYING;
    		status = new ReportStatus(CSGame.currentState,currentLevel);
    		TimerFactory.getNewTimer(status).start();
    		*/
    	}
    	
    	input = new InputHelper(board,buttons);
    	Gdx.input.setInputProcessor(input);
    	renderer = new GameRenderer(board,midPointY,(int) gameHeight,(int) gameWidth,input);
    	
    	//TODO make a class to handle the audio.
    	MusicAccessor.play(AssetLoader.gameSound);
    	
    	
		
	}


    @Override
    public void render(float delta) {
		switch (CSGame.currentState) {
			case COUNTDOWN:
				board.pause();
				renderer.render();
		    	//renderer.renderScore(board.getScore());
				float elapsedTime=(System.nanoTime()-actionBeginTime)/1000000000.0f;
				renderer.renderCount(Math.round(elapsedTime));
		        if(elapsedTime>3f){
		        	CSGame.currentState = GameState.PLAYING;
		    		status = new ReportStatus(CSGame.currentState,currentLevel);
		    		TimerFactory.getNewTimer(status).start();
		    		board.start();
		        }
				break;
			case GAMEOVER:
				if(status.getGameState().equals(GameState.PLAYING)) {
					Report.report(currentLevel, GameState.GAMEOVER);
					Timer t = TimerFactory.getRunningTimer(status);
					t.getState().setState(CSGame.currentState);
					t.stop();
					game.analytics();
					status = new ReportStatus(GameState.GAMEOVERSCREEN,currentLevel);
					TimerFactory.getNewTimer(status).start();
				}
				MusicAccessor.play(AssetLoader.defeatSound);
				board.pause();
				renderer.render();	
				renderer.renderPopUp(gameOverPopUp);
				renderer.renderScoreFinalLOSE(board.getScore());
				//pause();
				break;
			case PLAYING:
				MusicAccessor.play(AssetLoader.gameSound);
		    	board.update(delta);
		    	renderer.render();
		    	renderer.renderScore(board.getScore());
		    	break;
			case WON:
				if(status.getGameState().equals(GameState.PLAYING)) {
					Report.report(currentLevel, GameState.WON);
					Report.reportGameScore(currentLevel, board.getScore());
					Timer t = TimerFactory.getRunningTimer(status);
					t.getState().setState(CSGame.currentState);
					t.stop();
					//game.analytics();
					status = new ReportStatus(GameState.WONSCREEN,currentLevel);
					TimerFactory.getNewTimer(status).start();
					game.saveLevel(currentLevel.getNextLevel());
				}
				MusicAccessor.play(AssetLoader.victorySound);
				renderer.render();
				renderer.renderPopUp(rt);
				//renderer.renderScore(board.getScore());
				renderer.renderScoreFinalWIN(board.getScore());
				//pause();
				break;
			case TUTORIAL:
				renderer.render();
				renderer.renderTutorial(gameTutorial);
				//board.tutorial();
				break;
			case PAUSE:
				break;
		}
    	
    	//Print the fps
    	//Gdx.app.log("GameScreen FPS", (1/delta) + "");
    }


	@Override
    public void resize(int width, int height) {
        //Gdx.app.log("GameScreen", "resizing");
    }

    @Override
    public void show() {
        //Gdx.app.log("GameScreen", "show called");
    }

    @Override
    public void hide() {
        Gdx.app.log("GameScreen", "hide called");   
    	//FIXME did remove this is important?
        board.dispose();
//    	TimerFactory.pauseAll(currentLevel);
    }

    @Override
    public void pause() {
    	//game.dispose();
    	TimerFactory.pauseAll(currentLevel);
    	
        Gdx.app.log("GameScreen", "pause called");        
    }

    @Override
    public void resume() {
    	TimerFactory.resumeAll();
    	//TimerFactory.getNewTimer(status).start();
    	//TimerFactory.resumeAll();
       // Gdx.app.log("GameScreen", "resume called");       
    }

    @Override
    public void dispose() {
    	Timer t = TimerFactory.getRunningTimer(status);
    	if(t!= null) {
        	t.stop();
    	}
    	board.dispose();
    	//game.analytics();
    }

}
