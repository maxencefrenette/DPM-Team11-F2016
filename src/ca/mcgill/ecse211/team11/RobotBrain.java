package ca.mcgill.ecse211.team11;

import java.util.HashMap;

import ca.mcgill.ecse211.team11.pathfinding.InternalGrid;
import ca.mcgill.ecse211.team11.pathfinding.PathNode;

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
  private USSensorController usSensorController;
  private ClawMotorController clawMotorController;
  private Display display;
  private Odometer odometer;
  private Navigation navigation;
  private Localization localizer;
  private InternalGrid grid;
  private int role;
  private long startTimeMilli;

  public RobotBrain(Initializer init) {
    // TODO
	  grid = new InternalGrid (init);
  }

  /**
   * Starts the main algorithm.
   * <p>
   * The robot will continuously collect blocks and bring them back to its goal area. If the robot
   * is a builder, it will stack the blocks.
   */
  public void run() {
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
	  double currentX = odometer.getX();
	  double currentY = odometer.getY();
	  while(grid.locationOfObjects.isEmpty())
	  {
          grid.scan();
          if(grid.locationOfObjects.isEmpty())
          {
        	  
              //using path finding, update to where robot should move
          }
	  }
    
	  Integer[] objectLocation = grid.locationOfObjects.remove(0);
	  //convert grid coordinates to real coordinates
          
	  //using pathfinding, navigate to block
	  
      if(!colorSensorController.identifyBlock())
      {
    	  //grid.setWoodBlock(objectLocation[0],objectLocation[1]);
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
	  	grid.scan();
	  	// pathfind to greenzone if builder, pathfind to red zone if collector
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
	  double localizationX = (30*(Math.round(odometer.getX()/30)));
	  double localizationY = (30*(Math.round(odometer.getY()/30)));
	  navigation.travelTo(localizationX, localizationY);
	  localizer.lightLocalize();
	  return State.EXPLORE;
  }

  /**
   * This enumeration defines the different states in which the robot can be.
   */
  private enum State {
    EXPLORE, CATCH_BLOCK, STACK_BLOCK, RE_LOCALIZE, RETURN_TO_CORNER
  }
}
