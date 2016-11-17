package ca.mcgill.ecse211.team11;

/**
 * The Constants class contains all the constants to be used in other classes. This makes adjusting
 * constants during the testing phase easier since all constants are in one location.
 * 
 * @author Justin Szeto
 * @version 1.0
 * @since 1.0
 */

public class Constants {

  // Sensor port labels
  public static final String US_SENSOR_PORT = "S3";
  public static final String LIGHT_SENSOR_LINE_DETECTION_PORT = "S2";
  public static final String LIGHT_SENSOR_OBJECT_IDENTIFIER_PORT = "S1";

  // Motor port labels
  public static final String LEFT_WHEEL_MOTOR_PORT = "A";
  public static final String RIGHT_WHEEL_MOTOR_PORT = "B";
  public static final String CLAW_CLOSING_MOTOR_PORT = "D";
  public static final String CLAW_RAISING_MOTOR_PORT = "C";

  // Navigation constants
  public static final int FORWARD_SPEED = 200;
  public static final int TURNING_SPEED = 75;
  public static final int WHEEL_ACCELERATION = 2000;
  public static final double ANGLE_ERROR = Math.toRadians(3.0);
  public static final double DIST_ERROR = 1.0;
  
  // Claw Controller Constants
  public static final int CLAW_RAISING_SPEED = 100;
  public static final int CLAW_RAISING_DEGREE_ROTATION = 666;
  public static final int CLAW_LOWERING_SPEED = 100;
  public static final int CLAW_LOWERING_DEGREE_ROTATION = -666;
  public static final int CLAW_CLOSING_SPEED = 50;
  public static final int CLAW_CLOSING_DEGREE_ROTATION = 666;
  public static final int CLAW_OPENING_SPEED = 30;
  public static final int CLAW_OPENING_DEGREE_ROTATION = -666;
  
  // US Localization constants
  public static final double RISING_EDGE_RANGE = 0.30;
  public static final int US_LOCALIZE_WAIT_TIME = 500;
  public static final double HEADING_OFFSET = 0;

  // Thread/Timer periods
  public static final int DISPLAY_WAIT_PERIOD = 500;
  public static final int ODOMETER_WAIT_PERIOD = 25;
  public static final int US_POLLER_WAIT_PERIOD = 50;
  public static final int LIGHT_POLLER_WAIT_PERIOD = 10;

  // Sensor filter parameters
  public static final int LIGHT_POLLER_MEDIAN_FILTER_SIZE = 3;
  public static final int US_POLLER_MEDIAN_FILTER_SIZE = 3;

  // Physical dimensions of robot
  public static final double LEFT_WHEEL_RADIUS = 2.15;
  public static final double RIGHT_WHEEL_RADIUS = 2.15;
  public static final double WHEEL_BASE = 12.9;
  public static final double DIST_CENTER_TO_LINE_DETECTION_LIGHT_SENSOR = 7.4;
  public static final double DIST_CENTER_TO_OBJECT_IDENTIFIER_LIGHT_SENSOR = 5.9;
  public static final double DIST_CENTER_TO_US_SENSOR = 7.1;

  // Wifi constants
  public static final String SERVER_IP = "192.168.2.8";
  public static final int TEAM_NUMBER = 11;

  // Environment constants
  public static final double GRID_SIZE = 30.48;
  public static final int BOARD_SIZE = 8;
  
  // Other constants
  public static final String LOG_FILENAME = "Log.txt";
  public static final int HARDWARE_INITIALIZATION_MAXIMUM_TRIALS = 5;
  public static final int HARDWARE_INITIALIZATION_RETRY_DELAY = 500;
  public static final int ODOMETRY_CORRECTION_MAX_ERROR_MARGIN = 2;
  public static final int SOUND_VOLUME = 60;
}
