package cheese.squeeze.screens;

import cheese.squeeze.game.*;
import cheese.squeeze.helpers.AssetLoader;
import cheese.squeeze.helpers.Timer;
import cheese.squeeze.helpers.TimerFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class SplashScreen implements Screen{
	
    private CSGame game;
    public boolean animationDone = false;
	private Stage stage;
	private Image splashImage;
	private BitmapFont font;
	private SpriteBatch batch;
	private ReportStatus status = new ReportStatus(GameState.LOADING);
	private AssetLoader loader;
	
	public SplashScreen(CSGame game, AssetLoader loader) {
		TimerFactory.getNewTimer(status).start();
		//TimerFactory.getNewTimer(new ReportStatus(GameState.GAMESTART)).start();
		splashImage = new Image(AssetLoader.logo);
		splashImage.setSize((Gdx.graphics.getWidth()/1.23f), (Gdx.graphics.getHeight()/1.77f));
		splashImage.setPosition((Gdx.graphics.getWidth()/2)-splashImage.getWidth()/2, (Gdx.graphics.getHeight()/2.2f));
		
        this.game = game;
        stage = new Stage();
        font= AssetLoader.font;
        batch=new SpriteBatch();
        
        this.loader = loader;

    }

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        batch.begin();
        
        AssetLoader.empty.draw(batch, 20, Gdx.graphics.getHeight()/3,Gdx.graphics.getWidth()-40 , 30);
        AssetLoader.full.draw(batch, 20, Gdx.graphics.getHeight()/3, AssetLoader.getProcess()*(Gdx.graphics.getWidth()-40), 30);
        font.drawMultiLine(batch,(int)(AssetLoader.getProcess()*100)+"% loaded",Gdx.graphics.getWidth()/2,(Gdx.graphics.getHeight()/3)+22,0, BitmapFont.HAlignment.CENTER);
        
        batch.end();
        
        loader.lastLoadingStep();
        
        if(AssetLoader.update()){ // check if all files are loaded
            
            if(animationDone){ // when the animation is finished, go to MainMenu()
                //AssetLoader.setAtlas(); // uses files to create menuSkin
                
                //AssetLoader.load();
            	this.dispose();
                game.setScreen(new MenuScreen(game));
                
            }
            
            
        }
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		
        stage.addActor(splashImage);
        
        splashImage.addAction(Actions.sequence(Actions.alpha(0)
                ,Actions.fadeIn(0.75f),Actions.delay(1.5f),Actions.run(new Runnable() {
            @Override
            public void run() {
                animationDone = true;
            }
        })));
        
        loader.queueLoading();
	
		
	}
	
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		//game.dispose();
		TimerFactory.pauseAll();
	}

	@Override
	public void resume() {
		TimerFactory.resumeAll();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		AssetLoader.disposeSplash();
		font.dispose();
		Timer t = TimerFactory.getRunningTimer(status);
		t.stop();
		System.out.println(t);
        stage.dispose();
	}

}
