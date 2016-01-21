package cox5529;

/**
 * Object used to store Notes within songs.
 * 
 * @author Brandon Cox
 * 		
 */
public class Note {
	
	private int pitch;
	private int duration;
	
	/**
	 * Constructs a Note object.
	 * 
	 * @param pitch
	 *            The pitch of the Note
	 * @param duration
	 *            The duration of the Note
	 */
	public Note(int pitch, int duration) {
		this.pitch = pitch;
		this.duration = duration;
	}
	
	/**
	 * Gets the pitch of this Note
	 * 
	 * @return The pitch of this Note
	 */
	public int getPitch() {
		return pitch;
	}
	
	/**
	 * Gets the duration of this Note
	 * 
	 * @return The duration of this Note
	 */
	public int getDuration() {
		return duration;
	}
	
}
