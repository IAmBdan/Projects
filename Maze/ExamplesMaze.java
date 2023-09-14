import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.function.Predicate;

import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

class ExamplesMaze {

  // example vertices
  Vertex vertex1 = new Vertex(0, 0, 30, 30);
  Vertex vertex2 = new Vertex(0, 0, 30, 30);
  Vertex vertex3 = new Vertex(30, 30, 15, 30);
  Vertex vertex4 = new Vertex(100, 100, 30, 30);
  Vertex vertex5 = new Vertex(50, 50, 15, 30);

  // example edges
  Edge edge1 = new Edge(vertex1, vertex2, 100, "vertical", 30);
  Edge edge2 = new Edge(vertex1, vertex2, 100, "vertical", 30);
  Edge edge3 = new Edge(vertex2, vertex3, 30, "vertical", 30);
  Edge edge4 = new Edge(vertex2, vertex3, 30, "horizontal", 30);

  // example world scenes and world images
  WorldScene background = new WorldScene(200, 200);
  WorldScene background2 = new WorldScene(200, 200);
  WorldScene background3 = new WorldScene(200, 200);
  WorldScene background4 = new WorldScene(200, 200);
  WorldImage line1 = new LineImage(new Posn(0, 30), Color.black);
  WorldImage line2 = new LineImage(new Posn(30, 0), Color.black);

  // example vertices
  Vertex vertex1a = new Vertex(0, 0, 20, 20);
  Vertex vertex1b = new Vertex(20, 0, 20, 20);
  Vertex vertex1c = new Vertex(40, 0, 20, 20);

  Vertex vertex2a = new Vertex(0, 20, 20, 20);
  Vertex vertex2b = new Vertex(20, 20, 20, 20);
  Vertex vertex2c = new Vertex(40, 20, 20, 20);

  Vertex vertex3a = new Vertex(0, 40, 20, 20);
  Vertex vertex3b = new Vertex(20, 40, 20, 20);
  Vertex vertex3c = new Vertex(40, 40, 20, 20);

  // example columns and rows of vertices
  ArrayList<Vertex> col1 = new ArrayList<Vertex>(
      Arrays.asList(this.vertex1a, this.vertex2a, this.vertex3a));
  ArrayList<Vertex> col2 = new ArrayList<Vertex>(
      Arrays.asList(this.vertex1b, this.vertex2b, this.vertex3b));
  ArrayList<Vertex> col3 = new ArrayList<Vertex>(
      Arrays.asList(this.vertex1c, this.vertex2c, this.vertex3c));

  ArrayList<Vertex> row1 = new ArrayList<Vertex>(
      Arrays.asList(this.vertex1a, this.vertex1b, this.vertex1c));
  ArrayList<Vertex> row2 = new ArrayList<Vertex>(
      Arrays.asList(this.vertex2a, this.vertex2b, this.vertex2c));
  ArrayList<Vertex> row3 = new ArrayList<Vertex>(
      Arrays.asList(this.vertex3a, this.vertex3b, this.vertex3c));

  // example vertex maps
  ArrayList<ArrayList<Vertex>> threex3board1 = new ArrayList<ArrayList<Vertex>>(
      Arrays.asList(this.col1, this.col2, this.col3));
  ArrayList<ArrayList<Vertex>> threex3board2 = new ArrayList<ArrayList<Vertex>>(
      Arrays.asList(this.row1, this.row2, this.row3));

  ArrayList<ArrayList<Vertex>> test = new ArrayList<ArrayList<Vertex>>(Arrays.asList());

  // vertices for maze1
  Vertex mazeVert1a = new Vertex(0, 0, 3, 296);
  Vertex mazeVert1b = new Vertex(0, 296, 3, 296);
  Vertex mazeVert1c = new Vertex(0, 592, 3, 296);

  Vertex mazeVert2a = new Vertex(296, 0, 3, 296);
  Vertex mazeVert2b = new Vertex(296, 296, 3, 296);
  Vertex mazeVert2c = new Vertex(296, 592, 3, 296);

  Vertex mazeVert3a = new Vertex(592, 0, 3, 296);
  Vertex mazeVert3b = new Vertex(592, 296, 3, 296);
  Vertex mazeVert3c = new Vertex(592, 592, 3, 296);

  // columns for maze1
  ArrayList<Vertex> mazeCol1 = new ArrayList<Vertex>(
      Arrays.asList(this.mazeVert1a, this.mazeVert1b, this.mazeVert1c));
  ArrayList<Vertex> mazeCol2 = new ArrayList<Vertex>(
      Arrays.asList(this.mazeVert2a, this.mazeVert2b, this.mazeVert2c));
  ArrayList<Vertex> mazeCol3 = new ArrayList<Vertex>(
      Arrays.asList(this.mazeVert3a, this.mazeVert3b, this.mazeVert3c));

  // vertexMap for maze1
  ArrayList<ArrayList<Vertex>> initMazeTest = new ArrayList<ArrayList<Vertex>>(
      Arrays.asList(this.mazeCol1, this.mazeCol2, this.mazeCol3));

