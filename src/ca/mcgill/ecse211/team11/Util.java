package ca.mcgill.ecse211.team11;

/**
 * Provides a assortment of utility methods that are used throughout the project.
 * 
 * @author Maxence Frenette
 * @version 1.0
 * @since 1.0
 */
public class Util {
  /**
   * Pauses the execution of the current thread and silently fails in case of error.
   * 
   * @param time The amount of time (in milliseconds) to pause for
   */
  public static void sleep(long time) {
    try {
      Thread.sleep(time);
    } catch (Exception e) {
    }
  }

  /**
   * Normalizes an angle so that -pi > angle >= pi
   * 
   * @param angle The angle to normalize (in radians)
   * @return The normalized angle
   */
  public static double normalizeAngle180(double angle) {
    while (angle < 0) {
      angle += 2 * Math.PI;
    }
    angle %= (2 * Math.PI);
    if (angle > Math.PI) {
      angle -= 2 * Math.PI;
    }
    return angle;
  }

  /**
   * Normalizes an angle so that 0 >= angle > 2pi
   * 
   * @param angle The angle to normalize (in radians)
   * @return The normalized angle
   */
  public static double normalizeAngle360(double angle) {
    while (angle < 0) {
      angle += 2 * Math.PI;
    }
    return angle % (2 * Math.PI);
  }
  
  /**
   * Find the minimum angle the robot needs to turn to reach the desired heading
   * 
   * @param startHeading
   * @param endHeading
   * @return The minimum angle to turn to the endHeading. Positive for CCW. Negative for CW.
   */
  public static double findMinAngle(double startHeading, double endHeading) {
    return normalizeAngle180(endHeading-startHeading);
  }
  
  /**
   * Calculates the heading the robot needs to face in order to travel a straight path to target.
   * 
   * @param currentX - Current x coordinate robot is on
   * @param currentY - Current y coordinate robot is on
   * @param targetX - x coordinate of target destination
   * @param targetY - y coordinate of target destination
   * @return The heading in radians
   */
  public static double calculateHeading(double currentX, double currentY, double targetX, double targetY) {
    double angle = Math.atan2(targetY-currentY, targetX-currentX);
    if (targetX-currentX < 0) {
      angle += Math.PI/2;
    }
    return normalizeAngle360(angle);
  }
}
