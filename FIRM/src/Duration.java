import java.util.ArrayList;
import java.util.HashMap;

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
	
	/**
	 * Gets the percentage of notes followed by a specific duration
	 * 
	 * @return A HashMap of the percentages. The keys are the note values, the values are the percentages.
	 */
	public HashMap<Integer, Double> calcPercentage() {
		// count all of specific duration
		HashMap<Integer, Integer> count = new HashMap<Integer, Integer>();
		for(int i = 0; i < follow.size(); i++) {
			if(count.containsKey(i)) {
				count.put(i, count.get(i) + 1);
			} else {
				count.put(i, 1);
			}
		}
		// get percentages
		HashMap<Integer, Double> pct = new HashMap<Integer, Double>();
		int sum = 0;
		for(int i: count.values()) {
			sum += i;
		}
		for(int i = 0; i < count.size(); i++) {
			int key = (int) count.keySet().toArray()[i];
			pct.put(key, (count.get(key) + 0.0) / sum);
		}
		// return
		return pct;
	}
}
