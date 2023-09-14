import java.util.ArrayList;
import java.util.Random;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

class FloodItWorld extends World {
  // All the cells of the game
  ArrayList<ArrayList<Cell>> board;
  // width of the board
  int width;
  // height of the board
  int height;
  // number of colors on the board
  int numColors;
  // represents the random which determines cell color
  Random rand;
  // represents the color of the top left cell
  Color topLeft;
  // represents a list of cells that needs to be drawn
  ArrayList<Cell> drawList;
  // represents the list of flooded cells
  ArrayList<ICell> floodedCells;
  // value that adds the indexes together to determine which diagonal row we are
  // on
  int sum;
  // represents guesses so far
  int guessesSoFar;
  // represents max number of guesses
  int guessesAllowed;
  // represents if the game is over or not
  boolean theGameIsOver;
  // represents if they person won the game or not
  boolean win;
  // represents the time elapsed since the beginning of the game in seconds
  double timer;

  // regular constructor
  FloodItWorld(int width, int numColors) {
    this.width = width;
    this.height = width;
    this.numColors = numColors;
    this.rand = new Random();
    this.board = this.initBoard();
    this.assignAllCells();
    this.topLeft = this.board.get(0).get(0).initColor();
    this.board.get(0).get(0).setFlooded();
    this.drawList = new ArrayList<Cell>();
    this.drawList.add(this.board.get(0).get(0));
    this.floodedCells = new ArrayList<ICell>();
    this.floodedCells.add(this.board.get(0).get(0));
    this.sum = 0;
    this.guessesSoFar = 0;
    this.guessesAllowed = this.calcGuesses();
    this.theGameIsOver = false;
    this.win = this.allSameColor() && (this.guessesSoFar <= this.guessesAllowed);
    this.timer = 0;

    // checking to make sure the input is in the parameters otherwise throw and
    // exception
    if (this.width < 3) {
      throw new IllegalArgumentException(this.width + " is below the minimum board size.");
    }
    else if (this.width > 26) {
      throw new IllegalArgumentException(this.width + " is above the maximum board size.");
    }
    else if (this.height < 3) {
      throw new IllegalArgumentException(this.height + " is below the minimum board size.");
    }
    else if (this.height > 26) {
      throw new IllegalArgumentException(this.height + " is above the maximum board size.");
    }
    else if (this.numColors < 3) {
      throw new IllegalArgumentException(
          this.numColors + " is below the minimum number of colors.");
    }
    else if (this.numColors > 9) {
      throw new IllegalArgumentException(
          this.numColors + " is above the maximum number of colors.");
    }
  }

  //  constructor to create a rectangle board
  FloodItWorld(int width, int height, int numColors) {
    this.width = width;
    this.height = height;
    this.numColors = numColors;
    this.rand = new Random();
    this.board = this.initBoard();
    this.assignAllCells();
    this.topLeft = this.board.get(0).get(0).initColor();
    this.board.get(0).get(0).setFlooded();
    this.drawList = new ArrayList<Cell>();
    this.drawList.add(this.board.get(0).get(0));
    this.floodedCells = new ArrayList<ICell>();
    this.floodedCells.add(this.board.get(0).get(0));
    this.sum = 0;
    this.guessesSoFar = 0;
    this.guessesAllowed = this.calcGuesses();
    this.theGameIsOver = false;
    this.win = this.allSameColor() && (this.guessesSoFar <= this.guessesAllowed);
    this.timer = 0;

    // checking to make sure the input is in the parameters otherwise throw and
    // exception
    if (this.width < 3) {
      throw new IllegalArgumentException(this.width + " is below the minimum board size.");
    }
    else if (this.width > 26) {
      throw new IllegalArgumentException(this.width + " is above the maximum board size.");
    }
    else if (this.height < 3) {
      throw new IllegalArgumentException(this.height + " is below the minimum board size.");
    }
    else if (this.height > 26) {
      throw new IllegalArgumentException(this.height + " is above the maximum board size.");
    }
    else if (this.numColors < 3) {
      throw new IllegalArgumentException(
          this.numColors + " is below the minimum number of colors.");
    }
    else if (this.numColors > 9) {
      throw new IllegalArgumentException(
          this.numColors + " is above the maximum number of colors.");
    }
  }

