package cs3500.music;

import java.io.FileNotFoundException;
import java.io.FileReader;

import cs3500.music.controller.IViewFactory;
import cs3500.music.controller.MusicCreatorController;
import cs3500.music.controller.TutorialModeController;
import cs3500.music.model.MusicCreatorOperations;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.CompositionBuilderImpl;
import cs3500.music.util.MusicReader;
import cs3500.music.view.IView;
import cs3500.music.view.TutorialView;

/**
 * The master MusicCreator Class.
 * It has a main function to take in the order.
 */
public final class MusicCreator {

  /**
   * This method provides an entering gateway for the program.
   * @param args The string arguments passed to this program.
   */
  public static void main(String[] args) {
    String fileName = args[0];
    String viewType = args[1];

    IView view;

    MusicCreatorController mcc;

    switch (viewType) {
      case "midi":
        view = IViewFactory.create(IViewFactory.IViewType.MIDI);
        break;
      case "gui":
        view = IViewFactory.create(IViewFactory.IViewType.GUI);
        break;
      case "console":
        view = IViewFactory.create(IViewFactory.IViewType.Console);
        break;
      case "composite":
        view = IViewFactory.create(IViewFactory.IViewType.Composite);
        break;
      case "tutorial":
        view = IViewFactory.create(IViewFactory.IViewType.Tutorial);
        break;
      default:
        throw new IllegalArgumentException("Not valid type!");
    }

    try {
      FileReader reader = new FileReader(fileName);
      CompositionBuilder<MusicCreatorOperations> compositionBuilder = new CompositionBuilderImpl();
      MusicCreatorOperations mcm = MusicReader.parseFile(reader, compositionBuilder);
      if (view instanceof TutorialView) {
        mcc = new TutorialModeController(view, mcm);
      } else {
        mcc = new MusicCreatorController(view, mcm);
      }
      mcc.start(0);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
