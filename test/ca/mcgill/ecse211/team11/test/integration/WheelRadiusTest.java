package ca.mcgill.ecse211.team11.test.integration;

import ca.mcgill.ecse211.team11.Display;
import ca.mcgill.ecse211.team11.Initializer;
import ca.mcgill.ecse211.team11.Navigation;
import ca.mcgill.ecse211.team11.Odometer;
import lejos.hardware.Button;

public class WheelRadiusTest {
  public static void main(String[] args) {
    Initializer init = new Initializer();

    Navigation nav = init.navigation;
    Odometer odo = init.odometer;
    Display dis = init.display;

    dis.start();
    odo.start();

    nav.travelTo(0, 60.96);

    Button.waitForAnyPress();
    System.exit(0);
  }
}
