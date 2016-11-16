package ca.mcgill.ecse211.team11;

public class InternalGrid {
  private InternalGridSquare[][] grid;
  
  public InternalGrid(Initializer init) {
    grid = new InternalGridSquare[2*Constants.BOARD_SIZE][2*Constants.BOARD_SIZE];
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
  }
}
