package cox5529;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

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
		File folder = new File("Output Depth of " + depth);
		if(!folder.exists())
			folder.mkdirs();
		folder = new File("Tables\\Output Depth of " + depth);
		if(!folder.exists())
			folder.mkdirs();
		Base b = Base.readBase(new File("Depth of " + depth + "\\" + f + ".songBase"));
		Song s = b.generateSong(b.getOriginalLength(), depth, acc);
		writeTable("Output Depth of " + depth + "\\" + f + " DegreeTable", b.getDegreeTable());
		writeTable("Output Depth of " + depth + "\\" + f + " MetricTable", b.getMetricTable());
		s.setInstrument(ProgramChange.MidiProgram.ACOUSTIC_GRAND_PIANO);
		s.write(new File("Output Depth of " + depth + "\\" + f + ".mid"), b.getRes());
	}
	
	private static void writeBase(int depth, String song, File f) {
		File folder = new File("Depth of " + depth);
		if(!folder.exists())
			folder.mkdirs();
		folder = new File("Tables\\Depth of " + depth);
		if(!folder.exists())
			folder.mkdirs();
		Base b = new Base(depth, f);
		b.saveBase(new File("Depth of " + depth + "\\" + song + ".songBase"));
		writeTable("Depth of " + depth + "\\" + song + " DegreeTable", b.getDegreeTable());
		writeTable("Depth of " + depth + "\\" + song + " MetricTable", b.getMetricTable());
	}
	
	private static void writeTable(String title, int[] table) {
		File f = new File("Tables\\" + title + ".txt");
		try {
			Scanner sc = new Scanner(f);
			String exist = "";
			while(sc.hasNext()) {
				exist += sc.nextLine() + "\n";
			}
			sc.close();
			FileWriter fw = new FileWriter(f);
			fw.write(exist + "\n" + title + "\n\n");
			for(int i = 0; i < table.length; i++) {
				fw.write(/* i + "\t\t" + */table[i] + "\n");
			}
			fw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
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
		File dem = new File("Demo.mid");
		
		// generate songbases
		for(int i = 2; i <= 2; i++) {
			File f = new File("Depth of " + i);
			if(!f.exists())
				f.mkdir();
			writeBase(1, "Demo", dem);
			writeSong(1, "Demo", 0);
			// new Base(i, mar).saveBase(new File("Depth of " + i + "\\Mario Theme.songBase"));
			// writeSong(i, "Depth of " + i + "\\Mario Theme", -3);
			// new Base(i, fi).saveBase(new File("Depth of " + i + "\\Fiesta.songBase"));
			// writeSong(i, "Depth of " + i + "\\Fiesta", -3);
			// new Base(i, tbm, mar).saveBase(new File("Depth of " + i + "\\Three_Blind_Mice_AND_Mario.songBase"));
			// writeSong(i, "Depth of " + i + "\\Three_Blind_Mice_AND_Mario", -3);
			// new Base(i, tbm, fi).saveBase(new File("Depth of " + i + "\\Three_Blind_Mice_AND_Fiesta.songBase"));
			// writeSong(i, "Depth of " + i + "\\Three_Blind_Mice_AND_Fiesta", -3);
			// new Base(i, mar, fi).saveBase(new File("Depth of " + i + "\\Mario_AND_Fiesta.songBase"));
			// writeSong(i, "Depth of " + i + "\\Mario_AND_Fiesta", -3);
			// new Base(i, tbm, mar, fi).saveBase(new File("Depth of " + i + "\\Three_Blind_Mice_AND_Mario_AND_Fiesta.songBase"));
			// writeSong(i, "Depth of " + i + "\\Three_Blind_Mice_AND_Mario_AND_Fiesta", -3);
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