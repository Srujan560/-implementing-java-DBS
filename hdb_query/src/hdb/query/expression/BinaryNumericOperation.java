package hdb.query.expression;

/**
 * A {@code BinaryNumericOperation} represents a binary numeric operation.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public abstract class BinaryNumericOperation extends BinaryOperation {

	/**
	 * Constructs a {@code BinaryNumericOperation}.
	 * 
	 * @param left
	 *            the left child
	 * @param right
	 *            the right child
	 */
	public BinaryNumericOperation(Node left, Node right) {
		super(left, right);
	}

	/**
	 * Returns the result type of the expression represented by this {@code BinaryNumericOperation} and its descendants.
	 * 
	 * @return the result type of the expression represented by this {@code BinaryNumericOperation} and its descendants
	 * @throws UnboundVariableException
	 *             if the expression represented by this {@code BinaryNumericOperation} and its descendants has a
	 *             variable whose type is not set
	 */
	@Override
	public Class<?> resultType() throws UnboundVariableException {
		if (left.resultType() == Integer.class) {
			if (right.resultType() == Integer.class)
				return Integer.class;
			else if (right.resultType() == Double.class)
				return Double.class;
			else
				throw new UnsupportedOperationException();

		} else if (left.resultType() == Double.class) {
			if (right.resultType() == Integer.class)
				return Double.class;
			else if (right.resultType() == Double.class)
				return Double.class;
			else
				throw new UnsupportedOperationException();
		} else
			throw new UnsupportedOperationException();
	}

}
