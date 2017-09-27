package cs3500.music.model;

import java.util.HashMap;
import java.util.HashSet;

/**
 * The interface of all models for the music creator.
 * Users can get or reset the tempo of the music.
 * Users can modify the notes by adding or deleting the note.
 * Can search the wanted note , get the highest or lowest note.
 * Can get the size of the music or beat information about the music.
 * Users can also play two music either simultaneously or consecutively.
 * Users can also get the whole music score view.
 */
public interface MusicCreatorOperations {

  /**
   * This method gets the tempo of current music.
   *
   * @return The tempo of current music.
   */
  int getTempo();

  /**
   * This method resets the tempo of this piece of music.
   *
   * @param tempo The tempo to be set.
   */
  void setTempo(int tempo);

  /**
   * This method adds a new node into the music.
   *
   * @param input The new node to be added.
   */
  void addNote(INote input);

  /**
   * This method adds a new node into the music
   *
   * @param start      The start time of the note.
   * @param duration   The length of this note.
   * @param instrument The instrument number to be interpreted by MIDI.
   * @param pitch      The pitch of this new note.
   * @param octave     The octave number for this new note.
   * @param volume     The volume of this new note.
   */
  void addNote(int start, int duration,
               int instrument, Pitch pitch, int octave, int volume);


  /**
   * This method deletes the note.
   *
   * @param input The note to be deleted.
   * @throws IllegalArgumentException if the input note doesnt exsit
   */
  void deleteNote(INote input) throws IllegalArgumentException;

  /**
   * This method searches if the input note contains in the music.
   *
   * @param input The note to be searched.
   * @return True if the music contains this note.
   */
  boolean searchNote(INote input);

  /**
   * This method gets the highest note in the current music.
   *
   * @return The highest note for this music.
   */
  INote getTheHighestNote();

  /**
   * This method gets the lowest note in the current music.
   *
   * @return The lowest note for this music.
   */
  INote getTheLowestNote();

  /**
   * This method adds the repeat symbol to the music model.
   * @param type The type of repeat.
   * @param beat The beat where the repeat is put.
   */
  void addRepeatSymbol(String type, int beat);

  /**
   * This method gets the repeat symbol based on the input beat.
   * @return Hash map indicating the beat type. If there is no beat, returning "no beat".
   */
  HashMap<Integer, String> getRepeatSymbol();

  /**
   * This method outputs the current size of the music, which means how many notes in the music.
   *
   * @return Integer represents the size of the music.
   */
  int getSize();

  /**
   * This method gets the sets of notes based on the given beat number.
   *
   * @param beat The beat number.
   * @return A hashSet of notes exists on that beat.
   */
  HashSet<INote> getNotesOfTheBeat(int beat);

  /**
   * This method gets a hashset of numbers which indicating
   * whether a certain note level exist on a certain beat in the time line
   *
   * @param beat The current beat on the time line.
   * @return A hashset of integer indicating the note levels condition.
   */
  HashSet<Integer> getNoteLevelOfTheBeat(int beat);

  /**
   * This method gets the node level difference based on the highest and lowest node.
   *
   * @return An integer representing the note level difference.
   */
  int getNoteLevelDifference();

  /**
   * This method gets the latest beat.
   *
   * @return The latest beat.
   */
  int getLatestBeat();

  /**
   * This method gets the last starting beat in this music.
   *
   * @return The last starting beat in this music.
   */
  int getTheLastStartBeat();

  /**
   * This method gets the whole data contains in this model.
   *
   * @return All the note data.
   */
  HashMap<Integer, HashSet<INote>> getNotesArray();

  /**
   * This method gets the notes contains whether a certain note level exists on
   * a certain beat in the timeline.
   *
   * @return notes of integer representing the beat.
   */
  HashMap<Integer, HashSet<Integer>> getHelper();

  /**
   * This method combines two pieces of music and play simultaneously.
   *
   * @param mco The other piece of music.
   */
  void playSimultaneous(MusicCreatorOperations mco);

  /**
   * This method combines two pieces of music and play consecutively.
   *
   * @param mco The other piece of music.
   */
  void playConsecutive(MusicCreatorOperations mco);
}
