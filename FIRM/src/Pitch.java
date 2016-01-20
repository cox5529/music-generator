import java.util.ArrayList;

/**
 * A class used to store the occurences of a given
 * 
 * @author Brandon Cox
 * 		
 */
public class Pitch {
	
	private int p;
	private ArrayList<Integer> follow;
	
	/**
	 * Constructs a Pitch object based on a given pitch id.
	 * 
	 * @param p
	 *            The pitch id.
	 */
	public Pitch(int p) {
		this.p = p;
		this.follow = new ArrayList<Integer>();
	}
	
	/**
	 * Adds a pitch to the list of the following pitches.
	 * 
	 * @param f
	 *            The pitch to add.
	 */
	public void addFollow(int f) {
		follow.add(f);
	}
	
	/**
	 * Gets the pitch.
	 * 
	 * @return The pitch.
	 */
	public int getPitch() {
		return p;
	}
	
}
