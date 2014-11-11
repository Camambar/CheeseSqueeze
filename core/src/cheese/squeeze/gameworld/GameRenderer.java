package cheese.squeeze.gameworld;

import java.util.ArrayList;

import cheese.squeeze.game.CSGame;
import cheese.squeeze.game.GameState;
import cheese.squeeze.gameLogic.GameBoard;
import cheese.squeeze.gameObjects.Cheese;
import cheese.squeeze.gameObjects.HorizontalLine;
import cheese.squeeze.gameObjects.Line;
import cheese.squeeze.gameObjects.Mouse;
import cheese.squeeze.gameObjects.Trap;
import cheese.squeeze.helpers.AssetLoader;
import cheese.squeeze.helpers.GoalSprites;
import cheese.squeeze.ui.SimpleButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import cheese.squeeze.game.GameState;

public class GameRenderer {

	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batcher;
	private int midPointY;
	private int height;
	private int width;
	private GameBoard board;
	private boolean updateStaticElements;
	private TextureRegion bg,trap,mouse,failed,trapClosed;
	private GoalSprites goals;
	private Vector2 mouseSize= new Vector2(9,8);
	private Sprite completed;
	private float iterator = 0;
	private TextureRegion nextMouse;
	private float lineWidth = (Gdx.graphics.getHeight()/Gdx.graphics.getWidth())*10;


	public GameRenderer(GameBoard board,int midPointY,int height, int width) {
		this.height = height;
		this.width = width;
		this.board = board;
		cam = board.getCamera();
		updateStaticElements = true;

		batcher = new SpriteBatch();
		// Attach batcher to camera
		batcher.setProjectionMatrix(cam.combined);

		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);



