package hdb.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import hdb.data.relational.RelationSchema;
import hdb.data.relational.Tuple;
import hdb.query.relational.TupleArrayReader;

/**
 * This program tests the {@code TupleArrayReader} class.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 * 
 */
public class TupleArrayReaderTest {

	/**
	 * Attribute names.
	 */
	static String[] attributeNames = new String[] { "ID", "Location", "Temperature" };

	/**
	 * Attribute types.
	 */
	static Class<?>[] attributeTypes = new Class<?>[] { Integer.class, Integer.class, Double.class };

	/**
	 * A {@code RelationSchema}.
	 */
	protected RelationSchema schema;

	/**
	 * An array of {@code Tuple}s.
	 */
	protected Tuple[] tuples;

	{
		try {
			schema = new RelationSchema(attributeNames, attributeTypes);
			int n = 100;
			tuples = new Tuple[n];
			for (int i = 0; i < n; i++)
				tuples[i] = new Tuple(schema, i, i / 10, i / 10.0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * (20 points) Tests {@link TupleArrayReader#hasNext()} and {@link TupleArrayReader#next()}.
	 * 
	 * @throws Exception
	 *             if an error occurs
	 */
	@Test
	public void iteration() throws Exception {
		TupleArrayReader o = tupleArrayReader();
		iterate(o);
	}

	/**
	 * (10 points) Tests {@link TupleArrayReader#rewind()}.
	 * 
	 * @throws Exception
	 *             if an error occurs
	 */
	@Test
	public void rewind() throws Exception {
		TupleArrayReader o = tupleArrayReader();
		iterate(o);
		o.rewind();
		iterate(o);
	}

	/**
	 * Returns a {@code TupleArrayReader}.
	 * 
	 * @return a {@code TupleArrayReader}
	 */
	public TupleArrayReader tupleArrayReader() {
		return new TupleArrayReader(tuples, schema);
	}

	/**
	 * Iterates over the {@code Tuple}s from the specified {@code TupleArrayReader}.
	 * 
	 * @param r
	 *            a {@code TupleArrayReader}
	 */
	protected void iterate(TupleArrayReader r) {
		for (Tuple tuple : tuples) {
			assertTrue(r.hasNext());
			assertEquals(tuple, r.next());
		}
		assertFalse(r.hasNext());
	}

}
