package cheese.squeeze.screens;

import java.util.ArrayList;

import cheese.squeeze.game.CSGame;
import cheese.squeeze.game.Level;
import cheese.squeeze.game.GameState;
import cheese.squeeze.helpers.AssetLoader;
import cheese.squeeze.helpers.InputHelperMenu;
import cheese.squeeze.helpers.TimerFactory;
import cheese.squeeze.tweenAccessors.MusicAccessor;
import cheese.squeeze.tweenAccessors.SoundAccessor;
import cheese.squeeze.ui.SimpleButton;
import cheese.squeeze.ui.SimpleButtonListener;
import cheese.squeeze.ui.SwitchButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class MenuScreen implements Screen{
	

	private SpriteBatch batcher;
	private ArrayList<SimpleButton> menuButtons;
	private boolean musicOn = MusicAccessor.isOn();
	private boolean soundOn = SoundAccessor.isOn();

	public MenuScreen(final CSGame game) {
		
		TimerFactory.getNewTimer(GameState.MENU).start();
		CSGame.currentState = GameState.MENU;
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
				game.setScreen(new GameScreen(game));
			}
		},AssetLoader.play.getX(),AssetLoader.play.getY(),
        		AssetLoader.play.getWidth()*AssetLoader.play.getScaleX(),AssetLoader.play.getHeight()*AssetLoader.play.getScaleY(),AssetLoader.play,AssetLoader.play);
		
		SwitchButton soundButton = new SwitchButton(new SimpleButtonListener() {
    		
			@Override
			public void pushButtonListener(SimpleButton btn) {
				SoundAccessor.setEnabled(!((SwitchButton)btn).getVal());
				SoundAccessor.play(AssetLoader.buttonSound);
			}
		},AssetLoader.sound_on.getX(),AssetLoader.sound_on.getY(),
        		AssetLoader.sound_on.getWidth()*AssetLoader.sound_on.getScaleX(),AssetLoader.sound_on.getHeight()*AssetLoader.sound_on.getScaleY(),AssetLoader.sound_on,AssetLoader.sound_off,soundOn,"sound");
		
		SwitchButton musicButton = new SwitchButton(new SimpleButtonListener() {
			
			@Override
			public void pushButtonListener(SimpleButton btn) {
				MusicAccessor.setEnabled(!((SwitchButton)btn).getVal());
				MusicAccessor.play(AssetLoader.menuSound);
			}
		},AssetLoader.music_on.getX(),AssetLoader.music_on.getY(),
        		AssetLoader.music_on.getWidth()*AssetLoader.music_on.getScaleX(),AssetLoader.music_on.getHeight()*AssetLoader.music_on.getScaleY(),AssetLoader.music_on,AssetLoader.music_off,musicOn,"music");
        
		menuButtons.add(playButton);
        menuButtons.add(soundButton);
        menuButtons.add(musicButton);
	}

	@Override
	public void render(float delta) {
		batcher.begin();
		AssetLoader.menuBg.draw(batcher);
		//batcher.draw(AssetLoader.menuBg, 0, 0);
		batcher.end();
		
		batcher.begin();
		batcher.enableBlending();
		AssetLoader.logo_shadow.draw(batcher);
		for(SimpleButton b:menuButtons) {
			b.draw(batcher);
		}
		//batcher.draw(AssetLoader.menuBg, 0, 0);
		batcher.end();

		// TODO render the game play buttend ed.
		
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		System.out.println("pauze");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		TimerFactory.getRunningTimer(GameState.MENU).stop();
	}

}
