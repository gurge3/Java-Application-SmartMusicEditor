package cs3500.music.controller;

import cs3500.music.view.CompositeView;
import cs3500.music.view.ConsoleView;
import cs3500.music.view.GUIView;
import cs3500.music.view.IView;
import cs3500.music.view.MidiView;
import cs3500.music.view.TutorialView;

/**
 * This class creates three difference kinds of IView (Console, GUI or MIDI).
 * Console view is the text view of the music.
 * Gui view is the visual view of the music with a read line represents the ongoin beat.
 * Midi view is the audio view.
 */
public class IViewFactory {
  /**
   * Get the correspond view accroding to the given IViewType.
   *
   * @param type the given view type
   * @return the view of the music
   * @throws IllegalArgumentException if there is no such type
   */
  public static IView create(IViewType type) throws IllegalArgumentException {
    switch (type) {
      case Console:
        return new ConsoleView();
      case GUI:
        return new GUIView();
      case MIDI:
        return new MidiView();
      case Composite:
        return new CompositeView(new GUIView(), new MidiView());
      case Tutorial:
        return new TutorialView(new GUIView(), new MidiView());
      default:
        throw new IllegalArgumentException("Please verify your ViewType!");
    }
  }

  /**
   * Enum class for view type.
   */
  public enum IViewType {
    GUI, Console, MIDI, Composite, Tutorial
  }
}
