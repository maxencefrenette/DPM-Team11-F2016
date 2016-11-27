package ca.mcgill.ecse211.team11;

import java.util.Iterator;

import ca.mcgill.ecse211.team11.pathfinding.Path;
import ca.mcgill.ecse211.team11.pathfinding.PathNode;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

/**
 * This class controls the motors and moves the robot according to the information given by the
 * odometer.
 * 
 * @author Justin Szeto
 * @version 4.0
 * @since 1.0
 *
 */
public class Navigation {

  private Odometer odometer;

  private EV3LargeRegulatedMotor leftMotor;
  private EV3LargeRegulatedMotor rightMotor;

  public Navigation(Initializer init) {
    odometer = init.odometer;
    leftMotor = init.leftMotor;
    rightMotor = init.rightMotor;
  }

  /**
   * Set motor speeds for left and right wheel, and rotate motors.
   * 
   * @param leftMotorSpeed
   * @param rightMotorSpeed
   */
  public void setSpeeds(double leftMotorSpeed, double rightMotorSpeed) {
    leftMotor.setSpeed((float) leftMotorSpeed);
    rightMotor.setSpeed((float) rightMotorSpeed);

    if (leftMotorSpeed < 0) {
      leftMotor.backward();
    } else {
      leftMotor.forward();
    }

    if (rightMotorSpeed < 0) {
      rightMotor.backward();
    } else {
      rightMotor.forward();
    }
  }

  /**
   * Set motor speeds for left and right wheel, and rotate motors.
   * 
   * @param leftMotorSpeed
   * @param rightMotorSpeed
   */
  public void setSpeeds(int leftMotorSpeed, int rightMotorSpeed) {
    leftMotor.setSpeed(leftMotorSpeed);
    rightMotor.setSpeed(rightMotorSpeed);

    if (leftMotorSpeed < 0) {
      leftMotor.backward();
    } else {
      leftMotor.forward();
    }

    if (rightMotorSpeed < 0) {
      rightMotor.backward();
    } else {
      rightMotor.forward();
    }
  }

  /**
   * 
   * @param leftMotorAccel
   * @param rightMotorAccel
   */
  public void setAccelerations(int leftMotorAccel, int rightMotorAccel) {
    leftMotor.setAcceleration(leftMotorAccel);
    rightMotor.setAcceleration(rightMotorAccel);
  }

  /**
   * Travel along the specified path.
   * 
   * @param path The path to travel along.
   */
  public void travelAlongPath(Path path) {
    Iterator<PathNode> it = path.iterator();
    while (it.hasNext()) {
      PathNode nextNode = it.next();
      travelTo(nextNode.getX(), nextNode.getY());
      turnToWithMinAngle(nextNode.getTheta(), true);
    }
  }

  /**
   * Travel to specified coordinates based on odometer while adjusting heading if needed.
   * 
   * @param x - x coordinate to travel to
   * @param y - y coordinate to travel to
   */
  public void travelTo(double x, double y) {
    double errorX = Math.abs(x - odometer.getX());
    double errorY = Math.abs(y - odometer.getY());
    double targetHeading = Util.calculateHeading(odometer.getX(), odometer.getY(), x, y);
    double headingError = 0;
    double speedAdjustment = 0;

    turnToWithMinAngle(targetHeading, true);

    while ((errorX > Constants.DIST_ERROR || errorY > Constants.DIST_ERROR)
        && Math.abs(headingError) < Math.PI / 2) {
      targetHeading = Util.calculateHeading(odometer.getX(), odometer.getY(), x, y);
      headingError = Util.normalizeAngle180(targetHeading - odometer.getTheta());

      if (Math.abs(headingError) > Constants.ANGLE_ERROR) {
        speedAdjustment = Constants.SPEED_ADJUSTMENT * Math.signum(headingError);
      } else {
        speedAdjustment = 0;
      }

      setSpeeds(Constants.FORWARD_SPEED - speedAdjustment,
          Constants.FORWARD_SPEED + speedAdjustment);
      Util.sleep(50);

      errorX = Math.abs(x - odometer.getX());
      errorY = Math.abs(y - odometer.getY());
    }

    setSpeeds(0, 0);
  }

