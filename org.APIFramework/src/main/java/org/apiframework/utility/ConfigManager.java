package org.apiframework.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigManager {

	private static final Properties properties = new Properties();
	private static final Logger logger = LogManager.getLogger(ConfigManager.class);

	static {
		try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("property.properties")) {
			if (input != null) {
				properties.load(input);
			} else {
				logger.error("property.properties file not found in classpath");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retrieves a property value by its key.
	 *
	 * @param key the property name
	 * @return the corresponding property value or null if not found
	 */
	public static String get(String key) {
		return properties.getProperty(key);
	}

	/**
	 * Retrieves a property value by key or returns a default value if the key is
	 * not found.
	 *
	 * @param key          the property name
	 * @param defaultValue the default value to return if key is missing
	 * @return the property value or the default
	 */
	public static String get(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}
}
