package cheese.squeeze.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SwitchButton extends SimpleButton{

    
    private boolean val = true; // true is up false is down

	public SwitchButton(float x, float y, float width, float height,
			TextureRegion buttonUp, TextureRegion buttonDown) {
		super(x, y, width, height, buttonUp, buttonDown);

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
    public boolean isTouchUp(int screenX, int screenY) {
        
        // It only counts as a touchUp if the button is in a pressed state.
        if (contains(screenX, screenY) && isPressed()) {
        	val = !val;
        	setPressed(false);
            return true;
        }
        
        // Whenever a finger is released, we will cancel any presses.
        setPressed(false);
        return false;
    }

}
