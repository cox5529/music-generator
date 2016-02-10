package cox5529.listeners;

import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.meta.KeySignature;
import com.leff.midi.util.MidiEventListener;

/**
 * Listener used to listen for KeySignature events.
 * 
 * @author Brandon Cox
 * 		
 */
public class KeySignatureListener implements MidiEventListener {
	
	private int flats;
	private boolean init = false;
	private boolean major;
	
	/**
	 * Gets the amount of flats or sharps in the key signature.
	 * 
	 * @return The amount of flats/sharps in the key signature. Negative if there are flats, positive if there are sharps.
	 */
	public int getFlats() {
		return flats;
	}
	
	/**
	 * Determines if the key signature is major or minor.
	 * 
	 * @return True if it is major, false if it is minor.
	 */
	public boolean isMajor() {
		return major;
	}
	
	/**
	 * Determines if the key signature has been calculated yet.
	 * 
	 * @return True if the key signature has been calculated, false if it has not.
	 */
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
