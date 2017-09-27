package cs3500.music.view;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import cs3500.music.controller.MusicCreatorController;
import cs3500.music.model.INote;
import cs3500.music.model.MusicCreatorModel;
import cs3500.music.model.MusicCreatorOperations;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.CompositionBuilderImpl;
import cs3500.music.util.MusicReader;

import static junit.framework.TestCase.assertEquals;

/**
 * This class tests the MidiView class.
 */
public class MidiViewTest {
  private MusicCreatorOperations mcm = new MusicCreatorModel(80);

  private INote m1 = new Note(Pitch.C, 4, 2, 0, 1, 1);
  private INote m2 = new Note(Pitch.C, 2, 2, 1, 2, 1);
  private INote m3 = new Note(Pitch.C, 4, 3, 0, 1, 1);
  private INote m4 = new Note(Pitch.C, 2, 3, 1, 2, 1);
  private INote m5 = new Note(Pitch.G, 10, 2, 5, 1, 1);


  private void addNotes(MusicCreatorOperations mcm) {
    mcm.addNote(m2);
    mcm.addNote(m3);
    mcm.addNote(m4);
    mcm.addNote(m5);
  }

  private void removeNotes(MusicCreatorOperations mcm) {
    mcm.deleteNote(m2);
    mcm.deleteNote(m3);
    mcm.deleteNote(m4);
    mcm.deleteNote(m5);
  }

  //tests for adding notes
  @Test
  public void testAddNoNotes() {
    StringBuilder out = new StringBuilder();
    MockSequencer sequencer = new MockSequencer(out);
    IView v = new MidiView(sequencer);
    MusicCreatorController controller = new MusicCreatorController(v, mcm);
    controller.start(0);
    assertEquals("", out.toString());
  }


  @Test
  public void testAddNotes() {
    addNotes(mcm);
    StringBuilder out = new StringBuilder();
    MockSequencer sequencer = new MockSequencer(out);
    IView v = new MidiView(sequencer);
    MusicCreatorController controller = new MusicCreatorController(v, mcm);
    controller.start(0);
    assertEquals("Command: 144 Note level: 48 volume: 1\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 144 Note level: 36 volume: 2\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 144 Note level: 48 volume: 2\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 128 Note level: 36 volume: 2\n"
            + "Command: 128 Note level: 48 volume: 2\n"
            + "Command: 128 Note level: 48 volume: 1\n"
            + "Command: 144 Note level: 43 volume: 1\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 128 Note level: 43 volume: 1\n", out.toString());

  }

  //tests for removing notes
  @Test
  public void testRemoveSomeNotes() {
    addNotes(mcm);
    mcm.deleteNote(m2);
    mcm.deleteNote(m3);
    mcm.deleteNote(m5);
    StringBuilder out = new StringBuilder();
    MockSequencer sequencer = new MockSequencer(out);
    IView v = new MidiView(sequencer);
    MusicCreatorController controller = new MusicCreatorController(v, mcm);
    controller.start(0);
    assertEquals("Command: 144 Note level: 48 volume: 2\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 128 Note level: 48 volume: 2\n", out.toString());
  }

  @Test
  public void testRemoveAllNotes() {
    addNotes(mcm);
    removeNotes(mcm);
    StringBuilder out = new StringBuilder();
    MockSequencer sequencer = new MockSequencer(out);
    IView v = new MidiView(sequencer);
    MusicCreatorController controller = new MusicCreatorController(v, mcm);
    controller.start(0);
    assertEquals("", out.toString());
  }

