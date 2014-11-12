package cheese.squeeze.ui;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RatingButton {

	private TextureRegion background;
	private ArrayList<SwitchButton> btns;
	
	public RatingButton(SimpleButtonListener listen, float x, float y,
			float width, float height, TextureRegion buttonUp,
			TextureRegion buttonDown,int amount,TextureRegion background) {
		
		for(int i= 1; i < amount;i++) {
			//TODO
		}
		
	}
	
	

}
