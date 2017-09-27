package cs3500.music.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JPanel;
import javax.swing.JLabel;

import cs3500.music.controller.NotesContainer;

/**
 * This class represents a piano graph JPanel class.
 * A piano has black keys and white keys.
 * black keys are sharps and white keys are normal notes.
 * The class has a notescontainer to get the information for the model.
 */
public class PianoGraph extends JPanel {

  //fields of the class
  protected NotesContainer notesContainer;

  protected int beat;

  protected ArrayList<PianoKey> whiteKeys;
  protected ArrayList<PianoKey> blackKeys;

  private MouseListener listener;

  /**
   * This constructor constructs a PianoGraph class
   *
   * @param notesContainer The note container to be imported.
   * @param beat           The current time of playing
   */
  public PianoGraph(NotesContainer notesContainer, int beat) {
    this.notesContainer = notesContainer;
    this.beat = beat;
    importPitches();
  }

  /**
   * This method sets the beat.
   *
   * @param beat The beat to be set.
   */
  public void setCurrentBeat(int beat) {
    this.beat = beat;
    repaint();
  }

  /**
   * This method prints on the canvas.
   *
   * @param g The Graphics.
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    importPitches();
    // Setting color to black and drawing white key with black margin.
    for (int i = 0; i < whiteKeys.size(); i++) {
      PianoKey whiteKey = whiteKeys.get(i);
      g2d.setColor(Color.BLACK);
      g2d.drawRect(60 + i * 17, 0, 17, 360);
      if (!whiteKey.isKeyPressed) {
        g2d.setColor(Color.WHITE);
      } else {
        g2d.setColor(Color.GREEN);
      }
      JLabel label = whiteKey.getLabel();
      label.setBounds(61 + i * 17, 0, 16, 360);
      label.setBackground(Color.white);
      g2d.fillRect(61 + i * 17, 0, 16, 360);
      this.add(label);
    }

    int counter = 0;
    int currentLoc = 74;
    // Setting color to black and drawing black key with black margin.
    for (int i = 0; i < blackKeys.size(); i++) {
      PianoKey blackKey = blackKeys.get(i);
      counter++;
      int xPos = currentLoc + i * 17;
      if (counter == 2) {
        currentLoc += 17;
      }
      if (counter == 5) {
        currentLoc += 17;
        counter = 0;
      }
      if (!blackKey.isKeyPressed) {
        g2d.setColor(Color.BLACK);
      } else {
        g2d.setColor(Color.PINK);
      }
      JLabel label = blackKey.getLabel();
      label.setBounds(xPos, 0, 8, 160);
      label.setBackground(Color.white);
      g2d.fillRect(xPos, 0, 8, 160);
      this.add(label);
    }
  }

  /**
   * This method maps all the pitches to the piano keys.
   */
  private void importPitches() {
    // Importing pitches.
    ArrayList<PianoKey> keys = new ArrayList<>();
    whiteKeys = new ArrayList<>();
    blackKeys = new ArrayList<>();

    for (int i = 0; i <= 127; i++) {
      keys.add(new PianoKey(i));
    }

    // This method updates keys indicating whether or not they are pressed.
    updateBlackAndWhiteKeys(keys);

    // Distributing black keys and white keys.
    for (PianoKey key : keys) {
      if (!key.isBlackKey()) {
        whiteKeys.add(key);
      } else {
        blackKeys.add(key);
      }
    }

    for (PianoKey key : whiteKeys) {
      JLabel label = key.getLabel();
      label.addMouseListener(this.listener);
    }
    for (PianoKey key : blackKeys) {
      JLabel label = key.getLabel();
      label.addMouseListener(this.listener);
    }
  }

  /**
   * This method updates the pressing key situation of this specific time.
   *
   * @param pianoKeys the presses keys
   */
  private void updateBlackAndWhiteKeys(ArrayList<PianoKey> pianoKeys) {
    int currentTime = beat;
    HashSet<Integer> noteLevelsOnTheBeat = notesContainer.getNoteLevelOfTheBeat(currentTime);
    for (int number : noteLevelsOnTheBeat) {
      for (PianoKey pianoKey : pianoKeys) {
        if (pianoKey.getNoteLevel() == number) {
          pianoKey.pressCurrentKey();
        }
      }
    }
  }

  /**
   * This method sets the note container.
   * @param container The note container to be set.
   */
  public void setContainer(NotesContainer container) {
    this.notesContainer = container;
    updateUI();
  }

  /**
   * This method adds the MouseListener to the keys.
   * @param mouseListener The MouseListener to be added.
   */
  public void addMouseListener(MouseListener mouseListener) {
    this.listener = mouseListener;
  }
}
