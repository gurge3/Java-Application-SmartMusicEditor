package cs3500.music.view;

import org.junit.Assert;
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
 * Tests for console view.
 */
public class ConsoleViewTest {
  //Variables for testing
  MusicCreatorOperations model2 = new MusicCreatorModel(80);
  MusicCreatorOperations model3 = new MusicCreatorModel(80);

  Note f1 = new Note(Pitch.FSharp, 5, 1, 3, 1, 1);
  Note f2 = new Note(Pitch.F, 3, 1, 4, 1, 1);
  Note c5 = new Note(Pitch.C, 5, 2, 3, 1, 1);
  Note c1 = new Note(Pitch.C, 3, 1, 0, 1, 1);

  private MusicCreatorOperations m1 = new MusicCreatorModel(80);
  private INote n1 = new Note(Pitch.C, 4, 2, 0, 1, 1);
  private INote n2 = new Note(Pitch.C, 2, 2, 1, 1, 1);
  private INote n3 = new Note(Pitch.C, 4, 3, 0, 1, 1);
  private INote n4 = new Note(Pitch.C, 2, 3, 1, 1, 1);
  private INote n5 = new Note(Pitch.G, 10, 2, 5, 1, 1);

  /**
   * This method adds notes into the music.
   *
   * @param m the music creator model
   */
  public void addNotes(MusicCreatorOperations m) {
    m.addNote(n1);
    m.addNote(n2);
    m.addNote(n3);
    m.addNote(n4);
    m.addNote(n5);
  }

  /**
   * This method removes notes from the music.
   *
   * @param m the music creator model
   */
  public void removeNotes(MusicCreatorOperations m) {
    m.deleteNote(n1);
    m.deleteNote(n2);
    m.deleteNote(n3);
    m.deleteNote(n4);
    m.deleteNote(n5);
  }

  //tests for empty music
  @Test
  public void testAddNoNotes() {
    Appendable out = new StringBuffer();
    IView v = new ConsoleView(out);
    MusicCreatorController controller = new MusicCreatorController(v, m1);
    controller.start(0);
    assertEquals("Current No Node in this music", out.toString());
  }

  //tests for adding notes
  @Test
  public void testAddNotes() {
    addNotes(m1);
    Appendable out = new StringBuffer();
    IView v = new ConsoleView(out);
    MusicCreatorController controller = new MusicCreatorController(v, m1);
    controller.start(0);
    assertEquals("    C2  C#2   D2  D#2   E2   F2  F#2   G2  G#2   A2  A#2   B2   C3 \n" +
            " 0  X                                                           X  \n" +
            " 1  X                                                           X  \n" +
            " 2  |                                                           |  \n" +
            " 3  |                                                           |  \n" +
            " 4                                                                 \n" +
            " 5                                     X                           \n" +
            " 6                                     |                           \n" +
            " 7                                     |                           \n" +
            " 8                                     |                           \n" +
            " 9                                     |                           \n" +
            "10                                     |                           \n" +
            "11                                     |                           \n" +
            "12                                     |                           \n" +
            "13                                     |                           \n" +
            "14                                     |                           \n",
            out.toString());
  }

  //tests for removing notes
  @Test
  public void testRemoveSomeNotes() {
    addNotes(m1);
    m1.deleteNote(n2);
    m1.deleteNote(n3);
    m1.deleteNote(n5);
    Appendable out = new StringBuffer();
    IView v = new ConsoleView(out);
    MusicCreatorController controller = new MusicCreatorController(v, m1);
    controller.start(0);
    assertEquals("   C2  C#2   D2  D#2   E2   F2  F#2   G2  G#2   A2  A#2   B2   C3 \n" +
            "0  X                                                              \n" +
            "1  |                                                           X  \n" +
            "2  |                                                           |  \n" +
            "3  |                                                              \n", out.toString());
  }

  //test play simultaneously
  @Test
  public void testPlaySimultaneously() {
    m1.addNote(c1);
    m1.addNote(c5);
    m1.playSimultaneous(m1);
    Appendable out = new StringBuffer();
    IView v = new ConsoleView(out);
    MusicCreatorController controller = new MusicCreatorController(v, m1);
    controller.start(0);
    Assert.assertEquals("   C1  C#1   D1  D#1   E1   F1  F#1   G1  G#1   A1  A#1   B1   C2 \n" +
                    "0  X                                                              \n" +
                    "1  |                                                              \n" +
                    "2  |                                                              \n" +
                    "3                                                              X  \n" +
                    "4                                                              |  \n" +
                    "5                                                              |  \n" +
                    "6                                                              |  \n" +
                    "7                                                              |  \n",
            out.toString());
    model2.addNote(f1);
    model2.addNote(f2);
    m1.playSimultaneous(model2);
    out = new StringBuffer();
    v = new ConsoleView(out);
    controller = new MusicCreatorController(v, m1);
    controller.start(0);

    Assert.assertEquals("   C1  C#1   D1  D#1   E1   F1  F#1   G1  G#1   A1  A#1   B1   C2 \n" +
                    "0  X                                                              \n" +
                    "1  |                                                              \n" +
                    "2  |                                                              \n" +
                    "3                                X                             X  \n" +
                    "4                           X    |                             |  \n" +
                    "5                           |    |                             |  \n" +
                    "6                           |    |                             |  \n" +
                    "7                                |                             |  \n",
            out.toString());
    Appendable out2 = new StringBuffer();
    IView v2 = new ConsoleView(out2);
    MusicCreatorController c2 = new MusicCreatorController(v2, model2);
    c2.start(0);
    Assert.assertEquals("   F1  F#1 \n" +
                    "0          \n" +
                    "1          \n" +
                    "2          \n" +
                    "3       X  \n" +
                    "4  X    |  \n" +
                    "5  |    |  \n" +
                    "6  |    |  \n" +
                    "7       |  \n",
            out2.toString());
  }

