package cox5529.listeners;

import java.util.ArrayList;
import java.util.HashMap;

import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;
import com.leff.midi.util.MidiEventListener;

import cox5529.storage.Pitch;
import cox5529.storage.Song;

/**
 * Listener used to listen for NoteOn events.
 * 
 * @author Brandon Cox
 * 		
 */
public class NoteListener extends Listener implements MidiEventListener {
	
	private ArrayList<Long> starts;
	private int depth;
	private ArrayList<Integer> follow;
	private ArrayList<Long> endings;
	private HashMap<ArrayList<Integer>, Pitch> pitchFollow;
	private long size;
	private int acc;
	
	/**
	 * Constructs a NoteListener object.
	 * 
	 * @param depth
	 *            The depth to scan for pitches.
	 * @param ticksPerMeasure
	 *            The amount of ticks per measure.
	 * @param size
	 *            The length in ticks of the song.
	 * @param acc
	 *            The amount of flats (negative number) or sharps (positive number) in the key signature.
	 */
	public NoteListener(int depth, int ticksPerMeasure, long size, int acc) {
		super(size);
		pitchFollow = new HashMap<ArrayList<Integer>, Pitch>();
		endings = new ArrayList<Long>();
		starts = new ArrayList<Long>();
		this.depth = depth;
		this.size = size;
		this.acc = acc;
	}
	
	/**
	 * Gets the tick position of all NoteOn events.
	 * 
	 * @return An ArrayList of NoteOn event tick positions.
	 */
	public ArrayList<Long> getStarts() {
		return starts;
	}
	
	/**
	 * Gets the HashMap of pitches, which contains what pitches most commonly follow every set of pitches.
	 * 
	 * @return The HashMap of pitches.
	 */
	public HashMap<ArrayList<Integer>, Pitch> getPitchFollow() {
		return pitchFollow;
	}
	
	@Override
	public void onEvent(MidiEvent event, long ms) {
		if(event instanceof NoteOn) {
			NoteOn no = (NoteOn) event;
			if(no.getVelocity() != 0) {
				// pitch stuff
				int pitch = Song.transpose(no.getNoteValue(), acc);
				if(follow == null)
					follow = new ArrayList<Integer>();
				else if(follow.size() == depth) {
					if(pitchFollow.containsKey(follow)) {
						Pitch p = pitchFollow.get(follow);
						p.addFollow(pitch);
						pitchFollow.put(follow, p);
					} else {
						pitchFollow.put(follow, new Pitch(follow, pitch));
					}
					int[] old = new int[depth - 1];
					for(int i = 1; i < depth; i++) {
						old[i - 1] = follow.get(i);
					}
					follow = new ArrayList<Integer>();
					for(int i = 0; i < old.length; i++) {
						follow.add(old[i]);
					}
				}
				follow.add(pitch);
				starts.add(no.getTick());
				System.out.println(event.getTick() + ": processed note_on\tPCT COMPLETE: " + (((0.0 + no.getTick()) / size) * 100));
			} else {
				endings.add(event.getTick());
			}
		} else if(event instanceof NoteOff) {
			System.out.println("Note off");
		}
	}
	
	/**
	 * Gets the tick position of all NoteOn events with a velocity of 0.
	 * 
	 * @return An ArrayList of NoteOn events with a velocity of 0 tick positions.
	 */
	public ArrayList<Long> getEndings() {
		return endings;
	}
	
	@Override
	public void onStart(boolean fromBeginning) {
		System.out.println("Reading for note beginnings.");
	}
	
	@Override
	public void onStop(boolean finished) {
		System.out.println("Finished reading for note beginnings.");
	}
	
}
