
package frc.robot;

import java.util.function.Supplier;

public class SoftLimiter {
  private Double maxValue = null;
  private Double minValue = null;
  public static boolean onWorked = false;
  private Supplier<Double> state;

  public SoftLimiter(Supplier<Double> state) {
    this.state = state;
  }

  public void setRange(double max, double min) {
    this.maxValue = max;
    this.minValue = min;
  }

  public Double getOutput(double input) {
    double state = this.state.get();
    double output = input;
    if (onWorked) {
      if (state > maxValue) {output = output > 0 ? 0: output;}
      if (state < minValue) {output = output < 0 ? 0: output;}
    }
    return output;
  }
}