  // images and scenes for makeScene for maze1
  WorldScene backgroundScene = new WorldScene(888, 888);
  WorldImage greyRectangle = new RectangleImage(888, 888, OutlineMode.SOLID, Color.gray);
  WorldImage greenRectangle = new RectangleImage(296, 296, OutlineMode.SOLID, Color.green.darker());
  WorldImage redRectangle = new RectangleImage(296, 296, OutlineMode.SOLID, Color.red.darker());
  WorldImage blackRectangle = new RectangleImage(888, 888, OutlineMode.OUTLINE, Color.black);

  WorldImage edgeVert = new LineImage(new Posn(0, 296), Color.black);
  WorldImage edgeHorz = new LineImage(new Posn(296, 0), Color.black);

  // example maze
  Maze maze1 = new Maze(3, 3, new Random(1));

  // example HashMap
  HashMap<Vertex, Vertex> maze1StartingHashMap = new HashMap<Vertex, Vertex>();

  // example edges for maze1
  Edge mazeEdge1 = new Edge(this.mazeVert1a, this.mazeVert2a, 25, "vertical", 296);
  Edge mazeEdge2 = new Edge(this.mazeVert1a, this.mazeVert1b, 18, "horizontal", 296);
  Edge mazeEdge3 = new Edge(this.mazeVert1b, this.mazeVert2b, 22, "vertical", 296);
  Edge mazeEdge4 = new Edge(this.mazeVert1b, this.mazeVert1c, 28, "horizontal", 296);
  Edge mazeEdge5 = new Edge(this.mazeVert1c, this.mazeVert2c, 34, "vertical", 296);
  Edge mazeEdge6 = new Edge(this.mazeVert2a, this.mazeVert3a, 34, "vertical", 296);
  Edge mazeEdge7 = new Edge(this.mazeVert2a, this.mazeVert2b, 14, "horizontal", 296);
  Edge mazeEdge8 = new Edge(this.mazeVert2b, this.mazeVert3b, 1, "vertical", 296);
  Edge mazeEdge9 = new Edge(this.mazeVert2b, this.mazeVert2c, 3, "horizontal", 296);
  Edge mazeEdge10 = new Edge(this.mazeVert2c, this.mazeVert3c, 13, "vertical", 296);
  Edge mazeEdge11 = new Edge(this.mazeVert3a, this.mazeVert3b, 19, "horizontal", 296);
  Edge mazeEdge12 = new Edge(this.mazeVert3b, this.mazeVert3c, 33, "horizontal", 296);

  // example comparator object
  WeightCheck compareWeight = new WeightCheck();

  // eample maze
  Maze maze2;

  // example vertices for maze 2
  Vertex maze21a = new Vertex(0, 0, 2, 446);
  Vertex maze21b = new Vertex(0, 446, 2, 446);
  Vertex maze22a = new Vertex(446, 0, 2, 446);
  Vertex maze22b = new Vertex(446, 446, 2, 446);

  // example edges for maze 2
  Edge maze2e1 = new Edge(this.maze21a, this.maze22a, 19, "vertical", 446);
  Edge maze2e2 = new Edge(this.maze21a, this.maze21b, 5, "horizontal", 446);
  Edge maze2e3 = new Edge(this.maze21b, this.maze22b, 20, "vertical", 446);
  Edge maze2e4 = new Edge(this.maze22a, this.maze22b, 11, "horizontal", 446);

  // example maze 3 & 4
  Maze maze3;
  Maze maze4;

  // example maze 5
  Maze m5 = new Maze(2, 2, new Random(3));

  // maze 5 drawing
  WorldScene scene5 = new WorldScene(1292, 892);

  // second canvas displaying instructions
  Instructions ins = new Instructions();
  WorldScene backgroundScene2 = new WorldScene(400, 225);
  WorldImage greyRect = new RectangleImage(400, 300, OutlineMode.SOLID, Color.LIGHT_GRAY);
  WorldImage d = new TextImage("d: runs a depth first search", 20, Color.black);
  WorldImage b = new TextImage("b: runs a breadth first search", 20, Color.black);
  WorldImage r = new TextImage("r: creates a new unsolved random maze", 20, Color.black);
  WorldImage v = new TextImage("v: creates a maze with a vertical bias", 20, Color.black);
  WorldImage h = new TextImage("h: creates a maze with a horizontal bias", 20, Color.black);
  WorldImage s = new TextImage("s: skips the animation of the search", 20, Color.black);
  WorldImage t = new TextImage("t: toggles the viewing of the visited paths", 20, Color.black);
  WorldImage c = new TextImage("c: clears/resets the current maze", 20, Color.black);

