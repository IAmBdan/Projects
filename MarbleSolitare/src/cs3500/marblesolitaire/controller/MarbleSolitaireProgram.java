package cs3500.marblesolitaire.controller;
import java.io.IOException;
import java.io.InputStreamReader;

import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;
import cs3500.marblesolitaire.model.hw04.*;
import cs3500.marblesolitaire.view.hw04.TriangleSolitaireTextView;


//code used for testing purposes, disregard while grading
public class MarbleSolitaireProgram {

  public static void main(String []args) throws IOException {

    Readable rd = new InputStreamReader(System.in);
    Appendable ap = System.out;

    EnglishSolitaireModel model = new EnglishSolitaireModel();
    MarbleSolitaireTextView viewer = new MarbleSolitaireTextView(model, ap);


    TriangleSolitaireModel model2 = new TriangleSolitaireModel();
    TriangleSolitaireTextView viewer2 = new TriangleSolitaireTextView(model2, ap);

    EuropeanSolitaireModel model3 = new EuropeanSolitaireModel(3);
    MarbleSolitaireTextView viewer3 = new MarbleSolitaireTextView(model3, ap);


    MarbleSolitaireControllerImpl control = new MarbleSolitaireControllerImpl(model, viewer, rd);
    MarbleSolitaireControllerImpl control2 = new MarbleSolitaireControllerImpl(model2, viewer2, rd);
    MarbleSolitaireControllerImpl control3 = new MarbleSolitaireControllerImpl(model3, viewer3, rd);

    control2.playGame();

  }
}
