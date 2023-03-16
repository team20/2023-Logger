package frc.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.wpi.first.networktables.DoubleArraySubscriber;
import edu.wpi.first.networktables.DoubleArrayPublisher;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.IntegerArraySubscriber;
import edu.wpi.first.networktables.IntegerPublisher;
import edu.wpi.first.networktables.IntegerArrayPublisher;
import edu.wpi.first.networktables.IntegerSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.Publisher;
import edu.wpi.first.networktables.StringArrayPublisher;
import edu.wpi.first.networktables.StringArraySubscriber;
import edu.wpi.first.networktables.StringPublisher;
import edu.wpi.first.networktables.StringSubscriber;
import hlib.frc.table.LoggingOperator;
import hlib.frc.table.Operator;
import hlib.frc.table.ServerOperator;
import hlib.frc.table.SubscriptionDefinition;
import hlib.frc.table.SubscriptionList;
import hlib.frc.table.SubscriptionMap;
import hlib.frc.table.SubscriptionRecord;
import hlib.frc.table.SubscriptionSchema.DataType;

/**
 * A {@code Logger} logs the changes of some entries in a {@code NetworkTable}.
 * 
 * @author Jeong-Hyon Hwang (jhhbrown@gmail.com)
 */
public class Logger extends Operator {

	/**
	 * The path to the "deploy" directory.
	 */
	public static String deployPath = "." + File.separator + "src" + File.separator + "main" + File.separator
			+ "deploy";

	static {
		// System.loadLibrary("ntcorejni");
	}

	/**
	 * The {@code NetworkTableInstance} used by this {@code Logger} .
	 */
	NetworkTableInstance ntInstance = NetworkTableInstance.getDefault();
	
	SubscriptionMap subscriptionMap;

	HashMap<String, HashMap<String, Publisher>> publishers = new HashMap<String, HashMap<String, Publisher>>();
	
	/**
	 * Constructs a {@code Logger}.
	 * 
	 * @param serverName
	 *            the name of the server maintaining a {@code NetworkTable}
	 * @param subscriptions
	 *            {@code SubscriptionDefinition}s
	 * @param nextOperators
	 *            the next {@code Operator}s
	 * @throws FileNotFoundException
	 *             if a file cannot be found
	 */
	Logger(String serverName, Iterable<SubscriptionDefinition> subscriptions, Operator... nextOperators)
			throws FileNotFoundException {
		super(nextOperators);
		ntInstance.startClient4("visualizer");
		ntInstance.setServer(serverName);
		ntInstance.startDSClient(); // recommended if running on DS computer; this gets the robot IP from the DS
		System.out.println("NetworkTable subscriptions:");
		for (SubscriptionDefinition subscription : subscriptions)
			System.out.println(subscription);
		System.out.println();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("connections: " + Arrays.toString(ntInstance.getConnections()));

		for (SubscriptionDefinition subscription : subscriptions) {
			NetworkTable table = ntInstance.getTable(subscription.tableName());
			String tableName = subscription.tableName();
			String topicName = subscription.topicName();
			if (subscription.dataType() == DataType.STRING) {
				StringSubscriber s = table.getStringTopic(subscription.topicName())
						.subscribe((String) subscription.defaultValue());
				ntInstance.addListener(s, EnumSet.of(NetworkTableEvent.Kind.kValueAll), event -> {
					output(new SubscriptionRecord(System.currentTimeMillis(), tableName, topicName, DataType.STRING,
							event.valueData.value.getString()));
				});
			} else if (subscription.dataType() == DataType.STRING_ARRAY) {
				StringArraySubscriber s = table.getStringArrayTopic(subscription.topicName())
						.subscribe((String[]) subscription.defaultValue());
				ntInstance.addListener(s, EnumSet.of(NetworkTableEvent.Kind.kValueAll), event -> {
					output(new SubscriptionRecord(System.currentTimeMillis(), tableName, topicName,
							DataType.STRING_ARRAY, event.valueData.value.getStringArray()));
				});
			} else if (subscription.dataType() == DataType.LONG) {
				IntegerSubscriber s = table.getIntegerTopic(subscription.topicName())
						.subscribe((long) subscription.defaultValue());
				ntInstance.addListener(s, EnumSet.of(NetworkTableEvent.Kind.kValueAll), event -> {
					output(new SubscriptionRecord(System.currentTimeMillis(), tableName, topicName, DataType.LONG,
							event.valueData.value.getInteger()));
				});
			} else if (subscription.dataType() == DataType.LONG_ARRAY) {
				IntegerArraySubscriber s = table.getIntegerArrayTopic(subscription.topicName())
						.subscribe((long[]) subscription.defaultValue());
				ntInstance.addListener(s, EnumSet.of(NetworkTableEvent.Kind.kValueAll), event -> {
					output(new SubscriptionRecord(System.currentTimeMillis(), tableName, topicName, DataType.LONG_ARRAY,
							event.valueData.value.getIntegerArray()));
				});
			} else if (subscription.dataType() == DataType.DOUBLE) {
				DoubleSubscriber s = table.getDoubleTopic(subscription.topicName())
						.subscribe((double) subscription.defaultValue());
				ntInstance.addListener(s, EnumSet.of(NetworkTableEvent.Kind.kValueAll), event -> {
					output(new SubscriptionRecord(System.currentTimeMillis(), tableName, topicName, DataType.DOUBLE,
							event.valueData.value.getDouble()));
				});
			} else if (subscription.dataType() == DataType.DOUBLE_ARRAY) {
				DoubleArraySubscriber s = table.getDoubleArrayTopic(subscription.topicName())
						.subscribe((double[]) subscription.defaultValue());
				ntInstance.addListener(s, EnumSet.of(NetworkTableEvent.Kind.kValueAll), event -> {
					output(new SubscriptionRecord(System.currentTimeMillis(), tableName, topicName,
							DataType.DOUBLE_ARRAY, event.valueData.value.getDoubleArray()));
				});
			}

		}
		subscriptionMap = new SubscriptionMap(subscriptions);
	}

