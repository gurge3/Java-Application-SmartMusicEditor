package cs3500.music.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.BasicStroke;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JPanel;

import cs3500.music.controller.NotesContainer;
import cs3500.music.model.INote;
import cs3500.music.model.PitchIndex;

/**
 * This class represents a visual view of the music.
 * It consists a notes container to get the information from model.
 * It also has a red beat bar to show the on going beat.
 */
public class PitchGraph extends JPanel {

  // Declaring constants
  private static final int XINTERVAL = 32;
  private static final int YINTERVAL = 18;
  protected NotesContainer container;
  protected int currentBeat;
  protected int height;
  protected int length;

  /**
   * Constructs the pitch graph.
   *
   * @param container   The data container.
   * @param currentBeat The current playing time.
   */
  public PitchGraph(NotesContainer container, int currentBeat) {
    int lastBeat = container.getLastBeat();
    height = container.getNoteLevelDifference() * YINTERVAL + 67;
    // unify the size of this panel
    if (height < 400) {
      height = 400;
    }
    length = 140 + XINTERVAL * lastBeat;
    // unify the size of this panel
    if (length < 1400) {
      length = 1400;
    }
    this.setPreferredSize(new Dimension(length, height));
    this.container = container;
    this.currentBeat = currentBeat;

    this.setFocusable(true);
    this.requestFocusInWindow();
  }


  /**
   * This method sets the current on going beat as the given beat.
   *
   * @param beat the given beat
   */
  public void setCurrentBeat(int beat) {
    this.currentBeat = beat;
    repaint();
  }

  /**
   * This method sets the note container.
   * @param container The note container to be set.
   */
  public void setContainer(NotesContainer container) {
    this.container = container;
    height = container.getNoteLevelDifference() * YINTERVAL + 67;
    this.setPreferredSize(new Dimension((int) this.getPreferredSize().getWidth(), height));
    updateUI();
  }

  /**
   * This method draws the visual view of the music.
   *
   * @param g the given graph to be painted on
   */
  public void paintComponent(Graphics g) {
    // Initializing Graphics2D.
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    // Adding all pitch strings to a list and then print this list of strings to the panel.
    int difference = container.getNoteLevelDifference();
    int lastBeat = container.getLastBeat();
    ArrayList<String> listOfPitches = new ArrayList<>();
    int lowestLevel = container.getLowestNoteLevel();
    for (int i = container.getLowestNoteLevel(); i <= lowestLevel + difference; i++) {
      listOfPitches.add(PitchIndex.getStringFromIndex(i));
    }

    // Drawing string representing each Pitch.
    g2d.setFont(new Font("default", Font.BOLD, 15));
    for (int i = 0; i < listOfPitches.size(); i++) {
      g2d.drawString(listOfPitches.get(i), 10, 60 + YINTERVAL * i);
    }

    // Setting line bold
    g2d.setStroke(new BasicStroke(3));

    // Drawing horizontal lines representing Pitches.
    for (int i = 0; i <= difference + 1; i++) {
      g2d.drawLine(70, 47 + YINTERVAL * i,
              70 + XINTERVAL * (lastBeat + 1), 47 + YINTERVAL * i);
    }

    g2d.setFont(new Font("default", Font.BOLD, 18));


    // Drawing color bars representing notes
    for (int i = 0; i <= lastBeat; i++) {
      if (container.getNotesArray(i) == null) {
        continue;
      }
      if (!container.getNotesArray(i).isEmpty()) {
        HashSet<INote> notes = container.getNotesArray(i);
        for (INote note : notes) {
          int startBeat = note.getStartTime();
          int duration = note.getBeat();
          int noteLevel = note.getNoteLevel();
          int respectivePosition = noteLevel - container.getLowestNoteLevel();
          for (int j = startBeat; j < startBeat + duration; j++) {
            if (j == startBeat) {
              g2d.setColor(Color.BLACK);

            } else {
              g2d.setColor(Color.GREEN);
            }
            g2d.fillRect((70 + XINTERVAL * j),
                    respectivePosition * YINTERVAL + 47, XINTERVAL, YINTERVAL);
          }
        }
      }
    }

    g2d.setColor(Color.BLACK);
    // Drawing vertical lines representing beats
    for (int i = 0; i <= lastBeat + 1; i++) {
      // only draw lines when i is a multiple of 4.
      if (i % 4 == 0) {
        g2d.drawString(Integer.toString(i), 60 + XINTERVAL * i, 27);
        // "70" is the length of the empty space for the far right buffer space
        // "47" is the height from the top to the first line
        g2d.drawLine(70 + XINTERVAL * i, 47, 70 + XINTERVAL * i,
                (container.getNoteLevelDifference() + 1) * YINTERVAL + 47);
      }
      // Always drawing a line at the end with string notation.
      if (i == lastBeat + 1) {
        g2d.drawString(Integer.toString(i), 60 + XINTERVAL * i, 27);
        g2d.drawLine(70 + XINTERVAL * i, 47, 70 + XINTERVAL * i,
                (container.getNoteLevelDifference() + 1) * YINTERVAL + 47);
      }
    }

    // Drawing a status bar representing the current beat
    g2d.setColor(Color.RED);
    g2d.drawLine(70 + XINTERVAL * currentBeat, 47, 70 + XINTERVAL * currentBeat,
            (container.getNoteLevelDifference() + 1) * YINTERVAL + 47);

    // Drawing repeat symbol
    for (int i = 0; i <= container.getLastBeat(); i++) {
      if (container.getRepeatType(i).equals("backward")) {
        g2d.setColor(Color.BLACK);
        g2d.drawLine(70 + XINTERVAL * i, 47, 70 + XINTERVAL * i,
                (container.getNoteLevelDifference() + 1) * YINTERVAL + 47);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        g2d.drawString("Backward Repeat", 60 + XINTERVAL * i, 42);
      } else if (container.getRepeatType(i).equals("forward")) {
        g2d.setColor(Color.BLUE);
        g2d.drawLine(70 + XINTERVAL * i, 47, 70 + XINTERVAL * i,
                (container.getNoteLevelDifference() + 1) * YINTERVAL + 47);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        g2d.drawString("Forward Repeat", 60 + XINTERVAL * i, 42);
      }
    }
  }

  /**
   * This method gets the current x position of the status bar.
   * @return An integer representing the x position of the bar.
   */
  public int getRedLineXPosition() {
    return 70 + XINTERVAL * currentBeat;
  }
}
