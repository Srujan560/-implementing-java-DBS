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
public class DataObjectTestBasic {

	/**
	 * (1 points) Tests {@link DataObject#setAttribute(String, Object)}.
	 * 
	 * @throws Exception
	 *             if an error occurs
	 */
	@Test
	public void setAttributeBasic() throws Exception {
		DataObject object = createDataObject(123, "John");
		System.out.println(object);
		assertEquals("{0:ID=123, 1:Name=John}", object.toString());
		object = createDataObject(456, "Tom");
		System.out.println(object);
		assertEquals("{0:ID=456, 1:Name=Tom}", object.toString());
	}

	/**
	 * (1 points) Tests {@link DataObject#attributeValue(int[])}.
	 * 
	 * @throws Exception
	 *             if an error occurs
	 */
	@Test
	public void attributeValueBasic() throws Exception {
		DataObject object = createDataObject(123, "John");
		assertEquals(123, object.attributeValue(object.schema().attributeIndex("ID")));
		assertEquals("John", object.attributeValue(object.schema().attributeIndex("Name")));
		object = createDataObject(456, "Tom");
		assertEquals(456, object.attributeValue(object.schema().attributeIndex("ID")));
		assertEquals("Tom", object.attributeValue(object.schema().attributeIndex("Name")));
	}

	/**
	 * (2 points) Tests {@link DataObject#writeAttributes(ObjectOutputStream)} and
	 * {@link DataObject#DataObject(CollectionSchema, ObjectInputStream)}.
	 * 
	 * @throws Exception
	 *             if an error occurs
	 */
	@Test
	public void writeReadBasic() throws Exception {
		DataObject object = createDataObject(123, "John");
		byte[] byteArray = TupleTest.toByteArray((ObjectOutputStream out) -> {
			out.writeObject(object);
		}); // byte array obtained from ObjectOutputStream#writeObject(Tuple)
		System.out.println("size of data object " + object + " (standard Java serialization): " + byteArray.length);
		byteArray = TupleTest.toByteArray((ObjectOutputStream out) -> {
			object.writeAttributes(out);
		}); // byte array obtained from Tuple#writeAttributes(ObjectOutputStream)
		System.out.println("size of data object " + object + " (custom serialization): " + byteArray.length);
		ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(byteArray));
		DataObject newObject = new DataObject(object.schema(), in);
		assertFalse(object.toString().equals("{}"));
		assertEquals(object.toString(), newObject.toString());
	}

	/**
	 * Constructs a sample {@code DataObject} representing a person.
	 * 
	 * @param identifier
	 *            the identifier of a person
	 * @param name
	 *            the name of a person
	 * @return a sample {@code DataObject} representing a person
	 */
	DataObject createDataObject(int identifier, String name) {
		CollectionSchema schema = new CollectionSchema();
		DataObject object = new DataObject(schema);
		object.setAttribute("ID", identifier);
		object.setAttribute("Name", name);
		return object;
	}

}
