package cox5529.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import cox5529.Main;
import cox5529.Note;
import cox5529.Pitch;
import cox5529.Song;

public class BaseTrack {
	
	private ArrayList<Note> notes;
	private HashMap<Integer, Pitch> pitchFollow;
	private int instrument;
	private int channel;
	private int fixedVel;
	
	public BaseTrack(Track t) {
		notes = new ArrayList<Note>();
		pitchFollow = new HashMap<Integer, Pitch>();
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
				} else if(sm.getCommand() == ShortMessage.PROGRAM_CHANGE) {
					instrument = sm.getData1();
					channel = sm.getChannel();
					System.out.println(instrument);
					System.out.println(channel);
				}
			}
		}
		if(noteIndex != 0)
			fixedVel = sumOfVel / noteIndex;
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
	public Track generateTrack(Track t, long length) {
		if(pitchFollow.keySet().size() == 0)
			return null;
		long dur = 0;
		int notePitch = (int) pitchFollow.keySet().toArray()[0];
		int noteDur;
		t.add(Song.createNoteEvent(ShortMessage.PROGRAM_CHANGE, channel, instrument, 0));
		while(dur < length) {
			Random r = new Random(System.nanoTime());
			int random = r.nextInt(100);
			if(random < 50) {
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
			t.add(Song.createNoteEvent(ShortMessage.NOTE_ON, channel, notePitch, fixedVel, dur));
			t.add(Song.createNoteEvent(ShortMessage.NOTE_OFF, channel, notePitch, fixedVel, dur + noteDur));
			dur += noteDur;
			Pitch p = pitchFollow.get(notePitch);
			HashMap<Integer, Double> pct = p.calcPercentage();
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
		return t;
	}
}
