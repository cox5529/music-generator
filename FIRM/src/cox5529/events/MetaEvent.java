package cox5529.events;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;

/**
 * Utility class used to store MidiEvents that are meta.
 * 
 * @author Brandon Cox
 *		
 */
public class MetaEvent extends MidiEvent {
	
	/**
	 * ID for a Tempo change Event
	 */
	public static final int TEMPO = 0x51;
	
	/**
	 * ID for a Time Signature change Event.
	 */
	public static final int TIME_SIGNATURE = 0x58;
	
	/**
	 * ID for a Key Signature change Event.
	 */
	public static final int KEY_SIGNATURE = 0x59;
	
	/**
	 * Constructs a MetaEvent object.
	 * 
	 * @param msg
	 *            MetaMessage to event-ify
	 * @param tick
	 *            The tick at which the event occurs.
	 */
	public MetaEvent(MetaMessage msg, long tick) {
		super(msg, tick);
	}
	
	/**
	 * Constructs a MetaEvent object.
	 * 
	 * @param type
	 *            The int type of the Event.
	 * @param data
	 *            The data to store in the event.
	 * @param tick
	 *            The tick at which the event occurs.
	 * @throws InvalidMidiDataException
	 *             If the MetaMessage object cannot be created.
	 */
	public MetaEvent(int type, byte[] data, int tick) throws InvalidMidiDataException {
		this(new MetaMessage(type, data, data.length), tick);
	}
	
}
