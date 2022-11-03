package hdb.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import org.junit.Test;

import hdb.data.relational.RelationSchema;
import hdb.data.relational.Tuple;
import hdb.data.relational.Tuple.TypeException;

/**
 * This program tests the {@code Tuple} class.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 * 
 */
public class TupleTest {

	/**
	 * Attribute names.
	 */
	static String[] attributeNames = new String[] { "ID", "Celsius" };

	/**
	 * Attribute types.
	 */
	static Class<?>[] attributeTypes = new Class<?>[] { Integer.class, Double.class };

	/**
	 * Attribute values.
	 */
	static Object[] attributeValues = new Object[] { 1, 5.0 };

	/**
	 * (10 points) Tests {@link Tuple#Tuple(RelationSchema, Object...)} and
	 * {@link Tuple#setAttribute(RelationSchema, int, Object)}.
	 * 
	 * @throws Exception
	 *             if an error occurs
	 */
	@Test
	public void setAttribute() throws Exception {
		RelationSchema schema = new RelationSchema(attributeNames, attributeTypes);
		Tuple t = new Tuple(schema, attributeValues);
		System.out.println("tuple: " + t);
		assertEquals(Arrays.toString(attributeValues), t.toString());
		Object result = null;
		try {
			t.setAttribute(0, "123"); // must throw a TypeException due to type mismatch
		}
		catch(Exception e) {
			result = e;
		}
		assertTrue(result instanceof TypeException);
	}

	/**
	 * (10 points) Tests {@link Tuple#writeAttributes(ObjectOutputStream)}.
	 * 
	 * @throws Exception
	 *             if an error occurs
	 */
	@Test
	public void writeAttributes() throws Exception {
		writeAttributes(200); // the amount of data written needs to be no larger than 200
	}

	/**
	 * (20 points) Tests {@link Tuple#write(Object, ObjectOutputStream)} and
	 * {@link Tuple#read(Class, ObjectInputStream)}.
	 * 
	 * @throws Exception
	 *             if an error occurs
	 */
	@Test
	public void writeRead() throws Exception {
		writeAttributes(50); // the amount of data written needs to be no larger than 50
	}

	/**
	 * Writes a {@code Tuple} to a {@code ByteArrayOutputStream} and then creates a new {@code Tuple} from that
	 * {@code ByteArrayOutputStream}.
	 * 
	 * @param threshold
	 *            a threshold for throwing an {@code Exception} indicating a failure if the amount of data written to
	 *            the {@code ByteArrayOutputStream} is larger than that threshold
	 * @throws Exception
	 *             if an error occurs
	 */
	void writeAttributes(int threshold) throws Exception {
		RelationSchema schema = new RelationSchema(attributeNames, attributeTypes);
		Tuple tuple = new Tuple(schema, attributeValues);
		byte[] byteArray = toByteArray((ObjectOutputStream out) -> {
			out.writeObject(tuple);
		}); // byte array obtained from ObjectOutputStream#writeObject(Tuple)
		System.out.println("size of tuple " + tuple + " (standard Java serialization): " + byteArray.length);
		byteArray = toByteArray((ObjectOutputStream out) -> {
			tuple.writeAttributes(out);
		}); // byte array obtained from Tuple#writeAttributes(ObjectOutputStream)
		System.out.println("size of tuple " + tuple + " (custom serialization): " + byteArray.length);
		ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(byteArray));
		Tuple newTuple = new Tuple(schema, in);
		assertEquals(tuple.toString(), newTuple.toString());
		assertTrue(byteArray.length <= threshold);
	}

	/**
	 * A {@code WritingTask} represents a task that can possibly write data to an {@code ObjectOutputStream}.
	 * 
	 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
	 */
	static public interface WritingTask {
		/**
		 * Performs a task that can possibly write data to an {@code ObjectOutputStream}.
		 * 
		 * @param out
		 *            an {@code ObjectOutputStream}
		 * @throws Exception
		 *             if an error occurs
		 */
		void run(ObjectOutputStream out) throws Exception;
	}

	/**
	 * Returns the size of data written to an {@code ObjectOutputStream} by the specified {@code WritingTask}.
	 * 
	 * @param task
	 *            a {@code WritingTask}
	 * @return the size of data written to an {@code ObjectOutputStream} by the specified {@code WritingTask}
	 * @throws Exception
	 *             if an error occurs
	 */
	static public byte[] toByteArray(WritingTask task) throws Exception {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(b);
		task.run(out);
		out.close();
		return b.toByteArray();
	}

}
