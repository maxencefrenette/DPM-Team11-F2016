package ca.mcgill.ecse211.team11.test.unit;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.mcgill.ecse211.team11.Constants;
import ca.mcgill.ecse211.team11.pathfinding.InternalGrid;
import ca.mcgill.ecse211.team11.pathfinding.InternalGridCell;
import ca.mcgill.ecse211.team11.pathfinding.Path;
import ca.mcgill.ecse211.team11.pathfinding.PathNode;

/**
 * Tests the InternalGrid class.
 * 
 * @author Maxence Frenette
 * @version 4.1
 * @since 4.0
 */
public class InternalGridTest {

  public static void main(String[] args) {
    InternalGrid grid = new InternalGrid(Constants.BOARD_SIZE);

    System.out.println("Initialized Board");
    grid.printBoard(1);

    System.out.println("Update no entry zone");
    grid.updateNoEntryZone(1);
    grid.printBoard(1);

    System.out.println("Update red zone and green zone");
    grid.updateRedZone(0, 5, 2, 9);
    grid.updateGreenZone(6, 3, 8, 4);
    grid.printBoard(1);
  }

  @Test
  public void testConvertToInternalGrid() {
    InternalGrid grid = new InternalGrid(3);
    assertArrayEquals("X: 30.1, Y: 30.1", new int[] {1, 1}, grid.convertToInternalGrid(30.1, 30.1));
    assertArrayEquals("X: 200, Y: 36", new int[] {13, 2}, grid.convertToInternalGrid(200, 36));
    assertArrayEquals("X: -1, Y: -3", new int[] {0, 0}, grid.convertToInternalGrid(-1, -3));
  }

  @Test
  public void testPathfindTo() throws Exception {
    // Grid initialization
    InternalGrid grid = new InternalGrid(3);
    grid.setZoneByIndex(0, 0, 6, 6, InternalGridCell.EMPTY);
    grid.setCellByIndex(1, 2, InternalGridCell.UNKNOWN_BLOCK);
    grid.setCellByIndex(2, 5, InternalGridCell.UNKNOWN_BLOCK);
    System.out.println(grid);

    // Regular pathfinding
    PathNode start1 = new PathNode(0.5 * Constants.GRID_SIZE, 0.5 * Constants.GRID_SIZE, 0);
    PathNode end1 = new PathNode(0.75 * Constants.GRID_SIZE, 2.75 * Constants.GRID_SIZE, 0);
    Path p1 = grid.pathfindTo(start1, end1);
    System.out.println(p1);
    System.out.println();

    // Pathfinding to closest grid intersection
    PathNode start2 = new PathNode(0.5 * Constants.GRID_SIZE, 2.5 * Constants.GRID_SIZE, 0);
    Path p2 = grid.pathfindTo(start2, InternalGrid.CLOSEST_INTERSECTION);
    System.out.println(p2);

    assertTrue("Pathfinding completed without errors", true);
  }
}
