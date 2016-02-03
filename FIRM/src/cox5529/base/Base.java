package cox5529.base;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import cox5529.Song;

/**
 * Class used to store the Base songs.
 * 
 * @author Brandon Cox
 * 		
 */
public class Base {
	
	private ArrayList<BaseTrack> bases;
	private int resolution;
	
	/**
	 * Creates a list of every note in the given songs. Creates the basis of the algorithm to compose songs.
	 * 
	 * @param songs
	 *            The songs to create the base from.
	 */
	public Base(int depth, Song... songs) {
		bases = new ArrayList<BaseTrack>();
		Sequence s = null;
		try {
			s = new Sequence(Sequence.PPQ, songs[0].getSequence().getResolution());
		} catch(Exception e) {
			e.printStackTrace();
		}
		resolution = songs[0].getSequence().getResolution();
		Track end = s.createTrack();
		long time = 0;
		for(int i = 0; i < songs.length; i++) { // this just combines all of the tracks of each song into a single track
			Track[] tracks = songs[i].getTracks();
			for(int j = 0; j < tracks.length; j++) {
				MidiEvent[] events = new MidiEvent[tracks[j].size()];
				for(int k = 0; k < events.length; k++) {
					events[k] = tracks[j].get(k);
					if(events[k].getMessage() instanceof ShortMessage) {
						ShortMessage sm = (ShortMessage) events[k].getMessage();
						ShortMessage last = null;
						if(sm.getCommand() == ShortMessage.NOTE_ON && (sm.getData2() != 0 && sm.getChannel() != 9)) {
							long dur = -1;
							for(int l = k + 1; l < tracks[j].size(); l++) { // loop through more midievents
								MidiEvent me1 = tracks[j].get(l);
								if(me1.getMessage() instanceof ShortMessage) {
									last = (ShortMessage) me1.getMessage();
									if(last.getCommand() == ShortMessage.NOTE_ON) {
										dur = (me1.getTick() - events[k].getTick());
										break;
									}
								}
							}
							if(dur != 0) {
								MidiEvent toAdd = new MidiEvent(sm, time);
								end.add(toAdd);
								time += dur;
								toAdd = new MidiEvent(last, time);
								end.add(toAdd);
							}
						}
					}
				}
			}
		}
		bases.add(new BaseTrack(end, depth, resolution));
	}
	
	/**
	 * Generates a song based on a given length and depth
	 * 
	 * @param length
	 *            The length of the generated song.
	 * @param depth
	 *            The depth of the generated song.
	 * @return The generated song.
	 */
	public Song generateSong(int length, int depth) {
		Sequence s = null;
		try {
			s = new Sequence(Sequence.PPQ, resolution);
			s.createTrack();
			s.createTrack();
		} catch(InvalidMidiDataException e) {
			e.printStackTrace();
		}
		Track[] tracks = s.getTracks();
		tracks[0] = BaseTrack.generateMetaTrack(tracks[0], 4, 4);
		tracks[1] = bases.get(0).generateTrack(tracks[1], length, depth);
		System.out.println(tracks.length);
		System.out.println(s.getResolution());
		return new Song(s);
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
