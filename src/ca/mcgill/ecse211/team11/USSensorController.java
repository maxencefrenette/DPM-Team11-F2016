package ca.mcgill.ecse211.team11;

import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

/**
 * Operates the ultrasonic sensor used to measure distances in front of the robot.
 * 
 * @author Justin Szeto
 * @version 4.0
 * @since 1.0
 *
 */
public class USSensorController {
  private SampleProvider spPrecise;
  private SampleProvider spFast;
  private float[] dataPrecise;
  private float[] dataFast;

  public USSensorController(EV3UltrasonicSensor usSensor) {
    spPrecise = usSensor.getDistanceMode();
    spPrecise =
        new NonBufferedMedianFilter(spPrecise, Constants.US_SENSOR_LOCALIZATION_MEDIAN_FILTER_SIZE);
    dataPrecise = new float[spPrecise.sampleSize()];

    spFast = usSensor.getDistanceMode();
    spFast = new NonBufferedMedianFilter(spPrecise, Constants.US_SENSOR_SCAN_MEDIAN_FILTER_SIZE);
    dataFast = new float[spFast.sampleSize()];
  }

  /**
   * Fetches the distance from the ultrasonic sensor to the closest object in front of the robot.
   * 
   * @return The distance read from ultrasonic sensor
   */
  public float getPreciseDistance() {
    spPrecise.fetchSample(dataPrecise, 0);
    return dataPrecise[0];
  }

  public double getLastPreciseDistance() {
    return dataPrecise[0];
  }
  
  public float getFastDistance() {
    spFast.fetchSample(dataFast, 0);
    return dataFast[0];
  }
  
  public double getLastFastDistance() {
    return dataFast[0];
  }
}
