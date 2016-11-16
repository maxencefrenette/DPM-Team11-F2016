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
   * Computes r such that a = n*q + r and that -n/2 < r <= n/2
   * 
   * @param a The dividend
   * @param n The divisor
   * @return The value fo r
   */
  public static double specialMod(double a, double n) {
    double r = a % n;
    if (r <= -n/2) {
      r += n;
    } else if (n/2 < r) {
      r -= n;
    }
    return r;
  }
  
  /**
   * Normalizes an angle so that -pi > angle >= pi
   * 
   * @param angle The angle to normalize (in radians)
   * @return The normalized angle
   */
  public static double normalizeAngle180(double angle) {
    return specialMod(angle, 2*Math.PI);
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
    return normalizeAngle360(angle);
  }
  
  /**
   * Calculates the corrected heading for US Localization.
   * <p>
   * Assumes the robot is stopped at angleB when this calculation is performed.
   * 
   * @param angleA - Heading from odometer when detected rising edge while rotating CW.
   * @param angleB - Heading from odometer when detected rising edge while rotating CCW.
   * @param cornerNumber - The corner number the robot starts at.
   * @return The corrected heading the robot is at in radians.
   */
  public static double calculateUSLocalizeHeading(double angleA, double angleB, int cornerNumber) {
    double correctedHeading;
    if (angleA < angleB) {
      correctedHeading = angleB - Math.PI*3/4 -(angleA+angleB)/2 + Constants.HEADING_OFFSET;
    } else {
      correctedHeading = angleB + Math.PI/4 -(angleA+angleB)/2 + Constants.HEADING_OFFSET;
    }
    
    // Adjust heading depending on starting corner so that 0 heading is east 
    correctedHeading -= 90*(cornerNumber-1);
    return correctedHeading;
  }
}
