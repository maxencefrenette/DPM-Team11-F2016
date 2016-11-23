package ca.mcgill.ecse211.team11.pathfinding;

import ca.mcgill.ecse211.team11.Constants;
import ca.mcgill.ecse211.team11.Initializer;

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

  public InternalGrid(Initializer init) {
    grid = new InternalGridSquare[2 * Constants.BOARD_SIZE][2 * Constants.BOARD_SIZE];
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
    // TODO
  
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
