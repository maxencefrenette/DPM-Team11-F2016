package ca.mcgill.ecse211.team11.test.unit;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.mcgill.ecse211.team11.Constants;
import ca.mcgill.ecse211.team11.pathfinding.InternalGrid;
import ca.mcgill.ecse211.team11.pathfinding.InternalGridCell;
import ca.mcgill.ecse211.team11.pathfinding.SearchNode;

/**
 * Tests the SearchNode class.
 * 
 * @author Maxence Frenette
 * @version 4.1
 * @since 4.0
 */
public class SearchNodeTest {

  @Test
  public void isValidTest() {
    InternalGrid grid = new InternalGrid(3);
    grid.setZoneByIndex(0, 0, 6, 6, InternalGridCell.EMPTY);
    grid.setCellByIndex(0, 0, InternalGridCell.UNKNOWN_BLOCK);
    System.out.println(grid);

    SearchNode n1 = new SearchNode(0.5 * Constants.GRID_SIZE, 0.5 * Constants.GRID_SIZE, 0, null);
    assertTrue(!n1.isValid(grid));

    SearchNode n2 = new SearchNode(1 * Constants.GRID_SIZE, 1 * Constants.GRID_SIZE, 0, null);
    assertTrue(n2.isValid(grid));
  }

}
