package hlib.frc.table;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import hlib.frc.table.SubscriptionSchema.DataType;

/**
 * @author Jeong-Hyon Hwang (jhhbrown@gmail.com)
 * @author Andrew Hwang (u.andrew.h@gmail.com)
 */
public class ReaderOperator extends Operator {

	SubscriptionMap map;

	BufferedReader reader;

	public ReaderOperator(String fileName, Iterable<SubscriptionDefinition> schemas, Operator... nextOperators)
			throws FileNotFoundException {
		super(nextOperators);
		reader = new BufferedReader(new FileReader(fileName));
		map = new SubscriptionMap(schemas);
	}

	public void run() {
		try {
			String line;
			while ((line = reader.readLine()) != null)
				try {
					process(createRecord(line));
				} catch (Exception ee) {
				}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected SubscriptionRecord createRecord(String line) {
		String[] tokens = line.split(";");
		String tableName = tokens[1].trim();
		String topicName = tokens[2].trim();
		SubscriptionSchema schema = schema(tableName, topicName);
		if (schema == null)
			return null;
		return new SubscriptionRecord(Long.parseLong(tokens[0]), tableName, topicName, schema.dataType(),
				value(schema.dataType(), tokens[3].trim()));
	}

	private Object value(DataType dataType, String value) {
		if (dataType == DataType.STRING)
			return value;
		else if (dataType == DataType.STRING_ARRAY)
			return stringArray(value);
		else if (dataType == DataType.LONG)
			return Long.parseLong(value);
		else if (dataType == DataType.LONG_ARRAY)
			return longArray(value);
		else if (dataType == DataType.DOUBLE)
			return Double.parseDouble(value);
		else if (dataType == DataType.DOUBLE_ARRAY)
			return doubleArray(value);
		return null;
	}

	protected SubscriptionDefinition schema(String tableName, String topicName) {
		HashMap<String, SubscriptionDefinition> m = map.get(tableName);
		if (m == null)
			return null;
		return m.get(topicName);
	}

	public static String[] stringArray(String value) {
		value = value.substring(1, value.length() - 1);
		String[] tokens = value.split(",");
		String[] a = new String[tokens.length];
		for (int i = 0; i < tokens.length; i++)
			a[i] = tokens[i].trim();
		return a;
	}

	public static long[] longArray(String value) {
		value = value.substring(1, value.length() - 1);
		String[] tokens = value.split(",");
		long[] a = new long[tokens.length];
		for (int i = 0; i < tokens.length; i++)
			a[i] = Long.parseLong(tokens[i]);
		return a;
	}

	public static double[] doubleArray(String value) {
		value = value.substring(1, value.length() - 1);
		String[] tokens = value.split(",");
		double[] a = new double[tokens.length];
		for (int i = 0; i < tokens.length; i++)
			a[i] = Double.parseDouble(tokens[i]);
		return a;
	}

	@Override
	protected void process(SubscriptionRecord record) {
		if (record != null)
			output(record);
	}

}
