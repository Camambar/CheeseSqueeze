package cheese.squeeze.gameworld;

import cheese.squeeze.gameObjects.Line;
import cheese.squeeze.helpers.AssetLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;

public class GameRenderer {

	private GameWorld world;
	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batcher;
	private int midPointY;
	private int height;
	private int width;
	private Line line;

	public GameRenderer(GameWorld world,int midPointY,int height, int width) {
		this.world = world;
		this.height = height;
		this.width = width;
		cam = new OrthographicCamera();
		cam.setToOrtho(true, 136, 204);

		batcher = new SpriteBatch();
		// Attach batcher to camera
		batcher.setProjectionMatrix(cam.combined);
		line = world.getLine();
		
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);
	}

	public void render() {

        // Fill the entire screen with black, to prevent potential flickering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin ShapeRenderer
        shapeRenderer.begin(ShapeType.Filled);

        // Draw Background color
        //shapeRenderer.setColor(255.0f, 255.0f, 255.0f, (float) 0.6);
        //shapeRenderer.rect(0, 0, width, height);

        // End ShapeRenderer
        shapeRenderer.end();
        
        
        
        // Begin SpriteBatch
        batcher.begin();
        // Disable transparency
        // This is good for performance when drawing images that do not require
        // transparency.
        batcher.disableBlending();
        //batcher.draw(AssetLoader.bg, 0, midPointY + 23, 136, 43);
        batcher.draw(AssetLoader.bg, 0, midPointY, width, height);
        // The bird needs transparency, so we enable that again.
        batcher.enableBlending();

        // Draw bird at its coordinates. Retrieve the Animation object from
        // AssetLoader
        // Pass in the runTime variable to get the current frame.


        // End SpriteBatch
        batcher.end();
        
        shapeRenderer.begin(ShapeType.Line);
        //Draw Static lines.
        shapeRenderer.setColor(126f, 71f, 255.0f, (float) 1);
        float totw = (width-20)/2;
        shapeRenderer.line(10, 10, 10, height-10);
        shapeRenderer.line((totw)+10, 10, (totw)+10, height-10);
        shapeRenderer.line((2*totw)+10, 10, (2*totw)+10, height-10);

        // End ShapeRenderer
        shapeRenderer.end();
        
        if(line.isdrawable()) {
        	shapeRenderer.begin(ShapeType.Line);
        	Vector3 vec1 = new Vector3(line.getX1(),line.getY1(),0);
        	Vector3 vec2 = new Vector3(line.getX2(),line.getY2(),0);
        	cam.unproject(vec1);
        	cam.unproject(vec2);
        	shapeRenderer.line(vec1.x,vec1.y,vec2.x,vec2.y);
        	shapeRenderer.end();
        }
        
		Gdx.app.log("GameRenderer", "rendered");
	}

}
