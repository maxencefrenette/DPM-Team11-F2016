package ca.mcgill.ecse211.team11;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

/**
 * Operates the color sensor used for object identification.
 * 
 * @author Maxence
 * @version 1.0
 * @since 1.0
 */
public class ColorSensorController {
  private SampleProvider sp;
  private float[] data;

  public ColorSensorController(EV3ColorSensor colorSensor) {
    sp = colorSensor.getRGBMode();
    data = new float[sp.sampleSize()];
  }
  
  /**
   * Fetches raw data from the color sensor.
   * 
   * @return The RGB values read by the color sensor
   */
  public float[] getData() {
    sp.fetchSample(data, 0);
    return new float[] {data[0], data[1], data[2]};
  }
  
  /**
   * Identifies the the nature of the block in front of the color sensor
   * 
   * @return Is the block a blue block ?
   */
  public boolean identifyBlock() {
    float[] newData = getData();
    return newData[2] > newData[0];
  }
}
