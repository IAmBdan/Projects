import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;

import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

//class that represents our maze
class Maze extends World {
  // width of the board
  int width;
  // height of the board
  int height;
  // how big each "cell" is
  int cellSize;
  // 2d array list of all the vertices
  ArrayList<ArrayList<Vertex>> vertexMap;
  // array list of all of the edges (will create a grid)
  ArrayList<Edge> edges;
  // array list of all of the edges we want to use in our maze (unique maze)
  // different from the list above because this list is "filtered" using the
  // algorithm
  ArrayList<Edge> viableEdges;
  // hash map for our vertexes
  HashMap<Vertex, Vertex> represent;
  // random number
  Random rand;
  // list of seen vertices in the searchTree method
  ArrayList<Vertex> runThrough = new ArrayList<Vertex>();
  // list of vertices in the minimal search tree
  ArrayList<Vertex> mst = new ArrayList<Vertex>();
  // keeps track of the vertex being drawn in onTick
  int currCell = 0;
  // the max random number when assigning vertical edges
  int vertWeight = 35;
  // the max random number when assigning horizontal edges
  int horWeight = 35;
  // represents whether or not the visited paths are visible
  boolean toggle = true;
  // number of wrong moves in breadth first search
  int breadthWrong = 0;
  // number of wrong moves in depth first search
  int depthWrong = 0;
  // number of moves in the minimal path
  int minimalPath = 0;

  // regular maze constructor
  Maze(int width, int height) {
    this.width = width;
    this.height = height;
    int widthSize = Math.round(890 / this.width);
    int heightSize = Math.round(890 / this.height);
    if (widthSize <= heightSize) {
      if (widthSize % 2 != 0) {
        widthSize++;
        this.cellSize = widthSize;
      }
      else {
        this.cellSize = widthSize;
      }
    }
    else {
      if (heightSize % 2 != 0) {
        heightSize++;
        this.cellSize = heightSize;
      }
      else {
        this.cellSize = heightSize;
      }
    }

    this.rand = new Random();
    this.represent = new HashMap<Vertex, Vertex>();
    this.vertexMap = this.initMap();
    this.edges = this.initEdges();
    this.viableEdges = this.kruskalsAlgorithm();
    if (this.width > 100) {
      throw new IllegalArgumentException("The width of your maze cannot exceed 100");
    }
    else if (this.height > 60) {
      throw new IllegalArgumentException("The height of your maze cannot exceed 60");
    }
    else if (this.width < 2) {
      throw new IllegalArgumentException("The width of your maze cannot be less than 2");
    }
    else if (this.height < 2) {
      throw new IllegalArgumentException("The height of your maze cannot be less than 2");
    }
  }

  // seeded random maze constructor
  Maze(int width, int height, Random rand) {
    this.width = width;
    this.height = height;
    int widthSize = Math.round(890 / this.width);
    int heightSize = Math.round(890 / this.height);
    if (widthSize <= heightSize) {
      if (widthSize % 2 != 0) {
        widthSize++;
        this.cellSize = widthSize;
      }
      else {
        this.cellSize = widthSize;
      }
    }
    else {
      if (heightSize % 2 != 0) {
        heightSize++;
        this.cellSize = heightSize;
      }
      else {
        this.cellSize = heightSize;
      }
    }

    this.rand = rand;
    this.represent = new HashMap<Vertex, Vertex>();
    this.vertexMap = this.initMap();
    this.edges = this.initEdges();
    this.viableEdges = this.kruskalsAlgorithm();
    if (this.width > 100) {
      throw new IllegalArgumentException("The width of your maze cannot exceed 100");
    }
    else if (this.height > 60) {
      throw new IllegalArgumentException("The height of your maze cannot exceed 60");
    }
    else if (this.width < 2) {
      throw new IllegalArgumentException("The width of your maze cannot be less than 2");
    }
    else if (this.height < 2) {
      throw new IllegalArgumentException("The height of your maze cannot be less than 2");
    }
  }

