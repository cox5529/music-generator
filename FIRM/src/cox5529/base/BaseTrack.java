package cox5529.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import cox5529.Note;
import cox5529.Pitch;
import cox5529.Song;

public class BaseTrack {
	
	private ArrayList<Note> notes;
	private HashMap<ArrayList<Integer>, Pitch> pitchFollow;
	private int instrument;
	private int channel;
	private int fixedVel;
	
	public BaseTrack(Track t, int depth) {
		notes = new ArrayList<Note>();
		pitchFollow = new HashMap<ArrayList<Integer>, Pitch>();
		int noteIndex = 0;
		int sumOfVel = 0;
		for(int k = 0; k < t.size(); k++) { // loop through midiEvents
			MidiEvent me = t.get(k);
			if(me.getMessage() instanceof ShortMessage) {
				ShortMessage sm = (ShortMessage) me.getMessage();
				// data1 = key
				// data2 = vel
				if(sm.getCommand() == ShortMessage.NOTE_ON && sm.getData2() != 0) {
					sumOfVel += sm.getData2();
					int dur = -1;
					for(int l = k + 1; l < t.size(); l++) { // loop through more midievents
						MidiEvent me1 = t.get(l);
						if(me1.getMessage() instanceof ShortMessage) {
							ShortMessage end = (ShortMessage) me1.getMessage();
							if(end.getCommand() == ShortMessage.NOTE_ON) {
								dur = (int) (me1.getTick() - me.getTick());
								break;
							}
						}
					}
					int pitch = sm.getData1();
					notes.add(new Note(pitch, dur));
					if(noteIndex > depth - 1) {
						ArrayList<Integer> follow = new ArrayList<Integer>();
						for(int i = 0; i < depth; i++) {
							follow.add(notes.get(noteIndex - 1 - i).getPitch());
						}
						
						if(pitchFollow.containsKey(follow)) {
							Pitch p = pitchFollow.get(follow);
							p.addFollow(pitch);
							pitchFollow.put(follow, p);
						} else {
							pitchFollow.put(follow, new Pitch(follow, pitch));
						}
						
					}
					noteIndex++;
				} else if(sm.getCommand() == ShortMessage.PROGRAM_CHANGE) {
					instrument = sm.getData1();
					channel = sm.getChannel();
				}
			}
		}
		if(noteIndex != 0)
			fixedVel = 127;// TODO fixedVel = sumOfVel / noteIndex;
	}
	
	/**
	 * Generates a track based on the given parameters.
	 * 
	 * @param length
	 *            The length of the track in ticks
	 * @param speed
	 *            The speed of the track.
	 * @return The track that was created.
	 */
	public Track generateTrack(Track t, long length, int depth) {
		if(pitchFollow.keySet().size() == 0 || pitchFollow.keySet().size() == 1)
			return null;
		long dur = 0;
		int index = 0;
		ArrayList<Integer> notePitchKey = (ArrayList<Integer>) (pitchFollow.keySet().toArray()[0]);
		System.out.println();
		ArrayList<Integer> follow = notePitchKey;
		int notePitch = notePitchKey.get(0);
		int noteDur = 24;
		t.add(Song.createNoteEvent(ShortMessage.PROGRAM_CHANGE, channel, instrument, 0));
		while(dur < length) {
			System.out.println("DURATION: " + dur + "\tNOTE: " + notePitch);
			t.add(Song.createNoteEvent(ShortMessage.NOTE_ON, channel, notePitch, fixedVel, dur));
			t.add(Song.createNoteEvent(ShortMessage.NOTE_OFF, channel, notePitch, fixedVel, dur + noteDur));
			HashMap<Integer, Double> pct;
			double rand = Math.random();
			dur += noteDur;
			Pitch p = pitchFollow.get(notePitchKey);
			// if(p != null) {
			pct = p.calcPercentage();
			// }
			rand = Math.random();
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
					
			index++;
			if(index >= depth) {
				// set latest one at 0
				// shift everything up
				follow.add(0, notePitch);
				follow.remove(depth);
				follow.set(depth - 1, notePitch);
				notePitchKey = follow;
				for(int i = 0; i < notePitchKey.size(); i++) {
					System.out.print(notePitchKey.get(i) + " ");
				}
				System.out.println();
			}
			
		}
		System.out.println(t.ticks());
		return t;
	}
}
