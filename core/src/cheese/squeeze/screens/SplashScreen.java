package cheese.squeeze.screens;

import cheese.squeeze.game.*;
import cheese.squeeze.helpers.AssetLoader;

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
	
	public SplashScreen(CSGame game) {
		splashImage = new Image(AssetLoader.logo);
		splashImage.setSize((Gdx.graphics.getWidth()/1.5f), (Gdx.graphics.getHeight()/1.5f));
		splashImage.setPosition((Gdx.graphics.getWidth()/2)-splashImage.getWidth()/2, (Gdx.graphics.getHeight()/3));
		
        this.game = game;
        stage = new Stage();
        font=new BitmapFont();
        batch=new SpriteBatch();

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
        
        AssetLoader.lastLoadingStep();
        
        if(AssetLoader.update()){ // check if all files are loaded
            
            if(animationDone){ // when the animation is finished, go to MainMenu()
                //AssetLoader.setAtlas(); // uses files to create menuSkin
                
                //AssetLoader.load();
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
        
		AssetLoader.queueLoading();
	
		
	}
	
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		AssetLoader.disposeSplash();
        stage.dispose();
	}

}
