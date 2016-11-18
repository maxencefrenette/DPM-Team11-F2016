package ca.mcgill.ecse211.team11;

import lejos.robotics.SampleProvider;
import lejos.robotics.filter.AbstractFilter;

public class NonBufferedMedianFilter extends AbstractFilter {
  int filterSize;
  float[][] data;

  public NonBufferedMedianFilter(SampleProvider source, int filterSize) {
    super(source);
    this.filterSize = filterSize;
    this.data = new float[sampleSize][filterSize];
  }

  @Override
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
