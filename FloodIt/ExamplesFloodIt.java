import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import tester.Tester;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

//example class
class ExamplesFloodIt {

  // example FloodItWorlds
  FloodItWorld world1 = new FloodItWorld(10, 8, new Random(1));
  FloodItWorld world2;
  FloodItWorld world3 = new FloodItWorld(10, 3, new Random(1));
  FloodItWorld world4 = new FloodItWorld(10, 3, new Random(1));
  FloodItWorld world5;
  FloodItWorld world6;
  FloodItWorld world7;

  // example cells
  Cell cell1;
  Cell cell2;
  Cell cell3;

  Cell cella1 = new Cell(0, 0, Color.orange, false);
  Cell cella2 = new Cell(0, 20, Color.red, false);
  Cell cella3 = new Cell(0, 40, Color.green, false);

  Cell cellb1 = new Cell(20, 0, Color.green, false);
  Cell cellb2 = new Cell(20, 20, Color.yellow, false);
  Cell cellb3 = new Cell(20, 40, Color.red, false);

  Cell cellc1 = new Cell(40, 0, Color.blue, false);
  Cell cellc2 = new Cell(40, 20, Color.orange, false);
  Cell cellc3 = new Cell(40, 40, Color.gray, false);

  ICell mtCell = new MtCell();
  ICell mtVoid;

  // example columns of cells for a board
  ArrayList<Cell> col1 = new ArrayList<Cell>(Arrays.asList(this.cella1, this.cella2, this.cella3));
  ArrayList<Cell> col2 = new ArrayList<Cell>(Arrays.asList(this.cellb1, this.cellb2, this.cellb3));
  ArrayList<Cell> col3 = new ArrayList<Cell>(Arrays.asList(this.cellc1, this.cellc2, this.cellc3));

  // example board
  ArrayList<ArrayList<Cell>> world2Board = new ArrayList<ArrayList<Cell>>(
      Arrays.asList(this.col1, this.col2, this.col3));

  // example images of cells
  WorldImage imgcella1 = new OverlayImage(
      new RectangleImage(16, 16, OutlineMode.SOLID, Color.orange),
      new RectangleImage(20, 20, OutlineMode.SOLID, Color.orange.darker()));
  WorldImage imgcella2 = new OverlayImage(new RectangleImage(16, 16, OutlineMode.SOLID, Color.red),
      new RectangleImage(20, 20, OutlineMode.SOLID, Color.red.darker()));
  WorldImage imgcella3 = new OverlayImage(
      new RectangleImage(16, 16, OutlineMode.SOLID, Color.green),
      new RectangleImage(20, 20, OutlineMode.SOLID, Color.green.darker()));

  WorldImage imgcellb1 = new OverlayImage(
      new RectangleImage(16, 16, OutlineMode.SOLID, Color.green),
      new RectangleImage(20, 20, OutlineMode.SOLID, Color.green.darker()));
  WorldImage imgcellb2 = new OverlayImage(
      new RectangleImage(16, 16, OutlineMode.SOLID, Color.yellow),
      new RectangleImage(20, 20, OutlineMode.SOLID, Color.yellow.darker()));
  WorldImage imgcellb3 = new OverlayImage(new RectangleImage(16, 16, OutlineMode.SOLID, Color.red),
      new RectangleImage(20, 20, OutlineMode.SOLID, Color.red.darker()));

  WorldImage imgcellc1 = new OverlayImage(new RectangleImage(16, 16, OutlineMode.SOLID, Color.blue),
      new RectangleImage(20, 20, OutlineMode.SOLID, Color.blue.darker()));
  WorldImage imgcellc2 = new OverlayImage(
      new RectangleImage(16, 16, OutlineMode.SOLID, Color.orange),
      new RectangleImage(20, 20, OutlineMode.SOLID, Color.orange.darker()));
  WorldImage imgcellc3 = new OverlayImage(new RectangleImage(16, 16, OutlineMode.SOLID, Color.gray),
      new RectangleImage(20, 20, OutlineMode.SOLID, Color.gray.darker()));

