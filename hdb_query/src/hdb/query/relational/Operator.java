package hdb.query.relational;

import java.util.Iterator;

import hdb.data.relational.RelationSchema;
import hdb.data.relational.Tuple;

/**
 * An {@code Operator} processes {@code Tuple}s and produces {Tuple}s.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public abstract class Operator implements Iterator<Tuple> {

	/**
	 * Returns the output schema of this {@code Operator}.
	 * 
	 * @return the output schema of this {@code Operator}
	 */
	public abstract RelationSchema outputSchema();

	/**
	 * Rewind this {@code Operator}.
	 */
	public abstract void rewind();

}
