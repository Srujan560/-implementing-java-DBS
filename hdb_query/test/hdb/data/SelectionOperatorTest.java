package hdb.data;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import hdb.query.relational.Operator;
import hdb.query.relational.SelectionOperator;
import hdb.query.relational.TupleArrayReader;

/**
 * This program tests the {@code SelectionOperator} class.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 * 
 */
public class SelectionOperatorTest {

	/**
	 * A predicate.
	 */
	String predicate1 = "Temperature < .5";

	/**
	 * A predicate.
	 */
	String predicate2 = "Temperature > 9";

	/**
	 * (10 points) Tests {@link SelectionOperator#outputSchema()}.
	 * 
	 * @throws Exception
	 *             if an error occurs
	 */
	@Test
	public void outputSchema() throws Exception {
		Operator o = new TupleArrayReaderTest().tupleArrayReader();
		o.rewind(); // rewind the input operator
		SelectionOperator s = new SelectionOperator(o, predicate1);
		System.out.println("output schema: " + s.outputSchema());
		assertEquals("{ID=java.lang.Integer, Location=java.lang.Integer, Temperature=java.lang.Double}",
				s.outputSchema().toString());
	}

	/**
	 * (30 points) Tests {@link SelectionOperator#hasNext()} and {@link TupleArrayReader#next()}.
	 * 
	 * @throws Exception
	 *             if an error occurs
	 */
	@Test
	public void iteration() throws Exception {
		Operator o = new TupleArrayReaderTest().tupleArrayReader();
		int count = 0;
		while (o.hasNext()) {
			count++;
			o.next();
		}
		System.out.println("total number of input tuples: " + count);

		o.rewind(); // rewind the input operator
		iterate(new SelectionOperator(o, predicate1), 5);

		o.rewind(); // rewind the input operator
		iterate(new SelectionOperator(o, predicate2), 9);
	}

	/**
	 * (10 points) Tests {@link TupleArrayReader#rewind()}.
	 * 
	 * @throws Exception
	 *             if an error occurs
	 */
	@Test
	public void rewind() throws Exception {
		Operator o = new TupleArrayReaderTest().tupleArrayReader();
		SelectionOperator s = new SelectionOperator(o, predicate1);
		iterate(s, 5);
		iterate(s, 0);
		s.rewind();
		iterate(s, 5);
	}

	/**
	 * Iterates over the output {@code Tuple}s from the specified {@code SelectionOperator}.
	 * 
	 * @param s
	 *            a {@code SelectionOperator}
	 * @param targetCount
	 *            the correct number of output {@code Tuple}s
	 */
	protected void iterate(SelectionOperator s, int targetCount) {
		int count = 0;
		System.out.println("tuples satisfying predicate \"" + s.predicate() + "\": ");
		while (s.hasNext()) {
			count++;
			System.out.println(s.next());
		}
		assertEquals(targetCount, count);
		System.out.println("number of tuples satisfying predicate \"" + s.predicate() + "\": " + count);
	}

}
