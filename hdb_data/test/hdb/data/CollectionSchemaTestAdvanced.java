package hdb.data;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import hdb.data.nonrelational.CollectionSchema;

/**
 * This program tests the {@code CollectionSchema} class.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 * 
 */
public class CollectionSchemaTestAdvanced {

	/**
	 * (2 points) Tests {@link CollectionSchema#attributeIndex(String)}.
	 * 
	 * @throws Exception
	 *             if an error occurs
	 */
	@Test
	public void attributeIndex() throws Exception {
		CollectionSchema schema = new CollectionSchema();
		int[] index = schema.attributeIndex("ID");
		assertEquals("[0]", Arrays.toString(index));
		System.out.println(schema);
		index = schema.attributeIndex("Name.FirstName");
		assertEquals("[1, 0]", Arrays.toString(index));
		System.out.println(schema);
		index = schema.attributeIndex("Name.LastName");
		assertEquals("[1, 1]", Arrays.toString(index));
		System.out.println(schema);
	}

	/**
	 * (1 points) Tests {@link CollectionSchema#attributeName(int[])}.
	 * 
	 * @throws Exception
	 *             if an error occurs
	 */
	@Test
	public void attributeName() throws Exception {
		CollectionSchema schema = new CollectionSchema();
		int[] index = schema.attributeIndex("ID");
		String name = schema.attributeName(index);
		assertEquals("ID", name);

		index = schema.attributeIndex("Name.FirstName");
		name = schema.attributeName(index);
		assertEquals("Name.FirstName", name);

		index = schema.attributeIndex("Name.LastName");
		name = schema.attributeName(index);
		assertEquals("Name.LastName", name);
	}

}
