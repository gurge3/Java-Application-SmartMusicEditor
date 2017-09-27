package cs3500.music.model;

/**
 * A note class.
 * A note has its own pitch, beat, octave, starting time, volume and instrument played.
 * It has all the methods from its interface and its own private helper methods.
 */
public class Note implements INote {
  //fields of the Note
  private Pitch pitch;

  private int beat;

  private int octave;

  private int startTime;

  private int volume;

  private int instrument;

  /**
   * This constructor constructs a Node.
   *
   * @param pitch     The pitch of this node.
   * @param beat      The beat of this node, including the start time.
   * @param startTime The start time of this node.
   */
  public Note(Pitch pitch, int beat, int octave, int startTime, int volume, int instrument) {
    // Restrictions defined in the constructor.
    if (beat < 1) {
      throw new IllegalArgumentException("Please define a valid beat greater than 0!");
    } else {
      this.beat = beat;
    }

    if (octave < 0) {
      throw new IllegalArgumentException("Please define a valid octave number greater than -1");
    } else {
      this.octave = octave;
    }

    if (startTime < 0) {
      throw new IllegalArgumentException("Please define a valid start time greater than 0!");
    } else {
      this.startTime = startTime;
    }

    this.pitch = pitch;
    this.volume = volume;
    this.instrument = instrument;
  }


  @Override
  public Pitch getPitch() {
    return pitch;
  }

  @Override
  public void setPitch(Pitch pitch) {
    this.pitch = pitch;
  }

  @Override
  public int getBeat() {
    return beat;
  }

  @Override
  public void setBeat(int beat) {
    this.beat = beat;
  }

  @Override
  public int getOctave() {
    return octave;
  }

  @Override
  public void setOctave(int octave) {
    this.octave = octave;
  }

  @Override
  public int getStartTime() {
    return startTime;
  }

  @Override
  public void setStartTime(int startTime) {
    this.startTime = startTime;
  }

  @Override
  public int getNoteLevel() {
    return PitchIndex.getIndexFromString(toString());
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    result.append(pitch.toString());
    result.append(Integer.toString(octave));
    return result.toString();
  }

  @Override
  public boolean compares(INote input) {
    return pitch.equals(input.getPitch()) && beat == input.getBeat() && octave == input.getOctave()
            && startTime == input.getStartTime();
  }

  @Override
  public int getVolume() {
    return volume;
  }

  @Override
  public void setVolume(int volume) {
    this.volume = volume;
  }

  @Override
  public int getInstrument() {
    return instrument;
  }

  @Override
  public void setInstrument(int instrument) {
    this.instrument = instrument;
  }
}
