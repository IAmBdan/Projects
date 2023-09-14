package cs3500.marblesolitaire.model.hw04;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.abs;

public class TriangleSolitaireModel extends SolitaireModelTemp {

  /**
   * Initializes a new game board with default parameters.
   */
  public TriangleSolitaireModel() {
    initboard(5, 0, 0);
  }


  /**
   * Initializes a new game board with a specified empty slot position.
   * @param srow Row of the empty slot.
   * @param scol Column of the empty slot.
   */
  public TriangleSolitaireModel(int srow, int scol) {
    initboard(5, srow, scol);
  }


  /**
   * Initializes a new game board with a specified baseLength thickness and default empty slot position.
   * @param baseLength baseLength thickness of the board.
   */
  public TriangleSolitaireModel(int baseLength) {
    initboard(baseLength, 0, 0);
  }


  /**
   * Initializes a new game board with specified baseLength and empty slot position.
   * @param baseLength baseLength thickness of the board.
   * @param srow Row of the empty slot.
   * @param scol Column of the empty slot.
   */
  public TriangleSolitaireModel(int baseLength, int srow, int scol) {
    initboard(baseLength, srow, scol);
  }


  /**
   * Initializes the game board with the specified base length and the initial empty slot.
   * The method sets up the state of each slot on the board (invalid, empty, or marble)
   * based on the rules of the game. It also calculates and sets the initial score of the game.
   *
   * @param baseLength the base length of the triangle board. The baseLength must be greater than 0.
   * @param srow the row number of the initial empty slot
   * @param scol the column number of the initial empty slot
   * @throws IllegalArgumentException if the baseLength length is less than 1 or the initial empty slot
   *         is outside the bounds of the board
   */

  private void initboard(int baseLength, int srow, int scol) {
    if(baseLength < 1) {
      throw new IllegalArgumentException("baseLength must be greater than 0");
    }

    this.board = new SlotState[baseLength][baseLength];

    for (SlotState[] slotStates : board) {
      Arrays.fill(slotStates, SlotState.Marble);
    }
    for(int i = 0; i < board.length; i++) {
      for(int j = 0; j < board.length; j++) {
        if(i > j - 1){
          continue;
        }
        board[i][j] = SlotState.Invalid;
      }
    }

    if((srow >= board.length || srow < 0) || (scol >= board[0].length || scol < 0)) {
      throw new IllegalArgumentException("Empty row and column must be valid");
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
   * overrides the move method to move the parbles after checking the position
   * @param fromRow the row number of the position to be moved from
   *                (starts at 0)
   * @param fromCol the column number of the position to be moved from
   *                (starts at 0)
   * @param toRow   the row number of the position to be moved to
   *                (starts at 0)
   * @param toCol   the column number of the position to be moved to
   *                (starts at 0)
   * @throws IllegalArgumentException when its an invalid move
   */
  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) {
    if(getSlotAt(fromRow, fromCol) == SlotState.Invalid || getSlotAt(fromRow, fromCol) == SlotState.Empty) {
      throw new IllegalArgumentException("Can only move a spot with a marble");
    }
    else if(getSlotAt(toRow, toCol) == SlotState.Invalid || getSlotAt(toRow, toCol) == SlotState.Marble) {
      throw new IllegalArgumentException("Can only move a marble to an empty spot");
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
      int skip1 = ((fromRow + toRow) / 2);
      int skip2 = ((fromCol + toCol) / 2);
      if(getSlotAt(skip1, skip2) == SlotState.Invalid || getSlotAt(skip1, skip2) == SlotState.Empty) {
        throw new IllegalArgumentException("Can only move over a marble");
      }

      this.board[fromRow][fromCol] = SlotState.Empty;
      this.board[skip1][skip2] = SlotState.Empty;
      this.board[toRow][toCol] = SlotState.Marble;
      this.score --;
      empty.add(new int[]{fromRow, fromCol});
      empty.add(new int[]{skip1, skip2});
    }
  }


}
