package cs3500.music.model;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Test class for PitchIndex.
 */
public class PitchIndexTest {
  @Test
  public void testGetIndexFromString() {
    assertEquals(91, PitchIndex.getIndexFromString("G8"));
    assertEquals(92, PitchIndex.getIndexFromString("G#8"));
  }

  @Test
  public void testGetStringFromIndex() {
    assertEquals("G8", PitchIndex.getStringFromIndex(91));
    assertEquals("G#8", PitchIndex.getStringFromIndex(92));
  }
}