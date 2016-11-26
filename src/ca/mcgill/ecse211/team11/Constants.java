package ca.mcgill.ecse211.team11;

/**
 * The Constants class contains all the constants to be used in other classes. This makes adjusting
 * constants during the testing phase easier since all constants are in one location.
 * 
 * @author Justin Szeto
 * @version 4.0
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
  public static final double ANGLE_ERROR = Math.toRadians(2);
  public static final double DIST_ERROR = 1.0;
  public static final double SPEED_ADJUSTMENT = 25;

  // Claw Controller Constants
  public static final int CLAW_RAISING_SPEED = 100;
  public static final int CLAW_RAISING_DEGREE_ROTATION = -6 * 180;
  public static final int CLAW_LOWERING_SPEED = 100;
  public static final int CLAW_LOWERING_DEGREE_ROTATION = 6 * 180;
  public static final int CLAW_CLOSING_SPEED = 30;
  public static final int CLAW_CLOSING_DEGREE_ROTATION = -50;
  public static final int CLAW_OPENING_SPEED = 30;
  public static final int CLAW_OPENING_DEGREE_ROTATION = 50;

  // US Localization constants
  public static final double RISING_EDGE_RANGE = 0.40;
  public static final int US_LOCALIZE_WAIT_TIME = 1000;
  public static final double HEADING_OFFSET = 0;

  // Thread/Timer periods
  public static final int DISPLAY_WAIT_PERIOD = 500;
  public static final int ODOMETER_WAIT_PERIOD = 25;
  public static final int LIGHT_POLLER_WAIT_PERIOD = 10;

  // Sensor filter parameters
  public static final int COLOR_SENSOR_MEDIAN_FILTER_SIZE = 1;
  public static final int LIGHT_SENSOR_MEDIAN_FILTER_SIZE = 1;
  public static final int US_SENSOR_LOCALIZATION_MEDIAN_FILTER_SIZE = 15;
  public static final int US_SENSOR_SCAN_MEDIAN_FILTER_SIZE = 5;

  // Physical dimensions of robot
  public static final double LEFT_WHEEL_RADIUS = 2.05;
  public static final double RIGHT_WHEEL_RADIUS = 2.05;
  public static final double WHEEL_BASE = 11.7;
  public static final double DIST_CENTER_TO_LINE_DETECTION_LIGHT_SENSOR = 12.3;
  public static final double DIST_CENTER_TO_OBJECT_IDENTIFIER_LIGHT_SENSOR = 5.9;
  public static final double DIST_CENTER_TO_US_SENSOR = 6.6;
  /**
   * Radius spanned by the robot when rotating on itself.
   * <p>
   * The actual value is smaller, but we set it to 15 to provide a safety margin. Setting this any
   * higher would make the robot occupy more than one of the board's tiles.
   */
  public static final double DIST_CENTER_TO_BACK = 15;

  // Wifi constants
  public static final String SERVER_IP = "192.168.2.3";
  public static final int TEAM_NUMBER = 11;

  // Environment constants
  public static final double GRID_SIZE = 30.48;
  public static final int BOARD_SIZE = 12;

  // Other constants
  public static final String LOG_FILENAME = "Log.txt";
  public static final int HARDWARE_INITIALIZATION_MAXIMUM_TRIALS = 5;
  public static final int HARDWARE_INITIALIZATION_RETRY_DELAY = 500;
  public static final int ODOMETRY_CORRECTION_MAX_ERROR_MARGIN = 2;
  public static final int SOUND_VOLUME = 60;
  public static final double LINE_CROSSED_LIGHT_THRESHOLD = -0.06;
  public static final double SCANNING_RANGE = 0.5;
  /**
   * Allows to tweak the speed/balance of the pathfinding algorithm.
   * <p>
   * A value of 1 means will make the algorithm always find the optimal path at the cost of
   * computation time. A higher value will result in suboptimal path that are faster to compute.
   * 
   * @see https://en.wikipedia.org/wiki/A*_search_algorithm#Bounded_relaxation
   */
  public static double PATHFINDING_EPSILON = 3;
}
