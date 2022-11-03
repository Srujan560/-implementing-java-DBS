package hdb.data;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import hdb.query.aggregate.Minimum;
import hdb.query.relational.AggregageOperator;
import hdb.query.relational.Operator;

/**
 * This program tests the {@code AggregateOperator} class.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 * 
 */
public class AggregateOperatorTest {

	/**
	 * (5 points) Tests {@link AggregateOperator#outputSchema()}.
	 * 
	 * @throws Exception
	 *             if an error occurs
	 */
	@Test
	public void outputSchema() throws Exception {
		Operator o = new TupleArrayReaderTest().tupleArrayReader();

		System.out.println("input schema: " + o.outputSchema());
		AggregageOperator a = new AggregageOperator(o, new String[] { "Location" }, new Class<?>[] { Minimum.class },
				new String[] { "Temperature" });
		System.out.println("grouping attributes: " + Arrays.toString(a.groupingAttributeNames()));
		System.out.println("aggregate functions: " + Arrays.toString(a.aggregateFunctions()));
		System.out.println("output schema: " + a.outputSchema());
		assertEquals("{Location=java.lang.Integer, Minimum(Temperature)=java.lang.Double}",
				a.outputSchema().toString());
	}

}
