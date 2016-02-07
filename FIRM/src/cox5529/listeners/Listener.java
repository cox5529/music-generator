package cox5529.listeners;

/**
 * Class used in Listeners.
 * 
 * @author Brandon Cox
 *		
 */
public class Listener {
	
	/**
	 * The length in ticks of the MidiFile being analyzed.
	 */
	protected long size;
	
	/**
	 * Constructs a Listener object.
	 * 
	 * @param size The length in ticks of the MidiFile being analyzed.
	 */
	public Listener(long size) {
		this.size = size;
	}
}