  // initializes data
  void initData() {
    this.background.placeImageXY(this.line1, 0, 30);
    this.background3.placeImageXY(this.line2, 45, 30);
    this.backgroundScene.placeImageXY(this.greyRectangle, 444, 444);
    this.backgroundScene.placeImageXY(this.greenRectangle, 148, 148);
    this.backgroundScene.placeImageXY(redRectangle, 740, 740);
    this.backgroundScene.placeImageXY(blackRectangle, 444, 444);
    this.backgroundScene.placeImageXY(edgeVert, 296, 148);
    this.backgroundScene.placeImageXY(edgeVert, 296, 740);
    this.backgroundScene.placeImageXY(edgeVert, 592, 148);
    this.backgroundScene.placeImageXY(edgeHorz, 740, 592);

    this.maze1StartingHashMap.put(mazeVert1a, mazeVert1a);
    this.maze1StartingHashMap.put(mazeVert1b, mazeVert1b);
    this.maze1StartingHashMap.put(mazeVert1c, mazeVert1c);

    this.maze1StartingHashMap.put(mazeVert2a, mazeVert2a);
    this.maze1StartingHashMap.put(mazeVert2b, mazeVert2b);
    this.maze1StartingHashMap.put(mazeVert2c, mazeVert2c);

    this.maze1StartingHashMap.put(mazeVert3a, mazeVert3a);
    this.maze1StartingHashMap.put(mazeVert3b, mazeVert3b);
    this.maze1StartingHashMap.put(mazeVert3c, mazeVert3c);

    maze2 = new Maze(2, 2, new Random(3));
    maze3 = new Maze(2, 2, new Random(3));
    this.maze3.assignVertexEdges();

    maze4 = new Maze(2, 2, new Random(3));
    this.maze4.searchTree("b");
    this.maze4.onTick();
    this.maze4.onTick();
    this.maze4.onTick();
    this.maze4.onTick();
    this.maze4.onTick();

    this.maze21a.connEdge.add(this.maze2e2);
    this.maze21a.connEdge.add(this.maze2e1);
    this.maze21b.connEdge.add(this.maze2e2);
    this.maze22a.connEdge.add(this.maze2e4);
    this.maze22a.connEdge.add(this.maze2e3);
    this.maze22b.connEdge.add(this.maze2e4);

    // gray background
    this.scene5.placeImageXY(new RectangleImage(892, 892, OutlineMode.SOLID, Color.gray), 446, 446);
    // green start
    this.scene5.placeImageXY(new RectangleImage(446, 446, OutlineMode.SOLID, Color.green.darker()),
        223, 223);
    // red end
    this.scene5.placeImageXY(new RectangleImage(446, 446, OutlineMode.SOLID, Color.red.darker()),
        669, 669);
    // black outline
    this.scene5.placeImageXY(new RectangleImage(892, 892, OutlineMode.OUTLINE, Color.black), 446,
        446);
    // edges
    this.scene5.placeImageXY(new LineImage(new Posn(0, 446), Color.black), 446, 669);
    // bfs wrong moves
    this.scene5.placeImageXY(new TextImage("BFS Wrong Moves:0", 20, Color.black), 1092, 50);
    // dfs wrong moves
    this.scene5.placeImageXY(new TextImage("DFS Wrong Moves:0", 20, Color.black), 1092, 100);
    // minimum number of moves
    this.scene5.placeImageXY(new TextImage("Minimum Number of Moves:0", 20, Color.black), 1092,
        150);

    this.backgroundScene2.placeImageXY(this.greyRect, 200, 122);
    this.backgroundScene2.placeImageXY(d, 200, 25);
    this.backgroundScene2.placeImageXY(b, 200, 50);
    this.backgroundScene2.placeImageXY(r, 200, 75);
    this.backgroundScene2.placeImageXY(v, 200, 100);
    this.backgroundScene2.placeImageXY(h, 200, 125);
    this.backgroundScene2.placeImageXY(s, 200, 150);
    this.backgroundScene2.placeImageXY(t, 200, 175);
    this.backgroundScene2.placeImageXY(c, 200, 200);
  }

  // Tests for the Maze Class

  // to test for IllegalArgumentExceptions
  void testException(Tester t) {
    this.initData();
    t.checkConstructorException("Test for a maze greater than 100 wide",
        new IllegalArgumentException("The width of your maze cannot exceed 100"), "Maze", 200, 3);
    t.checkConstructorException("Test for a maze greater than 60 tall",
        new IllegalArgumentException("The height of your maze cannot exceed 60"), "Maze", 30, 61);
    t.checkConstructorException("Test for a maze less than 2 wide",
        new IllegalArgumentException("The width of your maze cannot be less than 2"), "Maze", 1, 3);
    t.checkConstructorException("Test for a maze less than 2 tall",
        new IllegalArgumentException("The height of your maze cannot be less than 2"), "Maze", 30,
        1);
  }

  // to test the method initMap
  boolean testInitMap(Tester t) {
    ArrayList<ArrayList<Vertex>> maze1Map = this.maze1.vertexMap;
    HashMap<Vertex, Vertex> maze1Hash = this.maze1.represent;
    return t.checkExpect(this.maze1.initMap(), this.initMazeTest)
        // to test the hashmap created in initMap
        && t.checkExpect(maze1Hash.get(maze1Map.get(0).get(0)), this.mazeVert1a)
        && t.checkExpect(maze1Hash.get(maze1Map.get(0).get(1)), this.mazeVert1b)
        && t.checkExpect(maze1Hash.get(maze1Map.get(0).get(2)), this.mazeVert1c)
        && t.checkExpect(maze1Hash.get(maze1Map.get(1).get(0)), this.mazeVert2a)
        && t.checkExpect(maze1Hash.get(maze1Map.get(1).get(1)), this.mazeVert2b)
        && t.checkExpect(maze1Hash.get(maze1Map.get(1).get(2)), this.mazeVert2c)
        && t.checkExpect(maze1Hash.get(maze1Map.get(2).get(0)), this.mazeVert3a)
        && t.checkExpect(maze1Hash.get(maze1Map.get(2).get(1)), this.mazeVert3b)
        && t.checkExpect(maze1Hash.get(maze1Map.get(2).get(2)), this.mazeVert3c);
  }

