package ca.mcgill.ecse211.team11.test.integration;

import ca.mcgill.ecse211.team11.Initializer;

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
		init.odometer.start();
		init.display.start();
		
		init.navigation.travelTo(0, 60);
		init.navigation.travelTo(60, 60);
		init.navigation.travelTo(60, 0);
		init.navigation.travelTo(0, 0);
	}

}
