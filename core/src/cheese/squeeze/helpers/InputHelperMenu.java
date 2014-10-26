package cheese.squeeze.helpers;

import java.util.ArrayList;










import cheese.squeeze.tweenAccessors.MusicAccessor;
import cheese.squeeze.tweenAccessors.SoundAccessor;
import cheese.squeeze.ui.SimpleButton;
import cheese.squeeze.ui.SwitchButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;



public class InputHelperMenu implements InputProcessor {
	
	private ArrayList<SimpleButton> menuButtons;
	private OrthographicCamera cam;
	
	
	
	public InputHelperMenu(ArrayList<SimpleButton> menuButtons) {
		this.menuButtons = menuButtons;
		cam = new OrthographicCamera();
		cam.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		//if(playButton.isTouchDown(screenX, screenY)) {
		//	game.setScreen(new GameScreen());
		//}
		
		if(pointer == 0){
			Vector3 unp = cam.unproject(new Vector3(screenX,screenY,0));
			for(SimpleButton b : menuButtons) {
				if(b.isTouchDown((int) unp.x,(int) unp.y)){
					
				}
				
				//TODO check if button is play
			}
		}
		return true;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(pointer == 0) {
			Vector3 unp = cam.unproject(new Vector3(screenX,screenY,0));
			for(SimpleButton b : menuButtons) {
				if(b.isTouchUp((int) unp.x,(int) unp.y)) {
					
				}
					
				//TODO check if button is play
			}
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
