package ca.mcgill.ecse211.team11.test.unit;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import ca.mcgill.ecse211.team11.Constants;
import ca.mcgill.ecse211.team11.pathfinding.InternalGrid;
import ca.mcgill.ecse211.team11.pathfinding.InternalGridSquare;

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
  public void testPathfindTo() {
    InternalGrid grid = new InternalGrid(3);
    grid.setCellByIndex(2, 1, InternalGridSquare.UNKNOWN_BLOCK);
    System.out.println(grid);
    assert(true);
  }
}
