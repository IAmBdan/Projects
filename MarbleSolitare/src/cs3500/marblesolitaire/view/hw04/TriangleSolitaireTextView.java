package cs3500.marblesolitaire.view.hw04;

import java.io.IOException;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;
import cs3500.marblesolitaire.model.hw04.SolitaireModelTemp;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireView;

/**
 * class for triangle board
 */
public class TriangleSolitaireTextView implements MarbleSolitaireView {

  private TriangleSolitaireModel model;
  private final Appendable ap;
  public TriangleSolitaireTextView() {
    model = new TriangleSolitaireModel();
    ap = System.out;
  }

  /**
   * constructorfor triangle text view
   * @param model
   * @param ap
   * @throws IllegalStateException if the model or ap r null
   */
  public TriangleSolitaireTextView(SolitaireModelTemp model, Appendable ap) throws IllegalStateException{
    if(model.equals(null) || ap.equals(null)) {
      throw new IllegalStateException("Both Model and Appendable must be initialized");
    }
    this.model = (TriangleSolitaireModel) model;
    this.ap = ap;
  }

  /**
   * Visualizes the marble soliatire board as a string
   * @param model the model of the english solitaire board
   * @return the String visualization fo the board provided
   */
  public String toString(MarbleSolitaireModel model){

    String view = "";

    for(int i = 0; i < model.getBoardSize(); i ++) {
      for(int k = 0; k < (model.getBoardSize() - i - 1); k++) {
        view = view.concat(" ");
      }

      for(int j = 0; j < model.getBoardSize(); j++) {
        if(model.getSlotAt(i,j).equals(MarbleSolitaireModelState.SlotState.Marble)) {
          view = view.concat(" o");
        }
        else if(model.getSlotAt(i,j).equals(MarbleSolitaireModelState.SlotState.Invalid)) {
          view = view.concat("  ");
        }
        else if(model.getSlotAt(i,j).equals(MarbleSolitaireModelState.SlotState.Empty)) {
          view = view.concat(" _");
        }

      }
      if (!(i == (model.getBoardSize() - 1))){
        view = view.concat("\n");
      }
    }
    return view;
  }

  /**
   * Visualizes the marble soliatire board as a string, overloads other toString() for ease of code
   * @return the String visualization for the local board
   */
  public String toString() {
    return toString(this.model);
  }

  /**
   * override for renderboard method in the triangle class
   * @throws IOException
   */
  @Override
  public void renderBoard() throws IOException {
    try {
      ap.append(toString());
    }
    catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  /**
   * override method for rendermessage
   * @param message
   * @throws IOException
   */
  @Override
  public void renderMessage(String message) throws IOException {
    try {
      ap.append(message);
    }
    catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

}

