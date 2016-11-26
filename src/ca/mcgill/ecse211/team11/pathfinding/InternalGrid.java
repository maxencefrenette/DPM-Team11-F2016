package ca.mcgill.ecse211.team11.pathfinding;

import java.util.Comparator;
import java.util.PriorityQueue;

import ca.mcgill.ecse211.team11.Constants;
import ca.mcgill.ecse211.team11.Logger;
import ca.mcgill.ecse211.team11.Util;

/**
 * Stores the internal representation of the field.
 * <p>
 * The field is divided in discrete sectors that each have a state.
 * 
 * @author Maxence Frenette
 * @version 4.0
 * @since 2.0
 */
public class InternalGrid {
  private InternalGridCell[][] grid;

  /**
   * Constructs an InternalGrid object.
   * 
   * @param boardSize The size of the board (the amount of physical board tiles, not internal grid
   *        tiles)
   */
  public InternalGrid(int boardSize) {
    grid = new InternalGridCell[2 * boardSize][2 * boardSize];
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid.length; j++) {
        grid[i][j] = InternalGridCell.UNKNOWN;
      }
    }
  }

  /**
   * Converts a real-world coordinate to a grid coordinate
   * 
   * @param coordinate The coordinate to convert
   * @return The corresponding grid index
   */
  public int coordToGrid(double coordinate) {
    return (int) (Math.floor(coordinate) / (Constants.GRID_SIZE / 2));
  }

  /**
   * Converts a grid index to a real world coordinate
   * 
   * @param gridIndex
   * @return The corresponding grid index
   */
  public double gridToCoord(int gridIndex) {
    return (gridIndex + 0.5) * (Constants.GRID_SIZE / 2);
  }

  /**
   * Converts a (x,y) coordinate into an equivalent x,y in the internal grid.
   * 
   * @param x x-coordinate
   * @param y y-coordinate
   * @return array containing the x and y of the corresponding internal grid
   */
  public int[] convertToInternalGrid(double x, double y) {
    int internalGridX = coordToGrid(x);
    int internalGridY = coordToGrid(y);

    return new int[] {internalGridX, internalGridY};
  }

  /**
   * Accesses the state of a grid cell referenced by its grid indices.
   * 
   * @param i The horizontal index of the cell
   * @param j The vertical index of the cell
   * @return The state of the cell
   */
  public InternalGridCell getCellByIndex(int i, int j) {
    if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length) {
      return InternalGridCell.NO_ENTRY;
    }
    return grid[i][j];
  }

  /**
   * Accesses the state of a cell by its coordinates
   * 
   * @param x The x coordinate of the cell to access
   * @param y The y coordinate of the cell to access
   * @return The state of the cell
   */
  public InternalGridCell getCellByCoord(double x, double y) {
    return getCellByIndex(coordToGrid(x), coordToGrid(y));
  }

  /**
   * Modifies the state of a grid cell referenced by its grid indices.
   * 
   * @param i The horizontal index of the cell
   * @param j The vertical index of the cell
   * @param newState The new state of the cell
   */
  public void setCellByIndex(int i, int j, InternalGridCell newState) {
    grid[i][j] = newState;
  }

  /**
   * Modifies the state of a rectangular zone in the grid.
   * 
   * @param i1 The left of the rectangle (included)
   * @param i2 The right of the rectangle (excluded)
   * @param j1 The bottom of the rectangle (included)
   * @param j2 The top of the rectangle (excluded)
   */
  public void setZoneByIndex(int i1, int j1, int i2, int j2, InternalGridCell state) {
    for (int i = i1; i < i2; i++) {
      for (int j = j1; j < j2; j++) {
        setCellByIndex(i, j, state);
      }
    }
  }

  /**
   * Calculates a short path from startNode to endNode
   * 
   * @param start The starting location
   * @param end The ending location
   * @return The calculated path
   * @throws Exception
   */
  public Path pathfindTo(PathNode start, final PathNode end) throws Exception {
    // This comparator will be in charge of sorting the PriorityQueue
    class SearchNodeComparator implements Comparator<SearchNode> {
      @Override
      public int compare(SearchNode n1, SearchNode n2) {
        return (int) (n1.f(end) - n2.f(end));
      }
    }

    PriorityQueue<SearchNode> fringe = new PriorityQueue<SearchNode>(new SearchNodeComparator());
    fringe.add(new SearchNode(start));

    while (!fringe.isEmpty()) {
      SearchNode nextNode = fringe.poll();

      SearchNode[] neighbours = nextNode.getNeighbours();
      for (SearchNode n : neighbours) {
        if (n.distTo(end) < Constants.GRID_SIZE) {
          Path path = n.getPath();
          PathNode lastNode = path.lastNode();
          double heading =
              Util.calculateHeading(lastNode.getX(), lastNode.getY(), end.getX(), end.getY());
          path.addNode(new PathNode(lastNode.getX(), lastNode.getY(), heading));

          // TODO remove aligned waypoints

          return path;
        }

        if (n.isValid(this) && !fringe.contains(n)) {
          fringe.add(n);
        }
      }
    }

    throw new Exception("No path could be found");
  }

  /**
   * Update internal grid with green zone tiles
   * 
   * @param LGZx x coordinate of lower left corner of green zone given through WiFi
   * @param LGZy y coordinate of lower left corner of green zone given through WiFi
   * @param UGZx x coordinate of upper right corner of green zone given through WiFi
   * @param UGZy y coordinate of upper right corner of green zone given through WiFi
   */
  public void updateGreenZone(int LGZx, int LGZy, int UGZx, int UGZy) {
    int dx = (UGZx - LGZx) * 2;
    int dy = (UGZy - LGZy) * 2;

    int lowerLeftGridX = (LGZx + 1) * 2;
    int lowerLeftGridY = (LGZy + 1) * 2;

    for (int i = lowerLeftGridX; i < lowerLeftGridX + dx; i++) {
      for (int j = lowerLeftGridY; j < lowerLeftGridY + dy; j++) {
        grid[i][j] = InternalGridCell.GREEN_ZONE;
      }
    }
  }

  /**
   * Update internal grid with red zone tiles
   * 
   * @param LRZx x coordinate of lower left corner of green zone given through WiFi
   * @param LRZy y coordinate of lower left corner of green zone given through WiFi
   * @param URZx x coordinate of upper right corner of green zone given through WiFi
   * @param URZy y coordinate of upper right corner of green zone given through WiFi
   */
  public void updateRedZone(int LRZx, int LRZy, int URZx, int URZy) {
    int dx = (URZx - LRZx) * 2;
    int dy = (URZy - LRZy) * 2;

    int lowerLeftGridX = (LRZx + 1) * 2;
    int lowerLeftGridY = (LRZy + 1) * 2;

    for (int i = lowerLeftGridX; i < lowerLeftGridX + dx; i++) {
      for (int j = lowerLeftGridY; j < lowerLeftGridY + dy; j++) {
        grid[i][j] = InternalGridCell.RED_ZONE;
      }
    }
  }

  public void updateNoEntryZone(int startCorner) {
    for (int i = 0; i < grid.length; i++) {
      grid[0][i] = InternalGridCell.NO_ENTRY;
      grid[i][0] = InternalGridCell.NO_ENTRY;
      grid[grid.length - 1][i] = InternalGridCell.NO_ENTRY;
      grid[i][grid.length - 1] = InternalGridCell.NO_ENTRY;
    }

    switch (startCorner) {
      case 1:
        grid[grid.length - 2][grid.length - 2] = InternalGridCell.NO_ENTRY;
        grid[1][grid.length - 2] = InternalGridCell.NO_ENTRY;
        grid[grid.length - 2][1] = InternalGridCell.NO_ENTRY;
        break;

      case 2:
        grid[grid.length - 2][grid.length - 2] = InternalGridCell.NO_ENTRY;
        grid[1][grid.length - 2] = InternalGridCell.NO_ENTRY;
        grid[1][1] = InternalGridCell.NO_ENTRY;
        break;

      case 3:
        grid[1][1] = InternalGridCell.NO_ENTRY;
        grid[1][grid.length - 2] = InternalGridCell.NO_ENTRY;
        grid[grid.length - 2][1] = InternalGridCell.NO_ENTRY;
        break;

      case 4:
        grid[grid.length - 2][grid.length - 2] = InternalGridCell.NO_ENTRY;
        grid[1][1] = InternalGridCell.NO_ENTRY;
        grid[grid.length - 2][1] = InternalGridCell.NO_ENTRY;
        break;
    }
  }

  /**
   * Checks if a cell is editable
   * 
   * @param gridCoordinates x and y coordinates of the cell to check
   * @return true if allowed to edit grid's status. false if not allowed to edit grid's status.
   */
  public boolean isModifiableGrid(Integer[] gridCoordinates) {
    InternalGridCell gridStatus = grid[gridCoordinates[0]][gridCoordinates[1]];

    if (gridStatus == InternalGridCell.NO_ENTRY || gridStatus == InternalGridCell.GREEN_ZONE
        || gridStatus == InternalGridCell.RED_ZONE) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * Prints the board to the desired output
   * 
   * @param output if output is 0, then board will be printed to the log file. otherwise, board will
   *        be printed to System.out
   */
  public void printBoard(int output) {
    String board = toString();
    if (output == 0) {
      Logger.logData("Board\n:" + board);
    } else {
      System.out.println(board);
    }
  }

  /**
   * Converts the board to a string
   * 
   * @return The resulting string
   */
  @Override
  public String toString() {
    String board = "";
    String horizontalLine = "";
    for (int i = 0; i < grid[0].length; i++) {
      horizontalLine += "+---";
    }
    horizontalLine += "+\n";

    for (int j = grid.length - 1; j >= 0; j--) {
      board += horizontalLine;
      for (int i = 0; i < grid.length; i++) {
        board += "| " + grid[i][j] + " ";
      }
      board += "|\n";
    }
    board += horizontalLine;

    return board;
  }
}