  // to test the method initEdges
  boolean testInitEdges(Tester t) {
    return t.checkExpect(this.maze1.edges.get(0), this.mazeEdge1)
        && t.checkExpect(this.maze1.edges.get(1), this.mazeEdge2)
        && t.checkExpect(this.maze1.edges.get(2), this.mazeEdge3)
        && t.checkExpect(this.maze1.edges.get(3), this.mazeEdge4)
        && t.checkExpect(this.maze1.edges.get(4), this.mazeEdge5)
        && t.checkExpect(this.maze1.edges.get(5), this.mazeEdge6)
        && t.checkExpect(this.maze1.edges.get(6), this.mazeEdge7)
        && t.checkExpect(this.maze1.edges.get(7), this.mazeEdge8)
        && t.checkExpect(this.maze1.edges.get(8), this.mazeEdge9)
        && t.checkExpect(this.maze1.edges.get(9), this.mazeEdge10)
        && t.checkExpect(this.maze1.edges.get(10), this.mazeEdge11)
        && t.checkExpect(this.maze1.edges.get(11), this.mazeEdge12);
  }

  // to test the method makeScene
  boolean testMakeScene(Tester t) {
    initData();
    return t.checkExpect(this.m5.makeScene(), this.scene5)
        && t.checkExpect(this.ins.makeScene(), this.backgroundScene2);
  }

  // to test the method kruskalsAlgorithm
  boolean testKruskalsAlgorithm(Tester t) {
    return t.checkExpect(this.maze1.viableEdges.get(0), this.mazeEdge8)
        && t.checkExpect(this.maze1.viableEdges.get(1), this.mazeEdge9)
        && t.checkExpect(this.maze1.viableEdges.get(2), this.mazeEdge10)
        && t.checkExpect(this.maze1.viableEdges.get(3), this.mazeEdge7)
        && t.checkExpect(this.maze1.viableEdges.get(4), this.mazeEdge2)
        && t.checkExpect(this.maze1.viableEdges.get(5), this.mazeEdge11)
        && t.checkExpect(this.maze1.viableEdges.get(6), this.mazeEdge3)
        && t.checkExpect(this.maze1.viableEdges.get(7), this.mazeEdge4);
  }

  // to test the method assignVertexEdges
  void testAssignVertexEdges(Tester t) {
    this.initData();
    this.maze2.assignVertexEdges();
    t.checkExpect(this.maze2.vertexMap.get(0).get(0).connEdge,
        this.maze3.vertexMap.get(0).get(0).connEdge);
  }

  // to test the method searchTree
  void testSearchTree(Tester t) {
    this.initData();
    this.maze2.assignVertexEdges();
    this.maze2.searchTree("b");
    this.maze3.searchTree("b");
    t.checkExpect(this.maze2.runThrough, this.maze3.runThrough);
    this.maze2.searchTree("d");
    this.maze3.searchTree("d");
    t.checkExpect(this.maze2.runThrough, this.maze3.runThrough);
  }

  // to test the method reconstruct
  void testReconstruct(Tester t) {
    // method is called in the searchTree method
    this.initData();
    this.maze2.assignVertexEdges();
    this.maze2.searchTree("b");
    this.maze3.searchTree("b");
    t.checkExpect(this.maze2.mst, this.maze3.mst);
    this.maze2.searchTree("d");
    this.maze3.searchTree("d");
    t.checkExpect(this.maze2.mst, this.maze3.mst);
  }

  // to test the method onTick
  void testOnTick(Tester t) {
    this.initData();
    this.maze2.assignVertexEdges();
    this.maze2.onTick();
    t.checkExpect(this.maze2.currCell, 0);
    t.checkExpect(this.maze2.runThrough.size(), 0);
    this.maze2.searchTree("b");
    this.maze2.onTick();
    t.checkExpect(this.maze2.currCell, 1);
    t.checkExpect(this.maze2.runThrough.size(), 4);
    t.checkExpect(this.maze2.vertexMap.get(0).get(0).color, Color.magenta);
    this.maze2.onTick();
    t.checkExpect(this.maze2.currCell, 2);
    t.checkExpect(this.maze2.vertexMap.get(0).get(1).color, Color.magenta);
    this.maze2.onTick();
    t.checkExpect(this.maze2.currCell, 3);
    t.checkExpect(this.maze2.vertexMap.get(1).get(0).color, Color.magenta);
    this.maze2.onTick();
    t.checkExpect(this.maze2.currCell, 4);
    t.checkExpect(this.maze2.vertexMap.get(1).get(1).color, Color.magenta);
    this.maze2.onTick();
    t.checkExpect(this.maze2.currCell, 4);
    t.checkExpect(this.maze2.vertexMap.get(0).get(0).color, Color.blue);
    t.checkExpect(this.maze2.vertexMap.get(1).get(0).color, Color.blue);
    t.checkExpect(this.maze2.vertexMap.get(1).get(1).color, Color.blue);
  }

