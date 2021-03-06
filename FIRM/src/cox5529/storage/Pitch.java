package cox5529.storage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class used to store the occurrences of a given pitch within a song.
 * 
 * @author Brandon Cox
 * 		
 */
public class Pitch implements Serializable{
	
	private static final long serialVersionUID = -1238593192399516331L;
	private ArrayList<Integer> p;
	private ArrayList<Integer> follow;
	
	/**
	 * Constructs a Pitch object based on a given array pitch ids.
	 * 
	 * @param p The pitch ids.
	 * @param fol The pitch which follows the set of pitch ids.
	 */
	public Pitch(ArrayList<Integer> p, int fol) {
		this.p = p;
		this.follow = new ArrayList<Integer>();
		follow.add(fol);
	}
	
	/**
	 * Adds a pitch to the list of the following pitches.
	 * 
	 * @param f The pitch to add.
	 */
	public void addFollow(int f) {
		follow.add(f);
	}
	
	/**
	 * Gets the pitch array.
	 * 
	 * @return The pitch array.
	 */
	public ArrayList<Integer> getPitch() {
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
		for(int i = 0; i < pct.size(); i++) {
			s += pct.get((Integer) pct.keySet().toArray()[i]);
			pct.put((Integer) pct.keySet().toArray()[i], s);
		}
		// return
		return pct;
	}
	
	/**
	 * Combines the follow ArrayList with another ArrayList.
	 * 
	 * @param f The ArrayList to combine with follow.
	 */
	public void combine(ArrayList<Integer> f) {
		follow.addAll(f);
	}
	
	/**
	 * Gets the follow ArrayList.
	 * 
	 * @return The follow ArrayList.
	 */
	public ArrayList<Integer> getFollow() {
		return follow;
	}
	
	public String toString() {
		String re = "";
		for(int i = 0; i < p.size(); i++) {
			re += " " + p.get(i);
		}
		
		return re;
	}
	
}
