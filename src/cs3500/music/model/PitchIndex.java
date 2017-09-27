package cs3500.music.model;

import java.util.HashMap;

/**
 * This helper class helps determine the pitch based on the given index.
 */
public class PitchIndex {

  /**
   * This method creates a HashMap linking the index with string.
   *
   * @return A HashMap linking index with string.
   */
  private static HashMap<String, Integer> getMap() {
    HashMap<String, Integer> map = new HashMap<>();
    int j = 0;
    for (int i = -1; i <= 9; i++) {
      for (Pitch pitch : Pitch.values()) {
        map.put(getMapHelper(pitch, i), j);
        j++;
      }
    }
    return map;
  }

  /**
   * This method creates a HashMap linking the string with index .
   *
   * @return A HashMap linking string with index.
   */
  private static HashMap<Integer, String> getMapReversed() {
    HashMap<Integer, String> map = new HashMap<>();
    int j = 0;
    for (int i = -1; i <= 9; i++) {
      for (Pitch pitch : Pitch.values()) {
        map.put(j, getMapHelper(pitch, i));
        j++;
      }
    }
    return map;
  }

  /**
   * This helper method helps to create a string containing a pitch and an index.
   *
   * @param pitch The pitch to be included in the string.
   * @param index The index to be included in the string.
   * @return A string containing a pitch and an index.
   */
  private static String getMapHelper(Pitch pitch, int index) {
    StringBuilder sb = new StringBuilder();
    sb.append(pitch.toString());
    sb.append(Integer.toString(index));
    return sb.toString();
  }

  /**
   * This method gets the corresponding index by the input string.
   *
   * @param input The string to be searched.
   * @return The index corresponding to the string.
   */
  public static int getIndexFromString(String input) {
    return getMap().get(input);
  }

  /**
   * This method gets the corresponding string by the input index.
   *
   * @param input The index to be searched.
   * @return The string corresponding to the index.
   */
  public static String getStringFromIndex(int input) {
    return getMapReversed().get(input);
  }
}
