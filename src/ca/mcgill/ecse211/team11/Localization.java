package ca.mcgill.ecse211.team11;

import lejos.hardware.Sound;

/**
 * Performs localization using the ultrasonic sensor and the rising edge method.
 * 
 * @author Justin Szeto
 * @version 2.0
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
    // Rotate CW until wall detected
    while (usSensorController.getDistance() > Constants.RISING_EDGE_RANGE) {
      navigation.turnClockwise(true);
      Util.sleep(50);
    }
    beep();

    // Sleep this thread to allow robot to rotate past edge
    Util.sleep(Constants.US_LOCALIZE_WAIT_TIME);

    // Continue rotating CW until wall no longer detected
    while (usSensorController.getDistance() <= Constants.RISING_EDGE_RANGE) {
      navigation.turnClockwise(true);
      Util.sleep(50);
    }
    beep();

    // Stop turning and record angle
    navigation.setSpeeds(0, 0);
    double angleA = odometer.getTheta();

    // Rotate CCW until wall detected
    while (usSensorController.getDistance() > Constants.RISING_EDGE_RANGE) {
      navigation.turnClockwise(false);
      Util.sleep(50);
    }
    beep();

    // Sleep thread to allow robot to rotate past edge
    Util.sleep(Constants.US_LOCALIZE_WAIT_TIME);

    // Continue rotating CCW until wall no longer detected
    while (usSensorController.getDistance() <= Constants.RISING_EDGE_RANGE) {
      navigation.turnClockwise(false);
      Util.sleep(50);
    }
    beep();

    // Stop turning and record angle
    navigation.setSpeeds(0, 0);
    double angleB = odometer.getTheta();

    odometer.setTheta(Util.calculateUSLocalizeHeading(angleA, angleB, cornerNumber));

    //TODO Modify to make it apply to any corner, and go over coordinate convention from pdf
    // Calculate x and y
    navigation.turnToWithMinAngle(-Math.PI / 2, true);
    double x = usSensorController.getDistance()*100 + Constants.DIST_CENTER_TO_US_SENSOR;
    navigation.turnToWithMinAngle(-Math.PI, true);
    double y = usSensorController.getDistance()*100 + Constants.DIST_CENTER_TO_US_SENSOR;
    odometer.setX(x);
    odometer.setY(y);
  }

  /**
   * Performs light localization.
   */
  public void lightLocalize() {
    // TODO
    
    navigation.turnClockwise(true);
    //counts number of lines seen
    int count = 0;
    //constants to record angles at which each axis is seen
    double y1 = 0;
    double y2 = 0;
    double x1 = 0;
    double x2 = 0;
    
    //break after 4 lines are seen
    while(count<4) 
    {
        //black line detected
        if(lightSensorController.getLightLevel()*1000<=400 && lightSensorController.getLightLevel()*1000>0) 
                                                         
        {
            //records positive Y axis angle
            if (odometer.getTheta()<Math.toRadians(135) && odometer.getTheta()>Math.toRadians(45)) {
                y1 = odometer.getTheta();
                count++;
            }
            
            //records negative Y axis angle
            if (odometer.getTheta()<Math.toRadians(315) && odometer.getTheta()>Math.toRadians(225)) {
                y2 = odometer.getTheta(); 
                count++;
            }
            
            //records positive X axis angle
            if (odometer.getTheta()>Math.toRadians(315) || odometer.getTheta()<Math.toRadians(45)) {
                x1 = odometer.getTheta();
                count++;    
            }
            
            //records negative X axis angle
            if (odometer.getTheta()<Math.toRadians(225) && odometer.getTheta()>Math.toRadians(135)) {
                x2 = odometer.getTheta();
                count++;
            }
            
        }
    }
    
    navigation.setSpeeds(0, 0);
    
    
    // TODO generalize for any intersection
    // Calculate how far off robot is from (0,0,0)
    double xOffset = -(Constants.DIST_CENTER_TO_LINE_DETECTION_LIGHT_SENSOR)*Math.cos(Math.abs(y1-y2)/2);
    double yOffset = -(Constants.DIST_CENTER_TO_LINE_DETECTION_LIGHT_SENSOR)*Math.cos(Math.abs(x1-x2)/2);
    double thetaOffset = (Math.abs(y1-y2)-360+y2);
    
    odometer.setX(xOffset);
    odometer.setY(yOffset);
    odometer.setTheta(thetaOffset);
   
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
