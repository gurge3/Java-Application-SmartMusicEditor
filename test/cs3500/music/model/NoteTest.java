package cs3500.music.model;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for Note.
 */
public class NoteTest {
  Note note1 = new Note(Pitch.G, 20, 8, 3, 1, 1);
  Note note2 = new Note(Pitch.GSharp, 3, 2, 3, 1, 1);

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalBeatNumber() {
    Note noteWithIllegalBeat = new Note(Pitch.A, -23, 3, 3, 1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalNegativeOctaveNumber() {
    Note noteWithIllegalOctave = new Note(Pitch.A, 23, -32, 3, 1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalOctaveNumber() {
    Note noteWithIllegalOctave = new Note(Pitch.A, 23, 12, 3, 1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalStartTime() {
    Note noteWithIllegalStartTime = new Note(Pitch.A, 23, 3, -3, 1, 1);
  }

  @Test
  public void testGetPitch() {
    assertEquals(Pitch.G, note1.getPitch());
  }

  @Test
  public void testGetBeat() {
    assertEquals(20, note1.getBeat());
  }

  @Test
  public void testGetOctave() {
    assertEquals(8, note1.getOctave());
  }

  @Test
  public void testGetStartTime() {
    assertEquals(3, note1.getStartTime());
  }

  @Test
  public void testGetNoteLevel() {
    assertEquals(91, note1.getNoteLevel());
    assertEquals(20, note2.getNoteLevel());
  }

  @Test
  public void testToString() {
    assertEquals("G8", note1.toString());
    assertEquals("G#2", note2.toString());
  }

  @Test
  public void testEqual() {
    Note note3 = new Note(Pitch.G, 20, 8, 3, 1, 1);
    assertTrue(note3.equals(note1));
  }

  @Test
  public void testNotEqual() {
    assertFalse(note1.equals(note2));
  }

}