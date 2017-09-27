package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

import cs3500.music.controller.NotesContainer;
import cs3500.music.model.INote;

/**
 * This class represents a midi view of this music creator.
 * A midi is to convert and play the music in audio.
 * It has a sythesizer to syhehsis the music.
 * It has a notes container to get the information from the model.
 * It has sequencer and sequnce to send the messenge to the midi.
 * It has a current beat to keep tracking the beat.
 */
public class MidiView extends Thread implements IView {

  // fields of the class
  private Synthesizer synth;

  private NotesContainer container;

  private int currentBeat;

  private Sequence sequence;

  private Sequencer sequencer;

  /**
   * The default constructor for MidiView class.
   */
  public MidiView() {
    try {
      this.synth = MidiSystem.getSynthesizer();
      synth.open();
      this.synth.open();
      sequencer = MidiSystem.getSequencer();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }

  /**
   * The second constructor which takes in a sequencer.(for testing)
   *
   * @param sequencer The sequencer takes in.
   */
  public MidiView(Sequencer sequencer) {
    try {
      this.synth = MidiSystem.getSynthesizer();
      synth.open();
      this.synth.open();
      this.sequencer = sequencer;
      sequence = sequencer.getSequence();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }


  /**
   * This method adds note to the current track.
   *
   * @param sequence  The sequence of this MIDI system.
   */
  protected void addTrack(Sequence sequence)
          throws InvalidMidiDataException {
    Track track = sequence.createTrack();
    for (int i = 0; i <= container.getTheLastStartBeat(); i++) {
      if (container.getNotesArray(i) != null) {
        for (INote note : container.getNotesArray(i)) {
          ShortMessage on = new ShortMessage();
          ShortMessage instrumentMessage = new ShortMessage();
          on.setMessage(ShortMessage.NOTE_ON, note.getNoteLevel(), note.getVolume());
          // Configuring the instrument..
          instrumentMessage.setMessage(ShortMessage.PROGRAM_CHANGE,
                  0, note.getInstrument(), 0);
          ShortMessage off = new ShortMessage();
          off.setMessage(ShortMessage.NOTE_OFF, note.getNoteLevel(), note.getVolume());
          MidiEvent noteOn = new MidiEvent(on, note.getStartTime());
          MidiEvent setInstrument = new MidiEvent(instrumentMessage, note.getStartTime());
          MidiEvent noteOff = new MidiEvent(off, note.getStartTime() + note.getBeat());
          track.add(noteOn);
          track.add(setInstrument);
          track.add(noteOff);
        }
      }
    }
  }

  @Override
  public void showNote() {
    try {
      sequence = new Sequence(Sequence.PPQ, 1);
      addTrack(sequence);
      sequencer.open();
      sequencer.setSequence(sequence);
      sequencer.setTempoInBPM(60000000 / container.getTempo());
    } catch (InvalidMidiDataException | MidiUnavailableException e) {
      e.printStackTrace();
    }
  }

  @Override
  public int getCurrentBeat() {
    int beat = currentBeat;
    return beat;
  }

  @Override
  public void controlMusic() {
    if (sequencer.isRunning()) {
      sequencer.stop();
    } else {
      float tempo = sequencer.getTempoInBPM();
      sequencer.start();
      sequencer.setTempoInBPM(tempo);
    }
  }

  @Override
  public void setCurrentBeat(int beat) {
    this.currentBeat = beat;
    sequencer.setTickPosition(currentBeat);
    sequencer.setTempoInBPM(60000000 / container.getTempo());

  }


  @Override
  public void setContainer(NotesContainer container) {
    this.container = container;
    showNote();
  }


  @Override
  public void setKeyListener(KeyListener listener) {
    return;
  }

  @Override
  public void setMouseListener(MouseListener listener) {
    // Do nothing.
  }

  @Override
  public void decreaseTempo() {
    int currentTempo = container.getTempo();
    int tempoToBeSet = currentTempo - 10;
    sequencer.setTempoInBPM(60000000 / tempoToBeSet);
  }

  @Override
  public void increaseTempo() {
    int currentTempo = container.getTempo();
    int tempoToBeSet = currentTempo + 10;
    sequencer.setTempoInBPM(60000000 / tempoToBeSet);
  }

  @Override
  public void close() {
    if (sequence != null && sequencer.isOpen()) {
      sequencer.close();
    }
  }
}
