package ca.mcgill.ecse211.team11;

import java.util.HashMap;

import lejos.hardware.Sound;

/**
 * Main class for the beta demo
 * 
 * @author Justin Szeto
 * @version 3.0
 * @since 2.0
 */
public class DemoMain {

  /**
   * Entry point of the program
   * <p>
   * Initializes all the robot's components and start the algorithm.
   * 
   * @param args
   */
  public static void main(String[] args) {

    // Initialization
    Initializer init = new Initializer();

    // Get wifi data
    while (!WifiClient.connectToServer()) {
      System.out.println("Connecting...:");
    } ;
    HashMap<String, Integer> wifiData = null;
    do {
      wifiData = WifiClient.retrieveDataFromServer();
      System.out.println("Retrieving..");
    } while (wifiData == null);
    System.out.print("Complete!");

    init.display.start();
    init.odometer.start();
    init.localizer.setBeeping(true);

    init.localizer.setCornerNumber(wifiData.get("BSC"));
    init.localizer.usLocalize();
    init.navigation.travelTo(Constants.GRID_SIZE, Constants.GRID_SIZE);
    init.navigation.turnToWithMinAngle(0, true);
    Sound.beep();

    // Scan area
    init.navigation.turnClockwise(false);
    while (Math.abs(init.odometer.getTheta() - Math.PI / 2) > Math.toRadians(1)) {
      if (init.usSensorController.getDistance() < 0.4) {
        break;
      }
    }
    double angle1 = init.odometer.getTheta();
    init.navigation.setSpeeds(0, 0);

    // Scan area
    init.navigation.turnClockwise(false);
    while (Math.abs(init.odometer.getTheta() - Math.PI / 2) > Math.toRadians(1)) {
      if (init.usSensorController.getDistance() > 0.5) {
        break;
      }
    }
    double angle2 = init.odometer.getTheta();
    init.navigation.setSpeeds(0, 0);



    // Turn to block
    init.navigation.turnToWithMinAngle((angle1 + angle2) / 2, true);

    // Go forward until close enough
    while (init.usSensorController.getDistance() > 0.03) {
      init.navigation.setSpeeds(Constants.FORWARD_SPEED, Constants.FORWARD_SPEED);
    }

    while (init.usSensorController.getDistance() < 0.10) {
      init.navigation.setSpeeds(-Constants.FORWARD_SPEED, -Constants.FORWARD_SPEED);
    }

    // Turn around and grab block
    init.navigation.turn(Math.PI, true);
    init.clawMotorController.openClaw();
    init.clawMotorController.lowerClaw();
    init.clawMotorController.closeClaw();

    // Travel to green zone
    double LGZx = (wifiData.get("LGZx") + 1) * Constants.GRID_SIZE;
    double LGZy = (wifiData.get("LGZy") + 1) * Constants.GRID_SIZE;
    init.navigation.travelTo(LGZx, LGZy);

    // Face inside of green zone
    double UGZx = (wifiData.get("UGZx") + 1) * Constants.GRID_SIZE;
    double UGZy = (wifiData.get("UGZy") + 1) * Constants.GRID_SIZE;
    init.navigation.turnToWithMinAngle(
        Util.calculateHeading(init.odometer.getX(), init.odometer.getY(), UGZx, UGZy), true);

    // Turn 180 degree and drop block
    init.navigation.turn(Math.PI, true);
    init.clawMotorController.openClaw();

  }

}
