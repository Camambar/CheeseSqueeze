package cheese.squeeze.ui;

import cheese.squeeze.game.CSGame;
import cheese.squeeze.screens.GameScreen;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayButton extends SimpleButton{
	
	private CSGame game;

	public PlayButton(float x, float y, float width, float height,
			TextureRegion buttonUp, TextureRegion buttonDown, CSGame game) {
		super(x, y, width, height, buttonUp, buttonDown);
		this.game= game;
	}

	
	@Override
	public boolean isTouchUp(int screenX,int screenY) {
		if(super.isTouchUp(screenX, screenY)){ 
			game.setScreen(new GameScreen());
			return true;
		}
		return false;
	}
}
