package cs3500.music.controller;

import org.junit.Test;

import java.awt.event.InputEvent;
import java.awt.Robot;
import java.awt.AWTException;

import cs3500.music.model.INote;
import cs3500.music.model.MusicCreatorModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.view.CompositeView;
import cs3500.music.view.GUIView;
import cs3500.music.view.MidiView;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;


/**
 * This class tests all the mouse and keyboard events handled by the controller.
 */
public class MusicCreatorControllerTest extends MusicCreatorController {

  /**
   * This constructor takes in nothing.
   */
  public MusicCreatorControllerTest() {
    initializeTestingEnvironment();
  }

  /**
   * This method initializes the testing environment.
   */
  protected void initializeTestingEnvironment() {
    GUIView jfw = new GUIView();
    MidiView midiView = new MidiView();
    CompositeView compositeView = new CompositeView(jfw, midiView);
    view = compositeView;
    mco = new MusicCreatorModel(1000000);
    mco.addNote(1, 20, 1, Pitch.A, 3, 3);
    start(0);
  }

  @Test
  public void testLeftRunnable() {
    int pastBeat = view.getCurrentBeat();
    LeftRunnable runnable = new LeftRunnable();
    runnable.run();
    int currentBeat = view.getCurrentBeat();
    assertEquals(pastBeat, currentBeat);
  }




  @Test
  public void testRightRunnable() {
    int pastBeat = view.getCurrentBeat();
    RightRunnable runnable = new RightRunnable();
    runnable.run();
    int currentBeat = view.getCurrentBeat();
    assertEquals(pastBeat + 1, currentBeat);
  }

  @Test
  public void testHomeRunnable() {
    //test end than home
    EndRunnable end = new EndRunnable();
    end.run();
    HomeRunnable home = new HomeRunnable();
    home.run();
    assertEquals(0, view.getCurrentBeat());
    //test right than home
    RightRunnable right = new RightRunnable();
    right.run();
    home.run();
    assertEquals(0, view.getCurrentBeat());
  }

  @Test
  public void testEndRunnable() {
    int expectedLatestBeat = mco.getLatestBeat() + 1;
    EndRunnable end = new EndRunnable();
    end.run();
    assertEquals(expectedLatestBeat, view.getCurrentBeat());
    HomeRunnable home = new HomeRunnable();
    home.run();
    end.run();
    assertEquals(expectedLatestBeat, view.getCurrentBeat());
  }

  /**
   * This method first starts the music for some seconds, than stop the music.
   * Thus we can test if the music played an interval we expected.
   */
  @Test
  public void testStartRunnable() {
    int pastBeat = view.getCurrentBeat();
    StartRunnable runnable = new StartRunnable();
    runnable.run();
    // Sleep 3 seconds. So the music should play 3 seconds.
    try {
      Thread.sleep(3300);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    runnable.run();
    int currentBeat = view.getCurrentBeat();
    assertEquals(pastBeat + 3, currentBeat);
  }

  /**
   * This method tests if a mouse event can be made and whether
   * this mouse event behaves correctly.
   */
  @Test
  public void testMouseEvent() {
    try {
      int pastNumberOfNote = mco.getSize();
      // Adding a robot to simulate mouse clicking behavior.
      Robot robot = new Robot();
      int leftButton = InputEvent.BUTTON1_DOWN_MASK;
      INote f3 = new Note(Pitch.F,1,3,0,70,1);
      Thread.sleep(1000);
      robot.mouseMove(595, 600);
      robot.mousePress(leftButton);
      robot.mouseRelease(leftButton);
      Thread.sleep(1000);
      int currentNumberOfNote = mco.getSize();
      assertEquals(pastNumberOfNote + 1, currentNumberOfNote);
      assertTrue(mco.searchNote(f3));
    } catch (AWTException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}