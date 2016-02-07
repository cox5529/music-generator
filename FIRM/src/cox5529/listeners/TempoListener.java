package cox5529.listeners;

import java.util.ArrayList;

import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.util.MidiEventListener;

/**
 * Listener used to listen for TempoChange events.
 * 
 * @author Brandon Cox
 * 		
 */
public class TempoListener implements MidiEventListener {
	
	private ArrayList<Float> bpm;
	
	/**
	 * Constructs a TempoListener object.
	 */
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
	
	/**
	 * Gets the list of tempos used throughout the song.
	 * 
	 * @return An ArrayList of tempos.
	 */
	public ArrayList<Float> getBpm() {
		return bpm;
	}
	
}
