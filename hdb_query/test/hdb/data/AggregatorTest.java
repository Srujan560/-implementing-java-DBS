package hdb.data;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import hdb.data.relational.Tuple;
import hdb.query.aggregate.Count;
import hdb.query.aggregate.Maximum;
import hdb.query.aggregate.Minimum;
import hdb.query.relational.AggregageOperator;
import hdb.query.relational.Operator;

/**
 * This program tests the {@code Aggregator} class.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 * 
 */
public class AggregatorTest {

	/**
	 * (10 points) Tests {@link Aggregator#Aggregator(Operator, RelationSchema, String[], Class<?>[], String[] )},
	 * {@link Aggregator#iterator()}, {@link AggregageOperator#hasNext()}, and {@link AggregageOperator#next()}.
	 * 
	 * @throws Exception
	 *             if an error occurs
	 */
	@Test
	public void iteration() throws Exception {
		Operator o = new TupleArrayReaderTest().tupleArrayReader();

		System.out.println("input schema: " + o.outputSchema());
		AggregageOperator a = new AggregageOperator(o, new String[] { "Location" }, new Class<?>[] { Minimum.class },
				new String[] { "Temperature" });
		System.out.println("grouping attributes: " + Arrays.toString(a.groupingAttributeNames()));
		System.out.println("aggregate functions: " + Arrays.toString(a.aggregateFunctions()));
		System.out.println("output schema: " + a.outputSchema());

		int i = 0;
		while (a.hasNext()) {
			Tuple t = a.next();
			System.out.println(t);
			assertEquals("[" + i + ", " + i * 1.0 + "]", t.toString());
			i++;
		}

		o.rewind(); // rewind the input operator
		a = new AggregageOperator(o, new String[] { "Location" }, new Class<?>[] { Maximum.class, Count.class },
				new String[] { "Temperature", "Temperature" });
		System.out.println("grouping attributes: " + Arrays.toString(a.groupingAttributeNames()));
		System.out.println("aggregate functions: " + Arrays.toString(a.aggregateFunctions()));
		System.out.println("output schema: " + a.outputSchema());
		assertEquals(
				"{Location=java.lang.Integer, Maximum(Temperature)=java.lang.Double, Count(Temperature)=java.lang.Integer}",
				a.outputSchema().toString());
		i = 0;
		while (a.hasNext()) {
			Tuple t = a.next();
			System.out.println(t);
			assertEquals("[" + i + ", " + (i + .9) + ", 10]", t.toString());
			i++;
		}
	}

}
