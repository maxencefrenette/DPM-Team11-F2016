package ca.mcgill.ecse211.team11.test.unit;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.mcgill.ecse211.team11.Util;

/**
 * Tests the Util class
 * 
 * @author Maxence
 * @version 1.0
 * @since 1.0
 */
public class UtilTest {

  @Test
  public void testNormalizeAngle180() {
    assertEquals("Positive angle", Math.toRadians(10),
        Util.normalizeAngle180(Math.toRadians(360 + 10)), 0.01);
    assertEquals("Negative angle", Math.toRadians(-10),
        Util.normalizeAngle180(Math.toRadians(-4 * 360 - 10)), 0.01);
    assertEquals("Edge case", Math.toRadians(0), Util.normalizeAngle180(Math.toRadians(2 * 360)),
        0.01);
  }

  @Test
  public void testNormalizeAngle360() {
    assertEquals("Positive angle", Math.toRadians(10),
        Util.normalizeAngle360(Math.toRadians(360 + 10)), 0.01);
    assertEquals("Negative angle", Math.toRadians(10),
        Util.normalizeAngle360(Math.toRadians(-4 * 360 + 10)), 0.01);
    assertEquals("Edge case", Math.toRadians(180), Util.normalizeAngle360(Math.toRadians(-180)),
        0.01);
  }
  
  @Test
  public void testFindMinAngle() {
    assertEquals("Positive minimum angle, positive start heading, positive end heading", 
        Math.toRadians(30), Util.findMinAngle(Math.toRadians(10), Math.toRadians(40)), 0.001);
    assertEquals("Positive minimum angle, negative start heading, positive end heading", 
        Math.toRadians(30), Util.findMinAngle(Math.toRadians(-10), Math.toRadians(20)), 0.001);
    assertEquals("Positive minimum angle, positive start heading, positive end heading", 
        Math.toRadians(30), Util.findMinAngle(Math.toRadians(190), Math.toRadians(220)), 0.001);
    assertEquals("Positive minimum angle, positive start heading, positive end heading", 
        Math.toRadians(30), Util.findMinAngle(Math.toRadians(350), Math.toRadians(20)), 0.001);
   
    assertEquals("Negative minimum angle, positive start heading, positive end heading", 
        Math.toRadians(-30), Util.findMinAngle(Math.toRadians(40), Math.toRadians(10)), 0.001);
    assertEquals("Negative minimum angle, positive start heading, negative end heading", 
        Math.toRadians(-30), Util.findMinAngle(Math.toRadians(10), Math.toRadians(-20)), 0.001);
    assertEquals("Negative minimum angle, positive start heading, positive end heading", 
        Math.toRadians(-30), Util.findMinAngle(Math.toRadians(270), Math.toRadians(240)), 0.001);
    assertEquals("Negative minimum angle, positive start heading, positive end heading", 
        Math.toRadians(-30), Util.findMinAngle(Math.toRadians(10), Math.toRadians(340)), 0.001);
    
    assertEquals("Edge case, positive start heading, positive end heading", 
        Math.toRadians(10), Util.findMinAngle(Math.toRadians(4*360+10), Math.toRadians(6*360+20)), 0.001);
    assertEquals("Edge case, negative start heading, negative end heading", 
        Math.toRadians(10), Util.findMinAngle(Math.toRadians(-5*360+10), Math.toRadians(-6*360+20)), 0.001);
    
  }
}
