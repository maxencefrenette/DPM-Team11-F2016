package ca.mcgill.ecse211.team11.test.unit;

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

}
