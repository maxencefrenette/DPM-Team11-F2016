package ca.mcgill.ecse211.team11.test.unit;

import ca.mcgill.ecse211.team11.Initializer;
import lejos.hardware.Button;

/**
 * Tests the claw mechanism.
 * 
 * @author Justin Szeto
 * @version 4.1
 * @since 4.0
 */
public class ClawUnitTest {

  public static void main(String[] args) {
    Initializer init = new Initializer();

    init.clawMotorController.openClaw();
    Button.waitForAnyPress();

    init.clawMotorController.lowerClaw();
    Button.waitForAnyPress();

    init.clawMotorController.closeClaw();
    Button.waitForAnyPress();

    init.clawMotorController.raiseClaw();
    Button.waitForAnyPress();

    System.exit(0);
  }
}
