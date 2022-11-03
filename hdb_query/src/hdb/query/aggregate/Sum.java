package hdb.query.aggregate;

/**
 * A {@code Sum} computes, given a collection of values, the sum of the values.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public class Sum extends AggregateFunction {

	/**
	 * The sum maintained by this {@code Sum}.
	 */
	Number sum = 0;

	/**
	 * Constructs a {@code Sum}.
	 * 
	 * @param attributeName
	 *            the name of the attribute used by the {@code Sum}
	 * @param attributeType
	 *            the type of the attribute used by the {@code Sum}
	 */
	public Sum(String attributeName, Class<?> attributeType) {
		super(attributeName, attributeType);
	}

	/**
	 * Updates this {@code Sum} based on the specified value.
	 * 
	 * @param v
	 *            a value for updating this {@code Sum}
	 */
	@Override
	public void update(Object v) {
		if (v instanceof Integer)
			sum = ((Integer) v).intValue() + sum.intValue();
		else if (v instanceof Double)
			sum = ((Double) v).doubleValue() + sum.doubleValue();
		else
			throw new UnsupportedOperationException();
	}

	/**
	 * Returns the summary value (i.e., sum) from this {@code Sum}.
	 * 
	 * @return the summary value (i.e., sum) from this {@code Sum}
	 */
	@Override
	public Object value() {
		return sum;
	}

	/**
	 * Returns the type of the summary value (i.e., sum) from this {@code Sum}.
	 * 
	 * @return the type of the summary value (i.e., sum) from this {@code Sum}
	 */
	@Override
	public Class<?> valueType() {
		return attributeType;
	}

}