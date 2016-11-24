package ca.mcgill.ecse211.team11.pathfinding;

import java.util.ArrayList;
import java.util.Iterator;

public class Path implements Iterable<PathNode> {
  ArrayList<PathNode> nodes;
  double length;

  /**
   * Creates a new empty Path object.
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
    double dx = node.getX() - lastNode.getX();
    double dy = node.getY() - lastNode.getY();
    length += Math.sqrt(dx*dx + dy*dy);
    
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
  
  @Override
  public Iterator<PathNode> iterator() {
    return nodes.iterator();
  }
}