  // to test the method onKeyEvent
  void testOnKeyEvent(Tester t) {
    this.initData();
    this.maze2.onKeyEvent("d");
    this.maze3.searchTree("d");
    t.checkExpect(this.maze2.currCell, 0);
    t.checkExpect(this.maze2.depthWrong, 0);
    t.checkExpect(this.maze2.minimalPath, 3);
    this.maze2.onKeyEvent("b");
    this.maze3.runThrough = new ArrayList<Vertex>();
    this.maze3.mst = new ArrayList<Vertex>();
    this.maze3.searchTree("b");
    t.checkExpect(this.maze2.currCell, 0);
    t.checkExpect(this.maze2.breadthWrong, 1);
    t.checkExpect(this.maze2.minimalPath, 3);
    this.maze2.onKeyEvent("r");
    t.checkExpect(this.maze2.vertWeight, 35);
    t.checkExpect(this.maze2.horWeight, 35);
    t.checkExpect(this.maze2.currCell, 0);
    t.checkExpect(this.maze2.breadthWrong, 0);
    t.checkExpect(this.maze2.depthWrong, 0);
    t.checkExpect(this.maze2.minimalPath, 0);
    this.initData();
    this.maze2.searchTree("b");
    this.maze2.onKeyEvent("s");
    t.checkExpect(this.maze2.runThrough.size(), 4);
    t.checkExpect(this.maze2.vertexMap.get(0).get(0).color, Color.magenta);
    t.checkExpect(this.maze2.vertexMap.get(0).get(1).color, Color.magenta);
    t.checkExpect(this.maze2.vertexMap.get(1).get(0).color, Color.magenta);
    t.checkExpect(this.maze2.vertexMap.get(1).get(1).color, Color.magenta);
    this.maze2.onKeyEvent("v");
    t.checkExpect(this.maze2.vertWeight, 100);
    t.checkExpect(this.maze2.horWeight, 1);
    t.checkExpect(this.maze2.currCell, 0);
    t.checkExpect(this.maze2.breadthWrong, 0);
    t.checkExpect(this.maze2.depthWrong, 0);
    t.checkExpect(this.maze2.minimalPath, 0);
    this.maze2.onKeyEvent("h");
    t.checkExpect(this.maze2.vertWeight, 1);
    t.checkExpect(this.maze2.horWeight, 100);
    t.checkExpect(this.maze2.currCell, 0);
    t.checkExpect(this.maze2.breadthWrong, 0);
    t.checkExpect(this.maze2.depthWrong, 0);
    t.checkExpect(this.maze2.minimalPath, 0);
    this.maze3.onKeyEvent("t");
    t.checkExpect(this.maze3.vertexMap.get(0).get(0).color, Color.gray);
    t.checkExpect(this.maze3.vertexMap.get(0).get(1).color, Color.gray);
    t.checkExpect(this.maze3.vertexMap.get(1).get(0).color, Color.gray);
    t.checkExpect(this.maze3.vertexMap.get(1).get(1).color, Color.gray);
    t.checkExpect(this.maze3.toggle, false);
    this.maze4.onKeyEvent("c");
    t.checkExpect(this.maze4.runThrough, new ArrayList<Vertex>());
    t.checkExpect(this.maze4.mst, new ArrayList<Vertex>());
    t.checkExpect(this.maze4.currCell, 0);
    t.checkExpect(this.maze4.vertexMap.get(0).get(0).color, Color.gray);
    t.checkExpect(this.maze4.vertexMap.get(0).get(1).color, Color.gray);
    t.checkExpect(this.maze4.vertexMap.get(1).get(0).color, Color.gray);
    t.checkExpect(this.maze4.vertexMap.get(1).get(1).color, Color.gray);

  }

  // Tests for the Edge Class

  // to test the method drawEdge
  boolean testDrawEdge(Tester t) {
    this.initData();
    return t.checkExpect(this.edge3.drawEdge(), this.line1)
        && t.checkExpect(this.edge4.drawEdge(), this.line2);
  }

  // to test the method placeEdge
  boolean testPlaceEdge(Tester t) {
    this.initData();
    return t.checkExpect(this.edge1.placeEdge(this.background2), this.background)
        && t.checkExpect(this.edge4.placeEdge(this.background4), this.background3);
  }

