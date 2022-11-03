package hdb.data.nonrelational;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 * A {@code CollectionSchema} represents the schema of a non-relational collection.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public class CollectionSchema implements java.io.Serializable {

	/**
	 * An {@code InvalidAttributeIndexException} is thrown if an invalid attribute index is given to a
	 * {@code CollectionSchema}.
	 * 
	 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
	 */
	public static class InvalidAttributeIndexException extends Exception {

		/**
		 * Automatically generated serial version UID.
		 */
		private static final long serialVersionUID = -7371027889948222798L;

	}

	/**
	 * Automatically generated serial version UID.
	 */
	private static final long serialVersionUID = -100208280521684870L;

	/**
	 * A {@code HashMap} that associates each attribute name with an attribute index.
	 */
	HashMap<String, Integer> name2index = new HashMap<String, Integer>();

	/**
	 * A {@code HashMap} that associates each attribute index with an attribute name.
	 */
	HashMap<Integer, String> index2name = new HashMap<Integer, String>();

	/**
	 * A {@code HashMap} that associates attribute indices with {@code CollectionSchema}s.
	 */
	HashMap<Integer, CollectionSchema> index2schema = new HashMap<Integer, CollectionSchema>();

	/**
	 * Constructs a {@code CollectionSchema}.
	 */
	public CollectionSchema() {
	}

	/**
	 * Returns a string representation of this {@code CollectionSchema}.
	 */
	@Override
	public String toString() {
		String s = "{";
		for (Entry<Integer, String> e : index2name.entrySet()) {
			s += (s.length() == 1 ? "" : ", ") + e.getKey() + "=" + e.getValue();
			CollectionSchema c = index2schema.get(e.getKey());
			if (c != null)
				s += c.toString();
		}
		return s + "}";
	}

	/**
	 * Returns the name of the specified attribute.
	 * 
	 * @param attributeIndex
	 *            the index of an attribute
	 * @return the name of the specified attribute
	 */
	public String attributeName(int attributeIndex) {
		return index2name.get(attributeIndex);//just return string based on the index
	}

	/**
	 * Returns the subschema associated with the specified attribute.
	 * 
	 * @param attributeIndex
	 *            the index of an attribute
	 * @return the subschema associated with the specified attribute
	 */
	public CollectionSchema subschema(int attributeIndex) {
		return index2schema.get(attributeIndex);// just returning based on index 
	}

	/**
	 * Returns the index of the specified attribute in this {@code CollectionSchema} (needs to register that attribute
	 * if no such attribute has been registered in this {@code CollectionSchema}).
	 * 
	 * @param attributeName
	 *            the name of an attribute
	 * @return the index of the specified attribute in this {@code CollectionSchema}
	 */
	public int[] attributeIndex(String attributeName) {
		// TODO complete this method
		//int num =name2index.get(attributeName);// we can not use int == null
		// here we want to see if get null results 
		if(name2index.get(attributeName)==null) {
			//System.out.println("I am inside here "+ this.name2index.size());
			int index = this.name2index.size();
			//System.out.println("sad "+ index);
			
			name2index.put(attributeName, index);// We always want to add to next index
			this.index2name.put(index, attributeName);// and doing the other way around
//			System.out.println("+ + + + + "+ this.name2index.size());
//			System.out.println(" - --  -- -  -"+ this.name2index.get(attributeName));
//			System.out.println("******* "+ this.name2index);
//			System.out.println("%%%%%%%*" + this.index2name);
			
			
			//return new int[index];
		}
		//System.out.println(" - --  -- -  - "+ this.name2index.get(attributeName));
		
		return  new int[]{name2index.get(attributeName)};// returing as array 
	}

	/**
	 * Returns the name of the specified attribute.
	 * 
	 * @param attributeIndex
	 *            the index of an attribute
	 * @return the name of the specified attribute
	 * @throws InvalidAttributeIndexException
	 *             if the specified attribute index is invalid
	 */
	public String attributeName(int[] attributeIndex) throws InvalidAttributeIndexException {
		// TODO complete this method
		for(int x=0;x<attributeIndex.length;x++) {// loop based on size
			if(attributeIndex[x]<0||attributeIndex[x] >=this.name2index.size())// if index is out of range 
				throw new InvalidAttributeIndexException();// than throw an exactions 
		}
		return this.index2name.get(attributeIndex[0]);// return 
	}

}
