package ca.mcgill.ecse211.team11.test.integration;

import ca.mcgill.ecse211.team11.Constants;
import ca.mcgill.ecse211.team11.Initializer;
import ca.mcgill.ecse211.team11.Logger;
import ca.mcgill.ecse211.team11.Scanner;
import ca.mcgill.ecse211.team11.pathfinding.InternalGrid;

/**
 * Tests the scanning.
 * 
 * @author Justin Szeto
 * @since 4.0
 * @version 4.1
 */
public class ScanTest {

  public static void main(String[] args) {
    Initializer init = new Initializer();
    init.odometer.start();
    init.display.start();

    init.odometer.setX(Constants.GRID_SIZE);
    init.odometer.setY(Constants.GRID_SIZE);

    double scanRange = 0.75;
    InternalGrid grid = new InternalGrid(Constants.BOARD_SIZE);
    grid.updateNoEntryZone(1);
    Scanner scanner = new Scanner(init, grid, scanRange);
    scanner.start();
    scanner.setScanning(true);
    init.navigation.turn(Math.toRadians(357), true);
    scanner.setScanning(false);
    Logger.logData("Scan Range: " + scanRange + "\n");
    grid.printBoard(0);
  }
}
