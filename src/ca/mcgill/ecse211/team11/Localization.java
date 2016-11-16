package ca.mcgill.ecse211.team11;

/**
 * Performs localization using the ultrasonic sensor and the rising edge method.
 * 
 * @author Justin Szeto
 * @version 1.0
 * @since 1.0
 *
 */
public class Localization {

  private Odometer odometer;
  private Navigation navigation;
  private USSensorController usSensorController;
  private int cornerNumber;

  public Localization(Initializer init) {
    this.odometer = init.odometer;
    this.navigation = init.navigation;
    this.usSensorController = init.usSensorController;
  }

  /**
   * Performs US localization.
   */
  public void usLocalize() {

    // Rotate CW until wall detected
    while (usSensorController.getDistance() > Constants.RISING_EDGE_RANGE) {
      navigation.turnClockwise(true);
    }

    // Sleep this thread to allow robot to rotate past edge
    Util.sleep(Constants.US_LOCALIZE_WAIT_TIME);

    // Continue rotating CW until wall no longer detected
    while (usSensorController.getDistance() <= Constants.RISING_EDGE_RANGE) {
      navigation.turnClockwise(true);
    }

    // Stop turning and record angle
    navigation.setSpeeds(0, 0);
    double angleA = odometer.getTheta();

    // Rotate CCW until wall detected
    while (usSensorController.getDistance() > Constants.RISING_EDGE_RANGE) {
      navigation.turnClockwise(false);
    }

    // Sleep thread to allow robot to rotate past edge
    Util.sleep(Constants.US_LOCALIZE_WAIT_TIME);

    // Continue rotating CCW until wall no longer detected
    while (usSensorController.getDistance() <= Constants.RISING_EDGE_RANGE) {
      navigation.turnClockwise(false);
    }

    // Stop turning and record angle
    navigation.setSpeeds(0, 0);
    double angleB = odometer.getTheta();

    odometer.setTheta(Util.calculateUSLocalizeHeading(angleA, angleB, cornerNumber));
    
    // Calculate x and y
    navigation.turnToWithMinAngle(-Math.PI/2, true);
    double x = usSensorController.getDistance() + Constants.DIST_CENTER_TO_US_SENSOR;
    navigation.turnToWithMinAngle(-Math.PI, true);
    double y = usSensorController.getDistance() + Constants.DIST_CENTER_TO_US_SENSOR;
    odometer.setX(x);
    odometer.setY(y);
  }
  
  /**
   * Performs light localization.
   */
  public void lightLocalize() {
    // TODO
  }
  
  /**
   * Sets the corner in which the robot started.
   * 
   * @param cornerNumber
   */
  public void setCornerNumber(int cornerNumber) {
    this.cornerNumber = cornerNumber;
  }
}
