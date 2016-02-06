package cox5529.listeners;

import java.util.ArrayList;

import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.util.MidiEventListener;

public class TempoListener implements MidiEventListener {
	
	private ArrayList<Float> bpm;
	
	public TempoListener() {
		bpm = new ArrayList<Float>();
	}
	
	@Override
	public void onStart(boolean fromBeginning) {
		System.out.println("Reading for tempo.");
	}
	
	@Override
	public void onEvent(MidiEvent event, long ms) {
		if(event instanceof Tempo) {
			Tempo t = (Tempo) event;
			bpm.add(t.getBpm());
			System.out.println(t.getTick() + ": processed tempo");
		}
	}
	
	@Override
	public void onStop(boolean finished) {
		System.out.println("Finished reading for tempo.");
	}
	
	public ArrayList<Float> getBpm() {
		return bpm;
	}
	
}
