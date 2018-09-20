package grapher;

/**
 * Represents a linear function.
 * @author William Leverone
 * @version 1.0
 * @see Function
 */
public class LinearFunction implements Function {
	/**
	 * Represents the {@link Function Function}'s slope.
	 */
	private double m;
	
	/**
	 * Represents the {@link Function Function}'s y-intercept.
	 */
	private double b;
	
	public LinearFunction(double slope, double yIntercept) {
		this.m = slope;
		this.b = yIntercept;
	}
	
	/**
	 * @return the result of {@link #m m} * x + {@link #b b}
	 */
	public double func(double x) {
		return (this.m * x) + this.b;
	}
}