  // initialized the vertices
  public ArrayList<ArrayList<Vertex>> initMap() {
    ArrayList<ArrayList<Vertex>> map = new ArrayList<ArrayList<Vertex>>(this.width);
    int initX = 0;
    int initY = 0;
    for (int col = 0; col < this.width; col++) {
      ArrayList<Vertex> newCol = new ArrayList<Vertex>(this.height);
      initY = 0;
      for (int row = 0; row < this.height; row++) {
        newCol.add(new Vertex(initX, initY, this.width, this.cellSize));
        initY += this.cellSize;
        this.represent.put(newCol.get(row), newCol.get(row)); // may b col and row switched
      }
      map.add(newCol);
      initX += this.cellSize;
    }
    return map;
  }

  // assigns the list of edges to each vertex
  public ArrayList<Edge> initEdges() {

    ArrayList<Vertex> checkedList = new ArrayList<Vertex>();
    ArrayList<Edge> edgeList = new ArrayList<Edge>();
    for (int col = 0; col < this.width; col++) {
      for (int row = 0; row < this.height; row++) {
        Vertex current = this.vertexMap.get(col).get(row);

        if (col < this.width - 1) {
          if (!checkedList.contains(this.vertexMap.get(col + 1).get(row))) {
            edgeList.add(new Edge(current, this.vertexMap.get(col + 1).get(row),
                this.rand.nextInt(this.vertWeight), "vertical", this.cellSize));
          }
        }
        if (row < this.height - 1) {
          if (!checkedList.contains(this.vertexMap.get(col).get(row + 1))) {
            edgeList.add(new Edge(current, this.vertexMap.get(col).get(row + 1),
                this.rand.nextInt(this.horWeight), "horizontal", this.cellSize));
          }
        }
        checkedList.add(current);
      }
    }
    return edgeList;
  }

