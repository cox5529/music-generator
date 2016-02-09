package cox5529;

import java.io.File;

import com.leff.midi.event.ProgramChange;

import cox5529.storage.Song;

/**
 * Main class for music generator.
 * 
 * @author Brandon Cox
 * 		
 */
public class Main {
	
	/**
	 * Main method for music generator.
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {
		// Song s = Base.importMidi(new File("smb1-Theme.mid"));
		File[] s = new File[8];
		for(int i = 0; i < s.length; i++) {
			s[i] = new File("m" + (i + 1) + ".mid");
		}
		File fi = new File("Fiesta.mid");
		File tbm = new File("tbm.mid");
		File mar = new File("mario.mid");
		Base b = new Base(2, mar, tbm, fi);
		b.saveBase(new File("Mar.songBase"));
		Song song = b.generateSong(25 * b.getRes() * 4, 2);
		song.setInstrument(ProgramChange.MidiProgram.TRUMPET);
		song.write(new File("out.mid"), b.getRes());
	}
}

// Read probability of a note of a specific type and pitch following another note
// Used Math.random() to generate a song based on that

// read in bars at a time rather than just notes for duration calculation
// increase library
// read in 2 notes at a time rather than just one for pitch calculation
// make measures work...