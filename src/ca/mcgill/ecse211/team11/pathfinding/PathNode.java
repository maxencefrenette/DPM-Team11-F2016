package ca.mcgill.ecse211.team11.pathfinding;

public class PathNode {
  private double x;
  private double y;
  private double theta;

  public PathNode(double x, double y, double theta) {
    this.x = x;
    this.y = y;
    this.theta = theta;
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
}
