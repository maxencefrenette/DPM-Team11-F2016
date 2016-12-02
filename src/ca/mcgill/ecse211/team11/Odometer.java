package ca.mcgill.ecse211.team11;

import lejos.hardware.motor.EV3LargeRegulatedMotor;

/**
 * This class implements a basic odometer that keep tracks of the position of the robot using the
 * rotation data from the wheel motors.
 * 
 * @author Maxence Frenette
 * @version 4.1
 * @since 1.0
 */
public class Odometer extends Thread {
  /** The robot's x position */
  private double x;
  /** The robot's y position */
  private double y;
  /** The robot's orientation in radians according to the mathematical angle convention. */
  private double theta;
  /** Reference to the robot's left wheel motor */
  private EV3LargeRegulatedMotor leftMotor;
  /** Reference to the robot's right wheel motor */
  private EV3LargeRegulatedMotor rightMotor;
  /** Lock object that synchronizes any access to the odometer's coordinates */
  private Object lock;

  /**
   * Set to true to activate the logging
   */
  private boolean logging = false;

  /**
   * Creates and initializes the odometer.
   * 
   * @param init The Initializer in charge of creating the connection with the components
   */
  public Odometer(Initializer init) {
    this.leftMotor = init.leftMotor;
    this.rightMotor = init.rightMotor;
    this.x = 0;
    this.y = 0;
    this.theta = 0;
    lock = new Object();
  }

  @Override
  /**
   * Starts the odometer.
   */
  public void run() {
    long updateStart, updateEnd;
    int previousTachoL = 0;
    int previousTachoR = 0;

    while (true) {
      updateStart = System.currentTimeMillis();
      int currentTachoL = leftMotor.getTachoCount();
      int currentTachoR = rightMotor.getTachoCount();

      // Difference in radians of left wheel rotation since last update
      double dL = Constants.LEFT_WHEEL_RADIUS * Math.toRadians(currentTachoL - previousTachoL);
      // Difference in radians of right wheel rotation since last update
      double dR = Constants.RIGHT_WHEEL_RADIUS * Math.toRadians(currentTachoR - previousTachoR);
      // Distance moved since last update
      double dDist = (dL + dR) / 2;
      // Change in orientation since last update
      double dTheta = (dR - dL) / Constants.WHEEL_BASE;

      synchronized (lock) {
        theta += dTheta;
        x += dDist * Math.cos(theta);
        y += dDist * Math.sin(theta);

        // Log updated data
        if (logging) {
          // Make sure to log the normalized theta
          Logger.logData("Odometer: X: " + x + " Y: " + y + " Theta: " + getTheta());
        }
      }

      // Updates previous tacho count
      previousTachoL = currentTachoL;
      previousTachoR = currentTachoR;

      // Ensures that the odometer only runs once every period
      updateEnd = System.currentTimeMillis();
      if (updateEnd - updateStart < Constants.ODOMETER_WAIT_PERIOD) {
        Util.sleep(Constants.ODOMETER_WAIT_PERIOD - (updateEnd - updateStart));
      }
    }
  }

  /**
   * @return The x
   */
  public synchronized double getX() {
    double result;

    synchronized (lock) {
      result = x;
    }

    return result;
  }

  /**
   * @param x The x to set
   */
  public synchronized void setX(double x) {
    synchronized (lock) {
      this.x = x;
    }
  }

  /**
   * @return The y
   */
  public double getY() {
    double result;

    synchronized (lock) {
      result = y;
    }

    return result;
  }

  /**
   * @param y The y to set
   */
  public void setY(double y) {
    synchronized (lock) {
      this.y = y;
    }
  }

  /**
   * @return The unnormalized theta
   */
  public double getUnwrappedTheta() {
    double result;

    synchronized (lock) {
      result = theta;
    }

    return result;
  }

  /**
   * @return The theta normalized to be between 0 and 2pi.
   */
  public double getTheta() {
    return Util.normalizeAngle360(getUnwrappedTheta());
  }

  /**
   * @param theta The theta to set
   */
  public void setTheta(double theta) {
    synchronized (lock) {
      this.theta = theta;
    }
  }

  /**
   * Set to true to log the robot's position on each update.
   * 
   * @param logging
   */
  public void setLogging(boolean logging) {
    this.logging = logging;
  }
}
