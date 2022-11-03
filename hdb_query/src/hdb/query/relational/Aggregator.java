package hdb.query.relational;

import java.util.ArrayList;
import java.util.Iterator;

import hdb.data.relational.RelationSchema;
import hdb.data.relational.RelationSchema.InvalidAttributeIndexException;
import hdb.data.relational.Tuple;

/**
 * An {@code Aggregator} groups all {@code Tuple}s from an input {@code Operator} and obtains, for each group of
 * {@code Tuple}s, a {@code Tuple} that represents/summarizes that group of {@code Tuple}s.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public class Aggregator {

	/**
	 * Constructs an {@code Aggregator}.
	 * 
	 * @param input
	 *            the input {@code Operator} for the {@code Aggregator}
	 * @param outputSchema
	 *            the output schema for the {@code Aggregator}
	 * @param groupingAttributeNames
	 *            the names of the grouping attributes
	 * @param aggregateFunctionTypes
	 *            the types of the {@code AggregateFunction}s used by the {@code Aggregator}
	 * @param aggregationAttributeNames
	 *            the names of attributes used by the {@code AggregateFunction}s
	 * @throws InvalidAttributeIndexException
	 *             if an invalid attribute index is given to this {@code RelationSchema}
	 */
	ArrayList<Tuple> tupleList = new ArrayList<>();
	public Aggregator(Operator input, RelationSchema outputSchema, String[] groupingAttributeNames,
			Class<?>[] aggregateFunctionTypes, String[] aggregationAttributeNames) throws InvalidAttributeIndexException {
		// TODO complete this method (5 points)

		
		
		
		while(input.hasNext()) {
			//System.out.println();
			Tuple tempTuple = input.next();// we save our tuple
			tupleList.add(tempTuple);// he add to arraylist 
			System.out.println("List with id, Location, Minium"+ tempTuple.toString());// test out to see what printing out 
			//x++;
		}
		
		
		
		
//		while(input.hasNext()) 
//		Tuple tempTuple = input.next();
//		t.add(tempTuple);
//		
//		
//	}
	}

	/**
	 * Returns an iterator over the output {@code Tuple}s of this {@code Aggregator}.
	 * 
	 * @return an iterator over the output {@code Tuple}s of this {@code Aggregator}
	 */
	public Iterator<Tuple> iterator() {
		// TODO complete this method (5 points)
//		this.tupleList.iterator().next();
//		this.tupleList.iterator().hasNext();
//		System.out.println("My asdas "+this.iterator().getClass().toString());
		// b/c we put in arraylist we easliy turn into arraylist
		return this.tupleList.iterator();
	}

}
