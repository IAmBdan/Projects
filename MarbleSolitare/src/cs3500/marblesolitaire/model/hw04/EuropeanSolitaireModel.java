package cs3500.marblesolitaire.model.hw04;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.abs;


public class EuropeanSolitaireModel extends SolitaireModelTemp {



  /**
   * Initializes a new game board with default parameters.
   */
  public EuropeanSolitaireModel() {
    initboard(3, 3, 3);
  }

  /**
   * Initializes a new game board with a specified empty slot position.
   * @param srow Row of the empty slot.
   * @param scol Column of the empty slot.
   */
  public EuropeanSolitaireModel(int srow, int scol) {
    initboard(3, srow, scol);
  }


  /**
   * Initializes a new game board with a specified arm thickness and default empty slot position.
   * @param arm Arm thickness of the board.
   */
  public EuropeanSolitaireModel(int arm) {
    initboard(arm, (arm - 1) + (arm/2), (arm - 1) + (arm/2));
  }


  /**
   * Initializes a new game board with specified arm thickness and empty slot position.
   * @param arm Arm thickness of the board.
   * @param srow Row of the empty slot.
   * @param scol Column of the empty slot.
   */
  public EuropeanSolitaireModel(int arm, int srow, int scol) {
    initboard(arm, srow, scol);
  }



  /**
   * Initializes the game board with the specified arm length and the initial empty slot.
   * The method sets up the state of each slot on the board (invalid, empty, or marble)
   * based on the rules of the game. It also calculates and sets the initial score of the game.
   *
   * @param arm the arm length of the board. The arm length must be greater than 0.
   * @param srow the row number of the initial empty slot
   * @param scol the column number of the initial empty slot
   * @throws IllegalArgumentException if the arm length is less than 1 or the initial empty slot
   *         is outside the bounds of the board
   */
  private void initboard(int arm, int srow, int scol){
    if(arm < 1) {
      throw new IllegalArgumentException("Arm length must be greater than 0");
    }

    this.board = new SlotState[arm + (2 *  (arm - 1))][arm + (2 *  (arm - 1))];

    for (SlotState[] slotStates : board) {
      Arrays.fill(slotStates, SlotState.Marble);
    }
    for(int i = 0; i < board.length; i++) {
      for(int j = 0; j < board.length; j++) {

        if((i < (2 * arm) - 1) && (i > (arm - 2))){
          continue;
        }
        else if ((j < (2 * arm) - 1) && (j > (arm - 2))) {
          continue;
        }
        else if ((j < (2 * arm) - 1 + i) && (j > (arm - 2) - i)) {
          continue;
        }

        board[i][j] = SlotState.Invalid;
        board[board.length - 1 - i][j] = SlotState.Invalid;
      }


    }



    if((srow >= board.length || srow < 0) || (scol >= board[0].length || scol < 0)) {
      throw new IllegalArgumentException();
    }

    board[srow][scol] = SlotState.Empty;
    empty = new ArrayList<int[]>();
    empty.add(new int[]{srow, scol});

    for(SlotState[] row :board) {
      for(SlotState state : row) {
        if(state.equals(SlotState.Marble)) {
          this.score ++;
        }
      }
    }
  }




  /**
   * Moves a marble from one slot to another on the game board. The method checks if the move is
   * valid based on the rules of the game. The marble must be moved orthogonally over an adjacent
   * marble to an empty slot, and the marble being jumped over is then removed from the game.
   * The score is also updated every time a valid move is made.
   *
   * @param fromRow the row number of the slot from which the marble is being moved
   * @param fromCol the column number of the slot from which the marble is being moved
   * @param toRow the row number of the slot to which the marble is being moved
   * @param toCol the column number of the slot to which the marble is being moved
   * @throws IllegalArgumentException if any of the following conditions are true:
   * 1) the from-slot is empty or invalid
   * 2) the to-slot is not empty
   * 3) the move is not made orthogonally
   * 4) the move is not over exactly one marble
   * 5) the move is not to an empty slot two positions away
   */
  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) {
    if(getSlotAt(fromRow, fromCol) == SlotState.Invalid || getSlotAt(fromRow, fromCol) == SlotState.Empty) {
      throw new IllegalArgumentException("Can only move a spot with a marble");
    }
    else if(getSlotAt(toRow, toCol) == SlotState.Invalid || getSlotAt(toRow, toCol) == SlotState.Marble) {
      throw new IllegalArgumentException("Can only move a marble to an empty spot");
    }
    else if(fromRow != toRow && fromCol != toCol) {
      throw new IllegalArgumentException("Can only move orthogonally");
    }
    else if((!(abs(fromRow - toRow) == 2)) && (!(abs(fromCol - toCol) == 2))) {
      throw new IllegalArgumentException("Can only move a distance of two spaces");
    }
    else if(fromRow == toRow) {
      int skip = ((fromCol + toCol) / 2);
      if(getSlotAt(fromRow, skip) == SlotState.Invalid || getSlotAt(fromRow, skip) == SlotState.Empty) {
        throw new IllegalArgumentException("Can only move over a marble");
      }

      this.board[fromRow][fromCol] = SlotState.Empty;
      this.board[fromRow][skip] = SlotState.Empty;
      this.board[toRow][toCol] = SlotState.Marble;
      this.score --;
      empty.add(new int[]{fromRow, fromCol});
      empty.add(new int[]{fromRow, skip});
    }
    else {
      int skip = ((fromRow + toRow) / 2);
      if(getSlotAt(skip, fromCol) == SlotState.Invalid || getSlotAt(skip, fromCol) == SlotState.Empty) {
        throw new IllegalArgumentException("Can only move over a marble");
      }

      this.board[fromRow][fromCol] = SlotState.Empty;
      this.board[skip][fromCol] = SlotState.Empty;
      this.board[toRow][toCol] = SlotState.Marble;
      this.score --;
      empty.add(new int[]{fromRow, fromCol});
      empty.add(new int[]{skip, fromCol});
    }
  }



}
