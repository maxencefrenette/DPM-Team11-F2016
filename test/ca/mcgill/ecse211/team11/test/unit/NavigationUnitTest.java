package ca.mcgill.ecse211.team11.test.unit;

import ca.mcgill.ecse211.team11.Initializer;
import ca.mcgill.ecse211.team11.Navigation;
import lejos.hardware.Button;

/**
 * Tests each method in the Navigation class
 * 
 * @author Justin Szeto
 * @version 1.0
 * @since 1.0
 */
public class NavigationUnitTest {

  public static void main(String[] args) {
    Initializer init = new Initializer();
    
    init.odometer.start();
    init.display.start();
    Navigation nav = init.navigation;
   
    nav.turnClockwise(true);
    while (Button.waitForAnyPress() == Button.ID_ALL);
    
    nav.turnClockwise(false);
    while (Button.waitForAnyPress() == Button.ID_ALL);
    
    nav.turn(Math.toRadians(90), true);
    while (Button.waitForAnyPress() == Button.ID_ALL);
    
    nav.turn(Math.toRadians(-90), true);
    while (Button.waitForAnyPress() == Button.ID_ALL);
    
    nav.turnTo(0, true, true);
    while (Button.waitForAnyPress() == Button.ID_ALL);
    
    nav.turnTo(Math.toRadians(90), true, true);
    while (Button.waitForAnyPress() == Button.ID_ALL);
    
    nav.turnTo(0, false, true);
    while (Button.waitForAnyPress() == Button.ID_ALL);
    
    nav.turnTo(Math.toRadians(90), false, true);
    while (Button.waitForAnyPress() == Button.ID_ALL);
    
    nav.turnToWithMinAngle(0, true);
    while (Button.waitForAnyPress() == Button.ID_ALL);
    
    nav.turnToWithMinAngle(Math.toRadians(270), true);
    while (Button.waitForAnyPress() == Button.ID_ALL);
    
    nav.travelTo(30, 30);
    while (Button.waitForAnyPress() == Button.ID_ALL);
    
    nav.travelTo(60, 30);
    while (Button.waitForAnyPress() == Button.ID_ALL);
    
    nav.goBackward(30);
    while (Button.waitForAnyPress() == Button.ID_ALL);
    
    nav.goForward(30);
    while (Button.waitForAnyPress() == Button.ID_ALL);
    
  }

}
