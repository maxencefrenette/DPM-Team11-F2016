package ca.mcgill.ecse211.team11;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;

/**
 * This class bootstraps the robot by initializing every system.
 * <p>
 * It initializes all the sensors and motors and handles the eventual exceptions that could arise.
 * It also initializes all the robot's subsystems.
 * 
 * @author Maxence Frenette
 * @version 1.0
 * @since 1.0
 */
public class Initializer {
  // Motors and sensors
  public EV3LargeRegulatedMotor leftMotor;
  public EV3LargeRegulatedMotor rightMotor;
  public EV3ColorSensor lineDetectionLightSensor;
  public EV3ColorSensor objectIdentifierLightSensor;
  public EV3UltrasonicSensor ultrasonicSensor;
  
  // Subsystems
  public Display display;
  public Odometer odometer;
  public Navigation navigation;

  public Initializer() {
    Logger.setLogWriter(Constants.LOG_FILENAME);
    Logger.setLogStartTime();
    
    leftMotor = initMotor(Constants.LEFT_WHEEL_MOTOR_PORT);
    rightMotor = initMotor(Constants.RIGHT_WHEEL_MOTOR_PORT);
    lineDetectionLightSensor = initColorSensor(Constants.LIGHT_SENSOR_LINE_DETECTION_PORT);
    objectIdentifierLightSensor = initColorSensor(Constants.LIGHT_SENSOR_LINE_DETECTION_PORT);
    ultrasonicSensor = initUltrasonicSensor(Constants.US_SENSOR_PORT);
    
    odometer = new Odometer(this);
    display = new Display(this);
    navigation = new Navigation(this);
  }

  /**
   * Initializes a single motor and manages initialization errors. If an exception is thrown, it
   * will try again up to a maximum number of trials.
   * 
   * @param port The motor's port
   * @return The initialized motor object
   */
  private EV3LargeRegulatedMotor initMotor(String port) {
    EV3LargeRegulatedMotor motor = null;

    for (int i = 0; i < Constants.HARDWARE_INITIALIZATION_MAXIMUM_TRIALS && motor == null; i++) {
      try {
        motor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort(port));
      } catch (Exception e) {
        Util.sleep(Constants.HARDWARE_INITIALIZATION_RETRY_DELAY);
        Logger.logData(e.getMessage());
      }
    }

    return motor;
  }

  /**
   * Initializes a single color sensor and manages initialization errors. If an exception is thrown,
   * it will try again up to a maximum number of trials.
   * 
   * @param port The color sensor's port
   * @return The initialized color sensor object
   */
  private EV3ColorSensor initColorSensor(String port) {
    EV3ColorSensor cs = null;

    for (int i = 0; i < Constants.HARDWARE_INITIALIZATION_MAXIMUM_TRIALS && cs == null; i++) {
      try {
        cs = new EV3ColorSensor(LocalEV3.get().getPort(port));
      } catch (Exception e) {
        Util.sleep(Constants.HARDWARE_INITIALIZATION_RETRY_DELAY);
        Logger.logData(e.getMessage());
      }
    }

    return cs;
  }

  /**
   * Initializes a single ultrasonic sensor and manages initialization errors. If an exception is
   * thrown, it will try again up to a maximum number of trials.
   * 
   * @param port The ultrasonic sensor's port
   * @return The initialized ultrasonic sensor object
   */
  private EV3UltrasonicSensor initUltrasonicSensor(String port) {
    EV3UltrasonicSensor us = null;

    for (int i = 0; i < Constants.HARDWARE_INITIALIZATION_MAXIMUM_TRIALS && us == null; i++) {
      try {
        us = new EV3UltrasonicSensor(LocalEV3.get().getPort(port));
      } catch (Exception e) {
        Util.sleep(Constants.HARDWARE_INITIALIZATION_RETRY_DELAY);
        Logger.logData(e.getMessage());
      }
    }

    return us;
  }
}