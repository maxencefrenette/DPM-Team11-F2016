package ca.mcgill.ecse211.team11.test.unit;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.mcgill.ecse211.team11.Util;

/**
 * Tests the Util class.
 * 
 * @author Maxence Frenette
 * @version 3.0
 * @since 1.0
 * @see ca.mcgill.ecse211.team11.Util
 */
public class UtilTest {
  public static final double e = 0.001;

  @Test
  public void testNormalizeAngle180() {
    assertEquals("Positive angle", Math.toRadians(10),
        Util.normalizeAngle180(Math.toRadians(360 + 10)), e);
    assertEquals("Negative angle", Math.toRadians(-10),
        Util.normalizeAngle180(Math.toRadians(-4 * 360 - 10)), e);
    assertEquals("Edge case", Math.toRadians(0), Util.normalizeAngle180(Math.toRadians(2 * 360)), e);
  }

  @Test
  public void testNormalizeAngle360() {
    assertEquals("Positive angle", Math.toRadians(10),
        Util.normalizeAngle360(Math.toRadians(360 + 10)), e);
    assertEquals("Negative angle", Math.toRadians(10),
        Util.normalizeAngle360(Math.toRadians(-4 * 360 + 10)), e);
    assertEquals("Edge case", Math.toRadians(180), Util.normalizeAngle360(Math.toRadians(-180)), e);
  }

  @Test
  public void testFindMinAngle() {
    assertEquals("Positive minimum angle, positive start heading, positive end heading",
        Math.toRadians(30), Util.findMinAngle(Math.toRadians(10), Math.toRadians(40)), e);
    assertEquals("Positive minimum angle, negative start heading, positive end heading",
        Math.toRadians(30), Util.findMinAngle(Math.toRadians(-10), Math.toRadians(20)), e);
    assertEquals("Positive minimum angle, positive start heading, positive end heading",
        Math.toRadians(30), Util.findMinAngle(Math.toRadians(190), Math.toRadians(220)), e);
    assertEquals("Positive minimum angle, positive start heading, positive end heading",
        Math.toRadians(30), Util.findMinAngle(Math.toRadians(350), Math.toRadians(20)), e);

    assertEquals("Negative minimum angle, positive start heading, positive end heading",
        Math.toRadians(-30), Util.findMinAngle(Math.toRadians(40), Math.toRadians(10)), e);
    assertEquals("Negative minimum angle, positive start heading, negative end heading",
        Math.toRadians(-30), Util.findMinAngle(Math.toRadians(10), Math.toRadians(-20)), e);
    assertEquals("Negative minimum angle, positive start heading, positive end heading",
        Math.toRadians(-30), Util.findMinAngle(Math.toRadians(270), Math.toRadians(240)), e);
    assertEquals("Negative minimum angle, positive start heading, positive end heading",
        Math.toRadians(-30), Util.findMinAngle(Math.toRadians(10), Math.toRadians(340)), e);

    assertEquals("Edge case, positive start heading, positive end heading", Math.toRadians(10),
        Util.findMinAngle(Math.toRadians(4 * 360 + 10), Math.toRadians(6 * 360 + 20)), e);
    assertEquals("Edge case, negative start heading, negative end heading", Math.toRadians(10),
        Util.findMinAngle(Math.toRadians(-5 * 360 + 10), Math.toRadians(-6 * 360 + 20)), e);

  }

  @Test
  public void testCalculateHeading() {
    assertEquals("From 0,0 to 0,60", Math.toRadians(90), Util.calculateHeading(0, 0, 0, 60), e);
    assertEquals("From 0,0 to 60,0", Math.toRadians(0), Util.calculateHeading(0, 0, 60, 0), e);
    assertEquals("From 0,0 to 60,60", Math.toRadians(45), Util.calculateHeading(0, 0, 60, 60), e);
    assertEquals("From 60,60 to 0,0", Math.toRadians(180 + 45),
        Util.calculateHeading(60, 60, 0, 0), e);
    assertEquals("From 0,60 to 60,0", Math.toRadians(360 - 45),
        Util.calculateHeading(0, 60, 60, 0), e);
    assertEquals("From 60,30 to 30,30", Math.toRadians(180), Util.calculateHeading(60, 30, 30, 30),
        e);
    assertEquals("From 30,30 to 0,0", Math.toRadians(180 + 45),
        Util.calculateHeading(30, 30, 0, 0), e);
  }

