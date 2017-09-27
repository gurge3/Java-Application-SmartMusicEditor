package cs3500.music.view;

import java.awt.Container;
import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;

import cs3500.music.controller.NotesContainer;


/**
 * This class implements the GUI view of the music creator.
 * A gui is to convert and display the music with a piano key board.
 * It has a notes container to get the information from the model.
 * It has pitch graph to represent the note.
 * It has piano graph to represent the piano key board.
 * It has a current beat to keep tracking the beat.
 */
public class GUIView extends JFrame implements IView {

  //fields of the class
  private static final int APPLICATION_WIDTH = 1400;
  private static final int APPLICATION_HEIGHT = 800;
  private NotesContainer notesContainer;
  private PitchGraph pitchPanel;
  private PianoGraph keyboardPanel;
  private int currentBeat;
  private JScrollPane jScrollPane;
  private int counter;

  /**
   * Constructs the JFrame view.
   */
  public GUIView() {
    super();
  }

  /**
   * Initializing the gui view.
   */
  protected void initGUI() {
    this.setTitle("Music Editor");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Disable resizable.
    this.setResizable(false);

    // Setting default JFrame background color
    Container container = getContentPane();
    container.setBackground(Color.WHITE);

    // Pitches Panel
    pitchPanel = new PitchGraph(notesContainer, currentBeat);
    pitchPanel.setBackground(Color.WHITE);
    this.add(pitchPanel, BorderLayout.NORTH);

    jScrollPane = new JScrollPane(pitchPanel);
    this.getContentPane().add(jScrollPane);


    // Keyboard Panel
    keyboardPanel = new PianoGraph(notesContainer, currentBeat);
    keyboardPanel.setPreferredSize(new Dimension(APPLICATION_WIDTH, 400));
    keyboardPanel.setBackground(Color.GRAY);
    this.add(keyboardPanel, BorderLayout.SOUTH);

    this.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);

    setFocusable(true);
    requestFocusInWindow();

    counter = 1;
  }

  @Override
  public void showNote() {
    this.setVisible(true);
  }


  @Override
  public void setCurrentBeat(int beat) {
    this.currentBeat = beat;
    if (beat == 0) {
      this.counter = 1;
      JScrollBar horizontalScrollBar = jScrollPane.getHorizontalScrollBar();
      horizontalScrollBar.setValue(0);
    }
    pitchPanel.setCurrentBeat(beat);
    keyboardPanel.setCurrentBeat(beat);
    configureScrollbar();
  }

  /**
   * This method configures the scrollbar with auto-scrolling feature.
   */
  private void configureScrollbar() {
    int currentBarPosition = pitchPanel.getRedLineXPosition();
    if (APPLICATION_WIDTH * counter - currentBarPosition <= 18) {
      JScrollBar horizontalScrollBar = jScrollPane.getHorizontalScrollBar();
      horizontalScrollBar.setMaximum((int) pitchPanel.getPreferredSize().getWidth());
      horizontalScrollBar.setValue(currentBarPosition);
      counter++;
    }
  }

  @Override
  public int getCurrentBeat() {
    int beat = currentBeat;
    return beat;
  }

  @Override
  public void controlMusic() {
    // Do nothing.
  }

  @Override
  public void setContainer(NotesContainer container) {
    if (pitchPanel == null || keyboardPanel == null) {
      this.notesContainer = container;
      initGUI();
    } else {
      pitchPanel.setContainer(container);
      keyboardPanel.setContainer(container);
      JScrollBar verticalBar = jScrollPane.getVerticalScrollBar();
      verticalBar.setMaximum((int) pitchPanel.getPreferredSize().getHeight());
      verticalBar.updateUI();
    }
  }

  @Override
  public void close() {
    this.setVisible(false);
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
  public void setKeyListener(KeyListener listener) {
    addKeyListener(listener);
  }

  @Override
  public void setMouseListener(MouseListener listener) {
    keyboardPanel.addMouseListener(listener);
  }
}
