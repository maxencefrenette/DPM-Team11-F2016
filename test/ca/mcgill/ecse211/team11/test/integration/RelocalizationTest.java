package ca.mcgill.ecse211.team11.test.integration;

import ca.mcgill.ecse211.team11.Constants;
import ca.mcgill.ecse211.team11.Initializer;
import lejos.hardware.Button;
import lejos.hardware.Sound;

public class RelocalizationTest {

  public static void main(String[] args) {
    // Initialization
    Initializer init = new Initializer();
    init.display.start();
    init.odometer.start();
    init.localizer.setBeeping(false);

    // Main test
    init.odometer.setX(Constants.GRID_SIZE);
    init.odometer.setY(Constants.GRID_SIZE);
    init.localizer.lightLocalize();
    init.navigation.travelTo(Constants.GRID_SIZE, Constants.GRID_SIZE);
    init.navigation.turnToWithMinAngle(0, true);
    Sound.beep();

    Button.waitForAnyPress();
    System.exit(0);
  }

}
