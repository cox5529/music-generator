
/**
 * Object used to store Notes within songs.
 * 
 * @author Brandon Cox
 * 		
 */
public class Note {
	
	private int pitch;
	private long duration;
	private Note follow;
	
	/**
	 * Constructs a Note object.
	 * 
	 * @param pitch
	 *            The pitch of the Note
	 * @param duration
	 *            The duration of the Note
	 */
	public Note(int pitch, long duration) {
		this.pitch = pitch;
		this.duration = duration;
	}
	
	/**
	 * Sets the note which follows this note.
	 * 
	 * @param follow
	 *            The Note object which follows this Note.
	 */
	public void setFollow(Note follow) {
		this.follow = follow;
	}
	
	/**
	 * Gets the Note which follows this Note.
	 * 
	 * @return The Note which follows this Note.
	 */
	public Note getFollow() {
		return follow;
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
	public long getDuration() {
		return duration;
	}
	
}
