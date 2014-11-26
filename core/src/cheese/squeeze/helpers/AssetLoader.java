package cheese.squeeze.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class AssetLoader {
	
	public static AssetManager manager = new AssetManager();
    
    public static Sprite bg,menuBg;
    
    public static Vector2 mouseCenter,goalCenter,trapCenter;
    public static Vector2 mouseNose;
    
    public static boolean musicOn = true;
    
    public static GoalSprites goals;

    public static Sprite trap,home,trapClosed;

	public static TextureRegion mouse;
    public static Sprite play,sound_on,sound_off,music_on,music_off,logo_shadow;
    
    private  TextureAtlas atlasMenu;
    
    private  TextureAtlas atlasGame;
    
    private  TextureAtlas atlasFloor;
    
    
    private  TextureAtlas atlasMenuLeeg;

	private TextureAtlas atlasRating;
    
    //private static TextureAtlas atlas = new TextureAtlas("data/onderdelenpack.pack");

	public static TextureRegion logo;
	
	private static Texture starEmpty,starFull,ratingboard;
	
	public static Texture texture;
	
	public static Sound chalk,buttonSound,death,pop;
	
	public static Music menuSound,gameSound;

	public static Texture emptyT,fullT;

	public static NinePatch empty,full;
	
	private static boolean dataLoaded,soundLoaded = false;

	public static Sprite failed;
	
	public static Sprite tutorial,tutorial2,hand;

	public static Sprite dot;

	public static TextureRegion next;

	public static Sprite completed;

	public static Music defeatSound;

	public static Music victorySound;
	
	public static BitmapFont font;

	public static Vector2 trapClosedCenter;

	public static TextureAtlas atlasTutorial;

	public static Sprite version;
	
    private static float width = Gdx.graphics.getWidth();
    private static float height = Gdx.graphics.getHeight();


	private final static int NBCHEESE = 5;

	public AssetLoader() {
		
	}

	public void queueLoading(){
		manager.load("graph/Menu.pack", TextureAtlas.class);
		manager.load("graph/Game.pack", TextureAtlas.class);
		manager.load("graph/Rating.pack", TextureAtlas.class);
		manager.load("graph/floor.pack", TextureAtlas.class);
		manager.load("graph/Tutorial.pack", TextureAtlas.class);
		manager.load("graph/menu_leeg.pack", TextureAtlas.class);
		manager.load("data/Game Music.mp3",Music.class);
		manager.load("data/Menu Music.mp3",Music.class);
		manager.load("data/Defeat.mp3",Music.class);
		manager.load("data/Victory.mp3",Music.class);
		manager.load("data/button.mp3",Sound.class);
		manager.load("data/chalk.mp3",Sound.class);
		manager.load("data/death.mp3",Sound.class);
		manager.load("data/pop.mp3",Sound.class);
	}
	
	public void lastLoadingStep() {
		if(manager.update()) {
			setAtlas();
			this.setSounds();
			this.load();
		}
	}
    
	public void setAtlas() {
		atlasMenu = manager.get("graph/Menu.pack",TextureAtlas.class);
		atlasGame = manager.get("graph/Game.pack",TextureAtlas.class);
		atlasFloor = manager.get("graph/floor.pack",TextureAtlas.class);
		atlasMenuLeeg = manager.get("graph/menu_leeg.pack",TextureAtlas.class);
		atlasTutorial = manager.get("graph/Tutorial.pack",TextureAtlas.class);
		atlasRating = manager.get("graph/Rating.pack",TextureAtlas.class);
	}
	
	public void setSounds() {
		gameSound = manager.get("data/Game Music.mp3");
    	menuSound = manager.get("data/Menu Music.mp3");
    	buttonSound = manager.get("data/button.mp3");
    	defeatSound = manager.get("data/Defeat.mp3");
    	victorySound = manager.get("data/Victory.mp3");
    	chalk = manager.get("data/chalk.mp3");
    	pop = manager.get("data/pop.mp3");
    	death = manager.get("data/death.mp3");
    	AssetLoader.soundLoaded = true;
	}
 
	public void loadSplashScreen() {
		
		texture = new Texture(Gdx.files.internal("data/game.png"));
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        logo = new TextureRegion(texture, 0, 0, 1000, 1000);
        emptyT=new Texture(Gdx.files.internal("data/empty.png"));
        fullT=new Texture(Gdx.files.internal("data/full.png"));
        empty=new NinePatch(new TextureRegion(AssetLoader.emptyT,24,24),8,8,8,8);
        full=new NinePatch(new TextureRegion(AssetLoader.fullT,24,24),8,8,8,8);
	}
	
	public void loadMenu() {
		//TODO replace the menu parts in a diff png.
	}
	
	public void desktopLoad() {

    	//FreeTypeFontGenerator generator = FreeTypeFontGenerator(Gdx.files.internal("fonts/myfont.ttf"));
    	
    	font = new BitmapFont(true);
    	//font = new BitmapFont(Gdx.files.internal("data/font.fnt"),Gdx.files.internal("data/font.png"),true);
    	//font.setScale(0.1f);
    	font.setScale(0.5f);
    	font.setColor(Color.WHITE);
    	goals = new GoalSprites();
    	
    	for(int i = 1; i<=NBCHEESE;i++) {
        	Sprite goal = new Sprite(new TextureRegion(atlasGame.findRegion("cheese_pile"+i)));
            //goal.flip(false,true);
        	goalCenter = new Vector2(goal.getWidth()/(2*goal.getScaleX()),goal.getHeight()/(2*goal.getScaleY()));
        	goals.addSprite(i, goal);
    	}

        trap = new Sprite(new TextureRegion(atlasGame.findRegion("trap_open")));
        trap.flip(false,true);
        trapCenter = new Vector2(trap.getWidth()/(2*trap.getScaleX()),trap.getHeight()/(2*trap.getScaleY()));
        
        trapClosed = new Sprite(new TextureRegion(atlasGame.findRegion("trap_closed")));
        trapClosedCenter = new Vector2(trap.getWidth()/(2*trap.getScaleX()),trap.getHeight()/(2*trap.getScaleY()));
        trapClosed.flip(false, true);
        
        bg = new Sprite(new TextureRegion(atlasFloor.findRegion("floor")));
    	
        
        mouse = new TextureRegion(atlasGame.findRegion("mouse"));
        mouse.flip(false, true);
        

        

        //mouse.setSize(10, 10);
        //mouseCenter = new Vector2(mouse.getWidth()/(2*mouse.getScaleX()),mouse.getHeight()/(2*mouse.getScaleY()));
        //mouseNose = new Vector2((mouse.getWidth()/2)*mouse.getScaleX(),mouse.getHeight()/(mouse.getScaleY()));
        
        
        menuBg = new Sprite(new TextureRegion(atlasMenuLeeg.findRegion("menu_leeg")));
        

        float desiredWidth = width;
        float scale = desiredWidth / menuBg.getWidth();
        
        menuBg.setSize(menuBg.getWidth() * scale, menuBg.getHeight() * scale);
        //menuBg.setPosition((width / 2) - (menuBg.getWidth() / 2), (height / 2)- (menuBg.getHeight() / 2));
        
        next = new TextureRegion(atlasGame.findRegion("next"));
        next.flip(false, true);

        
        //menuBg.setSize(675,1500);
        //menuBg.setScale(1f);
        
        failed = new Sprite(new TextureRegion(atlasGame.findRegion("failed")));
        failed.flip(false, true);

        tutorial = new Sprite(new TextureRegion(atlasTutorial.findRegion("tutorial")));
        tutorial.flip(false, true);
        
        tutorial2 = new Sprite(new TextureRegion(atlasTutorial.findRegion("tutorial2")));
        tutorial2.flip(false, true);
        
        hand = new Sprite(new TextureRegion(atlasTutorial.findRegion("hand")));
        hand.flip(false, true);
        
        dot = new Sprite(new TextureRegion(atlasTutorial.findRegion("dot")));
        dot.flip(false, true);
        
        
        completed = new Sprite(new TextureRegion(atlasGame.findRegion("completed")));
        completed.flip(false, true);
        
        logo_shadow = new Sprite(new TextureRegion(atlasMenu.findRegion("logo")));
        
        play =new Sprite(new TextureRegion(atlasMenu.findRegion("play")));
        
        desiredWidth = width*0.5f;
        scale = desiredWidth / menuBg.getWidth();
        play.setSize(play.getWidth() * scale, play.getHeight() * scale);
        // (height/2)+10 +play.getWidth()
        //width-(1.5f*logo_shadow.getWidth())
        play.setPosition((width/2)-(.5f*play.getWidth()),(height/2)+20);
        
        version = new Sprite(new TextureRegion(atlasMenu.findRegion("download")));
        version.setSize(version.getWidth()*scale, version.getHeight()*scale );
        // (height/2)+10 +play.getWidth()
        //width-(1.5f*logo_shadow.getWidth())
        version.setPosition((width/2)-(.25f*play.getWidth()),(height/5)+20);
        
        logo_shadow.setSize(logo_shadow.getWidth() * scale, logo_shadow.getHeight() * scale);
        //set position to the top middle 10 pixle from top
        logo_shadow.setPosition((width/2)-(.5f*logo_shadow.getWidth()), height-50f-logo_shadow.getHeight());
        
        
        scale = scale * 1f;
        music_on = new Sprite(new TextureRegion(atlasMenu.findRegion("music_on")));
        music_on.setSize(music_on.getWidth() * scale, music_on.getHeight() * scale); 
        music_on.setPosition(play.getX(),(height/2)-20-music_on.getHeight());
        
        music_off = new Sprite(new TextureRegion(atlasMenu.findRegion("music_off")));
        music_off.setSize(music_off.getWidth() * scale, music_off.getHeight() * scale);
        music_off.setPosition(play.getX(),(height/2)-20-music_off.getHeight());
        
        
        sound_on = new Sprite(new TextureRegion(atlasMenu.findRegion("sound_on")));
        sound_on.setSize(sound_on.getWidth() * scale, sound_on.getHeight() * scale);
        sound_on.setPosition((width/2)+(.5f*play.getWidth())-sound_on.getWidth(),(height/2)-20-sound_on.getHeight());
        sound_off = new Sprite(new TextureRegion(atlasMenu.findRegion("sound_off")));
        sound_off.setSize(sound_off.getWidth() * scale, sound_off.getHeight() * scale);
        sound_off.setPosition((width/2)+(.5f*play.getWidth())-sound_off.getWidth(),(height/2)-20-sound_off.getHeight());
    	
       
        
        home = new Sprite(new TextureRegion(atlasGame.findRegion("home")));
        home.setSize(13,10);
        home.flip(false, true);
        home.setPosition(1, 1);
        
        dataLoaded = true;
	}
	
	public void androidLoad() {

    	//FreeTypeFontGenerator generator = FreeTypeFontGenerator(Gdx.files.internal("fonts/myfont.ttf"));
    	
    	font = new BitmapFont(true);
    	//font = new BitmapFont(Gdx.files.internal("data/font.fnt"),Gdx.files.internal("data/font.png"),true);
    	//font.setScale(0.1f);
    	font.setScale(0.5f);
    	font.setColor(Color.WHITE);
    	goals = new GoalSprites();
    	
    	for(int i = 1; i<=NBCHEESE;i++) {
        	Sprite goal = new Sprite(new TextureRegion(atlasGame.findRegion("cheese_pile"+i)));
            //goal.flip(false,true);
        	goalCenter = new Vector2(goal.getWidth()/(2*goal.getScaleX()),goal.getHeight()/(2*goal.getScaleY()));
        	goals.addSprite(i, goal);
    	}

        trap = new Sprite(new TextureRegion(atlasGame.findRegion("trap_open")));
        trap.flip(false,true);
        trapCenter = new Vector2(trap.getWidth()/(2*trap.getScaleX()),trap.getHeight()/(2*trap.getScaleY()));
        
        trapClosed = new Sprite(new TextureRegion(atlasGame.findRegion("trap_closed")));
        trapClosedCenter = new Vector2(trap.getWidth()/(2*trap.getScaleX()),trap.getHeight()/(2*trap.getScaleY()));
        trapClosed.flip(false, true);
        
        bg = new Sprite(new TextureRegion(atlasFloor.findRegion("floor")));
    	
        
        mouse = new TextureRegion(atlasGame.findRegion("mouse"));
        mouse.flip(false, true);
        

        

        //mouse.setSize(10, 10);
        //mouseCenter = new Vector2(mouse.getWidth()/(2*mouse.getScaleX()),mouse.getHeight()/(2*mouse.getScaleY()));
        //mouseNose = new Vector2((mouse.getWidth()/2)*mouse.getScaleX(),mouse.getHeight()/(mouse.getScaleY()));
        
        
        menuBg = new Sprite(new TextureRegion(atlasMenuLeeg.findRegion("menu_leeg")));
        

        float desiredWidth = width;
        float scale = desiredWidth / menuBg.getWidth();
        
        menuBg.setSize(menuBg.getWidth() * scale, menuBg.getHeight() * scale);
        //menuBg.setPosition((width / 2) - (menuBg.getWidth() / 2), (height / 2)- (menuBg.getHeight() / 2));
        
        next = new TextureRegion(atlasGame.findRegion("next"));
        next.flip(false, true);

        
        //menuBg.setSize(675,1500);
        //menuBg.setScale(1f);
        
        failed = new Sprite(new TextureRegion(atlasGame.findRegion("failed")));
        failed.flip(false, true);

        tutorial = new Sprite(new TextureRegion(atlasTutorial.findRegion("tutorial")));
        tutorial.flip(false, true);
        
        tutorial2 = new Sprite(new TextureRegion(atlasTutorial.findRegion("tutorial2")));
        tutorial2.flip(false, true);
        
        hand = new Sprite(new TextureRegion(atlasTutorial.findRegion("hand")));
        hand.flip(false, true);
        
        dot = new Sprite(new TextureRegion(atlasTutorial.findRegion("dot")));
        dot.flip(false, true);
        
        
        completed = new Sprite(new TextureRegion(atlasGame.findRegion("completed")));
        completed.flip(false, true);
        
        logo_shadow = new Sprite(new TextureRegion(atlasMenu.findRegion("logo")));
        
        play =new Sprite(new TextureRegion(atlasMenu.findRegion("play")));
        
        desiredWidth = width*0.9f;
        scale = desiredWidth / menuBg.getWidth();
        play.setSize(play.getWidth() * scale, play.getHeight() * scale);
        // (height/2)+10 +play.getWidth()
        //width-(1.5f*logo_shadow.getWidth())
        play.setPosition((width/2)-(.5f*play.getWidth()),(height/1.75f)+20);
        
        version = new Sprite(new TextureRegion(atlasMenu.findRegion("download")));
        version.setSize(version.getWidth()*scale, version.getHeight()*scale );
        // (height/2)+10 +play.getWidth()
        //width-(1.5f*logo_shadow.getWidth())
        version.setPosition((width/2)-(.25f*play.getWidth()),(height/5)+20);
        
        logo_shadow.setSize(logo_shadow.getWidth() * scale, logo_shadow.getHeight() * scale);
        //set position to the top middle 10 pixle from top
        logo_shadow.setPosition((width/2)-(.5f*logo_shadow.getWidth()), height-50f-logo_shadow.getHeight());
        
        
        scale = scale * 1f;
        music_on = new Sprite(new TextureRegion(atlasMenu.findRegion("music_on")));
        music_on.setSize(music_on.getWidth() * scale, music_on.getHeight() * scale); 
        music_on.setPosition(play.getX(),(height/1.75f)-20-music_on.getHeight());
        
        music_off = new Sprite(new TextureRegion(atlasMenu.findRegion("music_off")));
        music_off.setSize(music_off.getWidth() * scale, music_off.getHeight() * scale);
        music_off.setPosition(play.getX(),(height/1.75f)-20-music_off.getHeight());
        
        
        sound_on = new Sprite(new TextureRegion(atlasMenu.findRegion("sound_on")));
        sound_on.setSize(sound_on.getWidth() * scale, sound_on.getHeight() * scale);
        sound_on.setPosition((width/2)+(.5f*play.getWidth())-sound_on.getWidth(),(height/1.75f)-20-sound_on.getHeight());
        sound_off = new Sprite(new TextureRegion(atlasMenu.findRegion("sound_off")));
        sound_off.setSize(sound_off.getWidth() * scale, sound_off.getHeight() * scale);
        sound_off.setPosition((width/2)+(.5f*play.getWidth())-sound_off.getWidth(),(height/1.75f)-20-sound_off.getHeight());
    	
       
        
        home = new Sprite(new TextureRegion(atlasGame.findRegion("home")));
        home.setSize(13,10);
        home.flip(false, true);
        home.setPosition(1, 1);
        
        dataLoaded =true;
	}
	
    public void load() {
    	switch(Gdx.app.getType()) {
    	   case Android:
    	       androidLoad();
    		   break;
    	   case Desktop:
    	       desktopLoad();
    		   break;
    	   case WebGL:
    		   desktopLoad();
    		   break;
    	   case iOS:
    		   androidLoad();
    		   break;
    	}
    }
    
    public static void soundSwitch() {
    	
    }
    
    
    
    public static Sprite musicSwitch() {
    	if(musicOn == true) {
    		musicOn = false;
    		gameSound.dispose();
    		menuSound.dispose();
    		return music_off;
    	}
    	return music_on;
    }
    
    public static boolean update() {
        return (manager.update() && AssetLoader.dataLoaded);
    }

    public void dispose() {
        // We must dispose of the texture when we are finished.
        atlasGame.dispose();
        atlasMenu.dispose();
        atlasFloor.dispose();
        atlasMenuLeeg.dispose();
        manager.clear();
    }
    
    public static float getProcess() {
    	return manager.getProgress();
    	
    }


	public static void disposeSplash() {
		texture.dispose();
	}

}
