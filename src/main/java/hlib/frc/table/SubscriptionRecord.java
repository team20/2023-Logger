package hlib.frc.table;

import java.util.Arrays;

public class SubscriptionRecord extends SubscriptionSchema {

	private static final long serialVersionUID = -113499538292712027L;
	long timestamp;
	Object value;

	public SubscriptionRecord(long timestamp, String tableName, String topicName, DataType dataType, Object value) {
		super(tableName, topicName, dataType);
		this.timestamp = timestamp;
		if (dataType == DataType.STRING)
			this.value = (String) value;
		else if (dataType == DataType.STRING_ARRAY)
			this.value = (String[]) value;
		else if (dataType == DataType.LONG)
			this.value = (long) value;
		else if (dataType == DataType.LONG_ARRAY)
			this.value = (long[]) value;
		else if (dataType == DataType.DOUBLE)
			this.value = (double) value;
		else if (dataType == DataType.DOUBLE_ARRAY)
			this.value = (double[]) value;
	}

	@Override
	public String toString() {
		return timestamp + "; " + tableName + "; " + topicName + "; " + toString(value);
	}

	public static String toString(Object value) {
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

	public long timestamp() {
		return timestamp;
	}

	public Object value() {
		return value;
	}

}
