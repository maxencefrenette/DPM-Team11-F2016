package ca.mcgill.ecse211.team11.test.integration;

import ca.mcgill.ecse211.team11.ClawMotorController;
import ca.mcgill.ecse211.team11.Initializer;
import lejos.hardware.Button;

/**
 * This class tests the motors for the claw and its lifting mechanism.
 * 
 * @author Saleh Bakhit
 * @version 3.0
 * @since 3.0
 *
 */
public class ClawTest {
  public static void main(String[] args) {
    Initializer init = new Initializer();
    
    ClawMotorController claw = new ClawMotorController(init.clawClosingMotor, init.clawRaisingMotor);
    
    while(Button.waitForAnyPress() != Button.ID_ESCAPE) {
      if(Button.waitForAnyPress() == Button.ID_DOWN) {
        claw.lowerClaw();
      } else if(Button.waitForAnyPress() == Button.ID_UP) {
        claw.raiseClaw();
      } else if(Button.waitForAnyPress() == Button.ID_RIGHT) {
        claw.openClaw();
      } else if(Button.waitForAnyPress() == Button.ID_LEFT) {
        claw.closeClaw();
      }
    }
    
    System.exit(0);
  }
}
