package cheese.squeeze.screens;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import cheese.squeeze.game.CSGame;
import cheese.squeeze.game.GameState;
import cheese.squeeze.game.Level;
import cheese.squeeze.game.ReportStatus;
import cheese.squeeze.helpers.AssetLoader;
import cheese.squeeze.helpers.InputHelperMenu;
import cheese.squeeze.helpers.Timer;
import cheese.squeeze.helpers.TimerFactory;
import cheese.squeeze.tweenAccessors.MusicAccessor;
import cheese.squeeze.tweenAccessors.SoundAccessor;
import cheese.squeeze.ui.RatingButton;
import cheese.squeeze.ui.SimpleButton;
import cheese.squeeze.ui.SimpleButtonListener;
import cheese.squeeze.ui.SwitchButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class MenuScreen implements Screen{
	

	private SpriteBatch batcher;
	private ArrayList<SimpleButton> menuButtons;
	private boolean musicOn = MusicAccessor.isOn();
	private boolean soundOn = SoundAccessor.isOn();
	private String latestVersion;
	private CSGame game;
	private ReportStatus status = new ReportStatus(GameState.MENU);

	
	
	public MenuScreen(final CSGame game) {


		
		CSGame.currentState = GameState.MENU;
		TimerFactory.getNewTimer(status).start();
		
		this.game = game;
		
		
		try {
			URL url = new URL(game.checkVersionURL);
			Scanner s = new Scanner(url.openStream());
			s.nextLine();
			latestVersion = s.nextLine();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		addButtons(game);
		batcher = new SpriteBatch();
		Gdx.input.setInputProcessor(new InputHelperMenu(menuButtons));
		MusicAccessor.play(AssetLoader.menuSound);
		
		
		
		
		
		
		
		
	}

	private void addButtons(final CSGame game) {
		menuButtons = new ArrayList<SimpleButton>();
		
		SimpleButton playButton = new SimpleButton(new SimpleButtonListener() {
			
			@Override
			public void pushButtonListener(SimpleButton btn) {
				dispose();
				game.setScreen(new GameScreen(game,Level.LEVEL2));
			}
		},AssetLoader.play.getX(),AssetLoader.play.getY(),
        		AssetLoader.play.getWidth()*AssetLoader.play.getScaleX(),AssetLoader.play.getHeight()*AssetLoader.play.getScaleY(),AssetLoader.play,AssetLoader.play);
		
		SwitchButton soundButton = new SwitchButton(new SimpleButtonListener(){
    		
			@Override
			public void pushButtonListener(SimpleButton btn) {
				SoundAccessor.setEnabled(!((SwitchButton)btn).getVal());
				SoundAccessor.play(AssetLoader.buttonSound);
								
			}
		},AssetLoader.sound_on.getX(),AssetLoader.sound_on.getY(),
        		AssetLoader.sound_on.getWidth()*AssetLoader.sound_on.getScaleX(),AssetLoader.sound_on.getHeight()*AssetLoader.sound_on.getScaleY(),AssetLoader.sound_on,AssetLoader.sound_off,soundOn,"sound");
		soundButton.setSpecial(true);
		
		SwitchButton musicButton = new SwitchButton(new SimpleButtonListener() {
			
			@Override
			public void pushButtonListener(SimpleButton btn) {
				MusicAccessor.setEnabled(!((SwitchButton)btn).getVal());
				MusicAccessor.play(AssetLoader.menuSound);
			}
		},AssetLoader.music_on.getX(),AssetLoader.music_on.getY(),
        		AssetLoader.music_on.getWidth()*AssetLoader.music_on.getScaleX(),AssetLoader.music_on.getHeight()*AssetLoader.music_on.getScaleY(),AssetLoader.music_on,AssetLoader.music_off,musicOn,"music");
        
		if(this.latestVersion != null && !this.latestVersion.equals(game.version)) {
			
		
			SimpleButton versionButton = new SimpleButton(new SimpleButtonListener() {
				
				@Override
				public void pushButtonListener(SimpleButton btn) {
					//try {
					
						Gdx.net.openURI(game.updateURL);
						
						//Desktop.getDesktop().browse(new URI(game.updateURL));
					//} catch (IOException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					//} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					//.}
				}
			},AssetLoader.version.getX(),AssetLoader.version.getY(),
	        		AssetLoader.version.getWidth()*AssetLoader.version.getScaleX(),AssetLoader.version.getHeight()*AssetLoader.version.getScaleY(),AssetLoader.version,AssetLoader.version);
			
			menuButtons.add(versionButton);
		}
		menuButtons.add(playButton);
        menuButtons.add(soundButton);
        menuButtons.add(musicButton);
	}

	@Override
	public void render(float delta) {
		batcher.begin();
		AssetLoader.menuBg.draw(batcher);
		
		batcher.end();
		
		batcher.begin();
		batcher.enableBlending();
		AssetLoader.logo_shadow.draw(batcher);
		for(SimpleButton b:menuButtons) {
			b.draw(batcher);
		}

		
		
		
		
		
		
		batcher.end();
		


		// TODO render the game play buttend ed.
		
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		Gdx.app.log("Menu screen", "show");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		Gdx.app.log("Menu screen", "hide");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		Gdx.app.log("Menu screen", "pause");
		game.analytics();
		//game.dispose();
		//TimerFactory.pauseAll();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		Gdx.app.log("Menu screen", "resume");
		TimerFactory.getNewTimer(status).start();
		//TimerFactory.resumeAll();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		Gdx.app.log("Menu screen", "dispose");
		// TODO Auto-generated method stub
		Timer t = TimerFactory.getRunningTimer(status);
		t.stop();
		//System.out.println(t);
	}

}
