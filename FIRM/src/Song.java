import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;

public class Song {

    private Sequence seq;
    private Track[] tracks;

    public Song(int tracks) {
        try {
            seq = new Sequence(Sequence.PPQ, 1, tracks);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }

        this.tracks = new Track[tracks];
        for (int i = 0; i < tracks; i++) {
            this.tracks[i] = seq.createTrack();
        }
    }

    public static MidiEvent createNoteEvent(int com, int key, int vel, long tick) {
        ShortMessage mess = new ShortMessage();
        try {
            mess.setMessage(com, 0, key, vel);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        return new MidiEvent(mess, tick);
    }

    public void write(File out) {
        try {
            MidiSystem.write(seq, MidiSystem.getMidiFileTypes(seq)[0], out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Track[] getTracks() {
        return tracks;
    }

    public void createNote(Track t, int key, int vel, long tick, long dur) {
        t.add(createNoteEvent(ShortMessage.NOTE_ON, key, vel, tick));
        t.add(createNoteEvent(ShortMessage.NOTE_OFF, key, vel, tick + dur));
    }

}
