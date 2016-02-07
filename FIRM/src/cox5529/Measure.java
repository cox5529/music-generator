package cox5529;

import java.util.ArrayList;

/**
 * Class used to store Measures within read songs.
 * 
 * @author Brandon Cox
 *		
 */
public class Measure {
	
	private ArrayList<Long> notes;
	
	/**
	 * Constructs a measure object using the notes within the measure.
	 * 
	 * @param notes The notes within the measure in the order in which they are played.
	 */
	public Measure(ArrayList<Long> notes) {
		this.notes = notes;
	}
	
	/**
	 * Boxes the measure object up into an array.
	 * 
	 * @return The array.
	 */
	public long[] generateMeasure() {
		long[] re = new long[notes.size()];
		for(int i = 0; i < re.length; i++) {
			re[i] = notes.get(i);
		}
		return re;
	}
}
