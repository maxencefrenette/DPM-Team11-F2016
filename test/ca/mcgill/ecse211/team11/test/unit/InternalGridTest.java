package ca.mcgill.ecse211.team11.test.unit;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import ca.mcgill.ecse211.team11.pathfinding.InternalGrid;

public class InternalGridTest {

  public static void main(String[] args) {
    InternalGrid grid = new InternalGrid();
    
    System.out.println("Initialized Board");
    grid.printBoard(1);
    
    System.out.println("Update no entry zone");
    grid.updateNoEntryZone();
    grid.printBoard(1);
    
    System.out.println("Update red zone and green zone");
    grid.updateRedZone(0, 5, 2, 9);
    grid.updateGreenZone(6, 3, 8, 4);
    grid.printBoard(1);
  }

  @Test
  public void testConvertToInternalGrid() {
    assertArrayEquals("X: 30.1, Y: 30.1", new int[] {1, 1}, InternalGrid.convertToInternalGrid(30.1, 30.1));
    assertArrayEquals("X: 200, Y: 36", new int[] {13, 2}, InternalGrid.convertToInternalGrid(200, 36));
    assertArrayEquals("X: -1, Y: -3", new int[] {0, 0}, InternalGrid.convertToInternalGrid(-1, -3));
  }
}
