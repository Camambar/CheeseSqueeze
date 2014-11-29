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
import cheese.squeeze.gameObjects.VerticalLine;
import cheese.squeeze.helpers.AssetLoader;
import cheese.squeeze.helpers.GoalSprites;
import cheese.squeeze.helpers.InputHelper;
import cheese.squeeze.ui.SimpleButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import cheese.squeeze.game.GameState;

public class GameRenderer {

	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batcher;
	private int midPointY;
	private int height;
	private int width;
	private GameBoard board;
	private TextureRegion bg,trap,mouse,trapClosed;
	private GoalSprites goals;
	private Vector2 mouseSize= new Vector2(9,8);
	private float iterator = 0;
	private float move = 0;
	private TextureRegion nextMouse;
	private float lineWidth = (Gdx.graphics.getHeight()/Gdx.graphics.getWidth())*10;
	private Color gestureColor = new Color(255.0f, 255.0f, 255.0f, 0.4f);
	private Color guideColor = new Color(255.0f, 255.0f, 255.0f, 0.07f);
	private Color verticalLineColor = new Color(255.0f, 255.0f, 255.0f, 1f);
	private BitmapFont font;
	private StringBuffer str;
	private int lengthstr;
	private float move2 = 0;
	private Array<PooledEffect> effects;
	private InputHelper input;


	public GameRenderer(GameBoard board,int midPointY,int height, int width, InputHelper input) {
		this.height = height;
		this.width = width;
		this.board = board;
		cam = board.getCamera();
		this.input = input;
		effects = new Array();

		font = AssetLoader.font12;
		batcher = new SpriteBatch();
		// Attach batcher to camera
		batcher.setProjectionMatrix(cam.combined);

		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);

		str = new StringBuffer("YOUR SCORE: ");
		lengthstr = str.length();
		
		
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
			
			drawBackground();

			drawVerticalLines();

			drawTraps();
			
			drawGoals();
			//updateStaticElements = false;

		
		//Dynamic elements
		
		drawGuide();
		
		drawHorizontalLines();

		drawGestureLine();
		
		//drawRedDot();
		
		drawMice();
		
		//draw back button
		batcher.begin();
		AssetLoader.home.draw(batcher);
		//XXX Optimize
		AssetLoader.restart.setPosition(width-14, 1);
		AssetLoader.restart.draw(batcher);
		batcher.end();

