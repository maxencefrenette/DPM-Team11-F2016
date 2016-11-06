package ca.mcgill.ecse211.team11;

/**
 * Observer pattern implementation for the line detection. It will allow the LightSensorController
 * instance to tell the OdometryCorrection instance that a line has been crossed.
 * 
 * @author Maxence
 * @version 1.0
 * @since 1.0
 */
public interface LightSensorObserver {
  public void onLineCrossed();
}