  // example images of columns
  WorldImage imgcol1 = new AboveImage(new AboveImage(this.imgcella1, this.imgcella2),
      this.imgcella3);
  WorldImage imgcol2 = new AboveImage(new AboveImage(this.imgcellb1, this.imgcellb2),
      this.imgcellb3);
  WorldImage imgcol3 = new AboveImage(new AboveImage(this.imgcellc1, this.imgcellc2),
      this.imgcellc3);

  // the initial image to start a board
  WorldImage initDrawImage = new BesideImage(
      new RectangleImage(0, 0, OutlineMode.SOLID, Color.white), this.imgcol1);

  // example image of a board
  WorldImage imgboard1 = new BesideImage(new BesideImage(this.initDrawImage, this.imgcol2),
      this.imgcol3);

  // background scene for the example board
  WorldScene background2 = new WorldScene(200, 200);

  // initializes data that needs to be reset after a test
  void initData() {
    cell1 = new Cell(0, 0, Color.orange, false);
    cell2 = new Cell(20, 0, Color.red, false);
    cell3 = new Cell(0, 20, Color.red, false);

    mtVoid = new MtCell();

    world2 = new FloodItWorld(3, 8, new Random(1));
    world2Board.get(0).get(0).setFlooded();

    world5 = new FloodItWorld(26, 16, 8, new Random(2));

    world6 = new FloodItWorld(3, 8, new Random(2));

    world7 = new FloodItWorld(3, 3, new Random(1));
  }

  // runs the game by creating a world and calling bigBang
  void testFloodItWorld(Tester t) {
    this.initData();
    FloodItWorld starterWorld = new FloodItWorld(20, 8);
    int sceneSize = starterWorld.width * 20;
    int sceneHeight = starterWorld.height * 20;
    if (starterWorld.width < 6) {
      starterWorld.bigBang(sceneSize + 100, sceneHeight, .01);
    }
    else {
      starterWorld.bigBang(sceneSize, sceneHeight + 75, .01);
    }

  }

  // to test exceptions
  void testExceptions(Tester t) {
    this.initData();
    t.checkConstructorException(new IllegalArgumentException("2 is below the minimum board size."),
        "FloodItWorld", 2, 4);
    t.checkConstructorException(new IllegalArgumentException("30 is above the maximum board size."),
        "FloodItWorld", 30, 4);
    t.checkConstructorException(new IllegalArgumentException("2 is below the minimum board size."),
        "FloodItWorld", 4, 2, 5);
    t.checkConstructorException(new IllegalArgumentException("30 is above the maximum board size."),
        "FloodItWorld", 4, 30, 5);
    t.checkConstructorException(
        new IllegalArgumentException("1 is below the minimum number of colors."), "FloodItWorld", 4,
        1);
    t.checkConstructorException(
        new IllegalArgumentException("10 is above the maximum number of colors."), "FloodItWorld",
        4, 10);
    t.checkException("Test for calling drawCell on an MtCell",
        new RuntimeException("Cannot call drawCell on an empty cell"), new MtCell(), "drawCell");
    t.checkException("Test for calling assignCell on an MtCell",
        new RuntimeException("Cannot call assignCell on an empty cell"), new MtCell(), "assignCell",
        new MtCell(), new MtCell(), new MtCell(), new MtCell());
    t.checkException("Test for calling initColor on an MtCell",
        new RuntimeException("Cannot return a color of an empty cell"), new MtCell(), "initColor");
    t.checkException("Test for calling setFlooded on an MtCell",
        new RuntimeException("Cannot call setFlooded on an empty cell"), new MtCell(),
        "setFlooded");
    t.checkException("Test for calling floodedHelper on an MtCell",
        new RuntimeException("Cannot call floodedHelper on an empty cell"), new MtCell(),
        "floodedHelper", Color.red, new ArrayList<ICell>());
  }

  ////////// FLOOD IT WORLD METHODS //////////

