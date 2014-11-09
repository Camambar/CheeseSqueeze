package cheese.squeeze.ui;

import cheese.squeeze.game.CSGame;
import cheese.squeeze.game.GameState;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PopUpButton extends SimpleButton{
	
	private GameState state;

	public PopUpButton(SimpleButtonListener listen, float x, float y,
			float width, float height, TextureRegion buttonUp,
			TextureRegion buttonDown,GameState state) {
		super(listen, x, y, width, height, buttonUp, buttonDown);
		this.state = state;
	}
	
	public GameState state() {
		return this.state;
	}
	
	@Override
	public boolean isTouchDown(int screenX, int screenY) {
		if(CSGame.currentState.equals(state)) {
			return super.isTouchDown(screenX, screenY);
		}
		return false;
	}
	
	@Override
	public void playButtonSound(){
		if(CSGame.currentState.equals(state)) {
			super.playButtonSound();
		}
	}
	

}
