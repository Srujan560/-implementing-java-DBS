package hdb.query.relational;

import java.util.ArrayList;

import hdb.data.relational.RelationSchema;
import hdb.data.relational.Tuple;
import hdb.query.expression.LogicalExpression;
import hdb.query.expression.ParsingException;
import hdb.query.expression.UnboundVariableException;

/**
 * A {@code SelectionOperator} outputs, among the input {@code Tuple}s, those that satisfy a specified predicate.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public class SelectionOperator extends UnaryOperator {

	/**
	 * The {@code ExpressionEvaluator} for this {@code SelectionOperator}.
	 */
	protected ExpressionEvaluator evaluator;

	/**
	 * The predicate for this {@code SelectionOperator}.
	 */
	protected String predicate;
	private Tuple tupleArray[];

	/**
	 * Constructs a {@code SelectionOperator}.
	 * 
	 * @param input
	 *            the input {@code Operator} for the {@code SelectionOperator}
	 * @param predicate
	 *            the predicate for the {@code SelectionOperator}
	 * @throws ParsingException
	 *             if an error occurs while parsing the expressions
	 * @throws UnboundVariableException
	 *             if a variable in the predicate does not correspond to any attribute in the input schema of the
	 *             {@code SelectionOperator}
	 */
	public SelectionOperator(Operator input, String predicate) throws ParsingException, UnboundVariableException {
		super(input);
		this.predicate = predicate;
		evaluator = new ExpressionEvaluator(new LogicalExpression(predicate), input.outputSchema());
		// TODO complete this method (10 points)
//		ArrayList<Tuple> tupleList = new ArrayList<>();// Here we want to use array list to save data
		// let count how we will going to be reading 
		int count =0;
		while(input.hasNext()) {
			if(evaluator.evaluate(input.next())==Boolean.TRUE) {
//				System.out.println("Heollo I am here ");
//				System.out.println("AAAD "+input.next());
//				tupleList.add(this.input.next());// we add it to arraylist
				
				count++;//counter
			}
//			System.out.println("Heollo I am here 2222222222");
		}
//		this.classList= tupleList;
//		System.out.println(" count: "+count);
		this.tupleArray= new Tuple[count];// make the array size based on how may we will read
//		for(int x=0;x<this.tupleArray.length;x++) {
//		this.tupleArray[x]=this.input.next();
//	}
		
//		this.input.rewind();
		int temp=0;
		while(input.hasNext()) {
			if(evaluator.evaluate(input.next())==Boolean.TRUE) {
				this.tupleArray[temp]=input.next();
				temp++;
			}
		}
//		System.out.println(temp+ " asdasd asdad");

	}
	private int classCout=0;

	/**
	 * Returns the predicate of this {@code SelectionOperator}.
	 * 
	 * @return the predicate of this {@code SelectionOperator}
	 */
	public String predicate() {
		return predicate;
	}

	/**
	 * Returns the output schema of this {@code SelectionOperator}.
	 * 
	 * @return the output schema of this {@code SelectionOperator}
	 */
	@Override
	public RelationSchema outputSchema() {
		// TODO complete this method (5 points)
		return this.input.outputSchema();
	}

	/**
	 * Determines whether or not this {@code SelectionOperator} has the next output {@code Tuple}.
	 * 
	 * @return {@code true} if this {@code SelectionOperator} has the next output {@code Tuple}; {@code false} otherwise
	 */
	@Override
	public boolean hasNext() {
		// TODO complete this method (10 points)
		// here we check with array length and classCount to see it is in bound
		return this.tupleArray.length>this.classCout;
	}

	/**
	 * Returns the next output {@code Tuple} from this {@code SelectionOperator}.
	 * 
	 * @return the next output {@code Tuple} from this {@code SelectionOperator}
	 */
	@Override
	public Tuple next() {
		// TODO complete this method (10 points)
		// we save the current count 
		int currentCount = this.classCout;
		this.classCout++;// add 1 so next time we will get next tupleArray 
		return this.tupleArray[currentCount];// return current count
	}

	/**
	 * Rewind this {@code SelectionOperator}.
	 */
	@Override
	public void rewind() {
		// TODO complete this method (5 points)
		// Complete reset 
		this.classCout=0;
	}

}
