package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

/**
 * This method handles the key events(only for visual view)
 * Users can move the beat bar forward or backwards.
 */
public class MusicCreatorKeyHandler implements KeyListener {


  protected Map<Integer, Runnable> keyPressedMap;

  @Override
  public void keyTyped(KeyEvent e) {
    // Do nothing.
  }


  @Override
  public void keyPressed(KeyEvent e) {
    if (keyPressedMap.containsKey(e.getKeyCode())) {
      keyPressedMap.get(e.getKeyCode()).run();
    }
  }


  @Override
  public void keyReleased(KeyEvent e) {
    // Do nothing.
  }

  /**
   * This method sets the key handler map.
   * @param map The key handler map to be set.
   */
  public void setKeyPressedMap(Map<Integer, Runnable> map) {
    this.keyPressedMap = map;
  }

  /**
   * This getter is particularly used for testing.
   * @return The field of map.
   */
  public Map<Integer, Runnable> getKeyPressedMap() {
    return keyPressedMap;
  }
}
