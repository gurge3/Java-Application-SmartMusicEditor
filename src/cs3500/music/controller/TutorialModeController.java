package cs3500.music.controller;

import cs3500.music.model.MusicCreatorOperations;
import cs3500.music.view.CompositeView;
import cs3500.music.view.IView;

/**
 * This controller is designed specifically for tutorial mode of this music editor.
 */
public class TutorialModeController extends MusicCreatorController {
  /**
   * The default constructor.
   * @param view The view to be passed.
   * @param mco The model to be passed.
   */
  public TutorialModeController(IView view, MusicCreatorOperations mco) {
    this.view = view;
    this.mco = mco;
  }

  @Override
  public void start(int startingBeat) {
    view.setContainer(convertModelToContainer());
    view.setCurrentBeat(startingBeat);
    configureKeyboardListener();
    view.setMouseListener(new TutorialModeMouseHandler(view, mco, this));
    view.showNote();
  }
}