  /**
   * Turn robot to specified heading using a minimum angle.
   * 
   * @param heading - Heading to turn to
   * @param stop - true to stop motors once finished turning. false to let motors continue
   */
  public void turnToWithMinAngle(double heading, boolean stop) {
    turn(Util.findMinAngle(odometer.getTheta(), heading), stop);
  }

  /**
   * Turn robot to specified heading while turning in specified direction.
   * 
   * @param heading - Heading to turn to
   * @param clockwise - true to turn clockwise. false to turn counterclockwise.
   * @param stop - true to stop motors once finished turning. false to let motors continue
   */
  public void turnTo(double heading, boolean clockwise, boolean stop) {
    double normalizedHeading = Util.normalizeAngle360(heading);

    while (Math.abs(normalizedHeading - odometer.getTheta()) > Constants.ANGLE_ERROR) {
      turnClockwise(clockwise);
    }
    if (stop) {
      setSpeeds(0, 0);
    }

  }

  /**
   * Turns the robot by a specified angle.
   * 
   * @param angle - Angle for robot to turn. Positive for clockwise. Negative for counterclockwise.
   * @param stop - true to stop motors once finished turning. false to let motors continue
   */
  public void turn(double angle, boolean stop) {
    double initialHeading = odometer.getTheta();
    double finalHeading = Util.normalizeAngle360(initialHeading + angle);

    if (angle > 0) {
      while (Math.abs(odometer.getTheta() - finalHeading) > Constants.ANGLE_ERROR) {
        turnClockwise(false);
      }

    } else {
      while (Math.abs(odometer.getTheta() - finalHeading) > Constants.ANGLE_ERROR) {
        turnClockwise(true);
      }
    }
    if (stop) {
      setSpeeds(0, 0);
    }

  }

  /**
   * Turns the robot clockwise or counterclockwise.
   * 
   * @param clockwise - true to turn clockwise. false to turn counterclockwise
   */
  public void turnClockwise(boolean clockwise) {
    if (clockwise) {
      setSpeeds(Constants.TURNING_SPEED, -Constants.TURNING_SPEED);
    } else {
      setSpeeds(-Constants.TURNING_SPEED, Constants.TURNING_SPEED);
    }

  }

  /**
   * Moves robot forward by a given distance based on the odometer.
   * 
   * @param distance - Distance to move robot forward by
   */
  public void goForward(double distance) {
    double targetX = odometer.getX() + Math.cos(odometer.getTheta()) * distance;
    double targetY = odometer.getY() + Math.sin(odometer.getTheta()) * distance;

    travelTo(targetX, targetY);
  }

  /**
   * Moves robot backwards by a given distance based on the odometer.
   * 
   * @param distance - Distance to move robot backward by
   */
  public void goBackward(double distance) {
    double targetX = odometer.getX() - Math.cos(odometer.getTheta()) * distance;
    double targetY = odometer.getY() - Math.sin(odometer.getTheta()) * distance;

    double errorX = Math.abs(targetX - odometer.getX());
    double errorY = Math.abs(targetY - odometer.getY());

    while (errorX > Constants.DIST_ERROR || errorY > Constants.DIST_ERROR) {
      double targetHeading =
          Util.calculateHeading(targetX, targetY, odometer.getX(), odometer.getY());

      if (Math.abs(
          Util.normalizeAngle180(odometer.getTheta() - targetHeading)) > Constants.ANGLE_ERROR) {
        turnToWithMinAngle(targetHeading, false);
      }

      setSpeeds(-Constants.FORWARD_SPEED, -Constants.FORWARD_SPEED);

      errorX = Math.abs(targetX - odometer.getX());
      errorY = Math.abs(targetY - odometer.getY());

    }
    setSpeeds(0, 0);
  }

}