		// Call helper methods to initialize instance variables
		initGameObjects();
		initAssets();
	}

	public void render() {
		iterator += 200;

		Gdx.gl.glLineWidth(lineWidth);
		// Fill the entire screen with black, to prevent potential flickering.
		//Gdx.gl.glClearColor(0, 0, 0, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//shapeRenderer.begin(ShapeType.Filled);
		//shapeRenderer.end();

		//TODO gives strange things if it is updated once
		//Fixed Elements
		if(updateStaticElements){
			drawBackground();

			drawVerticalLines();

			drawTraps();
			
			drawGoals();
			//updateStaticElements = false;
		}

		
		//Dynamic elements
		
		drawGuide();
		
		drawHorizontalLines();

		drawGestureLine();
		
		drawRedDot();
		
		drawMice();
		
		//draw back button
		batcher.begin();
		AssetLoader.home.draw(batcher);
		batcher.end();


		Gdx.app.log("GameRenderer", "rendered");
	}



	private void drawRedDot() {
		if(board.getBeginPosition() != null) {

			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(new Color(255.0f, 0f, 0f, 0.5f));
			shapeRenderer.circle(board.getBeginPosition().x, board.getBeginPosition().y, board.beginRadius);
			shapeRenderer.end();

			Gdx.gl.glDisable(GL20.GL_BLEND);
		}
		
	}

	private void drawMice() {
		for(Mouse m : board.getMice()) {
			batcher.begin();
			//TODO nog wa prutsen me de wiggle :P
			
			// enable transparency
			batcher.enableBlending();
			
	        float offset = MathUtils.sinDeg( (float) (iterator*(0.1f*m.getSpeed() )))*(0.3f*m.getSpeed());
	        if(m.getRotation() == 90) {
	        	batcher.draw(mouse, m.getX()-(mouseSize.x/2)+offset,m.getY()-(mouseSize.y/2),mouseSize.x/2,mouseSize.y/2 , mouseSize.x, mouseSize.y, 1, 1, m.getRotation()+MathUtils.radiansToDegrees*offset*0.5f, true);
	        }
	        else {
	        	batcher.draw(mouse, m.getX()-(mouseSize.x/2),m.getY()-(mouseSize.y/2)+offset,mouseSize.x/2,mouseSize.y/2 , mouseSize.x, mouseSize.y, 1, 1, m.getRotation()+ MathUtils.radiansToDegrees*offset*0.5f, true);
	        }
			
			//batcher.draw(mouse,m.getX(),m.getY(),width/7,height/10);
			//AssetLoader.mouseS.draw(batcher);
			// End SpriteBatch
			batcher.end();
		}
		float offset2 = MathUtils.sinDeg( (float) (iterator*(0.1f )))*(0.4f);
		batcher.begin();
		//batcher.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
		batcher.draw(nextMouse, board.getNextMouseLine().getX1(),board.getNextMouseLine().getY1()-7+offset2,5,0,10,10,1,1,90,true);
        //next.setSize(5, 5);
       // next.setOrigin(next.getRegionWidth()/2,0);
		batcher.end();
	}

	private void drawGestureLine() {
		HorizontalLine line = board.getGesturedLine();
		if(line != null && line.isdrawable()) {

			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(new Color(255.0f, 255.0f, 255.0f, 0.4f));
			shapeRenderer.line(line.getPoint1(),line.getPoint2());
			shapeRenderer.end();

			Gdx.gl.glDisable(GL20.GL_BLEND);
		}
	}
	
	private void drawGuide() {

		for(HorizontalLine l: board.getHGuideLines()) {
			
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(new Color(255.0f, 255.0f, 255.0f, 0.07f));
		shapeRenderer.line(l.getX1(),l.getY1(),l.getX2(),l.getY2());
		shapeRenderer.end();

		Gdx.gl.glDisable(GL20.GL_BLEND);
		}
	}

	private void drawHorizontalLines() {
		for(Line l : board.getHLines()) {
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.line(l.getPoint1(),l.getPoint2());
			shapeRenderer.end();
		}
	}

	private void drawGoals() {
		// TODO Auto-generated method stub
		for(Cheese g: board.getGoals()) {
			if(g.getTickets() > 0) {
				batcher.begin();
				batcher.enableBlending();
				// 2* 10 rekening houdend met die schaal : width/10
				
	 			TextureRegion kak = (TextureRegion) goals.getGoal(g.getTickets());
				batcher.draw(kak,(g.getPosition().x)-(AssetLoader.goalCenter.x/(2*10)), g.getPosition().y+2,width/10,height/10);
				batcher.end();
			}
		}
	}
	
	private void drawTraps() {
		ArrayList<Trap> ta = (ArrayList<Trap>) board.getTraps();
		for(Trap t: board.getTraps()) {
			batcher.begin();
			batcher.enableBlending();
			// 2* 10 rekening houdend met die schaal : width/10
			if(t.isSnapped()) {
				batcher.draw(trapClosed,(t.getPosition().x)-(AssetLoader.goalCenter.x/(2*10))-0.6f, t.getPosition().y-4f,width/11,height/8);
			}else {
				batcher.draw(trap,(t.getPosition().x)-(AssetLoader.goalCenter.x/(2*10))-0.6f, t.getPosition().y-4f,width/11,height/8);
			}
			
			batcher.end();
		}
		
	}

	private void drawVerticalLines() {
		//set line w
		//TODO scale with zoom
		

		// begin line draw
		shapeRenderer.begin(ShapeType.Line);
		//Draw Static lines.
		shapeRenderer.setColor(new Color(255.0f, 255.0f, 255.0f, 1f));
		//shapeRenderer.setColor(126f, 71f, 255.0f, (float) 1);

		for(Line l : board.getVLines()) {
			
			shapeRenderer.line(l.getPoint1(),l.getPoint2());
		}

		// End ShapeRenderer
		shapeRenderer.end();

	}

	private void drawBackground() {
		// Begin SpriteBatch draw bg
		batcher.begin();
		// Disable transparency
		batcher.disableBlending();     
		batcher.draw(bg, 0, midPointY, width, height);
		batcher.end();
	}

	private void initGameObjects() {

	}

	private void initAssets() {
		bg = AssetLoader.bg;
		goals = AssetLoader.goals;
		trap = AssetLoader.trap;
		mouse = AssetLoader.mouse;
		nextMouse = AssetLoader.next;
		trapClosed = AssetLoader.trapClosed;
	}
	

	public void renderPopUp(SimpleButton btn) {
    	batcher.begin();
    	btn.draw(batcher);
    	//batcher.draw(failed, (width/2)-((width/2)/2), midPointY+(height/4), width/2, height/2);
    	batcher.end();
	}

	public void renderTutorial(SimpleButton btn, int i) {
		float betweenX1 = board.getVLines().get(i-1).getX1();
		float betweenX2 = board.getVLines().get(i).getX1();
		shapeRenderer.begin(ShapeType.Filled);
		//shapeRenderer.rect(btn.getBounds().x, btn.getBounds().y, btn.getBounds().width, btn.getBounds().height);
		shapeRenderer.end();
		//(gameWidth/2)-((gameWidth/2)/2),midPointY-(gameHeight/8), gameWidth/2,(gameHeight/4)+4
    	batcher.begin();
    	Sprite dot = AssetLoader.dot;
    	Sprite tutorial = AssetLoader.tutorial;
    	Sprite hand = AssetLoader.hand;
    	tutorial.setSize( width-20, (height/4));
    	tutorial.setPosition((width/2)-((width-20)/2), height/2);
    	
    	hand.setPosition(btn.getBounds().x, btn.getBounds().y);
    	hand.setSize(width/4, height/3);
    	//float offset = MathUtils.sinDeg( (float) (iterator*(0.02f )))*(0.09f);
    	float offset = MathUtils.sinDeg( (float) (iterator*(0.02f )))*(0.009f);
    	dot.setBounds(btn.getBounds().x, btn.getBounds().y,btn.getBounds().width, btn.getBounds().height);
    	//dot.setPosition(btn.getBounds().x, btn.getBounds().y);
    	//dot.setBounds(btn.getBounds().x, btn.getBounds().y, btn.getBounds().width, btn.getBounds().height);
    	//dot.setCenter(dot.getWidth()/2, dot.getHeight()/2);
    	//dot.setScale(0.4f, 0.4f);
    	dot.scale(offset);
    	//dot.setSize(dot.getWidth()*offset, dot.getHeight()*offset);
    	
    	hand.draw(batcher);
    	dot.draw(batcher);
    	tutorial.draw(batcher);
    	System.out.println(offset);

    	//batcher.draw(failed, (width/2)-((width/2)/2), midPointY+(height/4), width/2, height/2);
    	batcher.end();
	}
}
