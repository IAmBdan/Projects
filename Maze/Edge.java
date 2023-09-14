import java.util.HashMap;
import java.util.Set;

import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

//class to represent the "walls" of the maze
class Edge {
  // the first vertex that is linked by the wall
  Vertex start;
  //the second vertex that is linked by the wall
  Vertex end;
  // random weight of the wall
  int weight;
  // which way is the wall
  String orientation;
  // how big is the cell created
  int cellSize;

  Edge(Vertex start, Vertex end, int weight, String orientation, int cellSize) {
    this.start = start;
    this.end = end;
    this.weight = weight;
    this.orientation = orientation;
    this.cellSize = cellSize;
  }

  // draws the edge
  public WorldImage drawEdge() {
    if (this.orientation.equals("vertical")) {
      return new LineImage(new Posn(0, this.cellSize), Color.black);
    }
    else {
      return new LineImage(new Posn(this.cellSize, 0), Color.black);
    }
  }

  // places the edge on the scene
  public WorldScene placeEdge(WorldScene scene) {
    if (this.orientation.endsWith("vertical")) {
      this.end.drawEdgeHelp(scene, 0, this.cellSize / 2, this);
    }
    else {
      this.end.drawEdgeHelp(scene, this.cellSize / 2, 0, this);
    }
    return scene;
  }

  // finds the value of the given key
  public Vertex find(HashMap<Vertex, Vertex> represent, String vert) {
    if (vert.equals("start")) {
      return represent.get(this.start);
    }
    else {
      return represent.get(this.end);
    }
  }

  // replaces any value in the hashmap that equals Y with X
  // EFFECT: goes through the list given and updates everything in the list 
  // with the correct representitive
  public void union(HashMap<Vertex, Vertex> represent, Vertex x, Vertex y) {
    Set<Vertex> setOfKeys = represent.keySet();
    for (Vertex key : setOfKeys) {
      if (represent.get(key).equals(y)) {
        represent.replace(key, x);
      }
    }
  }
}