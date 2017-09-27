package cs3500.music.controller;

import java.util.HashMap;
import java.util.HashSet;

import cs3500.music.model.INote;

/**
 *  This interface is used for the data object passed to the view classes.
 *  The class implemented is used to provide notes data for View classes.
 *  The class implemented is only constructed when all the notes are imported.
 */
public interface INotesContainer {

  /**
   * This method gets the tempo from the notes container.
   *
   * @return An interger representing the tempo.
   */
  int getTempo();

  /**
   * This method sets the tempo to be played
   * @param tempo the tempo to be set.
   */
  void setTempo(int tempo);

  /**
   * This method gets the lowest note level for the music.
   *
   * @return An integer representing the lowest note level.
   */
  int getLowestNoteLevel();

  /**
   * This method gets the highest note level for the music.
   *
   * @return An integer representing the highest note level.
   */
  int getHighestNoteLevel();

  /**
   * This method gets the whole data contains in this model.
   *
   * @return All the note data.
   */
  int getNoteLevelDifference();

  /**
   * This method gets the set of note array at a certain beat.
   *
   * @param beat The beat number.
   * @return A note array at a certain beat.
   */
  HashSet<INote> getNotesArray(int beat);

  /**
   * This method gets the complete structure.
   *
   * @return The complete structure of the data.
   */
  HashMap<Integer, HashSet<INote>> getArray();

  /**
   * This method gets the processed data structure.
   *
   * @return The processed data structure.
   */
  HashMap<Integer, HashSet<Integer>> getHelper();

  /**
   * This method gets the type of the repeat symbol.
   * @param key The position of the repeat symbol.
   * @return A string representing the type of the repeat symbol.
   */
  String getRepeatType(int key);

  /**
   * This method gets the last starting beat in this music.
   *
   * @return The last starting beat in this music.
   */
  int getTheLastStartBeat();

  /**
   * This method gets the latest beat.
   *
   * @return The latest beat.
   */
  int getLastBeat();

  /**
   * This method gets a hashset of numbers which indicating
   * whether a certain note level exist on a certain beat in the time line
   *
   * @param beat The current beat on the time line.
   * @return A hashset of integer indicating the note levels condition.
   */
  HashSet<Integer> getNoteLevelOfTheBeat(int beat);
}
