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
	
	private static void writeSong(int depth, File f) {
		Base b = Base.readBase(f);
		Song s = b.generateSong(65536, depth);
		s.setInstrument(ProgramChange.MidiProgram.ACOUSTIC_GRAND_PIANO);
		s.write(f, b.getRes());
	}
	
	/**
	 * Main method for music generator.
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {
		File fi = new File("Fiesta.mid");
		File tbm = new File("tbm.mid");
		File mar = new File("mario.mid");
		
		// generate songbases
		for(int i = 2; i <= 2; i++) {
			File f = new File("Depth of " + i);
			if(!f.exists())
				f.mkdir();
			new Base(2, tbm).saveBase(new File("Depth of " + i + "\\Three Blind Mice.songBase"));
			new Base(i, mar).saveBase(new File("Depth of " + i + "\\Mario Theme.songBase"));
			new Base(i, fi).saveBase(new File("Depth of " + i + "\\Fiesta.songBase"));
			new Base(i, tbm, mar).saveBase(new File("Depth of " + i + "\\Three_Blind_Mice_AND_Mario.songBase"));
			new Base(i, tbm, fi).saveBase(new File("Depth of " + i + "\\Three_Blind_Mice_AND_Fiesta.songBase"));
			new Base(i, mar, fi).saveBase(new File("Depth of " + i + "\\Mario_AND_Fiesta.songBase"));
			new Base(i, tbm, mar, fi).saveBase(new File("Depth of " + i + "\\Three_Blind_Mice_AND_Mario_AND_Fiesta.songBase"));
			
		}
		// key
		// transpose
		
		// write new song
		
		for(int i = 2; i <= 2; i++) {
			writeSong(i, new File("Depth of " + i + "\\Three Blind Mice.songBase"));
			writeSong(i, new File("Depth of " + i + "\\Mario Theme.songBase"));
			writeSong(i, new File("Depth of " + i + "\\Fiesta.songBase"));
			writeSong(i, new File("Depth of " + i + "\\Three_Blind_Mice_AND_Mario.songBase"));
			writeSong(i, new File("Depth of " + i + "\\Three_Blind_Mice_AND_Fiesta.songBase"));
			writeSong(i, new File("Depth of " + i + "\\Mario_AND_Fiesta.songBase"));
			writeSong(i, new File("Depth of " + i + "\\Three_Blind_Mice_AND_Mario_AND_Fiesta.songBase"));
		}
		// INTRO
		// why is it important
		// what are you doing
		// tell a story as to what is currently being done, what are people currently doing, etc. (5 para.)
		// make sure you cite
		// hypothesis (what is success)
		// intro to procedures
		
		// PROCEDURES
		// what are you going to do
		// data analysis
		
		// CONCLUSION
		// summarize everything
		// make conclusions
		// what are error sources/problems
		// future research
	}
}

// Read probability of a note of a specific type and pitch following another note
// Used Math.random() to generate a song based on that

// read in bars at a time rather than just notes for duration calculation
// increase library
// read in 2 notes at a time rather than just one for pitch calculation
// make measures work...