package cox5529.listeners;

import java.util.ArrayList;

import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOff;
import com.leff.midi.util.MidiEventListener;

/**
 * Listener used to listen for NoteOff events.
 * @author Brandon Cox
 *
 */
public class NoteOffListener extends Listener implements MidiEventListener {
	
	private ArrayList<Long> endings;
	
	/**
	 * Constructs a NoteOffListener object.
	 * @param size Length in ticks of the MidiFile being analyzed.
	 */
	public NoteOffListener(long size) {
		super(size);
		endings = new ArrayList<Long>();
	}
	
	@Override
	public void onStart(boolean fromBeginning) {
		System.out.println("Reading for note endings.");
	}
	
	/**
	 * Gets the tick position of all NoteOff events.
	 * @return An ArrayList of NoteOff event tick positions.
	 */
	public ArrayList<Long> getEndings() {
		return endings;
	}
	
	@Override
	public void onEvent(MidiEvent event, long ms) {
		if(event instanceof NoteOff) {
			endings.add(event.getTick());
			System.out.println(event.getTick() + ": processed note_off\tPCT COMPLETE: " + (((0.0 + event.getTick()) / size) * 100));
		}
	}
	
	@Override
	public void onStop(boolean finished) {
		System.out.println("Finished reading for note endings.");
	}
	
}
