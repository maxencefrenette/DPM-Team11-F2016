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
}
