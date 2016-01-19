import java.io.File;
import java.util.Random;

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
		Song s = new Song(1, 6);
		Random r = new Random(System.nanoTime());
		int tick = 0;
		int[] notes = Song.generateMinorOctave(60);
		while(tick < 2048) {
			int rand = r.nextInt(100);
			int dur = 0;
			if(rand < 50) {
				dur = SIXTEENTH_NOTE_DURATION;
			} else if(rand < 70) {
				dur = EIGHTH_NOTE_DURATION;
			} else if(rand < 90) {
				dur = QUARTER_NOTE_DURATION;
			} else if(rand < 95) {
				dur = HALF_NOTE_DURATION;
			} else {
				dur = WHOLE_NOTE_DURATION;
			}
			s.createNote(s.getTracks()[0], notes[r.nextInt(notes.length)], 127, tick, dur);
			tick += dur;
		}
		s.write(new File("out.mid"));
	}
}
