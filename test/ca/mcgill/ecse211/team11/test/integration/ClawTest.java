package ca.mcgill.ecse211.team11.test.integration;

import ca.mcgill.ecse211.team11.ClawMotorController;
import ca.mcgill.ecse211.team11.Initializer;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;

/**
 * This class tests the motors for the claw and its lifting mechanism.
 * 
 * @author Saleh Bakhit
 * @version 4.1
 * @since 3.0
 *
 */
public class ClawTest {
  public static void main(String[] args) {
    Initializer init = new Initializer();

    ClawMotorController claw =
        new ClawMotorController(init.clawClosingMotor, init.clawRaisingMotor);

    TextLCD screen = LocalEV3.get().getTextLCD();
    screen.clear();
    screen.drawString("Program Started", 0, 0);

    int ButtonPressed = 0;
    while (ButtonPressed != Button.ID_ESCAPE) {
      ButtonPressed = Button.waitForAnyPress();

      if (ButtonPressed == Button.ID_DOWN) {
        claw.lowerClaw();
      } else if (ButtonPressed == Button.ID_UP) {
        claw.raiseClaw();
      } else if (ButtonPressed == Button.ID_RIGHT) {
        claw.openClaw();
      } else if (ButtonPressed == Button.ID_LEFT) {
        claw.closeClaw();
      }
    }

    System.exit(0);
  }
}
