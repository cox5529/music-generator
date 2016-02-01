package cox5529.base;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
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
		Sequence s;
		try {
			s = new Sequence(Sequence.PPQ, songs[0].getSequence().getResolution());
		} catch(InvalidMidiDataException e) {
			e.printStackTrace();
		}
		resolution = songs[0].getSequence().getResolution();
		Track[] t = songs[0].getSequence().getTracks(); // combine all tracks before generating
		for(int j = 0; j < t.length; j++) { // loop through tracks
			bases.add(new BaseTrack(t[j], depth));
		}
	}
	
	public Song generateSong(int length, int depth) {
		Song s = new Song(bases.size(), resolution);
		Track[] tracks = s.getTracks();
		for(int i = 0; i < tracks.length; i++) {
			tracks[i] = bases.get(i).generateTrack(tracks[i], length, depth);
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
