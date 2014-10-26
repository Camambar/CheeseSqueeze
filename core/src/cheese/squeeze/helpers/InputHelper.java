package cheese.squeeze.helpers;

import java.util.ArrayList;

import cheese.squeeze.game.CSGame;
import cheese.squeeze.gameLogic.GameBoard;
import cheese.squeeze.gameObjects.HorizontalLine;
import cheese.squeeze.gameObjects.VerticalLine;
import cheese.squeeze.screens.GameScreen;
import cheese.squeeze.screens.MenuScreen;
import cheese.squeeze.ui.SimpleButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class InputHelper implements InputProcessor{
	
	private GameBoard board;
	private HorizontalLine l;
	private HorizontalLine gl;
	boolean touchedDown = false;
	private CSGame game;
	private ArrayList<SimpleButton> buttons;
	
	public InputHelper(GameBoard board, CSGame game, ArrayList<SimpleButton> buttons){
		l = new HorizontalLine();
		gl = new HorizontalLine();
		this.board = board;
		this.game = game;
		this.buttons = buttons;
		
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
		//TODO prevent multitouch crash with Gdx.input.isTouched(pointer)
		// no it works with pointer == 0 the first touch 
		if (pointer == 0) {
			
			//back is touched!
			//FOR NOW THERE IS ONE BUTTON
			//TODO make this check if this button in the list of buttons is thehome buttone
			Vector2 vec2 = board.unProject(screenX, screenY);
			if(buttons.get(0).isClicked((int)vec2.x, (int)vec2.y)) {
				game.setScreen(new MenuScreen(game));
			}
			
			else {
			if (!touchedDown) {
				board.setClickedPosition(new Vector2(screenX,screenY));
				touchedDown = true;
				gl.onClick(screenX, screenY);
			}
			
			//gl.onClick(screenX, screenY);
			if(!gl.isdrawable()) {
				gl.onClick(screenX+1, screenY);
				board.setGesturedLine(gl);
			}
			}
				//l.onClick(screenX,screenY);
				//l.onClick(screenX,screenY);
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (pointer == 0) {
			if(!board.isClearable(screenX,screenY)) {
				board.addHLine(gl.clone());
				//l.clear();
			}
			if(!Gdx.input.isTouched()) {
				gl.clear();
				touchedDown = false;
			}
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(pointer == 0) {
			gl.onDrag(screenX, screenY);
			board.setGesturedLineDragged(gl);
		}
		return true;
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
