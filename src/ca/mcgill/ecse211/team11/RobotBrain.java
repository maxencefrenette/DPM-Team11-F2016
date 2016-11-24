package ca.mcgill.ecse211.team11;

/**
 * Contains the main algorithm of the robot.
 * <p>
 * The robot is programmed as a state machine. It has some states that it can be in and each state
 * corresponds to a method that will perform some actions, then determine the robot's next state.
 * 
 * @author Maxence Frenette
 * @version 3.0
 * @since 1.0
 */
public class RobotBrain extends Thread {

  private ColorSensorController colorSensorController;
  private USSensorController usSensorController;
  private ClawMotorController clawMotorController;
  private Display display;
  private Odometer odometer;
  private Navigation navigation;
  private Localization localizer;

  public RobotBrain(Initializer init) {
    // TODO
  }

  /**
   * Starts the main algorithm.
   * <p>
   * The robot will continuously collect blocks and bring them back to its goal area. If the robot
   * is a builder, it will stack the blocks.
   */
  public void run() {
    // TODO fetch data from the server
    localizer.setCornerNumber(0); // TODO: Set the actual corner number.
    localizer.usLocalize();
    // TODO travel to the closest line crossing
    localizer.lightLocalize();
    State state = State.EXPLORE;

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
        default:
          Logger.logData("Error: Default case reached in RobotBrain.run()");
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
    // TODO
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
    // pathfind to greenzone
    clawMotorController.lowerClaw();
    clawMotorController.openClaw();
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
    // TODO
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
    // TODO
    return State.EXPLORE;
  }

  /**
   * This enumeration defines the different states in which the robot can be.
   */
  private enum State {
    EXPLORE, CATCH_BLOCK, STACK_BLOCK, RE_LOCALIZE
  }
}
