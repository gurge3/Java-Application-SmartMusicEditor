package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import cs3500.music.controller.NotesContainer;

/**
 * This view is a composite of MidiView and GUIView.
 * It has a GUIView which handles visual representation of the music.
 * It has a MidiView which handles audio representation of the music.
 * It has a current beat to keep tracking the beat.
 * It has keyboard and mouse event setters to enable keyboard and mouse operation.
 */
public class CompositeView implements IView {

  protected GUIView guiView;

  protected MidiView midiView;

  protected NotesContainer notesContainer;

  protected int currentBeat;

  private boolean isStop = true;

  private boolean isChange = false;

  private boolean isPlaying = false;

  private int repeatBeginBeat;

  private ArrayList<Integer> repeatedBeat;

  /**
   * This constructor constructs a Composite View by taking
   * both GUIView and MidiView.
   *
   * @param guiView The GUIView to be taken.
   * @param midiView   The MidiView to be taken.
   */
  public CompositeView(GUIView guiView, MidiView midiView) {
    this.guiView = guiView;
    this.midiView = midiView;
    repeatBeginBeat = 0;
  }

  @Override
  public void showNote() {
    guiView.showNote();
    if (!isPlaying) {
      Runnable moveRunnable = () -> {
        isPlaying = true;
        while (currentBeat <= notesContainer.getLastBeat()) {
          if (notesContainer.getLastBeat() == 0) {
            break;
          }
          if (!isStop && !isChange) {
            currentBeat++;
            setCurrentBeatHelper(currentBeat);
            String repeatType = notesContainer.getRepeatType(currentBeat);
            if (repeatType.equals("forward")) {
              repeatBeginBeat = currentBeat;
            } else if (repeatType.equals("backward") && !repeatedBeat.contains(currentBeat)) {
              repeatedBeat.add(currentBeat);
              currentBeat = repeatBeginBeat;
              setCurrentBeatHelperForRepeat(currentBeat);
            }
            try {
              Thread.sleep(notesContainer.getTempo() / 1055);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
        isPlaying = false;
        isStop = true;
      };
      Thread moveThread = new Thread(moveRunnable);
      moveThread.start();
    }
  }

  @Override
  public int getCurrentBeat() {
    int beat = currentBeat;
    return beat;
  }

  @Override
  public void controlMusic() {
    Runnable delayRunnable = () -> {
      try {
        if (this.isStop) {
          Thread.sleep(notesContainer.getTempo() / 1055);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }   
      this.isStop = !this.isStop;
    };
    Thread delayThread = new Thread(delayRunnable);
    delayThread.start();
    midiView.controlMusic();
  }

  @Override
  public void setCurrentBeat(int beat) {
    setCurrentBeatHelperForRepeat(beat);
    if (beat == 0) {
      repeatedBeat = new ArrayList<>();
    }
  }

  /**
   * This helper method is used to set beat for internal loop.
   * @param beat The beat ot be set.
   */
  private void setCurrentBeatHelper(int beat) {
    this.currentBeat = beat;
    guiView.setCurrentBeat(currentBeat);
    showNote();
  }

  /**
   * This method is used to set beat for the repeat feature.
   * @param beat The beat to be set.
   */
  private void setCurrentBeatHelperForRepeat(int beat) {
    this.currentBeat = beat;
    guiView.setCurrentBeat(currentBeat);
    midiView.setCurrentBeat(currentBeat);
    showNote();
  }

  @Override
  public void setContainer(NotesContainer container) {
    this.notesContainer = container;
    guiView.setContainer(notesContainer);
    midiView.setContainer(notesContainer);
  }

  @Override
  public void setKeyListener(KeyListener listener) {
    guiView.addKeyListener(listener);
  }


  @Override
  public void setMouseListener(MouseListener listener) {
    guiView.setMouseListener(listener);
  }

  @Override
  public void decreaseTempo() {
    int currentTempo = notesContainer.getTempo();
    notesContainer.setTempo(60000000 / (60000000 / currentTempo - 10));
    System.out.println("Tempo in composite: " + (60000000 / currentTempo - 10));
    midiView.decreaseTempo();
    changeTempoHelper();
  }

  @Override
  public void increaseTempo() {
    int currentTempo = notesContainer.getTempo();
    notesContainer.setTempo(60000000 / (60000000 / currentTempo + 10));
    System.out.println("Tempo in composite: " + (60000000 / currentTempo + 10));
    midiView.increaseTempo();
    changeTempoHelper();
  }

  // Private helper method to help pause the music for a bit while entering the tempo change button.
  private void changeTempoHelper() {
    try {
      isChange = true;
      Thread.sleep(notesContainer.getTempo() / 1000);
      isChange = false;
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
  }

  @Override
  public void close() {
    guiView.close();
  }

}
