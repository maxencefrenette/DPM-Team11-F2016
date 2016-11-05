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
}
