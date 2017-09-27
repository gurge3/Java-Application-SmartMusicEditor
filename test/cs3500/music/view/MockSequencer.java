package cs3500.music.view;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;

/**
 * This class represents a mock sequencer.
 */
public class MockSequencer implements Sequencer {

  Sequence sequence;

  StringBuilder sb;

  /**
   * The default constructor for the MockSequencer class.
   */
  public MockSequencer(StringBuilder sb) {
    this.sb = sb;
  }

  @Override
  public void setSequence(Sequence sequence) throws InvalidMidiDataException {
    this.sequence = sequence;
    Track[] tracks = sequence.getTracks();
    for (int i = 0; i < tracks.length; i++) {
      Track track = tracks[i];
      for (int j = 0; j < track.size(); j++) {
        if (track.get(j).getMessage() instanceof ShortMessage) {
          ShortMessage msg = (ShortMessage) track.get(j).getMessage();
          if (msg.getCommand() == 144) {
            sb.append("Command: " + msg.getCommand() + " Note level: "
                    + msg.getData1() + " volume: " + msg.getData2() + "\n");
          }
          if (msg.getCommand() == 128) {
            sb.append("Command: " + msg.getCommand() + " Note level: "
                    + msg.getData1() + " volume: " + msg.getData2() + "\n");
          }
          if (msg.getCommand() == 192) {
            sb.append("Command: " + msg.getCommand() + " Instrument: " + msg.getData1() + "\n");
          }
        }
      }
    }
  }

  @Override
  public void setSequence(InputStream stream) throws IOException, InvalidMidiDataException {
    // Do nothing.
  }

  @Override
  public Sequence getSequence() {
    return sequence;
  }

  @Override
  public void start() {
    // Do nothing.
  }


  @Override
  public void stop() {
    // Do nothing.
  }


  @Override
  public boolean isRunning() {
    return false;
  }


  @Override
  public void startRecording() {
    // Do nothing.
  }

  @Override
  public void stopRecording() {
    // Do nothing.
  }

  @Override
  public boolean isRecording() {
    return false;
  }

  @Override
  public void recordEnable(Track track, int channel) {
    // Do nothing.
  }


  @Override
  public void recordDisable(Track track) {
    // Do nothing.
  }


  @Override
  public float getTempoInBPM() {
    return 0;
  }


  @Override
  public void setTempoInBPM(float bpm) {
    // Do nothing.
  }


  @Override
  public float getTempoInMPQ() {
    return 0;
  }


  @Override
  public void setTempoInMPQ(float mpq) {
    // Do nothing.
  }

  @Override
  public float getTempoFactor() {
    return 0;
  }

  @Override
  public void setTempoFactor(float factor) {
    // Do nothing.
  }

  @Override
  public long getTickLength() {
    return 0;
  }


  @Override
  public long getTickPosition() {
    return 0;
  }


  @Override
  public void setTickPosition(long tick) {
    // Do nothing.
  }


  @Override
  public long getMicrosecondLength() {
    return 0;
  }


  @Override
  public Info getDeviceInfo() {
    return null;
  }

  @Override
  public void open() throws MidiUnavailableException {
    // Do nothing.
  }


  @Override
  public void close() {
    // Do nothing.
  }

  @Override
  public boolean isOpen() {
    return false;
  }


  @Override
  public long getMicrosecondPosition() {
    return 0;
  }

  @Override
  public void setMicrosecondPosition(long microseconds) {
    // Do nothing.
  }

  @Override
  public SyncMode getMasterSyncMode() {
    return null;
  }

  @Override
  public void setMasterSyncMode(SyncMode sync) {
    // Do nothing.
  }

  @Override
  public SyncMode[] getMasterSyncModes() {
    return new SyncMode[0];
  }

  @Override
  public SyncMode getSlaveSyncMode() {
    return null;
  }

  @Override
  public void setSlaveSyncMode(SyncMode sync) {
    // Do nothing.
  }

  @Override
  public SyncMode[] getSlaveSyncModes() {
    return new SyncMode[0];
  }

  @Override
  public void setTrackMute(int track, boolean mute) {
    // Do nothing.
  }

  @Override
  public boolean getTrackMute(int track) {
    return false;
  }

  @Override
  public void setTrackSolo(int track, boolean solo) {
    // Do nothing.
  }

  @Override
  public boolean getTrackSolo(int track) {
    return false;
  }

  @Override
  public boolean addMetaEventListener(MetaEventListener listener) {
    return false;
  }

  @Override
  public void removeMetaEventListener(MetaEventListener listener) {
    // Do nothing.
  }

  @Override
  public int[] addControllerEventListener(ControllerEventListener listener, int[] controllers) {
    return new int[0];
  }

  @Override
  public int[] removeControllerEventListener(ControllerEventListener listener, int[] controllers) {
    return new int[0];
  }

  @Override
  public long getLoopStartPoint() {
    return 0;
  }

  @Override
  public void setLoopStartPoint(long tick) {
    // Do nothing.
  }

  @Override
  public long getLoopEndPoint() {
    return 0;
  }

  @Override
  public void setLoopEndPoint(long tick) {
    // Do nothing.
  }

  @Override
  public int getLoopCount() {
    return 0;
  }

  @Override
  public void setLoopCount(int count) {
    // Do nothing.
  }

  @Override
  public int getMaxReceivers() {
    return 0;
  }


  @Override
  public int getMaxTransmitters() {
    return 0;
  }

  @Override
  public Receiver getReceiver() throws MidiUnavailableException {
    return null;
  }

  @Override
  public List<Receiver> getReceivers() {
    return null;
  }

  @Override
  public Transmitter getTransmitter() throws MidiUnavailableException {
    return null;
  }

  @Override
  public List<Transmitter> getTransmitters() {
    return null;
  }

}