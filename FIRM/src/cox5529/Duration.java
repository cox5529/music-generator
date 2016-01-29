package cox5529;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class used to store the occurrences of a given duration within a song.
 * 
 * @author Brandon Cox
 * 		
 */
public class Duration {
	
	private int d;
	private ArrayList<Integer> follow;
	
	/**
	 * Constructs a Duration object based on a given duration.
	 * 
	 * @param d
	 *            The given duration.
	 */
	public Duration(int d) {
		this.d = d;
		this.follow = new ArrayList<Integer>();
	}
	
	/**
	 * Adds a duration to the list of the following durations.
	 * 
	 * @param f
	 *            The duration to add.
	 */
	public void addFollow(int f) {
		follow.add(f);
	}
	
	/**
	 * Gets the duration.
	 * 
	 * @return The duration.
	 */
	public int getDuration() {
		return d;
	}
	
	public HashMap<Integer, Double> calcPercentage() {
		// count all of the specific durations
		HashMap<Integer, Integer> count = new HashMap<Integer, Integer>();
		for(int i = 0; i < follow.size(); i++) {
			int index = follow.get(i);
			if(count.containsKey(index)) {
				count.put(index, count.get(index) + 1);
			} else {
				count.put(index, 1);
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
		double s = 0;
		for(int i = 0; i<pct.size(); i++){
			s+=pct.get((Integer) pct.keySet().toArray()[i]);
			pct.put((Integer) pct.keySet().toArray()[i], s);
		}
		return pct;
	}
}
