package cheese.squeeze.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
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
    
    private static TextureAtlas atlas;
    
    //private static TextureAtlas atlas = new TextureAtlas("data/onderdelenpack.pack");

	public static TextureRegion logo;
	
	public static Texture texture;
	
	public static Sound chalk,buttonSound,death,pop;
	
	public static Music menuSound,gameSound;

	public static Texture emptyT,fullT;

	public static NinePatch empty,full;
	
	private static boolean dataLoaded,soundLoaded = false;

	public static Sprite failed;

	public static Sprite completed;

	public static Music defeatSound;

	public static Music victorySound;

	public static Vector2 trapClosedCenter;

	private final static int NBCHEESE = 5;


	public static void queueLoading(){
		manager.load("graph/newparts.pack", TextureAtlas.class);
		manager.load("data/Game Music.mp3",Music.class);
		manager.load("data/Menu Music.mp3",Music.class);
		manager.load("data/Defeat.mp3",Music.class);
		manager.load("data/Victory.mp3",Music.class);
		manager.load("data/button.mp3",Sound.class);
		manager.load("data/chalk.mp3",Sound.class);
		manager.load("data/death.mp3",Sound.class);
		manager.load("data/pop.mp3",Sound.class);
	}
	
	public static void lastLoadingStep() {
		if(manager.update()) {
			setAtlas();
			AssetLoader.setSounds();
			AssetLoader.load();
		}
	}
    
	public static void setAtlas() {
		atlas = manager.get("graph/newparts.pack",TextureAtlas.class);
	}
	
	public static void setSounds() {
		gameSound = manager.get("data/Game Music.mp3");
    	menuSound = manager.get("data/Menu Music.mp3");
    	buttonSound = manager.get("data/button.mp3");
    	defeatSound = manager.get("data/Defeat.wav");
    	victorySound = manager.get("data/Victory.wav");
    	chalk = manager.get("data/chalk.mp3");
    	pop = manager.get("data/pop.mp3");
    	death = manager.get("data/death.mp3");
    	AssetLoader.soundLoaded = true;
	}
 
	public static void loadSplashScreen() {
		
		texture = new Texture(Gdx.files.internal("data/game.png"));
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        logo = new TextureRegion(texture, 0, 0, 1000, 1000);
        emptyT=new Texture(Gdx.files.internal("data/empty.png"));
        fullT=new Texture(Gdx.files.internal("data/full.png"));
        empty=new NinePatch(new TextureRegion(AssetLoader.emptyT,24,24),8,8,8,8);
        full=new NinePatch(new TextureRegion(AssetLoader.fullT,24,24),8,8,8,8);
	}
	
	public static void loadMenu() {
		//TODO replace the menu parts in a diff png.
	}
	
    public static void load() {
    	
    	goals = new GoalSprites();
    	
    	for(int i = 1; i<=NBCHEESE;i++) {
        	Sprite goal = new Sprite(new TextureRegion(atlas.findRegion("cheese_pile"+i)));
            //goal.flip(false,true);
        	goalCenter = new Vector2(goal.getWidth()/(2*goal.getScaleX()),goal.getHeight()/(2*goal.getScaleY()));
        	goals.addSprite(i, goal);
    	}

        trap = new Sprite(new TextureRegion(atlas.findRegion("trap_open")));
        //trap.flip(false,true);
        trapCenter = new Vector2(trap.getWidth()/(2*trap.getScaleX()),trap.getHeight()/(2*trap.getScaleY()));
        
        trapClosed = new Sprite(new TextureRegion(atlas.findRegion("trap_closed")));
        trapClosedCenter = new Vector2(trap.getWidth()/(2*trap.getScaleX()),trap.getHeight()/(2*trap.getScaleY()));
        trapClosed.flip(false, true);
        
        bg = new Sprite(new TextureRegion(atlas.findRegion("floor")));
    	
        
        mouse = new TextureRegion(atlas.findRegion("mouse"));
        mouse.flip(false, true);
        //mouse.setSize(10, 10);
        //mouseCenter = new Vector2(mouse.getWidth()/(2*mouse.getScaleX()),mouse.getHeight()/(2*mouse.getScaleY()));
        //mouseNose = new Vector2((mouse.getWidth()/2)*mouse.getScaleX(),mouse.getHeight()/(mouse.getScaleY()));
        
        
        menuBg = new Sprite(new TextureRegion(atlas.findRegion("menu_leeg")));
        
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        float desiredWidth = width;
        float scale = desiredWidth / menuBg.getWidth();
        
        menuBg.setSize(menuBg.getWidth() * scale, menuBg.getHeight() * scale);
        //menuBg.setPosition((width / 2) - (menuBg.getWidth() / 2), (height / 2)- (menuBg.getHeight() / 2));
        
        
        //menuBg.setSize(675,1500);
        //menuBg.setScale(1f);
        
        failed = new Sprite(new TextureRegion(atlas.findRegion("failed")));
        failed.flip(false, true);

        
        completed = new Sprite(new TextureRegion(atlas.findRegion("completed")));
        completed.flip(false, true);
        
        logo_shadow = new Sprite(new TextureRegion(atlas.findRegion("logo")));
        
        play =new Sprite(new TextureRegion(atlas.findRegion("play")));
        
        desiredWidth = width*0.5f;
        scale = desiredWidth / menuBg.getWidth();
        play.setSize(play.getWidth() * scale, play.getHeight() * scale);
        // (height/2)+10 +play.getWidth()
        //width-(1.5f*logo_shadow.getWidth())
        play.setPosition((width/2)-(.5f*play.getWidth()),(height/2)+20);
        
        
        logo_shadow.setSize(logo_shadow.getWidth() * scale, logo_shadow.getHeight() * scale);
        //set position to the top middle 10 pixle from top
        logo_shadow.setPosition((width/2)-(.5f*logo_shadow.getWidth()), height-20f-logo_shadow.getHeight());
        
        
        scale = scale * 1f;
        music_on = new Sprite(new TextureRegion(atlas.findRegion("music_on")));
        music_on.setSize(music_on.getWidth() * scale, music_on.getHeight() * scale); 
        music_on.setPosition(play.getX(),(height/2)-20-music_on.getHeight());
        
        music_off = new Sprite(new TextureRegion(atlas.findRegion("music_off")));
        music_off.setSize(music_off.getWidth() * scale, music_off.getHeight() * scale);
        music_off.setPosition(play.getX(),(height/2)-20-music_off.getHeight());
        
        
        sound_on = new Sprite(new TextureRegion(atlas.findRegion("sound_on")));
        sound_on.setSize(sound_on.getWidth() * scale, sound_on.getHeight() * scale);
        sound_on.setPosition((width/2)+(.5f*play.getWidth())-sound_on.getWidth(),(height/2)-20-sound_on.getHeight());
        sound_off = new Sprite(new TextureRegion(atlas.findRegion("sound_off")));
        sound_off.setSize(sound_off.getWidth() * scale, sound_off.getHeight() * scale);
        sound_off.setPosition((width/2)+(.5f*play.getWidth())-sound_off.getWidth(),(height/2)-20-sound_off.getHeight());
    	
       
        
        home = new Sprite(new TextureRegion(atlas.findRegion("home")));
        home.setSize(13,10);
        home.flip(false, true);
        home.setPosition(1, 1);
        
        /*
    	 * OLD STUFF
    	 * 
        texture = new Texture(Gdx.files.internal("data/bg.png"));
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        
        bg = new TextureRegion(texture, 0, 0, 1092, 1915);
        bg.flip(false, true);
        
        texture = new Texture(Gdx.files.internal("data/mouse.png"));
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        //TODO Right pictures with right dim!!
        mouse = new TextureRegion(texture, 0, 0, 513, 545);
        mouse.flip(false, true);
        mouseS = new Sprite(mouse);
        mouseS.setScale(30);
        mouseCenter = new Vector2(mouseS.getWidth()/(2*mouseS.getScaleX()),mouseS.getHeight()/(2*mouseS.getScaleY()));
        mouseNose = new Vector2(mouseS.getWidth()/(2*mouseS.getScaleX()),mouseS.getHeight()/(2*mouseS.getScaleY()));
        

        goal = new Sprite(new TextureRegion(atlas.findRegion("goal")));
        goal.flip(false,true);
        goalCenter = new Vector2(goal.getWidth()/(2*goal.getScaleX()),goal.getHeight()/(2*goal.getScaleY()));
        
        
        trap = new Sprite(new TextureRegion(atlas.findRegion("trap")));
        trap.flip(false,true);
        trapCenter = new Vector2(trap.getWidth()/(2*trap.getScaleX()),trap.getHeight()/(2*trap.getScaleY()));
        
        
        
        texture = new Texture(Gdx.files.internal("data/play.png"));
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
       
        playButtonUp = new TextureRegion(texture, 0, 0, 803, 264);
        playButtonDown = new TextureRegion(texture, 0, 0, 803, 264);
        */
        
        dataLoaded = true;
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

    public static void dispose() {
        // We must dispose of the texture when we are finished.
        atlas.dispose();
    }
    
    public static float getProcess() {
    	return manager.getProgress();
    	
    }


	public static void disposeSplash() {
		texture.dispose();
	}

}