  // draws the scene
  @Override
  public WorldScene makeScene() {
    WorldScene scene;
    if (this.height * this.cellSize < 750) {
      scene = new WorldScene(this.width * this.cellSize, this.height * this.cellSize + 150);
    }
    else {
      scene = new WorldScene(this.width * this.cellSize + 400, this.height * this.cellSize);
    }

    scene.placeImageXY(
        new RectangleImage(this.width * this.cellSize, this.height * this.cellSize,
            OutlineMode.SOLID, Color.gray),
        this.width * this.cellSize / 2, this.height * this.cellSize / 2);

    scene.placeImageXY(
        new RectangleImage(this.cellSize, this.cellSize, OutlineMode.SOLID, Color.green.darker()),
        this.cellSize / 2, this.cellSize / 2);
    scene.placeImageXY(
        new RectangleImage(this.cellSize, this.cellSize, OutlineMode.SOLID, Color.red.darker()),
        this.width * this.cellSize - this.cellSize / 2,
        this.height * this.cellSize - this.cellSize / 2);

    for (ArrayList<Vertex> col : this.vertexMap) {
      for (Vertex v : col) {
        if (!v.color.equals(Color.gray)) {
          v.drawVertex(scene);
        }
      }
    }

    scene.placeImageXY(
        new RectangleImage(this.width * this.cellSize, this.height * this.cellSize,
            OutlineMode.OUTLINE, Color.black),
        this.width * this.cellSize / 2, this.height * this.cellSize / 2);
    for (Edge edge : this.edges) {
      if (!this.viableEdges.contains(edge)) {
        scene = edge.placeEdge(scene);
      }

    }

    // EXTRA CREDIT
    // displays the wrong moves made and the minimum amount of moves
    if (this.height * this.cellSize < 750) {
      scene.placeImageXY(
          new TextImage("BFS Wrong Moves:" + this.breadthWrong + "", 20, Color.black),
          this.width * this.cellSize / 4 - 50, this.height * this.cellSize + 50);
      scene.placeImageXY(new TextImage("DFS Wrong Moves:" + this.depthWrong + "", 20, Color.black),
          this.width * this.cellSize / 2, this.height * this.cellSize + 50);
      scene.placeImageXY(
          new TextImage("Minimum Number of Moves:" + this.minimalPath + "", 20, Color.black),
          this.width * this.cellSize / 4 * 3 + 50, this.height * this.cellSize + 50);
    }
    else {
      scene.placeImageXY(
          new TextImage("BFS Wrong Moves:" + this.breadthWrong + "", 20, Color.black),
          this.width * this.cellSize + 200, 50);
      scene.placeImageXY(new TextImage("DFS Wrong Moves:" + this.depthWrong + "", 20, Color.black),
          this.width * this.cellSize + 200, 100);
      scene.placeImageXY(
          new TextImage("Minimum Number of Moves:" + this.minimalPath + "", 20, Color.black),
          this.width * this.cellSize + 200, 150);
    }

    // EXTRA CREDIT
    // displays which method is faster
    if (this.height * this.cellSize < 750) {
      if (this.breadthWrong < this.depthWrong && this.breadthWrong > 0 && this.depthWrong > 0) {
        scene
            .placeImageXY(
                new TextImage("Breadth first search is the fastest method to search this maze!", 20,
                    Color.black),
                this.width * this.cellSize / 2, this.height * this.cellSize + 100);
      }
      else if (this.breadthWrong > this.depthWrong && this.breadthWrong > 0
          && this.depthWrong > 0) {
        scene
            .placeImageXY(
                new TextImage("Depth first search is the fastest method to search this maze!", 20,
                    Color.black),
                this.width * this.cellSize / 2, this.height * this.cellSize + 100);
      }
    }
    else {
      if (this.breadthWrong < this.depthWrong && this.breadthWrong > 0 && this.depthWrong > 0) {
        scene.placeImageXY(new TextImage("Breadth first search is the fastest", 20, Color.black),
            this.width * this.cellSize + 200, 200);
        scene.placeImageXY(new TextImage("method to search this maze!", 20, Color.black),
            this.width * this.cellSize + 200, 220);
      }
      else if (this.breadthWrong > this.depthWrong && this.breadthWrong > 0
          && this.depthWrong > 0) {
        scene.placeImageXY(new TextImage("Depth first search is the fastest", 20, Color.black),
            this.width * this.cellSize + 200, 200);
        scene.placeImageXY(new TextImage("method to search this maze!", 20, Color.black),
            this.width * this.cellSize + 200, 220);
      }
    }

    return scene;
  }

  // filters the list and finds a minimum spanning tree using the weights of each
  // node
  // first sorts the list based on weight then finds a minimum tree
  public ArrayList<Edge> kruskalsAlgorithm() {
    ArrayList<Edge> workList = new ArrayList<Edge>(this.edges);
    ArrayList<Edge> edgesInTree = new ArrayList<Edge>();
    workList.sort(new WeightCheck());
    while (workList.size() != 0) {
      Edge current = workList.remove(0);
      if (!current.find(this.represent, "start").equals(current.find(this.represent, "end"))) {
        edgesInTree.add(current);
        current.union(this.represent, current.find(this.represent, "start"),
            current.find(this.represent, "end"));
      }
    }
    return edgesInTree;
  }

  public void assignVertexEdges() {
    for (ArrayList<Vertex> col : this.vertexMap) {
      for (Vertex vert : col) {
        for (Edge e : this.viableEdges) {
          if (vert.equals(e.start) || vert.equals(e.end)) {
            vert.connEdge.add(e);
          }
        }
      }
    }
  }