  // to test the method initBoard
  boolean testInitBoard(Tester t) {
    this.initData();
    // assigns the top, bottom, left, and right to the cells created to test
    // initBoard
    this.cella1.assignCell(new MtCell(), this.cella2, new MtCell(), this.cellb1);
    this.cella2.assignCell(this.cella1, this.cella3, new MtCell(), this.cellb2);
    this.cella3.assignCell(this.cella2, new MtCell(), new MtCell(), this.cellb3);
    this.cellb1.assignCell(new MtCell(), this.cellb2, this.cella1, this.cellc1);
    this.cellb2.assignCell(this.cellb1, this.cellb3, this.cella2, this.cellc2);
    this.cellb3.assignCell(this.cellb2, new MtCell(), this.cella3, this.cellc3);
    this.cellc1.assignCell(new MtCell(), this.cellc2, this.cellb1, new MtCell());
    this.cellc2.assignCell(this.cellc1, this.cellc3, this.cellb2, new MtCell());
    this.cellc3.assignCell(this.cellc2, new MtCell(), this.cellb3, new MtCell());
    return t.checkExpect(this.world2.board, this.world2Board);
  }

  // to test the method drawBoard
  boolean testDrawBoard(Tester t) {
    this.initData();
    return t.checkExpect(this.world2.drawBoard(), this.imgboard1);
  }

  // to test the method makeScene
  boolean testMakeScene(Tester t) {
    this.background2.placeImageXY(this.world1.drawBoard(), 100, 100);
    return t.checkExpect(this.world1.makeScene(), this.background2);
  }

  // to test the method randColor
  // random int sequence for new Random(1):
  // 5, 0, 3, 3, 1, 0, 2, 5, 7, 5, 0, 1
  // 5 returns orange, 0 returns red, 1 returns yellow, the first 9 numbers were
  // used in initBoard
  boolean testRandomColor(Tester t) {
    this.initData();
    return t.checkExpect(this.world2.randColor(), Color.orange)
        && t.checkExpect(this.world2.randColor(), Color.red)
        && t.checkExpect(this.world2.randColor(), Color.yellow);
  }

  // to test the method assignCells
  void testAssignAllCells(Tester t) {
    this.initData();

    // Check top assignment
    t.checkExpect(this.world2.board.get(0).get(0).top, new MtCell());
    t.checkExpect(this.world2.board.get(0).get(1).top, this.world2.board.get(0).get(0));
    t.checkExpect(this.world2.board.get(0).get(2).top, this.world2.board.get(0).get(1));

    // Check bottom assignment
    t.checkExpect(this.world2.board.get(0).get(2).bottom, new MtCell());
    t.checkExpect(this.world2.board.get(0).get(1).bottom, this.world2.board.get(0).get(2));
    t.checkExpect(this.world2.board.get(0).get(0).bottom, this.world2.board.get(0).get(1));

    // Check left assignment
    t.checkExpect(this.world2.board.get(0).get(0).left, new MtCell());
    t.checkExpect(this.world2.board.get(1).get(0).left, this.world2.board.get(0).get(0));
    t.checkExpect(this.world2.board.get(2).get(0).left, this.world2.board.get(1).get(0));

    // Check left assignment
    t.checkExpect(this.world2.board.get(2).get(0).right, new MtCell());
    t.checkExpect(this.world2.board.get(1).get(0).right, this.world2.board.get(2).get(0));
    t.checkExpect(this.world2.board.get(0).get(0).right, this.world2.board.get(1).get(0));
  }

  // to test the method getCellColor
  boolean testGetCellColor(Tester t) {
    this.initData();
    return t.checkExpect(this.world2.getCellColor(new Posn(0, 0)), Color.orange)
        && t.checkExpect(this.world2.getCellColor(new Posn(10, 10)), Color.orange)
        && t.checkExpect(this.world2.getCellColor(new Posn(30, 30)), Color.yellow)
        && t.checkExpect(this.world2.getCellColor(new Posn(59, 59)), Color.gray)
        && t.checkExpect(this.world2.getCellColor(new Posn(60, 60)), Color.white)
        && t.checkExpect(this.world2.getCellColor(new Posn(100, 100)), Color.white);
  }

  // to test the method onMouseClicked
  void testOnMouseClicked(Tester t) {
    this.initData();
    this.world2.onMouseClicked(new Posn(100, 100));
    t.checkExpect(this.world2.topLeft, Color.orange);
    t.checkExpect(this.world2.sum, 0);
    this.world2.onMouseClicked(new Posn(50, 30));
    t.checkExpect(this.world2.topLeft, Color.orange);
    t.checkExpect(this.world2.sum, 0);
    this.world2.onMouseClicked(new Posn(30, 30));
    t.checkExpect(this.world2.topLeft, Color.yellow);
    t.checkExpect(this.world2.sum, 0);
  }

