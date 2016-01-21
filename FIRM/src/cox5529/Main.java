package cox5529;
import java.io.File;

import cox5529.base.Base;

/**
 * Main class for music generator.
 * 
 * @author Brandon Cox
 * 		
 */
public class Main {
	
	/**
	 * The duration of a sixteenth note.
	 */
	public static final int SIXTEENTH_NOTE_DURATION = 1;
	
	/**
	 * The duration of an eighth note.
	 */
	public static final int EIGHTH_NOTE_DURATION = 2;
	
	/**
	 * The duration of a quarter note.
	 */
	public static final int QUARTER_NOTE_DURATION = 4;
	
	/**
	 * The duration of a half note.
	 */
	public static final int HALF_NOTE_DURATION = 8;
	
	/**
	 * The duration of a whole note.
	 */
	public static final int WHOLE_NOTE_DURATION = 16;
	
	/**
	 * Main method for music generator.
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {
		Song s = Base.importMidi(new File("smb1-Theme.mid"));
		Song s1 = Base.importMidi(new File("starwars.mid"));
		Base b = new Base(s, s1);
		b.generateSong(1024, 4).write(new File("out.mid"));
	}
}

// Read probability of a note of a specific type and pitch following another note
// Used Math.random() to generate a song based on that