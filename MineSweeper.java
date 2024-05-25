import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;


// represents a world in minesweeper game
class MinesweeperWorld extends World {
  ArrayList<ArrayList<Cell>> grid;
  int clicks;
  Random rand;
  int rows;
  int cols;
  int cellSize;
  int gameState;
  int numMines;
  boolean won;
  int score;

  // constructor that takes in rows, columns, number of mines, random, and cell size
  MinesweeperWorld(int rows, int cols, int numMines, Random rand, int cellSize) {
    clicks = 0;
    this.rand = rand;
    this.rows = rows;
    this.cols = cols;
    this.cellSize = 15;
    this.cellSize = cellSize;
    this.numMines = numMines;
    this.grid = this.makeGrid(rows, cols);
    this.grid =  this.setNeighborsAndMines(rows, cols, numMines);
    this.gameState = 0; // 0 for intro, 1 for game, 2 for game over
    this.won = false;
    this.score = 0;
  }

  // constructor that takes in rows, columns, number of mines, and random
  MinesweeperWorld(int rows, int cols, int numMines, Random rand) {
    this(rows, cols, numMines, rand, 15);
  }

  // populates the grid with cells 
  ArrayList<ArrayList<Cell>> makeGrid(int rows, int cols) {

    ArrayList<ArrayList<Cell>> grid = new ArrayList<>();
    for (int i = 0; i < rows; i++) {
      grid.add(new ArrayList<Cell>());
      for (int j = 0; j < cols; j++) {
        grid.get(i).add(new Cell(false, this.cellSize));
      }
    }

    return grid;
  }