  @Test
  public void testcalculateUSLocalizeHeading() {
    // Corner 1 tests
    assertEquals(
        "Initial true heading: 0degrees, AngleA: 135degrees, AngleB: 315degrees, Corner: 1",
        Math.toRadians(315),
        Util.calculateUSLocalizeHeading(Math.toRadians(135), Math.toRadians(315), 1), e);
    assertEquals(
        "Initial true heading: 45degrees, AngleA: 90degrees, AngleB: 270degrees, Corner: 1",
        Math.toRadians(315),
        Util.calculateUSLocalizeHeading(Math.toRadians(90), Math.toRadians(270), 1), e);
    assertEquals(
        "Initial true heading: 180degrees, AngleA: 315degrees, AngleB: 135degrees, Corner: 1",
        Math.toRadians(315),
        Util.calculateUSLocalizeHeading(Math.toRadians(315), Math.toRadians(135), 1), e);

    // Corner 2 tests
    assertEquals(
        "Initial true heading: 0degrees, AngleA: 225degrees, AngleB: 45degrees, Corner: 2",
        Math.toRadians(45),
        Util.calculateUSLocalizeHeading(Math.toRadians(225), Math.toRadians(45), 2), e);
    assertEquals(
        "Initial true heading: 45degrees, AngleA: 180degrees, AngleB: 0degrees, Corner: 2",
        Math.toRadians(45),
        Util.calculateUSLocalizeHeading(Math.toRadians(180), Math.toRadians(0), 2), e);
    assertEquals(
        "Initial true heading: 180degrees, AngleA: 45degrees, AngleB: 225degrees, Corner: 2",
        Math.toRadians(45),
        Util.calculateUSLocalizeHeading(Math.toRadians(45), Math.toRadians(225), 2), e);

    // Corner 3 tests
    assertEquals(
        "Initial true heading: 0degrees, AngleA: 315degrees, AngleB: 135degrees, Corner: 3",
        Math.toRadians(135),
        Util.calculateUSLocalizeHeading(Math.toRadians(315), Math.toRadians(135), 3), e);
    assertEquals(
        "Initial true heading: 45degrees, AngleA: 270degrees, AngleB: 90degrees, Corner: 3",
        Math.toRadians(135),
        Util.calculateUSLocalizeHeading(Math.toRadians(270), Math.toRadians(90), 3), e);
    assertEquals(
        "Initial true heading: 180degrees, AngleA: 135degrees, AngleB: 315degrees, Corner: 3",
        Math.toRadians(135),
        Util.calculateUSLocalizeHeading(Math.toRadians(135), Math.toRadians(315), 3), e);

    // Corner 4 tests
    assertEquals(
        "Initial true heading: 0degrees, AngleA: 45degrees, AngleB: 225degrees, Corner: 4",
        Math.toRadians(225),
        Util.calculateUSLocalizeHeading(Math.toRadians(45), Math.toRadians(225), 4), e);
    assertEquals(
        "Initial true heading: 45degrees, AngleA: 0degrees, AngleB: 180degrees, Corner: 4",
        Math.toRadians(225),
        Util.calculateUSLocalizeHeading(Math.toRadians(0), Math.toRadians(180), 4), e);
    assertEquals(
        "Initial true heading: 180degrees, AngleA: 225degrees, AngleB: 45degrees, Corner: 4",
        Math.toRadians(225),
        Util.calculateUSLocalizeHeading(Math.toRadians(225), Math.toRadians(45), 4), e);
  }

  @Test
  public void testMedian() {
    assertEquals("Median of one number", 1, Util.median(new float[] {1}), e);
    assertEquals("Median of even array", 2.5, Util.median(new float[] {1, 4}), e);
    assertEquals("Median of odd array", 3, Util.median(new float[] {1, 3, 5}), e);
  }
  
  @Test
  public void testConvertToInternalGrid() {
    assertArrayEquals("X: 30.1, Y: 30.1", new int[] {1,1}, Util.convertToInternalGrid(30.1, 30.1));
    assertArrayEquals("X: 200, Y: 36", new int[] {13,2}, Util.convertToInternalGrid(200, 36));
    assertArrayEquals("X: -1, Y: -3", new int[] {0,0}, Util.convertToInternalGrid(-1, -3));
  }
}
