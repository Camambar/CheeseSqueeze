package cheese.squeeze.helpers;

import java.util.ArrayList;

import cheese.squeeze.game.CSGame;
import cheese.squeeze.game.GameState;
import cheese.squeeze.gameLogic.GameBoard;
import cheese.squeeze.gameObjects.HorizontalLine;
import cheese.squeeze.gameObjects.VerticalLine;
import cheese.squeeze.screens.GameScreen;
import cheese.squeeze.screens.MenuScreen;
import cheese.squeeze.ui.PopUpButton;
import cheese.squeeze.ui.SimpleButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class InputHelper implements InputProcessor {

	private GameBoard board;
	private HorizontalLine l;
	private HorizontalLine gl;
	private Vector3 touchCoord;
	boolean touchedDown = false;
	private ArrayList<SimpleButton> buttons;
	private boolean buttonPressed = true;

	public InputHelper(GameBoard board,
			ArrayList<SimpleButton> buttons) {
		l = new HorizontalLine();
		gl = new HorizontalLine();
		this.board = board;
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
		touchCoord = new Vector3(screenX, screenY,0);
		if(!touchButton(screenX, screenY)){	
			switch (CSGame.currentState) {
			case GAMEOVER:
	
				break;
	
			case PLAYING:
				touchDownPlaying(screenX, screenY, pointer, button);
				break;
			case PAUSE:
	
				break;
	
			}
		}
		
		return true;

	}

	public boolean touchDownPlaying(int screenX, int screenY, int pointer,
			int button) {

		if (pointer == 0) {
			if (!touchedDown) {
				board.setClickedPosition(new Vector2(screenX, screenY));
				touchedDown = true;
				gl.onClick(screenX, screenY);
			}

			// gl.onClick(screenX, screenY);
			if (!gl.isdrawable()) {
				gl.onClick(screenX + 1, screenY);
				board.setGesturedLine(gl);
			}
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(!buttonPressed) {	
			switch (CSGame.currentState) {
			case GAMEOVER:
	
				break;
			case PLAYING:
				touchUpPlaying(screenX, screenY, pointer, button);
				break;
			case PAUSE:
	
				break;
	
			}
		}
		return true;
	}

	public boolean touchUpPlaying(int screenX, int screenY, int pointer,
			int button) {
		if (pointer == 0) {
			if (!board.isClearable(screenX, screenY)) {
				board.addHLine(gl.clone());
				// l.clear();
			}
			if (!Gdx.input.isTouched()) {
				gl.clear();
				touchedDown = false;
			}
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(!buttonPressed) {	
			if (pointer == 0) {
				switch (CSGame.currentState) {
				case GAMEOVER:
	
					break;
				case PLAYING:
					touchDraggedPlaying(screenX, screenY, pointer);
					break;
				case PAUSE:
	
					break;
				}
			}
		}
		return true;
	}

	public boolean touchDraggedPlaying(int screenX, int screenY, int pointer) {
		if (pointer == 0) {
			gl.onDrag(screenX, screenY);
			board.setGesturedLineDragged(gl);
		}
		return true;
	}

	private boolean touchButton(int x, int y) {
		Vector2 vec2 = board.unProject(x, y);
		for(SimpleButton btn: buttons) {
			if(btn.isTouchDown((int) vec2.x, (int) vec2.y)) {
				buttonPressed = true;
				return true;
			}
		}
		buttonPressed = false;
		return false;
	}
	
	public void virtualTouchDown(int x,int y) {
		buttonPressed = false;
		/*
		Vector2 v = board.unProject(x, y);
		this.touchDown((int)v.x,(int)v.y,0,0);
		this.touchUp((int)v.x,(int)v.y,0,0);
		*/
		this.touchDown(x,y,0,0);
		this.touchUp(x,y,0,0);
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		this.touchDownPlaying(screenX, screenY, 0, 0);
		return this.touchDraggedPlaying(screenX, screenY, 0);
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public Vector3 getTouchCoord() {
		if (touchCoord!=null) {
			Vector3 v= touchCoord.cpy();
			touchCoord = null;
			return v;
		}
		return null;
		
	}


}
