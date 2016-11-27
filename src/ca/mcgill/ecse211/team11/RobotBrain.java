package ca.mcgill.ecse211.team11;

import java.util.HashMap;

import ca.mcgill.ecse211.team11.pathfinding.InternalGrid;
import ca.mcgill.ecse211.team11.pathfinding.InternalGridCell;
import lejos.hardware.Sound;

/**
 * Contains the main algorithm of the robot.
 * <p>
 * The robot is programmed as a state machine. It has some states that it can be in and each state
 * corresponds to a method that will perform some actions, then determine the robot's next state.
 * 
 * @author Maxence Frenette
 * @version 4.0
 * @since 1.0
 */
public class RobotBrain extends Thread {

  private ColorSensorController colorSensorController;
  private ClawMotorController clawMotorController;
  private Display display;
  private Odometer odometer;
  private Navigation navigation;
  private Localization localizer;
  private InternalGrid grid;
  private Scanner scanner;
  private int role;
  private long startTimeMilli;

  public RobotBrain(Initializer init) {
    grid = new InternalGrid(Constants.BOARD_SIZE);
    scanner = new Scanner(init, grid, Constants.SCANNING_RANGE);
  }

  /**
   * Starts the main algorithm.
   * <p>
   * The robot will continuously collect blocks and bring them back to its goal area. If the robot
   * is a builder, it will stack the blocks.
   */
  public void run() {
    // Start threads
    display.start();
    odometer.start();
    scanner.start();

    // Get wifi data
    while (!WifiClient.connectToServer());
    HashMap<String, Integer> wifiData = null;
    do {
      wifiData = WifiClient.retrieveDataFromServer();
    } while (wifiData == null);

    startTimeMilli = System.currentTimeMillis();

    int builderTeam = wifiData.get("BTN");
    int startingCorner;
    if (builderTeam == Constants.TEAM_NUMBER) {
      startingCorner = wifiData.get("BSC");
      role = 0;
    } else {
      startingCorner = wifiData.get("CSC");
      role = 1;
    }

    localizer.setCornerNumber(startingCorner);
    localizer.usLocalize();

    switch (startingCorner) {
      case 1:
        navigation.travelTo(Constants.GRID_SIZE, Constants.GRID_SIZE);
        break;

      case 2:
        navigation.travelTo(Constants.GRID_SIZE * (Constants.BOARD_SIZE - 1), Constants.GRID_SIZE);
        break;

      case 3:
        navigation.travelTo(Constants.GRID_SIZE * (Constants.BOARD_SIZE - 1),
            Constants.GRID_SIZE * (Constants.BOARD_SIZE - 1));
        break;

      case 4:
        navigation.travelTo(Constants.GRID_SIZE, Constants.GRID_SIZE * (Constants.BOARD_SIZE - 1));
        break;
    }
    
    // Beep to indicate that localization is done
    Sound.beep();

    scanner.setScanning(true);
    localizer.lightLocalize();
    scanner.setScanning(false);
    
    State state;
    if (Constants.STAY_IN_CORNER) {
      state = State.RETURN_TO_CORNER;
    } else {
      state = State.EXPLORE;
    }

    while (true) {
      switch (state) {
        case EXPLORE:
          state = explore();
          break;
        case CATCH_BLOCK:
          state = catchBlock();
          break;
        case STACK_BLOCK:
          state = stackBlock();
          break;
        case RE_LOCALIZE:
          state = reLocalize();
          break;
        case RETURN_TO_CORNER:
          state = returnToCorner();
          break;
        default:
          return;
      }
    }
  }

  /**
   * Explores the field by moving around and scanning with the US sensor.
   * <p>
   * Wanders around the field in order to build an internal map containing all the obstacles and the
   * blocks.
   * 
   * @return The next state of the robot.
   */
  public State explore() {
    double currentX = odometer.getX();
    double currentY = odometer.getY();
    while (scanner.locationOfObjects.isEmpty()) {
      if (scanner.locationOfObjects.isEmpty()) {
        // TODO using path finding, update to where robot should move
        return State.EXPLORE;
      }
    }

    Integer[] objectLocation = scanner.locationOfObjects.remove(0);
    // TODO using pathfinding, navigate to block

    if (!colorSensorController.identifyBlock()) {
      grid.setCellByIndex(objectLocation[0], objectLocation[1], InternalGridCell.WOODEN_BLOCK);
      navigation.goBackward(30);
      navigation.travelTo(currentX, currentY);
      return State.RE_LOCALIZE;
    }
    return State.CATCH_BLOCK;
  }

  /**
   * Attempts to grab the block that is right in front of the robot.
   * <p>
   * In case of failure, the block will be tagged as "ungrabable" and will be ignored for the rest
   * of the round.
   * 
   * @return The next state of the robot.
   */
  public State catchBlock() {
    navigation.goBackward(20);
    navigation.turn(180, true);
    clawMotorController.openClaw();
    clawMotorController.lowerClaw();
    navigation.goBackward(25);
    clawMotorController.closeClaw();
    clawMotorController.raiseClaw();
    return State.STACK_BLOCK;
  }

  /**
   * Drives back to the target zone and places the block that is currently being held in the claws.
   * <p>
   * If the robot is the builder, it will stack the block. If it is the garbage collector, it will
   * simply place the block on the ground.
   * 
   * @return The next state of the robot.
   */
  public State stackBlock() {
    // TODO scan while light localizing
    // TODO pathfind to greenzone
    clawMotorController.lowerClaw();
    clawMotorController.openClaw();
    return State.EXPLORE;
  }

  /**
   * Performs the light localization routine to correct the odometer.
   * <p>
   * This method drives to the nearest known free intersection of two lines and performs the light
   * localization routine on it. Wile doing so, it scans the map and updates the internal
   * representation of the map.
   * 
   * @return The next state of the robot.
   */
  public State reLocalize() {
    double localizationX =
        (Constants.GRID_SIZE * (Math.round(odometer.getX() / Constants.GRID_SIZE)));
    double localizationY =
        (Constants.GRID_SIZE * (Math.round(odometer.getY() / Constants.GRID_SIZE)));
    navigation.travelTo(localizationX, localizationY);
    localizer.lightLocalize();
    return State.EXPLORE;
  }

  public State returnToCorner() {
    // TODO navigate to starting corner
    return null;
  }

  /**
   * This enumeration defines the different states in which the robot can be.
   */
  private enum State {
    EXPLORE, CATCH_BLOCK, STACK_BLOCK, RE_LOCALIZE, RETURN_TO_CORNER
  }
}