  // to test the method find
  boolean testFind(Tester t) {
    this.initData();
    return t.checkExpect(this.maze1.edges.get(0).find(this.maze1StartingHashMap, "start"),
        this.maze1.vertexMap.get(0).get(0))
        && t.checkExpect(this.maze1.edges.get(0).find(this.maze1StartingHashMap, "end"),
            this.maze1.vertexMap.get(1).get(0))
        && t.checkExpect(this.maze1.edges.get(1).find(this.maze1StartingHashMap, "start"),
            this.maze1.vertexMap.get(0).get(0))
        && t.checkExpect(this.maze1.edges.get(1).find(this.maze1StartingHashMap, "end"),
            this.maze1.vertexMap.get(0).get(1))
        && t.checkExpect(this.maze1.edges.get(11).find(this.maze1StartingHashMap, "start"),
            this.maze1.vertexMap.get(2).get(1))
        && t.checkExpect(this.maze1.edges.get(11).find(this.maze1StartingHashMap, "end"),
            this.maze1.vertexMap.get(2).get(2));
  }

  // to test the method union
  void testUnion(Tester t) {
    this.initData();
    Edge maze1Edge1 = this.maze1.edges.get(0);
    Edge maze1Edge6 = this.maze1.edges.get(5);
    Edge maze1Edge11 = this.maze1.edges.get(10);
    Edge maze1Edge12 = this.maze1.edges.get(11);
    maze1Edge1.union(this.maze1StartingHashMap, maze1Edge1.start, maze1Edge1.end);
    t.checkExpect(this.maze1StartingHashMap.get(maze1Edge1.end), this.mazeVert1a);
    maze1Edge12.union(this.maze1StartingHashMap, maze1Edge12.start, maze1Edge12.end);
    t.checkExpect(this.maze1StartingHashMap.get(maze1Edge12.end), this.mazeVert3b);
    t.checkExpect(this.maze1StartingHashMap.get(this.mazeVert1a), this.mazeVert1a);
    t.checkExpect(this.maze1StartingHashMap.get(this.mazeVert2a), this.mazeVert1a);
    t.checkExpect(this.maze1StartingHashMap.get(this.mazeVert3a), this.mazeVert3a);
    t.checkExpect(this.maze1StartingHashMap.get(this.mazeVert3b), this.mazeVert3b);
    t.checkExpect(this.maze1StartingHashMap.get(this.mazeVert3c), this.mazeVert3b);
    maze1Edge11.union(this.maze1StartingHashMap, maze1Edge11.start, maze1Edge11.end);
    t.checkExpect(this.maze1StartingHashMap.get(maze1Edge11.end), this.mazeVert3a);
    t.checkExpect(this.maze1StartingHashMap.get(this.mazeVert3b), this.mazeVert3a);
    t.checkExpect(this.maze1StartingHashMap.get(this.mazeVert3c), this.mazeVert3a);
    maze1Edge6.union(this.maze1StartingHashMap, maze1Edge1.start, maze1Edge6.end);
    t.checkExpect(this.maze1StartingHashMap.get(this.mazeVert1a), this.mazeVert1a);
    t.checkExpect(this.maze1StartingHashMap.get(this.mazeVert2a), this.mazeVert1a);
    t.checkExpect(this.maze1StartingHashMap.get(this.mazeVert3a), this.mazeVert1a);
    t.checkExpect(this.maze1StartingHashMap.get(this.mazeVert3b), this.mazeVert1a);
    t.checkExpect(this.maze1StartingHashMap.get(this.mazeVert3c), this.mazeVert1a);
  }

  // Tests for the Vertex Class

  // to test the method drawEdgeHelp
  boolean testDrawEdgeHelp(Tester t) {
    this.initData();
    return t.checkExpect(this.vertex1.drawEdgeHelp(this.background2, 0, 30, this.edge3),
        this.background);
  }

  // to test the method hashCode
  boolean testHashCode(Tester t) {
    this.initData();
    return t.checkExpect(this.vertex1.hashCode(), 1) && t.checkExpect(this.vertex3.hashCode(), 17)
        && t.checkExpect(this.vertex4.hashCode(), 94) && t.checkExpect(this.vertex5.hashCode(), 17);
  }

  // to test the method equals
  boolean testEquals(Tester t) {
    this.initData();
    return t.checkExpect(this.vertex1.equals(this.edge1), false)
        && t.checkExpect(this.vertex1.equals(this.vertex1), true)
        && t.checkExpect(this.vertex1.equals(this.vertex2), true)
        && t.checkExpect(this.vertex1.equals(this.vertex3), false);
  }

  // to test the method drawVertex
  boolean testDrawVertex(Tester t) {
    Vertex v = new Vertex(0, 0, 20, 2);
    WorldScene scene = new WorldScene(20, 20);
    WorldScene scene1 = new WorldScene(20, 20);
    scene1.placeImageXY(new RectangleImage(2, 2, OutlineMode.SOLID, Color.gray), 1, 1);

    return t.checkExpect(v.drawVertex(scene), scene1);
  }

  // to test the method changeColor
  void testChangeColor(Tester t) {
    Vertex v = new Vertex(0, 0, 20, 20);
    t.checkExpect(v.color, Color.gray);
    v.changeColor(Color.red);
    t.checkExpect(v.color, Color.red);
  }

  // Tests for the WeightCheck class

