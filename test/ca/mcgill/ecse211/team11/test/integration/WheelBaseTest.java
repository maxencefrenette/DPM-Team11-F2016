package ca.mcgill.ecse211.team11.test.integration;

import ca.mcgill.ecse211.team11.Display;
import ca.mcgill.ecse211.team11.Initializer;
import ca.mcgill.ecse211.team11.Navigation;
import ca.mcgill.ecse211.team11.Odometer;
import lejos.hardware.Button;

/**
 * Tests the calibration of the WHEEL_BASE constant.
 * 
 * @author Saleh Bakhit
 * @version 2.0
 * @since 1.0
 * @see ca.mcgill.ecse211.team11.Constants#WHEEL_BASE
 */
public class WheelBaseTest {
  public static void main(String[] args) {
    Initializer init = new Initializer();

    Navigation nav = init.navigation;
    Odometer odo = init.odometer;
    Display dis = init.display;

    dis.start();
    odo.start();

    boolean conTest = true;
    while (conTest) {
      nav.turn(Math.PI, true);

      // Button.waitForAnyPress();
      if (Button.waitForAnyPress() != Button.ID_ESCAPE) {
        conTest = false;
      }
    }
  }
}
