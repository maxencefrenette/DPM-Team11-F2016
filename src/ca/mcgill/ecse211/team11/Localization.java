package ca.mcgill.ecse211.team11;

import lejos.hardware.Sound;

/**
 * Performs localization using the ultrasonic sensor and the rising edge method.
 * 
 * @author Justin Szeto
 * @version 3.0
 * @since 1.0
 *
 */
public class Localization {

  private Odometer odometer;
  private Navigation navigation;
  private USSensorController usSensorController;
  private LightSensorController lightSensorController;
  private int cornerNumber;
  private boolean beeping = false;

  public Localization(Initializer init) {
    this.odometer = init.odometer;
    this.navigation = init.navigation;
    this.lightSensorController = init.lightSensorController;
    this.usSensorController = init.usSensorController;

  }

  /**
   * Performs US localization.
   */
  public void usLocalize() {
    double minimumDistance = usSensorController.getDistance();
    
    // Rotate CW until wall detected
    navigation.turnClockwise(true);
    while (usSensorController.getDistance() > Constants.RISING_EDGE_RANGE) {
      Util.sleep(50);
    }
    beep();

    // Sleep this thread to allow robot to rotate past edge
    Util.sleep(Constants.US_LOCALIZE_WAIT_TIME);

    // Continue rotating CW until wall no longer detected
    while (usSensorController.getDistance() <= Constants.RISING_EDGE_RANGE) {
      if (usSensorController.getLastDistance() < minimumDistance) {
        minimumDistance = usSensorController.getLastDistance();
      }
      Util.sleep(50);
    }
    beep();

    // Stop turning and record angle
    double angleA = odometer.getTheta();
    navigation.setSpeeds(0, 0);

    // Rotate CCW until wall detected
    navigation.turnClockwise(false);
    while (usSensorController.getDistance() > Constants.RISING_EDGE_RANGE) {
      Util.sleep(50);
    }

    // Sleep thread to allow robot to rotate past edge
    Util.sleep(Constants.US_LOCALIZE_WAIT_TIME);
    
    // Continue rotating CCW until wall no longer detected
    while (usSensorController.getDistance() <= Constants.RISING_EDGE_RANGE) {
      if (usSensorController.getLastDistance() < minimumDistance) {
        minimumDistance = usSensorController.getLastDistance();
      }
      Util.sleep(50);
    }
    beep();

    // Stop turning and record angle
    double angleB = odometer.getTheta();
    navigation.setSpeeds(0, 0);

    odometer.setTheta(Util.calculateUSLocalizeHeading(angleA, angleB, cornerNumber));

    // Calculate x and y
    double x = minimumDistance* 100 + Constants.DIST_CENTER_TO_US_SENSOR;
    double y = minimumDistance* 100 + Constants.DIST_CENTER_TO_US_SENSOR;
    switch (cornerNumber) {
      case 1:
        break;
        
      case 2:
        x = Constants.GRID_SIZE*12 - x;
        break;
       
      case 3:
        x = Constants.GRID_SIZE*12 - x;
        y = Constants.GRID_SIZE*12 - y;
        break;
        
      case 4:
        y = Constants.GRID_SIZE*12 - y;
        break;
    }
    odometer.setX(x);
    odometer.setY(y);
  }

  /**
   * Performs light localization.
   */
  public void lightLocalize() {
    navigation.turnClockwise(true);
    // counts number of lines seen
    int count = 0;
    // constants to record angles at which each axis is seen
    double y1 = 0;
    double y2 = 0;
    double x1 = 0;
    double x2 = 0;

    // break after 4 lines are seen
    while (count < 4) {
      // black line detected
      if (lightSensorController.isLineCrossed()) {
        // records positive Y axis angle
        if (odometer.getTheta() < Math.toRadians(135) && odometer.getTheta() > Math.toRadians(45)) {
          y1 = odometer.getTheta();
          count++;
        }

        // records negative Y axis angle
        if (odometer.getTheta() < Math.toRadians(315) && odometer.getTheta() > Math.toRadians(225)) {
          y2 = odometer.getTheta();
          count++;
        }

        // records positive X axis angle
        if (odometer.getTheta() > Math.toRadians(315) || odometer.getTheta() < Math.toRadians(45)) {
          x1 = odometer.getTheta();
          count++;
        }

        // records negative X axis angle
        if (odometer.getTheta() < Math.toRadians(225) && odometer.getTheta() > Math.toRadians(135)) {
          x2 = odometer.getTheta();
          count++;
        }

        beep();
      }

      Util.sleep(50);
    } // End of while (count < 4)

    navigation.setSpeeds(0, 0);

    // Calculate how far off robot is from (0,0,0)
    double dx =
        -(Constants.DIST_CENTER_TO_LINE_DETECTION_LIGHT_SENSOR)
            * Math.cos(Util.normalizeAngle180(y1 - y2) / 2);
    double dy =
        -(Constants.DIST_CENTER_TO_LINE_DETECTION_LIGHT_SENSOR)
            * Math.cos(Util.normalizeAngle180(x1 - x2) / 2);
    double dTheta = Util.normalizeAngle180(y1 + y2) / 2;

    odometer.setX(odometer.getX() - Util.specialMod(odometer.getX(), Constants.GRID_SIZE) + dx);
    odometer.setY(odometer.getY() - Util.specialMod(odometer.getY(), Constants.GRID_SIZE) + dy);
    odometer.setTheta(odometer.getTheta() + dTheta);
  }

  /**
   * Sets the corner in which the robot started.
   * 
   * @param cornerNumber
   */
  public void setCornerNumber(int cornerNumber) {
    this.cornerNumber = cornerNumber;
  }

  /**
   * Activates the debug beeps on the localization.
   * 
   * @param beeping Should the localization beep ?
   */
  public void setBeeping(boolean beeping) {
    this.beeping = beeping;
  }

  /**
   * Beeps if the debug beeping is activated.
   */
  private void beep() {
    if (beeping) {
      Sound.beep();
    }
  }

}