  // to test the method allSameColor
  boolean testAllSameColor(Tester t) {
    this.initData();
    this.world6.changeCellColor(Color.black);
    this.world6.topLeft = Color.black;
    this.world6.board.get(0).get(0).floodedHelper(this.world6.topLeft, new ArrayList<ICell>());
    return t.checkExpect(this.world1.allSameColor(), false)
        && t.checkExpect(this.world6.allSameColor(), true);
  }

  // to test the method calcGuesses
  boolean testCalcGuesses(Tester t) {
    this.initData();
    return t.checkExpect(this.world1.calcGuesses(), 26)
        && t.checkExpect(this.world3.calcGuesses(), 10)
        && t.checkExpect(this.world5.calcGuesses(), 53);
  }

  // to test the method drawScore
  boolean testDrawScore(Tester t) {
    this.initData();
    this.world5.guessesSoFar = 7;
    return t.checkExpect(this.world1.drawScore(),
        new TextImage(String.valueOf(0) + "/" + String.valueOf(26), 20, Color.BLACK))
        && t.checkExpect(this.world5.drawScore(),
            new TextImage(String.valueOf(7) + "/" + String.valueOf(53), 20, Color.BLACK));
  }

  // to test the method drawTimer
  boolean testDrawTimer(Tester t) {
    this.initData();
    this.world5.timer = 6.56;
    return t.checkExpect(this.world1.drawTimer(), new TextImage("Time: " + 0 + "", 20, Color.BLACK))
        && t.checkExpect(this.world5.drawTimer(),
            new TextImage("Time: " + 7 + "", 20, Color.BLACK));
  }

  // to test the method worldEnds
  boolean testWorldEnds(Tester t) {
    this.initData();
    this.world2.theGameIsOver = true;
    this.world5.theGameIsOver = true;
    this.world5.changeCellColor(Color.black);
    this.world5.topLeft = Color.black;
    this.world5.board.get(0).get(0).floodedHelper(this.world5.topLeft, new ArrayList<ICell>());
    this.world5.guessesSoFar = 60;
    this.world6.theGameIsOver = true;
    this.world6.changeCellColor(Color.black);
    this.world6.topLeft = Color.black;
    this.world6.board.get(0).get(0).floodedHelper(this.world6.topLeft, new ArrayList<ICell>());
    return t.checkExpect(this.world1.worldEnds(), new WorldEnd(false, this.world1.makeScene()))
        && t.checkExpect(this.world2.worldEnds(),
            new WorldEnd(true, this.world2.lastScene("you lose")))
        && t.checkExpect(this.world5.worldEnds(),
            new WorldEnd(true, this.world5.lastScene("you lose")))
        && t.checkExpect(this.world6.worldEnds(),
            new WorldEnd(true, this.world6.lastScene("you win")));
  }

  // to test the method lastScene
  boolean testLastScene(Tester t) {
    this.initData();
    WorldScene scene1 = new WorldScene(this.world1.width * 20, this.world1.height * 20);
    WorldImage textBack1 = new TextImage("you lose", this.world1.width * 4, Color.black);
    scene1.placeImageXY(textBack1, this.world1.width * 10, this.world1.height * 10);
    WorldScene scene2 = new WorldScene(this.world1.width * 20, this.world1.height * 20);
    WorldImage textBack2 = new TextImage("you win", this.world1.width * 4, Color.black);
    scene2.placeImageXY(textBack2, this.world1.width * 10, this.world1.height * 10);
    return t.checkExpect(this.world1.lastScene("you lose"), scene1)
        && t.checkExpect(this.world1.lastScene("you win"), scene2);
  }

  // to test equal sum method
  boolean testEqualsSum(Tester t) {
    return t.checkExpect(this.world1.equalsSum(cell2), 1)
        && t.checkExpect(this.world1.equalsSum(cell3), 1)
        && t.checkExpect(this.world3.equalsSum(cellc3), 4)
        && t.checkExpect(this.world3.equalsSum(cellc2), 3)
        && t.checkExpect(this.world3.equalsSum(cellc1), 2)
        && t.checkExpect(this.world3.equalsSum(cella1), 0)
        && t.checkExpect(this.world3.equalsSum(cella2), 1);
  }

