package ca.mcgill.ecse211.team11.pathfinding;

import java.util.ArrayList;
import java.util.Iterator;

public class Path implements Iterable<PathNode> {
  /** The array of nodes that compose the path */
  ArrayList<PathNode> nodes;
  /** The path's length (cm) */
  double length;

  /**
   * Creates a new empty Path object.
   * 
   * @param startNode The first node of the path
   */
  public Path(PathNode startNode) {
    nodes = new ArrayList<PathNode>();
    nodes.add(startNode);
  }

  /**
   * Appends a node at the end of a path.
   * 
   * @param node The node to append to the path.
   */
  public void addNode(PathNode node) {
    PathNode lastNode = nodes.get(nodes.size() - 1);
    length += lastNode.distTo(node);

    nodes.add(node);
  }

  /**
   * Calculates the path's length in cm.
   * 
   * @return The path's length
   */
  public double length() {
    return length;
  }

  /**
   * @return The last node of the path
   */
  public PathNode lastNode() {
    return nodes.get(nodes.size() - 1);
  }

  @Override
  /**
   * Iterates over the path's nodes.
   * 
   * @return An iterator over the path's nodes
   */
  public Iterator<PathNode> iterator() {
    return nodes.iterator();
  }

  @Override
  /**
   * Converts this object as a string for debugging purposes.
   * 
   * @return The string representation of this object
   */
  public String toString() {
    String result = "";
    for (PathNode node : this) {
      result += node + "\n";
    }
    return result;
  }
}
