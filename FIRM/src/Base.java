import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

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
							int dur = -1;
							for(int l = k + 1; l < t[j].size(); l++) { // loop through more midievents
								MidiEvent me1 = t[j].get(l);
								if(me1.getMessage() instanceof ShortMessage) {
									ShortMessage end = (ShortMessage) me1.getMessage();
									if(end.getCommand() == ShortMessage.NOTE_ON && (end.getData2() == 0 && end.getData1() == sm.getData1())) {
										dur = (int) (me1.getTick() - me.getTick());
										break;
									}
								}
							}
							int pitch = sm.getData1();
							notes.add(new Note(pitch, dur));
							
							if(noteIndex != 0) {
								if(pitchFollow.containsKey(pitch)) {
									int index = notes.get(noteIndex - 1).getPitch();
									Pitch p = pitchFollow.get(index);
									p.addFollow(pitch);
									pitchFollow.put(index, p);
								} else {
									pitchFollow.put(pitch, new Pitch(pitch));
								}
							}
							noteIndex++;
						}
					}
				}
			}
		}
	}
	
	public Song generateSong(long length, int speed) {
		Song s = new Song(1, speed);
		long dur = 0;
		int notePitch = (int) pitchFollow.keySet().toArray()[0];
		int noteDur;
		while(dur < length) {
			Random r = new Random(System.nanoTime());
			int random = r.nextInt(100);
			if(random < 100) {
				noteDur = Main.SIXTEENTH_NOTE_DURATION;
			} else if(random < 70) {
				noteDur = Main.EIGHTH_NOTE_DURATION;
			} else if(random < 80) {
				noteDur = Main.QUARTER_NOTE_DURATION;
			} else if(random < 90) {
				noteDur = Main.HALF_NOTE_DURATION;
			} else {
				noteDur = Main.WHOLE_NOTE_DURATION;
			}
			s.createNote(s.getTracks()[0], notePitch, 127, dur, dur + noteDur);
			dur += noteDur;
			System.out.println(notePitch);
			Pitch p = pitchFollow.get(notePitch);
			System.out.println(pitchFollow.containsKey(notePitch));
			HashMap<Integer, Double> pct = p.calcPercentage();
			for(int i: pct.keySet()) {
				//System.out.println(i);
			}
			double rand = Math.random();
			Iterator<Entry<Integer, Double>> it = pct.entrySet().iterator();
			while(it.hasNext()) {
				Entry<Integer, Double> pair = (Entry<Integer, Double>) it.next();
				// System.out.println(pair.getKey());
				// System.out.println(pair.getValue());
				if(rand <= (Double) pair.getValue()) {
					notePitch = (Integer) pair.getKey();
					break;
				}
			}
		}
		return s;
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
