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
    assertEquals("Edge case", Math.toRadians(0), Util.normalizeAngle180(Math.toRadians(2 * 360)),
        e);
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
    assertEquals("From 60,60 to 0,0", Math.toRadians(180 + 45), Util.calculateHeading(60, 60, 0, 0),
        e);
    assertEquals("From 0,60 to 60,0", Math.toRadians(360 - 45), Util.calculateHeading(0, 60, 60, 0),
        e);
    assertEquals("From 60,30 to 30,30", Math.toRadians(180), Util.calculateHeading(60, 30, 30, 30),
        e);
    assertEquals("From 30,30 to 0,0", Math.toRadians(180 + 45), Util.calculateHeading(30, 30, 0, 0),
        e);
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
    assertEquals("Initial true heading: 0degrees, AngleA: 225degrees, AngleB: 45degrees, Corner: 2",
        Math.toRadians(45),
        Util.calculateUSLocalizeHeading(Math.toRadians(225), Math.toRadians(45), 2), e);
    assertEquals("Initial true heading: 45degrees, AngleA: 180degrees, AngleB: 0degrees, Corner: 2",
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
    assertEquals("Initial true heading: 0degrees, AngleA: 45degrees, AngleB: 225degrees, Corner: 4",
        Math.toRadians(225),
        Util.calculateUSLocalizeHeading(Math.toRadians(45), Math.toRadians(225), 4), e);
    assertEquals("Initial true heading: 45degrees, AngleA: 0degrees, AngleB: 180degrees, Corner: 4",
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
    assertArrayEquals("X: 30.1, Y: 30.1", new int[] {1, 1}, Util.convertToInternalGrid(30.1, 30.1));
    assertArrayEquals("X: 200, Y: 36", new int[] {13, 2}, Util.convertToInternalGrid(200, 36));
    assertArrayEquals("X: -1, Y: -3", new int[] {0, 0}, Util.convertToInternalGrid(-1, -3));
  }

  @Test
  public void testGetGridsInLineOfSight() {
    // First Octant
    assertArrayEquals("Initial Grid: 0,0 Final Grid: 5,0",
        new Integer[][] {{0, 0}, {1, 0}, {2, 0}, {3, 0}, {4, 0}, {5, 0}},
        Util.getGridsInLineOfSight(0, 0, 5, 0).toArray());

    assertArrayEquals("Initial Grid: 0,0 Final Grid: 6,3",
        new Integer[][] {{0, 0}, {1, 0}, {2, 1}, {3, 1}, {4, 2}, {5, 2}, {6, 3}},
        Util.getGridsInLineOfSight(0, 0, 6, 3).toArray());

    assertArrayEquals("Initial Grid: 0,0 Final Grid: 4,4",
        new Integer[][] {{0, 0}, {1, 1}, {2, 2}, {3, 3}, {4, 4}},
        Util.getGridsInLineOfSight(0, 0, 4, 4).toArray());

    // Second Octant
    assertArrayEquals("Initial Grid: 0,0 Final Grid: 0,5",
        new Integer[][] {{0, 0}, {0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5}},
        Util.getGridsInLineOfSight(0, 0, 0, 5).toArray());

    assertArrayEquals("Initial Grid: 0,0 Final Grid: 3,6",
        new Integer[][] {{0, 0}, {0, 1}, {1, 2}, {1, 3}, {2, 4}, {2, 5}, {3, 6}},
        Util.getGridsInLineOfSight(0, 0, 3, 6).toArray());

    // Third Octant
    assertArrayEquals("Initial Grid: 3,6 Final Grid: 0,12",
        new Integer[][] {{3, 6}, {3, 7}, {2, 8}, {2, 9}, {1, 10}, {1, 11}, {0, 12}},
        Util.getGridsInLineOfSight(3, 6, 0, 12).toArray());

    // Fourth Octant
    assertArrayEquals("Initial Grid: 12,0 Final Grid: 6,3",
        new Integer[][] {{12, 0}, {11, 0}, {10, 1}, {9, 1}, {8, 2}, {7, 2}, {6, 3}},
        Util.getGridsInLineOfSight(12, 0, 6, 3).toArray());

    assertArrayEquals("Initial Grid: 8,0 Final Grid: 4,4",
        new Integer[][] {{8, 0}, {7, 1}, {6, 2}, {5, 3}, {4, 4}},
        Util.getGridsInLineOfSight(8, 0, 4, 4).toArray());

    // Fifth Octant
    assertArrayEquals("Initial Grid: 5,0 Final Grid: 0,0",
        new Integer[][] {{5, 0}, {4, 0}, {3, 0}, {2, 0}, {1, 0}, {0, 0}},
        Util.getGridsInLineOfSight(5, 0, 0, 0).toArray());

    assertArrayEquals("Initial Grid: 6,3 Final Grid: 0,0",
        new Integer[][] {{6, 3}, {5, 3}, {4, 2}, {3, 2}, {2, 1}, {1, 1}, {0, 0}},
        Util.getGridsInLineOfSight(6, 3, 0, 0).toArray());

    assertArrayEquals("Initial Grid: 4,4 Final Grid: 0,0",
        new Integer[][] {{4, 4}, {3, 3}, {2, 2}, {1, 1}, {0, 0}},
        Util.getGridsInLineOfSight(4, 4, 0, 0).toArray());

    // Sixth Octant
    assertArrayEquals("Initial Grid: 3,6 Final Grid: 0,0",
        new Integer[][] {{3, 6}, {3, 5}, {2, 4}, {2, 3}, {1, 2}, {1, 1}, {0, 0}},
        Util.getGridsInLineOfSight(3, 6, 0, 0).toArray());

    // Seventh Octant
    assertArrayEquals("Initial Grid: 5,0 Final Grid: 0,0",
        new Integer[][] {{5, 0}, {4, 0}, {3, 0}, {2, 0}, {1, 0}, {0, 0}},
        Util.getGridsInLineOfSight(5, 0, 0, 0).toArray());

    assertArrayEquals("Initial Grid: 3,6 Final Grid: 6,0",
        new Integer[][] {{3, 6}, {3, 5}, {4, 4}, {4, 3}, {5, 2}, {5, 1}, {6, 0}},
        Util.getGridsInLineOfSight(3, 6, 6, 0).toArray());

    // Eighth Octant
    assertArrayEquals("Initial Grid: 12,0 Final Grid: 6,3",
        new Integer[][] {{6, 3}, {7, 3}, {8, 2}, {9, 2}, {10, 1}, {11, 1}, {12, 0}},
        Util.getGridsInLineOfSight(6, 3, 12, 0).toArray());

    assertArrayEquals("Initial Grid: 4,4 Final Grid: 8,0",
        new Integer[][] {{4, 4}, {5, 3}, {6, 2}, {7, 1}, {8, 0}},
        Util.getGridsInLineOfSight(4, 4, 8, 0).toArray());

  }
}
