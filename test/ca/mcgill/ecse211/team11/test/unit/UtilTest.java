package ca.mcgill.ecse211.team11.test.unit;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.mcgill.ecse211.team11.Util;

/**
 * Tests the Util class.
 * 
 * @author Maxence Frenette
 * @version 2.0
 * @since 1.0
 * @see ca.mcgill.ecse211.team11.Util
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
  
  @Test
  public void testCalculateHeading() {
    assertEquals("From 0,0 to 0,60", Math.toRadians(90), Util.calculateHeading(0, 0, 0, 60), 0.0001);
    assertEquals("From 0,0 to 60,0", Math.toRadians(0), Util.calculateHeading(0, 0, 60, 0), 0.0001);
    assertEquals("From 0,0 to 60,60", Math.toRadians(45), Util.calculateHeading(0, 0, 60, 60), 0.0001);
    assertEquals("From 60,60 to 0,0", Math.toRadians(180+45), Util.calculateHeading(60, 60, 0, 0), 0.0001);
    assertEquals("From 0,60 to 60,0", Math.toRadians(360-45), Util.calculateHeading(0, 60, 60, 0), 0.0001);
    assertEquals("From 60,30 to 30,30", Math.toRadians(180), Util.calculateHeading(60, 30, 30, 30), 0.0001);
    assertEquals("From 30,30 to 0,0", Math.toRadians(180+45), Util.calculateHeading(30, 30, 0, 0), 0.0001);
  }
  
  @Test
  public void testcalculateUSLocalizeHeading() {
    // Corner 1 tests
    assertEquals("Initial true heading: 0degrees, AngleA: 135degrees, AngleB: 315degrees, Corner: 1",
        Math.toRadians(315), Util.calculateUSLocalizeHeading(Math.toRadians(135), Math.toRadians(315), 1),
        0.001);
    assertEquals("Initial true heading: 45degrees, AngleA: 90degrees, AngleB: 270degrees, Corner: 1",
        Math.toRadians(315), Util.calculateUSLocalizeHeading(Math.toRadians(90), Math.toRadians(270), 1),
        0.001);
    assertEquals("Initial true heading: 180degrees, AngleA: 315degrees, AngleB: 135degrees, Corner: 1",
        Math.toRadians(315), Util.calculateUSLocalizeHeading(Math.toRadians(315), Math.toRadians(135), 1),
        0.001);
    
    // Corner 2 tests
    assertEquals("Initial true heading: 0degrees, AngleA: 225degrees, AngleB: 45degrees, Corner: 2",
        Math.toRadians(45), Util.calculateUSLocalizeHeading(Math.toRadians(225), Math.toRadians(45), 2),
        0.001);
    assertEquals("Initial true heading: 45degrees, AngleA: 180degrees, AngleB: 0degrees, Corner: 2",
        Math.toRadians(45), Util.calculateUSLocalizeHeading(Math.toRadians(180), Math.toRadians(0), 2),
        0.001);
    assertEquals("Initial true heading: 180degrees, AngleA: 45degrees, AngleB: 225degrees, Corner: 2",
        Math.toRadians(45), Util.calculateUSLocalizeHeading(Math.toRadians(45), Math.toRadians(225), 2),
        0.001);
    
    // Corner 3 tests
    assertEquals("Initial true heading: 0degrees, AngleA: 315degrees, AngleB: 135degrees, Corner: 3",
        Math.toRadians(135), Util.calculateUSLocalizeHeading(Math.toRadians(315), Math.toRadians(135), 3),
        0.001);
    assertEquals("Initial true heading: 45degrees, AngleA: 270degrees, AngleB: 90degrees, Corner: 3",
        Math.toRadians(135), Util.calculateUSLocalizeHeading(Math.toRadians(270), Math.toRadians(90), 3),
        0.001);
    assertEquals("Initial true heading: 180degrees, AngleA: 135degrees, AngleB: 315degrees, Corner: 3",
        Math.toRadians(135), Util.calculateUSLocalizeHeading(Math.toRadians(135), Math.toRadians(315), 3),
        0.001);
    
    // Corner 4 tests
    assertEquals("Initial true heading: 0degrees, AngleA: 45degrees, AngleB: 225degrees, Corner: 4",
        Math.toRadians(225), Util.calculateUSLocalizeHeading(Math.toRadians(45), Math.toRadians(225), 4),
        0.001);
    assertEquals("Initial true heading: 45degrees, AngleA: 0degrees, AngleB: 180degrees, Corner: 4",
        Math.toRadians(225), Util.calculateUSLocalizeHeading(Math.toRadians(0), Math.toRadians(180), 4),
        0.001);
    assertEquals("Initial true heading: 180degrees, AngleA: 225degrees, AngleB: 45degrees, Corner: 4",
        Math.toRadians(225), Util.calculateUSLocalizeHeading(Math.toRadians(225), Math.toRadians(45), 4),
        0.001);
  }
  
  @Test
  public void testMedian() {
    assertEquals("Median of one number", 1, Util.median(new float[] {1}), 0.001);
    assertEquals("Median of even array", 2.5, Util.median(new float[] {1, 4}), 0.001);
    assertEquals("Median of odd array", 3, Util.median(new float[] {1, 3, 5}), 0.001);
  }
}
