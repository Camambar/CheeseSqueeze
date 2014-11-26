package cheese.squeeze.ui;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RatingButton {

	private TextureRegion background;
	private float width;
	private float height;
	private static final float BORDER_OFFSET = 3;
	private ArrayList<SwitchButton> btns;
	private float x;
	private float y;
	
	public RatingButton(SimpleButtonListener listen, float x, float y,
			float width, float height, TextureRegion buttonUp,
			TextureRegion buttonDown,int amount,TextureRegion background) {
		this.background = background;
        this.x = x;
        this.y = y;
		this.width = width;
		this.height = height;
		float unitWidth = (width/amount) - (amount+1)*BORDER_OFFSET;
		float unitHeight = height - 2 * BORDER_OFFSET;
		for(int i= 1; i < amount;i++) {
			//TODO x and y positions
			//TODO test this button shizzle
			btns.add(new SwitchButton(listen,x+BORDER_OFFSET+((amount-1)*(unitWidth+BORDER_OFFSET)), y-BORDER_OFFSET, unitWidth, unitHeight, buttonUp, buttonDown, false, "star"+i));
		}
		
	}
	
    public void draw(SpriteBatch batcher) {
    	batcher.draw(background, x, y,width,height);
    	for(SwitchButton btn : btns) {
    		btn.draw(batcher);
    	}
    }
	

}
