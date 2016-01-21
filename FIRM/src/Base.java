import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 * Class used to store the Base songs.
 * 
 * @author Brandon Cox
 * 		
 */
public class Base {
	
	private ArrayList<Note> notes;
	private HashMap<Integer, Duration> noteFollow;
	private HashMap<Integer, Pitch> pitchFollow;
	
	/**
	 * Creates a list of every note in the given songs. Creates the basis of the algorithm to compose songs.
	 * 
	 * @param songs
	 *            The songs to create the base from.
	 */
	public Base(Song... songs) {
		notes = new ArrayList<Note>();
		pitchFollow = new HashMap<Integer, Pitch>();
		noteFollow = new HashMap<Integer, Duration>();
		for(int i = 0; i < songs.length; i++) { // Loop through songs
			Sequence s = songs[i].getSequence();
			Track[] t = s.getTracks();
			int noteIndex = 0;
			for(int j = 0; j < t.length; j++) { // loop through tracks
				for(int k = 0; k < t[j].size(); k++) { // loop through midiEvents
					MidiEvent me = t[j].get(k);
					if(me.getMessage() instanceof ShortMessage) {
						ShortMessage sm = (ShortMessage) me.getMessage();
						// data1 = key
						// data2 = vel
						if(sm.getCommand() == ShortMessage.NOTE_ON) {
							long dur = -1;
							for(int l = k + 1; l < t[j].size(); l++) { // loop through more midievents
								MidiEvent me1 = t[j].get(l);
								if(me1.getMessage() instanceof ShortMessage) {
									ShortMessage end = (ShortMessage) me1.getMessage();
									if(end.getCommand() == ShortMessage.NOTE_ON && (end.getData2() == 0 && end.getData1() == sm.getData1())) {
										dur = me1.getTick() - me.getTick();
										System.out.println(dur);
										break;
									}
								}
							}
							int pitch = sm.getData1();
							notes.add(new Note(pitch, dur));
							
							if(noteIndex != 0) {
								int roundDur = s.getResolution() / 24; // integer division could be an issue
								if(pitchFollow.containsKey(pitch)) {
									pitchFollow.get(notes.get(noteIndex - 1).getPitch()).addFollow(pitch);
								} else {
									pitchFollow.put(pitch, new Pitch(pitch));
								}
								if(noteFollow.containsKey(roundDur)) {
									noteFollow.get(notes.get(noteIndex - 1).getDuration()).addFollow(roundDur);
								} else {
									noteFollow.put(roundDur, new Duration(roundDur));
								}
							}
							noteIndex++;
						}
					}
				}
			}
		}
	}
	
	/**
	 * Generates a Song object based on the .mid file given.
	 * 
	 * @param f
	 *            The .mid file to read from
	 * @return A song object created from the imported midi file
	 */
	public static Song importMidi(File f) {
		try {
			Sequence s = MidiSystem.getSequence(f);
			return new Song(s);
		} catch(InvalidMidiDataException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
