package ca.mcgill.ecse211.team11.pathfinding;

import ca.mcgill.ecse211.team11.Constants;
import ca.mcgill.ecse211.team11.Util;

/**
 * Represents a position on the board.
 * <p>
 * This classed is used to represent a possible position of the robot during pathfinding.
 * 
 * @author Maxence Frenette
 * @version 4.1
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

  /**
   * Calculates the f score of this search node.
   * 
   * @param goal The position to pathfind to
   * @return The f score of this search node
   */
  public double f(PathNode goal) {
    return g() + Constants.PATHFINDING_EPSILON * h(goal);
  }

  /**
   * Calculates the g score of this search node.
   * <p>
   * This is equivalent to the length of the path currently explored.
   * 
   * @return The g score of this search node
   */
  public double g() {
    return g;
  }

  /**
   * Calculates the heuristic of this search node.
   * <p>
   * In this case, the heuristic is the euclidian distance to the goal.
   * 
   * @param goal The position to pathfind to
   * @return The heuristic for this search node
   */
  public double h(PathNode goal) {
    return distTo(goal);
  }

  /**
   * Generates the neighbour states of this search node.
   * 
   * @return The neighbours of this search nodes
   */
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
    return grid.getCellByCoord(getX() + Constants.DIST_CENTER_TO_BACK,
        getY() + Constants.DIST_CENTER_TO_BACK) == InternalGridCell.EMPTY
        && grid.getCellByCoord(getX() - Constants.DIST_CENTER_TO_BACK,
            getY() + Constants.DIST_CENTER_TO_BACK) == InternalGridCell.EMPTY
        && grid.getCellByCoord(getX() - Constants.DIST_CENTER_TO_BACK,
            getY() - Constants.DIST_CENTER_TO_BACK) == InternalGridCell.EMPTY
        && grid.getCellByCoord(getX() + Constants.DIST_CENTER_TO_BACK,
            getY() - Constants.DIST_CENTER_TO_BACK) == InternalGridCell.EMPTY;
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
  /**
   * @inheritDoc
   * 
   * @param o {@inheritDoc}
   * @return {@inheritDoc}
   */
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
  /**
   * {@inheritDoc}
   * 
   * @return {@inheritDoc}
   */
  public int hashCode() {
    return Double.hashCode(getX()) + Double.hashCode(getY()) + Double.hashCode(getTheta());
  }
}
