package cox5529.storage;

import java.io.Serializable;

/**
 * Object used to store Notes within songs.
 * 
 * @author Brandon Cox
 * 		
 */
public class Note implements Serializable{
	
	private static final long serialVersionUID = 5113749444335308737L;
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