  // to test the method compare
  boolean testCompare(Tester t) {
    return t.checkExpect(this.compareWeight.compare(this.mazeEdge1, this.mazeEdge2), 7)
        && t.checkExpect(this.compareWeight.compare(this.mazeEdge1, this.mazeEdge4), -3)
        && t.checkExpect(this.compareWeight.compare(this.mazeEdge5, this.mazeEdge6), 0)
        && t.checkExpect(this.compareWeight.compare(this.mazeEdge1, this.mazeEdge1), 0)
        && t.checkExpect(this.compareWeight.compare(this.mazeEdge2, this.mazeEdge1), -7)
        && t.checkExpect(this.compareWeight.compare(this.mazeEdge4, this.mazeEdge1), 3)
        && t.checkExpect(this.compareWeight.compare(this.mazeEdge6, this.mazeEdge5), 0)
        && t.checkExpect(this.compareWeight.compare(this.mazeEdge5, this.mazeEdge12), 1)
        && t.checkExpect(this.compareWeight.compare(this.mazeEdge12, this.mazeEdge5), -1);
  }

  // Tests for the Deque class

  Predicate<Integer> filterEven = new FilterEven();
  Predicate<Integer> filterBySeven = new FilterBySeven();

  Deque<String> deque1 = new Deque<String>();

  Sentinel<String> sent1 = new Sentinel<String>();
  Node<String> abc = new Node<String>("abc");
  Node<String> bcd = new Node<String>("bcd");
  Node<String> cde = new Node<String>("cde");
  Node<String> def = new Node<String>("def");
  Deque<String> deque2;

  Sentinel<Integer> sent2 = new Sentinel<Integer>();
  Node<Integer> num1 = new Node<Integer>(15);
  Node<Integer> num2 = new Node<Integer>(20);
  Node<Integer> num3 = new Node<Integer>(3);
  Node<Integer> num4 = new Node<Integer>(8);
  Node<Integer> num5 = new Node<Integer>(400);
  Deque<Integer> deque3;

  Deque<String> deque4 = new Deque<String>();

  Sentinel<String> sent3 = new Sentinel<String>();
  Node<String> yay = new Node<String>("yay");
  Deque<String> deque5;

  Sentinel<String> sent4 = new Sentinel<String>();
  Node<String> nay = new Node<String>("nay");
  Deque<String> deque6;

  Deque<String> deque7 = new Deque<String>();

  Sentinel<String> sent5 = new Sentinel<String>();
  Node<String> back = new Node<String>("back");
  Deque<String> deque8;

  Sentinel<String> sent6 = new Sentinel<String>();
  Node<String> front = new Node<String>("front");
  Deque<String> deque9;

  Sentinel<Integer> sent7 = new Sentinel<Integer>();
  Node<Integer> num6 = new Node<Integer>(6);
  Node<Integer> num7 = new Node<Integer>(7);
  Deque<Integer> deque10;

  Sentinel<Integer> sent8 = new Sentinel<Integer>();
  Node<Integer> seven = new Node<Integer>(7);
  Deque<Integer> deque11;

  // initializes the data
  void initData1() {
    abc = new Node<String>("abc", this.bcd, this.sent1);
    bcd = new Node<String>("bcd", this.cde, this.abc);
    cde = new Node<String>("cde", this.def, this.bcd);
    def = new Node<String>("def", this.sent1, this.cde);
    deque2 = new Deque<String>(this.sent1);

    num1 = new Node<Integer>(15, this.num2, this.sent2);
    num2 = new Node<Integer>(20, this.num3, this.num1);
    num3 = new Node<Integer>(3, this.num4, this.num2);
    num4 = new Node<Integer>(8, this.num5, this.num3);
    num5 = new Node<Integer>(400, this.sent2, this.num4);
    deque3 = new Deque<Integer>(this.sent2);

    yay = new Node<String>("yay", this.sent3, this.sent3);
    deque5 = new Deque<String>(this.sent3);

    back = new Node<String>("back", this.sent5, this.sent5);
    deque8 = new Deque<String>(this.sent5);

    num6 = new Node<Integer>(6, this.num7, this.sent7);
    num7 = new Node<Integer>(7, this.sent7, this.num6);
    deque10 = new Deque<Integer>(this.sent7);

    seven = new Node<Integer>(7, this.sent8, this.sent8);
    deque11 = new Deque<Integer>(this.sent8);
  }

  // to test the exceptions thrown
  void testExceptions(Tester t) {
    this.initData1();
    t.checkException("Test for removing from an empty list",
        new RuntimeException("A node cannot be removed from an empty deque"), this.deque1,
        "removeFromHead");
    t.checkException("Test for removing from an empty list",
        new RuntimeException("A node cannot be removed from an empty deque"), this.deque1,
        "removeFromTail");
  }

  // to test the method size
  void testSize(Tester t) {
    this.initData1();
    t.checkExpect(this.deque1.size(), 0);
    t.checkExpect(this.deque2.size(), 4);
    t.checkExpect(this.deque3.size(), 5);
  }

  // to test the method addAtHead
  void testAddAtHead(Tester t) {
    this.initData1();
    this.deque4.addAtHead("yay");
    t.checkExpect(this.deque4, this.deque5);
    this.deque5.addAtHead("nay");
    nay = new Node<String>("nay", this.yay, this.sent4);
    yay = new Node<String>("yay", this.sent4, this.nay);
    deque6 = new Deque<String>(this.sent4);
    t.checkExpect(this.deque5, this.deque6);
  }

