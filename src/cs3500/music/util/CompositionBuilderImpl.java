package cs3500.music.util;

import cs3500.music.model.MusicCreatorModel;
import cs3500.music.model.MusicCreatorOperations;
import cs3500.music.model.Pitch;
import cs3500.music.model.PitchIndex;

/**
 * This class implements the composition built by importing a file.
 */
public class CompositionBuilderImpl implements CompositionBuilder<MusicCreatorOperations> {

  protected MusicCreatorOperations mco;

  /**
   * The default constructor.
   */
  public CompositionBuilderImpl() {
    this.mco = new MusicCreatorModel(1);
  }

  /**
   * The second constructor for CompositionBuilderImpl.
   *
   * @param mco The MusicCreatorOperations to be passed.
   */
  private CompositionBuilderImpl(MusicCreatorOperations mco) {
    this.mco = mco;
  }

  @Override
  public MusicCreatorOperations build() {
    return mco;
  }

  @Override
  public CompositionBuilder setTempo(int tempo) {
    mco.setTempo(tempo);
    return new CompositionBuilderImpl(mco);
  }

  @Override
  public CompositionBuilder addNote(int start, int end, int instrument, int pitch, int volume) {
    if (pitch < 0 && pitch > 127) {
      throw new IllegalArgumentException("Not valid pitch number!");
    }
    String representation = PitchIndex.getStringFromIndex(pitch);
    String pitchString;
    int octave;
    if (representation.length() == 2) {
      pitchString = representation.substring(0, 1);
      octave = Integer.parseInt(representation.substring(1, 2));
    } else {
      pitchString = representation.substring(0, 2);
      octave = Integer.parseInt(representation.substring(2, 3));
    }
    Pitch thisPitch = Pitch.toPitch(pitchString);
    mco.addNote(start, end - start + 1, instrument, thisPitch, octave, volume);
    return new CompositionBuilderImpl(mco);
  }

  @Override
  public CompositionBuilder addRepeatSymbol(String type, int beat) {
    if (type.equals("forward") || type.equals("backward")) {
      mco.addRepeatSymbol(type, beat);
    } else {
      throw new IllegalArgumentException("Not valid type!");
    }
    return new CompositionBuilderImpl(mco);
  }
}
