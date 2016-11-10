package ca.mcgill.ecse211.team11;

public class OdometryCorrection implements LightSensorObserver {
  private LightSensorController lsc;
  private Odometer odometer;

  public OdometryCorrection(Initializer init) {
    lsc = init.lightSensorController;
    odometer = init.odometer;
    // Get notified every time a line is crossed
    lsc.setObserver(this);
  }

  @Override
  public void onLineCrossed() {
    double x = odometer.getX();
    double y = odometer.getY();
    double theta = odometer.getTheta();

    double lightSensorX =
        x + Constants.DIST_CENTER_TO_LINE_DETECTION_LIGHT_SENSOR * Math.cos(theta);
    double lightSensorY =
        y + Constants.DIST_CENTER_TO_LINE_DETECTION_LIGHT_SENSOR * Math.sin(theta);

    double dx = Util.specialMod(lightSensorX, Constants.GRID_SIZE);
    double dy = Util.specialMod(lightSensorY, Constants.GRID_SIZE);

    if (Math.abs(dx) < Constants.ODOMETRY_CORRECTION_MAX_ERROR_MARGIN
        && Math.abs(dy) < Constants.ODOMETRY_CORRECTION_MAX_ERROR_MARGIN) {
      // No correction can be made since the light sensor just hit a corner
    } else if (Math.abs(dx) < Constants.ODOMETRY_CORRECTION_MAX_ERROR_MARGIN) {
      odometer.setX(x + dx);
    } else if (Math.abs(dy) < Constants.ODOMETRY_CORRECTION_MAX_ERROR_MARGIN) {
      odometer.setY(y + dy);
    } else {
      // The position error has grown over the maximum error margin. This is not good.
      // TODO Trigger a relocalization.
      Logger.logData("The robot's position error has grown over "
          + Constants.ODOMETRY_CORRECTION_MAX_ERROR_MARGIN
          + "cm. No odometry correction could be done.");
    }
  }
}
