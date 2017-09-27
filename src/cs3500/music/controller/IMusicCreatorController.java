package cs3500.music.controller;

/**
 * This is the interface for the controller.
 * This interface is for initiating the view of the music model,
 * based on the starting beat.
 */
public interface IMusicCreatorController {
  /**
   * This method initiates the View.
   *
   * @param startingBeat The stating point to be played.
   */
  void start(int startingBeat);
}
