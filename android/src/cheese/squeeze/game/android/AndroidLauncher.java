package cheese.squeeze.game.android;

import java.util.HashMap;

import android.os.Bundle;
import android.provider.Settings.Secure;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Logger.LogLevel;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;

import cheese.squeeze.game.CSGame;
import cheese.squeeze.tweenAccessors.ActionResolver;

public class AndroidLauncher extends AndroidApplication implements ActionResolver	 {

	private Tracker tracker;
	private Tracker globalTracker;
	private static Tracker t;
	 
	

	private static final String PROPERTY_ID = "UA-56519107-1";
 
	/**
	 * Enum used to identify the tracker that needs to be used for tracking.
	 * 
	 * A single tracker is usually enough for most purposes. In case you do need
	 * multiple trackers, storing them all in Application object helps ensure
	 * that they are created only once per application instance.
	 */
	public enum TrackerName {
		APP_TRACKER, // Tracker used only in this app.
		GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg:
						// roll-up tracking.
		ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a
							// company.
	}
 
	HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();
 
	synchronized Tracker getTracker(TrackerName trackerId) {
		if (!mTrackers.containsKey(trackerId)) {
 
			GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
			
			Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.getTracker(PROPERTY_ID)
					: (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics
							.getTracker("UA-56519107-1") : analytics
							.getTracker(String.valueOf(R.xml.ecommerce_tracker));
							mTrackers.put(trackerId, t);
		}
		return mTrackers.get(trackerId);
	}
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//t = GoogleAnalytics.getInstance(this).getTracker(PROPERTY_ID);
		//tracker = this.getTracker(TrackerName.APP_TRACKER);
		//globalTracker = this.getTracker(TrackerName.GLOBAL_TRACKER);
		GoogleAnalytics.getInstance(this).getLogger().setLogLevel(LogLevel.VERBOSE);
		initialize(new CSGame(this), config);
	}

	@Override
	public void reportAnalytics(String action,String cat,long value) {

		
		EasyTracker easyTracker = EasyTracker.getInstance(AndroidLauncher.this);

		// Send the custom dimension value with a screen view.
		// Note that the value only needs to be sent once, so it is set on the Map,
		// not the tracker.
		//easyTracker.send(MapBuilder.createAppView().set("1", "test").build());
		
		String android_id = Secure.getString(getContext().getContentResolver(),Secure.ANDROID_ID);
		
		easyTracker.send(MapBuilder.createEvent(cat, action,android_id, value).build());
		//System.out.println("cat: "+cat + "\t" + action+ "\t" + "4 \t" + value);
		
		
		/*
		globalTracker.set(Fields.SCREEN_NAME, path);

		globalTracker.send(MapBuilder.createAppView().build());
		
		
		

		HashMap<String, String> hitParameters = new HashMap<String, String>();
		hitParameters.put(Fields.HIT_TYPE, "appview");
		hitParameters.put(Fields.SCREEN_NAME, "Home Screen");

		tracker.send(hitParameters);
		
		
		globalTracker.set(Fields.SCREEN_NAME, path);

		globalTracker.send(MapBuilder.createAppView().build());
		
		t.set(Fields.SCREEN_NAME, path);
		

		t.send(MapBuilder.createAppView().build());
		
		t.send(MapBuilder
			      .createEvent("UX", "appstart", null, null)
			      .set(Fields.SESSION_CONTROL, "start")
			      .build()
			    );
		
		*/
		
	}
	
	  @Override
	  public void onStart() {
	    super.onStart();
	    // The rest of your onStart() code.
	    EasyTracker.getInstance(this).activityStart(this);  // Add this method.
	  }

	  @Override
	  public void onStop() {
	    super.onStop();
	    // The rest of your onStop() code.
	    EasyTracker.getInstance(this).activityStop(this);  // Add this method.
	  }

}
