package ca.mcgill.ecse211.team11;

import lejos.hardware.motor.EV3LargeRegulatedMotor;

/**
 * This class implements a basic odometer that keep tracks of the position of the robot using the
 * rotation data from the wheel motors.
 * 
 * @author Maxence Frenette
 * @version 1.0
 * @since 1.0
 */
public class Odometer extends Thread {
  /** The robot's x position */
  private double x;
  /** The robot's y position */
  private double y;
  /**
   * The robot's orientation in radians according to the mathematical angle convention. 
   */
  private double theta;
  private EV3LargeRegulatedMotor leftMotor, rightMotor;

  /**
   * Creates and initializes the odometer.
   * 
   * @param init The Initializer in charge of creating the connection with the components
   */
  public Odometer(Initializer init) {
    this.leftMotor = init.leftMotor;
    this.rightMotor = init.leftMotor;
    this.x = 0;
    this.y = 0;
    this.theta = 0;
  }

  /**
   * Starts the odometer.
   */
  @Override
  public void run() {
    long updateStart, updateEnd;
    int currentTachoL, currentTachoR;
    int previousTachoL = 0;
    int previousTachoR = 0;

    while (true) {
      updateStart = System.currentTimeMillis();
      currentTachoL = leftMotor.getTachoCount();
      currentTachoR = rightMotor.getTachoCount();

      // Difference in radians of left wheel rotation since last update
      double dL = Constants.LEFT_WHEEL_RADIUS * Math.toRadians(currentTachoL - previousTachoL);
      // Difference in radians of right wheel rotation since last update
      double dR = Constants.RIGHT_WHEEL_RADIUS * Math.toRadians(currentTachoR - previousTachoR);
      // Distance moved since last update
      double dDist = (dL + dR) / 2;
      // Change in orientation since last update
      double dTheta = (dR - dL) / Constants.WHEEL_BASE; 

      synchronized (this) {
        theta += dTheta;
        x += dDist * Math.cos(theta);
        y += dDist * Math.sin(theta);
        theta = Util.normalizeAngle360(theta); // Make sure theta stays within 0 and 2pi
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
    return x;
  }

  /**
   * @param x The x to set
   */
  public synchronized void setX(double x) {
    this.x = x;
  }

  /**
   * @return The y
   */
  public synchronized double getY() {
    return y;
  }

  /**
   * @param y The y to set
   */
  public synchronized void setY(double y) {
    this.y = y;
  }

  /**
   * @return The theta
   */
  public synchronized double getTheta() {
    return theta;
  }

  /**
   * @param theta The theta to set
   */
  public synchronized void setTheta(double theta) {
    this.theta = theta;
  }
}
