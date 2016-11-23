package ca.mcgill.ecse211.team11.pathfinding;

import java.util.ArrayList;

import ca.mcgill.ecse211.team11.Constants;
import ca.mcgill.ecse211.team11.Initializer;
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
 * @version 3.0
 * @since 2.0
 */
public class InternalGrid {
  private InternalGridSquare[][] grid;
  private Navigation navigation;
  private Odometer odometer;
  private USSensorController usSensorController;

  public InternalGrid(Initializer init) {
    grid = new InternalGridSquare[2 * Constants.BOARD_SIZE][2 * Constants.BOARD_SIZE];
    navigation = init.navigation;
    odometer = init.odometer;
    usSensorController = init.usSensorController;
  }

  /**
   * Travels to the specified location while avoiding obstacles
   */
  public void pathfindTo(double x, double y) {
    // TODO
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
        double objectDistanceFromWheels = Constants.DIST_CENTER_TO_US_SENSOR + distance*100;
        double objectXCoordinate = odometer.getX() + objectDistanceFromWheels*Math.cos(odometer.getTheta());
        double objectYCoordinate = odometer.getY() + objectDistanceFromWheels*Math.sin(odometer.getTheta());
        
        // Get grid coordinates
        int[] currentGrid = Util.convertToInternalGrid(odometer.getX(), odometer.getY());
        int[] objectGridLocation = Util.convertToInternalGrid(objectXCoordinate, objectYCoordinate);
        ArrayList<Integer[]> gridsInLineOfSight = Util.getGridsInLineOfSight(currentGrid[0], currentGrid[1], objectGridLocation[0], objectGridLocation[1]);
        
        //Update grid info
        if (isModifiableGrid(gridsInLineOfSight.get(gridsInLineOfSight.size()-1))) {
          grid[objectGridLocation[0]][objectGridLocation[1]] = InternalGridSquare.UNKNOWN_BLOCK;
        }
        for(int i = 0; i < gridsInLineOfSight.size()-1; i++) {
          Integer[] gridTileCoordinates = gridsInLineOfSight.get(i);
          if (isModifiableGrid(gridTileCoordinates)) {
            grid[gridTileCoordinates[0]][gridTileCoordinates[1]] = InternalGridSquare.EMPTY;
          }
        }
        
      } else {
        
        // Find end coordinates
        double endDistanceFromWheels = Constants.DIST_CENTER_TO_US_SENSOR + Constants.SCANNING_RANGE*100;
        double endXCoordinate = odometer.getX() + endDistanceFromWheels*Math.cos(odometer.getTheta());
        double endYCoordinate = odometer.getY() + endDistanceFromWheels*Math.sin(odometer.getTheta());
        
        // Get grid coordinates
        int[] currentGrid = Util.convertToInternalGrid(odometer.getX(), odometer.getY());
        int[] endGridLocation = Util.convertToInternalGrid(endXCoordinate, endYCoordinate);
        ArrayList<Integer[]> gridsInLineOfSight = Util.getGridsInLineOfSight(currentGrid[0], currentGrid[1], endGridLocation[0], endGridLocation[1]);
        
        //Update grid info
        for(int i = 0; i < gridsInLineOfSight.size(); i++) {
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
    int dx = (UGZx - LGZx)*2;
    int dy = (UGZy - LGZy)*2;
    
    int lowerLeftGridX = (LGZx+1)*2;
    int lowerLeftGridY = (LGZy+1)*2;
    
    for (int i = lowerLeftGridX; i < lowerLeftGridX + dx; i++) {
      for (int j = lowerLeftGridY; i < lowerLeftGridY + dy; j++) {
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
    int dx = (URZx - LRZx)*2;
    int dy = (URZy - LRZy)*2;
    
    int lowerLeftGridX = (LRZx+1)*2;
    int lowerLeftGridY = (LRZy+1)*2;
    
    for (int i = lowerLeftGridX; i < lowerLeftGridX + dx; i++) {
      for (int j = lowerLeftGridY; i < lowerLeftGridY + dy; j++) {
        grid[i][j] = InternalGridSquare.RED_ZONE;
      }
    }
  }
  
  /**
   * 
   * @param gridCoordinates grid's X and Y
   * @return true if allowed to edit grid's status. false if not allowed to edit grid's status.
   */
  private boolean isModifiableGrid(Integer[] gridCoordinates) {
    InternalGridSquare gridStatus = grid[gridCoordinates[0]][gridCoordinates[1]];
    
    if(gridStatus == InternalGridSquare.NO_ENTRY || gridStatus == InternalGridSquare.GREEN_ZONE
        || gridStatus == InternalGridSquare.RED_ZONE) {
      return false;
    } else {
      return true;
    }
  }
}
