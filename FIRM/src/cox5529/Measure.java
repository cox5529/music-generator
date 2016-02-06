package cox5529;

import java.util.ArrayList;

public class Measure {
	
	private ArrayList<Long> notes;
	
	public Measure(ArrayList<Long> notes) {
		this.notes = notes;
	}
	
	public long[] generateMeasure() {
		long[] re = new long[notes.size()];
		for(int i = 0; i < re.length; i++) {
			re[i] = notes.get(i);
		}
		return re;
	}
}
