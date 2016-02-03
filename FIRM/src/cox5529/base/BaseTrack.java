package cox5529.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import cox5529.Measure;
import cox5529.Note;
import cox5529.Pitch;
import cox5529.Song;

/**
 * An object used to store information used when generating songs.
 * 
 * @author Brandon Cox
 * 		
 */
public class BaseTrack {
	
	private ArrayList<Note> notes;
	private ArrayList<Measure> measures;
	private HashMap<ArrayList<Integer>, Pitch> pitchFollow;
	private int instrument;
	private int channel;
	
	/**
	 * Scans a track and generates a BaseTrack object used to generate songs.
	 * 
	 * @param t
	 *            The track to scan.
	 * @param depth
	 *            The depth to store for the track.
	 * @param resolution
	 *            The resolution of the track.
	 */
	public BaseTrack(Track t, int depth, int resolution) {
		notes = new ArrayList<Note>();
		measures = new ArrayList<Measure>();
		pitchFollow = new HashMap<ArrayList<Integer>, Pitch>();
		int noteIndex = 0;
		ArrayList<Integer> curMeasure = new ArrayList<Integer>();
		long length = 0;
		for(int k = 0; k < t.size(); k++) { // loop through midiEvents
			MidiEvent me = t.get(k);
			if(me.getMessage() instanceof ShortMessage) {
				ShortMessage sm = (ShortMessage) me.getMessage();
				// data1 = key
				// data2 = vel
				if(sm.getCommand() == ShortMessage.NOTE_ON && sm.getData2() != 0) {
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
					length += dur;
					curMeasure.add(dur);
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
					if(length >= resolution * 4 && dur != 0) {
						measures.add(new Measure(curMeasure, resolution));
						curMeasure.clear();
						length = 0;
					}
					noteIndex++;
				} else if(sm.getCommand() == ShortMessage.PROGRAM_CHANGE) {
					instrument = sm.getData1();
					channel = sm.getChannel();
				}
			}
		}
	}
	
	private Measure getRandomMeasure() {
		return measures.get((int) (Math.random() * measures.size()));
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
		ArrayList<Integer> follow = notePitchKey;
		int[] measure = getRandomMeasure().generateMeasure(24);
		int measureIndex = 1;
		int notePitch = notePitchKey.get(0);
		int noteDur = measure[0];
		MetaMessage timeSig = new MetaMessage();
		try {
			timeSig.setMessage(MetaMessage.META, new byte[]{0x58, 0x04, 0x04}, 3);
		} catch(InvalidMidiDataException e) {
			e.printStackTrace();
		}
		t.add(Song.createNoteEvent(ShortMessage.PROGRAM_CHANGE, channel, instrument, 0));
		while(dur < length) {
			System.out.println("DURATION: " + dur + "\tNOTE: " + notePitch);
			t.add(Song.createNoteEvent(ShortMessage.NOTE_ON, channel, notePitch, 127, dur));
			t.add(Song.createNoteEvent(ShortMessage.NOTE_ON, channel, notePitch, 0, dur + noteDur));
			double rand = Math.random();
			noteDur = measure[measureIndex];
			measureIndex++;
			if(measureIndex == measure.length) {
				measure = getRandomMeasure().generateMeasure(24);
				measureIndex = 0;
			}
			dur += noteDur;
			Pitch p = pitchFollow.get(notePitchKey);
			HashMap<Integer, Double> pct = p.calcPercentage();
			rand = Math.random();
			Iterator<Entry<Integer, Double>> it = pct.entrySet().iterator();
			while(it.hasNext()) {
				Entry<Integer, Double> pair = (Entry<Integer, Double>) it.next();
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
			}
			
		}
		System.out.println(t.ticks());
		return t;
	}
}
