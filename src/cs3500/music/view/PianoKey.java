package cs3500.music.view;

import javax.swing.JLabel;

import cs3500.music.model.PitchIndex;

/**
 * This class represents a single key(either black or white)of a piano.
 * Each key has a note level which is the assigned note level.
 * It also has a boolean to determine if it is pressed.
 */
public class PianoKey {

  //fields of the class
  private final int noteLevel;
  //Button
  private JLabel label;
  //true if it is pressed
  boolean isKeyPressed;

  /**
   * This constructor constructs a piano key.
   *
   * @param noteLevel The note level to be assigned to this key.
   */
  public PianoKey(int noteLevel) {
    this.noteLevel = noteLevel;
    isKeyPressed = false;
    label = new JLabel();
    label.setName(Integer.toString(noteLevel));
  }

  /**
   * This method determines whether this piano key is a black key or not.
   * Obviously, pitch contains "sharp" is a black key.
   *
   * @return true if this piano key is a black key.
   */
  public boolean isBlackKey() {
    return PitchIndex.getStringFromIndex(noteLevel).substring(1, 2).equals("#");
  }

  /**
   * This method sets the current key presssed.
   */
  public void pressCurrentKey() {
    isKeyPressed = true;
  }

  /**
   * This method sets the current key presssed.
   */
  public void releaseCurrentKey() {
    isKeyPressed = false;
  }

  /**
   * This method gets the note level for this key.
   *
   * @return An integer representing the note level for this key.
   */
  public int getNoteLevel() {
    return noteLevel;
  }

  /**
   * This method gets a JButton.
   * @return A JButton.
   */
  public JLabel getLabel() {
    return label;
  }
}
