package cheese.squeeze.game;

import java.util.logging.Level;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.foundation.NSNumber;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.bindings.googleanalytics.GAI;
import org.robovm.bindings.googleanalytics.GAIDefaultLogger;
import org.robovm.bindings.googleanalytics.GAIDictionaryBuilder;
import org.robovm.bindings.googleanalytics.GAITrackedViewController;
import org.robovm.bindings.googleanalytics.GAITracker;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;

import cheese.squeeze.game.CSGame;
import cheese.squeeze.tweenAccessors.ActionResolver;


public class IOSLauncher extends IOSApplication.Delegate implements ActionResolver{
	
	private GAITracker tracker;
	
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        GAI.getSharedInstance().setDryRun(false);
        GAI.getSharedInstance().setDispatchInterval(20.0);
        GAI.getSharedInstance().setDefaultTracker(GAI.getSharedInstance().getTracker("UA-56519107-1"));
        GAI.getSharedInstance().setLogger(new GAIDefaultLogger());
        tracker = GAI.getSharedInstance().getTracker("UA-56519107-1");
        
        return new IOSApplication(new CSGame(), config);
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }

	public void reportAnalytics(String action, String cat, long value) {
		
		tracker.send(GAIDictionaryBuilder.createEvent(cat, action,"apple" ,NSNumber.valueOf(value)).build());
		
		GAI.getSharedInstance().dispatch();
	}
}