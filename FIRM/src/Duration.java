import java.util.ArrayList;

/**
 * Class to store the duration of various notes.
 * 
 * @author Brandon Cox
 *		
 */
public class Duration {
	
	private int d;
	private ArrayList<Integer> follow;
	
	/**
	 * Constructs a Duration object for the given duration.
	 * 
	 * @param d
	 *            The duration to construct the Duration object around.
	 */
	public Duration(int d) {
		this.d = d;
		follow = new ArrayList<Integer>();
	}
	
	/**
	 * Adds a duration value which follows this duration to the follow ArrayList.
	 * 
	 * @param d
	 *            The duration value which follows this duration.
	 */
	public void addFollow(int d) {
		follow.add(d);
	}
	
	/**
	 * Gets the duration this object was constructed for.
	 * 
	 * @return The duration value.
	 */
	public int getDuration() {
		return d;
	}
}
