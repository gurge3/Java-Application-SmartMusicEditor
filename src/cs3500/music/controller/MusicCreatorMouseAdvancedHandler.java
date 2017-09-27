package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.*;

import cs3500.music.model.MusicCreatorOperations;
import cs3500.music.model.Pitch;
import cs3500.music.view.IView;

/**
 * An advanced mouse handler handles long press mouse operation.
 */
public class MusicCreatorMouseAdvancedHandler extends MusicCreatorMouseHandler {

  private int originalBeat;

  private int finalBeat;

  private boolean isClicked = false;

  private Thread pressThread;

  /**
   * The default constructor.
   */
  public MusicCreatorMouseAdvancedHandler(IView view, MusicCreatorOperations mco,
                                          MusicCreatorController mcc) {
    super(view, mco, mcc);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    // Do nothing.
  }

  @Override
  public void mousePressed(MouseEvent e) {
    isClicked = true;
    originalBeat = view.getCurrentBeat();
    Runnable pressRunnable = () -> {
      while (isClicked) {
        view.setCurrentBeat(view.getCurrentBeat() + 1);
        finalBeat = view.getCurrentBeat();
        try {
          Thread.sleep(mco.getTempo() / 1055);
        } catch (InterruptedException ie) {
          ie.printStackTrace();
        }
      }
    };
    pressThread = new Thread(pressRunnable);
    pressThread.start();
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    JLabel label = (JLabel) e.getSource();
    Pitch pitch = clickHelperGetPitch(label);
    Integer octave = clickHelperGetOctave(label);
    isClicked = false;
    int interval = finalBeat - originalBeat;
    mco.addNote(view.getCurrentBeat() - interval, interval, 1, pitch, octave, 70);
    view.setContainer(mcc.convertModelToContainer());
  }

}
