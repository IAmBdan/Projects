import org.junit.Before;
import org.junit.Test;

import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;

import static cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState.SlotState.Empty;
import static cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState.SlotState.Invalid;
import static cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState.SlotState.Marble;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class EnglishSolitaireModelTest {

  private EnglishSolitaireModel model;
  private EnglishSolitaireModel model2;
  private EnglishSolitaireModel model3;
  private EnglishSolitaireModel model4;
  private EnglishSolitaireModel model5;
  private EnglishSolitaireModel model6;
  private EnglishSolitaireModel model7;


  private MarbleSolitaireTextView viewer;

  @Before
  /**
   * Set up models with various initial configurations and a text viewer for visualization.
   */
  public void setUp() {

    model = new EnglishSolitaireModel();
    model2 = new EnglishSolitaireModel(2, 2);
    model3 =new EnglishSolitaireModel(5);
    model4 = new EnglishSolitaireModel(3, 0, 4);
    model5 = new EnglishSolitaireModel(6, 8, 8);
    model6 = new EnglishSolitaireModel(2, 2);
    model7 = new EnglishSolitaireModel(2,2, 2);


    viewer = new MarbleSolitaireTextView();
  }
  /**
   * Test will throw an exception if the arm is 0
   */
  @Test
  public void testInitBoard0Arm(){
    try {
      EnglishSolitaireModel testModel1 = new EnglishSolitaireModel(0, 2,2);
      fail("does not throw an exception for a board with an arm length of 0");
    } catch (IllegalArgumentException e) {
    }
  }

  /**
   * Test will throw an exception if the arm is negative
   */
  @Test
  public void testInitBoardNegArm(){
    try {
      EnglishSolitaireModel testModel1 = new EnglishSolitaireModel(-1, 2,2);
      fail("does not throw an exception for a board with an arm length of -1");
    } catch (IllegalArgumentException e) {
    }
  }

  /**
   * Test will throw an exception if the row is negative
   */
  @Test
  public void testInitBoardNegRow(){
    try {
      EnglishSolitaireModel testModel1 = new EnglishSolitaireModel(2, -1,0);
      fail("does not throw an exception for a board with a row length of -1");
    } catch (IllegalArgumentException e) {
    }
  }
  /**
   * Test will throw an exception if the col is negative
   */
  @Test
  public void testInitBoardNegCol(){
    try {
      EnglishSolitaireModel testModel1 = new EnglishSolitaireModel(2, 0,-1);
      fail("does not throw an exception for a board with a col length of -1");
    } catch (IllegalArgumentException e) {
    }
  }
  /**
   * Test will throw an exception if the col greater than the length
   */
  @Test
  public void testInitBoardCol(){
    try {
      EnglishSolitaireModel testModel1 = new EnglishSolitaireModel(2, 0,50);
      fail("does not throw an exception for a board with a col length greater than the length");
    } catch (IllegalArgumentException e) {
    }
  }
  /**
   * Test will throw an exception if the row greater than the length
   */
  @Test
  public void testInitBoardRow(){
    try {
      EnglishSolitaireModel testModel1 = new EnglishSolitaireModel(2, 50,0);
      fail("does not throw an exception for a board with a row length greater than the length");
    } catch (IllegalArgumentException e) {
    }
  }
  /**
   * Test so see if we initialize the board correctly
   */

  @Test
  public void testInitBoard(){
    EnglishSolitaireModel model7 = new EnglishSolitaireModel(2, 2, 2);
    assertEquals(model7.getSlotAt(0, 0), Invalid);
    assertEquals(model7.getSlotAt(1, 0), Marble);
    assertEquals(model7.getSlotAt(0, 1), Marble);
    assertEquals(model7.getSlotAt(1, 1), Marble);
    assertEquals(model7.getSlotAt(2, 0), Marble);
    assertEquals(model7.getSlotAt(2, 1), Marble);
    assertEquals(model7.getSlotAt(1, 2), Marble);
    assertEquals(model7.getSlotAt(0, 2), Marble);
    assertEquals(model7.getSlotAt(2, 2), Empty);
    assertEquals(model7.getSlotAt(0, 3), Invalid);
    assertEquals(model7.getSlotAt(1, 3), Marble);
    assertEquals(model7.getSlotAt(2, 3), Marble);
    assertEquals(model7.getSlotAt(3, 3), Invalid);
    assertEquals(model7.getSlotAt(3, 0), Invalid);
    assertEquals(model7.getSlotAt(3, 1), Marble);
    assertEquals(model7.getSlotAt(3, 2), Marble);
    assertEquals(model7.getSlotAt(0, 4), Invalid);
    assertEquals(model7.getSlotAt(1, 4), Invalid);
    assertEquals(model7.getSlotAt(2, 4), Invalid);
    assertEquals(model7.getSlotAt(3, 4), Invalid);
    assertEquals(model7.getSlotAt(4, 4), Invalid);
    assertEquals(model7.getSlotAt(4, 0), Invalid);
    assertEquals(model7.getSlotAt(4, 1), Invalid);
    assertEquals(model7.getSlotAt(4, 2), Invalid);
    assertEquals(model7.getSlotAt(4, 3), Invalid);
  }


  @Test
  /**
   * Test if the score of each model is correctly calculated.
   */
  public void testGetScore() {
    assertEquals(32, model.getScore());
    assertEquals(32, model2.getScore());
    assertEquals(104, model3.getScore());
    assertEquals(32, model4.getScore());
    assertEquals(155, model5.getScore());
    assertEquals(11, model7.getScore());
  }

  @Test
  /**
   * Test if the board has been correctly instantiated
   */
  public void testInstantiation() {
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(3, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model2.getSlotAt(2, 2));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model3.getSlotAt(6, 6));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model4.getSlotAt(0, 4));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model5.getSlotAt(8, 8));

    assertEquals(7 ,model.getBoardSize());
    assertEquals(7 ,model2.getBoardSize());
    assertEquals(13 ,model3.getBoardSize());
    assertEquals(7 ,model4.getBoardSize());
    assertEquals(16 ,model5.getBoardSize());
    assertEquals(7, model6.getBoardSize());
    assertEquals(4, model7.getBoardSize());

    assertEquals(Invalid, model.getSlotAt(0, 0));
    assertEquals(Invalid, model2.getSlotAt(7, 7));
    assertEquals(Invalid, model3.getSlotAt(0, 12));
    assertEquals(Invalid, model4.getSlotAt(6, 0));
    assertEquals(Invalid, model5.getSlotAt(0, 0));
  }


  @Test
  /**
   * Test if valid moves are correctly handled in each model by checking the score and state of
   * involved slots.
   */
  public void testMove() {
    model.move(1, 3, 3, 3);
    assertEquals(31, model.getScore());
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(1, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(3, 3));

    model2.move(0, 2, 2, 2);
    assertEquals(31, model2.getScore());
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model2.getSlotAt(0, 2));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model2.getSlotAt(2, 2));

    model3.move(4, 6, 6, 6);
    assertEquals(103, model3.getScore());
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model3.getSlotAt(4, 6));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model3.getSlotAt(6, 6));

    model4.move(2, 4, 0, 4);
    assertEquals(31, model4.getScore());
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model4.getSlotAt(2, 4));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model4.getSlotAt(0, 4));

    model5.move(6, 8, 8, 8);
    assertEquals(154, model5.getScore());
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model5.getSlotAt(6, 8));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model5.getSlotAt(8, 8));

    model7.move(2, 0, 2, 2);
    assertEquals(10, model7.getScore());
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model7.getSlotAt(2,0));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model7.getSlotAt(2,2));
  }

  @Test(expected = IllegalArgumentException.class)
  /**
   * Test if invalid moves correctly throw an IllegalArgumentException in each model.
   */
  public void testInvalidMove() {
    model.move(0, 3, 3, 3);
    model2.move(0, 3, 2, 3);
    model3.move(2, 2, 8, 3);
    model4.move(0, 3, 7, 4);
    model5.move(6, 6, 6, 8);

    model7.move(3,3, 3, 3);
    model7.move(0,0, 3, 3);
    model7.move(0,0, 4, 4);
    model7.move(0,2, 4, 4);
  }


  @Test
  /**
   * Test if the game over status is correctly determined in each model.
   */
  public void testIsGameOver() {
    assertFalse(model.isGameOver());
    assertFalse(model2.isGameOver());
    assertFalse(model3.isGameOver());
    assertFalse(model4.isGameOver());
    assertFalse(model5.isGameOver());
    assertFalse(model6.isGameOver());
    assertFalse(model7.isGameOver());


    model.finishGame();
    assertTrue(model.isGameOver());
    model2.finishGame();
    assertTrue(model2.isGameOver());
    model3.finishGame();
    assertTrue(model3.isGameOver());
    model4.finishGame();
    assertTrue(model4.isGameOver());
    model5.finishGame();
    assertTrue(model5.isGameOver());
    model6.finishGame();
    assertTrue(model6.isGameOver());
    model7.finishGame();
    assertTrue(model7.isGameOver());
  }



  @Test
  /**
   * Test if the board size is correctly reported by each model.
   */
  public void testGetBoardSize() {
    assertEquals(7, model.getBoardSize());
    assertEquals(7, model2.getBoardSize());
    assertEquals(13, model3.getBoardSize());
    assertEquals(7, model4.getBoardSize());
    assertEquals(16, model5.getBoardSize());
  }

  @Test
  /**
   * Test if the game board is correctly visualized by the text viewer for each model.
   */
  public void testVisualize() {
    assertEquals("    0 1 2 3 4 5 6\n" +
            "0       o o o    \n" +
            "1       o o o    \n" +
            "2   o o o o o o o\n" +
            "3   o o o _ o o o\n" +
            "4   o o o o o o o\n" +
            "5       o o o    \n" +
            "6       o o o    ", viewer.toString(model));
    assertEquals(
            "    0 1 2 3 4 5 6\n" +
            "0       o o o    \n" +
            "1       o o o    \n" +
            "2   o o _ o o o o\n" +
            "3   o o o o o o o\n" +
            "4   o o o o o o o\n" +
            "5       o o o    \n" +
            "6       o o o    ", viewer.toString(model2));
    assertEquals("    0 1 2 3 4 5 6 7 8 9 10 11 12\n" +
            "0           o o o o o        \n" +
            "1           o o o o o        \n" +
            "2           o o o o o        \n" +
            "3           o o o o o        \n" +
            "4   o o o o o o o o o o o o o\n" +
            "5   o o o o o o o o o o o o o\n" +
            "6   o o o o o o _ o o o o o o\n" +
            "7   o o o o o o o o o o o o o\n" +
            "8   o o o o o o o o o o o o o\n" +
            "9           o o o o o        \n" +
            "10           o o o o o        \n" +
            "11           o o o o o        \n" +
            "12           o o o o o        ", viewer.toString(model3));
    assertEquals("    0 1 2 3 4 5 6\n" +
            "0       o o _    \n" +
            "1       o o o    \n" +
            "2   o o o o o o o\n" +
            "3   o o o o o o o\n" +
            "4   o o o o o o o\n" +
            "5       o o o    \n" +
            "6       o o o    ", viewer.toString(model4));
    assertEquals("    0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15\n" +
            "0             o o o o o o          \n" +
            "1             o o o o o o          \n" +
            "2             o o o o o o          \n" +
            "3             o o o o o o          \n" +
            "4             o o o o o o          \n" +
            "5   o o o o o o o o o o o o o o o o\n" +
            "6   o o o o o o o o o o o o o o o o\n" +
            "7   o o o o o o o o o o o o o o o o\n" +
            "8   o o o o o o o o _ o o o o o o o\n" +
            "9   o o o o o o o o o o o o o o o o\n" +
            "10   o o o o o o o o o o o o o o o o\n" +
            "11             o o o o o o          \n" +
            "12             o o o o o o          \n" +
            "13             o o o o o o          \n" +
            "14             o o o o o o          \n" +
            "15             o o o o o o          ", viewer.toString(model5));
    assertEquals("    0 1 2 3 4 5 6\n" +
            "0       o o o    \n" +
            "1       o o o    \n" +
            "2   o o _ o o o o\n" +
            "3   o o o o o o o\n" +
            "4   o o o o o o o\n" +
            "5       o o o    \n" +
            "6       o o o    ", viewer.toString(model6));
    assertEquals("    0 1 2 3\n" +
            "0     o o  \n" +
            "1   o o o o\n" +
            "2   o o _ o\n" +
            "3     o o  ", viewer.toString(model7));


  }
}
