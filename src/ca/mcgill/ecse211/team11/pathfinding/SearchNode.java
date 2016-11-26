package ca.mcgill.ecse211.team11.pathfinding;

import ca.mcgill.ecse211.team11.Constants;
import ca.mcgill.ecse211.team11.Util;

/**
 * Represents a position on the board.
 * <p>
 * This classed is used to represent a possible position of the robot during pathfinding.
 * 
 * @author Maxence Frenette
 * @version 4.0
 * @since 4.0
 */
public class SearchNode extends PathNode {
  /**
   * The searchNode that lead the search algorithm to discover this node.
   */
  SearchNode previousNode;
  private double g;

  /**
   * Creates a SearchNode objects and initializes it.
   * 
   * @param x The x position on the board
   * @param y The y position on the board
   * @param theta The heading associated with this position
   */
  public SearchNode(double x, double y, double theta, SearchNode previousNode) {
    super(x, y, theta);
    this.previousNode = previousNode;
    if (previousNode != null) {
      this.g = previousNode.g() + previousNode.distTo(this);
    } else {
      this.g = 0;
    }
  }

  /**
   * Creates a SearchNode from a PathNode.
   * 
   * @param node The PathNode to copy
   */
  public SearchNode(PathNode node) {
    super(node.getX(), node.getY(), node.getTheta());
  }

  public double f(PathNode goal) {
    return g() + Constants.PATHFINDING_EPSILON*h(goal);
  }

  public double g() {
    return g;
  }

  public double h(PathNode goal) {
    return distTo(goal);
  }

  public SearchNode[] getNeighbours() {
    double clampedX = getX() - Util.specialMod(getX(), Constants.GRID_SIZE / 2);
    double clampedY = getY() - Util.specialMod(getY(), Constants.GRID_SIZE / 2);

    return new SearchNode[] {
        new SearchNode(clampedX + Constants.GRID_SIZE / 2, clampedY, Math.toRadians(0), this),
        new SearchNode(clampedX, clampedY + Constants.GRID_SIZE / 2, Math.toRadians(90), this),
        new SearchNode(clampedX - Constants.GRID_SIZE / 2, clampedY, Math.toRadians(180), this),
        new SearchNode(clampedX, clampedY - Constants.GRID_SIZE / 2, Math.toRadians(270), this)};
  }


  /**
   * Test whether this is a valid position for the robot
   * 
   * @param internalGrid
   * @return True if the position is valid, false otherwise
   */
  public boolean isValid(InternalGrid grid) {
    return grid.getCellByCoord(getX() + Constants.DIST_CENTER_TO_BACK, getY()
        + Constants.DIST_CENTER_TO_BACK) == InternalGridCell.EMPTY
        && grid.getCellByCoord(getX() - Constants.DIST_CENTER_TO_BACK, getY()
            + Constants.DIST_CENTER_TO_BACK) == InternalGridCell.EMPTY
        && grid.getCellByCoord(getX() - Constants.DIST_CENTER_TO_BACK, getY()
            - Constants.DIST_CENTER_TO_BACK) == InternalGridCell.EMPTY
        && grid.getCellByCoord(getX() + Constants.DIST_CENTER_TO_BACK, getY()
            - Constants.DIST_CENTER_TO_BACK) == InternalGridCell.EMPTY;
  }

  /**
   * Converts this SearchNode to an equivalent PathNode.
   * 
   * @return The resulting PathNode
   */
  public PathNode toPathNode() {
    return new PathNode(getX(), getY(), getTheta());
  }

  /**
   * Computes the full path that led to this position.
   * <p>
   * If 3 or more waypoints are aligned, the middle waypoints will be removed.
   * 
   * @return The path that led to this position
   */
  public Path getPath() {
    if (previousNode == null) {
      return new Path(this.toPathNode());
    } else {
      Path p = previousNode.getPath();
      p.addNode(this.toPathNode());
      return p;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof SearchNode)) {
      return false;
    }

    SearchNode n = (SearchNode) o;

    return getX() == n.getX() && getY() == n.getY() && getTheta() == n.getTheta();
  }

  @Override
  public int hashCode() {
    return Double.hashCode(getX()) + Double.hashCode(getY()) + Double.hashCode(getTheta());
  }
}
