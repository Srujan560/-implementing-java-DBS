package hdb.query.aggregate;

/**
 * An {@code AggregateFunction} computes a summary value (e.g., maximum, minimum, count, and average) over a set of
 * values.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public abstract class AggregateFunction {

	/**
	 * The name of the attribute used by this {@code AggregateFunction}.
	 */
	protected String attributeName;

	/**
	 * The type of the attribute used by this {@code AggregateFunction}.
	 */
	protected Class<?> attributeType;

	/**
	 * Constructs an {@code AggregateFunction}.
	 * 
	 * @param attributeName
	 *            the name of the attribute used by the {@code AggregateFunction}
	 * @param attributeType
	 *            the type of the attribute used by the {@code AggregateFunction}
	 */
	public AggregateFunction(String attributeName, Class<?> attributeType) {
		this.attributeName = attributeName;
		this.attributeType = attributeType;
	}

	/**
	 * Returns a string representation of this {@code AggregateFunction}.
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" + attributeName + ")";
	}

	/**
	 * Updates this {@code AggregateFunction} based on the specified value.
	 * 
	 * @param v
	 *            a value for updating this {@code AggregateFunction}
	 */
	public abstract void update(Object v);

	/**
	 * Returns the summary value from this {@code AggregateFunction}.
	 * 
	 * @return the summary value from this {@code AggregateFunction}
	 */
	public abstract Object value();

	/**
	 * Returns the type of the summary value from this {@code AggregateFunction}.
	 * 
	 * @return the type of the summary value from this {@code AggregateFunction}
	 */
	public abstract Class<?> valueType();

}
