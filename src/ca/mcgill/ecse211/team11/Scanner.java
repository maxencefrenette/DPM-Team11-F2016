package ca.mcgill.ecse211.team11;

import java.util.ArrayList;

import ca.mcgill.ecse211.team11.pathfinding.InternalGrid;
import ca.mcgill.ecse211.team11.pathfinding.InternalGridSquare;

public class Scanner extends Thread {
  private boolean scanning = false;
  public ArrayList<Integer[]> locationOfObjects = new ArrayList<Integer[]>();
  
  private USSensorController usSensorController;
  private Odometer odometer;
  private InternalGrid grid;

  public Scanner(Initializer init, InternalGrid grid) {
    usSensorController = init.usSensorController;
    odometer = init.odometer;
    this.grid = grid;
  }

  @Override
  public void run() {
    while (true) {
      while (scanning) {
        scan();
      }
      
      Util.sleep(100);
    }
  }

  /**
   * Scans the field in front of the robot without rotating.
   */
  private void scan() {
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
      int[] currentGrid = grid.convertToInternalGrid(odometer.getX(), odometer.getY());
      int[] objectGridLocation = grid.convertToInternalGrid(objectXCoordinate, objectYCoordinate);
      ArrayList<Integer[]> gridsInLineOfSight =
          Util.getGridsInLineOfSight(currentGrid[0], currentGrid[1], objectGridLocation[0],
              objectGridLocation[1]);

      // Update grid info
      if (grid.isModifiableGrid(gridsInLineOfSight.get(gridsInLineOfSight.size() - 1))) {
        if (grid.getCellByIndex(objectGridLocation[0], objectGridLocation[1]) == InternalGridSquare.UNKNOWN) {
          grid.setCellByIndex(objectGridLocation[0], objectGridLocation[1], InternalGridSquare.UNKNOWN_BLOCK);
          locationOfObjects.add(new Integer[] {objectGridLocation[0], objectGridLocation[1]});
        }
      }
      for (int i = 0; i < gridsInLineOfSight.size() - 1; i++) {
        Integer[] gridTileCoordinates = gridsInLineOfSight.get(i);
        if (grid.isModifiableGrid(gridTileCoordinates)) {
          grid.setCellByIndex(objectGridLocation[0], objectGridLocation[1], InternalGridSquare.EMPTY);
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
      int[] currentGrid = grid.convertToInternalGrid(odometer.getX(), odometer.getY());
      int[] endGridLocation = grid.convertToInternalGrid(endXCoordinate, endYCoordinate);
      ArrayList<Integer[]> gridsInLineOfSight =
          Util.getGridsInLineOfSight(currentGrid[0], currentGrid[1], endGridLocation[0],
              endGridLocation[1]);

      // Update grid info
      for (int i = 0; i < gridsInLineOfSight.size(); i++) {
        Integer[] gridTileCoordinates = gridsInLineOfSight.get(i);
        if (grid.isModifiableGrid(gridTileCoordinates)) {
          grid.setCellByIndex(gridTileCoordinates[0], gridTileCoordinates[1], InternalGridSquare.EMPTY);
        }
      }
    }
  }

  /**
   * Starts or stops the scanning.
   * 
   * @param scanning Set to true to start the scanning or set to false to stop the scanning.
   */
  public void setScanning(boolean scanning) {
    this.scanning = scanning;
  }
}