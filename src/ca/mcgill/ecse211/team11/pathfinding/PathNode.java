package ca.mcgill.ecse211.team11.pathfinding;

public class PathNode {
  // The following properties have no accessibility modifier because they are intended to be
  // package-private: http://docs.oracle.com/javase/tutorial/java/javaOO/accesscontrol.html
  double x;
  double y;
  double theta;
  
  public PathNode(double x, double y, double theta) {
    this.x = x;
    this.y = y;
    this.theta = theta;
  }
}