  // to test the method addAtTail
  void testAddAtTail(Tester t) {
    this.initData1();
    this.deque7.addAtTail("back");
    t.checkExpect(this.deque7, this.deque8);
    this.deque8.addAtHead("front");
    back = new Node<String>("back", this.front, this.sent5);
    front = new Node<String>("front", this.sent5, this.back);
    deque9 = new Deque<String>(this.sent5);
    t.checkExpect(this.deque8, this.deque9);
  }

  // to test the method removeFromHead
  void testRemoveFromHead(Tester t) {
    this.initData1();
    t.checkExpect(this.deque5.removeFromHead(), "yay");
    t.checkExpect(this.deque5, this.deque1);
    this.initData1();
    this.deque5.addAtTail("back");
    t.checkExpect(this.deque5.removeFromHead(), "yay");
    t.checkExpect(this.deque5, this.deque8);
  }

  // to test the method removeFromTail
  void testRemoveFromTail(Tester t) {
    this.initData1();
    t.checkExpect(this.deque5.removeFromTail(), "yay");
    t.checkExpect(this.deque5, this.deque1);
    this.initData1();
    this.deque5.addAtHead("back");
    t.checkExpect(this.deque5.removeFromTail(), "yay");
    t.checkExpect(this.deque5, this.deque8);
  }

  // to test the method find
  void testFindDeque(Tester t) {
    this.initData1();
    t.checkExpect(this.deque3.find(this.filterEven), this.num2);
    t.checkExpect(this.deque3.find(this.filterBySeven), this.sent2);
  }

  // to test the method updatePrev
  void testUpdatePrev(Tester t) {
    this.initData1();
    t.checkExpect(this.num2, new Node<Integer>(20, this.num3, this.num1));
    this.num2.updatePrev(this.num4);
    t.checkExpect(this.num2, new Node<Integer>(20, this.num3, this.num4));
    t.checkExpect(this.sent1.prev, this.def);
    this.sent1.updatePrev(this.bcd);
    t.checkExpect(this.sent1.prev, this.bcd);
    this.initData1();
    t.checkExpect(this.num2, new Node<Integer>(20, this.num3, this.num1));
    this.num2.updatePrev(this.sent2);
    t.checkExpect(this.num2, new Node<Integer>(20, this.num3, this.sent2));
  }

  // to test the method updateNext
  void testUpdateNext(Tester t) {
    this.initData1();
    t.checkExpect(this.num2, new Node<Integer>(20, this.num3, this.num1));
    this.num2.updateNext(this.num4);
    t.checkExpect(this.num2, new Node<Integer>(20, this.num4, this.num1));
    t.checkExpect(this.sent1.next, this.abc);
    this.sent1.updateNext(this.bcd);
    t.checkExpect(this.sent1.next, this.bcd);
    this.initData1();
    t.checkExpect(this.num2, new Node<Integer>(20, this.num3, this.num1));
    this.num2.updateNext(this.sent2);
    t.checkExpect(this.num2, new Node<Integer>(20, this.sent2, this.num1));
  }

  // to test the method SizeHelp
  boolean testSizeHelp(Tester t) {
    this.initData1();
    return t.checkExpect(this.num1.sizeHelp(), 5) && t.checkExpect(this.num2.sizeHelp(), 4)
        && t.checkExpect(this.sent8.sizeHelp(), 0);
  }

  // to test the method removeHelp
  void testRemoveHelp(Tester t) {
    this.initData1();
    t.checkExpect(this.num1.removeHelp(), 15);
    this.num1.updatePrev(this.num2);
    this.num1.updateNext(this.sent2);
    t.checkExpect(this.num1.removeHelp(), 15);
    t.checkExpect(this.num2.prev, this.sent2);
    t.checkExpect(this.sent2.next, this.num2);
  }

  // to test the method findHelp
  boolean testFindHelp(Tester t) {
    this.initData1();
    return t.checkExpect(this.sent2.findHelp(this.filterEven), this.sent2)
        && t.checkExpect(this.num2.findHelp(this.filterEven), this.num2)
        && t.checkExpect(this.num2.findHelp(this.filterBySeven), this.sent2);
  }

  // to test the method test
  boolean testTest(Tester t) {
    return t.checkExpect(this.filterBySeven.test(7), true)
        && t.checkExpect(this.filterBySeven.test(17), false)
        && t.checkExpect(this.filterEven.test(17), false)
        && t.checkExpect(this.filterEven.test(10), true);
  }

  // to display the maze on the screen
  void testMaze(Tester t) {
    Maze starterMaze = new Maze(100, 60);
    Instructions instruct = new Instructions();

    if (starterMaze.height * starterMaze.cellSize < 750) {
      starterMaze.bigBang(starterMaze.cellSize * starterMaze.width,
          starterMaze.cellSize * starterMaze.height + 150, .001);
    }
    else {
      starterMaze.bigBang(starterMaze.cellSize * starterMaze.width + 400,
          starterMaze.cellSize * starterMaze.height, .001);
    }

    instruct.bigBang(400, 225);
  }

}