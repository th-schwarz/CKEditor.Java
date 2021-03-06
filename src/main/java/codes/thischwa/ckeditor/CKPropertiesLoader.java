/*
 * CKEditor.java - Java backend of CKEditor (http://ckeditor.com). 
 * It provides a simple object for creating an editor instance.
 * Copyright (C) Thilo Schwarz
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package codes.thischwa.ckeditor;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Static object for managing the properties files. <p>
 * The files are loaded in the following order:
 * <ol>
 * <li>The default properties.</li>
 * <li>The user-defined properties <code>ckeditor.properties</code> if present.</li>
 * </ol> 
 * Please note: The user-defined properties <em>override</em> the default one.<p> 
 * Moreover, you can set properties programmatically too ({@link #setProperty(String, String)}) 
 * but make sure to override them <em>before</em> the first call of any property.
 */
public class CKPropertiesLoader {
	private static final Logger logger = LoggerFactory.getLogger(CKPropertiesLoader.class);
	private static final String DEFAULT_FILENAME = "default.properties";
	private static final String LOCAL_PROPERTIES = "/ckeditor.properties";
	private static Properties properties = new Properties();

	static {
		// 1. load library defaults
		InputStream inDefault = CKPropertiesLoader.class.getResourceAsStream(DEFAULT_FILENAME);

		if (inDefault == null) {
			logger.error("{} not found", DEFAULT_FILENAME);
			throw new RuntimeException(DEFAULT_FILENAME + " not found");
		} else {
			if (!(inDefault instanceof BufferedInputStream))
				inDefault = new BufferedInputStream(inDefault);

			try {
				properties.load(inDefault);
				inDefault.close();
				logger.info("{} successful loaded", DEFAULT_FILENAME);
			} catch (Exception e) {
				String msg = "Error while processing " + DEFAULT_FILENAME;
				logger.error(msg);
				throw new RuntimeException(msg, e);
			}
		}

		// 2. load user defaults if present
		InputStream inUser = CKPropertiesLoader.class.getResourceAsStream(LOCAL_PROPERTIES);
		if (inUser == null) {
			logger.info("{} not found", LOCAL_PROPERTIES);
		} else {
			if (!(inUser instanceof BufferedInputStream))
				inUser = new BufferedInputStream(inUser);
			try {
				properties.load(inUser);
				inUser.close();
				logger.info("{} successful loaded", LOCAL_PROPERTIES);
			} catch (Exception e) {
				String msg = "Error while processing " + LOCAL_PROPERTIES;
				logger.error(msg);
				throw new RuntimeException(msg, e);
			}
		}
	}
	
	static Map<String, String> getAllEditorProperties() {
		Map<String, String> props = new HashMap<>();
		for(Object keyObj : properties.keySet()) {
			String key = keyObj.toString();
			if(key.startsWith("default.") || key.startsWith("ckeditor."))
				continue;
			props.put(key, properties.getProperty(key));
		}
		return props;
	}

	/**
	 * Searches for the property with the specified key in this property list.
	 * 
	 * @see Properties#getProperty(String)
	 */
	public static String getProperty(final String key) {
		return properties.getProperty(key);
	}

	/**
	 * Sets the property with the specified key in this property list.
	 * 
	 * @see Properties#setProperty(String, String)
	 */
	public static void setProperty(final String key, final String value) {
		properties.setProperty(key, value);
	}

	/**
	 * Returns <code>default.encoding</code> property
	 */
	public static String getDefaultEncoding() {
		return properties.getProperty("default.encoding");
	}
	
	/**
	 * Returns <code>ckeditor.basePath</code> property and ensures that it ends with '/'.
	 */
	public static String getEditorBasePath() {
		String basePath = properties.getProperty("ckeditor.basePath");
		if(!basePath.endsWith("/"))
			basePath += "/";
		return basePath;
	}
}
