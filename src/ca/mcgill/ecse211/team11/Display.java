package ca.mcgill.ecse211.team11;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;

/**
 * Display class handles showing any vital info on the LCD of the EV3 brick during runtime.
 * 
 * @author Justin Szeto
 * @version 4.1
 * @since 1.0
 */
public class Display extends Thread {
  private Odometer odometer;
  private USSensorController usSensorController;

  /**
   * Creates and initializes the display.
   * 
   * @param init The Initializer in charge of creating the connection with the components.
   */
  public Display(Initializer init) {
    odometer = init.odometer;
    usSensorController = init.usSensorController;
  }

  /**
   * Starts the automatic update of the display.
   */
  public void run() {
    TextLCD t = LocalEV3.get().getTextLCD();

    while (true) {
      long displayStart = System.currentTimeMillis();

      // clear the lines for displaying odometry information
      t.drawString("X:                ", 0, 0);
      t.drawString("Y:                ", 0, 1);
      t.drawString("T:                ", 0, 2);

      // clear line for displaying ultrasonic distance reading
      t.drawString("Distance:         ", 0, 4);

      // Clears the line for displaying the last logged message
      t.drawString("                  ", 0, 6);
      t.drawString("                  ", 0, 7);

      // display odometry information
      t.drawString(String.format("%.2f", odometer.getX()), 3, 0);
      t.drawString(String.format("%.2f", odometer.getY()), 3, 1);
      t.drawString(String.format("%.2f", Math.toDegrees(odometer.getTheta())), 3, 2);

      // display ultrasonic distance reading
      t.drawString(String.format("%.3f", usSensorController.getLastPreciseDistance()), 10, 4);

      // Displays the last logged message
      try {
        String lastMessage = Logger.getLastMessage();
        int _18 = Math.min(18, lastMessage.length());
        int _36 = Math.min(36, lastMessage.length());
        t.drawString(lastMessage.substring(0, _18), 0, 6);
        t.drawString(lastMessage.substring(_18, _36), 0, 7);
      } catch (Exception e) {
        // For some reason, the above code seems to randomly fail sometimes. It should never happen
        // since the above code is correct, but if it ever does so again, it is better to fail
        // silently than to crash.
      }

      // adjust timing of wait period depending on how long it took to update screen
      long displayEnd = System.currentTimeMillis();
      if (displayEnd - displayStart < Constants.DISPLAY_WAIT_PERIOD) {
        Util.sleep(Constants.DISPLAY_WAIT_PERIOD - (displayEnd - displayStart));
      }
    }
  }
}
