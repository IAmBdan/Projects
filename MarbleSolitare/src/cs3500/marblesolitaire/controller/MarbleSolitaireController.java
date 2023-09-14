package cs3500.marblesolitaire.controller;

public interface MarbleSolitaireController {

  /**
   * Plays a  game of Marble Solitaire
   *
   * @throws IllegalArgumentException if the controller is unable to successfully read input or
   * transmit output
   */
  void playGame() throws IllegalArgumentException;
}