  // to test the method onKeyEvent
  void testOnKeyEvent(Tester t) {
    this.initData();
    FloodItWorld boardTest = new FloodItWorld(3, 3, new Random(1));
    boardTest.board = boardTest.initBoard();
    boardTest.assignAllCells();
    boardTest.topLeft = boardTest.board.get(0).get(0).initColor();
    boardTest.board.get(0).get(0).setFlooded();
    this.world7.onKeyEvent("r");

    t.checkExpect(this.world7.board, boardTest.board);
    t.checkExpect(this.world7.topLeft, Color.yellow);
    t.checkExpect(this.world7.drawList,
        new ArrayList<Cell>(Arrays.asList(this.world7.board.get(0).get(0))));
    t.checkExpect(this.world7.floodedCells,
        new ArrayList<ICell>(Arrays.asList(this.world7.board.get(0).get(0))));
    t.checkExpect(this.world7.sum, 0);
    t.checkExpect(this.world7.guessesSoFar, 0);
    t.checkExpect(this.world7.guessesAllowed, 3);
    t.checkExpect(this.world7.theGameIsOver, false);
    t.checkExpect(this.world7.timer, 0.0);

  }

  // to test the method onTick
  void testOnTick(Tester t) {
    this.initData();
    FloodItWorld boardTest = new FloodItWorld(3, 3, new Random(1));
    t.checkExpect(this.world7.board, boardTest.board);
    t.checkExpect(this.world7.topLeft, Color.red);
    t.checkExpect(this.world7.drawList,
        new ArrayList<Cell>(Arrays.asList(this.world7.board.get(0).get(0))));
    t.checkExpect(this.world7.floodedCells,
        new ArrayList<ICell>(Arrays.asList(this.world7.board.get(0).get(0))));
    t.checkExpect(this.world7.sum, 0);
    t.checkExpect(this.world7.guessesSoFar, 0);
    t.checkExpect(this.world7.guessesAllowed, 3);
    t.checkExpect(this.world7.theGameIsOver, false);
    t.checkExpect(this.world7.timer, 0.0);
    this.world7.onTick();
    boardTest.board.get(0).get(0).floodedHelper(boardTest.topLeft, new ArrayList<ICell>());
    t.checkExpect(this.world7.board, boardTest.board);
    t.checkExpect(this.world7.topLeft, Color.red);
    t.checkExpect(this.world7.drawList,
        new ArrayList<Cell>(Arrays.asList(this.world7.board.get(0).get(0))));
    t.checkExpect(this.world7.floodedCells,
        new ArrayList<ICell>(Arrays.asList(this.world7.board.get(0).get(0))));
    t.checkExpect(this.world7.sum, 1);
    t.checkExpect(this.world7.guessesSoFar, 0);
    t.checkExpect(this.world7.guessesAllowed, 3);
    t.checkExpect(this.world7.theGameIsOver, false);
    t.checkExpect(this.world7.timer, 0.01);
    this.world7.onTick();
    t.checkExpect(this.world7.sum, 2);
    t.checkExpect(this.world7.timer, 0.02);
  }

  ////////// CELL METHODS //////////

  // to test the method drawCell
  boolean testDrawCell(Tester t) {
    this.initData();
    return t.checkExpect(this.cell1.drawCell(),
        new OverlayImage(new RectangleImage(16, 16, OutlineMode.SOLID, Color.orange),
            new RectangleImage(20, 20, OutlineMode.SOLID, Color.orange.darker())))
        && t.checkExpect(this.cell2.drawCell(),
            new OverlayImage(new RectangleImage(16, 16, OutlineMode.SOLID, Color.red),
                new RectangleImage(20, 20, OutlineMode.SOLID, Color.red.darker())));
  }

