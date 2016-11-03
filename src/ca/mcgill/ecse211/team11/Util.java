package ca.mcgill.ecse211.team11;

/**
 * Provides a assortment of utility methods that are used throughout the project.
 * 
 * @author Maxence Frenette
 */
public class Util {
  /**
   * Pauses the execution of the current thread and silently fails in case of error.
   * 
   * @param time The amount of time (in milliseconds) to pause for
   */
  public static void sleep(int time) {
    try {
      Thread.sleep(time);
    } catch (Exception e) {
    }
  }
}
