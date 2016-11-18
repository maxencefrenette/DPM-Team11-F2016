package ca.mcgill.ecse211.team11.test.integration;

import ca.mcgill.ecse211.team11.Constants;
import ca.mcgill.ecse211.team11.Display;
import ca.mcgill.ecse211.team11.Initializer;
import ca.mcgill.ecse211.team11.Navigation;
import ca.mcgill.ecse211.team11.Odometer;
import lejos.hardware.Button;

/**
 * Tests the navigation class.
 * 
 * @author Saleh Bakhit
 * @version 2.0
 * @since 1.0
 */
public class NavigationTest {
  public static void main(String[] args) {
    Initializer init = new Initializer();

    Odometer odometer = init.odometer;
    Display display = init.display;
    Navigation nav = init.navigation;

    // navigation.setAccelerations(150, 150);

    display.start();
    odometer.start();

    nav.travelTo(2 * Constants.GRID_SIZE, Constants.GRID_SIZE);
    nav.travelTo(Constants.GRID_SIZE, Constants.GRID_SIZE);
    nav.travelTo(Constants.GRID_SIZE, 2 * Constants.GRID_SIZE);
    nav.travelTo(2 * Constants.GRID_SIZE, 0);

    Button.waitForAnyPress();
    System.exit(0);
  }
}
