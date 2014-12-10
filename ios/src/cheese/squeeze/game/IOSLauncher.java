package cheese.squeeze.game;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.foundation.NSDictionary;
import org.robovm.apple.foundation.NSError;
import org.robovm.apple.foundation.NSNumber;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIApplicationLaunchOptions;
import org.robovm.bindings.googleanalytics.GAIDictionaryBuilder;
import org.robovm.bindings.googleanalytics.GAITracker;


import cheese.squeeze.tweenAccessors.ActionResolver;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;


public class IOSLauncher extends IOSApplication.Delegate implements ActionResolver{
	
	private GAITracker tracker;
	private IOSApplication gdxApp;
	//private PlayServicesManager manager;
	private final String LEADERBOARD_ID = "CgkItIOKwawYEAIQBg";
	private final String CLIEND_ID = "349069207524-m9oi4dh8okmdfqppfk975u6ub56l3a3m.apps.googleusercontent.com";
	/*
	private LoginCallback loginCallback = new LoginCallback() {

		@Override
		public void error(NSError arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void success() {
			// TODO Auto-generated method stub
			
		}
	};	
	
	*/
	
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        tracker = GAI.getSharedInstance().getTracker("UA-56519107-1");
       /*
        if (manager == null) {
			manager = new PlayServicesManager();
			manager.setClientId(CLIEND_ID);
			manager.setUserDataToRetrieve(true, false);
			manager.setToastLocation(PlayServicesManager.TOAST_BOTH, GPGToastPlacement.GPGToastPlacementTop);
		}
		*/
        CSGame game = new CSGame(this);
        gdxApp = new IOSApplication(game, config);
        return gdxApp;
        
    }

    public static void main(String[] argv) {
        GAI.getSharedInstance().setDryRun(false);
        GAI.getSharedInstance().setDispatchInterval(20.0);
        GAI.getSharedInstance().setDefaultTracker(GAI.getSharedInstance().getTracker("UA-56519107-1"));
       
        
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }
    
	public void reportAnalytics(String action, String cat, long value) {
		
		tracker.send(GAIDictionaryBuilder.createEvent(cat, action,"apple" ,NSNumber.valueOf(value)).build());
		GAI.getSharedInstance().dispatch();
	}

	@Override
	public boolean getSignedInGPGS() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void loginGPGS() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void submitScoreGPGS(int score) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getLeaderboardGPGS() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getAchievementsGPGS() {
		// TODO Auto-generated method stub
		
	}
    
	/*
	@Override
	public boolean didFinishLaunching (UIApplication application, UIApplicationLaunchOptions launchOptions) {
		final boolean result = super.didFinishLaunching(application, launchOptions);

       manager = new PlayServicesManager();
       manager.setClientId("349069207524-m9oi4dh8okmdfqppfk975u6ub56l3a3m.apps.googleusercontent.com");
       manager.setViewController(gdxApp.getUIViewController());
       manager.setToastLocation(PlayServicesManager.TOAST_BOTH, GPGToastPlacement.GPGToastPlacementTop);
       manager.setUserDataToRetrieve(true, false);
       manager.setLoginCallback(loginCallback);
       manager.didFinishLaunching();

        return result;
	}
	


    



	
	@Override
	public boolean getSignedInGPGS() {
		return manager.isLoggedIn();
	}

	@Override
	public void loginGPGS() {
		if(manager != null) {
			manager.login();
		}
		else {
			manager = new PlayServicesManager();
			manager.setClientId(CLIEND_ID);
			manager.setUserDataToRetrieve(true, false);
			manager.setToastLocation(PlayServicesManager.TOAST_BOTH, GPGToastPlacement.GPGToastPlacementTop);
		}
		
	}

	@Override
	public void submitScoreGPGS(int score) {
		manager.postScore(LEADERBOARD_ID, score);
		
	}

	@Override
	public void getLeaderboardGPGS() {
		manager.showLeaderboard(LEADERBOARD_ID);
		
		
	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {
		manager.unlockAchievement(achievementId);
		
	}

	@Override
	public void getAchievementsGPGS() {
		manager.showAchievements();
		
	}
	*/
}