		addLineEffect();
		//addToucheffects();
		//Gdx.app.log("GameRenderer", "rendered");
		effects();
	}



	private void addLineEffect() {
		HorizontalLine l= board.getEffectLine();
		if(l!= null) {
			PooledEffect effectp = AssetLoader.smokeEffectPool.obtain();
	    	effectp.setPosition(100,100);
	    	effectp.scaleEffect(1f);
	    	effectp.allowCompletion();
			effects.add(effectp);
		}
		
		
	}


	private void effects() {
		batcher.begin();
		for (int i = effects.size - 1; i >= 0; i--) {
		    
			PooledEffect effect = effects.get(i);
		    //effect.update(delta);

		    effect.draw(batcher,Gdx.graphics.getDeltaTime());

		    if (effect.isComplete()) {
		        effect.free();
		        effects.removeIndex(i);
		    }
		}
		batcher.end();
	}


	private void addToucheffects() {
    	Vector3 v = input.getTouchCoord();
    	if(v != null) {
			PooledEffect effectp = AssetLoader.sparkEffectPool.obtain();
			board.getCamera().unproject(v);
	    	effectp.setPosition(v.x,v.y);
	    	effectp.scaleEffect(0.4f);
	    	effectp.setDuration(1);
			effects.add(effectp);
    	}
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
		if (board.getNextMouseLine().size() >0) {
			float offset2 = MathUtils.sinDeg( (float) (iterator*(0.1f )))*(0.4f);
			batcher.begin();
			for (VerticalLine l : board.getNextMouseLine()) {
				batcher.draw(nextMouse, l.getX1()-3.7f,l.getY1()-2+offset2,5,0,10,10,0.3f,0.25f,90,true);
			}
			//batcher.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
			
	        //next.setSize(5, 5);
	       // next.setOrigin(next.getRegionWidth()/2,0);
			batcher.end();
		}

	}

	private void drawGestureLine() {
		HorizontalLine line = board.getGesturedLine();
		if(line != null && line.isdrawable()) {

			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(gestureColor);
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
			shapeRenderer.setColor(guideColor);
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
				
	 			//TextureRegion kaas = (TextureRegion) goals.getGoal(g.getTickets());
				Sprite kaas = goals.getGoal(g.getTickets());
				batcher.draw(kaas,(g.getPosition().x)-(AssetLoader.goalCenter.x/(2*10)), g.getPosition().y+2,width/10,height/12);
				batcher.end();
			}
		}
	}
	
	private void drawTraps() {
		for(Trap t: board.getTraps()) {
			batcher.begin();
			batcher.enableBlending();
			// 2* 10 rekening houdend met die schaal : width/10
			if(t.isSnapped()) {
				batcher.draw(trapClosed,(t.getPosition().x)-(AssetLoader.goalCenter.x/(2*10))-0.6f, t.getPosition().y-4f,width/11,height/9);
			}else {
				batcher.draw(trap,(t.getPosition().x)-(AssetLoader.goalCenter.x/(2*10))-0.6f, t.getPosition().y-4f,width/11,height/9);
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
		shapeRenderer.setColor(verticalLineColor);
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
    	batcher.end();
	}

	public void renderTutorial(SimpleButton btn) {

    	Sprite dot = AssetLoader.dot;
    	Sprite tutorial = AssetLoader.tutorial;
    	Sprite tutorial2 = AssetLoader.tutorial2;
    	Sprite hand = AssetLoader.hand;
    	tutorial.setSize( width/3+10, (height/8));
    	tutorial.setPosition((width/2)+1, (height/2)-40);
    	tutorial2.setSize( width/3+10, (height/8));
    	tutorial2.setPosition((width/2)-tutorial2.getWidth(), -2);
    	
    	//hand.setOrigin(hand.getHeight()*hand.getScaleX(), hand.getScaleY()*hand.getWidth()/5);
    	hand.setSize(width/4, height/3);
    	hand.setPosition(btn.getBounds().x-((width/4/4))+btn.getBounds().width/2, btn.getBounds().y+btn.getBounds().height/2);
    	//float offset = MathUtils.sinDeg( (float) (iterator*(0.02f )))*(0.09f);
    	float offset = MathUtils.sinDeg( (float) (iterator*(0.02f )))*(0.009f);
    	//float offset = 1;
    	dot.setBounds(btn.getBounds().x, btn.getBounds().y,btn.getBounds().width, btn.getBounds().height);
    	//dot.setPosition(btn.getBounds().x, btn.getBounds().y);
    	//dot.setBounds(btn.getBounds().x, btn.getBounds().y, btn.getBounds().width, btn.getBounds().height);
    	//dot.setCenter(dot.getWidth()/2, dot.getHeight()/2);
    	//dot.setScale(0.4f, 0.4f);
    	dot.setOrigin(5f, 5f);
    	dot.scale(offset);
    	hand.setOrigin(1f,1f);
    	hand.scale(offset*0.5f);
    	//dot.setSize(dot.getWidth()*offset, dot.getHeight()*offset);
    	batcher.begin();
    	dot.draw(batcher);
    	hand.draw(batcher);
		//(gameWidth/2)-((gameWidth/2)/2),midPointY-(gameHeight/8), gameWidth/2,(gameHeight/4)+4
    	

    	
    	tutorial.draw(batcher);
    	tutorial2.draw(batcher);
    	
    	//batcher.draw(failed, (width/2)-((width/2)/2), midPointY+(height/4), width/2, height/2);
    	batcher.end();
	}


	public void renderScore(int score) {
		font.setColor(Color.WHITE);
		batcher.begin();
		str.delete(lengthstr, str.length());
		str.append(score);
		str.append("\n " + CSGame.currentLevel.toString());
		font.setScale(0.5f);
		//font.drawMultiLine(batch, str, x, y, alignmentWidth, alignment)
		font.drawMultiLine(batcher,str,width/4,2, width/2,BitmapFont.HAlignment.CENTER);
		batcher.end();
	}
	
	public void renderScoreFinalWIN(int score) {
		font.setScale(.5f+0.3f*Math.abs(MathUtils.sinDeg(iterator*0.04f)));
		font.setColor(Color.YELLOW);
		batcher.begin();
		str.delete(lengthstr, str.length());
		font.drawMultiLine(batcher, str.append(score),width/4,2+move,width/2,BitmapFont.HAlignment.CENTER);
		batcher.end();
	}

	public void renderScoreFinalLOSE(int score) {
		font.setScale(.5f+0.3f*Math.abs(MathUtils.sinDeg(iterator*0.04f)));
		font.setColor(Color.RED);
		batcher.begin();
		str.delete(lengthstr, str.length());
		font.drawMultiLine(batcher, str.append(score),width/4,2+move,width/2,BitmapFont.HAlignment.CENTER);
		batcher.end();
	}


	public void renderCount(int round) {
		font.setScale(.5f+(0.5f)*Math.abs(MathUtils.sinDeg(iterator)));
		String c = "";
		switch (round) {
		case 0:
			c = "3";
			font.setColor(Color.RED);
			break;
		case 1:
			c = "2";
			font.setColor(Color.RED);
			break;
		case 2:
			c = "1";
			font.setColor(Color.RED);
			break;
		case 3:
			c = "START";
			font.setColor(Color.GREEN);
			break;
		}
		batcher.begin();
		font.drawMultiLine(batcher,c,width/4,2,width/2,BitmapFont.HAlignment.CENTER);

		
		batcher.end();
	}
}