  //test to read the file
  //this test wont pass if run the whole view test
  //because hash map is not in order, so the result will change
  @Test
  public void readFile() {
    MusicCreatorOperations mcm2 = null;
    File file = new File("mary-little-lamb.txt");
    try {
      FileReader reader = new FileReader(file);
      CompositionBuilder<MusicCreatorOperations> compositionBuilder = new CompositionBuilderImpl();
      mcm2 = MusicReader.parseFile(reader, compositionBuilder);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    StringBuilder out = new StringBuilder();
    MockSequencer sequencer = new MockSequencer(out);
    IView v = new MidiView(sequencer);
    MusicCreatorController controller = new MusicCreatorController(v, mcm2);
    controller.start(0);
    String expectedResult = "Command: 144 Note level: 64 volume: 72\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 144 Note level: 55 volume: 70\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 144 Note level: 62 volume: 72\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 128 Note level: 64 volume: 72\n"
            + "Command: 144 Note level: 60 volume: 71\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 128 Note level: 62 volume: 72\n"
            + "Command: 144 Note level: 62 volume: 79\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 128 Note level: 60 volume: 71\n"
            + "Command: 128 Note level: 55 volume: 70\n"
            + "Command: 144 Note level: 64 volume: 85\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 144 Note level: 55 volume: 79\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 128 Note level: 62 volume: 79\n"
            + "Command: 144 Note level: 64 volume: 78\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 128 Note level: 64 volume: 85\n"
            + "Command: 144 Note level: 64 volume: 74\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 128 Note level: 64 volume: 78\n"
            + "Command: 128 Note level: 55 volume: 79\n"
            + "Command: 128 Note level: 64 volume: 74\n"
            + "Command: 144 Note level: 55 volume: 77\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 144 Note level: 62 volume: 75\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 144 Note level: 62 volume: 77\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 128 Note level: 62 volume: 75\n"
            + "Command: 144 Note level: 62 volume: 75\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 128 Note level: 62 volume: 77\n"
            + "Command: 144 Note level: 64 volume: 82\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 144 Note level: 55 volume: 79\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 128 Note level: 55 volume: 77\n"
            + "Command: 128 Note level: 62 volume: 75\n"
            + "Command: 144 Note level: 67 volume: 84\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 128 Note level: 64 volume: 82\n"
            + "Command: 128 Note level: 55 volume: 79\n"
            + "Command: 144 Note level: 67 volume: 75\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 128 Note level: 67 volume: 84\n"
            + "Command: 144 Note level: 64 volume: 73\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 144 Note level: 55 volume: 78\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 128 Note level: 67 volume: 75\n"
            + "Command: 144 Note level: 62 volume: 69\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 128 Note level: 64 volume: 73\n"
            + "Command: 144 Note level: 60 volume: 71\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 128 Note level: 62 volume: 69\n"
            + "Command: 144 Note level: 62 volume: 80\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 128 Note level: 60 volume: 71\n"
            + "Command: 144 Note level: 55 volume: 79\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 144 Note level: 64 volume: 84\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 128 Note level: 55 volume: 78\n"
            + "Command: 128 Note level: 62 volume: 80\n"
            + "Command: 144 Note level: 64 volume: 76\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 128 Note level: 64 volume: 84\n"
            + "Command: 144 Note level: 64 volume: 74\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 128 Note level: 64 volume: 76\n"
            + "Command: 144 Note level: 64 volume: 77\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 128 Note level: 64 volume: 74\n"
            + "Command: 144 Note level: 62 volume: 75\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 144 Note level: 55 volume: 78\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 128 Note level: 55 volume: 79\n"
            + "Command: 128 Note level: 64 volume: 77\n"
            + "Command: 144 Note level: 62 volume: 74\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 128 Note level: 62 volume: 75\n"
            + "Command: 144 Note level: 64 volume: 81\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 128 Note level: 62 volume: 74\n"
            + "Command: 144 Note level: 62 volume: 70\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 128 Note level: 64 volume: 81\n"
            + "Command: 144 Note level: 52 volume: 72\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 144 Note level: 60 volume: 73\n"
            + "Command: 192 Instrument: 1\n"
            + "Command: 128 Note level: 55 volume: 78\n"
            + "Command: 128 Note level: 62 volume: 70\n"
            + "Command: 128 Note level: 52 volume: 72\n"
            + "Command: 128 Note level: 60 volume: 73\n";
    assertEquals(expectedResult, out.toString());
  }
}

