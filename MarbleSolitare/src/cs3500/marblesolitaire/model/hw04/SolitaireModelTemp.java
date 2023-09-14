package cs3500.marblesolitaire.model.hw04;

import java.util.ArrayList;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;
public abstract class SolitaireModelTemp implements MarbleSolitaireModel {

  /**
   * Stores the positions of empty spots on the board.
   */
  protected MarbleSolitaireModelState.SlotState[][] board;

  /**
   * Represents the score of the current game.
   */
  protected int score;


  /**
   * Stores the positions of empty spots on the board.
   */
  protected ArrayList<int[]> empty;




  /**
   * Returns the slot at the specified row and column for public use by other classes.
   * @param row Row of the slot.
   * @param col Column of the slot.
   * @return the slotstate at the row and column
   */

  public MarbleSolitaireModelState.SlotState getSlotAt(int row, int col) {
    if(row >= board.length || row < 0 || col >= board.length || col < 0){
      return MarbleSolitaireModelState.SlotState.Invalid;
    }
    return board[row][col];
  }

  /**
   * Returns the number of marbles currently on the board
   * @return the score of the marble solitaire game
   */

  public int getScore() {
    return this.score;
  }

  /**
   * Abstract method that must be implemented in each class extending Solitaire Model Temp
   */
  @Override
  public abstract void move(int fromRow, int fromCol, int toRow, int toCol);


  /**
   * Returns the size of the game board.
   * @return the board length
   */
  public int getBoardSize() {
    return board.length;
  }


  /**
   * Returns the game board for public use by other classes.
   * @return the slotstate board
   */
  public MarbleSolitaireModelState.SlotState[][] getBoard() {
    return board;
  }



  /**
   * Returns a list of all possible moves on the game board.
   * @return the list of moves remaining
   */
  private ArrayList<int[][]> getMoves () {
    ArrayList<int[][]> moves = new ArrayList<int[][]>();

    for(int[] locs: empty) {
      if(getSlotAt(locs[0] + 2, locs[1]) == MarbleSolitaireModelState.SlotState.Marble
              && getSlotAt(locs[0] + 1, locs[1]) == MarbleSolitaireModelState.SlotState.Marble) {
        moves.add(new int[][]{{locs[0] + 2, locs[1]},{locs[0], locs[1]}});
      }
      else if(getSlotAt(locs[0] - 2, locs[1]) == MarbleSolitaireModelState.SlotState.Marble
              && getSlotAt(locs[0] - 1, locs[1]) == MarbleSolitaireModelState.SlotState.Marble) {
        moves.add(new int[][]{{locs[0] - 2, locs[1]},{locs[0], locs[1]}});
      }
      else if(getSlotAt(locs[0], locs[1] + 2) == MarbleSolitaireModelState.SlotState.Marble
              && getSlotAt(locs[0], locs[1] + 1) == MarbleSolitaireModelState.SlotState.Marble) {
        moves.add(new int[][]{{locs[0], locs[1] + 2},{locs[0], locs[1]}});
      }
      else if(getSlotAt(locs[0], locs[1] - 2) == MarbleSolitaireModelState.SlotState.Marble
              && getSlotAt(locs[0], locs[1] - 1) == MarbleSolitaireModelState.SlotState.Marble) {
        moves.add(new int[][]{{locs[0], locs[1] - 2},{locs[0], locs[1]}});
      }
      else if((this instanceof TriangleSolitaireModel) && getSlotAt(locs[0] - 2, locs[1] - 2) == MarbleSolitaireModelState.SlotState.Marble
              && getSlotAt(locs[0] - 1, locs[1] - 1) == MarbleSolitaireModelState.SlotState.Marble) {

        moves.add(new int[][]{{locs[0] - 2, locs[1] - 2},{locs[0], locs[1]}});
      }
      else if((this instanceof TriangleSolitaireModel) && getSlotAt(locs[0] + 2, locs[1] + 2) == MarbleSolitaireModelState.SlotState.Marble
              && getSlotAt(locs[0] + 1, locs[1] + 1) == MarbleSolitaireModelState.SlotState.Marble) {

        moves.add(new int[][]{{locs[0] - 2, locs[1] - 2},{locs[0], locs[1]}});
      }
    }
    return moves;
  }

  /**
   * Returns whether the game is over by checking the number of moves remaining
   * @return whether the game is over
   */
  @Override
  public boolean isGameOver() {
    return getMoves().isEmpty();
  }

  /**
   * Finishes the marble solitaire game by removing all marbles and leaving one in the center
   */
  public void finishGame() {
    for(int i = 0; i < board.length; i ++) {
      for(int j = 0; j < board.length; j ++) {
        if (board[i][j] == MarbleSolitaireModelState.SlotState.Marble) {
          board[i][j] = MarbleSolitaireModelState.SlotState.Empty;
        }
      }
    }
    board[board.length / 2][board.length / 2] = MarbleSolitaireModelState.SlotState.Marble;
  };

}
