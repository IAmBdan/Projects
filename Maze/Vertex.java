import java.awt.Color;
import java.util.ArrayList;

import javalib.impworld.*;
import javalib.worldimages.*;

//Vertex Class represents the blank spaces of the maze
class Vertex {
  // x position
  int x;
  // y position
  int y;
  // width of board
  int width;
  // distance from one wall to a parallel one
  int cellSize;
  // list of viable edge connect to the vertex
  ArrayList<Edge> connEdge;
  // Color of the vertex
  Color color;

  Vertex(int x, int y, int width, int cellSize) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.cellSize = cellSize;
    this.connEdge = new ArrayList<Edge>();
    this.color = Color.GRAY;
  }

  // updates the hashCode so 2 values cannot be the same
  @Override
  public int hashCode() {
    return (this.y / this.cellSize + 1) + (this.x / this.cellSize * this.width);
  }

  // overrides equals and checks if the objects are the same by its fields
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Vertex)) {
      return false;
    }

    Vertex that = (Vertex) obj;

    return this.x == that.x && this.y == that.y && this.width == that.width;
  }

  // helper method to draw an edge
  public WorldScene drawEdgeHelp(WorldScene scene, int xDis, int yDis, Edge edge) {
    scene.placeImageXY(edge.drawEdge(), this.x + xDis, this.y + yDis);
    return scene;
  }

  // draws a vertex on a worldScne
  public WorldScene drawVertex(WorldScene scene) {
    scene.placeImageXY(
        new RectangleImage(this.cellSize, this.cellSize, OutlineMode.SOLID, this.color),
        this.x + this.cellSize / 2, this.y + this.cellSize / 2);
    return scene;
  }

  // EFFECT: changes the color of the vertex to the given color
  public void changeColor(Color color) {
    this.color = color;
  }

}