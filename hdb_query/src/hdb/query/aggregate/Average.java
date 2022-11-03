package hdb.query.aggregate;

/**
 * A {@code Average} computes, given a collection of values, the average of the values.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public class Average extends Sum {

	/**
	 * The count.
	 */
	int count = 0;

	/**
	 * Constructs a {@code Average}.
	 * 
	 * @param attributeName
	 *            the name of the attribute used by the {@code Average}
	 * @param attributeType
	 *            the type of the attribute used by the {@code Average}
	 */
	public Average(String attributeName, Class<?> attributeType) {
		super(attributeName, attributeType);
	}

	/**
	 * Updates this {@code Average} based on the specified value.
	 * 
	 * @param v
	 *            a value for updating this {@code Average}
	 */
	@Override
	public void update(Object v) {
		super.update(v);
		count++;
	}

	/**
	 * Returns the summary value (i.e., average) from this {@code Average}.
	 * 
	 * @return the summary value (i.e., average) from this {@code Average}
	 */
	@Override
	public Object value() {
		if (attributeType.equals(Integer.class))
			return ((Integer) sum) / count;
		else if (attributeType.equals(Double.class))
			return ((Double) sum) / count;
		else
			throw new UnsupportedOperationException();
	}

}