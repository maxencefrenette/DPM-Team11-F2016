package ca.mcgill.ecse211.team11.pathfinding;

import java.util.ArrayList;

public class Path {
  ArrayList<PathNode> nodes;
  
  /**
   * Creates a new empty Path object.
   */
  public Path() {
    // Nothing needs to be done here.
  }
  
  /**
   * Appends a node at the end of a path.
   * 
   * @param node The node to append to the path.
   */
  void addNode(PathNode node) {
    nodes.add(node);
  }
  
  /**
   * Looks-up a node by index.
   * 
   * @param index The index of the node to query
   * @return The index-th node.
   */
  PathNode getNode(int index) {
    return nodes.get(index);
  }
  
  /**
   * @return The path's length
   */
  int length() {
    return nodes.size();
  }
}