  // seeded random constructor for testing a square board
  FloodItWorld(int width, int numColors, Random rand) {
    this.width = width;
    this.height = width;
    this.numColors = numColors;
    this.rand = rand;
    this.board = this.initBoard();
    this.assignAllCells();
    this.topLeft = this.board.get(0).get(0).initColor();
    this.board.get(0).get(0).setFlooded();
    this.drawList = new ArrayList<Cell>();
    this.drawList.add(this.board.get(0).get(0));
    this.floodedCells = new ArrayList<ICell>();
    this.floodedCells.add(this.board.get(0).get(0));
    this.sum = 0;
    this.guessesSoFar = 0;
    this.guessesAllowed = this.calcGuesses();
    this.theGameIsOver = false;
    this.win = this.allSameColor() && (this.guessesSoFar <= this.guessesAllowed);
    this.timer = 0;

    // checking to make sure the input is in the parameters otherwise throw and
    // exception
    if (this.width < 3) {
      throw new IllegalArgumentException(this.width + " is below the minimum board size.");
    }
    else if (this.width > 26) {
      throw new IllegalArgumentException(this.width + " is above the maximum board size.");
    }
    else if (this.height < 3) {
      throw new IllegalArgumentException(this.height + " is below the minimum board size.");
    }
    else if (this.height > 26) {
      throw new IllegalArgumentException(this.height + " is above the maximum board size.");
    }
    else if (this.numColors < 3) {
      throw new IllegalArgumentException(
          this.numColors + " is below the minimum number of colors.");
    }
    else if (this.numColors > 9) {
      throw new IllegalArgumentException(
          this.numColors + " is above the maximum number of colors.");
    }
  }

  // seeded random constructor for testing a rectangle board
  FloodItWorld(int width, int height, int numColors, Random rand) {
    this.width = width;
    this.height = height;
    this.numColors = numColors;
    this.rand = rand;
    this.board = this.initBoard();
    this.assignAllCells();
    this.topLeft = this.board.get(0).get(0).initColor();
    this.board.get(0).get(0).setFlooded();
    this.drawList = new ArrayList<Cell>();
    this.drawList.add(this.board.get(0).get(0));
    this.floodedCells = new ArrayList<ICell>();
    this.floodedCells.add(this.board.get(0).get(0));
    this.sum = 0;
    this.guessesSoFar = 0;
    this.guessesAllowed = this.calcGuesses();
    this.theGameIsOver = false;
    this.win = this.allSameColor() && (this.guessesSoFar <= this.guessesAllowed);
    this.timer = 0;

    // checking to make sure the input is in the parameters otherwise throw and
    // exception
    if (this.width < 3) {
      throw new IllegalArgumentException(this.width + " is below the minimum board size.");
    }
    else if (this.width > 26) {
      throw new IllegalArgumentException(this.width + " is above the maximum board size.");
    }
    else if (this.height < 3) {
      throw new IllegalArgumentException(this.height + " is below the minimum board size.");
    }
    else if (this.height > 26) {
      throw new IllegalArgumentException(this.height + " is above the maximum board size.");
    }
    else if (this.numColors < 3) {
      throw new IllegalArgumentException(
          this.numColors + " is below the minimum number of colors.");
    }
    else if (this.numColors > 9) {
      throw new IllegalArgumentException(
          this.numColors + " is above the maximum number of colors.");
    }
  }

  // checks if all of the cells in the board are the same color
  // we use this to determine if we win the game
  public boolean allSameColor() {
    boolean y = true;
    for (ArrayList<Cell> col : this.board) {
      for (Cell cell : col) {
        y = cell.flooded && y;
      }
    }
    return y;
  }

  // formula we use to calculate the allowed guesses based on width, height and
  // the numbers of colors
  public int calcGuesses() {
    return (int) (Math.sqrt(this.width * this.height)) * this.numColors / 3;
  }

  // draws the moves made and the moves allowed
  // draws the score
  public WorldImage drawScore() {
    WorldImage score = new TextImage(
        String.valueOf(this.guessesSoFar) + "/" + String.valueOf(this.guessesAllowed), 20,
        Color.BLACK);
    return score;
  }

  // created a timer that keeps track of the seconds since the game
  // started;
  // draws the timer
  // EFFECT: adds .15 to the timer each time its called
  public WorldImage drawTimer() {
    this.timer += .005;
    WorldImage score = new TextImage("Time: " + Math.round(this.timer) + "", 20, Color.BLACK);
    return score;
  }