  // EFFECT: updates the runThrough list to contain all the cells that a search
  // went through
  // searches the maze using either depth or breadth first search
  public void searchTree(String key) {
    this.assignVertexEdges();
    HashMap<Vertex, Edge> cameFromEdge = new HashMap<Vertex, Edge>();
    Deque<Vertex> workList = new Deque<Vertex>(new Sentinel<Vertex>());
    ArrayList<Vertex> seen = new ArrayList<Vertex>();
    Vertex target = this.vertexMap.get(this.width - 1).get(this.height - 1);

    workList.addAtHead(this.vertexMap.get(0).get(0));
    Vertex next = this.vertexMap.get(0).get(0);
    while (!next.equals(target)) {
      next = workList.removeFromHead();
      if (!this.runThrough.contains(next)) {
        this.runThrough.add(next);
      }
      if (seen.contains(next)) {
        // do nothing because next has already been seen
      }
      else if (next.equals(target)) {
        this.reconstruct(cameFromEdge, next);
      }
      else {
        for (Edge e : next.connEdge) {
          if (seen.contains(e.start) || seen.contains(e.end)) {
            // do nothing
          }
          else if (next.equals(e.start)) {
            if (key.equals("d")) {
              workList.addAtHead(e.end);
            }
            else if (key.equals("b")) {
              workList.addAtTail(e.end);
            }
            cameFromEdge.put(e.end, e);
          }
          else {
            if (key.equals("d")) {
              workList.addAtHead(e.start);
            }
            else if (key.equals("b")) {
              workList.addAtTail(e.start);
            }
            cameFromEdge.put(e.start, new Edge(next, e.start, e.weight, e.orientation, e.cellSize));
          }
        }
        seen.add(next);
      }
    }
  }

  // EFFECT: creates the minimal path from the start to the end and adds it to the
  // minimal path list
  public void reconstruct(HashMap<Vertex, Edge> cameFromEdge, Vertex next) {
    this.mst.add(next);

    while (cameFromEdge.containsKey(next)) {
      Edge current = cameFromEdge.get(next);
      this.mst.add(current.start);
      next = current.start;
    }

  }

  // EFFECT: changes the color of the cells based on the currCell counter
  @Override
  public void onTick() {
    if (this.currCell < this.runThrough.size()) {
      Vertex current = this.runThrough.get(this.currCell);
      current.changeColor(Color.magenta);
      currCell++;
    }
    else {
      for (Vertex v : this.mst) {
        v.changeColor(Color.blue);
      }
    }
  }

