package hdb.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;

import hdb.data.nonrelational.CollectionSchema;
import hdb.data.nonrelational.DataObject;

/**
 * This program tests the {@code DataObject} class.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 * 
 */
public class DataObjectTestAdvanced {

	/**
	 * (1 points) Tests {@link DataObject#setAttribute(String, Object)}.
	 * 
	 * @throws Exception
	 *             if an error occurs
	 */
	@Test
	public void setAttributeAdvanced() throws Exception {
		DataObject object = createDataObjectAdvanced();
		System.out.println(object);
		assertEquals("{0:ID=123, 1:Name={0:FirstName=John, 1:LastName=Smith}}", object.toString());
	}

	/**
	 * (1 points) Tests {@link DataObject#attributeValue(int[])}.
	 * 
	 * @throws Exception
	 *             if an error occurs
	 */
	@Test
	public void attributeValueAdvanced() throws Exception {
		DataObject object = createDataObjectAdvanced();
		assertEquals("John", object.attributeValue(object.schema().attributeIndex("Name.FirstName")));
		assertEquals("Smith", object.attributeValue(object.schema().attributeIndex("Name.LastName")));
	}

	/**
	 * (2 points) Tests {@link DataObject#writeAttributes(ObjectOutputStream)} and
	 * {@link DataObject#DataObject(CollectionSchema, ObjectInputStream)}.
	 * 
	 * @throws Exception
	 *             if an error occurs
	 */
	@Test
	public void writeReadAdvanced() throws Exception {
		DataObject object = createDataObjectAdvanced();
		byte[] byteArray = TupleTest.toByteArray((ObjectOutputStream out) -> {
			out.writeObject(object);
		}); // byte array obtained from ObjectOutputStream#writeObject(Tuple)
		System.out.println("size of data object "+ object + " (standard Java serialization): " + byteArray.length);
		byteArray = TupleTest.toByteArray((ObjectOutputStream out) -> {
			object.writeAttributes(out);
		}); // byte array obtained from Tuple#writeAttributes(ObjectOutputStream)
		System.out.println("size of data object "+ object + " (custom serialization): " + byteArray.length);
		ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(byteArray));
		DataObject newObject = new DataObject(object.schema(), in);
		assertFalse(object.toString().equals("{}"));
		assertEquals(object.toString(), newObject.toString());
	}

	/**
	 * Constructs a sample {@code DataObject}.
	 * 
	 * @return a sample {@code DataObject}
	 */
	DataObject createDataObjectAdvanced() {
		CollectionSchema schema = new CollectionSchema();
		DataObject object = new DataObject(schema);
		object.setAttribute("ID", 123);
		object.setAttribute("Name.FirstName", "John");
		object.setAttribute("Name.LastName", "Smith");
		return object;
	}


}