	@Override
	protected void process(SubscriptionRecord entry) {
		// System.out.println("Logger:  " + entry);
		if (entry.dataType() == DataType.STRING)
			process(entry.tableName(), entry.topicName(), entry.dataType(), (String) entry.value());
		else if (entry.dataType() == DataType.STRING_ARRAY)
			process(entry.tableName(), entry.topicName(), entry.dataType(), (String[]) entry.value());
		else if (entry.dataType() == DataType.LONG)
			process(entry.tableName(), entry.topicName(), entry.dataType(), (long) entry.value());
		else if (entry.dataType() == DataType.LONG_ARRAY)
			process(entry.tableName(), entry.topicName(), entry.dataType(), (long[]) entry.value());
		else if (entry.dataType() == DataType.DOUBLE)
			process(entry.tableName(), entry.topicName(), entry.dataType(), (double) entry.value());
		else if (entry.dataType() == DataType.DOUBLE_ARRAY)
			process(entry.tableName(), entry.topicName(), entry.dataType(), (double[]) entry.value());
	}

	void process(String tableName, String topicName, DataType dataType, String value) {
		StringPublisher publisher = publisher(tableName, topicName, dataType);
		publisher.set(value);
	}

	void process(String tableName, String topicName, DataType dataType, String[] value) {
		StringArrayPublisher publisher = publisher(tableName, topicName, dataType);
		publisher.set(value);
	}

	void process(String tableName, String topicName, DataType dataType, long value) {
		IntegerPublisher publisher = publisher(tableName, topicName, dataType);
		publisher.set(value);
	}

	void process(String tableName, String topicName, DataType dataType, long[] value) {
		IntegerArrayPublisher publisher = publisher(tableName, topicName, dataType);
		publisher.set(value);
	}

	void process(String tableName, String topicName, DataType dataType, double value) {
		DoublePublisher publisher = publisher(tableName, topicName, dataType);
		publisher.set(value);
	}

	void process(String tableName, String topicName, DataType dataType, double[] value) {
		DoubleArrayPublisher publisher = publisher(tableName, topicName, dataType);
		publisher.set(value);
	}

	@SuppressWarnings("unchecked")
	<P extends Publisher> P publisher(String tableName, String topicName, DataType dataType) {
		HashMap<String, Publisher> m = publishers.get(tableName);
		if (m == null) {
			m = new HashMap<String, Publisher>();
			publishers.put(tableName, m);
		}
		Publisher p = m.get(topicName);
		if (p == null) {
			if (dataType == DataType.STRING)
				p = ntInstance.getTable(tableName).getStringTopic(topicName).publish();
			else if (dataType == DataType.STRING_ARRAY)
				p = ntInstance.getTable(tableName).getStringArrayTopic(topicName).publish();
			else if (dataType == DataType.LONG)
				p = ntInstance.getTable(tableName).getIntegerTopic(topicName).publish();
			else if (dataType == DataType.LONG_ARRAY)
				p = ntInstance.getTable(tableName).getIntegerArrayTopic(topicName).publish();
			else if (dataType == DataType.DOUBLE)
				p = ntInstance.getTable(tableName).getDoubleTopic(topicName).publish();
			else if (dataType == DataType.DOUBLE_ARRAY)
				p = ntInstance.getTable(tableName).getDoubleArrayTopic(topicName).publish();
			m.put(topicName, p);
		}
		return (P)p;
	}

	/**
	 * Runs this {@code Logger} for the specified duration.
	 * 
	 * @param duration
	 *            the duration of logging in seconds
	 */
	public void run(double duration) {
		for (long i = 0; i < duration; i++)
			try {
				System.out.println("progress: " + i + "/" + duration);
				output(new SubscriptionRecord(System.currentTimeMillis(), "logger", "heartbeat", DataType.LONG, i));
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		ntInstance.disconnect();
		System.out.println("connections: " + Arrays.toString(ntInstance.getConnections()));
	}

	public static void main(String... args) throws IOException {
		String ipAddress = roboRioIPAddress(deployPath + File.separator + "configuration.json");
		if (ipAddress != null) {
			System.out.println("RoboRIO IP address: " + ipAddress);
			ServerOperator s = new ServerOperator(10000);
			Logger logger = new Logger(ipAddress,
					new SubscriptionList(deployPath + File.separator + "configuration.json"),
					new LoggingOperator(), s);
			s.addNextOperator(logger);
			logger.run(30 * 60);
			s.shutdown();
		}
	}

	static String roboRioIPAddress(String fileName) {
		try {
			JsonNode root = new ObjectMapper().readTree(new File(fileName));
			return root.path("RoboRIO IP address").asText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
