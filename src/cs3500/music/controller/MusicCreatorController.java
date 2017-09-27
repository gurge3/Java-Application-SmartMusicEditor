package cs3500.music.controller;

import java.awt.event.KeyEvent;

import java.security.Key;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


import cs3500.music.model.INote;
import cs3500.music.model.MusicCreatorOperations;
import cs3500.music.view.CompositeView;
import cs3500.music.view.IView;

/**
 * This controller controls the view of the music model.
 * There are three kinds of views:text, visual and audio.
 * There is also a current beat in the controller to keep tracking the ongoing beat.
 */
public class MusicCreatorController implements IMusicCreatorController {
  //fields of the controller, changed to protected so that these variables
  //can be accessed through inherited test class.
  protected IView view;

  protected MusicCreatorOperations mco;

  protected MusicCreatorKeyHandler handler;

  /**
   * Constructs the controller.
   *
   * @param view the type of view choose.
   * @param mco  the music creator model
   */
  public MusicCreatorController(IView view, MusicCreatorOperations mco) {
    this.view = view;
    this.mco = mco;
  }

  /**
   * The second constructor used for testing.
   */
  public MusicCreatorController() {
    // Only used for testing.
  }


  /**
   * This method initiates the View.
   *
   * @param startingBeat The stating point to be played.
   */
  public void start(int startingBeat) {
    view.setContainer(convertModelToContainer());
    view.setCurrentBeat(startingBeat);
    configureKeyboardListener();
    view.setMouseListener(new MusicCreatorMouseAdvancedHandler(view, mco, this));
    // Special case.
    if (view instanceof CompositeView) {
      return;
    }
    view.showNote();
  }

  /**
   * This method sets all notes in the model to a container.
   *
   * @return A container contains all the notes only accessible.
   */
  protected NotesContainer convertModelToContainer() {
    if (mco.getSize() == 0) {
      return new NotesContainer(mco.getTempo(), mco.getNotesArray(), mco.getHelper(),
              0, 0,0, new HashMap<>());
    }
    return new NotesContainer(mco.getTempo(), mco.getNotesArray(), mco.getHelper(),
            mco.getNoteLevelDifference(),
            mco.getTheLowestNote().getNoteLevel(),
            mco.getTheHighestNote().getNoteLevel(), mco.getRepeatSymbol());
  }

  /**
   * This method configures the KeyboardListener.
   */
  protected void configureKeyboardListener() {
    Map<Integer, Runnable> keyPressed = new HashMap<>();
    keyPressed.put(KeyEvent.VK_LEFT, new LeftRunnable());
    keyPressed.put(KeyEvent.VK_RIGHT, new RightRunnable());
    keyPressed.put(KeyEvent.VK_S, new StartRunnable());
    keyPressed.put(KeyEvent.VK_HOME, new HomeRunnable());
    keyPressed.put(KeyEvent.VK_END, new EndRunnable());
    keyPressed.put(KeyEvent.VK_PAGE_DOWN, new PageDownRunnable());
    keyPressed.put(KeyEvent.VK_PAGE_UP, new PageUpRunnable());
    handler = new MusicCreatorKeyHandler();
    handler.setKeyPressedMap(keyPressed);
    view.setKeyListener(handler);
  }


  // A class handles left key operation.
  class LeftRunnable implements Runnable {
    @Override
    public void run() {
      int currentBeat = view.getCurrentBeat();
      if (currentBeat != 0) {
        currentBeat--;
        view.setCurrentBeat(currentBeat);
      }
    }
  }

  // A class handles right key operation.
  class RightRunnable implements Runnable {
    @Override
    public void run() {
      int currentBeat = view.getCurrentBeat();
      if (currentBeat != mco.getLatestBeat() + 1) {
        currentBeat++;
        view.setCurrentBeat(currentBeat);
      }
    }
  }

  // A class handles "s" key operation.
  class StartRunnable implements Runnable {
    @Override
    public void run() {
      view.controlMusic();
    }
  }

  // A class handles "end" key operation.
  class EndRunnable implements Runnable {
    @Override
    public void run() {
      view.setCurrentBeat(mco.getLatestBeat() + 1);
    }
  }

  // A class handles "home" key operation.
  class HomeRunnable implements Runnable {
    @Override
    public void run() {
      view.setCurrentBeat(0);
      //view.showNote();
    }
  }

  // A class handles "Page Down" key operation
  class PageDownRunnable implements Runnable {
    @Override
    public void run() {
      view.decreaseTempo();
    }
  }

  // A class handles "Page Up" key operation
  class PageUpRunnable implements Runnable {
    @Override
    public void run() {
      view.increaseTempo();
    }
  }


}