  // makes the scene, and puts the game board and other elements on top of it
  // EFFECT: updates the scene with the timer and the score images
  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(this.width * 20, this.height * 20);
    scene.placeImageXY(this.drawBoard(), this.width * 10, this.height * 10);
    if (this.width < 6) {
      scene.placeImageXY(this.drawScore(), this.width * 20 + 50, this.height * 10 - 12);
      scene.placeImageXY(this.drawTimer(), this.width * 20 + 50, this.height * 10 + 12);
    }
    else {
      scene.placeImageXY(this.drawScore(), this.width * 10, this.height * 20 + 25);
      scene.placeImageXY(this.drawTimer(), this.width * 10, this.height * 20 + 50);
    }

    return scene;

  }

  // EFFECT: updates the topLeft field to the color that the mouse clicks unless
  // it is white or the same as the current color
  public void onMouseClicked(Posn pos) {
    if (!(this.getCellColor(pos).equals(this.topLeft))
        && !(this.getCellColor(pos).equals(Color.white))) {
      this.topLeft = this.getCellColor(pos);
      this.guessesSoFar += 1;
    }
    // this.drawList = new ArrayList<Cell>();
    Cell topLeftCell = this.board.get(0).get(0);
    topLeftCell.color = this.getCellColor(pos);
    // this.drawList.add(topLeftCell);
    this.sum = 0;
  }

  // world end function that is called if the game is over, and will return you
  // win or you lose based on the state of the board
  public WorldEnd worldEnds() {
    if (this.theGameIsOver) {
      if (this.allSameColor() && (this.guessesSoFar <= this.guessesAllowed)) {
        return new WorldEnd(true, this.lastScene("you win"));
      }
      else {
        return new WorldEnd(true, this.lastScene("you lose"));
      }
    }
    else {
      return new WorldEnd(false, this.makeScene());
    }
  }

  // last scene that is drawn, changes depending on if the user won or lost
  // EFFECT: updates the scene with a text image
  public WorldScene lastScene(String msg) {
    WorldScene scene = new WorldScene(this.width * 20, this.height * 20);
    WorldImage textBack = new TextImage(msg, this.width * 4, Color.black);
    scene.placeImageXY(textBack, this.width * 10, this.height * 10);
    return scene;
  }

  // returns the color of the cell clicked, or white if the mouse is clicked
  // outside the range of the board
  public Color getCellColor(Posn pos) {
    for (ArrayList<Cell> col : this.board) {
      for (Cell cell : col) {
        if (pos.x < cell.x + 20 && pos.x >= cell.x) {
          if (pos.y < cell.y + 20 && pos.y >= cell.y) {
            return cell.color;
          }
        }
      }
    }
    return Color.white;
  }

  // EFFECT: updates the list we want to draw, updates the cells that should be
  // flooded,
  // updates the variable to determine if the game is over, updates the times,
  // updates the cell color ,
  // updates the image of the board, adds 1 to the sum of this.sum
  // makes the wave smoother and improves the graphics
  public void onTick() {
    // updates the drawList field
    ArrayList<Cell> localList = new ArrayList<Cell>();
    for (Cell cell : this.drawList) {
      localList.add(cell);
    }
    for (Cell cell : localList) {
      this.drawList = cell.top.drawListHelp(this.drawList);
      this.drawList = cell.bottom.drawListHelp(this.drawList);
      this.drawList = cell.left.drawListHelp(this.drawList);
      this.drawList = cell.right.drawListHelp(this.drawList);
    }

    // sets cells to flooded based on the topLeft field
    this.board.get(0).get(0).floodedHelper(this.topLeft, new ArrayList<ICell>());

    // updates the theGameIsOver field
    this.theGameIsOver = this.allSameColor() || (this.guessesSoFar > this.guessesAllowed);

    // updates the timer field
    this.drawTimer();

    // changes the color of the cells in draw list based on their position in the
    // board
    for (Cell cell : this.drawList) {
      if (this.equalsSum(cell) == this.sum) {
        cell.color = this.topLeft;
      }
    }
    
    // draws the scene
    this.makeScene();
    
    // checks if theGameIsOver field is false
    if (this.theGameIsOver) {
      this.worldEnds();
    }
    
    // updates the sum field
    this.sum += 1;
  }

  // calculates the current diagonal we are on so we can determine which one to
  // update to make the waterfall
  public int equalsSum(Cell cell) {
    int currX = cell.x / 10;
    int currY = cell.y / 10;
    int row = 0;
    int col = 0;
    while (currX > 1) {
      currX -= 2;
      row += 1;
    }
    while (currY > 1) {
      currY -= 2;
      col += 1;
    }
    return row + col;
  }

  // EFFECT: updates the arraylist<arraylist<cell>> with the randomly generated
  // cells
  // initalizes the board and fills the rows and columns with cells
  public ArrayList<ArrayList<Cell>> initBoard() {
    ArrayList<ArrayList<Cell>> newBoard = new ArrayList<ArrayList<Cell>>(this.width);

    int initX = 0;
    int initY = 0;
    for (int col = 0; col < this.width; col++) {
      ArrayList<Cell> newCol = new ArrayList<Cell>(this.height);
      initY = 0;
      for (int row = 0; row < this.height; row++) {
        newCol.add(new Cell(initX, initY, this.randColor(), false));
        initY += 20;
      }
      newBoard.add(newCol);
      initX += 20;
    }
    return newBoard;
  }

  // picks a random color for the cell
  public Color randColor() {
    int randInt = this.rand.nextInt(this.numColors);
    if (randInt == 0) {
      return Color.red;
    }
    else if (randInt == 1) {
      return Color.yellow;
    }
    else if (randInt == 2) {
      return Color.blue;
    }
    else if (randInt == 3) {
      return Color.green;
    }
    else if (randInt == 4) {
      return Color.magenta;
    }
    else if (randInt == 5) {
      return Color.orange;
    }
    else if (randInt == 6) {
      return Color.cyan;
    }
    else {
      return Color.gray;
    }
  }

  // EFFECT: updates the current board by adding columns to it to create the board
  // draws the board from the given list
  public WorldImage drawBoard() {
    WorldImage currentBoard = new RectangleImage(0, 0, OutlineMode.SOLID, Color.white);
    for (ArrayList<Cell> col : this.board) {
      WorldImage currentCol = col.get(0).drawCell();
      for (int i = 1; i < col.size(); i++) {
        currentCol = new AboveImage(currentCol, col.get(i).drawCell());
      }
      currentBoard = new BesideImage(currentBoard, currentCol);
    }
    return currentBoard;
  }

  // EFFECT: updates all the cells with a top, bottom, left and right
  // assigns all the cells a top, bottom, left and right
  public void assignAllCells() {
    for (int col = 0; col < this.board.size(); col++) {
      for (int row = 0; row < this.board.get(col).size(); row++) {
        ICell currCell = this.board.get(col).get(row);
        ICell top = new MtCell();
        ICell bottom = new MtCell();
        ICell left = new MtCell();
        ICell right = new MtCell();

        if (row != 0) {
          top = this.board.get(col).get(row - 1);
        }

        if (row != this.board.get(col).size() - 1) {
          bottom = this.board.get(col).get(row + 1);
        }

        if (col != 0) {
          left = this.board.get(col - 1).get(row);
        }

        if (col != this.board.size() - 1) {
          right = this.board.get(col + 1).get(row);
        }

        currCell.assignCell(top, bottom, left, right);
      }
    }
  }

  // EFFECT: when the r key is pressed, resets the board back to the original
  // constructor, and sets
  // all parameters back to the default
  @Override
  public void onKeyEvent(String key) {
    if (key.equals("r")) {
      this.board = this.initBoard();
      this.assignAllCells();
      this.topLeft = this.board.get(0).get(0).initColor();
      this.board.get(0).get(0).setFlooded();
      this.drawList = new ArrayList<Cell>();
      this.drawList.add(this.board.get(0).get(0));
      this.floodedCells = new ArrayList<ICell>();
      this.floodedCells.add(this.board.get(0).get(0));
      this.sum = 0;
      this.guessesSoFar = 0;
      this.guessesAllowed = this.calcGuesses();
      this.theGameIsOver = this.guessesAllowed == this.guessesSoFar && !this.allSameColor();
      this.timer = 0;
    }
  }

  // purely used for testing to see if we won the game// do it one array list
  // EFFECT: changes the cell of all of the cells to the color we give it
  public void changeCellColor(Color color) {
    for (ArrayList<Cell> col : this.board) {
      for (Cell cell : col) {
        cell.color = color;
      }
    }
  }
}