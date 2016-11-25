package ca.mcgill.ecse211.team11.pathfinding;

import java.util.ArrayList;

import ca.mcgill.ecse211.team11.Constants;
import ca.mcgill.ecse211.team11.Initializer;
import ca.mcgill.ecse211.team11.Logger;
import ca.mcgill.ecse211.team11.Navigation;
import ca.mcgill.ecse211.team11.Odometer;
import ca.mcgill.ecse211.team11.USSensorController;
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
  private Navigation navigation;
  private Odometer odometer;
  private USSensorController usSensorController;
  public ArrayList<Integer[]> locationOfObjects = new ArrayList<Integer[]>();

  public InternalGrid(Initializer init) {
    grid = new InternalGridSquare[2 * Constants.BOARD_SIZE][2 * Constants.BOARD_SIZE];
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid.length; j++) {
        grid[i][j] = InternalGridSquare.UNKNOWN;
      }
    }
    navigation = init.navigation;
    odometer = init.odometer;
    usSensorController = init.usSensorController;
  }
  
  public InternalGrid() {
    grid = new InternalGridSquare[2 * Constants.BOARD_SIZE][2 * Constants.BOARD_SIZE];
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid.length; j++) {
        grid[i][j] = InternalGridSquare.UNKNOWN;
      }
    }
  }

  /**
   * Calculates a short path from startNode to endNode
   * 
   * @param start The starting location
   * @param end The ending location
   * @return The calculated path
   */
  public Path pathfindTo(PathNode start, PathNode end) {
    Path p = new Path(start);
    p.addNode(end);
    return p;
  }

  /**
   * Scans the surrounding with the ultrasonic sensor area by rotating on itself.
   */
  public void scan() {
    double initialHeading = odometer.getTheta();
    double finalHeading = Util.normalizeAngle360(initialHeading - Math.toRadians(355));

    navigation.turnClockwise(true);

    // While scanning
    while (Math.abs(odometer.getTheta() - finalHeading) > Constants.ANGLE_ERROR) {

      double distance = usSensorController.getDistance();

      // If an object in range
      if (distance < Constants.SCANNING_RANGE) {

        // Find object coordinates
        double objectDistanceFromWheels = Constants.DIST_CENTER_TO_US_SENSOR + distance * 100;
        double objectXCoordinate =
            odometer.getX() + objectDistanceFromWheels * Math.cos(odometer.getTheta());
        double objectYCoordinate =
            odometer.getY() + objectDistanceFromWheels * Math.sin(odometer.getTheta());

        // Get grid coordinates
        int[] currentGrid = Util.convertToInternalGrid(odometer.getX(), odometer.getY());
        int[] objectGridLocation = Util.convertToInternalGrid(objectXCoordinate, objectYCoordinate);
        ArrayList<Integer[]> gridsInLineOfSight = Util.getGridsInLineOfSight(currentGrid[0],
            currentGrid[1], objectGridLocation[0], objectGridLocation[1]);

        // Update grid info
        if (isModifiableGrid(gridsInLineOfSight.get(gridsInLineOfSight.size() - 1))) {
          if (grid[objectGridLocation[0]][objectGridLocation[1]] == InternalGridSquare.UNKNOWN) {
            grid[objectGridLocation[0]][objectGridLocation[1]] = InternalGridSquare.UNKNOWN_BLOCK;
            locationOfObjects.add(new Integer[] {objectGridLocation[0], objectGridLocation[1]});
          }
        }
        for (int i = 0; i < gridsInLineOfSight.size() - 1; i++) {
          Integer[] gridTileCoordinates = gridsInLineOfSight.get(i);
          if (isModifiableGrid(gridTileCoordinates)) {
            grid[gridTileCoordinates[0]][gridTileCoordinates[1]] = InternalGridSquare.EMPTY;
          }
        }

      } else {

        // Find end coordinates
        double endDistanceFromWheels =
            Constants.DIST_CENTER_TO_US_SENSOR + Constants.SCANNING_RANGE * 100;
        double endXCoordinate =
            odometer.getX() + endDistanceFromWheels * Math.cos(odometer.getTheta());
        double endYCoordinate =
            odometer.getY() + endDistanceFromWheels * Math.sin(odometer.getTheta());

        // Get grid coordinates
        int[] currentGrid = Util.convertToInternalGrid(odometer.getX(), odometer.getY());
        int[] endGridLocation = Util.convertToInternalGrid(endXCoordinate, endYCoordinate);
        ArrayList<Integer[]> gridsInLineOfSight = Util.getGridsInLineOfSight(currentGrid[0],
            currentGrid[1], endGridLocation[0], endGridLocation[1]);

        // Update grid info
        for (int i = 0; i < gridsInLineOfSight.size(); i++) {
          Integer[] gridTileCoordinates = gridsInLineOfSight.get(i);
          if (isModifiableGrid(gridTileCoordinates)) {
            grid[gridTileCoordinates[0]][gridTileCoordinates[1]] = InternalGridSquare.EMPTY;
          }
        }
      }
    }
    navigation.setSpeeds(0, 0);
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
  
  public void updateNoEntryZone() {
    for (int i = 0; i < grid.length; i++) {
      grid[0][i] = InternalGridSquare.NO_ENTRY;
      grid[i][0] = InternalGridSquare.NO_ENTRY;
      grid[grid.length-1][i] = InternalGridSquare.NO_ENTRY;
      grid[i][grid.length-1] = InternalGridSquare.NO_ENTRY;
    }
    
    grid[grid.length-2][grid.length-2] = InternalGridSquare.NO_ENTRY;
    grid[1][grid.length-2] = InternalGridSquare.NO_ENTRY;
    grid[grid.length-2][1] = InternalGridSquare.NO_ENTRY;
    grid[1][1] = InternalGridSquare.NO_ENTRY;
  }

  /**
   * 
   * @param gridCoordinates grid's X and Y
   * @return true if allowed to edit grid's status. false if not allowed to edit grid's status.
   */
  private boolean isModifiableGrid(Integer[] gridCoordinates) {
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
    String board = "";
    for (int j = grid.length-1; j >= 0; j--) {
      for (int i = 0; i < grid.length; i++) {
        board = board + "| " + grid[i][j] + " |";
      }
      board = board + "\n";
    }
    if (output == 0) {
      Logger.logData("Board\n:"+board);
    } else {
      System.out.println(board);  
    }
  }
}
