package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import cs3500.music.controller.NotesContainer;
import cs3500.music.model.INote;
import cs3500.music.model.PitchIndex;

/**
 * This class represents a console view of this music creator.
 * A console is to represent the music in text.
 * The class has appendable to output the text.
 * It also has notesContainer to get all the information from the given model.
 */
public class ConsoleView implements IView {

  //fields of the class
  Appendable appendable;
  private NotesContainer notesContainer;

  /**
   * The default constructor with System.out appendable.
   */
  public ConsoleView() {
    super();
    this.appendable = System.out;
  }

  /**
   * This helper function helpers print out current status in text format.
   * @return A string representing the current status in text format.
   */
  private String consoleHelper() {
    StringBuilder status = new StringBuilder();
    String xNotation = "  X  ";
    String barNotation = "  |  ";
    String emptyFiveSpaces = "     ";
    HashMap<Integer, HashSet<INote>> notesArray;
    HashMap<Integer, HashSet<Integer>> helper;

    notesArray = notesContainer.getArray();
    helper = notesContainer.getHelper();

    if (notesArray.size() == 0) {
      status.append("Current No Node in this music");
      return status.toString();
    } else {
      int timeOfTheEnd = Collections.max(notesArray.keySet());
      int latestTime = helper.size();


      // calculating the number of digits for latestTime
      int latestTimeDigits = numOfDigits(latestTime);
      // Printing pitches based on the level difference
      int pitchDifference = notesContainer.getNoteLevelDifference();
      // appending space referencing to the length of last beat number
      for (int i = 0; i < latestTimeDigits; i++) {
        status.append(" ");
      }
      // Appending pitch information
      for (int i = 0; i <= pitchDifference; i++) {
        String pitchString = PitchIndex
                .getStringFromIndex(notesContainer.getLowestNoteLevel() + i);
        if (pitchString.length() == 2) {
          StringBuilder sb = new StringBuilder();
          sb.append("  ");
          sb.append(pitchString);
          sb.append(" ");
          status.append(sb);
        } else if (pitchString.length() == 3) {
          StringBuilder sb = new StringBuilder();
          sb.append(" ");
          sb.append(pitchString);
          sb.append(" ");
          status.append(sb);
        } else {
          StringBuilder sb = new StringBuilder();
          sb.append(pitchString);
          sb.append(" ");
          status.append(sb);
        }
      }

      // Appending vertical timeline and note graph
      for (int i = 0; i < latestTime; i++) {
        status.append("\n");
        for (int j = 0; j < latestTimeDigits - numOfDigits(i); j++) {
          status.append(" ");
        }
        status.append(i);
        // Integer represents the note level, and Boolean represents whether it's head
        TreeMap<Integer, Boolean> tree = new TreeMap<>();
        if (notesArray.containsKey(i)) {
          for (INote n : notesArray.get(i)) {
            tree.put(n.getNoteLevel(), true);
          }
        }
        if (helper.containsKey(i)) {
          HashSet<Integer> currentHashSet = helper.get(i);
          for (Integer level : currentHashSet) {
            if (!tree.containsKey(level)) {
              tree.put(level, false);
            }
          }
        }
        if (!notesArray.containsKey(i) && helper.get(i).isEmpty()) {
          for (int j = 0;
               j <= notesContainer.getHighestNoteLevel() - notesContainer.getLowestNoteLevel();
               j++) {
            status.append(emptyFiveSpaces);
          }
        }
        int iteration = 0;
        int difference;
        int lastNumber = 0;
        // If a certain time is occupied, appending "  |  "
        // If a certain time is the starting time for a note, appending "  X  "
        Iterator entries = tree.entrySet().iterator();
        while (entries.hasNext()) {
          Map.Entry entry = (Map.Entry) entries.next();
          if (iteration == 0) {
            difference = (int) entry.getKey() - notesContainer.getLowestNoteLevel();
          } else {
            difference = (int) entry.getKey() - lastNumber - 1;
          }
          lastNumber = (int) entry.getKey();
          iteration++;
          for (int j = 0; j < difference; j++) {
            status.append(emptyFiveSpaces);
          }
          if ((boolean) entry.getValue()) {
            status.append(xNotation);
          } else {
            status.append(barNotation);
          }
          if (!entries.hasNext()) {
            for (int j = 0; j < notesContainer.getHighestNoteLevel() - lastNumber; j++) {
              status.append(emptyFiveSpaces);
            }
          }
        }
      }
      status.append("\n");
    }
    return status.toString();
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

  /**
   * The second constructor which takes an Appendable object.
   *
   * @param app The appendable object to be passed.
   */
  public ConsoleView(Appendable app) {
    super();
    this.appendable = app;
  }


  @Override
  public void showNote() {
    try {
      appendable.append(consoleHelper());
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }


  @Override
  public void setCurrentBeat(int beat) {
    // Do nothing.
  }

  @Override
  public int getCurrentBeat() {
    return 0;
  }

  @Override
  public void controlMusic() {
    // Do nothing.
  }

  @Override
  public void setContainer(NotesContainer notesContainer) {
    this.notesContainer = notesContainer;
  }


  @Override
  public void setKeyListener(KeyListener listener) {
    return;
  }

  /**
   * This method sets the MouseListener to this panel.
   *
   * @param listener The listener to be added.
   */
  @Override
  public void setMouseListener(MouseListener listener) {
    // Do nothing.
  }

  @Override
  public void decreaseTempo() {
    // Do nothing.
  }

  @Override
  public void increaseTempo() {
    // Do nothing.
  }

  @Override
  public void close() {
    return;
  }
}
