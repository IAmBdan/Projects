import java.util.ArrayList;
import java.awt.Color;
import javalib.worldimages.*;

// interface to represent a Cell whether it is in play or not
interface ICell {
  // draws the individual cell
  WorldImage drawCell();

  // EFFECT: assigns the top, bottom, left and right of a cell
  void assignCell(ICell top, ICell bottom, ICell left, ICell right);

  // gets the initial color of the top left cell
  Color initColor();

  // EFFECT: updates the cell to flooded if the given color and this color is the
  // same
  boolean checkColor(Color currColor);

  // EFFECT: sets the top left cell to flooded
  void setFlooded();

  // EFFECT: changes the cell color to the given color
  void changeColor(Color color);

  // EFFECT: checks to see if we checked the adjacent cells already (stored in the
  // checked list) if we didn't we call a helper on that cell
  void floodedHelper(Color color, ArrayList<ICell> checked);

  // EFFECT: checks the cell color with the color of the top left cell and then
  // adds that cell to the checked list
  public void floodedAbs(Color color, Cell init, ArrayList<ICell> checked);

  // checks to see if the cell we called this on is flooded or not
  public boolean isFlooded();

  // creates a list of the flooded cells we would like to draw
  public ArrayList<Cell> drawListHelp(ArrayList<Cell> drawList);
}

//Represents a single square of the game area
class Cell implements ICell {
  // In logical coordinates, with the origin at the top-left corner of the screen
  int x;
  int y;
  Color color;
  boolean flooded;
  // the four adjacent cells to this one
  ICell left;
  ICell top;
  ICell right;
  ICell bottom;

  Cell(int x, int y, Color color, boolean flooded) {
    this.x = x;
    this.y = y;
    this.color = color;
    this.flooded = false;

    this.left = new MtCell();
    this.top = new MtCell();
    this.right = new MtCell();
    this.bottom = new MtCell();
  }

  // draws the individual cell
  // EXTRA CREDIT: Improved graphics
  public WorldImage drawCell() {
    return new OverlayImage(new RectangleImage(16, 16, OutlineMode.SOLID, this.color),
        new RectangleImage(20, 20, OutlineMode.SOLID, this.color.darker()));
  }

  // EFFECT: assigns the top, bottom, left and right of a cell
  public void assignCell(ICell top, ICell bottom, ICell left, ICell right) {
    this.top = top;
    this.bottom = bottom;
    this.left = left;
    this.right = right;
  }

  // gets the initial color of the top left cell
  public Color initColor() {
    return this.color;
  }

  // EFFECT: updates the flooded field if the given color and this color is the
  // same
  public boolean checkColor(Color currColor) {
    this.flooded = this.color.equals(currColor);
    return this.color.equals(currColor);
  }

  // EFFECT: sets the top left cell to flooded
  public void setFlooded() {
    this.flooded = true;
  }

  // EFFECT: sets the cell color to the given color
  public void changeColor(Color color) {
    this.color = color;
  }

  // EFFECT: checks to see if we checked the adjacent cells already (stored in the
  // checked list) if we didn't we call a helper on that cell
  public void floodedHelper(Color color, ArrayList<ICell> checked) {
    if (!(checked.contains(this.top))) {
      this.top.floodedAbs(color, this, checked);
    }
    if (!(checked.contains(this.left))) {
      this.left.floodedAbs(color, this, checked);
    }
    if (!(checked.contains(this.right))) {
      this.right.floodedAbs(color, this, checked);
    }
    if (!(checked.contains(this.bottom))) {
      this.bottom.floodedAbs(color, this, checked);
    }
  }

  // EFFECT: checks the cell color with the color of the top left cell and then
  // adds that cell to the checked list
  public void floodedAbs(Color color, Cell init, ArrayList<ICell> checked) {
    if (this.checkColor(color)) {
      this.checkColor(color);
      checked.add(init);
      this.floodedHelper(color, checked);
    }
  }

  // checks to see if the cell we called this on is flooded or not
  public boolean isFlooded() {
    return this.flooded;
  }

  // creates a list of the flooded cells we would like to draw
  public ArrayList<Cell> drawListHelp(ArrayList<Cell> drawList) {
    if (!(drawList.contains(this))) {
      if (this.isFlooded()) {
        drawList.add(this);
      }
    }
    return drawList;
  }
}

// Represents an empty cell that is not in play
class MtCell implements ICell {
  MtCell() {
  }

  // draws the individual cell
  public WorldImage drawCell() {
    throw new RuntimeException("Cannot call drawCell on an empty cell");
  }

  // assigns the top, bottom, left and right of a cell
  public void assignCell(ICell top, ICell bottom, ICell left, ICell right) {
    throw new RuntimeException("Cannot call assignCell on an empty cell");
  }

  // gets the initial color of the top left cell
  public Color initColor() {
    throw new RuntimeException("Cannot return a color of an empty cell");
  }

  // if checkColor is called on an MtCell nothing happens since the method is void
  public boolean checkColor(Color currColor) {
    return false;
  }

  // sets the top left cell to flooded
  public void setFlooded() {
    throw new RuntimeException("Cannot call setFlooded on an empty cell");
  }

  // EFFECT: sets the cell color to the given color
  public void changeColor(Color color) {
    // when called on an MtCell the method does not update anything
  }

  // if floddedHelper is called on an MtCell, we throw an exception because you
  // can not access info of an empty cell
  public void floodedHelper(Color color, ArrayList<ICell> checked) {
    throw new RuntimeException("Cannot call floodedHelper on an empty cell");
  }

  // floodedHelper on an MtCells does nothing because checkColor will always
  // return false on an MtCell
  public void floodedAbs(Color color, Cell init, ArrayList<ICell> checked) {
    if (this.checkColor(color)) {
      this.floodedHelper(color, checked);
    }
  }

  // checks to see if the cell is flooded or not
  public boolean isFlooded() {
    return false;
  }

  // since we cannot draw an empty cell it just returns the empty list
  public ArrayList<Cell> drawListHelp(ArrayList<Cell> drawList) {
    return drawList;
  }
}