package cox5529.listeners;

import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.meta.KeySignature;
import com.leff.midi.util.MidiEventListener;

public class KeySignatureListener implements MidiEventListener {
	
	private int flats;
	private boolean init = false;
	private boolean major;
	
	public int getFlats() {
		return flats;
	}
	
	public boolean isMajor() {
		return major;
	}
	
	public boolean isInit() {
		return init;
	}
	
	@Override
	public void onStart(boolean fromBeginning) {
		System.out.println("Reading for Key Signature.");
	}
	
	@Override
	public void onEvent(MidiEvent event, long ms) {
		if(event instanceof KeySignature && !init) {
			KeySignature ks = (KeySignature) event;
			flats = ks.getKey();
			init = true;
			major = (ks.getScale() == 0);
			System.out.println("Processed key signature: " + (major ? "major": "minor") + " " + Math.abs(flats) + " " + (flats >= 0 ? "sharps": "flats"));
		}
	}
	
	@Override
	public void onStop(boolean finished) {
		System.out.println("Finished reading for Key Signature.");
	}
	
}
