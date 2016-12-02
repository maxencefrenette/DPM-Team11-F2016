package ca.mcgill.ecse211.team11.test.unit;

import static org.junit.Assert.*;
import lejos.robotics.SampleProvider;

import org.junit.Test;

import ca.mcgill.ecse211.team11.NonBufferedMedianFilter;

/**
 * Tests the NonBufferedMedianFilter class
 * 
 * @author Maxence Frenette
 * @version 4.1
 * @since 2.0
 */
public class NonBufferedMedianFilterTest {

  @Test
  public void test() {
    MockSampleProvider sample1 = new MockSampleProvider(
        new float[][] {new float[] {1, 4}, new float[] {2, 4}, new float[] {3, 5}});
    SampleProvider mf = new NonBufferedMedianFilter(sample1, 3);
    float[] data = new float[mf.sampleSize()];
    mf.fetchSample(data, 0);
    assertEquals("First data sample", data[0], 2, 0.001);
    assertEquals("Second data sample", data[1], 4, 0.001);
  }

  /**
   * A mock sample provider that outputs predetermined values
   * 
   * @author Maxence Frenette
   * @version 4.1
   * @since 2.0
   */
  class MockSampleProvider implements SampleProvider {
    int sampleSize;
    float[][] data;
    int numQueries = 0;

    public MockSampleProvider(float[][] data) {
      this.sampleSize = data[0].length;
      this.data = data;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public int sampleSize() {
      return sampleSize;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void fetchSample(float[] sample, int offset) {
      for (int i = 0; i < sampleSize; i++) {
        sample[offset + i] = data[numQueries][i];
      }
      numQueries = (numQueries + 1) % data.length;
    }
  } // End of MockSampleProvider
}
