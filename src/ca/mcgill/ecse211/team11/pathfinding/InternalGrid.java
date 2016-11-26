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
  private InternalGridSquare[][] grid;

  /**
   * Constructs an InternalGrid object.
   */
  public InternalGrid() {
    grid = new InternalGridSquare[2 * Constants.BOARD_SIZE][2 * Constants.BOARD_SIZE];
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid.length; j++) {
        grid[i][j] = InternalGridSquare.UNKNOWN;
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
   * Acceses the state of a grid cell referenced by its grid indices.
   * 
   * @param i The horizontal index of the cell
   * @param j The vertical index of the cell
   * @return The state of the cell
   */
  public InternalGridSquare getCellByIndex(int i, int j) {
    return grid[i][j];
  }
  
  /**
   * Modifies the state of a grid cell referenced by its grid indices.
   * 
   * @param i The horizontal index of the cell
   * @param j The vertical index of the cell
   * @param newState The new state of the cell
   */
  public void setCellByIndex(int i, int j, InternalGridSquare newState) {
    grid[i][j] = newState;
  }
  
  /**
   * Accesses the state of a cell by its coordinates
   * 
   * @param x The x coordinate of the cell to access
   * @param y The y coordinate of the cell to access
   * @return The state of the cell
   */
  public InternalGridSquare getCellByCoord(double x, double y) {
    return grid[coordToGrid(x)][coordToGrid(y)];
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
    
    while(!fringe.isEmpty()) {
      SearchNode nextNode = fringe.poll();
      
      SearchNode[] neighbours = nextNode.getNeighbours();
      for (SearchNode n : neighbours) {
        if (n.distTo(end) < Constants.GRID_SIZE) {
          Path path = n.getPath();
          PathNode lastNode = path.lastNode();
          double heading = Util.calculateHeading(lastNode.getX(), lastNode.getY(), end.getX(), end.getY());
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
        grid[i][j] = InternalGridSquare.GREEN_ZONE;
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
        grid[i][j] = InternalGridSquare.RED_ZONE;
      }
    }
  }
  
  public void updateNoEntryZone(int startCorner) {
    for (int i = 0; i < grid.length; i++) {
      grid[0][i] = InternalGridSquare.NO_ENTRY;
      grid[i][0] = InternalGridSquare.NO_ENTRY;
      grid[grid.length-1][i] = InternalGridSquare.NO_ENTRY;
      grid[i][grid.length-1] = InternalGridSquare.NO_ENTRY;
    }
    
    switch(startCorner) {
      case 1:
        grid[grid.length-2][grid.length-2] = InternalGridSquare.NO_ENTRY;
        grid[1][grid.length-2] = InternalGridSquare.NO_ENTRY;
        grid[grid.length-2][1] = InternalGridSquare.NO_ENTRY;
        break;
      
      case 2:
        grid[grid.length-2][grid.length-2] = InternalGridSquare.NO_ENTRY;
        grid[1][grid.length-2] = InternalGridSquare.NO_ENTRY;
        grid[1][1] = InternalGridSquare.NO_ENTRY;
        break;
        
      case 3:
        grid[1][1] = InternalGridSquare.NO_ENTRY;
        grid[1][grid.length-2] = InternalGridSquare.NO_ENTRY;
        grid[grid.length-2][1] = InternalGridSquare.NO_ENTRY;
        break;
        
      case 4:
        grid[grid.length-2][grid.length-2] = InternalGridSquare.NO_ENTRY;
        grid[1][1] = InternalGridSquare.NO_ENTRY;
        grid[grid.length-2][1] = InternalGridSquare.NO_ENTRY;
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
    InternalGridSquare gridStatus = grid[gridCoordinates[0]][gridCoordinates[1]];

    if (gridStatus == InternalGridSquare.NO_ENTRY || gridStatus == InternalGridSquare.GREEN_ZONE
        || gridStatus == InternalGridSquare.RED_ZONE) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * Prints the board to the desired output
   * 
   * @param output if output is 0, then board will be printed to the log file. otherwise, board will be printed to System.out
   */
  public void printBoard(int output) {
    String board = toString();
    if (output == 0) {
      Logger.logData("Board\n:"+board);
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
    for (int j = grid.length-1; j >= 0; j--) {
      for (int i = 0; i < grid.length; i++) {
        board += "| " + grid[i][j] + " |";
      }
      board += "\n";
    }
    return board;
  }
}
