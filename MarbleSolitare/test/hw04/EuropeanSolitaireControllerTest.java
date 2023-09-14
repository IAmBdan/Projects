package hw04;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cs3500.marblesolitaire.controller.MarbleSolitaireControllerImpl;
import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;

import static org.junit.Assert.assertEquals;

public class EuropeanSolitaireControllerTest {

  private EuropeanSolitaireModel model;


  private MarbleSolitaireControllerImpl controller;

  private MarbleSolitaireTextView viewer;

  private Appendable ap;

  private Readable rd;

  private MarbleSolitaireControllerImpl control;

  @Before
  /**
   * Set up models with various initial configurations and a text viewer for visualization.
   */
  public void setUp() {

    ap = new StringBuilder();

    model = new EuropeanSolitaireModel();

    viewer = new MarbleSolitaireTextView(model, ap);

  }

  @Test
  public void testQuit() throws IOException {

    String input = "menu\nq";
    InputStream stream = new ByteArrayInputStream(input.getBytes());

    rd = new InputStreamReader(stream);
    control = new MarbleSolitaireControllerImpl(model, viewer, rd);

    control.playGame();

    assertEquals(
            "    0 1 2 3 4 5 6\n" +
                    "0       o o o    \n" +
                    "1     o o o o o  \n" +
                    "2   o o o o o o o\n" +
                    "3   o o o _ o o o\n" +
                    "4   o o o o o o o\n" +
                    "5     o o o o o  \n" +
                    "6       o o o    " , viewer.toString());

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

    String input = "3 5 3 3\nq";
    InputStream stream = new ByteArrayInputStream(input.getBytes());

    rd = new InputStreamReader(stream);
    control = new MarbleSolitaireControllerImpl(model, viewer, rd);

    control.playGame();

    assertEquals(
            "    0 1 2 3 4 5 6\n" +
                    "0       o o o    \n" +
                    "1     o o o o o  \n" +
                    "2   o o o o o o o\n" +
                    "3   o o o o _ _ o\n" +
                    "4   o o o o o o o\n" +
                    "5     o o o o o  \n" +
                    "6       o o o    " , viewer.toString());

  }

  @Test
  public void testMultiMove() throws IOException {

    String input = "3 5 3 3\n3 2 3 4\nq";
    InputStream stream = new ByteArrayInputStream(input.getBytes());

    rd = new InputStreamReader(stream);
    control = new MarbleSolitaireControllerImpl(model, viewer, rd);

    control.playGame();

    assertEquals(
            "    0 1 2 3 4 5 6\n" +
                    "0       o o o    \n" +
                    "1     o o o o o  \n" +
                    "2   o o o o o o o\n" +
                    "3   o o _ _ o _ o\n" +
                    "4   o o o o o o o\n" +
                    "5     o o o o o  \n" +
                    "6       o o o    " , viewer.toString());

  }

  @Test
  public void testFullGame() throws IOException {

    String input = "3 1 3 3\n" +
            "1 2 3 2\n" +
            "2 4 2 2\n" +
            "2 1 2 3\n" +
            "4 2 2 2\n" +
            "4 4 2 4\n" +
            "1 4 3 4\n" +
            "4 0 4 2\n" +
            "5 2 3 2\n" +
            "3 2 1 2\n" +
            "3 4 3 2\n" +
            "1 3 3 3\n" +
            "4 3 2 3\n" +
            "3 6 3 4\n" +
            "6 3 4 3\n" +
            "2 6 2 4\n" +
            "5 5 5 3\n" +
            "4 6 4 4\n" +
            "4 3 4 5\n" +
            "0 2 2 2\n" +
            "0 4 0 2\n" +
            "2 3 2 5\n" +
            "2 2 4 2\n" +
            "1 5 3 5\n" +
            "3 5 3 3\n" +
            "2 0 4 0\n";
    InputStream stream = new ByteArrayInputStream(input.getBytes());

    rd = new InputStreamReader(stream);
    control = new MarbleSolitaireControllerImpl(model, viewer, rd);

    control.playGame();

    assertEquals(
                    "    0 1 2 3 4 5 6\n" +
                            "0       o _ _    \n" +
                            "1     o _ _ _ _  \n" +
                            "2   _ _ _ _ _ _ _\n" +
                            "3   _ _ _ o _ _ _\n" +
                            "4   o _ o _ _ o _\n" +
                            "5     o _ o _ _  \n" +
                            "6       o _ o    ", viewer.toString());

  }

}
