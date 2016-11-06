package ca.mcgill.ecse211.team11;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MedianFilter;

/**
 * Operates the light sensor that will be pointed downwards in order to detect lines for odometry
 * correction purposes.
 * 
 * @author Maxence
 * @version 1.0
 * @since 1.0
 */
public class LightSensorController extends Thread {
  private SampleProvider sp;
  private float[] data;
  private LightSensorObserver observer;

  public LightSensorController(EV3ColorSensor colorSensor) {
    sp = colorSensor.getRedMode();
    sp = new MedianFilter(sp, Constants.LIGHT_POLLER_MEDIAN_FILTER_SIZE);
    data = new float[sp.sampleSize()];
  }

  public float getData() {
    sp.fetchSample(data, 0);
    return data[0];
  }

  public void run() {
    while (true) {
      if (getData() < 0.30) {
        observer.onLineCrossed();
      }

      Util.sleep(Constants.LIGHT_POLLER_WAIT_PERIOD);
    }
  }

  public void setObserver(LightSensorObserver observer) {
    this.observer = observer;
  }
}
