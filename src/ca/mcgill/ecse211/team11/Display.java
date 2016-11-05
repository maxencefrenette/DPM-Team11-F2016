package ca.mcgill.ecse211.team11;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;

/**
 * Display class handles showing any vital info on the LCD of the EV3 brick during runtime.
 * 
 * @author Justin Szeto
 * @version 1.0
 * @since 1.0
 */
public class Display extends Thread {
  private Odometer odometer;
  
  /**
   * Creates and initializes the display.
   * 
   * @param init The Initializer in charge of creating the connection with the components.
   */
  public Display(Initializer init) {
    odometer = init.odometer;
  }
  
  /**
   * Starts the automatic update of the display.
   */
  public void run() {
    TextLCD t = LocalEV3.get().getTextLCD();

    while (true) {
      long displayStart = System.currentTimeMillis();

      // clear the lines for displaying odometry information
      t.drawString("X:              ", 0, 0);
      t.drawString("Y:              ", 0, 1);
      t.drawString("T:              ", 0, 2);

      // display odometry information
      t.drawString(String.format("%.2f", odometer.getX()), 3, 0);
      t.drawString(String.format("%.2f", odometer.getY()), 3, 1);
      t.drawString(String.format("%.2f", odometer.getTheta()), 3, 2);

      // adjust timing of wait period depending on how long it took to update screen
      long displayEnd = System.currentTimeMillis();
      if (displayEnd - displayStart < Constants.DISPLAY_WAIT_PERIOD) {
        Util.sleep(Constants.DISPLAY_WAIT_PERIOD - (displayEnd - displayStart));
      }
    }
  }
}
