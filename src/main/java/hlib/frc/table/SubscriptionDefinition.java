package hlib.frc.table;

import java.util.Iterator;
import java.util.LinkedList;

import com.fasterxml.jackson.databind.JsonNode;

public class SubscriptionDefinition extends SubscriptionSchema {

	private static final long serialVersionUID = -5944945827875818006L;

	Object defaultValue;

	public SubscriptionDefinition(String tableName, String topicName, DataType dataType, Object defaultValue) {
		super(tableName, topicName, dataType);
		this.defaultValue = defaultValue;
	}

	public static SubscriptionDefinition createSubscriptionDefinition(JsonNode n) {
		String tableName = n.path("table name").asText();
		String topicName = n.path("topic name").asText();
		DataType dataType = DataType.find(n.path("data type").asText());
		Object o = parse(dataType, n.path("default value"));
		if (o == null)
			return null;
		else
			return new SubscriptionDefinition(tableName, topicName, dataType, o);
	}

	private static Object parse(DataType dataType, JsonNode node) {
		if (dataType == DataType.STRING)
			return node.asText();
		else if (dataType == DataType.STRING_ARRAY)
			return toStringArray(node.elements());
		else if (dataType == DataType.LONG)
			return node.asLong();
		else if (dataType == DataType.LONG_ARRAY)
			return toLongArray(node.elements());
		else if (dataType == DataType.DOUBLE)
			return node.asDouble();
		else if (dataType == DataType.DOUBLE_ARRAY)
			return toDoubleArray(node.elements());
		return null;
	}

	public static double[] toDoubleArray(Iterator<JsonNode> elements) {
		LinkedList<Double> l = new LinkedList<Double>();
		while (elements.hasNext())
			l.add(elements.next().asDouble());
		double[] a = new double[l.size()];
		for (int i = 0; i < a.length; i++)
			a[i] = l.get(i);
		return a;
	}

	public static long[] toLongArray(Iterator<JsonNode> elements) {
		LinkedList<Long> l = new LinkedList<Long>();
		while (elements.hasNext())
			l.add(elements.next().asLong());
		long[] a = new long[l.size()];
		for (int i = 0; i < a.length; i++)
			a[i] = l.get(i);
		return a;
	}

	public static String[] toStringArray(Iterator<JsonNode> elements) {
		LinkedList<String> l = new LinkedList<String>();
		while (elements.hasNext())
			l.add(elements.next().asText());
		String[] a = new String[l.size()];
		for (int i = 0; i < a.length; i++)
			a[i] = l.get(i);
		return a;
	}

	public Object defaultValue() {
		return defaultValue;
	}

	@Override
	public String toString() {
		return super.toString() + "; " + toString(defaultValue);
	}

}
