package cox5529.listeners;

import java.util.ArrayList;

import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOff;
import com.leff.midi.util.MidiEventListener;

public class NoteOffListener implements MidiEventListener {
	
	private ArrayList<Long> endings;
	
	public NoteOffListener() {
		endings = new ArrayList<Long>();
	}
	
	@Override
	public void onStart(boolean fromBeginning) {
		System.out.println("Reading for note endings.");
	}
	
	public ArrayList<Long> getEndings() {
		return endings;
	}
	
	@Override
	public void onEvent(MidiEvent event, long ms) {
		if(event instanceof NoteOff) {
			endings.add(event.getTick());
			System.out.println(event.getTick() + ": processed note_off");
		}
	}
	
	@Override
	public void onStop(boolean finished) {
		System.out.println("Finished reading for note endings.");
	}
	
}
