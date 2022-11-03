package hdb.query.relational;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import hdb.query.aggregate.AggregateFunction;
import hdb.data.relational.RelationSchema;
import hdb.data.relational.RelationSchema.InvalidAttributeIndexException;
import hdb.data.relational.Tuple;

/**
 * An {@code AggregageOperator} groups {@code Tuple}s and computes, for each group of {@code Tuple}s, a {@code Tuple}
 * representing that group of {@code Tuple}s.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public class AggregageOperator extends UnaryOperator {

	/**
	 * The names of grouping attributes used by this {@code AggregageOperator}.
	 */
	protected String[] groupingAttributeNames;

	/**
	 * The {@code Aggregator} used by this {@code AggregageOperator}.
	 */
	protected Aggregator aggregator;

	/**
	 * An {@code Iterator} from the {@code Aggregator} used by this {@code AggregageOperator}.
	 */
	protected Iterator<Tuple> i;

	/**
	 * The output schema of this {@code AggregageOperator}.
	 */
	private RelationSchema outputSchema;

	/**
	 * The types of the {@code AggregateFunction}s used by this {@code AggregageOperator}.
	 */
	private Class<?>[] aggregateFunctionTypes;

	/**
	 * The names of the attributes used by the {@code AggregateFunction}s of this {@code AggregageOperator}.
	 */
	private String[] aggregationAttributeNames;

	/**
	 * Constructs an {@code AggregageOperator}.
	 * 
	 * @param input
	 *            the input {@code Operator} for this {@code AggregageOperator}
	 * @param groupingAttributeNames
	 *            the names of grouping attributes
	 * @param aggregateFunctionTypes
	 *            the types of the {@code AggregateFunction}s used by the {@code AggregageOperator}
	 * @param aggregationAttributeNames
	 *            the names of the attributes used by the {@code AggregateFunction}s
	 * @throws InstantiationException
	 *             if the specified {@code AggregateFunction}s cannot be instantiated
	 * @throws InvalidAttributeIndexException
	 *             if an invalid attribute index is given to this {@code RelationSchema}
	 */
	public AggregageOperator(Operator input, String[] groupingAttributeNames, Class<?>[] aggregateFunctionTypes,
			String[] aggregationAttributeNames) throws InstantiationException, InvalidAttributeIndexException {
		super(input);
		this.groupingAttributeNames = groupingAttributeNames;
		this.aggregateFunctionTypes = aggregateFunctionTypes;
		this.aggregationAttributeNames = aggregationAttributeNames;
		AggregateFunction[] aggregateFunctions = createAggreateFunctions(input.outputSchema(), aggregateFunctionTypes,
				aggregationAttributeNames);
		this.outputSchema = createOutputSchema(aggregateFunctions);
	}

	/**
	 * Constructs the output schema of this {@code AggregageOperator}.
	 * 
	 * @param aggregateFunctions
	 *            {@code AggregateFunction}s
	 * @return the output schema of this {@code AggregageOperator}
	 * @throws InvalidAttributeIndexException
	 *             if an invalid attribute index is given to this {@code RelationSchema}
	 */
	RelationSchema createOutputSchema(AggregateFunction[] aggregateFunctions) throws InvalidAttributeIndexException {
		String[] attributeNames = new String[groupingAttributeNames.length + aggregateFunctions.length];
		Class<?>[] attributeTypes = new Class<?>[groupingAttributeNames.length + aggregateFunctions.length];
		//		RelationSchema inputSchema = inputSchema();
		// TODO complete this method (10 points) using this.groupingAttributeNames, this.aggregateFunctions, and
		// inputSchema
//		System.out.println("At the Start of mehtod");
		
		int temp=0;// index holder to count the next 
		for(int x=0;x<this.groupingAttributeNames.length;x++) {
		
//			System.out.println("My temp count is "+temp);
//			System.out.println("Demmo: "+x);
			
			attributeNames[temp]=this.groupingAttributeNames()[x];
			//inputSchema.attributeIndex(String) and inputSchema.attributeType(int) form PDF
			attributeTypes[temp]=this.inputSchema().attributeType(this.inputSchema().attributeIndex(attributeNames[x]));
			temp++;
		}
//		System.out.println(aggregateFunctions[0].toString()+" ????????????");
		for (int x=0;x<aggregateFunctions.length;x++) {
//			System.out.println("+++++++"+ aggregateFunctions[x].toString());
			attributeNames[temp]=aggregateFunctions[x].toString();// just turing into string
//			System.out.println("+sadsdssadasda "+ aggregateFunctions[x].valueType());
			attributeTypes[temp]=aggregateFunctions[x].valueType();// turing into class type
			temp++;
		}
		//		attributeNames[temp] = aggregateFunctions[x].valueType();
		//		for (int i=0;i<aggregateFunctions.length;i++) {
		//			System.out.println("fun string"+aggregateFunctions[i].toString());
		//			attributeTypes[temp]=aggregateFunctions[i].valueType();
		//			System.out.println("temp: "+temp+" aggerrrrrrrr"+aggregateFunctions.length);
		////			System.out.println("fun string"+aggregateFunctions[i].toString());
		//			
		//			temp++;
		//		}

		try {
			return new RelationSchema(attributeNames, attributeTypes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns the names of grouping attributes used by this {@code AggregageOperator}.
	 * 
	 * @return the names of grouping attributes used by this {@code AggregageOperator}
	 */
	public String[] groupingAttributeNames() {
		return groupingAttributeNames;
	}

	/**
	 * Returns the {@code AggregateFunction}s used by this {@code AggregageOperator}.
	 * 
	 * @return {@code AggregateFunction}s used by this {@code AggregageOperator}
	 * @throws InstantiationException
	 *             if the {@code AggreateFunction}s cannot be constructed properly
	 * @throws InvalidAttributeIndexException
	 *             if an invalid attribute index is given to this {@code RelationSchema}
	 */
	public AggregateFunction[] aggregateFunctions() throws InstantiationException, InvalidAttributeIndexException {
		return createAggreateFunctions(input.outputSchema(), aggregateFunctionTypes, aggregationAttributeNames);
	}

	/**
	 * Returns the output schema of this {@code AggregageOperator}.
	 * 
	 * @return the output schema of this {@code AggregageOperator}
	 */
	@Override
	public RelationSchema outputSchema() {
		return outputSchema;
	}

	/**
	 * Determines whether or not this {@code AggregageOperator} has the next output {@code Tuple}.
	 * 
	 * @return {@code true} if this {@code AggregageOperator} has the next output {@code Tuple}; {@code false} otherwise
	 */
	@Override
	public boolean hasNext() {
		if (i == null)
			prepare();
		return i.hasNext();
	}

	/**
	 * Returns the next output {@code Tuple} from this {@code AggregageOperator}.
	 * 
	 * @return the next output {@code Tuple} from this {@code AggregageOperator}
	 */
	@Override
	public Tuple next() {
		if (i == null)
			prepare();
		return i.next();
	}

	/**
	 * Rewind this {@code AggregageOperator}.
	 */
	@Override
	public void rewind() {
		prepare();
	}

	/**
	 * Prepares an iterator over the output {@code Tuple}s.
	 */
	protected void prepare() {
		if (aggregator == null)
			try {
				aggregator = new Aggregator(input, outputSchema, groupingAttributeNames, aggregateFunctionTypes,
						aggregationAttributeNames);
			} catch (InvalidAttributeIndexException e) {
				e.printStackTrace();
			}
		if (i == null)
			i = aggregator.iterator();
	}

	/**
	 * Constructs {@code AggreateFunction}s.
	 * 
	 * @param schema
	 *            a {@code RelationSchema}
	 * @param aggregateFunctions
	 *            the types of the {@code AggreateFunction}s to create
	 * @param aggregationAttributeNames
	 *            the names of the attributes that the {@code AggreateFunction}s will use
	 * @return an array of {@code AggreateFunction}s that are created
	 * @throws InstantiationException
	 *             if the {@code AggreateFunction}s cannot be constructed properly
	 * @throws InvalidAttributeIndexException
	 *             if an invalid attribute index is given to this {@code RelationSchema}
	 */
	public static AggregateFunction[] createAggreateFunctions(RelationSchema schema, Class<?>[] aggregateFunctions,
			String[] aggregationAttributeNames) throws InstantiationException, InvalidAttributeIndexException {
		AggregateFunction[] a = new AggregateFunction[aggregateFunctions.length];
		for (int i = 0; i < aggregateFunctions.length; i++) {
			String aggregationAttributeName = aggregationAttributeNames[i];
			Class<?> aggregationAttributeType = schema.attributeType(schema.attributeIndex(aggregationAttributeName));
			try {
				a[i] = (AggregateFunction) aggregateFunctions[i].getConstructors()[0]
						.newInstance(aggregationAttributeName, aggregationAttributeType);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | SecurityException e) {
				e.printStackTrace();
				throw new InstantiationException(e.getMessage());
			}
		}
		return a;
	}

}
