package hw04;

import org.junit.Before;
import org.junit.Test;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModel;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;
import cs3500.marblesolitaire.view.hw04.TriangleSolitaireTextView;

import static cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState.SlotState.Empty;
import static cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState.SlotState.Invalid;
import static cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState.SlotState.Marble;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TriangleSolitaireModelTest {

  private TriangleSolitaireModel model;
  private TriangleSolitaireModel model2;
  private TriangleSolitaireModel model3;
  private TriangleSolitaireModel model4;
  private TriangleSolitaireModel model5;
  private TriangleSolitaireModel model6;
  private TriangleSolitaireModel model7;


  private TriangleSolitaireTextView viewer;

  @Before
  /**
   * Set up models with various initial configurations and a text viewer for visualization.
   */
  public void setUp() {

    model = new TriangleSolitaireModel();
    model2 = new TriangleSolitaireModel(2, 2);
    model3 =new TriangleSolitaireModel(5);
    model4 = new TriangleSolitaireModel(3);
    model5 = new TriangleSolitaireModel(6, 0, 0);
    model6 = new TriangleSolitaireModel(2, 2);
    model7 = new TriangleSolitaireModel(15,2, 2);


    viewer = new TriangleSolitaireTextView();
  }
  /**
   * Test will throw an exception if the Base is 0
   */
  @Test
  public void testInitBoard0Base(){
    try {
      TriangleSolitaireModel testModel1 = new TriangleSolitaireModel(0, 2,2);
      fail("Empty row and column must be valid");
    } catch (IllegalArgumentException e) {
    }
  }

  /**
   * Test will throw an exception if the Base is negative
   */
  @Test
  public void testInitBoardNegBase(){
    try {
      TriangleSolitaireModel testModel1 = new TriangleSolitaireModel(-1, 2,2);
      fail("does not throw an exception for a board with an Base length of -1");
    } catch (IllegalArgumentException e) {
    }
  }

  /**
   * Test will throw an exception if the row is negative
   */
  @Test
  public void testInitBoardNegRow(){
    try {
      TriangleSolitaireModel testModel1 = new TriangleSolitaireModel(2, -1,0);
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
      TriangleSolitaireModel testModel1 = new TriangleSolitaireModel(2, 0,-1);
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
      TriangleSolitaireModel testModel1 = new TriangleSolitaireModel(2, 0,50);
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
      TriangleSolitaireModel testModel1 = new TriangleSolitaireModel(2, 50,0);
      fail("does not throw an exception for a board with a row length greater than the length");
    } catch (IllegalArgumentException e) {
    }
  }
  /**
   * Test so see if we initialize the board correctly
   */


  @Test
  /**
   * Test if the score of each model is correctly calculated.
   */
  public void testGetScore() {
    assertEquals(14, model.getScore());
    assertEquals(14, model2.getScore());
    assertEquals(14, model3.getScore());
    assertEquals(5, model4.getScore());
    assertEquals(20, model5.getScore());
    assertEquals(119, model7.getScore());
  }

  @Test
  /**
   * Test if the board has been correctly instantiated
   */
  public void testInstantiation() {
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(0, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model2.getSlotAt(2, 2));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model3.getSlotAt(0, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model4.getSlotAt(0, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model5.getSlotAt(0, 0));

    assertEquals(5 ,model.getBoardSize());
    assertEquals(5 ,model2.getBoardSize());
    assertEquals(5 ,model3.getBoardSize());
    assertEquals(3 ,model4.getBoardSize());
    assertEquals(6 ,model5.getBoardSize());
    assertEquals(5, model6.getBoardSize());
    assertEquals(15, model7.getBoardSize());

    assertEquals(Invalid, model.getSlotAt(0, 1));
    assertEquals(Invalid, model2.getSlotAt(7, 7));
    assertEquals(Invalid, model3.getSlotAt(0, 12));
    assertEquals(Invalid, model4.getSlotAt(6, 0));
    assertEquals(Invalid, model5.getSlotAt(0, 2));
  }


  @Test
  /**
   * Test if valid moves are correctly handled in each model by checking the score and state of
   * involved slots.
   */
  public void testMove() {
    model.move(2, 0, 0, 0);
    assertEquals(13, model.getScore());
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(2, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(0, 0));

    model2.move(0, 0, 2, 2);
    assertEquals(13, model2.getScore());
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model2.getSlotAt(0, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model2.getSlotAt(2, 2));

    model3.move(2, 2, 0, 0);
    assertEquals(13, model3.getScore());
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model3.getSlotAt(2, 2));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model3.getSlotAt(0, 0));

    model4.move(2, 2, 0, 0);
    assertEquals(4, model4.getScore());
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model4.getSlotAt(2, 2));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model4.getSlotAt(0, 0));

    model5.move(2, 0, 0, 0);
    assertEquals(19, model5.getScore());
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model5.getSlotAt(2, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model5.getSlotAt(0, 0));

    model7.move(4, 2, 2, 2);
    assertEquals(118, model7.getScore());
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model7.getSlotAt(4,2));
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
    assertEquals(5, model.getBoardSize());
    assertEquals(5, model2.getBoardSize());
    assertEquals(5, model3.getBoardSize());
    assertEquals(3, model4.getBoardSize());
    assertEquals(6, model5.getBoardSize());
  }

  @Test
  /**
   * Test if the game board is correctly visualized by the text viewer for each model.
   */
  public void testVisualize() {
    assertEquals("     _        \n" +
            "    o o      \n" +
            "   o o o    \n" +
            "  o o o o  \n" +
            " o o o o o", viewer.toString(model));
    assertEquals(
            "     o        \n" +
                    "    o o      \n" +
                    "   o o _    \n" +
                    "  o o o o  \n" +
                    " o o o o o", viewer.toString(model2));
    assertEquals("     _        \n" +
            "    o o      \n" +
            "   o o o    \n" +
            "  o o o o  \n" +
            " o o o o o", viewer.toString(model3));
    assertEquals("   _    \n" +
            "  o o  \n" +
            " o o o", viewer.toString(model4));
    assertEquals("      _          \n" +
            "     o o        \n" +
            "    o o o      \n" +
            "   o o o o    \n" +
            "  o o o o o  \n" +
            " o o o o o o", viewer.toString(model5));
    assertEquals("     o        \n" +
            "    o o      \n" +
            "   o o _    \n" +
            "  o o o o  \n" +
            " o o o o o", viewer.toString(model6));
    assertEquals("               o                            \n" +
            "              o o                          \n" +
            "             o o _                        \n" +
            "            o o o o                      \n" +
            "           o o o o o                    \n" +
            "          o o o o o o                  \n" +
            "         o o o o o o o                \n" +
            "        o o o o o o o o              \n" +
            "       o o o o o o o o o            \n" +
            "      o o o o o o o o o o          \n" +
            "     o o o o o o o o o o o        \n" +
            "    o o o o o o o o o o o o      \n" +
            "   o o o o o o o o o o o o o    \n" +
            "  o o o o o o o o o o o o o o  \n" +
            " o o o o o o o o o o o o o o o", viewer.toString(model7));


  }
}
