package cs3500.music.view;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * This is the view which supports tutorial mode.
 */
public class TutorialView extends CompositeView {

  private HashSet<Integer> notesGoal;

  private ArrayList<Integer> pressedKey;

  private ArrayList<Integer> checkedNote;

  private int correctNoteNumber;


  /**
   * This constructor constructs a Composite View by taking
   * both GUIView and MidiView.
   *
   * @param guiView  The GUIView to be taken.
   * @param midiView The MidiView to be taken.
   */
  public TutorialView(GUIView guiView, MidiView midiView) {
    super(guiView, midiView);
    correctNoteNumber = 0;
    checkedNote = new ArrayList<>();
    pressedKey = new ArrayList<>();
  }

  @Override
  public void showNote() {
    guiView.showNote();
    notesGoal = notesContainer.getHelper().get(currentBeat);
    Runnable checkRunnable = () -> {
      while (currentBeat <= notesContainer.getLastBeat()) {
        if (completeAllGoals()) {
          currentBeat++;
          setCurrentBeat(currentBeat);
          if (notesGoal == null) {
            // Empty Hash Set if no notes are there in the music.
            notesGoal = new HashSet<>();
          }
        }
      }
    };
    Thread checkThread = new Thread(checkRunnable);
    checkThread.run();
  }

  @Override
  public void setCurrentBeat(int beat) {
    this.currentBeat = beat;
    guiView.setCurrentBeat(currentBeat);
    midiView.setCurrentBeat(currentBeat);
    pressedKey = new ArrayList<>();
    correctNoteNumber = 0;
    checkedNote = new ArrayList<>();
    notesGoal = notesContainer.getHelper().get(currentBeat);
  }

  /**
   * This helper method determines whether the goal of each beat is completed.
   *
   * @return True if all the keys are pressed in the current beat.
   */
  private boolean completeAllGoals() {
    for (Integer num : notesGoal) {
      if (pressedKey.contains(num)
              && !checkedNote.contains(num)) {
        correctNoteNumber++;
        checkedNote.add(num);
      }
    }
    return correctNoteNumber == notesGoal.size();
  }

  public void addPressKey(int noteLevel) {
    if (notesGoal.contains(noteLevel)) {
      pressedKey.add(noteLevel);
    }
  }
}