  // randomly sets mines and links cells with neighbors
  ArrayList<ArrayList<Cell>> setNeighborsAndMines(int rows, int cols, int numMines) {
    for (int i = 0; i < numMines; i++) {

      int row = this.rand.nextInt(rows);
      int col = this.rand.nextInt(cols);

      Cell cellToAlter = grid.get(row).get(col);

      // sets mines
      if (cellToAlter.hasMine()) {
        i -= 1;
      }
      while (!cellToAlter.hasMine()) {
        ArrayList<Cell> gridRow = grid.get(row);
        cellToAlter.setContainsMine(true);
        gridRow.set(col, cellToAlter);
        grid.set(row, gridRow);

      }
    }

    // sets neighbors
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        // -1, -1
        if (i > 0 && j > 0) {
          Cell cellToAdd = grid.get(i - 1).get(j - 1);
          grid.get(i).get(j).addNeighbor(cellToAdd);
        }
        // -1, 0
        if (i > 0) {
          Cell cellToAdd = grid.get(i - 1).get(j);
          grid.get(i).get(j).addNeighbor(cellToAdd);
        }
        // -1, 1
        if (i > 0 && j < cols - 1) {
          Cell cellToAdd = grid.get(i - 1).get(j + 1);
          grid.get(i).get(j).addNeighbor(cellToAdd);
        }
        // 0, 1
        if (j < cols - 1) {
          Cell cellToAdd = grid.get(i).get(j + 1);
          grid.get(i).get(j).addNeighbor(cellToAdd);
        }
        // 1, 1
        if (i < rows - 1 && j < cols - 1) {
          Cell cellToAdd = grid.get(i + 1).get(j + 1);
          grid.get(i).get(j).addNeighbor(cellToAdd);
        }
        // 1, 0
        if (i < rows - 1) {
          Cell cellToAdd = grid.get(i + 1).get(j);
          grid.get(i).get(j).addNeighbor(cellToAdd);
        }
        // 1, -1
        if (i < rows - 1 && j > 0) {
          Cell cellToAdd = grid.get(i + 1).get(j - 1);
          grid.get(i).get(j).addNeighbor(cellToAdd);
        }
        // 0, -1
        if (j > 0) {
          Cell cellToAdd = grid.get(i).get(j - 1);
          grid.get(i).get(j).addNeighbor(cellToAdd);
        }
      }
    }

    return grid;
  }

  // returns the number of active mines in the grid
  int activeMines() {
    int numMines = 0;
    for (int i = 0; i < this.grid.size(); i++) {
      for (int j = 0; j < this.grid.size(); j++) {
        if (this.grid.get(i).get(j).hasMine()) {
          numMines++;
        }
      }
    }
    return numMines;
  }

  // draws the gameboard
  public WorldScene makeScene() {
    // WorldScene scene = new WorldScene(this.rows * this.cellSize, this.cols * this.cellSize);


    // game beginning
    if (this.gameState == 0) {
      WorldScene scene = new WorldScene(this.rows * this.cellSize, this.cols * this.cellSize);
      scene.placeImageXY(new RectangleImage(500, 400, OutlineMode.SOLID, 
          new Color(119, 221, 119)), 200, 150);
      scene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.GREEN), 10, 10);
      scene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.blue), 30, 30);
      scene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.GREEN), 370, 100);
      scene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.blue), 390, 120);
      scene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.GREEN), 100, 150);
      scene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.blue), 120, 170);
      scene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.GREEN), 200, 225);
      scene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.blue), 220, 245);
      scene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.GREEN), 385, 300);
      scene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.blue), 405, 320);
      scene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.GREEN), 30, 250);
      scene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.blue), 50, 270);
      scene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.GREEN), 220, 25);
      scene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.blue), 240, 45);
      scene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.GREEN), 230, 400);
      scene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.blue), 250, 420);
      scene.placeImageXY(new TextImage("Minesweeper", 24, FontStyle.BOLD, Color.black), 230, 40);
      scene.placeImageXY(new RectangleImage(100, 50, OutlineMode.SOLID, Color.gray), 75, 150);
      scene.placeImageXY(new RectangleImage(100, 50, OutlineMode.OUTLINE, Color.black), 75, 150);
      scene.placeImageXY(new TextImage("Easy", 16, Color.black), 75, 150);
      scene.placeImageXY(new RectangleImage(100, 50, OutlineMode.SOLID, Color.gray), 225, 150);
      scene.placeImageXY(new RectangleImage(100, 50, OutlineMode.OUTLINE, Color.BLACK), 225, 150);
      scene.placeImageXY(new TextImage("Medium", 16, Color.black), 225, 150);
      scene.placeImageXY(new RectangleImage(100, 50, OutlineMode.SOLID, Color.gray), 375, 150);
      scene.placeImageXY(new RectangleImage(100, 50, OutlineMode.OUTLINE, Color.BLACK), 375, 150);
      scene.placeImageXY(new TextImage("Hard", 16, Color.black), 375, 150);
      return scene;
    }

    // when game is running
    else if (this.gameState == 1) {
      WorldScene scene = new WorldScene(this.rows * this.cellSize, this.cols * this.cellSize);
      for (int i = 0; i < grid.size(); i++) {
        for (int j = 0; j < grid.get(i).size(); j++) {
          scene = this.grid.get(i).get(j).drawCell(scene, i, j);
        }
      }
      // makes graphics for clicks at bottom of screen
      scene.placeImageXY(new RectangleImage(100, 40, OutlineMode.SOLID, Color.GRAY), 
          60, this.cols * this.cellSize + 30);
      scene.placeImageXY(new RectangleImage(100, 40, OutlineMode.OUTLINE, Color.BLACK), 
          60, this.cols * this.cellSize + 30);
      scene.placeImageXY(new TextImage("Clicks: " + this.clicks, 15, Color.BLUE),
          60, this.cols * this.cellSize + 30);

      // makes graphics for unfound mines at bottom of screen
      scene.placeImageXY(new RectangleImage(145, 40, OutlineMode.SOLID, Color.GRAY), 
          200, this.cols * this.cellSize + 30);
      scene.placeImageXY(new RectangleImage(145, 40, OutlineMode.OUTLINE, Color.BLACK), 
          200, this.cols * this.cellSize + 30);
      scene.placeImageXY(new TextImage("Unfound mines: " + Integer.toString(this.numMines),
          15, Color.BLUE),
          200, this.cols * this.cellSize + 30);

      // makes graphics for score at bottom of screen
      scene.placeImageXY(new RectangleImage(100, 40, OutlineMode.SOLID, Color.GRAY), 
          340, this.cols * this.cellSize + 30);
      scene.placeImageXY(new RectangleImage(100, 40, OutlineMode.OUTLINE, Color.BLACK), 
          340, this.cols * this.cellSize + 30);
      scene.placeImageXY(new TextImage("Score: " + Integer.toString(100 - this.score), 
          15, Color.BLUE),
          340, this.cols * this.cellSize + 30);

      // draws the cells
      for (int i = 0; i < this.rows; i++) {
        for (int j = 0; j < this.cols; j++) {
          scene = this.grid.get(i).get(j).drawCell(scene, i, j);
        }
      }

      return scene;
    }

    // ending screens
    else {
      WorldScene scene = new WorldScene(this.rows * this.cellSize, this.cols * this.cellSize);

      // if game is won
      if (won) {
        scene.placeImageXY(new RectangleImage(500, 400, OutlineMode.SOLID, 
            new Color(255, 213, 128)), 200, 150);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.ORANGE), 10, 10);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, new Color(204, 85, 0)), 
            30, 30);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.ORANGE), 370, 100);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, new Color(204, 85, 0)), 
            390, 120);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.ORANGE), 100, 150);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, new Color(204, 85, 0)), 
            120, 170);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.ORANGE), 200, 225);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, new Color(204, 85, 0)), 
            220, 245);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.ORANGE), 385, 300);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, new Color(204, 85, 0)), 
            405, 320);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.ORANGE), 30, 250);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, new Color(204, 85, 0)), 
            50, 270);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.ORANGE), 220, 25);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, new Color(204, 85, 0)), 
            240, 45);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.ORANGE), 230, 400);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, new Color(204, 85, 0)), 
            250, 420);
        scene.placeImageXY(new TextImage("You won!", 24, FontStyle.BOLD, Color.black), 230, 40);
      }

      // if game is lost
      else {
        scene.placeImageXY(new RectangleImage(500, 400, OutlineMode.SOLID, 
            new Color(255, 209, 220)), 200, 150);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.RED), 10, 10);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.PINK), 30, 30);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.RED), 370, 100);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.PINK), 390, 120);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.RED), 100, 150);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.PINK), 120, 170);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.RED), 200, 225);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.PINK), 220, 245);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.RED), 385, 300);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.PINK), 405, 320);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.RED), 30, 250);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.PINK), 50, 270);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.RED), 220, 25);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.PINK), 240, 45);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.RED), 230, 400);
        scene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.PINK), 250, 420);
        scene.placeImageXY(new TextImage("You lost!", 24, FontStyle.BOLD, Color.black), 230, 40);
      }

      // draws restart graphics
      scene.placeImageXY(new RectangleImage(100, 50, OutlineMode.SOLID, Color.gray), 225, 100);
      scene.placeImageXY(new TextImage("Restart", 16, FontStyle.BOLD, Color.black), 225, 100);
      return scene;
    }
  }

  // helps to set the win or lose condition
  boolean isGameWon() {
    for (ArrayList<Cell> row : grid) {
      for (Cell cell : row) {
        if (!cell.correctFlags()) {
          return false;
        }
      }
    }
    this.won = true;
    return true;
  }

  // to allow the user's clicks to change the game
  public void onMouseClicked(Posn pos, String buttonName) {

    // for the welcome screen and button graphics
    if (this.gameState == 0) {
      if (pos.x >= 25 && pos.x <= 125 && pos.y >= 125 && pos.y <= 175) {
        this.cellSize = 45;
        this.rows = 10;
        this.cols = 5;
        this.numMines = 20;
        this.grid = new MinesweeperWorld(this.rows, this.cols, 
            this.numMines, this.rand, this.cellSize).grid;
        this.gameState = 1;
      }
      else if (pos.x >= 175 && pos.x < 275 && pos.y >= 125 && pos.y <= 175) {
        this.cellSize = 30;
        this.rows = 15;
        this.cols = 8;
        this.numMines = 49;
        this.grid = new MinesweeperWorld(this.rows, this.cols, 
            this.numMines, this.rand, this.cellSize).grid;
        this.gameState = 1;
      }
      else if (pos.x >= 325 && pos.x < 425 && pos.y >= 125 && pos.y <= 175) {
        this.cellSize = 15;
        this.rows = 30;
        this.cols = 16;
        this.numMines = 99;
        this.grid = new MinesweeperWorld(this.rows, this.cols, 
            this.numMines, this.rand, this.cellSize).grid;
        this.gameState = 1;
      }
    }

    // for the game as it is being played
    else if (this.gameState == 1) {

      int cellRow = pos.x / this.cellSize;
      int cellCol = pos.y / this.cellSize;
      Cell clickedCell = this.grid.get(cellRow).get(cellCol);

      // for left button clicks
      if (buttonName.equals("LeftButton")) {
        clickedCell.setActive(false);
        clickedCell.floodfill();
        this.clicks += 1;

        // to lose the game
        if (clickedCell.hasMine()) {
          this.gameState = 2;        
        }
        clickedCell.setActive(false);
        clickedCell.floodfill();
      }


      // for right button clicks
      else if (buttonName.equals("RightButton")) {
        clickedCell.toggleFlag();
        this.clicks += 1;
        if (clickedCell.hasMine()) {
          if (clickedCell.correctFlags()) {
            // Decrement numMines if a flag is placed on a mine
            this.numMines -= 1;
          }
          else {
            // Increment numMines if a flag is removed from a mine
            this.numMines += 1;
          }
        }
      }

      // to win the game
      if (this.isGameWon()) {
        this.gameState = 2;
      }
    }

    else {
      // game over
      if (buttonName.equals("LeftButton")) {
        if (pos.x >= 175 && pos.x <= 275 && pos.y >= 75 && pos.y <= 125) {
          this.won = false;
          this.gameState = 0;
          this.clicks = 0;
          this.score = 0;
        }
      }
    }
  }

  // helps to keep score through time
  public void onTick() {
    this.score += 1;
  }
}

