
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
	 * @param follow
	 *            The Note which follows the Note. Used when generating songs.
	 */
	public Note(int pitch, long duration, Note follow) {
		this.pitch = pitch;
		this.duration = duration;
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
