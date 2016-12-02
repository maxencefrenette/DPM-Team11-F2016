package ca.mcgill.ecse211.team11;

import lejos.robotics.SampleProvider;
import lejos.robotics.filter.AbstractFilter;

/**
 * Provides a non-buffered median filter that seamlessly integrates with the lejos SampleProvider
 * convention.
 * <p>
 * Every time the fetchSample mehod is called, it will fetch n samples, where n is the filterSize,
 * and output their median.
 * 
 * @author Maxence Frenette
 * @version 4.1
 * @since 2.0
 * @see http://www.lejos.org/ev3/docs/lejos/robotics/SampleProvider.html
 */
public class NonBufferedMedianFilter extends AbstractFilter {
  int filterSize;
  float[][] data;

  /**
   * Creates a NonBufferedMedianFilter.
   * 
   * @param source The input SsampleProvider
   * @param filterSize The amount of samples to take the median from
   */
  public NonBufferedMedianFilter(SampleProvider source, int filterSize) {
    super(source);
    this.filterSize = filterSize;
    this.data = new float[sampleSize][filterSize];
  }

  @Override
  /**
   * Fetch n sample, where n is the filter size, from the input SampleProvider and output their
   * median.
   * 
   * @param recieverData {@inheritDoc}
   * @param offset {@inheritDoc}
   */
  public void fetchSample(float[] recieverData, int offset) {
    float[] sourceData = new float[sampleSize];
    for (int i = 0; i < filterSize; i++) {
      source.fetchSample(sourceData, 0);
      for (int j = 0; j < sampleSize; j++) {
        data[j][i] = sourceData[j];
      }
    }

    for (int i = 0; i < sampleSize; i++) {
      recieverData[offset + i] = Util.median(data[i]);
    }
  }
}
