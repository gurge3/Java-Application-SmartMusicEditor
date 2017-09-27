package cs3500.music.controller;

import org.junit.Test;

import java.awt.event.KeyEvent;

import static junit.framework.TestCase.assertTrue;
/**
 * This test particularly test if a key event can be successfully made.
 */
public class MusicCreatorKeyHandlerTest extends MusicCreatorControllerTest {

  /**
   * This method sets up the testing environment.
   */
  private void setupTestingEnvironment() {
    initializeTestingEnvironment();
  }

  @Test
  public void testLeftKeyRunnable() {
    setupTestingEnvironment();
    int key = KeyEvent.VK_LEFT;
    assertTrue(handler.getKeyPressedMap().get(key) instanceof LeftRunnable);
  }

  @Test
  public void testRightKeyRunnable() {
    setupTestingEnvironment();
    int key = KeyEvent.VK_RIGHT;
    assertTrue(handler.getKeyPressedMap().get(key) instanceof RightRunnable);
  }

  @Test
  public void testHomeKeyRunnable() {
    setupTestingEnvironment();
    int key = KeyEvent.VK_HOME;
    assertTrue(handler.getKeyPressedMap().get(key) instanceof HomeRunnable);
  }

  @Test
  public void testEndKeyRunnable() {
    setupTestingEnvironment();
    int key = KeyEvent.VK_END;
    assertTrue(handler.getKeyPressedMap().get(key) instanceof EndRunnable);
  }

  @Test
  public void testStartKeyRunnable() {
    setupTestingEnvironment();
    int key = KeyEvent.VK_S;
    assertTrue(handler.getKeyPressedMap().get(key) instanceof StartRunnable);
  }
}