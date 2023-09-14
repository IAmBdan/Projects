package cs3500.marblesolitaire.controller;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireGuiView;
/**
 * class for the gui controller
 */
public class GUIController implements ControllerFeatures{

  private MarbleSolitaireModel model;
  private MarbleSolitaireGuiView view;
  private int fromRow;
  private int fromCol;
  private int toRow;
  private int toCol;

  /**
   * gui controller constructor
   * @param model
   * @param view
   */
  public GUIController(MarbleSolitaireModel model, MarbleSolitaireGuiView view) {
    this.model = model;
    this.view = view;

    this.view.refresh();

    fromRow = fromCol = toRow = toCol = -1;
  }

  /**
   * overrides the input method
   * @param row
   * @param col
   */
  @Override
  public void input(int row, int col) {
    if(row < 0 || col < 0) {
      throw new IllegalArgumentException("Input cannot have negative value");
    }
    if(!model.isGameOver()) {
      if(fromRow == -1 || fromCol == -1) {
        fromRow = row;
        fromCol = col;
      }
      else{
        toRow = row;
        toCol = col;
        try{
          model.move(fromRow,fromCol,toRow,toCol);
          view.renderMessage("Successful move " + fromRow + " " + fromCol +
                  " " + toRow + " " + toCol);
          if(model.isGameOver()){
            view.renderMessage("\nGame Over! Play Again!");
          }
          view.refresh();
          fromRow = fromCol = toRow = toCol = -1;
        }
        catch(Exception e){
          fromRow = row;
          fromCol = col;
          toRow = toCol = -1;
        }
      }
    }

  }
}
