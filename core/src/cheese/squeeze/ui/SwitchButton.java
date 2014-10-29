package cheese.squeeze.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SwitchButton extends SimpleButton{

    
    private boolean val; // true is up false is down
    private String name;

	public SwitchButton(SimpleButtonListener listen, float x, float y, float width, float height,
			TextureRegion buttonUp, TextureRegion buttonDown, boolean on, String string) {
		super(listen, x, y, width, height, buttonUp, buttonDown);
		val = on;
		this.name = string;
	}
	
	@Override
	public void draw(SpriteBatch batcher) {
        if (val) {
            drawUp(batcher);
        } else {
            drawDown(batcher);
        }
    }

	@Override
	public boolean isTouchDown(int screenX, int screenY) {
		if(super.isTouchDown(screenX, screenY)) {
			val = !val;
			return true;
		}
		return false;
	}

	
	public String getName() {
		return name;
	}
	
	public boolean getVal() {
		return val;
	}
}
