package hdb.query.aggregate;

/**
 * A {@code Maximum} computes, given a collection of values, the minimum of the values.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public class Maximum extends AggregateFunction {

	/**
	 * The current maximum value.
	 */
	protected Object maximum = null;

	/**
	 * Constructs a {@code Maximum}.
	 * 
	 * @param attributeName
	 *            the name of the attribute used by the {@code Maximum}
	 * @param attributeType
	 *            the type of the attribute used by the {@code Maximum}
	 */
	public Maximum(String attributeName, Class<?> attributeType) {
		super(attributeName, attributeType);
	}

	/**
	 * Updates this {@code Maximum} based on the specified value.
	 * 
	 * @param v
	 *            a value for updating this {@code Maximum}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void update(Object v) {
		if (maximum == null || ((Comparable<Object>) maximum).compareTo(v) < 0)
			maximum = v;
	}

	/**
	 * Returns the summary value (i.e., maximum) from this {@code Maximum}.
	 * 
	 * @return the summary value (i.e., maximum) from this {@code Maximum}
	 */
	@Override
	public Object value() {
		return maximum;
	}

	/**
	 * Returns the type of the summary value (i.e., maximum) from this {@code Maximum}.
	 * 
	 * @return the type of the summary value (i.e., maximum) from this {@code Maximum}
	 */
	@Override
	public Class<?> valueType() {
		return attributeType;
	}

}
