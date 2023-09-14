package cs3500.marblesolitaire;

import java.io.InputStreamReader;

import cs3500.marblesolitaire.controller.MarbleSolitaireControllerImpl;
import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModel;
import cs3500.marblesolitaire.model.hw04.SolitaireModelTemp;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;
import cs3500.marblesolitaire.view.hw04.TriangleSolitaireTextView;

/**
 * main class for marble solitare
 */
public final class MarbleSolitaire {
  /**
   * method for the main that will run the game
   * @param args
   */
  public static void main(String[] args) {
    Readable rd = new InputStreamReader(System.in);
    Appendable ap = System.out;

    int size = -1;
    int holex = -1;
    int holey = -1;

    SolitaireModelTemp model;
    MarbleSolitaireTextView viewer;
    TriangleSolitaireTextView triangleViewer;
    MarbleSolitaireControllerImpl controller;


    if(args.length == 0){
      throw new IllegalArgumentException("Please provide board type");
    }
    String boardType = args[0];
    boardType = boardType.toLowerCase();
    if(args.length == 3) {
      if (args[1].equals("-size")) {
        size = Integer.parseInt(args[2]);
      }
    }
    if(args.length == 4) {
      if (args[1].equals("-hole")) {
        holex = Integer.parseInt(args[2]);
        holey = Integer.parseInt(args[3]);
      }
    }
    if(args.length == 6) {
      if (args[1].equals("-size")) {
        size = Integer.parseInt(args[2]);
        if (args[3].equals("-hole")) {
          holex = Integer.parseInt(args[4]);
          holey = Integer.parseInt(args[5]);
        }
      } else if (args[1].equals("-hole")) {
        holex = Integer.parseInt(args[2]);
        holey = Integer.parseInt(args[3]);
        if (args[4].equals("-size")) {
          size = Integer.parseInt(args[5]);

        }
      }
    }
    switch (boardType) {
      case "triangular":
        if(size != -1 && holex != -1 && holey != -1) {
          model = new TriangleSolitaireModel(size, holex, holey);
        }
        else if(size != -1) {
          model = new TriangleSolitaireModel(size);
        }
        else if(holex != -1 && holey != -1) {
          model = new TriangleSolitaireModel(holex, holey);
        }
        else{
          model = new TriangleSolitaireModel();
        }
        triangleViewer = new TriangleSolitaireTextView(model, ap);
        controller = new MarbleSolitaireControllerImpl(model, triangleViewer, rd);
        controller.playGame();
      case "european":
        if(size != -1 && holex != -1 && holey != -1) {
          model = new EuropeanSolitaireModel(size, holex, holey);
        }
        else if(size != -1) {
          model = new EuropeanSolitaireModel(size);
        }
        else if(holex != -1 && holey != -1) {
          model = new EuropeanSolitaireModel(holex, holey);
        }
        else{
          model = new EuropeanSolitaireModel();
        }
        viewer = new MarbleSolitaireTextView(model, ap);
        controller = new MarbleSolitaireControllerImpl(model, viewer, rd);
        controller.playGame();

      case "english":
        if(size != -1 && holex != -1 && holey != -1) {
          model = new EnglishSolitaireModel(size, holex, holey);
        }
        else if(size != -1) {
          model = new EnglishSolitaireModel(size);
        }
        else if(holex != -1 && holey != -1) {
          model = new EnglishSolitaireModel(holex, holey);
        }
        else{
          model = new EnglishSolitaireModel();
        }
        viewer = new MarbleSolitaireTextView(model, ap);
        controller = new MarbleSolitaireControllerImpl(model, viewer, rd);
        controller.playGame();

      default:
        System.out.println("Invalid Board Option");

    }
  }
}