// represents a cell
class Cell {
  boolean containsMine;
  boolean active;
  ArrayList<Cell> neighbors;
  boolean hasFlag;
  int cellSize;
  boolean floodFilled;

  // constructor with cell size
  Cell(boolean containsMine, int cellSize) {
    this.containsMine = containsMine;
    this.active = true;
    this.neighbors = new ArrayList<>();
    this.hasFlag = false;
    this.cellSize = cellSize;
    this.floodFilled = false;
  }

  // constructor without cell size
  Cell(boolean containsMine) {
    this(containsMine, 50);
  }

  // EFFECT: floodfill effect by checking neighbors and neighbors of neighbors
  void floodfill() {
    if (this.countNeighboringMines() == 0) {
      this.setInactiveNeighbors();
      this.floodFilled = true;
      for (Cell cell : neighbors) {
        if (!cell.floodFilled) {
          cell.floodfill();
        }
      }
    }
  }

  // counts if there is a mine in a cell
  int countMines() {
    if (this.containsMine) {
      return 1;
    }
    else { 
      return 0;
    }
  }

  // checks if a cell is active
  boolean isActive() {
    return this.active;
  }

  // helps to see if a cell is correctly flagged
  boolean correctFlags() {
    return this.hasFlag == this.containsMine;
  }

  // EFFECT: sets the neighbors list
  void setNeighbors(ArrayList<Cell> neighbors) {
    this.neighbors = neighbors;
  }

  // EFFECT: sets all neighbors to inactive
  void setInactiveNeighbors() {
    for (Cell cell : neighbors) {
      cell.setActive(false);
    }
  }

  // EFFECT: adds a neighbor
  void addNeighbor(Cell neighbor) {
    this.neighbors.add(neighbor);
  }

  // EFFECT: sets the containsMine field
  void setContainsMine(boolean containsMine) {
    this.containsMine = containsMine;
  }

  // EFFECT: sets the active field
  void setActive(boolean active) {
    this.active = active;
  }

  // EFFECT: sets the flag field
  void setHasFlag(boolean flag) {
    this.hasFlag = flag;
  }

  // EFFECT: to help toggle flag
  void toggleFlag() {
    this.hasFlag = !this.hasFlag;
  }

  // checks if a cell contains a mine
  boolean hasMine() {
    return this.containsMine;
  }

  // counts how many mines are neighboring
  int countNeighboringMines() {
    int num = 0;
    for (Cell c : neighbors) {
      if (c.hasMine()) {
        num ++;
      }
    }
    return num;
  }

  // helper to draw each cell
  public WorldScene drawCell(WorldScene ws, int row, int col) {

    // draws an active cell
    if (this.active) {
      ws.placeImageXY(new RectangleImage(this.cellSize, this.cellSize, 
          OutlineMode.SOLID, Color.GREEN), 
          row * this.cellSize + this.cellSize / 2, col * this.cellSize + this.cellSize / 2);
      ws.placeImageXY(new RectangleImage(this.cellSize, this.cellSize, 
          OutlineMode.OUTLINE, Color.BLACK), 
          row * this.cellSize + this.cellSize / 2, col * this.cellSize + this.cellSize / 2);

      // draws any possible flags
      if (this.hasFlag) {
        ws.placeImageXY(new EquilateralTriangleImage(this.cellSize / 2, 
            OutlineMode.SOLID, Color.RED), 
            row * this.cellSize + this.cellSize / 2, col * this.cellSize + this.cellSize / 2);
      }
    }

    // draws an inactive cell
    else {
      ws.placeImageXY(new RectangleImage(this.cellSize, this.cellSize, 
          OutlineMode.SOLID, Color.GRAY), 
          row * this.cellSize + this.cellSize / 2, col * this.cellSize + this.cellSize / 2);
      ws.placeImageXY(new RectangleImage(this.cellSize, this.cellSize, 
          OutlineMode.OUTLINE, Color.BLACK), 
          row * this.cellSize + this.cellSize / 2, col * this.cellSize + this.cellSize / 2);
      ws.placeImageXY(this.drawNum(), 
          row * this.cellSize + this.cellSize / 2, col * this.cellSize + this.cellSize / 2);
    }
    return ws;
  }

  // helper to draw the right colored number in the game
  public WorldImage drawNum() {
    if (this.countNeighboringMines() == 0) {
      return new TextImage("", Color.BLACK);
    }
    else if (this.countNeighboringMines() == 1) {
      return new TextImage(Integer.toString(this.countNeighboringMines()), Color.BLUE);
    }
    else if (this.countNeighboringMines() == 2) {
      return new TextImage(Integer.toString(this.countNeighboringMines()), Color.yellow);
    }
    else if (this.countNeighboringMines() == 3) {
      return new TextImage(Integer.toString(this.countNeighboringMines()), Color.PINK);
    }
    else if (this.countNeighboringMines() == 4) {
      return new TextImage(Integer.toString(this.countNeighboringMines()), Color.CYAN);
    }
    else if (this.countNeighboringMines() == 5) {
      return new TextImage(Integer.toString(this.countNeighboringMines()), Color.orange);
    }
    else {
      return new TextImage(Integer.toString(this.countNeighboringMines()), Color.black);
    }
  }
}

// examples and tests
class ExamplesMinesweeper {

  Random rand = new Random(5);

  MinesweeperWorld emptyWorld;
  ArrayList<ArrayList<Cell>> emptyGrid;

  MinesweeperWorld world1;
  ArrayList<ArrayList<Cell>> world1Grid;
  ArrayList<ArrayList<Cell>> world1GridMined;

  Cell activeClearCell;
  Cell activeMinedCell;

  Cell w100;
  Cell w101;
  Cell w102;
  Cell w110;
  Cell w111;
  Cell w112;
  Cell w120;
  Cell w121;
  Cell w122;

  ArrayList<ArrayList<Cell>> world1GridWithNeighbors;

  Cell cell2;
  ArrayList<Cell> cell2WithNeighbor;
  ArrayList<Cell> emptyAList;

  WorldScene emptyWS;

  Cell cell3;
  Cell cell4;
  Cell cell5;
  Cell cell6;

  MinesweeperWorld world2;
  MinesweeperWorld world3;
  MinesweeperWorld world4;


