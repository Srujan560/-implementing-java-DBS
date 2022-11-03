package hdb.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import hdb.data.relational.RelationSchema;
import hdb.data.relational.RelationSchema.InvalidAttributeIndexException;

/**
 * This program tests the {@code RelationSchema} class.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 * 
 */
public class RelationSchemaTest {

	/**
	 * Attribute definitions for the first {@code RelationSchema}.
	 */
	static Object[] definition1 = new Object[] { new String[] { "ID", "Temperature" },
			new Class<?>[] { Integer.class, Double.class } };

	/**
	 * Attribute definitions for the second {@code RelationSchema}.
	 */
	static Object[] definition2 = new Object[] { new String[] { "ID", "Name", "Address" },
			new Class<?>[] { Integer.class, String.class, String.class } };

	/**
	 * Attribute definitions for {@code RelationSchema}s.
	 */
	static Object[] definitions = new Object[] { definition1, definition2 };

	/**
	 * (10 points) Tests {@link RelationSchema#size()}.
	 * 
	 * @throws Exception
	 *             if an error occurs
	 */
	@Test
	public void size() throws Exception {
		for (Object definition : definitions) {
			String[] attributeNames = (String[]) ((Object[]) definition)[0];
			Class<?>[] attributeTypes = (Class<?>[]) ((Object[]) definition)[1];
			RelationSchema schema = new RelationSchema(attributeNames, attributeTypes);
			assertEquals(attributeNames.length, schema.size());
			System.out.println("schema: " + schema);
		}
	}

	/**
	 * (5 points) Tests {@link RelationSchema#attributeName(int)}.
	 * 
	 * @throws Exception
	 *             if an error occurs
	 */
	@Test
	public void attributeName() throws Exception {
		for (Object definition : definitions) {
			String[] attributeNames = (String[]) ((Object[]) definition)[0];
			Class<?>[] attributeTypes = (Class<?>[]) ((Object[]) definition)[1];
			RelationSchema schema = new RelationSchema(attributeNames, attributeTypes);
			for (int i = 0; i < attributeNames.length; i++)
				assertEquals(attributeNames[i], schema.attributeName(i));
			assertThrows(InvalidAttributeIndexException.class, () -> {
				schema.attributeName(-1);
			});
			assertThrows(InvalidAttributeIndexException.class, () -> {
				schema.attributeName(attributeNames.length);
			});
		}
	}

	/**
	 * (5 points) Tests {@link RelationSchema#attributeType(int)}.
	 * 
	 * @throws Exception
	 *             if an error occurs
	 */
	@Test
	public void attributeType() throws Exception {
		for (Object definition : definitions) {
			String[] attributeNames = (String[]) ((Object[]) definition)[0];
			Class<?>[] attributeTypes = (Class<?>[]) ((Object[]) definition)[1];
			RelationSchema schema = new RelationSchema(attributeNames, attributeTypes);
			for (int i = 0; i < attributeNames.length; i++)
				assertEquals(attributeTypes[i], schema.attributeType(i));
			assertThrows(InvalidAttributeIndexException.class, () -> {
				schema.attributeType(-1);
			});
			assertThrows(InvalidAttributeIndexException.class, () -> {
				schema.attributeType(attributeNames.length);
			});
		}
	}

	/**
	 * (10 points) Tests {@link RelationSchema#attributeIndex(String)}.
	 * 
	 * @throws Exception
	 *             if an error occurs
	 */
	@Test
	public void attributeIndex() throws Exception {
		for (Object definition : definitions) {
			String[] attributeNames = (String[]) ((Object[]) definition)[0];
			Class<?>[] attributeTypes = (Class<?>[]) ((Object[]) definition)[1];
			RelationSchema schema = new RelationSchema(attributeNames, attributeTypes);
			for (int i = 0; i < attributeNames.length; i++)
				assertEquals((Integer) i, schema.attributeIndex(new String(attributeNames[i])));
			assertEquals(null, schema.attributeIndex("?"));
		}
	}

	/**
	 * (10 points) Tests {@link RelationSchema#save(String)}.
	 * 
	 * @throws Exception
	 *             if an error occurs
	 */
	@Test
	public void save() throws Exception {
		for (Object definition : definitions) {
			String[] attributeNames = (String[]) ((Object[]) definition)[0];
			Class<?>[] attributeTypes = (Class<?>[]) ((Object[]) definition)[1];
			RelationSchema schema = new RelationSchema(attributeNames, attributeTypes);
			schema.save("test.schema");
			schema = null;
			schema = RelationSchema.createRelationSchema("test.schema");
			assertEquals(attributeNames.length, schema.size());
		}
	}

}