  //test play consecutively
  @Test
  public void testPlayConsecutively() {
    model2.addNote(f1);
    model2.addNote(f2);
    model3.addNote(c1);
    model3.addNote(c5);
    model3.playConsecutive(model2);
    Appendable out = new StringBuffer();
    IView v = new ConsoleView(out);
    MusicCreatorController controller = new MusicCreatorController(v, model3);
    controller.start(0);
    Assert.assertEquals("    C1  C#1   D1  D#1   E1   F1  F#1   G1  G#1   A1  A#1   B1   C2 \n" +
                    " 0  X                                                              \n" +
                    " 1  |                                                              \n" +
                    " 2  |                                                              \n" +
                    " 3                                                              X  \n" +
                    " 4                                                              |  \n" +
                    " 5                                                              |  \n" +
                    " 6                                                              |  \n" +
                    " 7                                                              |  \n" +
                    " 8                                                                 \n" +
                    " 9                                                                 \n" +
                    "10                                                                 \n" +
                    "11                                X                                \n" +
                    "12                           X    |                                \n" +
                    "13                           |    |                                \n" +
                    "14                           |    |                                \n" +
                    "15                                |                                \n",
            out.toString());
    Appendable out2 = new StringBuffer();
    IView v2 = new ConsoleView(out2);
    MusicCreatorController c2 = new MusicCreatorController(v2, model2);
    c2.start(0);
    Assert.assertEquals("   F1  F#1 \n" +
                    "0          \n" +
                    "1          \n" +
                    "2          \n" +
                    "3       X  \n" +
                    "4  X    |  \n" +
                    "5  |    |  \n" +
                    "6  |    |  \n" +
                    "7       |  \n",
            out2.toString());
  }

  @Test
  public void testRemoveAllNotes() {
    addNotes(m1);
    removeNotes(m1);
    Appendable out = new StringBuffer();
    IView v = new ConsoleView(out);
    MusicCreatorController controller = new MusicCreatorController(v, m1);
    controller.start(0);
    assertEquals("Current No Node in this music", out.toString());
  }

  //test to read the file
  @Test
  public void readFile() {
    MusicCreatorOperations mcm = null;
    File file = new File("mary-little-lamb.txt");
    try {
      FileReader reader = new FileReader(file);
      CompositionBuilder<MusicCreatorOperations> compositionBuilder = new CompositionBuilderImpl();
      mcm = MusicReader.parseFile(reader, compositionBuilder);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    Appendable out = new StringBuffer();
    IView v = new ConsoleView(out);
    MusicCreatorController mcc = new MusicCreatorController(v, mcm);
    mcc.start(0);
    assertEquals("    E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4  D#4   E4   F4  F#4   "
            + "G4 \n" +
            " 0                 X                                            X                 \n" +
            " 1                 |                                            |                 \n" +
            " 2                 |                                  X         |                 \n" +
            " 3                 |                                  |                           \n" +
            " 4                 |                        X         |                           \n" +
            " 5                 |                        |                                     \n" +
            " 6                 |                        |         X                           \n" +
            " 7                 |                                  |                           \n" +
            " 8                 X                                  |         X                 \n" +
            " 9                 |                                            |                 \n" +
            "10                 |                                            X                 \n" +
            "11                 |                                            |                 \n" +
            "12                 |                                            X                 \n" +
            "13                 |                                            |                 \n" +
            "14                 |                                            |                 \n" +
            "15                 |                                            |                 \n" +
            "16                 X                                  X                           \n" +
            "17                 |                                  |                           \n" +
            "18                 |                                  X                           \n" +
            "19                 |                                  |                           \n" +
            "20                 |                                  X                           \n" +
            "21                 |                                  |                           \n" +
            "22                 |                                  |                           \n" +
            "23                 |                                  |                           \n" +
            "24                 X                                  |         X                 \n" +
            "25                 |                                            |                 \n" +
            "26                 |                                            |              X  \n" +
            "27                                                                             |  \n" +
            "28                                                                             X  \n" +
            "29                                                                             |  \n" +
            "30                                                                             |  \n" +
            "31                                                                             |  \n" +
            "32                 X                                            X              |  \n" +
            "33                 |                                            |                 \n" +
            "34                 |                                  X         |                 \n" +
            "35                 |                                  |                           \n" +
            "36                 |                        X         |                           \n" +
            "37                 |                        |                                     \n" +
            "38                 |                        |         X                           \n" +
            "39                 |                                  |                           \n" +
            "40                 X                                  |         X                 \n" +
            "41                 |                                            |                 \n" +
            "42                 |                                            X                 \n" +
            "43                 |                                            |                 \n" +
            "44                 |                                            X                 \n" +
            "45                 |                                            |                 \n" +
            "46                 |                                            X                 \n" +
            "47                 |                                            |                 \n" +
            "48                 X                                  X         |                 \n" +
            "49                 |                                  |                           \n" +
            "50                 |                                  X                           \n" +
            "51                 |                                  |                           \n" +
            "52                 |                                  |         X                 \n" +
            "53                 |                                            |                 \n" +
            "54                 |                                  X         |                 \n" +
            "55                 |                                  |                           \n" +
            "56  X              |                        X         |                           \n" +
            "57  |                                       |                                     \n" +
            "58  |                                       |                                     \n" +
            "59  |                                       |                                     \n" +
            "60  |                                       |                                     \n" +
            "61  |                                       |                                     \n" +
            "62  |                                       |                                     \n" +
            "63  |                                       |                                     \n" +
            "64  |                                       |                                     "
            + "\n", out.toString());
  }

}
