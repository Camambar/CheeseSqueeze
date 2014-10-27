package cheese.squeeze.screens;

import java.util.ArrayList;

import cheese.squeeze.game.CSGame;
import cheese.squeeze.game.CSGame.GameState;
import cheese.squeeze.helpers.AssetLoader;
import cheese.squeeze.helpers.InputHelperMenu;
import cheese.squeeze.tweenAccessors.MusicAccessor;
import cheese.squeeze.tweenAccessors.SoundAccessor;
import cheese.squeeze.ui.PlayButton;
import cheese.squeeze.ui.SimpleButton;
import cheese.squeeze.ui.SwitchButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class MenuScreen implements Screen{
	

	private SpriteBatch batcher;
	private ArrayList<SimpleButton> menuButtons;
	SwitchButton soundButton;
	SwitchButton musicButton;
	private boolean musicOn = MusicAccessor.isOn();
	private boolean soundOn = SoundAccessor.isOn();

	public MenuScreen(CSGame game) {
		
		CSGame.currentState = GameState.MENU;
		
		batcher = new SpriteBatch();
		menuButtons = new ArrayList<SimpleButton>();
		PlayButton playButton = new PlayButton(AssetLoader.play.getX(),AssetLoader.play.getY(),
        		AssetLoader.play.getWidth()*AssetLoader.play.getScaleX(),AssetLoader.play.getHeight()*AssetLoader.play.getScaleY(),AssetLoader.play,AssetLoader.play,game);
		soundButton = new SwitchButton(AssetLoader.sound_on.getX(),AssetLoader.sound_on.getY(),
        		AssetLoader.sound_on.getWidth()*AssetLoader.sound_on.getScaleX(),AssetLoader.sound_on.getHeight()*AssetLoader.sound_on.getScaleY(),AssetLoader.sound_on,AssetLoader.sound_off,soundOn,"sound");
		musicButton = new SwitchButton(AssetLoader.music_on.getX(),AssetLoader.music_on.getY(),
        		AssetLoader.music_on.getWidth()*AssetLoader.music_on.getScaleX(),AssetLoader.music_on.getHeight()*AssetLoader.music_on.getScaleY(),AssetLoader.music_on,AssetLoader.music_off,musicOn,"music");
        
		menuButtons.add(playButton);
        menuButtons.add(soundButton);
        menuButtons.add(musicButton);
		Gdx.input.setInputProcessor(new InputHelperMenu(menuButtons));
		
		//TODO make a dedicated class to manange the audio
		
		MusicAccessor.play(AssetLoader.menuSound);
		//AssetLoader.gameSound.stop();
		//long id = AssetLoader.menuSound.play();
		//System.out.println(id);
	}

	@Override
	public void render(float delta) {
		if(SoundAccessor.isOn() != soundButton.getVal()) {
			SoundAccessor.setEnabled(soundButton.getVal());
		}
		if(MusicAccessor.isOn() != musicButton.getVal()) {
			MusicAccessor.setEnabled(musicButton.getVal());
		}

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
