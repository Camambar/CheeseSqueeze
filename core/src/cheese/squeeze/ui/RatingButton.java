package cheese.squeeze.ui;

import java.util.ArrayList;

import cheese.squeeze.game.CSGame;
import cheese.squeeze.game.GameState;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RatingButton extends SimpleButton{

	private TextureRegion background;
	private float width;
	private float height;
	private static final float BORDER_OFFSET = 3;
	private ArrayList<SwitchButton> btns;
	private float x;
	private float y;
	private GameState state;
	private boolean isRated = false;
	//private TextureRegion cont;
	private SimpleButton cont;
	private int points;
	
	public RatingButton(SimpleButtonListener listen, float x, float y,
			float width, float height, TextureRegion buttonUp,
			TextureRegion buttonDown,int amount,TextureRegion background,TextureRegion cont ,GameState state) {
		super(new SimpleButtonListener(){
			@Override
			public void pushButtonListener(SimpleButton btn) {
				
			}
		}, height, height, height, height, background, background);
		this.btns = new ArrayList<SwitchButton>();
		this.state = state;
		this.background = background;
        this.x = x;
        this.y = y-height/4;
		this.width = width;
		this.height = height;
		float unitWidth = (width/amount) - (1.5f*BORDER_OFFSET);
		float unitHeight = (height/3.2f) - 2 * BORDER_OFFSET;
		for(int i= 1; i < amount+1;i++) {
			//TODO x and y positions
			//TODO test this button shizzle
			btns.add(new SwitchButton(new SimpleButtonListener(){
				@Override
				public void pushButtonListener(SimpleButton btn) {
					
				}
			},x+BORDER_OFFSET+((i-1)*(unitWidth+BORDER_OFFSET)), y+(2*height/4)-BORDER_OFFSET, unitWidth, unitHeight, buttonDown,buttonUp, false, "star"+i));
		}
		 this.cont = new SimpleButton(listen,x, y+(3*height/4),width,height/4,cont,cont);
	}
	
	@Override
    public void draw(SpriteBatch batcher) {
    	batcher.draw(background, x, y,width,height);
    	for(SwitchButton btn : btns) {
    		btn.draw(batcher);
    	}
    	if(isRated){
    		cont.draw(batcher);
    	}
    }
	
	@Override
    public boolean isTouchDown(int screenX, int screenY) {
		if(CSGame.currentState.equals(state)) {
	        for(SwitchButton sw : btns) {
	        	if(sw.isTouchDown(screenX, screenY)){
	        		int index = btns.indexOf(sw);
	        		touchPrev(index);
	        		isRated = true;
	        		super.isTouchDown(screenX, screenY);
	        		return true;
	        	}
	        }
	        if(isRated && cont.isTouchDown(screenX, screenY)) {
	        		return true;
	        }
		}
        return false;
    }
	

	private void touchPrev(int indexOf) {
		int i =0;
		while(i <= btns.size()-1) {
			if (i<=indexOf) {
				btns.get(i).setPressed(true);
				points = i+1;
			}
			else {
				btns.get(i).setPressed(false);
			}
			i++;
		}
	}

	@Override
	public void playButtonSound(){
		if(CSGame.currentState.equals(state)) {
			super.playButtonSound();
		}
	}
	
	public int getPoints() {
		return points;
	}
    

}
