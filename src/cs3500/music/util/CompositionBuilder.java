package cs3500.music.util;

import cs3500.music.MusicCreator;

/**
 * A builder of compositions.  Since we do not know in advance what
 * the name of the main type is for a model, we parameterize this builder interface
 * by an unknown type.
 *
 * @param <MusicCreatorOperations> The type of the constructed composition
 */
public interface CompositionBuilder<MusicCreatorOperations> {
  /**
   * This method constructs an actual composition, given the notes that have been added.
   *
   * @return The new composition
   */
  MusicCreatorOperations build();

  /**
   * This sets the tempo of the piece.
   *
   * @param tempo The speed, in microseconds per beat
   * @return This builder
   */
  CompositionBuilder<MusicCreatorOperations> setTempo(int tempo);

  /**
   * This method adds a new note to the piece.
   *
   * @param start      The start time of the note, in beats
   * @param end        The end time of the note, in beats
   * @param instrument The instrument number (to be interpreted by MIDI)
   * @param pitch      The pitch (in the range [0, 127], where 60 represents C4, the middle-C on a
   *                   piano)
   * @param volume     The volume (in the range [0, 127])
   * @return This builder
   */
  CompositionBuilder<MusicCreatorOperations> addNote(int start, int end, int instrument, int
          pitch, int volume);

  /**
   * This method creates a compositionBuilder which contains the passed repeat symbol.
   * @param type The type of repeat symbol.
   * @param beat The position of repeat symbol.
   * @return The compositionBuilder which contains the added repeat symbol.
   */
  CompositionBuilder<MusicCreatorOperations> addRepeatSymbol(String type, int beat);
}
