package ca.mcgill.ecse211.team11.test.integration;

import ca.mcgill.ecse211.team11.ColorSensorController;
import ca.mcgill.ecse211.team11.Display;
import ca.mcgill.ecse211.team11.Initializer;
import ca.mcgill.ecse211.team11.Logger;
import ca.mcgill.ecse211.team11.USSensorController;
import lejos.hardware.Button;
import lejos.hardware.Sound;

/**
 * This class tests if the light sensor is able to identify whether an obstacle is a styrofoam block
 * or a wooden block. If(styrofoam) print("Block") else print("Not Block")
 * 
 * @author Saleh Bakhit
 * @version 4.1
 * @since 4.0
 *
 */
public class IdentificationTest {
  public static void main(String[] args) {
    Initializer init = new Initializer();

    Display dis = init.display;
    ColorSensorController Id = init.colorSensorController;
    USSensorController us = init.usSensorController;

    Sound.setVolume(50);
    dis.start();

    boolean isBlock;
    while (Button.readButtons() == 0) {
      if (us.getPreciseDistance() * 100 < 7) {
        isBlock = Id.identifyBlock();
        if (isBlock) {
          Sound.beep();
          Logger.logData("Block");
        } else {
          Sound.twoBeeps();
          Logger.logData("Not Block");
        }
      }
      while (us.getPreciseDistance() * 100 < 7) {
        try {
          Thread.sleep(50);
        } catch (Exception e) {
        } ;
      }
    }

    System.exit(0);
  }
}
