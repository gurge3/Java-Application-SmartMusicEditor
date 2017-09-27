package cs3500.music.model;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Test class for Pitch.
 */
public class PitchTest {
  @Test
  public void testToString() {
    assertEquals("C", Pitch.C.toString());
    assertEquals("C#", Pitch.CSharp.toString());
  }

  @Test
  public void testGetNumber() {
    assertEquals(0, Pitch.C.getNumber());
    assertEquals(8, Pitch.GSharp.getNumber());
  }
}