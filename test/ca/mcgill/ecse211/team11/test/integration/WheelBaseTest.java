package ca.mcgill.ecse211.team11.test.integration;

import ca.mcgill.ecse211.team11.Constants;
import ca.mcgill.ecse211.team11.Display;
import ca.mcgill.ecse211.team11.Initializer;
import ca.mcgill.ecse211.team11.LightSensorController;
import ca.mcgill.ecse211.team11.Logger;
import ca.mcgill.ecse211.team11.Navigation;
import ca.mcgill.ecse211.team11.Odometer;
import ca.mcgill.ecse211.team11.Util;
import lejos.hardware.Button;
import lejos.hardware.Sound;

/**
 * Tests the calibration of the WHEEL_BASE constant.
 * 
 * @author Saleh Bakhit
 * @version 4.1
 * @since 1.0
 * @see ca.mcgill.ecse211.team11.Constants#WHEEL_BASE
 */
public class WheelBaseTest {
  public static final int NUM_TURNS = 3;

  public static void main(String[] args) {
    Initializer init = new Initializer();

    LightSensorController lsc = init.lightSensorController;
    Navigation nav = init.navigation;
    Odometer odo = init.odometer;
    Display dis = init.display;

    dis.start();
    odo.start();

    nav.turnClockwise(false);
    waitForLines(lsc, 1);
    double theta1 = odo.getUnwrappedTheta();
    waitForLines(lsc, 4 * NUM_TURNS);
    double theta2 = odo.getUnwrappedTheta();
    nav.setSpeeds(0, 0);
    double wheelBase = Constants.WHEEL_BASE * ((theta2 - theta1) / (2 * Math.PI * NUM_TURNS));

    Logger.logData(String.format("Wheel Base: %.3f", wheelBase));

    Button.waitForAnyPress();
    System.exit(0);
  }

  /**
   * Waits until n lines are crossed
   * 
   * @param lsc The lightSensorController object to use to detect the line.
   * @param n The amount of lines crossed to wait for.
   */
  public static void waitForLines(LightSensorController lsc, int n) {
    while (n > 0) {
      if (lsc.isLineCrossed()) {
        n--;
        Sound.beep();
      }

      Util.sleep(50);
    }
  }
}
