package hw04;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cs3500.marblesolitaire.controller.MarbleSolitaireControllerImpl;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModel;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;
import cs3500.marblesolitaire.view.hw04.TriangleSolitaireTextView;

import static org.junit.Assert.assertEquals;

public class TriangleSolitaireControllerTest {

  private TriangleSolitaireModel model;


  private MarbleSolitaireControllerImpl controller;

  private TriangleSolitaireTextView viewer;

  private Appendable ap;

  private Readable rd;

  private MarbleSolitaireControllerImpl control;

  @Before
  /**
   * Set up models with various initial configurations and a text viewer for visualization.
   */
  public void setUp() {

    ap = new StringBuilder();

    model = new TriangleSolitaireModel();

    viewer = new TriangleSolitaireTextView(model, ap);

  }

  @Test
  public void testQuit() throws IOException {

    String input = "menu\nq";
    InputStream stream = new ByteArrayInputStream(input.getBytes());

    rd = new InputStreamReader(stream);
    control = new MarbleSolitaireControllerImpl(model, viewer, rd);

    control.playGame();

    assertEquals(
            "     _        \n" +
                    "    o o      \n" +
                    "   o o o    \n" +
                    "  o o o o  \n" +
                    " o o o o o" , viewer.toString());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testArgumentException() throws IllegalStateException {

    String input = "menu\nq";
    InputStream stream = new ByteArrayInputStream(input.getBytes());

    rd = null;
    control = new MarbleSolitaireControllerImpl(model, viewer, rd);

    control.playGame();


  }

  @Test
  public void testSingleMove() throws IOException {

    String input = "2 0 0 0\nq";
    InputStream stream = new ByteArrayInputStream(input.getBytes());

    rd = new InputStreamReader(stream);
    control = new MarbleSolitaireControllerImpl(model, viewer, rd);

    control.playGame();

    assertEquals(
            "     o        \n" +
                    "    _ o      \n" +
                    "   _ o o    \n" +
                    "  o o o o  \n" +
                    " o o o o o" , viewer.toString());

  }

  @Test
  public void testMultiMove() throws IOException {

    String input = "2 0 0 0\n3 2 1 0\nq";
    InputStream stream = new ByteArrayInputStream(input.getBytes());

    rd = new InputStreamReader(stream);
    control = new MarbleSolitaireControllerImpl(model, viewer, rd);

    control.playGame();

    assertEquals(
            "     o        \n" +
                    "    o o      \n" +
                    "   _ _ o    \n" +
                    "  o o _ o  \n" +
                    " o o o o o" , viewer.toString());

  }

  @Test
  public void testFullGame() throws IOException {

    String input = "2 0 0 0\n" +
            "3 2 1 0\n" +
            "0 0 3 0\n" +
            "0 0 2 0\n" +
            "2 2 0 0\n" +
            "3 0 1 0\n" +
            "4 4 2 2\n" +
            "4 2 4 4\n" +
            "4 0 4 2\n" +
            "4 2 2 0\n" +
            "1 0 3 0\n";
    InputStream stream = new ByteArrayInputStream(input.getBytes());

    rd = new InputStreamReader(stream);
    control = new MarbleSolitaireControllerImpl(model, viewer, rd);

    control.playGame();

    assertEquals(
                    "     o        \n" +
                            "    _ _      \n" +
                            "   _ _ o    \n" +
                            "  o _ _ _  \n" +
                            " _ _ _ _ o", viewer.toString());

  }

}
