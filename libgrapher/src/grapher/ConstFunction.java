package grapher;

/**
 * Represents a function whose output is constant.
 * @author William Leverone
 * @version 1.0
 * @see Function
 */
public class ConstFunction implements Function {
	double y;
	
	public ConstFunction(double y) {
		this.y = y;
	}

	/**
	 * @param x only here to comply with {@link Function Function}
	 * @return the value given to the constructor
	 * @see Function
	 */
	public double func(double x) {
		return y;
	}
}
