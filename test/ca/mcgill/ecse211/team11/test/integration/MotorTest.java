package ca.mcgill.ecse211.team11.test.integration;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.Button;

/**
 * This class tests all motors separately. It determines if they go back to the zero angle after
 * k*full revolutions
 * 
 * @author Saleh Bakhit
 * @version 4.0
 * @since 1.0
 */
public class MotorTest {

  public static final int SPEED = 200;

  private static final EV3LargeRegulatedMotor leftMotor =
      new EV3LargeRegulatedMotor(LocalEV3.get().getPort("A"));
  private static final EV3LargeRegulatedMotor rightMotor =
      new EV3LargeRegulatedMotor(LocalEV3.get().getPort("D"));

  public static void main(String[] args) {
    leftMotor.setSpeed(SPEED);
    rightMotor.setSpeed(SPEED);

    leftMotor.rotate(360 * 3, true);
    rightMotor.rotate(360 * 3, false);
    Button.waitForAnyPress();

    leftMotor.rotate(360 * 5, true);
    rightMotor.rotate(360 * 5, false);

    Button.waitForAnyPress();
    System.exit(0);
  }
}
