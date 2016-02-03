package cox5529;

import java.util.ArrayList;

public class Measure {
	
	private ArrayList<Double> notes;
	private ArrayList<Integer> old;
	
	public Measure(ArrayList<Double> notes) {
		this.notes = notes;
	}
	
	public Measure(ArrayList<Integer> notes, int resolution) {
		this.notes = new ArrayList<Double>();
		this.old = notes;
		for(int i = 0; i < notes.size(); i++) {
			if(notes.get(i) != 0) {
				this.notes.add((notes.get(i) + 0.0) / resolution);
			}
		}
	}
	
	public int[] generateMeasure(int resolution) {
		int[] re = new int[notes.size()];
		for(int i = 0; i < re.length; i++) {
			re[i] = old.get(i);
			// re[i] = (int) (resolution * notes.get(i));
			// System.out.println(notes.get(i) * resolution + " : " + old.get(i));
		}
		return re;
	}
}
