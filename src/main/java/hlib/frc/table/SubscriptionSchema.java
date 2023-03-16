package hlib.frc.table;

import java.util.Arrays;

public class SubscriptionSchema implements java.io.Serializable {

	private static final long serialVersionUID = 3397301513509326531L;

	public static enum DataType {

		STRING("String"), STRING_ARRAY("String[]"), LONG("long"), LONG_ARRAY("long[]"), DOUBLE("double"), DOUBLE_ARRAY(
				"double[]");

		String string;

		DataType(String string) {
			this.string = string;
		}

		static DataType find(String s) {
			for (DataType value : DataType.values())
				if (value.matches(s))
					return value;
			return null;
		}

		boolean matches(String s) {
			return this.string.equals(s);
		}

		public String toString() {
			return string;
		}

	};

	String tableName;

	String topicName;

	DataType dataType;

	public SubscriptionSchema(String tableName, String topicName, DataType dataType) {
		this.tableName = tableName;
		this.topicName = topicName;
		this.dataType = dataType;
	}

	@Override
	public String toString() {
		return tableName + "; " + topicName;
	}

	public static String toString(Object value) {
		if (value instanceof String)
			return "\"" + value + "\"";
		if (value instanceof int[])
			return Arrays.toString((int[]) value);
		if (value instanceof double[])
			return Arrays.toString((double[]) value);
		if (value instanceof String[]) {
			String[] a = (String[]) value;
			String s = "[";
			for (int i = 0; i < a.length; i++)
				s += ((i == 0 ? "" : ", ") + toString(a[i]));
			return s + "]";
		}
		return "" + value;
	}

	public String tableName() {
		return tableName;
	}

	public String topicName() {
		return topicName;
	}

	public DataType dataType() {
		return dataType;
	}

}
