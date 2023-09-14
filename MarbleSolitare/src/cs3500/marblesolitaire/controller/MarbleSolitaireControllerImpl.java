package cs3500.marblesolitaire.controller;

import java.io.IOException;
import java.util.Scanner;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireView;

/**
 * The implementation of MarbleSolitaireController. It controls the game play,
 * interact with the user, and update the view according to the model's state.
 */
public class MarbleSolitaireControllerImpl implements MarbleSolitaireController{
  private final MarbleSolitaireModel model;

  private final MarbleSolitaireView view;

  private final Readable rd;


  /**
   * Constructs a new MarbleSolitaireControllerImpl object with the provided model, view, and input source.
   *
   * @param model the game model.
   * @param view the game view.
   * @param rd the source of user input.
   * @throws IllegalArgumentException if any argument is null.
   */
  public MarbleSolitaireControllerImpl(MarbleSolitaireModel model,
                                       MarbleSolitaireView view,
                                       Readable rd) {

    if ((model==null) || (view==null) || (rd==null)) {
      throw new IllegalArgumentException("Cannot have null argument");
    }

    this.model = model;
    this.view = view;
    this.rd = rd;
  }

  /**
   * Begins the game and interacts with the user until the game is over or the user quits.
   *
   * @throws IllegalStateException if the controller is unable to read input or transmit output.
   */
  public void playGame() throws IllegalStateException {
    Scanner input = new Scanner(rd);
    boolean quit = false;

    try {
      welcomeMessage();
    }
    catch(Exception e){
      throw new IllegalStateException("Unable to transmit output");
    }
    while(!model.isGameOver() && !quit) {
      try {
        view.renderMessage("\n\n");
        view.renderBoard();
        view.renderMessage("\n\nScore: " + model.getScore() + "\nInput: ");
      }
      catch(Exception e) {
        throw new IllegalStateException("Unable to transmit output", e);
      }
      try {
        String in = input.nextLine();
        in = in.toLowerCase();
        switch (in) {
          case "menu": //print the menu of supported instructions
            welcomeMessage();
            break;
          case "moves":
            view.renderMessage("\n");
            break;
          case "q": //quit
          case "quit": //quit
            quit = true;
            break;
          default:
            String move = in.replaceAll("[^-?0-9]+", " ");
            int fromRow;
            int fromCol;
            int toRow;
            int toCol;


            String[] loc = move.trim().split(" ");
            if(loc.length == 4) {
              try {
                model.move(Integer.parseInt(loc[0]), Integer.parseInt(loc[1]),
                        Integer.parseInt(loc[2]), Integer.parseInt(loc[3]));
              }
              catch(Exception e) {
                view.renderMessage("\n" + e.getMessage() + ", try different input\n");
              }
            }
            else if(loc.length == 1) {
              fromRow = Integer.parseInt(in);
              view.renderMessage("Your input, Move from column: " + fromRow + "\nMove from column: ");
              in  = input.nextLine();
              fromCol = Integer.parseInt(in);
              view.renderMessage("Move to row: ");
              in  = input.nextLine();
              toRow = Integer.parseInt(in);
              while(toRow != fromRow && toRow != fromRow + 2 && toRow != fromRow - 2) {
                view.renderMessage("Invalid input, move to row: ");
                in  = input.nextLine();
                toRow = Integer.parseInt(in);
              }
              view.renderMessage("Move to column: ");
              in  = input.nextLine();
              toCol = Integer.parseInt(in);
              while(toCol != fromCol && toCol != fromCol + 2 && toCol != fromCol - 2) {
                view.renderMessage("Invalid input, move to column: ");
                in  = input.nextLine();
                toCol = Integer.parseInt(in);
              }

              try {
                model.move(fromRow, fromCol, toRow, toCol);
              }
              catch(Exception e) {
                view.renderMessage("\n" + e.getMessage() + ", try different input\n");
              }
            }
            else {
              view.renderMessage("\nInvalid Command, try format 'fromRow fromCol toRow toCol'\n");
            }
            break;
        }
      }
      catch(Exception e) {
        throw new IllegalStateException("Unable to read input", e);
      }

    }

    try {
      view.renderMessage("\n\n");
      view.renderMessage("Game Over\n");
      view.renderBoard();
      view.renderMessage("\nScore: " + model.getScore() + "\n");
    }
    catch(Exception e){
      throw new IllegalStateException("Unable to transmit output, " + e);
    }

    input.close();
  }


  /**
   * Prints the list of valid user commands to the view.
   * @throws IOException if the view is unable to render the message.
   */
  private void printMenu() throws IllegalStateException, IOException {
    view.renderMessage("Supported user instructions are: "+System.lineSeparator());
    view.renderMessage("Four integer values for row/col marble is moving to and from"+System.lineSeparator());
    view.renderMessage("menu (Print supported instruction list)"+System.lineSeparator());
    view.renderMessage("moves (gives list of valid moves)"+System.lineSeparator());
    view.renderMessage("q or quit (quit the program) "+System.lineSeparator());
  }

  /**
   * Prints the welcome message and the menu to the view.
   * @throws IOException if the view is unable to render the message.
   */
  private void welcomeMessage() throws IllegalStateException, IOException {
    view.renderMessage("Welcome to the spreadsheet program!" +System.lineSeparator());
    printMenu();
  }

  /**
   * Prints the farewell message to the view.
   * @throws IOException if the view is unable to render the message.
   */
  private void farewellMessage() throws IOException {
    view.renderMessage("Thank you for using this program!");
  }
}
