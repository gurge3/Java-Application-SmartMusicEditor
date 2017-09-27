package cs3500.music.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Represents the music creator model class.
 * It has a hash map with the beat(int) as key and hash set of notes in value.
 * it also has the highest note and lowest note in the music.
 * Besides all the method from interface, this model has its own private methods.
 */
public class MusicCreatorModel implements MusicCreatorOperations {


  //fields of the model
  private int tempo;

  private HashMap<Integer, HashSet<INote>> notesArray;

  private INote highestNote;

  private INote lowestNote;

  // This hash map is used to represent the location of repeat symbol.
  // If value is "backward", there is a backward repeat symbol in the value beat.
  // If value is "forward", there is a forward repeat symbol in the value beat.
  private HashMap<Integer, String> repeatMap;

  /**
   * This constructor constructs a MusicCreatorModel class with initially no music.
   */
  public MusicCreatorModel(int tempo) {
    notesArray = new HashMap<>();
    if (tempo <= 0) {
      throw new IllegalArgumentException("Invalid tempo.");
    } else {
      this.tempo = tempo;
      repeatMap = new HashMap<>();
    }
  }

  /**
   * This constructor constructs a MusicCreatorModel class with given list of notes.
   *
   * @param listOfNotes The list of music to be imported.
   * @param tempo       The tempo of music.
   */
  public MusicCreatorModel(ArrayList<INote> listOfNotes, int tempo) {
    for (INote note : listOfNotes) {
      addNote(note);
    }
    if (tempo <= 0) {
      throw new IllegalArgumentException("Invalid tempo.");
    } else {
      this.tempo = tempo;
      repeatMap = new HashMap<>();
    }
  }

  @Override
  public void addNote(INote note) {
    if (notesArray.containsKey(note.getStartTime())) {
      notesArray.get(note.getStartTime()).add(note);
    } else {
      HashSet<INote> notes = new HashSet<>();
      notes.add(note);
      notesArray.put(note.getStartTime(), notes);
    }
    updateNote(note);
  }

  @Override
  public void addNote(int start, int duration,
                      int instrument, Pitch pitch, int octave, int volume) {
    addNote(new Note(pitch, duration, octave, start, volume, instrument));
  }

  /**
   * This method updates the highest and lowest note referencing to the given note.
   *
   * @param note The given note to be referenced.
   */
  protected void updateNote(INote note) {
    if (highestNote == null) {
      highestNote = note;
    } else {
      if (note.getOctave() > highestNote.getOctave()) {
        highestNote = note;
      } else if (note.getOctave() == highestNote.getOctave()) {
        if (note.getPitch().getNumber() > highestNote.getPitch().getNumber()) {
          highestNote = note;
        }
      }
    }

    if (lowestNote == null) {
      lowestNote = note;
    } else {
      if (note.getOctave() < lowestNote.getOctave()) {
        lowestNote = note;
      } else if (note.getOctave() == lowestNote.getOctave()) {
        if (note.getPitch().getNumber() < lowestNote.getPitch().getNumber()) {
          lowestNote = note;
        }
      }
    }
  }


  @Override
  public void deleteNote(INote input) throws IllegalArgumentException {
    if (!searchNote(input)) {
      throw new IllegalArgumentException("Given note is not found in this music!");
    } else {
      notesArray.get(input.getStartTime()).remove(input);
    }
    if (notesArray.get(input.getStartTime()).isEmpty()) {
      notesArray.remove(input.getStartTime());
    }
    if (lowestNote.compares(input)) {
      for (int i = input.getNoteLevel(); i <= highestNote.getNoteLevel(); i++) {
        for (int j = 0; j <= getTheLastStartBeat(); j++) {
          if (notesArray.get(j) != null) {
            for (INote note : notesArray.get(j)) {
              if (note.getNoteLevel() == i) {
                this.lowestNote = note;
                return;
              }
            }
          }
        }
      }
      this.lowestNote = null;
    }

    if (highestNote.compares(input)) {
      for (int i = input.getNoteLevel(); i > 0; i--) {
        for (int j = 0; j <= getTheLastStartBeat(); j++) {
          if (notesArray.get(j) != null) {
            for (INote note : notesArray.get(j)) {
              if (note.getNoteLevel() == i) {
                this.highestNote = note;
                return;
              }
            }
          }
        }
      }
      this.highestNote = null;
    }
  }

