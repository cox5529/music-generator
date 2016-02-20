package cox5529.storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.ProgramChange;
import com.leff.midi.event.ProgramChange.MidiProgram;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.TimeSignature;

/**
 * Class to store songs.
 * 
 * @author Brandon Cox
 */
public class Song {
	
	private MidiTrack meta;
	private MidiTrack song;
	
	/**
	 * The default resolution of all MIDI files (24).
	 */
	public static final int DEFAULT_MIDI_RESOLUTION = 24;
	
	/**
	 * Creates a song.
	 */
	public Song() {
		meta = new MidiTrack();
		song = new MidiTrack();
	}
	
	/**
	 * Sets the instrument of the given Track object.
	 * 
	 * @param intstrument
	 *            The instrument number to change the track to.
	 */
	public void setInstrument(MidiProgram intstrument) {
		song.insertEvent(new ProgramChange(0, 0, intstrument.programNumber()));
	}
	
	/**
	 * Sets the tempo based on a give BPM count.
	 * 
	 * @param tempo
	 *            The BPM to set the tempo to.
	 */
	public void setTempo(float tempo) {
		Tempo t = new Tempo();
		t.setBpm(tempo);
		meta.insertEvent(t);
	}
	
	/**
	 * Sets the time signature to 4/4 time at the beginning of the song.
	 */
	public void setTimeSignature() {
		meta.insertEvent(new TimeSignature());
	}
	
	/**
	 * Sets the time signature to num/den time at the beginning of the song.
	 * 
	 * @param num
	 *            The numerator of the time signature.
	 * @param den
	 *            The denominator of the time signature.
	 */
	public void setTimeSignature(int num, int den) {
		meta.insertEvent(new TimeSignature(0, 0, num, den, TimeSignature.DEFAULT_METER, TimeSignature.DEFAULT_DIVISION));
	}
	
	/**
	 * Adds a note to the song.
	 * 
	 * @param channel
	 *            The channel to play the note on.
	 * @param pitch
	 *            The pitch to play.
	 * @param vel
	 *            The velocity of the note.
	 * @param tick
	 *            The tick to start the note at.
	 * @param dur
	 *            The duration of the note.
	 */
	public void playNote(int channel, int pitch, int vel, long tick, long dur) {
		song.insertNote(channel, pitch, vel, tick, dur);
	}
	
