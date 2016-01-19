import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;

/**
 * Class to store songs.
 * 
 * @author Brandon Cox
 * 		
 */
public class Song {
	
	private Sequence seq;
	private Track[] tracks;
	
	/**
	 * Creates a song with the given number of tracks.
	 * 
	 * @param speed The speed of the song. 4 is relatively fast.
	 * @param tracks
	 *            Number of tracks in the song
	 */
	public Song(int tracks, int speed) {
		try {
			seq = new Sequence(Sequence.PPQ, speed, tracks);
		} catch(InvalidMidiDataException e) {
			e.printStackTrace();
		}
		
		this.tracks = new Track[tracks];
		for(int i = 0; i < tracks; i++) {
			this.tracks[i] = seq.createTrack();
		}
	}
	
	private MidiEvent createNoteEvent(int com, int key, int vel, long tick) {
		ShortMessage mess = new ShortMessage();
		try {
			mess.setMessage(com, 0, key, vel);
		} catch(InvalidMidiDataException e) {
			e.printStackTrace();
		}
		return new MidiEvent(mess, tick);
	}
	
	/**
	 * Writes the song to the specified file.
	 * 
	 * @param out
	 *            File to write the song to. File must end in .mid.
	 */
	public void write(File out) {
		try {
			MidiSystem.write(seq, MidiSystem.getMidiFileTypes(seq)[0], out);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the tracks in the song.
	 * 
	 * @return The tracks in the song.
	 */
	public Track[] getTracks() {
		return tracks;
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
	 * Adds a note to the song.
	 * 
	 * @param t
	 *            The track to add the note to.
	 * @param key
	 *            The note to play.
	 * @param vel
	 *            The volume of the note from 1 to 127.
	 * @param tick
	 *            The time tick at which to play the note.
	 * @param dur
	 *            The duration of the note.
	 */
	public void createNote(Track t, int key, int vel, long tick, long dur) {
		t.add(createNoteEvent(ShortMessage.NOTE_ON, key, vel, tick));
		t.add(createNoteEvent(ShortMessage.NOTE_OFF, key, vel, tick + dur));
	}
	
}
