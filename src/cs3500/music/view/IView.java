package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import cs3500.music.controller.NotesContainer;

/**
 * The interface for all three views.(Console, GUI or MIDI).
 * Console view is the text view of the music.
 * Gui view is the visual view of the music with a read line represents the ongoin beat.
 * It also consists a piano view.
 * Midi view is the audio.
 */
public interface IView {
  /**
   * This method displays or play the note.
   */
  void showNote();

  /**
   * This method sets the current beat.
   *
   * @param beat An integer representing the current beat.
   */
  void setCurrentBeat(int beat);

  /**
   * This method gets the current beat playing in the view.
   * @return An integer representing the current playing beat.
   */
  int getCurrentBeat();

  /**
   * This method controls the music.
   */
  void controlMusic();

  /**
   * This method sets the MusicCreatorOperations.
   *
   * @param container The MusicCreatorOperations to be set.
   */
  void setContainer(NotesContainer container);

  /**
   * This method sets the listener to this JPanel
   *
   * @param listener The listener to be added.
   */
  void setKeyListener(KeyListener listener);

  /**
   * This method sets the MouseListener to this panel.
   * @param listener The listener to be added.
   */
  void setMouseListener(MouseListener listener);

  /**
   * This method decreases the tempo by 10 BPM.
   */
  void decreaseTempo();

  /**
   * This method increases the tempo by 10 BPM.
   */
  void increaseTempo();

  /**
   * This method closes or shut down the view.
   */
  void close();
}
