package cs3500.marblesolitaire.view.hw04;


import java.io.IOException;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModel;

/**
 * class for the test driver for triangle solitare
 */
public class TestDriver {
  public static void main(String[] args) throws IOException {
    MarbleSolitaireModel model = new TriangleSolitaireModel();
    TriangleSolitaireTextView view = new TriangleSolitaireTextView();
    view.renderBoard();

  }
}