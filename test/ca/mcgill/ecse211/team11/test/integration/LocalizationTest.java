package ca.mcgill.ecse211.team11.test.integration;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import ca.mcgill.ecse211.team11.Constants;
import ca.mcgill.ecse211.team11.Initializer;

/**
 * Tests the Localization class
 * 
 * @author Maxence Frenette
 * @version 2.0
 * @since 1.0
 */
public class LocalizationTest {

  public static void main(String[] args) {
    // Initialization
    Initializer init = new Initializer();
    init.display.start();
    init.odometer.start();
    init.localizer.setBeeping(true);

    // Main test
    init.localizer.setCornerNumber(1);
    init.localizer.usLocalize();
    init.navigation.travelTo(Constants.GRID_SIZE, Constants.GRID_SIZE);
    init.navigation.turnToWithMinAngle(0, true);
    init.localizer.lightLocalize();
    Sound.beep();

    Button.waitForAnyPress();
    System.exit(0);
  }
}