  void setup() {

    this.activeClearCell = new Cell(false);
    this.activeMinedCell = new Cell(true);


    this.emptyWorld = new MinesweeperWorld(0, 0, 0, rand);
    this.emptyGrid = new ArrayList<>();

    this.w100 = new Cell(false);
    this.w101 = new Cell(false);
    this.w102 = new Cell(true);
    this.w110 = new Cell(false);
    this.w111 = new Cell(false);
    this.w112 = new Cell(false);
    this.w120 = new Cell(false);
    this.w121 = new Cell(true);
    this.w122 = new Cell(true);

    this.world1 = new MinesweeperWorld(3, 3, 3, rand);
    this.world1Grid = new ArrayList<>(Arrays.asList(
        new ArrayList<Cell>(
            Arrays.asList(this.activeClearCell, this.activeClearCell, this.activeClearCell)),
        new ArrayList<Cell>(
            Arrays.asList(this.activeClearCell, this.activeClearCell, this.activeClearCell)),
        new ArrayList<Cell>(
            Arrays.asList(this.activeClearCell, this.activeClearCell, this.activeClearCell))));
    this.world1GridMined = new ArrayList<ArrayList<Cell>>(Arrays.asList(
        new ArrayList<Cell>(Arrays.asList(this.w100, this.w101, this.w102)),
        new ArrayList<Cell>(Arrays.asList(this.w110, this.w111, this.w112)),
        new ArrayList<Cell>(Arrays.asList(this.w120, this.w121, this.w122))));


    this.w100.setNeighbors(new ArrayList<Cell>(
        Arrays.asList(this.w101, this.w111, this.w110)));
    this.w101.setNeighbors(new ArrayList<Cell>(
        Arrays.asList(this.w102, this.w112, this.w111, this.w110, this.w100)));
    this.w102.setNeighbors(new ArrayList<Cell>(
        Arrays.asList(this.w112, this.w111, this.w101)));
    this.w110.setNeighbors(new ArrayList<Cell>(
        Arrays.asList(this.w100, this.w101, this.w111, this.w121, this.w120)));
    this.w111.setNeighbors(new ArrayList<Cell>(
        Arrays.asList(this.w100, this.w101, this.w102, 
            this.w112, this.w122, this.w121, this.w120, this.w110)));
    this.w112.setNeighbors(new ArrayList<Cell>(
        Arrays.asList(this.w101, this.w102, this.w122, this.w121, this.w111)));
    this.w120.setNeighbors(new ArrayList<Cell>(
        Arrays.asList(this.w110, this.w111, this.w121)));
    this.w121.setNeighbors(new ArrayList<Cell>(
        Arrays.asList(this.w110, this.w111, this.w112, this.w122, this.w120)));
    this.w122.setNeighbors(new ArrayList<Cell>(
        Arrays.asList(this.w111, this.w112, this.w121)));

    this.world1GridWithNeighbors = new ArrayList<ArrayList<Cell>>(Arrays.asList(
        new ArrayList<Cell>(Arrays.asList(this.w100, this.w101, this.w102)),
        new ArrayList<Cell>(Arrays.asList(this.w110, this.w111, this.w112)),
        new ArrayList<Cell>(Arrays.asList(this.w120, this.w121, this.w122))));

    this.cell2 = new Cell(false);
    this.cell2WithNeighbor = new ArrayList<Cell>(Arrays.asList(this.cell2));
    this.emptyAList = new ArrayList<>();

    this.emptyWS = new WorldScene(500, 500);


    this.world2 = new MinesweeperWorld(2, 2, 1, new Random(5));

    this.cell3 = new Cell(true);
    this.cell4 = new Cell(false);
    this.cell5 = new Cell(false);
    this.cell6 = new Cell(false);

    this.cell3.setNeighbors(new ArrayList<Cell>(
        Arrays.asList(this.cell4, this.cell5, this.cell6)));
    this.cell4.setNeighbors(new ArrayList<Cell>(
        Arrays.asList(this.cell3, this.cell5, this.cell6)));
    this.cell5.setNeighbors(new ArrayList<Cell>(
        Arrays.asList(this.cell3, this.cell4, this.cell6)));
    this.cell6.setNeighbors(new ArrayList<Cell>(
        Arrays.asList(this.cell3, this.cell4, this.cell5)));



    this.world3 = new MinesweeperWorld(30, 16, 99, new Random(5));
    this.world4 = new MinesweeperWorld(5, 5, 2, this.rand);
  }

