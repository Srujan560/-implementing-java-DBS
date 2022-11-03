package hdb.query.relational;

import java.util.LinkedList;

import hdb.data.relational.RelationSchema;
import hdb.data.relational.RelationSchema.InvalidAttributeIndexException;
import hdb.data.relational.Tuple;
import hdb.query.expression.Expression;
import hdb.query.expression.UnboundVariableException;
import hdb.query.expression.Variable;

/**
 * An {@code ExpressionEvaluator} can evaluate an {@code Expression} on each given {@code Tuple}.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public class ExpressionEvaluator {

	/**
	 * The {@code Expression} of this {@code ExpressionEvaluator}.
	 */
	Expression expression;

	/**
	 * The indices of the attributes that correspond to the {@code Variable}s of the {@code ArithmeticExpression}.
	 */
	Integer[] indices;

	/**
	 * Constructs an {@code ExpressionEvaluator}.
	 * 
	 * @param expression
	 *            an {@code Expression}
	 * @param schema
	 *            a {@code RelationSchema}
	 * @throws UnboundVariableException
	 *             if a variable in the {@code Expression} does not correspond to any attribute in the
	 *             {@code RelationSchema}
	 */
	public ExpressionEvaluator(Expression expression, RelationSchema schema) throws UnboundVariableException {
		this.expression = expression;
		LinkedList<Integer> indices = new LinkedList<Integer>();
		for (Variable v : expression.variables()) {
			Integer i = schema.attributeIndex(v.name());
			if (i == null)
				throw new UnboundVariableException(v.name());
			indices.add(i);
			try {
				v.setType(schema.attributeType(i));
			} catch (InvalidAttributeIndexException e) {
				e.printStackTrace();
			}
		}
		this.indices = indices.toArray(new Integer[0]);
	}

	/**
	 * Evaluates the {@code Expression} of this {@code ExpressionEvaluator} on the specified {@code Tuple}.
	 * 
	 * @param t
	 *            a {@code Tuple}
	 * @return the result of evaluating the {@code Expression} on the specified {@code Tuple}
	 * @throws UnboundVariableException
	 *             if the {@code Expression} contains a variable whose value is not set
	 */
	public Object evaluate(Tuple t) throws UnboundVariableException {
		int i = 0;
		for (Variable v : expression.variables())
			v.setValue(t.attributeValue(indices[i++]));
		return expression.evaluate();
	}

}
