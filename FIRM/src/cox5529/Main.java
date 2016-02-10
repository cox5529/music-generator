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
	
	private static void writeSong(int depth, String f, int acc) {
		Base b = Base.readBase(new File(f + ".songBase"));
		Song s = b.generateSong(65536, depth, acc);
		s.setInstrument(ProgramChange.MidiProgram.TRUMPET);
		s.write(new File(f + ".mid"), b.getRes());
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
			// new Base(2, tbm).saveBase(new File("Depth of " + i + "\\Three Blind Mice.songBase"));
			writeSong(i, "Depth of " + i + "\\Three Blind Mice", -3);
			// new Base(i, mar).saveBase(new File("Depth of " + i + "\\Mario Theme.songBase"));
			writeSong(i, "Depth of " + i + "\\Mario Theme", -3);
			// new Base(i, fi).saveBase(new File("Depth of " + i + "\\Fiesta.songBase"));
			writeSong(i, "Depth of " + i + "\\Fiesta", -3);
			// new Base(i, tbm, mar).saveBase(new File("Depth of " + i + "\\Three_Blind_Mice_AND_Mario.songBase"));
			writeSong(i, "Depth of " + i + "\\Three_Blind_Mice_AND_Mario", -3);
			// new Base(i, tbm, fi).saveBase(new File("Depth of " + i + "\\Three_Blind_Mice_AND_Fiesta.songBase"));
			writeSong(i, "Depth of " + i + "\\Three_Blind_Mice_AND_Fiesta", -3);
			// new Base(i, mar, fi).saveBase(new File("Depth of " + i + "\\Mario_AND_Fiesta.songBase"));
			writeSong(i, "Depth of " + i + "\\Mario_AND_Fiesta", -3);
			// new Base(i, tbm, mar, fi).saveBase(new File("Depth of " + i + "\\Three_Blind_Mice_AND_Mario_AND_Fiesta.songBase"));
			writeSong(i, "Depth of " + i + "\\Three_Blind_Mice_AND_Mario_AND_Fiesta", -3);
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