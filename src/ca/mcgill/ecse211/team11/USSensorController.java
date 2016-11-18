package ca.mcgill.ecse211.team11;

import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

/**
 * Operates the ultrasonic sensor used to measure distances in front of the robot.
 * 
 * @author Justin Szeto
 * @version 2.0
 * @since 1.0
 *
 */
public class USSensorController {
  private SampleProvider sp;
  private float[] data;

  public USSensorController(EV3UltrasonicSensor usSensor) {
    sp = usSensor.getDistanceMode();
    sp = new NonBufferedMedianFilter(sp, Constants.US_SENSOR_MEDIAN_FILTER_SIZE);
    data = new float[sp.sampleSize()];
  }

  /**
   * Fetches the distance from the ultrasonic sensor to the closest object in front of the robot.
   * 
   * @return The distance read from ultrasonic sensor
   */
  public float getDistance() {
    sp.fetchSample(data, 0);
    return data[0];
  }

  public double getLastDistance() {
    return data[0];
  }
}
