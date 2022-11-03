package hdb.query.relational;

import hdb.query.expression.ArithmeticExpression;
import hdb.query.expression.ParsingException;
import hdb.query.expression.UnboundVariableException;
import hdb.data.relational.RelationSchema;
import hdb.data.relational.Tuple;
import hdb.data.relational.RelationSchema.InvalidRelationSchemaDefinitionException;
import hdb.data.relational.Tuple.TypeException;

/**
 * A {@code ProjectionOperator} converts each input {@code Tuple} into an output {@code Tuple}.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public class ProjectionOperator extends UnaryOperator {

	/**
	 * The {@code ExpressionEvaluator}s for this {@code ProjectionOperator}.
	 */
	protected ExpressionEvaluator[] evaluators;

	/**
	 * The output schema of this {@code ProjectionOperator}.
	 */
	protected RelationSchema outputSchema;

	/**
	 * Constructs a {@code ProjectionOperator}.
	 * 
	 * @param input
	 *            the input {@code Operator} for this {@code ProjectionOperator}
	 * @param attributeNames
	 *            the names of the attributes to include in the output schema of this {@code ProjectionOperator}
	 * @param attributeDefinitions
	 *            strings representing expressions that define the attributes to include the output schema of this
	 *            {@code ProjectionOperator}
	 * @throws InvalidRelationSchemaDefinitionException
	 *             if the number of attribute names and the number of attribute types do not match
	 * @throws ParsingException
	 *             if an error occurs while parsing the expressions.
	 * @throws UnboundVariableException
	 *             if a variable in an attribute definition does not correspond to any attribute in the input schema of
	 *             the {@code ProjectionOperator}
	 */
	public ProjectionOperator(Operator input, String[] attributeNames, String[] attributeDefinitions)
			throws ParsingException, InvalidRelationSchemaDefinitionException, UnboundVariableException {
		super(input);
		if (attributeNames.length != attributeDefinitions.length)
			throw new InvalidRelationSchemaDefinitionException();
		Class<?>[] attributeTypes = new Class<?>[attributeDefinitions.length];
		evaluators = new ExpressionEvaluator[attributeDefinitions.length]; // construct evaluators (one for each output
																			// attribute)
		for (int i = 0; i < attributeTypes.length; i++) { // for each output attribute
			ArithmeticExpression expression = new ArithmeticExpression(attributeDefinitions[i]);
			evaluators[i] = new ExpressionEvaluator(expression, input.outputSchema()); // construct an evaluator
			try {
				attributeTypes[i] = expression.resultType(); // find the type of the output attribute
			} catch (UnboundVariableException e) {
				throw new ParsingException();
			}
		}
		this.outputSchema = new RelationSchema(attributeNames, attributeTypes); // construct the output schema
	}

	/**
	 * Returns the output schema of this {@code ProjectionOperator}.
	 * 
	 * @return the output schema of this {@code ProjectionOperator}
	 */
	@Override
	public RelationSchema outputSchema() {
		return outputSchema;
	}

	/**
	 * Determines whether or not this {@code ProjectionOperator} has the next output {@code Tuple}.
	 * 
	 * @return {@code true} if this {@code ProjectionOperator} has the next output {@code Tuple}; {@code false}
	 *         otherwise
	 */
	@Override
	public boolean hasNext() {
		return input.hasNext();
	}

	/**
	 * Returns the next output {@code Tuple} from this {@code ProjectionOperator}.
	 * 
	 * @return the next output {@code Tuple} from this {@code ProjectionOperator}
	 */
	@Override
	public Tuple next() {
		Tuple t = input.next(); // the next input tuple
		Object[] attributValues = new Object[outputSchema.size()];
		for (int i = 0; i < attributValues.length; i++) // for each output attribute
			try {
				attributValues[i] = evaluators[i].evaluate(t); // get an attribute value from the evaluator
			} catch (UnboundVariableException e) {
				e.printStackTrace();
			}
		try {
			return new Tuple(this.outputSchema, attributValues); // construct a tuple using the attribute values
		} catch (TypeException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Rewind this {@code ProjectionOperator}.
	 */
	@Override
	public void rewind() {
		input.rewind();
	}

}
