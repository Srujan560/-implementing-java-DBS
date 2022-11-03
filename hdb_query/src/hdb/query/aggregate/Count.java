package hdb.query.aggregate;

/**
 * A {@code Count} computes, given a collection of values, the count of the values.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public class Count extends AggregateFunction {

	/**
	 * The count managed by this {@code Count}.
	 */
	protected int count = 0;

	/**
	 * Constructs a {@code Count}.
	 * 
	 * @param attributeName
	 *            the name of the attribute used by the {@code Count}
	 * @param attributeType
	 *            the type of the attribute used by the {@code Count}
	 */
	public Count(String attributeName, Class<?> attributeType) {
		super(attributeName, attributeType);
	}

	/**
	 * Updates this {@code Count} based on the specified value.
	 * 
	 * @param v
	 *            a value for updating this {@code Count}
	 */
	@Override
	public void update(Object v) {
		count++;
	}

	/**
	 * Returns the summary value (i.e., count) from this {@code Average}.
	 * 
	 * @return the summary value (i.e., count) from this {@code Average}
	 */
	@Override
	public Integer value() {
		return count;
	}

	/**
	 * Returns the type of the summary value (i.e., count) from this {@code Average}.
	 * 
	 * @return the type of the summary value (i.e., count) from this {@code Average}
	 */
	@Override
	public Class<?> valueType() {
		return Integer.class;
	}

}