  // tests the method makeScene
  void testMakeScene(Tester t) {
    setup();

    // draws an empty world
    WorldScene finalEmpty = new WorldScene(0, 0);
    this.emptyWorld.gameState = 1;

    finalEmpty.placeImageXY(new RectangleImage(0, 0, OutlineMode.SOLID, Color.GRAY), 
        0, 0);
    finalEmpty.placeImageXY(new RectangleImage(100, 40, OutlineMode.SOLID, Color.GRAY), 
        60, 30);
    finalEmpty.placeImageXY(new RectangleImage(100, 40, OutlineMode.OUTLINE, Color.BLACK), 
        60, 30);
    finalEmpty.placeImageXY(new TextImage("Clicks: " + "0", 15, Color.BLUE),
        60, 30);
    finalEmpty.placeImageXY(new RectangleImage(145, 40, OutlineMode.SOLID, Color.GRAY), 
        200, 30);
    finalEmpty.placeImageXY(new RectangleImage(145, 40, OutlineMode.OUTLINE, Color.BLACK), 
        200, 30);
    finalEmpty.placeImageXY(new TextImage("Unfound mines: " + "", 15, Color.BLUE),
        200, 30);
    finalEmpty.placeImageXY(new RectangleImage(100, 40, OutlineMode.SOLID, Color.GRAY), 
        340, 30);
    finalEmpty.placeImageXY(new RectangleImage(100, 40, OutlineMode.OUTLINE, Color.BLACK), 
        340, 30);
    finalEmpty.placeImageXY(new TextImage("Score: " + Integer.toString(100), 15, Color.BLUE),
        340, 30);

    t.checkExpect(this.emptyWorld.makeScene(), finalEmpty);

    // draws a world with active, inactive, and flagged cells and all button graphics
    this.world2.grid.get(1).get(1).setActive(false);
    this.world2.grid.get(0).get(0).setHasFlag(true);
    this.world2.gameState = 1;

    WorldScene finalWorld2 = new WorldScene(30, 30);

    // DELETE THIS: TODO
    // graphics for the cells
    // cell 3
    finalWorld2.placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.GREEN), 
        7, 7);
    finalWorld2.placeImageXY(new RectangleImage(15, 15, OutlineMode.OUTLINE, Color.BLACK), 
        7, 7);
    finalWorld2.placeImageXY(new EquilateralTriangleImage(7, OutlineMode.SOLID, Color.RED), 
        7, 7);
    // cell 4
    finalWorld2.placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.GREEN), 
        7, 22);
    finalWorld2.placeImageXY(new RectangleImage(15, 15, OutlineMode.OUTLINE, Color.BLACK), 
        7, 22);
    // cell 5
    finalWorld2.placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.GREEN), 
        22, 7);
    finalWorld2.placeImageXY(new RectangleImage(15, 15, OutlineMode.OUTLINE, Color.BLACK), 
        22, 7);
    // cell 6
    finalWorld2.placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.GRAY), 
        22, 22);
    finalWorld2.placeImageXY(new RectangleImage(15, 15, OutlineMode.OUTLINE, Color.BLACK), 
        22, 22);
    finalWorld2.placeImageXY(new TextImage("1", Color.BLUE), 
        22, 22);




    // makes graphics for clicks at bottom of screen
    finalWorld2.placeImageXY(new RectangleImage(100, 40, OutlineMode.SOLID, Color.GRAY), 
        60, 60);
    finalWorld2.placeImageXY(new RectangleImage(100, 40, OutlineMode.OUTLINE, Color.BLACK), 
        60, 60);
    finalWorld2.placeImageXY(new TextImage("Clicks: " + Integer.toString(0), 15, Color.BLUE),
        60, 60);

    // makes graphics for unfound mines at bottom of screen
    finalWorld2.placeImageXY(new RectangleImage(145, 40, OutlineMode.SOLID, Color.GRAY), 
        200, 60);
    finalWorld2.placeImageXY(new RectangleImage(145, 40, OutlineMode.OUTLINE, Color.BLACK), 
        200, 60);
    finalWorld2.placeImageXY(new TextImage("Unfound mines: " + Integer.toString(1), 15, Color.BLUE),
        200, 60);

    // makes graphics for score at bottom of screen
    finalWorld2.placeImageXY(new RectangleImage(100, 40, OutlineMode.SOLID, Color.GRAY), 
        340, 60);
    finalWorld2.placeImageXY(new RectangleImage(100, 40, OutlineMode.OUTLINE, Color.BLACK), 
        340, 60);
    finalWorld2.placeImageXY(new TextImage("Score: " + Integer.toString(100), 15, Color.BLUE),
        340, 60);

    // graphics for the cells
    // cell 3
    finalWorld2.placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.GREEN), 
        7, 7);
    finalWorld2.placeImageXY(new RectangleImage(15, 15, OutlineMode.OUTLINE, Color.BLACK), 
        7, 7);
    finalWorld2.placeImageXY(new EquilateralTriangleImage(7, OutlineMode.SOLID, Color.RED), 
        7, 7);
    // cell 4
    finalWorld2.placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.GREEN), 
        7, 22);
    finalWorld2.placeImageXY(new RectangleImage(15, 15, OutlineMode.OUTLINE, Color.BLACK), 
        7, 22);
    // cell 5
    finalWorld2.placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.GREEN), 
        22, 7);
    finalWorld2.placeImageXY(new RectangleImage(15, 15, OutlineMode.OUTLINE, Color.BLACK), 
        22, 7);
    // cell 6
    finalWorld2.placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.GRAY), 
        22, 22);
    finalWorld2.placeImageXY(new RectangleImage(15, 15, OutlineMode.OUTLINE, Color.BLACK), 
        22, 22);
    finalWorld2.placeImageXY(new TextImage("1", Color.BLUE),
        22, 22);

    t.checkExpect(this.world2.makeScene(), finalWorld2);

    // draw a world with the start screen
    WorldScene startScene = new WorldScene(450, 240);
    startScene.placeImageXY(new RectangleImage(500, 400, OutlineMode.SOLID, 
        new Color(119, 221, 119)), 200, 150);
    startScene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.GREEN), 10, 10);
    startScene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.blue), 30, 30);
    startScene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.GREEN), 370, 100);
    startScene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.blue), 390, 120);
    startScene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.GREEN), 100, 150);
    startScene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.blue), 120, 170);
    startScene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.GREEN), 200, 225);
    startScene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.blue), 220, 245);
    startScene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.GREEN), 385, 300);
    startScene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.blue), 405, 320);
    startScene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.GREEN), 30, 250);
    startScene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.blue), 50, 270);
    startScene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.GREEN), 220, 25);
    startScene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.blue), 240, 45);
    startScene.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.GREEN), 230, 400);
    startScene.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.blue), 250, 420);
    startScene.placeImageXY(new TextImage("Minesweeper", 24, FontStyle.BOLD, Color.black), 
        230, 40);
    startScene.placeImageXY(new RectangleImage(100, 50, OutlineMode.SOLID, Color.gray), 75, 150);
    startScene.placeImageXY(new RectangleImage(100, 50, OutlineMode.OUTLINE, Color.black), 
        75, 150);
    startScene.placeImageXY(new TextImage("Easy", 16, Color.black), 75, 150);
    startScene.placeImageXY(new RectangleImage(100, 50, OutlineMode.SOLID, Color.gray), 225, 150);
    startScene.placeImageXY(new RectangleImage(100, 50, OutlineMode.OUTLINE, Color.BLACK), 
        225, 150);
    startScene.placeImageXY(new TextImage("Medium", 16, Color.black), 225, 150);
    startScene.placeImageXY(new RectangleImage(100, 50, OutlineMode.SOLID, Color.gray), 375, 150);
    startScene.placeImageXY(new RectangleImage(100, 50, OutlineMode.OUTLINE, Color.BLACK), 
        375, 150);
    startScene.placeImageXY(new TextImage("Hard", 16, Color.black), 375, 150);
    t.checkExpect(this.world3.makeScene(), startScene);

    // draw a world with the end screen, where the game was won
    WorldScene endSceneWin = new WorldScene(450, 240);
    endSceneWin.placeImageXY(new RectangleImage(500, 400, OutlineMode.SOLID, 
        new Color(255, 213, 128)), 200, 150);
    endSceneWin.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.ORANGE), 10, 10);
    endSceneWin.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, new Color(204, 85, 0)), 
        30, 30);
    endSceneWin.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.ORANGE), 370, 100);
    endSceneWin.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, new Color(204, 85, 0)), 
        390, 120);
    endSceneWin.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.ORANGE), 100, 150);
    endSceneWin.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, new Color(204, 85, 0)), 
        120, 170);
    endSceneWin.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.ORANGE), 200, 225);
    endSceneWin.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, new Color(204, 85, 0)), 
        220, 245);
    endSceneWin.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.ORANGE), 385, 300);
    endSceneWin.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, new Color(204, 85, 0)), 
        405, 320);
    endSceneWin.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.ORANGE), 30, 250);
    endSceneWin.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, new Color(204, 85, 0)), 
        50, 270);
    endSceneWin.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.ORANGE), 220, 25);
    endSceneWin.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, new Color(204, 85, 0)), 
        240, 45);
    endSceneWin.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.ORANGE), 230, 400);
    endSceneWin.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, new Color(204, 85, 0)), 
        250, 420);
    endSceneWin.placeImageXY(new TextImage("You won!", 24, FontStyle.BOLD, Color.black), 230, 40);
    endSceneWin.placeImageXY(new RectangleImage(100, 50, OutlineMode.SOLID, Color.gray), 225, 100);
    endSceneWin.placeImageXY(new TextImage("Restart", 16, FontStyle.BOLD, Color.black), 225, 100);
    this.world3.gameState = 2;
    this.world3.won = true;
    t.checkExpect(this.world3.makeScene(), endSceneWin);

    // draw a world with the end screen, where the world was lost 
    WorldScene endSceneLoss = new WorldScene(450, 240);
    endSceneLoss.placeImageXY(new RectangleImage(500, 400, OutlineMode.SOLID, 
        new Color(255, 209, 220)), 200, 150);
    endSceneLoss.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.RED), 10, 10);
    endSceneLoss.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.PINK), 30, 30);
    endSceneLoss.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.RED), 370, 100);
    endSceneLoss.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.PINK), 390, 120);
    endSceneLoss.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.RED), 100, 150);
    endSceneLoss.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.PINK), 120, 170);
    endSceneLoss.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.RED), 200, 225);
    endSceneLoss.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.PINK), 220, 245);
    endSceneLoss.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.RED), 385, 300);
    endSceneLoss.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.PINK), 405, 320);
    endSceneLoss.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.RED), 30, 250);
    endSceneLoss.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.PINK), 50, 270);
    endSceneLoss.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.RED), 220, 25);
    endSceneLoss.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.PINK), 240, 45);
    endSceneLoss.placeImageXY(new HexagonImage(40, OutlineMode.SOLID, Color.RED), 230, 400);
    endSceneLoss.placeImageXY(new HexagonImage(40, OutlineMode.OUTLINE, Color.PINK), 250, 420);
    endSceneLoss.placeImageXY(new TextImage("You lost!", 24, FontStyle.BOLD, Color.black), 230, 40);
    endSceneLoss.placeImageXY(new RectangleImage(100, 50, OutlineMode.SOLID, Color.gray), 225, 100);
    endSceneLoss.placeImageXY(new TextImage("Restart", 16, FontStyle.BOLD, Color.black), 225, 100);
    this.world3.won = false;
    t.checkExpect(this.world3.makeScene(), endSceneLoss);
  }

  // tests the method activeMines
  void testActiveMines(Tester t) {
    setup();
    // tests an empty world with randomness 0-5
    t.checkOneOf(this.emptyWorld.activeMines(), 0, 1, 2, 3, 4, 5);
    // tests a nonempty world with randomness 0-5
    t.checkOneOf(this.world1.activeMines(), 0, 1, 2, 3, 4, 5);

  }

  // tests the method setNeighbors
  void testSetNeighbors(Tester t) {
    setup();
    // tests setting a cell's neighbor with a non empty array list
    this.cell2.setNeighbors(this.cell2WithNeighbor);
    t.checkExpect(this.cell2.neighbors, this.cell2WithNeighbor);
    // tests setting a cell's neighbor with an empty array list
    this.cell2.setNeighbors(this.emptyAList);
    t.checkExpect(this.cell2.neighbors, this.emptyAList);
  }

  // Test the drawCell method
  void testDrawCell(Tester t) {
    setup();

    // Test an active cell with a flag
    this.cell2.setActive(true);
    this.cell2.setHasFlag(true);

    WorldScene expectedScene = this.emptyWS;
    expectedScene.placeImageXY(new RectangleImage(50, 50, OutlineMode.SOLID, Color.GREEN), 
        175, 175);
    expectedScene.placeImageXY(new RectangleImage(50, 50, OutlineMode.OUTLINE, Color.BLACK),
        175, 175);
    expectedScene.placeImageXY(new EquilateralTriangleImage(10, OutlineMode.SOLID, Color.RED), 
        175, 175);

    t.checkExpect(this.cell2.drawCell(this.emptyWS, 3, 3), expectedScene);

    // Test an active cell without a flag
    this.cell3.setActive(true);
    this.cell3.setHasFlag(false);

    WorldScene expectedScene1 = this.emptyWS;
    expectedScene1.placeImageXY(new RectangleImage(50, 50, OutlineMode.SOLID, Color.GREEN),
        175, 175);
    expectedScene1.placeImageXY(new RectangleImage(50, 50, OutlineMode.OUTLINE, Color.BLACK), 
        175, 175);

    t.checkExpect(this.cell3.drawCell(this.emptyWS, 3, 3), expectedScene1);

    // Test an inactive cell with 1 neighboring mine
    this.w110.setActive(false);

    WorldScene expectedScene2 = this.emptyWS;
    expectedScene2.placeImageXY(new RectangleImage(50, 50, OutlineMode.SOLID, Color.GREEN), 
        175, 175);
    expectedScene2.placeImageXY(new RectangleImage(50, 50, OutlineMode.OUTLINE, Color.BLACK), 
        175, 175);
    expectedScene2.placeImageXY(new TextImage("1", 20, Color.BLACK), 175, 175);

    t.checkExpect(this.w110.drawCell(this.emptyWS, 3, 3), expectedScene2);

    // Test an inactive cell with 3 neighboring mines
    this.w112.setActive(false);

    WorldScene expectedScene4 = this.emptyWS;
    expectedScene4.placeImageXY(new RectangleImage(50, 50, OutlineMode.SOLID, Color.GREEN), 
        175, 175);
    expectedScene4.placeImageXY(new RectangleImage(50, 50, OutlineMode.OUTLINE, Color.BLACK), 
        175, 175);
    expectedScene4.placeImageXY(new TextImage("3", 20, Color.BLACK), 175, 175);

    t.checkExpect(this.w112.drawCell(this.emptyWS, 3, 3), expectedScene4);

    // Test an inactive cell with 0 neighboring mines
    this.w100.setActive(false);

    WorldScene expectedScene3 = this.emptyWS;
    expectedScene3.placeImageXY(new RectangleImage(50, 50, OutlineMode.SOLID, Color.GREEN),
        175, 175);
    expectedScene3.placeImageXY(new RectangleImage(50, 50, OutlineMode.OUTLINE, Color.BLACK), 
        175, 175);
    expectedScene3.placeImageXY(new TextImage("0", 20, Color.BLACK), 175, 175);

    t.checkExpect(this.w100.drawCell(this.emptyWS, 3, 3), expectedScene3);
  }

  //test the make grid method 
  void testMakeGrid(Tester t) {
    setup();
    // test an empty grid
    t.checkExpect(this.emptyWorld.grid, this.emptyGrid);
    // test a non-empty grid
    this.activeClearCell.cellSize = 15;
    t.checkExpect(this.world1.makeGrid(3, 3), this.world1Grid);
  }

  // test the add neighbor method 
  void testAddNeighbor(Tester t) {
    setup();
    // test adding a neighbor to a cell with no neighbors 
    this.activeClearCell.addNeighbor(this.activeMinedCell);
    t.checkExpect(this.activeClearCell.neighbors,
        new ArrayList<Cell>(Arrays.asList(this.activeMinedCell)));
    // test adding a neighbor to a cell with a neighbor 
    this.activeClearCell.addNeighbor(this.activeClearCell);
    t.checkExpect(this.activeClearCell.neighbors, 
        new ArrayList<Cell>(Arrays.asList(this.activeMinedCell, this.activeClearCell)));
  }

  // test the set contains mined method 
  void testSetContainsMine(Tester t) {
    setup();
    // test going from mine to no mine 
    this.activeMinedCell.setContainsMine(false);
    t.checkExpect(this.activeMinedCell.containsMine, false);
    // test going from no mine to mine 
    this.activeClearCell.setContainsMine(true);
    t.checkExpect(this.activeClearCell.containsMine, true);
  }

  // test the set active method 
  void testSetActive(Tester t) {
    setup();
    // test going from active to inactive
    this.activeClearCell.setActive(false);
    t.checkExpect(this.activeClearCell.active, false);
    // test going from inactive to active
    this.activeClearCell.setActive(true);
    t.checkExpect(this.activeClearCell.active, true);
  }

  // tests the method set has flag method
  void testSetHasFlag(Tester t) {
    setup();
    // tests going from no flag to flag
    this.cell2.setHasFlag(true);
    t.checkExpect(this.cell2.hasFlag, true);
    // tests going from flag to no flag
    this.cell2.setHasFlag(false);
    t.checkExpect(this.cell2.hasFlag, false);
  }
  
  // test the on mouse clicked method 
  void testOnMouseClicked(Tester t) {
    setup();
    // test when game state == 0 and the mouse is clicked not on a button 
    // testing by checking that the game is the same before and after 
    MinesweeperWorld world3Before = new MinesweeperWorld(this.world3.rows,
        this.world3.cols, this.world3.numMines, this.world3.rand, this.world3.cellSize);
    world3Before.grid = this.world3.grid;
    t.checkExpect(world3Before, this.world3);
    this.world3.onMouseClicked(new Posn(0, 0), "LeftButton");
    t.checkExpect(world3Before, this.world3);
    // testing checking that the easy button works 
    setup();
    this.world3.onMouseClicked(new Posn(26, 126), "LeftButton");
    world3Before.cellSize = 45;
    world3Before.rows = 10;
    world3Before.cols = 5;
    world3Before.numMines = 20;
    world3Before.grid = new MinesweeperWorld(world3Before.rows, world3Before.cols,
        world3Before.numMines, rand, world3Before.cellSize).grid;
    world3Before.grid = this.world3.grid;
    world3Before.gameState = 1;
    t.checkExpect(this.world3, world3Before);
    // testing checking that the medium button works 
    setup();
    this.world3.onMouseClicked(new Posn(175, 125), "LeftButton");
    world3Before.cellSize = 30;
    world3Before.rows = 15;
    world3Before.cols = 8;
    world3Before.numMines = 49;
    world3Before.grid = this.world3.grid;
    world3Before.gameState = 1;
    t.checkExpect(this.world3, world3Before);
    // testing checking that the hard button works 
    setup();
    this.world3.onMouseClicked(new Posn(325, 125), "LeftButton");
    world3Before.cellSize = 15;
    world3Before.rows = 30;
    world3Before.cols = 16;
    world3Before.numMines = 99;
    world3Before.grid = this.world3.grid;
    world3Before.gameState = 1;
    t.checkExpect(this.world3, world3Before);
    // testing when game state == 1
    setup();
    this.world3.gameState = 1;
    // testing adding a flag
    // test that there is no flag currently
    t.checkExpect(this.world3.grid.get(0).get(0).hasFlag, false);
    this.world3.onMouseClicked(new Posn(0, 0), "RightButton");
    // test that there is a flag now 
    t.checkExpect(this.world3.grid.get(0).get(0).hasFlag, true);
    // test that clicking on a cell with no mine works 
    // show that the cell is active prior to the click
    t.checkExpect(this.world3.grid.get(0).get(0).isActive(), true);
    this.world3.grid.get(0).get(0).hasFlag = false;
    this.world3.onMouseClicked(new Posn(0, 0), "LeftButton");
    // show that the cell is inactive after the click
    t.checkExpect(this.world3.grid.get(0).get(0).isActive(), false);
    // test left clicking on a cell with a mine 
    this.world3.grid.get(10).get(3).containsMine = true;
    this.world3.onMouseClicked(new Posn(150, 45), "LeftButton");
    t.checkExpect(this.world3.gameState, 2);
    // test when game state == 2
    setup();
    this.world3.gameState = 2;
    this.world3.onMouseClicked(new Posn(175, 75), "LeftButton");
    // check that the properties that need to be reset are reset 
    t.checkExpect(this.world3.score, 0);
    t.checkExpect(this.world3.gameState, 0);
    t.checkExpect(this.world3.clicks, 0);
    t.checkExpect(this.world3.rows, 30);
    t.checkExpect(this.world3.cols, 16);
    t.checkExpect(this.world3.numMines, 99);
  }

  // test the has mine method 
  void testHasMine(Tester t) {
    setup();
    // test on a cell that has a mine
    t.checkExpect(this.activeMinedCell.hasMine(), true);
    // test on a cell with no mine
    t.checkExpect(this.activeClearCell.hasMine(), false);
  }

  // test the set neighbors and mines method 
  void testSetNeighborsAndMines(Tester t) {
    setup();
    // test an empty grid 
    t.checkExpect(this.emptyWorld.setNeighborsAndMines(0, 0, 0), this.emptyGrid);
    // test on a 3x3 grid 
    this.world1.grid = this.world1GridMined;
    t.checkExpect(this.world1.setNeighborsAndMines(3, 3, 3), this.world1GridWithNeighbors);
  }

  // test the count neighboring mines method 
  void testCountNeighboringMines(Tester t) {
    setup();
    // test a cell with no neighbors
    t.checkExpect(this.activeClearCell.countNeighboringMines(), 0);
    // test a cell with no neighboring mines but containing neighbors
    t.checkExpect(this.w100.countNeighboringMines(), 0);
    // test a cell with 1 neighboring mine 
    t.checkExpect(this.w110.countNeighboringMines(), 1);
    // test a cell with multiple neighboring mines
    t.checkExpect(this.w112.countNeighboringMines(), 3);
    // test a cell with a mine that has a neighboring mine
    t.checkExpect(this.w122.countNeighboringMines(), 1);
  }

  //tests the is game won method 
  void testIsGameWon(Tester t) {
    setup();
    // test on a game that has not been won
    t.checkExpect(this.world3.isGameWon(), false);
    // test on a game that has been won
    this.world4 = new MinesweeperWorld(5, 5, 2, new Random(5));
    this.world4.grid.get(2).get(2).setHasFlag(true);
    this.world4.grid.get(4).get(4).setHasFlag(true);
    t.checkExpect(this.world4.isGameWon(), true);
    t.checkExpect(this.world4.won, true);
  }

  //tests the is active method 
  void testIsActive(Tester t) {
    setup();
    // test on an active cell
    t.checkExpect(this.activeClearCell.isActive(), true);
    // test on an inactive cell
    this.activeClearCell.setActive(false);
    t.checkExpect(this.activeClearCell.isActive(), false);
  }

  // TODO uncomment this out before submitting
  //initialize BigBang WorldScene
  void testBigBang(Tester t) {
    setup();

    this.world2.grid.get(1).get(1).setActive(false);
    this.world2.grid.get(0).get(0).setHasFlag(true);

    this.world3.bigBang(450, 300, 5);
  }

  // tests the method countMines
  void testCountMines(Tester t) {
    setup();
    // tests a cell with a mine
    this.w100.setContainsMine(true);
    t.checkExpect(this.w100.countMines(), 1);
    // tests a cell without a mine
    this.w101.setContainsMine(false);
    t.checkExpect(this.w101.countMines(), 0);
  }

  // tests the method correctFlags
  void testCorrectFlags(Tester t) {
    setup();
    // tests a cell that has a mine and is flagged
    this.w100.setContainsMine(true);
    this.w100.setHasFlag(true);
    t.checkExpect(this.w100.correctFlags(), true);
    // tests a cell that does not have a mine and is not flagged
    this.w101.setContainsMine(false);
    this.w101.setHasFlag(false);
    t.checkExpect(this.w101.correctFlags(), true);
    // tests a cell that has a mine but is not flagged
    this.w102.setContainsMine(true);
    this.w102.setHasFlag(false);
    t.checkExpect(this.w102.correctFlags(), false);
    // tests a cell that does not have a mine but is flagged
    this.w110.setContainsMine(false);
    this.w110.setHasFlag(true);
    t.checkExpect(this.w110.correctFlags(), false);
  }

  // tests the method toggleFlag
  void testToggleFlag(Tester t) {
    setup();
    // tests a cell that is previously not flagged
    this.w100.setHasFlag(false);
    this.w100.toggleFlag();
    t.checkExpect(this.w100.hasFlag, true);
    // tests a cell that is previously flagged
    this.w101.setHasFlag(true);
    this.w101.toggleFlag();
    t.checkExpect(this.w101.hasFlag, false);
  }

  // tests the method drawNum
  void testDrawNum(Tester t) {
    setup();
    // tests a cell with 0 neighboring mines
    t.checkExpect(this.activeClearCell.drawNum(), new TextImage("", Color.BLACK));
    // tests a cell with 1 neighboring mine
    t.checkExpect(this.w110.drawNum(), new TextImage("1", Color.BLUE));
    // tests a cell with 2 neighboring mine
    this.w100.setContainsMine(true);
    this.w101.setContainsMine(false);
    this.w102.setContainsMine(false);
    this.w112.setContainsMine(false);
    this.w122.setContainsMine(false);
    this.w120.setContainsMine(false);
    this.w110.setContainsMine(false);
    t.checkExpect(this.w111.drawNum(), new TextImage("2", Color.yellow));
    // tests a cell with 3 neighboring mine
    this.w100.setContainsMine(true);
    this.w101.setContainsMine(true);
    this.w102.setContainsMine(false);
    this.w112.setContainsMine(false);
    this.w122.setContainsMine(false);
    this.w120.setContainsMine(false);
    this.w110.setContainsMine(false);
    t.checkExpect(this.w111.drawNum(), new TextImage("3", Color.PINK));
    // tests a cell with 4 neighboring mine
    this.w100.setContainsMine(true);
    this.w101.setContainsMine(true);
    this.w102.setContainsMine(true);
    this.w112.setContainsMine(false);
    this.w122.setContainsMine(false);
    this.w120.setContainsMine(false);
    this.w110.setContainsMine(false);
    t.checkExpect(this.w111.drawNum(), new TextImage("4", Color.CYAN));
    // tests a cell with 5 neighboring mine
    this.w100.setContainsMine(true);
    this.w101.setContainsMine(true);
    this.w102.setContainsMine(true);
    this.w112.setContainsMine(true);
    this.w122.setContainsMine(false);
    this.w120.setContainsMine(false);
    this.w110.setContainsMine(false);
    t.checkExpect(this.w111.drawNum(), new TextImage("5", Color.orange));
    // tests a cell with 6 neighboring mine
    this.w100.setContainsMine(true);
    this.w101.setContainsMine(true);
    this.w102.setContainsMine(true);
    this.w112.setContainsMine(true);
    this.w122.setContainsMine(true);
    this.w120.setContainsMine(false);
    this.w110.setContainsMine(false);
    t.checkExpect(this.w111.drawNum(), new TextImage("6", Color.black));
  }

  // test the set inactive neighbors method 
  void testSetInactiveNeighbors(Tester t) {
    setup();
    // test on a cell with no neighbors
    this.activeClearCell.setInactiveNeighbors();
    t.checkExpect(this.activeClearCell.neighbors,
        new ArrayList<Cell>());
    // test on a cell with all 8 neighbors
    this.w111.setInactiveNeighbors();
    // checking each of 8 neighbors individually 
    t.checkExpect(this.w111.neighbors.get(0).isActive(), false);
    t.checkExpect(this.w111.neighbors.get(1).isActive(), false);
    t.checkExpect(this.w111.neighbors.get(2).isActive(), false);
    t.checkExpect(this.w111.neighbors.get(3).isActive(), false);
    t.checkExpect(this.w111.neighbors.get(4).isActive(), false);
    t.checkExpect(this.w111.neighbors.get(5).isActive(), false);
    t.checkExpect(this.w111.neighbors.get(6).isActive(), false);
    t.checkExpect(this.w111.neighbors.get(7).isActive(), false);
  }


  // tests the method onTick
  void testOnTick(Tester t) {
    setup();
    // tests first tick
    this.world2.score = 0;
    this.world2.onTick();
    t.checkExpect(this.world2.score, 1);
    // tests second tick
    this.world2.onTick();
    t.checkExpect(this.world2.score, 2);
    // tests third tick
    this.world2.onTick();
    t.checkExpect(this.world2.score, 3);
  }

  //test the flood fill method 
  void testFloodFill(Tester t) {
    setup();
    // test on a cell with no neighbors
    this.activeClearCell.floodfill();
    t.checkExpect(this.activeClearCell.neighbors, 
        new ArrayList<Cell>());
    // test on a cell with all 8 neighbors
    this.w111.floodfill();
    // checking neighbors individually to ensure that they are all set active
    t.checkExpect(this.w111.neighbors.get(0).isActive(), true);
    t.checkExpect(this.w111.neighbors.get(1).isActive(), true);
    t.checkExpect(this.w111.neighbors.get(2).isActive(), true);
    t.checkExpect(this.w111.neighbors.get(3).isActive(), true);
    t.checkExpect(this.w111.neighbors.get(4).isActive(), true);
    t.checkExpect(this.w111.neighbors.get(5).isActive(), true);
    t.checkExpect(this.w111.neighbors.get(6).isActive(), true);
    t.checkExpect(this.w111.neighbors.get(7).isActive(), true);
  }
}