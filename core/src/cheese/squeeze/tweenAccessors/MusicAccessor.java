package cheese.squeeze.tweenAccessors;



import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

public class MusicAccessor extends Accessor{


	/**
	 * Holds the music currently being played, if any.
	 */
	private static Music musicBeingPlayed = null;

	/**
	 * The volume to be set on the music.
	 */
	private static float volume = 1f;

	/**
	 * Whether the music is enabled.
	 */
	private static boolean enabled = true;


	/**
	 * Plays the given music (starts the streaming).
	 * <p>
	 * If there is already a music being played it is stopped automatically.
	 */
	public static void play(Music music) {
		// check if the music is enabled
		if (!enabled)
			return;

		if(musicBeingPlayed == null || !musicBeingPlayed.equals(music)) {
			// stop any music being played if it is other music
			stop();

			// start streaming the new music
			musicBeingPlayed = music;
			musicBeingPlayed.setVolume(volume);
			musicBeingPlayed.setLooping(true);
			musicBeingPlayed.play();
		}
	}

	/**
	 * Stops and disposes the current music being played, if any.
	 */
	public static void stop() {
		if (musicBeingPlayed != null) {
			musicBeingPlayed.stop();
			musicBeingPlayed.dispose();
		}
	}

	/**
	 * Sets the music volume which must be inside the range [0,1].
	 */
	public static void setVolume(float volume) {

		// check and set the new volume
		if (volume < 0 || volume > 1f) {
			throw new IllegalArgumentException(
					"The volume must be inside the range: [0,1]");
		}
		MusicAccessor.volume = volume;

		// if there is a music being played, change its volume
		if (musicBeingPlayed != null) {
			musicBeingPlayed.setVolume(volume);
		}
	}

	/**
	 * Enables or disabled the music.
	 */
	public static void setEnabled(boolean enabled) {
		MusicAccessor.enabled = enabled;
		if(enabled) {
			play(musicBeingPlayed);
		}
		
		// if the music is being deactivated, stop any music being played
		if (!enabled) {
			stop();
		}
	}

	/**
	 * Disposes the music manager.
	 */
	public void dispose() {
		stop();
	}

	public static boolean isOn() {
		return enabled;
	}

}
