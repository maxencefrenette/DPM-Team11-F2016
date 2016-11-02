package ca.mcgill.ecse211.team11;

/**
 * 
 * The Constants class contains all the constants to be 
 * used in other classes. This makes adjusting constants
 * during the testing phase easier since all constants
 * are in one location.
 * 
 * @author	Justin Szeto
 * @version	1.0
 * @since	1.0
 * 
 */

public class Constants {
	
	// Sensor port labels
	public static final String US_SENSOR_PORT = "S1";
	public static final String LIGHT_SENSOR_LINE_DETECTION_PORT = "S2";
	public static final String LIGHT_SENSOR_OBJECT_IDENTIFIER_PORT = "S3";
	
	// Motor port labels
	public static final String LEFT_WHEEL_MOTOR_PORT = "A";
	public static final String RIGHT_WHEEL_MOTOR_PORT = "B";
	public static final String CLAW_CLOSING_MOTOR_PORT = "C";
	public static final String CLAW_RAISING_MOTOR_PORT = "D";

	// Navigation speeds and accelerations
	public static final int FORWARD_HIGH_SPEED = 200;
	public static final int FORWARD_LOW_SPEED = 100;
	public static final int TURNING_SPEED = 75;
	public static final int WHEEL_ACCELERATION = 2000;
	
	// Thread/Timer periods
	public static final int DISPLAY_WAIT_PERIOD = 100;
	public static final int ODOMETER_WAIT_PERIOD = 50;
	public static final int US_POLLER_WAIT_PERIOD = 50;
	public static final int LIGHT_POLLER_WAIT_PERIOD = 50;
	
	// Physical dimensions of robot
	public static final double WHEEL_RADIUS = 2.15;
	public static final double WHEEL_BASE = 13.5;
	public static final double DIST_CENTER_TO_LINE_DETECTION_LIGHT_SENSOR = 6.0;
	public static final double DIST_CENTER_TO_OBJECT_IDENTIFIER_LIGHT_SENSOR = 9.0;
	public static final double DIST_CENTER_TO_US_SENSOR = 8.0;
	
}
