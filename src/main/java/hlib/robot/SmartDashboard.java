package hlib.robot;

/**
 * A {@code SmartDashboard} can display information regarding a {@code Robot}.
 * 
 * @author Jeong-Hyon Hwang (jhhbrown@gmail.com)
 * @author Andrew Hwang (u.andrew.h@gmail.com)
 */
public interface SmartDashboard {

	/**
	 * Puts an int value in this {@code SmartDashboard}.
	 * 
	 * @param key
	 *            the key to be assigned to
	 * @param value
	 *            the value that will be assigned
	 * @return {@code false} if the table key already exists with a different type; {@code true} otherwise
	 */
	public boolean putInt(String key, long value);

	/**
	 * Puts an int array in this {@code SmartDashboard}.
	 * 
	 * @param key
	 *            the key to be assigned to
	 * @param value
	 *            the value that will be assigned
	 * @return {@code false} if the table key already exists with a different type; {@code true} otherwise
	 */
	public boolean putIntArray(String key, long[] value);

	/**
	 * Puts a number in this {@code SmartDashboard}.
	 * 
	 * @param key
	 *            the key to be assigned to
	 * @param value
	 *            the value that will be assigned
	 * @return {@code false} if the table key already exists with a different type; {@code true} otherwise
	 */
	public boolean putNumber(String key, double value);

	/**
	 * Puts a number array in this {@code SmartDashboard}.
	 * 
	 * @param key
	 *            the key to be assigned to
	 * @param value
	 *            the value that will be assigned
	 * @return {@code false} if the table key already exists with a different type; {@code true} otherwise
	 */
	public boolean putNumberArray(String key, double[] value);

	/**
	 * Puts a {@code String} in this {@code SmartDashboard}.
	 * 
	 * @param key
	 *            the key to be assigned to
	 * @param value
	 *            the value that will be assigned
	 * @return {@code false} if the table key already exists with a different type; {@code true} otherwise
	 */
	public boolean putString(String key, String value);

	/**
	 * Puts a {@code String} array in this {@code SmartDashboard}.
	 * 
	 * @param key
	 *            the key to be assigned to
	 * @param value
	 *            the value that will be assigned
	 * @return {@code false} if the table key already exists with a different type; {@code true} otherwise
	 */
	public boolean putStringArray(String key, String[] value);

	/**
	 * Returns the int value that the key maps to.
	 * 
	 * @param key
	 *            the key to look up
	 * @param defaultValue
	 *            the value to be returned if no value is found
	 * @return the value associated with the given key or the given default value if there is no value associated with
	 *         the key
	 */
	public long getInt(String key, long defaultValue);

	/**
	 * Returns the int array that the key maps to.
	 * 
	 * @param key
	 *            the key to look up
	 * @param defaultValue
	 *            the value to be returned if no value is found
	 * @return the value associated with the given key or the given default value if there is no value associated with
	 *         the key
	 */
	public long[] getIntArray(String key, long[] defaultValue);

	/**
	 * Returns the number that the key maps to.
	 * 
	 * @param key
	 *            the key to look up
	 * @param defaultValue
	 *            the value to be returned if no value is found
	 * @return the value associated with the given key or the given default value if there is no value associated with
	 *         the key
	 */
	public double getNumber(String key, double defaultValue);

	/**
	 * Returns the number array that the key maps to.
	 * 
	 * @param key
	 *            the key to look up
	 * @param defaultValue
	 *            the value to be returned if no value is found
	 * @return the value associated with the given key or the given default value if there is no value associated with
	 *         the key
	 */
	public double[] getNumberArray(String key, double[] defaultValue);

	/**
	 * Returns the {@code String} that the key maps to.
	 * 
	 * @param key
	 *            the key to look up
	 * @param defaultValue
	 *            the value to be returned if no value is found
	 * @return the value associated with the given key or the given default value if there is no value associated with
	 *         the key
	 */
	public String getString(String key, String defaultValue);

	/**
	 * Returns the {@code String} array that the key maps to.
	 * 
	 * @param key
	 *            the key to look up
	 * @param defaultValue
	 *            the value to be returned if no value is found
	 * @return the value associated with the given key or the given default value if there is no value associated with
	 *         the key
	 */
	public String[] getStringArray(String key, String[] defaultValue);

}
