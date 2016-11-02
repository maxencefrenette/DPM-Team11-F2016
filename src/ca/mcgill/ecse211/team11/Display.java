package ca.mcgill.ecse211.team11;

import java.util.Observable;
import java.util.Observer;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;

/**
 * Display class handles showing any vital info on the LCD of the EV3 brick during runtime.
 * 
 * @author	Justin Szeto
 * @version	1.0
 * @since	1.0
 *
 */

public class Display extends Thread implements Observer {
	
	private double[] position = new double[3];
	
	public void run() {
		TextLCD t = LocalEV3.get().getTextLCD();
		
		while (true) {
			long displayStart = System.currentTimeMillis();
			
			// clear the lines for displaying odometry information
			t.drawString("X:              ", 0, 0);
			t.drawString("Y:              ", 0, 1);
			t.drawString("T:              ", 0, 2);

			// display odometry information
			for (int i = 0; i < 3; i++) {
				t.drawString(String.format("%.2f", position[i]), 3, i);
			}

			// adjust timing of wait period depending on how long it took to update screen
			long displayEnd = System.currentTimeMillis();
			if (displayEnd - displayStart < Constants.DISPLAY_WAIT_PERIOD) {
				try {
					Thread.sleep(Constants.DISPLAY_WAIT_PERIOD - (displayEnd - displayStart));
				} catch (InterruptedException e) {
					/* In case something interrupts the thread but
					 * there should not be anything for now.
					 */
				}
			}
		}
	}

	/**
	 * Update data to display on screen every time data in observed object changes.
	 */
	@Override
	public void update(Observable observed, Object o) {
		// TODO Add in more classes of observable objects and what data to get from them
		if (observed instanceof Odometer) {
			position = ((Odometer) observed).getPosition();
		}
	}
}
