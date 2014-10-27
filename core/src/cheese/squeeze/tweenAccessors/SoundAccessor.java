package cheese.squeeze.tweenAccessors;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

public class SoundAccessor extends Accessor{

	private static Sound soundEffect;

	/**
	 * The volume to be set on the sound.
	 */
	private static float volume = 1f;

	/**
	 * Whether the sounds are enabled.
	 */
	private static boolean enabled = true;


	/**
	 * Plays the given sound (starts the streaming).
	 * <p>
	 * If there is already a sound being played it is stopped automatically.
	 */
	public static void play(Sound sound) {
		// check if the sound is enabled
		if (enabled) {
			// start streaming the new sound
			soundEffect = sound;
			long id = soundEffect.play();
			soundEffect.setLooping(id, false);
			//soundEffect.setVolume(0, volume);
			//soundEffect.play();
			//soundEffect.dispose();
			
		}
		

	}


	/**
	 * Sets the sound volume which must be inside the range [0,1].
	 */
	public static void setVolume(float volume) {

		// check and set the new volume
		if (volume < 0 || volume > 1f) {
			throw new IllegalArgumentException(
					"The volume must be inside the range: [0,1]");
		}
		SoundAccessor.volume = volume;
	}

	/**
	 * Enables or disables the sound.
	 */
	public static void setEnabled(boolean enabled) {
		SoundAccessor.enabled = enabled;
	}


	public static boolean isOn() {
		return enabled;
	}


}
