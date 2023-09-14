package cs3500.marblesolitaire.view;

import java.io.IOException;

import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;


public class MarbleSolitaireTextView implements MarbleSolitaireView {

  private final MarbleSolitaireModel model;
  private final Appendable ap;
  public MarbleSolitaireTextView() {
    model = new EnglishSolitaireModel();
    ap = System.out;
  }

  /**
   * constructor for marblesolitare
   * @param model
   * @param ap
   * @throws IllegalStateException if model or ap are null
   */
  public MarbleSolitaireTextView(MarbleSolitaireModel model, Appendable ap) throws IllegalStateException{
    if(model.equals(null) || ap.equals(null)) {
      throw new IllegalStateException("Both Model and Appendable must be initialized");
    }
    this.model = model;
    this.ap = ap;
  }

  /**
   * Visualizes the marble soliatire board as a string
   * @param model the model of the english solitaire board
   * @return the String visualization fo the board provided
   */
  public String toString(MarbleSolitaireModel model){

    String view = "";
    view = view.concat("   ");
    for(int i = 0; i < model.getBoardSize(); i ++) {
      view = view.concat(" " + i);
    }
    view = view.concat("\n");
    for(int i = 0; i < model.getBoardSize(); i ++) {
      view = view.concat(i + "  ");
      for(int j = 0; j < model.getBoardSize(); j ++) {
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


  @Override
  public void renderBoard() throws IOException {
    try {
      ap.append(toString());
    }
    catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

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
