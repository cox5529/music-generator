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
	 * Main method for music generator.
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {
		// Song s = Base.importMidi(new File("smb1-Theme.mid"));
		Song[] s = new Song[8];
		for(int i = 0; i < s.length; i++) {
			s[i] = Base.importMidi(new File("m" + (i + 1) + ".mid"));
		}
		Base b = new Base(2, Base.importMidi(new File("smb1-Theme.mid")));
		b.generateSong(4096, 2).write(new File("out.mid"));
	}
}

// Read probability of a note of a specific type and pitch following another note
// Used Math.random() to generate a song based on that

// read in bars at a time rather than just notes for duration calculation
// increase library
// read in 2 notes at a time rather than just one for pitch calculation
// make measures work...