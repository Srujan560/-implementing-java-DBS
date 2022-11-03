package hdb.query.expression;

/**
 * An {@code EqualTo} represents an equality operation.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public class EqualTo extends BinaryLogicalOperation {

	/**
	 * Constructs an {@code EqualTo}.
	 * 
	 * @param left
	 *            the left child
	 * @param right
	 *            the right child
	 */
	public EqualTo(Node left, Node right) {
		super(left, right);
	}

	/**
	 * Evaluates the expression represented by this {@code EqualTo} and its descendants.
	 * 
	 * @return the result of evaluating the expression represented by this {@code EqualTo} and its descendants
	 * @throws UnboundVariableException
	 *             if the expression represented by this {@code EqualTo} and its descendants has a variable whose value
	 *             is not set
	 */
	@Override
	public Object evaluate() throws UnboundVariableException {
		Object l = object2num(left.evaluate());
		Object r = object2num(right.evaluate());
		if (l instanceof Integer && r instanceof Integer)
			return ((Integer) l).intValue() == ((Integer) r).intValue();
		else if (l instanceof Integer && r instanceof Double)
			return ((Integer) l).intValue() == ((Double) r).doubleValue();
		else if (l instanceof Double && r instanceof Integer)
			return ((Double) l).doubleValue() == ((Integer) r).intValue();
		else if (l instanceof Double && r instanceof Double)
			return ((Double) l).doubleValue() == ((Double) r).doubleValue();
		else
			throw new UnsupportedOperationException();
	}

}