  // to test the method assignCell
  void testAssignCell(Tester t) {
    this.initData();
    this.cell1.assignCell(new MtCell(), this.cell2, new MtCell(), this.cell3);
    t.checkExpect(this.cell1.top, new MtCell());
    t.checkExpect(this.cell1.bottom, this.cell2);
    t.checkExpect(this.cell1.left, new MtCell());
    t.checkExpect(this.cell1.right, this.cell3);
  }

  // to test the method initColor
  boolean testInitColor(Tester t) {
    this.initData();
    return t.checkExpect(this.world2.topLeft, Color.orange)
        && t.checkExpect(this.world1.topLeft, Color.orange);
  }

  // to test the method checkColor
  boolean testCheckColor(Tester t) {
    this.initData();
    return t.checkExpect(this.cell1.checkColor(Color.yellow), false)
        && t.checkExpect(this.cell1.flooded, false)
        && t.checkExpect(this.cell1.checkColor(Color.orange), true)
        && t.checkExpect(this.cell1.flooded, true)
        && t.checkExpect(this.mtVoid.checkColor(Color.yellow), false);
  }

  // to test the method setFlooded
  void testSetFlooded(Tester t) {
    this.initData();
    t.checkExpect(this.world1.board.get(0).get(0).flooded, true);
    t.checkExpect(this.world2.board.get(0).get(0).flooded, true);
  }

  // to test the method changeColor
  void testChangeColor(Tester t) {
    this.initData();
    this.world2.board.get(0).get(0).changeColor(Color.red);
    t.checkExpect(this.world2.board.get(0).get(0).color, Color.red);
    this.world2.board.get(0).get(0).changeColor(Color.magenta);
    t.checkExpect(this.world2.board.get(0).get(0).color, Color.magenta);
  }

  // to test the method floodedHelper
  void testFloodedHelper(Tester t) {
    this.initData();
    this.world1.board.get(0).get(0).floodedHelper(this.world1.topLeft, new ArrayList<ICell>());
    t.checkExpect(this.world1.board.get(1).get(0).flooded, false);
    t.checkExpect(this.world1.board.get(0).get(1).flooded, false);
    this.world5.board.get(0).get(0).floodedHelper(this.world5.topLeft, new ArrayList<ICell>());
    t.checkExpect(this.world5.board.get(1).get(0).flooded, true);
  }

  // to test the method floodedAbs
  void testFloodedAbs(Tester t) {
    this.initData();
    this.world5.board.get(1).get(0).floodedAbs(this.world5.topLeft, this.world5.board.get(0).get(0),
        new ArrayList<ICell>());
    t.checkExpect(this.world5.board.get(1).get(0).flooded, true);
    this.world5.board.get(1).get(0).floodedHelper(this.world5.topLeft,
        new ArrayList<ICell>((Arrays.asList(this.world5.board.get(0).get(0)))));
    t.checkExpect(this.world5.board.get(2).get(0).flooded, false);
    t.checkExpect(this.world5.board.get(1).get(1).flooded, false);
    ICell emptyCell = new MtCell();
    emptyCell.floodedAbs(this.world5.topLeft, this.world5.board.get(0).get(0),
        new ArrayList<ICell>());
    // nothing happens because in the MtCell class floodedAbs calls the method
    // checkColor which returns false on an MtCell so nothing is changed
  }

  // to test the method isFlooded
  boolean testIsFlooded(Tester t) {
    this.initData();
    ICell emptyCell = new MtCell();
    return t.checkExpect(this.world1.board.get(0).get(0).isFlooded(), true)
        && t.checkExpect(this.world1.board.get(0).get(1).isFlooded(), false)
        && t.checkExpect(emptyCell.isFlooded(), false);
  }

  // to test the method drawListHelp
  boolean testDrawListHelp(Tester t) {
    this.initData();
    ICell emptyCell = new MtCell();
    ArrayList<Cell> exList = new ArrayList<Cell>(Arrays.asList(this.cell1, this.cell2));
    return t.checkExpect(emptyCell.drawListHelp(exList), exList)
        && t.checkExpect(this.world5.board.get(0).get(0).drawListHelp(exList),
            new ArrayList<Cell>(
                Arrays.asList(this.cell1, this.cell2, this.world5.board.get(0).get(0))))
        && t.checkExpect(this.cell1.drawListHelp(exList), exList);
  }
}