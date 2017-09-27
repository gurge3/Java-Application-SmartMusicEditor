package cs3500.music.model;

/**
 * This interface represents note Interface.
 * Users can get the pitch, beat, start time, octave,volume and instrument of the note.
 * Users can also reset all above of the note.
 * Users can compare the two notes.
 */
public interface INote {
  /**
   * This method gets the pitch of this node.
   *
   * @return The pitch of this node.
   */
  Pitch getPitch();

  /**
   * This method sets the pitch.
   *
   * @param pitch The pitch.
   */
  void setPitch(Pitch pitch);

  /**
   * This method gets the beat of this node.
   *
   * @return The number of beats for this node.
   */
  int getBeat();

  /**
   * This method sets the beat.
   *
   * @param beat The beat.
   */
  void setBeat(int beat);

  /**
   * This method gets the octave of this node.
   *
   * @return The octave of this node.
   */
  int getOctave();

  /**
   * This method sets the octave.
   *
   * @param octave The octave.
   */
  void setOctave(int octave);

  /**
   * This method gets the start time of this node.
   *
   * @return The time representing the start time of this node.
   */
  int getStartTime();

  /**
   * This method sets the start time.
   *
   * @param startTime The start time.
   */
  void setStartTime(int startTime);

  /**
   * This method outputs the sound level of this note.
   *
   * @return An integer representing the note level of this node.
   */
  int getNoteLevel();

  /**
   * This method compares two nodes and returns true if they are equal.
   *
   * @param input The given node.
   * @return True if these two nodes are same.
   */
  boolean compares(INote input);

  /**
   * This method gets the volume.
   *
   * @return The volume.
   */
  int getVolume();

  /**
   * This method sets the volume.
   *
   * @param volume The volume.
   */
  void setVolume(int volume);

  /**
   * This method gets the instrument.
   *
   * @return The instrument.
   */
  int getInstrument();

  /**
   * This method sets the instrument.
   *
   * @param instrument The instrument.
   */
  void setInstrument(int instrument);
}
