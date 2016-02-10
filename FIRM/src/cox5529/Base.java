package cox5529;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;
import com.leff.midi.event.meta.KeySignature;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.util.MidiProcessor;

import cox5529.listeners.KeySignatureListener;
import cox5529.listeners.NoteListener;
import cox5529.listeners.NoteOffListener;
import cox5529.listeners.TempoListener;
import cox5529.storage.Measure;
import cox5529.storage.Pitch;
import cox5529.storage.Song;

/**
 * Class used to store the Base songs.
 * 
 * @author Brandon Cox
 */
public class Base implements Serializable {
	
	private static final long serialVersionUID = 3954399902682584882L;
	private ArrayList<Measure> measures;
	private HashMap<ArrayList<Integer>, Pitch> pitchFollow;
	private ArrayList<Float> tempo;
	private int res;
	
	/**
	 * Creates a list of every note in the given songs. Creates the basis of the algorithm to compose songs.
	 * 
	 * @param depth
	 *            The depth to scan when generating the song.
	 * @param songs
	 *            The songs to create the base from.
	 */
	public Base(int depth, File... songs) { // needs to import all songs into the
											// bases array.
		pitchFollow = new HashMap<ArrayList<Integer>, Pitch>();
		tempo = new ArrayList<Float>();
		ArrayList<Long> endings = new ArrayList<Long>();
		ArrayList<Long> starts = new ArrayList<Long>();
		measures = new ArrayList<Measure>();
		
		for(int i = 0; i < songs.length; i++) {
			MidiFile mFile = null;
			MidiProcessor proc = null;
			try {
				mFile = new MidiFile(songs[i]);
				res = mFile.getResolution();
				ArrayList<MidiTrack> tracks = new ArrayList<MidiTrack>();
				tracks.add(mFile.getTracks().get(0));
				MidiFile read = new MidiFile(mFile.getResolution(), tracks);
				proc = new MidiProcessor(read);
			} catch(FileNotFoundException e) {
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			}
			KeySignatureListener ks = new KeySignatureListener();
			
			proc.registerEventListener(ks, KeySignature.class);
			
			proc.start();
			while(proc.isRunning()) {
				try {
					Thread.sleep(1);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
				if(ks.isInit())
					break;
			}
			proc.stop();
			try {
				Thread.sleep(500);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			NoteListener nl = new NoteListener(depth, res * 4, mFile.getLengthInTicks(), ks.getFlats());
			NoteOffListener no = new NoteOffListener(mFile.getLengthInTicks());
			TempoListener tl = new TempoListener();
			
			proc.unregisterAllEventListeners();
			proc.registerEventListener(nl, NoteOn.class);
			proc.registerEventListener(tl, Tempo.class);
			proc.registerEventListener(no, NoteOff.class);
			
			proc.start();
			while(proc.isRunning()) {
				try {
					Thread.sleep(1);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			proc.stop();
			
			// combine pitchFollows
			HashMap<ArrayList<Integer>, Pitch> newFollow = nl.getPitchFollow();
			Iterator<Entry<ArrayList<Integer>, Pitch>> it = newFollow.entrySet().iterator();
			while(it.hasNext()) {
				Entry<ArrayList<Integer>, Pitch> pair = (Entry<ArrayList<Integer>, Pitch>) it.next();
				ArrayList<Integer> key = pair.getKey();
				Pitch p = pair.getValue();
				if(pitchFollow.containsKey(key)) {
					Pitch p1 = pitchFollow.get(key);
					p1.combine(p.getFollow());
					pitchFollow.put(key, p1);
				} else {
					pitchFollow.put(key, p);
				}
			}
			
			tempo.addAll(tl.getBpm());
			starts.addAll(nl.getStarts());
			endings.addAll(no.getEndings());
			endings.addAll(nl.getEndings());
		}
		
		long length = 0;
		ArrayList<Long> cur = new ArrayList<Long>();
		for(int i = 0; i < starts.size() - 1; i++) {
			long dur = 0;
			
			// notes
			dur = (endings.get(i) - starts.get(i));
			// System.out.println(dur + ":\t" + starts.get(i) + "\t" + endings.get(i) + "\t" + length);
			cur.add(dur);
			length += Math.abs(dur);
			if(length >= res * 4) { // end of measure
				length = 0;
				measures.add(new Measure(cur));
				cur = new ArrayList<Long>();
			}
			
			if(endings.get(i) != starts.get(i + 1)) {
				// rests
				// use a negative num for rests
				dur = -1 * (starts.get(i + 1) - endings.get(i));
				// System.out.println(dur + ":\t" + endings.get(i) + "\t" + starts.get(i + 1) + "\t" + length);
				cur.add(dur);
				length += Math.abs(dur);
				if(length >= res * 4) { // end of measure
					length = 0;
					measures.add(new Measure(cur));
					cur = new ArrayList<Long>();
				}
				
			}
		}
		
		/*
		 * if(!cur.isEmpty()) { cur.add(res * 4 - length); measures.add(new Measure(cur)); }
		 */
	}
	
	/**
	 * Gets the resolution of the Base file.
	 * 
	 * @return The resolution.
	 */
	public int getRes() {
		return res;
	}
	
	private Measure getRandomMeasure() {
		return measures.get((int) (Math.random() * measures.size()));
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
	public Song generateSong(int length, int depth) throws NullPointerException {
		Song s = new Song();
		if(pitchFollow.keySet().size() == 0 || pitchFollow.keySet().size() == 1)
			return null;
		long dur = 0;
		int index = 0;
		ArrayList<Integer> notePitchKey = (ArrayList<Integer>) (pitchFollow.keySet().toArray()[(int) (Math.random() * pitchFollow.size())]);
		ArrayList<Integer> follow = notePitchKey;
		long[] measure = getRandomMeasure().generateMeasure();
		int measureIndex = 1;
		int notePitch = notePitchKey.get(0);
		long noteDur = measure[0];
		s.setTimeSignature();
		s.setTempo(tempo.get(0));
		while(dur < length) {
			if(noteDur > 0) {
				System.out.println("DURATION: " + dur + "\tNOTE: " + notePitch + "\tNOTEDUR: " + noteDur);
				s.playNote(0, notePitch, 127, dur, noteDur);
			} else {
				System.out.println("REST AT:  " + dur + "\t\t\tNOTEDUR: " + noteDur);
			}
			dur += Math.abs(noteDur);
			double rand = Math.random();
			noteDur = measure[measureIndex];
			measureIndex++;
			if(measureIndex == measure.length) {
				measure = getRandomMeasure().generateMeasure();
				measureIndex = 0;
			}
			if(noteDur > 0) {
				Pitch p = pitchFollow.get(notePitchKey);
				if(p == null)
					throw new NullPointerException("Your depth is too high for the given information.");
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
					follow.add(notePitch);
					follow.remove(0);
					notePitchKey = follow;
				}
			}
		}
		return s;
	}
	
	/**
	 * Reads in a serialized base from a given file.
	 * 
	 * @param f
	 *            The file to read from.
	 * @return The Base object read.
	 */
	public static Base readBase(File f) {
		Base re = null;
		try {
			FileInputStream fin = new FileInputStream(f);
			ObjectInputStream in = new ObjectInputStream(fin);
			re = (Base) in.readObject();
			in.close();
			fin.close();
			return re;
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return re;
	}
	
	/**
	 * Serializes this base to a file.
	 * 
	 * @param f
	 *            The file to serialize to.
	 */
	public void saveBase(File f) {
		try {
			FileOutputStream os = new FileOutputStream(f);
			ObjectOutputStream out = new ObjectOutputStream(os);
			out.writeObject(this);
			out.close();
			os.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}