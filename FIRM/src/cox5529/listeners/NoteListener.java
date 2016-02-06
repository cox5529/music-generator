package cox5529.listeners;

import java.util.ArrayList;
import java.util.HashMap;

import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;
import com.leff.midi.util.MidiEventListener;

import cox5529.Pitch;

public class NoteListener implements MidiEventListener {
	
	private ArrayList<Long> starts;
	private int depth;
	private ArrayList<Integer> follow;
	private ArrayList<Long> endings;
	private HashMap<ArrayList<Integer>, Pitch> pitchFollow;
	
	public NoteListener(int depth, int ticksPerMeasure) {
		pitchFollow = new HashMap<ArrayList<Integer>, Pitch>();
		endings = new ArrayList<Long>();
		starts = new ArrayList<Long>();
		this.depth = depth;
	}
	
	public ArrayList<Long> getStarts() {
		return starts;
	}
	
	public HashMap<ArrayList<Integer>, Pitch> getPitchFollow() {
		return pitchFollow;
	}
	
	@Override
	public void onEvent(MidiEvent event, long ms) {
		if(event instanceof NoteOn) {
			NoteOn no = (NoteOn) event;
			if(no.getVelocity() != 0) {
				// pitch stuff
				int pitch = no.getNoteValue();
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
					follow.clear();
					for(int i = 0; i < old.length; i++) {
						follow.add(old[i]);
					}
				}
				follow.add(pitch);
				starts.add(no.getTick());
				System.out.println(no.getTick() + ": procesed note_on");
			}else{
				endings.add(event.getTick());
			}
		} else if(event instanceof NoteOff) {
			System.out.println("Note off");
		}
	}
	
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
