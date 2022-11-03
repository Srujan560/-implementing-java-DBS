package hdb.query.aggregate;

/**
 * A {@code Minimum} computes, given a collection of values, the minimum of the values.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public class Minimum extends AggregateFunction {

	/**
	 * The current minimum value.
	 */
	protected Object minimum = null;

	/**
	 * Constructs a {@code Minimum}.
	 * 
	 * @param attributeName
	 *            the name of the attribute used by the {@code Minimum}
	 * @param attributeType
	 *            the type of the attribute used by the {@code Minimum}
	 */
	public Minimum(String attributeName, Class<?> attributeType) {
		super(attributeName, attributeType);
	}

	/**
	 * Updates this {@code Minimum} based on the specified value.
	 * 
	 * @param v
	 *            a value for updating this {@code Minimum}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void update(Object v) {
		if (minimum == null || ((Comparable<Object>) minimum).compareTo(v) > 0)
			minimum = v;
	}

	/**
	 * Returns the summary value (i.e., minimum) from this {@code Minimum}.
	 * 
	 * @return the summary value (i.e., minimum) from this {@code Minimum}
	 */
	@Override
	public Object value() {
		return minimum;
	}

	/**
	 * Returns the type of the summary value (i.e., minimum) from this {@code Minimum}.
	 * 
	 * @return the type of the summary value (i.e., minimum) from this {@code Minimum}
	 */
	@Override
	public Class<?> valueType() {
		return attributeType;
	}

}
