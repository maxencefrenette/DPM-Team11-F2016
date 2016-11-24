package ca.mcgill.ecse211.team11;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Provides a assortment of utility methods that are used throughout the project.
 * 
 * @author Maxence Frenette
 * @version 3.0
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
    if (r <= -n / 2) {
      r += n;
    } else if (n / 2 < r) {
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
    return specialMod(angle, 2 * Math.PI);
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
    return normalizeAngle180(endHeading - startHeading);
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
  public static double calculateHeading(double currentX, double currentY, double targetX,
      double targetY) {
    double angle = Math.atan2(targetY - currentY, targetX - currentX);
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
      correctedHeading =
          angleB - Math.PI * 3 / 4 - (angleA + angleB) / 2 + Constants.HEADING_OFFSET;
    } else {
      correctedHeading = angleB + Math.PI / 4 - (angleA + angleB) / 2 + Constants.HEADING_OFFSET;
    }

    // Adjust heading depending on starting corner so that 0 heading is east
    correctedHeading += Math.PI / 2 * (cornerNumber - 1);
    return normalizeAngle360(correctedHeading);
  }

  /**
   * Calculates an array's median.
   * 
   * @param array
   * @return The array's median
   */
  public static float median(float[] array) {
    float[] clonedArray = array.clone();
    Arrays.sort(clonedArray);
    if (clonedArray.length % 2 == 0)
      return (clonedArray[clonedArray.length / 2] + clonedArray[clonedArray.length / 2 - 1]) / 2;
    else
      return clonedArray[clonedArray.length / 2];
  }

  /**
   * Converts a (x,y) coordinate into an equivalent x,y in the internal grid.
   * 
   * @param x x-coordinate
   * @param y y-coordinate
   * @return array containing the x and y of the corresponding internal grid
   */
  public static int[] convertToInternalGrid(double x, double y) {
    int internalGridX = (int) (x / (Constants.GRID_SIZE / 2));
    int internalGridY = (int) (y / (Constants.GRID_SIZE / 2));

    return new int[] {internalGridX, internalGridY};
  }

  /**
   * Get the list of grids from an initial grid to a final grid using Bresenham's line algorithm.
   * 
   * @param initialX initial grid's X
   * @param initialY initial grid's Y
   * @param finalX final grid's X
   * @param finalY final grid's Y
   * @return An ArrayList containing all the internal grids from the initial grid to the final grid.
   */
  public static ArrayList<Integer[]> getGridsInLineOfSight(int initialX, int initialY, int finalX,
      int finalY) {
    ArrayList<Integer[]> gridsInLineOfSight = new ArrayList<Integer[]>();

    // Using Bresenham's line algorithm to approximate grids in line of sight
    int dx = finalX - initialX;
    int dy = finalY - initialY;

    if (Math.abs(dy) > Math.abs(dx)) {
      int diff = 2 * Math.abs(dx) - Math.abs(dy);
      int x = initialX;

      for (int y = initialY; y != finalY + Integer.signum(dy); y += Integer.signum(dy)) {
        gridsInLineOfSight.add(new Integer[] {x, y});
        if (diff > 0) {
          x += Integer.signum(dx);
          diff -= Math.abs(dy);
        }
        diff += Math.abs(dx);
      }
    } else {

      int diff = 2 * Math.abs(dy) - Math.abs(dx);
      int y = initialY;

      for (int x = initialX; x != finalX + Integer.signum(dx); x += Integer.signum(dx)) {
        gridsInLineOfSight.add(new Integer[] {x, y});
        if (diff > 0) {
          y += Integer.signum(dy);
          diff -= Math.abs(dx);
        }
        diff += Math.abs(dy);
      }
    }

    return gridsInLineOfSight;
  }
}
