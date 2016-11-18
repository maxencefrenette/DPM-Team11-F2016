package ca.mcgill.ecse211.team11.test.integration;

import ca.mcgill.ecse211.team11.Display;
import ca.mcgill.ecse211.team11.Initializer;
import ca.mcgill.ecse211.team11.Odometer;
import ca.mcgill.ecse211.team11.OdometryCorrection;
import ca.mcgill.ecse211.team11.Constants;
import ca.mcgill.ecse211.team11.LightSensorController;
import ca.mcgill.ecse211.team11.Navigation;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.utility.Delay;

/**
 * Tests the odometer by moving in a square.
 * 
 * @author Saleh Bakhit
 * @version 2.0
 * @since 1.0
 */
public class OdometryTest {

	public static void main(String[] args) {
		Initializer init = new Initializer();
		
		final EV3LargeRegulatedMotor leftMotor = init.leftMotor;
		final EV3LargeRegulatedMotor rightMotor = init.rightMotor;
		
		Odometer odometer = init.odometer;
		Display display = init.display;
		//OdometryCorrection odoCorr = new OdometryCorrection(init);
		
		display.start();
		odometer.start();
		//odoCorr.start();
		
		// Spawn a new Thread to avoid SquareDriver.drive() from blocking
		(new Thread() {
			public void run() {
				for (int i = 0; i < 4; i++) {
					// drive forward two tiles
					leftMotor.setSpeed(Constants.FORWARD_SPEED);
					rightMotor.setSpeed(Constants.FORWARD_SPEED);

					rightMotor.rotate(convertDistance(Constants.RIGHT_WHEEL_RADIUS, 60.96), true);
					leftMotor.rotate(convertDistance(Constants.LEFT_WHEEL_RADIUS, 60.96), false);

					// turn 90 degrees clockwise
					leftMotor.setSpeed(Constants.TURNING_SPEED);
					rightMotor.setSpeed(Constants.TURNING_SPEED);

					leftMotor.rotate(convertAngle(Constants.LEFT_WHEEL_RADIUS, Constants.WHEEL_BASE, 90.0), true);
					rightMotor.rotate(-convertAngle(Constants.RIGHT_WHEEL_RADIUS, Constants.WHEEL_BASE, 90.0), false);
				}
			}
		}).start();
		
		Button.waitForAnyPress();
		System.exit(0);
	}
	private static int convertDistance(double radius, double distance) {
		return (int) ((180.0 * distance) / (Math.PI * radius));
	}

	private static int convertAngle(double radius, double width, double angle) {
		return convertDistance(radius, Math.PI * width * angle / 360.0);
	}

}
