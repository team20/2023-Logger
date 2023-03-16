package hlib.visualization;

import java.util.HashMap;
import java.util.Map;

import hlib.robot.SmartDashboard;
import javafx.scene.control.TableView;

/**
 * The {@code SmartDashboardImpl} class implements the {@code SmartDashboard} interface. Each {@code SmartDashboardImpl}
 * uses a {@code TableView}.
 * 
 * @author Jeong-Hyon Hwang (jhhbrown@gmail.com)
 */
public class SmartDashboardImpl implements SmartDashboard {

	/**
	 * The {@code TableView} used by this {@code SmartDashboardImpl}.
	 */
	TableView<Object[]> tableView;

	/**
	 * The mapping that associates each key to the index representing a row in the {@code TableView}.
	 */
	Map<String, Integer> key2index = new HashMap<String, Integer>();

	ValueChangeEvent valueChangeEvent;

	/**
	 * Constructs a {@code SmartDashboardImpl}.
	 * 
	 * @param tableView
	 *            a {@code TableView} to be used by the {@code SmartDashboardImpl}
	 */
	public SmartDashboardImpl(TableView<Object[]> tableView) {
		this.tableView = tableView;
	}

	/**
	 * Puts an int value in this {@code SmartDashboard}.
	 * 
	 * @param key
	 *            the key to be assigned to
	 * @param value
	 *            the value that will be assigned
	 * @return {@code false} if the table key already exists with a different type; {@code true} otherwise
	 */
	@Override
	public boolean putInt(String key, long value) {
		return put(key, value);
	}

	/**
	 * Puts an int array in this {@code SmartDashboard}.
	 * 
	 * @param key
	 *            the key to be assigned to
	 * @param value
	 *            the value that will be assigned
	 * @return {@code false} if the table key already exists with a different type; {@code true} otherwise
	 */
	@Override
	public boolean putIntArray(String key, long[] value) {
		return put(key, value);
	}

	/**
	 * Puts a number in this {@code SmartDashboard}.
	 * 
	 * @param key
	 *            the key to be assigned to
	 * @param value
	 *            the value that will be assigned
	 * @return {@code false} if the table key already exists with a different type; {@code true} otherwise
	 */
	@Override
	public boolean putNumber(String key, double value) {
		return put(key, value);
	}

	/**
	 * Puts a number array in this {@code SmartDashboard}.
	 * 
	 * @param key
	 *            the key to be assigned to
	 * @param value
	 *            the value that will be assigned
	 * @return {@code false} if the table key already exists with a different type; {@code true} otherwise
	 */
	@Override
	public boolean putNumberArray(String key, double[] value) {
		return put(key, value);
	}

	/**
	 * Puts a {@code String} in this {@code SmartDashboard}.
	 * 
	 * @param key
	 *            the key to be assigned to
	 * @param value
	 *            the value that will be assigned
	 * @return {@code false} if the table key already exists with a different type; {@code true} otherwise
	 */
	@Override
	public boolean putString(String key, String value) {
		return put(key, value);
	}

	/**
	 * Puts a {@code String} array in this {@code SmartDashboard}.
	 * 
	 * @param key
	 *            the key to be assigned to
	 * @param value
	 *            the value that will be assigned
	 * @return {@code false} if the table key already exists with a different type; {@code true} otherwise
	 */
	@Override
	public boolean putStringArray(String key, String[] value) {
		return put(key, value);
	}

	/**
	 * Puts a value in this {@code SmartDashboard}.
	 * 
	 * @param key
	 *            the key to be assigned to
	 * @param value
	 *            the value that will be assigned
	 * @return {@code false} if the table key already exists with a different type; {@code true} otherwise
	 */
	protected boolean put(String key, Object value) {
		if (valueChangeEvent != null)
			valueChangeEvent.changed(key, value);
		Integer index = key2index.get(key);
		if (index == null) {
			index = key2index.size();
			key2index.put(key, index);
			tableView.getItems().add(index, new Object[] { key, value });
		} else {
			Object[] v = tableView.getItems().get(index);
			tableView.getItems().set(index, new Object[] { key, value });
			if ((v == null && value == null) || (v != null && value != null && v.getClass() != value.getClass()))
				return false;
		}
		return false;
	}

	/**
	 * Returns the int value that the key maps to.
	 * 
	 * @param key
	 *            the key
	 * @param defaultValue
	 *            the value to be returned if no value is found
	 * @return the value associated with the given key or the given default value if there is no value associated with
	 *         the key
	 */
	@Override
	public long getInt(String key, long defaultValue) {
		Integer index = key2index.get(key);
		if (index == null)
			return defaultValue;
		Object[] v = tableView.getItems().get(index);
		return ((Long) v[1]).longValue();
	}

	/**
	 * Returns the int array that the key maps to.
	 * 
	 * @param key
	 *            the key
	 * @param defaultValue
	 *            the value to be returned if no value is found
	 * @return the value associated with the given key or the given default value if there is no value associated with
	 *         the key
	 */
	@Override
	public long[] getIntArray(String key, long[] defaultValue) {
		Integer index = key2index.get(key);
		if (index == null)
			return defaultValue;
		Object[] v = tableView.getItems().get(index);
		return (long[]) v[1];
	}

	/**
	 * Returns the number that the key maps to.
	 * 
	 * @param key
	 *            the key
	 * @param defaultValue
	 *            the value to be returned if no value is found
	 * @return the value associated with the given key or the given default value if there is no value associated with
	 *         the key
	 */
	@Override
	public double getNumber(String key, double defaultValue) {
		Integer index = key2index.get(key);
		if (index == null)
			return defaultValue;
		Object[] v = tableView.getItems().get(index);
		return ((Double) v[1]).doubleValue();
	}

	/**
	 * Returns the number array that the key maps to.
	 * 
	 * @param key
	 *            the key
	 * @param defaultValue
	 *            the value to be returned if no value is found
	 * @return the value associated with the given key or the given default value if there is no value associated with
	 *         the key
	 */
	@Override
	public double[] getNumberArray(String key, double[] defaultValue) {
		Integer index = key2index.get(key);
		if (index == null)
			return defaultValue;
		Object[] v = tableView.getItems().get(index);
		return (double[]) v[1];
	}

	/**
	 * Returns the {@code String} that the key maps to.
	 * 
	 * @param key
	 *            the key
	 * @param defaultValue
	 *            the value to be returned if no value is found
	 * @return the value associated with the given key or the given default value if there is no value associated with
	 *         the key
	 */
	@Override
	public String getString(String key, String defaultValue) {
		Integer index = key2index.get(key);
		if (index == null)
			return defaultValue;
		Object[] v = tableView.getItems().get(index);
		return (String) v[1];
	}

	/**
	 * Returns the {@code String} array that the key maps to.
	 * 
	 * @param key
	 *            the key
	 * @param defaultValue
	 *            the value to be returned if no value is found
	 * @return the value associated with the given key or the given default value if there is no value associated with
	 *         the key
	 */
	@Override
	public String[] getStringArray(String key, String[] defaultValue) {
		Integer index = key2index.get(key);
		if (index == null)
			return defaultValue;
		Object[] v = tableView.getItems().get(index);
		return (String[]) v[1];
	}

	public static interface ValueChangeEvent {

		void changed(String topicName, Object value);
	}

	public void subscribe(ValueChangeEvent valueChangeEvent) {
		this.valueChangeEvent = valueChangeEvent;
	}

}