  // EFFECT: changes fields of the world based of a key press, and runs searchTree
  // on a maze when "b" or "d" is pressed
  @Override
  public void onKeyEvent(String key) {

    if (key.equals("d")) {
      // runs a depth first search on the maze
      this.runThrough = new ArrayList<Vertex>();
      this.mst = new ArrayList<Vertex>();
      this.currCell = 0;
      for (ArrayList<Vertex> col : this.vertexMap) {
        for (Vertex v : col) {
          v.changeColor(Color.gray);
        }
      }
      this.searchTree("d");
      this.depthWrong = this.runThrough.size() - this.mst.size();
      this.minimalPath = this.mst.size();
    }
    else if (key.equals("b")) {
      // runs a breadth first search on the maze
      this.runThrough = new ArrayList<Vertex>();
      this.mst = new ArrayList<Vertex>();
      this.currCell = 0;
      for (ArrayList<Vertex> col : this.vertexMap) {
        for (Vertex v : col) {
          v.changeColor(Color.gray);
        }
      }
      this.searchTree("b");
      this.breadthWrong = this.runThrough.size() - this.mst.size();
      this.minimalPath = this.mst.size();
    }
    else if (key.equals("r")) {
      // creates a new random maze
      this.vertWeight = 35;
      this.horWeight = 35;
      this.rand = new Random();
      this.represent = new HashMap<Vertex, Vertex>();
      this.vertexMap = this.initMap();
      this.edges = this.initEdges();
      this.viableEdges = this.kruskalsAlgorithm();
      this.assignVertexEdges();
      this.runThrough = new ArrayList<Vertex>();
      this.mst = new ArrayList<Vertex>();
      this.currCell = 0;
      this.breadthWrong = 0;
      this.depthWrong = 0;
      this.minimalPath = 0;
    }
    else if (key.equals("s")) {

      // skips the animation of depth or breadth first search
      this.currCell = this.runThrough.size();
      for (Vertex v : this.runThrough) {
        v.changeColor(Color.magenta);
      }
    }
    else if (key.equals("v")) {

      // creates a new random maze with a more vertical path
      this.vertWeight = 100;
      this.horWeight = 1;
      this.rand = new Random();
      this.represent = new HashMap<Vertex, Vertex>();
      this.vertexMap = this.initMap();
      this.edges = this.initEdges();
      this.viableEdges = this.kruskalsAlgorithm();
      this.assignVertexEdges();
      this.runThrough = new ArrayList<Vertex>();
      this.mst = new ArrayList<Vertex>();
      this.currCell = 0;
      this.breadthWrong = 0;
      this.depthWrong = 0;
      this.minimalPath = 0;
    }
    else if (key.equals("h")) {

      // creates a new random maze with a more horizontal path
      this.vertWeight = 1;
      this.horWeight = 100;
      this.rand = new Random();
      this.represent = new HashMap<Vertex, Vertex>();
      this.vertexMap = this.initMap();
      this.edges = this.initEdges();
      this.viableEdges = this.kruskalsAlgorithm();
      this.assignVertexEdges();
      this.runThrough = new ArrayList<Vertex>();
      this.mst = new ArrayList<Vertex>();
      this.currCell = 0;
      this.breadthWrong = 0;
      this.depthWrong = 0;
      this.minimalPath = 0;
    }
    else if (key.equals("t")) {

      // toggles on and off the visited paths
      if (this.toggle) {
        for (Vertex v : this.runThrough) {
          v.changeColor(Color.gray);
        }
      }
      else {
        for (Vertex v : this.runThrough) {
          v.changeColor(Color.magenta);
        }
      }
      this.toggle = !this.toggle;
    }
    else if (key.equals("c")) {

      // clears the current board of any shown path
      this.runThrough = new ArrayList<Vertex>();
      this.mst = new ArrayList<Vertex>();
      this.currCell = 0;
      for (ArrayList<Vertex> col : this.vertexMap) {
        for (Vertex v : col) {
          v.changeColor(Color.gray);
        }
      }

    }
  }

}

//comparator to compare the weights of each edge
class WeightCheck implements Comparator<Edge> {
  // compares two edges based on their weight
  @Override
  public int compare(Edge o1, Edge o2) {
    return o1.weight - o2.weight;
  }
}


// bigbang is called on this object and it displays a separate canvas
// that shows the user all of the keys they can press and what each one does
class Instructions extends World {
  
  // creates the instruction screen
  @Override
  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(400, 225);

    scene.placeImageXY(new RectangleImage(400, 300, OutlineMode.SOLID, Color.LIGHT_GRAY), 200, 122);

    // adds the instructions to the maze on the scene
    scene.placeImageXY(new TextImage("d: runs a depth first search", 20, Color.black), 200, 25);
    scene.placeImageXY(new TextImage("b: runs a breadth first search", 20, Color.black), 200, 50);
    scene.placeImageXY(new TextImage("r: creates a new unsolved random maze", 20, Color.black), 200,
        75);
    scene.placeImageXY(new TextImage("v: creates a maze with a vertical bias", 20, Color.black),
        200, 100);
    scene.placeImageXY(new TextImage("h: creates a maze with a horizontal bias", 20, Color.black),
        200, 125);
    scene.placeImageXY(new TextImage("s: skips the animation of the search", 20, Color.black), 200,
        150);
    scene.placeImageXY(
        new TextImage("t: toggles the viewing of the visited paths", 20, Color.black), 200, 175);
    scene.placeImageXY(new TextImage("c: clears/resets the current maze", 20, Color.black), 200,
        200);

    return scene;
  }
}