  @Override
  public boolean searchNote(INote input) {
    if (notesArray.containsKey(input.getStartTime())) {
      for (INote note : notesArray.get(input.getStartTime())) {
        if (note.compares(input)) {
          return true;
        }
      }
    } else {
      return false;
    }
    return false;
  }

  @Override
  public int getNoteLevelDifference() {
    if (notesArray.isEmpty()) {
      return 0;
    }
    return getTheHighestNote().getNoteLevel() - getTheLowestNote().getNoteLevel();
  }

  @Override
  public int getLatestBeat() {
    if (notesArray.isEmpty()) {
      return 0;
    } else {
      return Collections.max(getCurrentStatusHelper(
              Collections.max(notesArray.keySet())
      ).keySet());
    }
  }


  @Override
  public INote getTheHighestNote() {
    INote note = this.highestNote;
    return note;
  }


  @Override
  public INote getTheLowestNote() {
    INote note = this.lowestNote;
    return note;
  }

  @Override
  public void addRepeatSymbol(String type, int beat) {
    repeatMap.put(beat, type);
  }

  @Override
  public HashMap<Integer, String> getRepeatSymbol() {
    return repeatMap;
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
  public HashSet<INote> getNotesOfTheBeat(int beat) {
    return notesArray.get(beat);
  }

  @Override
  public HashMap<Integer, HashSet<INote>> getNotesArray() {
    return notesArray;
  }

  @Override
  public HashMap<Integer, HashSet<Integer>> getHelper() {
    return getCurrentStatusHelper(getTheLastStartBeat());
  }

  /**
   * This method calculates the number of digits given input.
   *
   * @param number The integer being calculated.
   * @return The number of digits for the input integer.
   */
  private int numOfDigits(int number) {
    if (number == 0) {
      return 1;
    }
    return (int) (Math.log10(number) + 1);
  }

  @Override
  public HashSet<Integer> getNoteLevelOfTheBeat(int beat) {
    HashSet<Integer> result
            = getCurrentStatusHelper(Collections.max(notesArray.keySet())).get(beat);
    if (result == null) {
      return new HashSet<Integer>();
    } else {
      return result;
    }
  }

  /**
   * This helper method helps find whether a certain note occupies in the timeline.
   *
   * @param timeOfTheEnd The last beat for this music.
   * @return A hashset indicating if a certain note occupies a certain beat in the timeline.
   */
  protected HashMap<Integer, HashSet<Integer>> getCurrentStatusHelper(int timeOfTheEnd) {
    HashMap<Integer, HashSet<Integer>> hm = new HashMap<>();
    for (int i = 0; i <= timeOfTheEnd; i++) {
      hm.put(i, new HashSet<>());
    }
    for (int i = 0; i <= timeOfTheEnd; i++) {
      if (notesArray.containsKey(i)) {
        for (INote n : notesArray.get(i)) {
          int duration = n.getBeat();
          int noteLevel = n.getNoteLevel();
          for (int j = 0; j < duration; j++) {
            if (hm.get(i + j) == null) {
              HashSet<Integer> hashSet2 = new HashSet<>();
              hashSet2.add(noteLevel);
              hm.put(i + j, hashSet2);
            } else {
              hm.get(i + j).add(noteLevel);
            }
          }
        }
      }
    }
    return hm;
  }

  @Override
  public int getSize() {
    int size = 0;
    for (int index : notesArray.keySet()) {
      size += notesArray.get(index).size();
    }
    return size;
  }

  @Override
  public int getTheLastStartBeat() {
    if (notesArray.isEmpty()) {
      return 0;
    }
    return Collections.max(notesArray.keySet());
  }

  @Override
  public void playSimultaneous(MusicCreatorOperations mco) {
    for (int i = 0; i <= mco.getTheLastStartBeat(); i++) {
      if (mco.getNotesOfTheBeat(i) != null) {
        for (INote note : mco.getNotesOfTheBeat(i)) {
          addNote(note);
        }
      }
    }
  }

  @Override
  public void playConsecutive(MusicCreatorOperations mco) {
    int lastBeat = mco.getTheLastStartBeat();
    int latestBeat = getLatestBeat();
    for (int i = 0; i <= lastBeat; i++) {
      if (mco.getNotesOfTheBeat(i) != null) {
        for (INote note : mco.getNotesOfTheBeat(i)) {
          INote thisNote = note;
          thisNote.setStartTime(thisNote.getStartTime() + latestBeat + 1);
          addNote(note);
        }
      }
    }
  }
}
