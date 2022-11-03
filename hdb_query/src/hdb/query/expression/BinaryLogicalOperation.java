package hdb.query.expression;

/**
 * A {@code BinaryLogicalOperation} represents a binary logical operation.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public abstract class BinaryLogicalOperation extends BinaryOperation {

	/**
	 * Constructs a {@code BinaryLogicalOperation}.
	 * 
	 * @param left
	 *            the left child
	 * @param right
	 *            the right child
	 */
	public BinaryLogicalOperation(Node left, Node right) {
		super(left, right);
	}

	/**
	 * Returns the result type of the expression represented by this {@code BinaryLogicalOperation} and its descendants.
	 * 
	 * @return the result type of the expression represented by this {@code BinaryLogicalOperation} and its descendants
	 * @throws UnboundVariableException
	 *             if the expression represented by this {@code BinaryLogicalOperation} and its descendants has a
	 *             variable whose type is not set
	 */
	@Override
	public Class<?> resultType() throws UnboundVariableException {
		left.resultType();
		right.resultType();
		return Boolean.class;
	}

}
