package cs3500.music.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.JLabel;

import cs3500.music.model.MusicCreatorOperations;
import cs3500.music.model.Pitch;
import cs3500.music.model.PitchIndex;
import cs3500.music.view.IView;

/**
 * This class handles mouse event.
 * It takes a view which will set the current beat for the view.
 * It takes a model which get data from the model.
 * It takes a controller which will convert all the data from the model to a container
 * and then apply this container to the view classes.
 */
public class MusicCreatorMouseHandler extends MouseAdapter {

  protected IView view;

  protected MusicCreatorOperations mco;

  protected MusicCreatorController mcc;

  /**
   * The default constructor.
   */
  public MusicCreatorMouseHandler(IView view,
                                  MusicCreatorOperations mco, MusicCreatorController mcc) {
    this.view = view;
    this.mco = mco;
    this.mcc = mcc;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    JLabel label = (JLabel) e.getSource();
    Pitch pitch = clickHelperGetPitch(label);
    Integer octave = clickHelperGetOctave(label);
    mco.addNote(view.getCurrentBeat(), 1, 1, pitch, octave, 70);
    view.setContainer(mcc.convertModelToContainer());
    view.setCurrentBeat(view.getCurrentBeat() + 1);
  }

  /**
   * This method helps get pitch based on the incoming JLabel.
   * @param label The label passed.
   * @return The pitch parsed based on the label.
   */
  protected Pitch clickHelperGetPitch(JLabel label) {
    int noteLevel = Integer.parseInt(label.getName());
    String representation = PitchIndex.getStringFromIndex(noteLevel);
    String pitchInString;
    int length = representation.length();
    if (length == 2) {
      pitchInString = representation.substring(0, 1);
    } else if (length == 3) {
      pitchInString = representation.substring(0, 2);
    } else {
      throw new IllegalArgumentException("Not valid pitch!");
    }
    return Pitch.toPitch(pitchInString);
  }

  /**
   * This method helps get octave based on the imcoming JLabel.
   * @param label The label passed.
   * @return The octave parsed based on the label.
   */
  protected Integer clickHelperGetOctave(JLabel label) {
    int noteLevel = Integer.parseInt(label.getName());
    String representation = PitchIndex.getStringFromIndex(noteLevel);
    int length = representation.length();
    if (length == 2) {
      return Integer.parseInt(representation.substring(1, 2));
    } else if (length == 3) {
      return Integer.parseInt(representation.substring(2, 3));
    } else {
      throw new IllegalArgumentException("Not valid pitch!");
    }
  }
}
