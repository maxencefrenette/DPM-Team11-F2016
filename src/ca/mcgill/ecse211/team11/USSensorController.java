package ca.mcgill.ecse211.team11;

import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MedianFilter;

/**
 * Operates the ultrasonic sensor used to measure distances in front of the robot.
 * 
 * @author Justin Szeto
 * @version 1.0
 * @since 1.0
 *
 */
public class USSensorController extends Thread {
  private SampleProvider sp;
  private float[] data;
  private float distance;
  private boolean loggingOn;

  public USSensorController(EV3UltrasonicSensor usSensor) {
    sp = usSensor.getDistanceMode();
    //sp = new MedianFilter(sp, Constants.US_POLLER_MEDIAN_FILTER_SIZE);
    data = new float[sp.sampleSize()];
  }

  /**
   * 
   * @return Distance read from ultrasonic sensor
   */
  public float getDistance() {
    return distance;
  }

  /**
   * Starts the data polling for the ultrasonic sensor.
   */
  public void run() {
    while (true) {
      long pollingStart = System.currentTimeMillis();

      // update distance
      sp.fetchSample(data, 0);
      distance = data[0];

      if (loggingOn) {
        Logger.logData("US Sensor Distance: " + distance);
      }

      // adjust timing of wait period depending on how long it took to get data
      long pollingEnd = System.currentTimeMillis();
      if (pollingEnd - pollingStart < Constants.US_POLLER_WAIT_PERIOD) {
        Util.sleep(Constants.US_POLLER_WAIT_PERIOD - (pollingEnd - pollingStart));
      }
    }
  }

  /**
   * Enables or disables logging of data from ultrasonic sensor.
   * 
   * @param loggingOn - true to enable logging. false to disable logging.
   */
  public void setLogging(boolean loggingOn) {
    this.loggingOn = loggingOn;
  }
}
