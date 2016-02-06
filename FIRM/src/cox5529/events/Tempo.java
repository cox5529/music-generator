package cox5529.events;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;

public class Tempo extends MetaMessage {
	
	private int n;
	private int d;
	
	public Tempo(int n, int d) {
		this.n = n;
		this.d = d;
	}
	
	public MetaMessage getMetaMessage() {
		byte[] data = {(byte) n, (byte) d};
		try {
			return new MetaMessage(MetaEvent.TEMPO, data, data.length);
		} catch(InvalidMidiDataException e) {
			e.printStackTrace();
		}
		return null;
	}
}