	/**
	 * Writes the song to the specified file.
	 * 
	 * @param out
	 *            File to write the song to. File must end in .mid.
	 * @param res
	 *            The resolution of the MIDI file.
	 */
	public void write(File out, int res) {
		ArrayList<MidiTrack> tracks = new ArrayList<MidiTrack>();
		tracks.add(meta);
		tracks.add(song);
		MidiFile midi = new MidiFile(res, tracks);
		try {
			midi.writeToFile(out);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes the song to the specified file using Song.DEFAULT_MIDI_RESOLUTION as the resolution.
	 * 
	 * @param out
	 *            File to write the song to. File must end in .mid.
	 */
	public void write(File out) {
		ArrayList<MidiTrack> tracks = new ArrayList<MidiTrack>();
		tracks.add(meta);
		tracks.add(song);
		MidiFile midi = new MidiFile(Song.DEFAULT_MIDI_RESOLUTION, tracks);
		try {
			midi.writeToFile(out);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Generates an array of 7 notes (an octave) in a minor key.
	 * 
	 * @param start
	 *            The starting note of the octave.
	 * @return An array of 7 notes in key.
	 */
	public static int[] generateMinorOctave(int start) {
		int[] re = new int[7];
		re[0] = start;
		re[1] = re[0] + 2;
		re[2] = re[1] + 1;
		re[3] = re[2] + 2;
		re[4] = re[3] + 2;
		re[5] = re[4] + 1;
		re[6] = re[5] + 2;
		return re;
	}
	
	/**
	 * Generates an array of 7 notes (an octave) in a major key.
	 * 
	 * @param start
	 *            The starting note of the octave.
	 * @return An array of 7 notes in key.
	 */
	public static int[] generateMajorOctave(int start) {
		int[] re = new int[7];
		re[0] = start;
		re[1] = re[0] + 2;
		re[2] = re[1] + 2;
		re[3] = re[2] + 1;
		re[4] = re[3] + 2;
		re[5] = re[4] + 2;
		re[6] = re[5] + 2;
		return re;
	}
	
	/**
	 * Generates an array of all 12 notes up to an octave above start.
	 * 
	 * @param start
	 *            The starting note of the octave.
	 * @return An array of 12 notes in key.
	 */
	public static int[] generateChromaticOctave(int start) {
		int[] re = new int[12];
		for(int i = 0; i < re.length; i++) {
			re[i] = start + i;
		}
		return re;
	}
	
	/**
	 * Determines if a note is an accidental.
	 * 
	 * @param pitch
	 *            The pitch to check.
	 * @param acc
	 *            The amount of flats (negative number) or sharps (positive number) in the key signature.
	 * @return True if the note is an accidental, false if it is not.
	 */
	public static boolean isAccidental(int pitch, int acc) {
		int note = pitch % 12;
		if(acc == 1 && note != 6)
			return true;
		if(acc == 2 && (note != 6 && note != 1))
			return true;
		if(acc == 3 && (note != 6 && note != 1 && note != 8))
			return true;
		if(acc == 4 && (note != 6 && note != 1 && note != 8 && note != 3))
			return true;
		if(acc == 5 && (note != 6 && note != 1 && note != 8 && note != 3 && note != 10))
			return true;
		if(acc == 6 && (note != 6 && note != 1 && note != 8 && note != 3 && note != 10 && note != 5)) {
			return true;
		}
		if(acc == -1 && note != 10)
			return true;
		if(acc == -2 && (note != 10 && note != 3))
			return true;
		if(acc == -3 && (note != 10 && note != 3 && note != 8))
			return true;
		if(acc == -4 && (note != 10 && note != 3 && note != 8 && note != 1))
			return true;
		if(acc == -5 && (note != 10 && note != 3 && note != 8 && note != 1 && note != 6))
			return true;
		if(acc == -6 && (note != 10 && note != 3 && note != 8 && note != 1 && note != 6 && note != 11))
			return true;
		return false;
	}
	
	/**
	 * Transposes the given note to C Major.
	 * 
	 * @param pitch
	 *            The pitch to transpose.
	 * @param acc
	 *            The amount of flats (negative number) or sharps (positive number) in the key signature.
	 * @return The transposed note.
	 */
	public static int transpose(int pitch, int acc) {
		if(isAccidental(pitch, acc))
			return pitch;
		int oct = pitch / 12;
		// Flat order = BEADGCF
		// Sharp order = FCGDAEB
		// see if the note needs to be transposed at all
		int[] key = generateMajorOctave(oct * 12); // generate major octave based on C in the given octave
		for(int i = 0; i < key.length; i++) {
			if(key[i] == pitch) {
				
				return pitch; // if the note is in key, return the note
			}
		}
		if(acc < 0) { // if there are sharps transpose down
			return pitch - 1;
		} else {
			return pitch + 1;
		}
	}
	
	/**
	 * Transposes a note from an old key to a new key.
	 * 
	 * @param pitch
	 *            The pitch to transpose.
	 * @param oldAcc
	 *            The amount of flats (negative number) or sharps (positive number) in the old key signature.
	 * @param acc
	 *            The amount of flats (negative number) or sharps (positive number) in the new key signature.
	 * @return The transposed pitch.
	 */
	public static int transpose(int pitch, int oldAcc, int acc) {
		pitch = transpose(pitch, oldAcc);
		int note = pitch % 12;
		int re = pitch;
		if(acc == 1 && note == 5)
			re = pitch + 1;
		else if(acc == 2 && (note == 5 || note == 0))
			re = pitch + 1;
		else if(acc == 3 && (note == 5 || note == 0 || note == 7))
			re = pitch + 1;
		else if(acc == 4 && (note == 5 || note == 0 || note == 7 && note == 2))
			re = pitch + 1;
		else if(acc == 5 && (note == 5 || note == 0 || note == 7 && note == 2 || note == 9))
			re = pitch + 1;
		else if(acc == 6 && (note == 5 || note == 0 || note == 7 || note == 2 || note == 9 || note == 4))
			re = pitch + 1;
		else if(acc == -1 && note == 11)
			re = pitch - 1;
		else if(acc == -2 && (note == 11 || note == 4))
			re = pitch - 1;
		else if(acc == -3 && (note == 11 || note == 4 || note == 9))
			re = pitch - 1;
		else if(acc == -4 && (note == 11 || note == 4 || note == 9 || note == 2))
			re = pitch - 1;
		else if(acc == -5 && (note == 11 || note == 4 || note == 9 || note == 2 || note == 7))
			re = pitch - 1;
		else if(acc == -6 && (note == 11 || note == 4 || note == 9 || note == 2 || note == 7 || note == 0))
			re = pitch - 1;
		System.out.println(pitch + ": " + re);
		return re;
	}
	
	/**
	 * Gets the scale degree of a given note.
	 * 
	 * @param note
	 *            The note to get the scale degree of.
	 * @return The scale degree of the note.
	 */
	public static int getScaleDegree(int note) {
		return (note) % 12;
	}
	
	/**
	 * Gets the beat within the measure a note occurs on.
	 * 
	 * @param resolution
	 *            The resolution of the input note.
	 * @param tickInMeasure
	 *            The tick within the measure the note occurs on.
	 * @return The beat within the measure * 4.
	 */
	public static int getBeat(long tickInMeasure, long resolution) {
		return (int) ((tickInMeasure * 4) / resolution);
	}
	
}
