package cs3500.music.controller;

import java.awt.event.MouseEvent;

import javax.swing.*;

import cs3500.music.model.MusicCreatorOperations;
import cs3500.music.view.IView;
import cs3500.music.view.TutorialView;

/**
 * This key handler is used when the program is run under tutorial mode.
 */
public class TutorialModeMouseHandler extends MusicCreatorMouseHandler {

  /**
   * The default constructor.
   */
  public TutorialModeMouseHandler(IView view, MusicCreatorOperations mco, MusicCreatorController
          mcc) {
    super(view, mco, mcc);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    JLabel label = (JLabel) e.getSource();
    TutorialView tw = (TutorialView) view;
    tw.addPressKey(Integer.parseInt(label.getName()));
  }
}
