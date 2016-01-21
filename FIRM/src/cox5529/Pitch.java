package cox5529;
import java.util.ArrayList;
import java.util.HashMap;

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
	
	/**
	 * Gets the percentage of notes followed by a specific pitch
	 * 
	 * @return A HashMap of the percentages. The keys are the note values, the values are the percentages.
	 */
	public HashMap<Integer, Double> calcPercentage() {
		// count all of specific duration
		HashMap<Integer, Integer> count = new HashMap<Integer, Integer>();
		for(int i = 0; i < follow.size(); i++) {
			if(count.containsKey(i)) {
				count.put(follow.get(i), count.get(i) + 1);
			} else {
				count.put(follow.get(i), 1);
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
			System.out.println(key);
			pct.put(key, (count.get(key) + 0.0) / sum);
		}
		double s = 0;
		for(int i = 0; i < pct.size(); i++) {
			s += pct.get((Integer) pct.keySet().toArray()[i]);
			pct.put((Integer) pct.keySet().toArray()[i], s);
		}
		// return
		return pct;
	}
	
}
