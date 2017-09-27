package cs3500.music.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import cs3500.music.model.INote;

/**
 * This class contains notes which are only constructed once, only accessible after.
 * This class is used to provide notes data for View classes.
 * This class is only constructed when all the notes are imported.
 */
public final class NotesContainer implements INotesContainer {
  private int tempo;
  private final HashMap<Integer, HashSet<INote>> notesArray;
  private final HashMap<Integer, HashSet<Integer>> helper;
  private final HashMap<Integer, String> repeatMap;
  private final int noteLevelDifference;
  private final int lowestNoteLevel;
  private final int highestNoteLevel;

  /**
   * The default constructor constructing a NotesContainer.
   *
   * @param tempo      The tempo of the music.
   * @param notesArray A note array at a certain beat.
   */
  public NotesContainer(int tempo, HashMap<Integer, HashSet<INote>> notesArray,
                        HashMap<Integer, HashSet<Integer>> helper, int noteLevelDifference,
                        int lowestNoteLevel, int highestNoteLevel,
                        HashMap<Integer, String> repeatMap) {
    this.tempo = tempo;
    this.notesArray = notesArray;
    this.helper = helper;
    this.noteLevelDifference = noteLevelDifference;
    this.highestNoteLevel = highestNoteLevel;
    this.lowestNoteLevel = lowestNoteLevel;
    this.repeatMap = repeatMap;
  }

  @Override
  public int getTempo() {
    return tempo;
  }

  @Override
  public void setTempo(int tempo) {
    this.tempo = tempo;
  }

  @Override
  public int getLowestNoteLevel() {
    return this.lowestNoteLevel;
  }

  @Override
  public int getHighestNoteLevel() {
    return this.highestNoteLevel;
  }

  @Override
  public int getNoteLevelDifference() {
    return this.noteLevelDifference;
  }

  @Override
  public HashSet<INote> getNotesArray(int beat) {
    return notesArray.get(beat);
  }

  @Override
  public HashMap<Integer, HashSet<INote>> getArray() {
    return notesArray;
  }

  @Override
  public HashMap<Integer, HashSet<Integer>> getHelper() {
    return helper;
  }

  @Override
  public String getRepeatType(int key) {
    if (!repeatMap.containsKey(key)) {
      return "No repeat";
    } else {
      return repeatMap.get(key);
    }
  }

  @Override
  public int getTheLastStartBeat() {
    if (notesArray.isEmpty()) {
      return 0;
    }
    return Collections.max(notesArray.keySet());
  }

  @Override
  public int getLastBeat() {
    if (notesArray.isEmpty()) {
      return 0;
    } else {
      return Collections.max(helper.keySet());
    }
  }

  @Override
  public HashSet<Integer> getNoteLevelOfTheBeat(int beat) {
    HashSet<Integer> result = helper.get(beat);
    if (result == null) {
      return new HashSet<Integer>();
    } else {
      return result;
    }
  }
}
