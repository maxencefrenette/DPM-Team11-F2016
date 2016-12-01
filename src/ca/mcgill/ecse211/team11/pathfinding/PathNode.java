package ca.mcgill.ecse211.team11.pathfinding;

/**
 * Represents a position on the board.
 * <p>
 * This classed is used to represent a waypoint in a path.
 * 
 * @author Maxence Frenette
 * @version 4.1
 * @since 4.0
 */
public class PathNode {
  private double x;
  private double y;
  private double theta;

  /**
   * Creates a PathNode objects and initializes it.
   * 
   * @param x The x position on the board
   * @param y The y position on the board
   * @param theta The heading associated with this position
   */
  public PathNode(double x, double y, double theta) {
    this.x = x;
    this.y = y;
    this.theta = theta;
  }

  public double distTo(PathNode other) {
    double dx = x - other.getX();
    double dy = y - other.getY();
    return Math.sqrt(dx * dx + dy * dy);
  }

  /**
   * @return the x
   */
  public synchronized double getX() {
    return x;
  }

  /**
   * @param x the x to set
   */
  public synchronized void setX(double x) {
    this.x = x;
  }

  /**
   * @return the y
   */
  public synchronized double getY() {
    return y;
  }

  /**
   * @param y the y to set
   */
  public synchronized void setY(double y) {
    this.y = y;
  }

  /**
   * @return the theta
   */
  public synchronized double getTheta() {
    return theta;
  }

  /**
   * @param theta the theta to set
   */
  public synchronized void setTheta(double theta) {
    this.theta = theta;
  }

  @Override
  public String toString() {
    return String.format("{%.2f, %.2f, %.2f}", x, y, Math.toDegrees(theta));
  }
}
