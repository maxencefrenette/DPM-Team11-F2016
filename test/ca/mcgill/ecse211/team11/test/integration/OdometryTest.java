package ca.mcgill.ecse211.team11.test.integration;

import ca.mcgill.ecse211.team11.Display;
import ca.mcgill.ecse211.team11.Initializer;
import ca.mcgill.ecse211.team11.Odometer;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

/**
 * Tests the odometer by moving in a square.
 * 
 * @author Saleh Bakhit
 * @version 1.0
 * @since 1.0
 */
public class OdometryTest {

	public static void main(String[] args) {
		Initializer init = new Initializer();
		
		EV3LargeRegulatedMotor leftMotor = init.leftMotor;
		EV3LargeRegulatedMotor rightMotor = init.rightMotor;
		
		/*Odometer odometer = init.odometer;
		 * Display odoDis = init.display;
		 * odometer.start();
		 * odoDis.start();
		 */
		
		init.odometer.start();
		init.display.start();
		
		//leftMotor.setSpeed(100);
		//rightMotor.setSpeed(100);
		//leftMotor.rotate(180);
		//rightMotor.forward();
		
		while(Button.waitForAnyPress() == Button.ID_ESCAPE) {
			leftMotor.forward();
			leftMotor.flt();
			rightMotor.forward();
			rightMotor.flt();
		}
		init.navigation.travelTo(0, 60);
		init.navigation.travelTo(60, 60);
		init.navigation.travelTo(60, 0);
		init.navigation.travelTo(0, 0);
		
		Button.waitForAnyPress();
		System.exit(0);
	}

}
